package ir.bppir.pishtazan.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class Panel extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Panel vm_panel;
    public int panelType;
    private AP_Person AP_person;
    public Byte PersonType = 0;
    private Dialog dialog;
    private Long longDate;
    private String stringDate;
    private Long longTime;
    private String stringTime;
    private boolean GoToAddPerson;

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

    @BindView(R.id.ProgressGifCertain)
    GifView ProgressGifCertain;

    @BindView(R.id.ProgressGifPossible)
    GifView ProgressGifPossible;

    @BindView(R.id.ProgressGifMaybe)
    GifView ProgressGifMaybe;


    public Panel() {//______________________________________________________________________________ Panel

    }//_____________________________________________________________________________________________ Panel


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_panel = new VM_Panel(getContext());
            FragmentPanelBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_panel, container, false);
            binding.setPanel(vm_panel);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            PersonType = 0;
            LinearLayoutAdd.setVisibility(View.VISIBLE);
            init();
            ProgressGifMaybe.setVisibility(View.VISIBLE);
            GetList();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                Panel.this,
                vm_panel.getPublishSubject(),
                vm_panel);
        if (GoToAddPerson == true) {
            ProgressGifMaybe.setVisibility(View.VISIBLE);
            GetList();
        }
        GoToAddPerson = false;
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        panelType = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        if (panelType == StaticValues.Colleague) {
            TextViewTitle.setText(getContext().getResources().getString(R.string.ColleaguePanel));
        } else {
            TextViewTitle.setText(getContext().getResources().getString(R.string.CustomerPanel));
        }

    }//_____________________________________________________________________________________________ init


    private void GetList() {//______________________________________________________________________ GetList
        vm_panel.GetPerson(panelType, PersonType);
    }//_____________________________________________________________________________________________ GetList


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_GetPerson) {
            if (PersonType == StaticValues.ML_Maybe)
                LinearLayoutAdd.setVisibility(View.VISIBLE);
            SetAdapterPerson();
            return;
        }

        if (action == StaticValues.ML_ConvertPerson) {
            GetList();
            ShowMessage(
                    vm_panel.getResponseMessage(),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_how_to_reg),
                    getResources().getColor(R.color.ML_OK));
            return;
        }

        if (action == StaticValues.ML_DeletePerson) {
            GetList();
            ShowMessage(
                    vm_panel.getResponseMessage(),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_how_to_reg),
                    getResources().getColor(R.color.ML_OK));
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GoneGifLoading() {//_______________________________________________________________ GoneGifLoading
        ProgressGifCertain.setVisibility(View.GONE);
        ProgressGifPossible.setVisibility(View.GONE);
        ProgressGifMaybe.setVisibility(View.GONE);
    }//_____________________________________________________________________________________________ GoneGifLoading


    private void SetClick() {//_____________________________________________________________________ SetClick

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
                LinearLayoutPossible.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutMaybe.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Maybe;
                GoneGifLoading();
                ProgressGifMaybe.setVisibility(View.VISIBLE);
                GetList();
            }
        });

        LinearLayoutPossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutAdd.setVisibility(View.INVISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutCertain.setBackground(null);
                LinearLayoutPossible.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Possible;
                GoneGifLoading();
                ProgressGifPossible.setVisibility(View.VISIBLE);
                GetList();
            }
        });

        LinearLayoutCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutAdd.setVisibility(View.INVISIBLE);
                LinearLayoutMaybe.setBackground(null);
                LinearLayoutPossible.setBackground(null);
                LinearLayoutCertain.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                PersonType = StaticValues.ML_Certain;
                GoneGifLoading();
                ProgressGifCertain.setVisibility(View.VISIBLE);
                GetList();
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetAdapterPerson() {//_____________________________________________________________ SetAdapterPerson
        GoneGifLoading();
        AP_person = new AP_Person(vm_panel.getPersonList(), getContext(), Panel.this);
        RecyclerViewPanel.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPanel.setAdapter(AP_person);
    }//_____________________________________________________________________________________________ SetAdapterPerson


    public void AdapterMoveToPossible(Integer Position) {//_________________________________________ AdapterMoveToPossible

    }//_____________________________________________________________________________________________ AdapterMoveToPossible


    public void ChooseActionFromList(Integer Position) {//__________________________________________ ChooseActionFromList

        if (dialog != null)
            dialog.dismiss();
        dialog = null;

        longDate = Long.valueOf(0);
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

        LinearLayout LinearLayoutCompleteInformation = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCompleteInformation);

        LinearLayout LinearLayoutCallReminder = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCallReminder);

        LinearLayout LinearLayoutMeetingReminder = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutMeetingReminder);

        LinearLayout LinearLayoutMoveToPossible = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutMoveToPossible);

        LinearLayout LinearLayoutMoveToCertain = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutMoveToCertain);

        LinearLayout LinearLayoutDeleteFromList = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutDeleteFromList);

        LinearLayout LinearLayoutCancel = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCancel);

        if (PersonType == StaticValues.ML_Maybe) {
            LinearLayoutMoveToPossible.setVisibility(View.VISIBLE);
            LinearLayoutMoveToCertain.setVisibility(View.GONE);
        } else if (PersonType == StaticValues.ML_Possible) {
            LinearLayoutMoveToPossible.setVisibility(View.GONE);
            LinearLayoutMoveToCertain.setVisibility(View.GONE);
        } else if (PersonType == StaticValues.ML_Certain) {
            LinearLayoutMoveToCertain.setVisibility(View.GONE);
            LinearLayoutMoveToPossible.setVisibility(View.GONE);
        }

        LinearLayoutCallReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                ShowCallReminder(Position);

            }
        });

        LinearLayoutMeetingReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                ShowMeetingReminder(Position);
            }
        });

        LinearLayoutMoveToPossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
                MoveToPossible(Position);
            }
        });

        LinearLayoutMoveToCertain.setOnClickListener(new View.OnClickListener() {
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

                ShowDeleteQuestion(Position);
            }
        });

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
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


    private void ShowCallReminder(Integer Position) {//_____________________________________________ ShowCallReminder

        if (dialog != null)
            dialog.dismiss();
        dialog = null;


        longDate = Long.valueOf(0);
        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reminder);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        TimePicker TimePickerReminder = (TimePicker)
                dialog.findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = (TextView)
                dialog.findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianPickerModule.context = getContext();
                PersianDatePickerDialog persianCalendar = PishtazanApplication
                        .getApplication(getContext())
                        .getPersianPickerComponent()
                        .getPersianDatePickerDialog();

                persianCalendar.setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(persianCalendar.getPersianYear());
                        sb1.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb1.append(String.format("%02d", persianCalendar.getPersianDay()));
                        longDate = Long.valueOf(sb1.toString());

                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(persianCalendar.getPersianYear());
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                        stringDate = sb2.toString();
                        TextViewChooseDate.setText(stringDate);
                        TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
                persianCalendar.show();
            }
        });

        LinearLayout LinearLayoutCancel = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayout LinearLayoutSave = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (longDate == 0) {
                    TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_empty_background));
                    return;
                }
                dialog.dismiss();
                dialog = null;
                StringBuilder sb1 = new StringBuilder();
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                longTime = Long.valueOf(sb1.toString());

                StringBuilder sb2 = new StringBuilder();
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb2.append(":");
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                stringTime = sb2.toString();

                SaveCallReminder(Position);
            }
        });

        dialog.show();

    }//_____________________________________________________________________________________________ ShowCallReminder


    private void ShowMeetingReminder(Integer Position) {//__________________________________________ ShowMeetingReminder

        if (dialog != null)
            dialog.dismiss();
        dialog = null;

        longDate = Long.valueOf(0);
        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reminder);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        TimePicker TimePickerReminder = (TimePicker)
                dialog.findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = (TextView)
                dialog.findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianPickerModule.context = getContext();
                PersianDatePickerDialog persianCalendar = PishtazanApplication
                        .getApplication(getContext())
                        .getPersianPickerComponent()
                        .getPersianDatePickerDialog();

                persianCalendar.setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(persianCalendar.getPersianYear());
                        sb1.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb1.append(String.format("%02d", persianCalendar.getPersianDay()));
                        longDate = Long.valueOf(sb1.toString());

                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(persianCalendar.getPersianYear());
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                        stringDate = sb2.toString();
                        TextViewChooseDate.setText(stringDate);
                        TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
                persianCalendar.show();
            }
        });

        LinearLayout LinearLayoutCancel = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayout LinearLayoutSave = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (longDate == 0) {
                    TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_empty_background));
                    return;
                }
                dialog.dismiss();
                dialog = null;
                StringBuilder sb1 = new StringBuilder();
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                longTime = Long.valueOf(sb1.toString());

                StringBuilder sb2 = new StringBuilder();
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb2.append(":");
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                stringTime = sb2.toString();
                SaveMeetingReminder(Position);
            }
        });

        dialog.show();

    }//_____________________________________________________________________________________________ ShowMeetingReminder


    public void ShowDeleteQuestion(Integer Position) {//____________________________________________ ShowMeetingReminder

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


        TextView TextViewQuestionTitle = (TextView)
                dialog.findViewById(R.id.TextViewQuestionTitle);

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
                dialog.dismiss();
                dialog = null;
                vm_panel.DeletePerson(Position);
            }
        });

        dialog.show();

    }//_____________________________________________________________________________________________ ShowMeetingReminder


    private void SaveCallReminder(Integer Position) {//_____________________________________________ SaveCallReminder
//        vm_panel.SaveCallReminder(Position, longDate, stringDate, longTime, stringTime);
    }//_____________________________________________________________________________________________ SaveCallReminder


    private void SaveMeetingReminder(Integer Position) {//__________________________________________ SaveMeetingReminder
//        vm_panel.SaveMeetingReminder(Position, longDate, stringDate, longTime, stringTime);
    }//_____________________________________________________________________________________________ SaveMeetingReminder


    private void MoveToPossible(Integer Position) {//_______________________________________________ MoveToPossible
        vm_panel.MoveToPossible(Position);
    }//_____________________________________________________________________________________________ MoveToPossible


    private void MoveToCertain(Integer Position) {//_______________________________________________ MoveToPossible
        vm_panel.MoveToCertain(Position);
    }//_____________________________________________________________________________________________ MoveToPossible


}
