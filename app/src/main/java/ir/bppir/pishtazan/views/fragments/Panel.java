package ir.bppir.pishtazan.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentPanelBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Panel;
import ir.bppir.pishtazan.views.adapters.AP_Person;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Panel extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable,
        AP_Person.ClickItemPerson {

    private NavController navController;
    private VM_Panel vm_panel;
    public Byte panelType;
    private AP_Person AP_person;
    public Byte PersonType = 0;
    private Dialog dialog;
    private String stringDate;
    private String stringTime;
    private boolean GoToAddPerson;
    private View personPositionView;
    private CompositeDisposable disposable = new CompositeDisposable();


    @BindView(R.id.LinearLayoutParent)
    LinearLayout LinearLayoutParent;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;

    @BindView(R.id.RecyclerViewPanel)
    RecyclerView RecyclerViewPanel;

    @BindView(R.id.LinearLayoutAdd)
    LinearLayout LinearLayoutAdd;

    @BindView(R.id.LinearLayoutMaybe)
    LinearLayout LinearLayoutMaybe;

    @BindView(R.id.LinearLayoutPossible)
    LinearLayout LinearLayoutPossible;

    @BindView(R.id.LinearLayoutCertain)
    LinearLayout LinearLayoutCertain;

    @BindView(R.id.LinearLayoutUser)
    LinearLayout LinearLayoutUser;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.SwitchMaterialArchived)
    SwitchMaterial SwitchMaterialArchived;

    @BindView(R.id.TextViewNothing)
    TextView TextViewNothing;

    @BindView(R.id.editTextName)
    EditText editTextName;

    @BindView(R.id.switchMaterialSort)
    SwitchMaterial switchMaterialSort;


    @BindView(R.id.relativeLayoutPanel)
    RelativeLayout relativeLayoutPanel;


    public Panel() {//______________________________________________________________________________ Panel

    }//_____________________________________________________________________________________________ Panel


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_panel = new VM_Panel(getActivity());
            FragmentPanelBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_panel, container, false);
            binding.setPanel(vm_panel);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            PersonType = 0;
            LinearLayoutAdd.setVisibility(View.VISIBLE);
            init();
            /*            GetList();*/
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                Panel.this,
                vm_panel.getPublishSubject(),
                vm_panel);
        GoToAddPerson = false;
        GetList();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        Integer temp = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        panelType = temp.byteValue();
        if (panelType == StaticValues.Colleague) {
            TextViewTitle.setText(getContext().getResources().getString(R.string.ColleaguePanel));
            LinearLayoutUser.setVisibility(View.VISIBLE);
        } else {
            TextViewTitle.setText(getContext().getResources().getString(R.string.CustomerPanel));
            LinearLayoutUser.setVisibility(View.GONE);
        }

        disposable.add(RxTextView.textChangeEvents(editTextName)
                .skipInitialValue()
                .debounce(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

    }//_____________________________________________________________________________________________ init


    //______________________________________________________________________________________________ searchContactsTextWatcher
    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (editTextName.getText().toString().isEmpty())
                    hideKeyboard();
                GetList();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
    //______________________________________________________________________________________________ searchContactsTextWatcher


    private void GetList() {//______________________________________________________________________ GetList
        RecyclerViewPanel.setVisibility(View.GONE);
        TextViewNothing.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.VISIBLE);
        if (vm_panel.getPersonList() != null)
            vm_panel.getPersonList().clear();
        String fullName = editTextName.getText().toString();
        if (fullName.isEmpty())
            fullName = null;
        vm_panel.getPerson(panelType, PersonType, SwitchMaterialArchived.isChecked(), fullName, switchMaterialSort.isChecked());
    }//_____________________________________________________________________________________________ GetList


    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        GifViewLoading.setVisibility(View.GONE);

        if (action == StaticValues.ML_GetPerson) {
            SetAdapterPerson();
            return;
        }

        if (action == StaticValues.ML_ConvertPerson) {
            Animation outLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
            personPositionView.setAnimation(outLeft);
            personPositionView.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> GetList(), 700);
            return;
        }


        if (action == StaticValues.ML_DeletePerson) {
            Animation outLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
            personPositionView.setAnimation(outLeft);
            personPositionView.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> GetList(), 700);
            return;
        }

        if (action.equals(StaticValues.ML_DeleteArchive)) {
            Handler handler = new Handler();
            handler.postDelayed(() -> GetList(), 700);
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick


        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            int downX, upX;
            int downY, upY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = (int) event.getX();
                    upY = (int) event.getY();

                    Log.i("meri", "downX : " + downX + "     upX : " + upX + "     MathX : " + Math.abs(upX-downX));
                    Log.i("meri", "downY : " + downY + "     upY : " + upY + "     MathY : " + Math.abs(upY-downY));

                    if (Math.abs(downY - upY) > 90)
                        return false;


                    if (upX == downX)
                        return false;

                    if (Math.abs(upX-downX) < 120)
                        return false;

                    if (upX - downX > 130) {
                        swipeListLeft();
                        // swipe right
                    } else if (downX - upX > -130) {
                        swipeListRight();
                        // swipe left
                    }
                    return true;

                }
                return false;
            }
        };


        relativeLayoutPanel.setOnTouchListener(onTouchListener);


        RecyclerViewPanel.setOnTouchListener(onTouchListener);


        LinearLayoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToAddPerson = true;
                Bundle bundle = new Bundle();
                bundle.putInt(getContext().getString(R.string.ML_PanelType), panelType);
                navController.navigate(R.id.action_panel_to_addPerson, bundle);
            }
        });

        LinearLayoutMaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
                SwitchMaterialArchived.setChecked(false);
                switchMaterialSort.setChecked(false);
                LinearLayoutAdd.setVisibility(View.VISIBLE);
                LinearLayoutPossible.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutUser.setBackground(null);
                LinearLayoutMaybe.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Maybe;
//                GetList();
            }
        });

        LinearLayoutPossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutAdd.setVisibility(View.GONE);
                editTextName.setText("");
                SwitchMaterialArchived.setChecked(false);
                switchMaterialSort.setChecked(false);
                editTextName.setVisibility(View.VISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutUser.setBackground(null);
                LinearLayoutPossible.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Possible;
//                GetList();
            }
        });

        LinearLayoutCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutAdd.setVisibility(View.GONE);
                editTextName.setText("");
                SwitchMaterialArchived.setChecked(false);
                switchMaterialSort.setChecked(false);
                editTextName.setVisibility(View.VISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutPossible.setBackground(null);
                LinearLayoutUser.setBackground(null);
                LinearLayoutCertain.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Certain;
//                GetList();
            }
        });


        LinearLayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutAdd.setVisibility(View.GONE);
                editTextName.setText("");
                SwitchMaterialArchived.setChecked(false);
                switchMaterialSort.setChecked(false);
                editTextName.setVisibility(View.VISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutPossible.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutUser.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_User;
//                GetList();
            }
        });

        SwitchMaterialArchived.setOnClickListener(v -> GetList());

        switchMaterialSort.setOnClickListener(v -> GetList());

    }//_____________________________________________________________________________________________ SetClick


    //______________________________________________________________________________________________ swipeListLeft
    private void swipeListLeft() {

        if (PersonType.equals(StaticValues.ML_Maybe)) {
            LinearLayoutAdd.setVisibility(View.GONE);
            editTextName.setText("");
            SwitchMaterialArchived.setChecked(false);
            switchMaterialSort.setChecked(false);
            editTextName.setVisibility(View.VISIBLE);
            LinearLayoutMaybe.setBackground(null);
            LinearLayoutCertain.setBackground(null);
            LinearLayoutUser.setBackground(null);
            LinearLayoutPossible.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
            PersonType = StaticValues.ML_Possible;
        } else if (PersonType.equals(StaticValues.ML_Possible)) {
            LinearLayoutAdd.setVisibility(View.GONE);
            editTextName.setText("");
            SwitchMaterialArchived.setChecked(false);
            switchMaterialSort.setChecked(false);
            editTextName.setVisibility(View.VISIBLE);
            LinearLayoutMaybe.setBackground(null);
            LinearLayoutPossible.setBackground(null);
            LinearLayoutUser.setBackground(null);
            LinearLayoutCertain.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
            PersonType = StaticValues.ML_Certain;
        } else if (PersonType.equals(StaticValues.ML_Certain)) {
            if (panelType.equals(StaticValues.Colleague)) {
                LinearLayoutAdd.setVisibility(View.GONE);
                editTextName.setText("");
                SwitchMaterialArchived.setChecked(false);
                switchMaterialSort.setChecked(false);
                editTextName.setVisibility(View.VISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutPossible.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutUser.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_User;
            }
        }
    }
    //______________________________________________________________________________________________ swipeListLeft


    //______________________________________________________________________________________________ swipeListRight
    private void swipeListRight() {

        if (PersonType.equals(StaticValues.ML_User)) {
            LinearLayoutAdd.setVisibility(View.GONE);
            editTextName.setText("");
            SwitchMaterialArchived.setChecked(false);
            switchMaterialSort.setChecked(false);
            editTextName.setVisibility(View.VISIBLE);
            LinearLayoutMaybe.setBackground(null);
            LinearLayoutPossible.setBackground(null);
            LinearLayoutUser.setBackground(null);
            LinearLayoutCertain.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
            PersonType = StaticValues.ML_Certain;
        } else if (PersonType.equals(StaticValues.ML_Certain)) {
            LinearLayoutAdd.setVisibility(View.GONE);
            editTextName.setText("");
            SwitchMaterialArchived.setChecked(false);
            switchMaterialSort.setChecked(false);
            editTextName.setVisibility(View.VISIBLE);
            LinearLayoutMaybe.setBackground(null);
            LinearLayoutCertain.setBackground(null);
            LinearLayoutUser.setBackground(null);
            LinearLayoutPossible.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
            PersonType = StaticValues.ML_Possible;
        } else if (PersonType.equals(StaticValues.ML_Possible)) {
            editTextName.setText("");
            SwitchMaterialArchived.setChecked(false);
            switchMaterialSort.setChecked(false);
            LinearLayoutAdd.setVisibility(View.VISIBLE);
            LinearLayoutPossible.setBackground(null);
            LinearLayoutCertain.setBackground(null);
            LinearLayoutUser.setBackground(null);
            LinearLayoutMaybe.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
            PersonType = StaticValues.ML_Maybe;
        }

    }
    //______________________________________________________________________________________________ swipeListRight


    private void SetAdapterPerson() {//_____________________________________________________________ SetAdapterPerson
        AP_person = new AP_Person(vm_panel.getPersonList(), getContext(), Panel.this);
        RecyclerViewPanel.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPanel.setAdapter(AP_person);
        if (vm_panel.getPersonList().size() == 0) {
            RecyclerViewPanel.setVisibility(View.GONE);
            TextViewNothing.setVisibility(View.VISIBLE);
        } else {
            RecyclerViewPanel.setVisibility(View.VISIBLE);
            TextViewNothing.setVisibility(View.GONE);
        }
    }//_____________________________________________________________________________________________ SetAdapterPerson


    public void ChooseActionFromList(Integer Position, View view) {//_______________________________ ChooseActionFromList

        if (dialog != null)
            dialog.dismiss();
        dialog = null;

        stringDate = "";
        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_actions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        LinearLayout LinearLayoutCompleteInformation = dialog.findViewById(R.id.LinearLayoutCompleteInformation);

        LinearLayout LinearLayoutCallReminder = dialog.findViewById(R.id.LinearLayoutCallReminder);

        LinearLayout LinearLayoutMeetingReminder = dialog.findViewById(R.id.LinearLayoutMeetingReminder);

        LinearLayout LinearLayoutConvertToCustomer = dialog.findViewById(R.id.LinearLayoutConvertToCustomer);

        LinearLayout LinearLayoutConvertToColleague = dialog.findViewById(R.id.LinearLayoutConvertToColleague);

        LinearLayout LinearLayoutDeleteFromList = dialog.findViewById(R.id.LinearLayoutDeleteFromList);

        LinearLayout LinearLayoutCancel = dialog.findViewById(R.id.LinearLayoutCancel);

        LinearLayout LinearLayoutQuestionnaire = dialog.findViewById(R.id.LinearLayoutQuestionnaire);

        LinearLayout LinearLayoutInsurance = dialog.findViewById(R.id.LinearLayoutInsurance);

        LinearLayout LinearLayoutNoArchived = dialog.findViewById(R.id.LinearLayoutNoArchived);

        TextView textView = dialog.findViewById(R.id.textViewActionDialog);

        LinearLayout LinearLayoutConvertToUser = dialog.findViewById(R.id.LinearLayoutConvertToUser);

        if (SwitchMaterialArchived.isChecked()) {
            LinearLayoutCompleteInformation.setVisibility(View.GONE);
            LinearLayoutCallReminder.setVisibility(View.GONE);
            LinearLayoutMeetingReminder.setVisibility(View.GONE);
            LinearLayoutConvertToCustomer.setVisibility(View.GONE);
            LinearLayoutConvertToColleague.setVisibility(View.GONE);
            LinearLayoutDeleteFromList.setVisibility(View.GONE);
            LinearLayoutQuestionnaire.setVisibility(View.GONE);
            LinearLayoutInsurance.setVisibility(View.GONE);
            LinearLayoutConvertToUser.setVisibility(View.GONE);
            LinearLayoutNoArchived.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            LinearLayoutNoArchived.setVisibility(View.GONE);

            if (panelType == StaticValues.Customer) {
                LinearLayoutConvertToColleague.setVisibility(View.GONE);
                LinearLayoutConvertToUser.setVisibility(View.GONE);
                if (PersonType == StaticValues.ML_Certain) {
                    textView.setVisibility(View.GONE);
//                LinearLayoutCompleteInformation.setVisibility(View.GONE);
//                LinearLayoutCallReminder.setVisibility(View.GONE);
//                LinearLayoutMeetingReminder.setVisibility(View.GONE);
                    LinearLayoutConvertToCustomer.setVisibility(View.GONE);
                    LinearLayoutConvertToColleague.setVisibility(View.GONE);
                    LinearLayoutDeleteFromList.setVisibility(View.GONE);
//                LinearLayoutQuestionnaire.setVisibility(View.GONE);
//                LinearLayoutInsurance.setVisibility(View.GONE);
                    LinearLayoutNoArchived.setVisibility(View.GONE);
                } else if (PersonType == StaticValues.ML_Possible) {
                    if (vm_panel.getPersonList().get(Position).getNationalCode() == null ||
                            vm_panel.getPersonList().get(Position).getNationalCode().equalsIgnoreCase("")) {
/*                    LinearLayoutCallReminder.setVisibility(View.GONE);
                    LinearLayoutMeetingReminder.setVisibility(View.GONE);*/
                        LinearLayoutConvertToCustomer.setVisibility(View.GONE);
                        LinearLayoutConvertToColleague.setVisibility(View.GONE);
                        /*                    LinearLayoutDeleteFromList.setVisibility(View.GONE);*/
                        LinearLayoutQuestionnaire.setVisibility(View.GONE);
                        LinearLayoutInsurance.setVisibility(View.GONE);
                        LinearLayoutNoArchived.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        LinearLayoutInsurance.setVisibility(View.GONE);
                        LinearLayoutQuestionnaire.setVisibility(View.GONE);
                    }
                }
            } else {
                LinearLayoutConvertToCustomer.setVisibility(View.GONE);
                if (PersonType == StaticValues.ML_Certain) {
                    textView.setVisibility(View.GONE);
//                LinearLayoutCompleteInformation.setVisibility(View.GONE);
//                LinearLayoutCallReminder.setVisibility(View.GONE);
//                LinearLayoutMeetingReminder.setVisibility(View.GONE);
                    LinearLayoutConvertToCustomer.setVisibility(View.GONE);
                    LinearLayoutConvertToColleague.setVisibility(View.GONE);
                    LinearLayoutDeleteFromList.setVisibility(View.GONE);
                    LinearLayoutNoArchived.setVisibility(View.GONE);
                    LinearLayoutConvertToUser.setVisibility(View.GONE);
                } else {
                    if (vm_panel.getPersonList().get(Position).getNationalCode() == null ||
                            vm_panel.getPersonList().get(Position).getNationalCode().equalsIgnoreCase("")) {
/*                    LinearLayoutCallReminder.setVisibility(View.GONE);
                    LinearLayoutMeetingReminder.setVisibility(View.GONE);*/
                        LinearLayoutConvertToCustomer.setVisibility(View.GONE);
                        LinearLayoutConvertToColleague.setVisibility(View.GONE);
                        /*                    LinearLayoutDeleteFromList.setVisibility(View.GONE);*/
                        LinearLayoutQuestionnaire.setVisibility(View.GONE);
                        LinearLayoutInsurance.setVisibility(View.GONE);
                        LinearLayoutNoArchived.setVisibility(View.GONE);
                        LinearLayoutConvertToUser.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        LinearLayoutQuestionnaire.setVisibility(View.GONE);
                        LinearLayoutConvertToUser.setVisibility(View.GONE);
//                        LinearLayoutConvertToColleague.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        LinearLayoutInsurance.setVisibility(View.GONE);
                    }
                }
            }
        }



        LinearLayoutQuestionnaire.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
            ShowSavePolicyType(Position, StaticValues.PolicyStatusQuestionnaire);
        });

        LinearLayoutInsurance.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
            ShowSavePolicyType(Position, StaticValues.PolicyStatusInsurance);
        });

        LinearLayoutCompleteInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                EditProfilePerson(Position);
            }
        });

        LinearLayoutCallReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                Bundle bundle = new Bundle();
                bundle.putInt(getResources().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
                bundle.putInt(getResources().getString(R.string.ML_Type), StaticValues.Call);
                bundle.putByte(getResources().getString(R.string.ML_PanelType), panelType);
                bundle.putString(getResources().getString(R.string.ML_Name), vm_panel.getPersonList().get(Position).getFullName());
                navController.navigate(R.id.action_panel_to_reminder, bundle);

            }
        });

        LinearLayoutMeetingReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                Bundle bundle = new Bundle();
                bundle.putInt(getResources().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
                bundle.putInt(getResources().getString(R.string.ML_Type), StaticValues.Meeting);
                bundle.putByte(getResources().getString(R.string.ML_PanelType), panelType);
                bundle.putString(getResources().getString(R.string.ML_Name), vm_panel.getPersonList().get(Position).getFullName());
                navController.navigate(R.id.action_panel_to_reminder, bundle);
            }
        });

        LinearLayoutConvertToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                MoveToCertain(Position);
            }
        });

        LinearLayoutConvertToColleague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                MoveToCertain(Position);
            }
        });

        LinearLayoutDeleteFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDeleteQuestion(Position, view);
            }
        });

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayoutNoArchived.setOnClickListener(v -> {
            GifViewLoading.setVisibility(View.VISIBLE);
            vm_panel.deletePersonFromArchive(panelType, Position);
        });

        dialog.show();


//        if (Action == 5)
//            ShowDeleteQuestion(Position);
//        else {
//            if (PersonType == StaticValues.ML_Maybe)
//                ActionForMaybe(Position, Action);
//            else if (PersonType == StaticValues.ML_Possible)
//                ActionForPossible(Position, Action);
//        }

    }//_____________________________________________________________________________________________ ChooseActionFromList


    private void ShowSavePolicyType(Integer Position, Byte type) {//________________________________ ShowSavePolicyType
        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getResources().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
        bundle.putInt(getContext().getResources().getString(R.string.ML_Type), type);
        bundle.putByte(getContext().getResources().getString(R.string.ML_PanelType), panelType);
        navController.navigate(R.id.action_panel_to_policies, bundle);
    }//_____________________________________________________________________________________________ ShowSavePolicyType


    public void ShowDeleteQuestion(Integer Position, View view) {//____________________________________________ ShowMeetingReminder

        if (dialog != null)
            dialog.dismiss();
        dialog = null;


        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_question);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        personPositionView = view;

        TextView TextViewQuestionTitle = (TextView)
                dialog.findViewById(R.id.TextViewQuestionTitle);

        GifView GifViewQuestion = (GifView) dialog.findViewById(R.id.GifViewQuestion);

        ImageView ImageViewQuestion = (ImageView) dialog.findViewById(R.id.ImageViewQuestion);

        GifViewQuestion.setVisibility(View.GONE);

        ImageViewQuestion.setVisibility(View.VISIBLE);


        StringBuilder sp = new StringBuilder();
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToDeleteIt1));
        sp.append(" ");
        sp.append(vm_panel.getPersonList().get(Position).getFullName());
        sp.append(" ");
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToDeleteIt2));

        TextViewQuestionTitle.setText(sp.toString());

        LinearLayout LinearLayoutNo = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutNo);

        LinearLayoutNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayout LinearLayoutYes = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutYes);

        LinearLayoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GifViewQuestion.setVisibility(View.VISIBLE);
                ImageViewQuestion.setVisibility(View.GONE);
                vm_panel.deletePerson(panelType, Position);
            }
        });

        dialog.show();

    }//_____________________________________________________________________________________________ ShowMeetingReminder


    public void AdapterMoveToPossible(Integer Position, View view) {//______________________________ AdapterMoveToPossible

        if (dialog != null)
            dialog.dismiss();
        dialog = null;


        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_question);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        personPositionView = view;

        TextView TextViewQuestionTitle = (TextView)
                dialog.findViewById(R.id.TextViewQuestionTitle);

        GifView GifViewQuestion = (GifView) dialog.findViewById(R.id.GifViewQuestion);

        ImageView ImageViewQuestion = (ImageView) dialog.findViewById(R.id.ImageViewQuestion);

        GifViewQuestion.setVisibility(View.GONE);

        ImageViewQuestion.setVisibility(View.VISIBLE);


        StringBuilder sp = new StringBuilder();
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToMoveIt1));
        sp.append(" ");
        sp.append(vm_panel.getPersonList().get(Position).getFullName());
        sp.append(" ");
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToDeleteIt2));

        TextViewQuestionTitle.setText(sp.toString());

        LinearLayout LinearLayoutNo = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutNo);

        LinearLayoutNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayout LinearLayoutYes = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutYes);

        LinearLayoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GifViewQuestion.setVisibility(View.VISIBLE);
                ImageViewQuestion.setVisibility(View.GONE);
                vm_panel.moveToPossible(panelType, Position);
            }
        });

        dialog.show();

    }//_____________________________________________________________________________________________ AdapterMoveToPossible


    private void MoveToCertain(Integer Position) {//_______________________________________________ MoveToPossible
        if (panelType == StaticValues.Customer) {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
            bundle.putBoolean(getContext().getResources().getString(R.string.ML_Type), false);
            bundle.putByte(getContext().getResources().getString(R.string.ML_PanelType), panelType);
            navController.navigate(R.id.action_panel_to_policyType, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
            bundle.putBoolean(getContext().getResources().getString(R.string.ML_Type), false);
            bundle.putByte(getContext().getResources().getString(R.string.ML_PanelType), panelType);
            navController.navigate(R.id.action_panel_to_policyType, bundle);
        }
    }//_____________________________________________________________________________________________ MoveToPossible


    @Override
    public void clickItemPerson(Integer Position, View view) {//____________________________________ clickItemPerson

        if (PersonType == StaticValues.ML_Maybe) {
            if (vm_panel.getPersonList().get(Position).isDelete())
                ChooseActionFromList(Position, view);
            else
                AdapterMoveToPossible(Position, view);
        } else if (PersonType == StaticValues.ML_Possible) {
            ChooseActionFromList(Position, view);
        } else
            ChooseActionFromList(Position, view);

    }//_____________________________________________________________________________________________ clickItemPerson

    @Override
    public void clickDeleteItemPerson(Integer Position, View view) {//______________________________ clickDeleteItemPerson
        ShowDeleteQuestion(Position, view);
    }//_____________________________________________________________________________________________ clickDeleteItemPerson


    private void EditProfilePerson(Integer Position) {//____________________________________________ EditProfilePerson
        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getString(R.string.ML_PanelType), panelType);
        bundle.putInt(getContext().getString(R.string.ML_personId), vm_panel.getPersonList().get(Position).getId());
        navController.navigate(R.id.action_panel_to_editPerson, bundle);
    }//_____________________________________________________________________________________________ EditProfilePerson


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
