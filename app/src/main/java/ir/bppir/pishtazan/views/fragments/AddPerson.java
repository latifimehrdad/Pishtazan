package ir.bppir.pishtazan.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentAddPersonBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.utility.StaticFunctions;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_AddPerson;
import ir.bppir.pishtazan.views.adapters.AP_Contact;

import static ir.bppir.pishtazan.utility.StaticFunctions.TextChangeForChangeBack;

public class AddPerson extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private int panelType;
    private VM_AddPerson vm_addPerson;
    private Dialog dialogContact;
    private AP_Contact AP_contact;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<MD_Contact> md_contacts;
    private RecyclerView RecyclerViewContact;
    private Byte Degree = 0;


    @BindView(R.id.RelativeLayoutAdd)
    RelativeLayout RelativeLayoutAdd;

    @BindView(R.id.ImageViewAdd)
    ImageView ImageViewAdd;

    @BindView(R.id.TextViewAdd)
    TextView TextViewAdd;

    @BindView(R.id.GifViewAdd)
    GifView GifViewAdd;

    @BindView(R.id.EditTextName)
    EditText EditTextName;

    @BindView(R.id.EditTextPhoneNumber)
    EditText EditTextPhoneNumber;

    @BindView(R.id.LinearLayoutCancel)
    LinearLayout LinearLayoutCancel;

    @BindView(R.id.LinearLayoutSave)
    LinearLayout LinearLayoutSave;

    @BindView(R.id.ImageViewSend)
    ImageView ImageViewSend;

    @BindView(R.id.GifViewSend)
    GifView GifViewSend;

    @BindView(R.id.TextViewSend)
    TextView TextViewSend;

    @BindView(R.id.LinearLayoutNormal)
    LinearLayout LinearLayoutNormal;

    @BindView(R.id.LinearLayoutPeach)
    LinearLayout LinearLayoutPeach;

    @BindView(R.id.LinearLayoutGiant)
    LinearLayout LinearLayoutGiant;

    public AddPerson() {//__________________________________________________________________________ AddPerson

    }//_____________________________________________________________________________________________ AddPerson


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_addPerson = new VM_AddPerson(getContext());
            FragmentAddPersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_add_person, container, false);
            binding.setAddPerson(vm_addPerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTextWatcher();
            SetClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(AddPerson.this, vm_addPerson.getPublishSubject());
        panelType = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        if (panelType == StaticValues.Customer) {

        } else {

        }

    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_AddPerson) {
            FinishLoadingSend();
            ShowMessage(
                    vm_addPerson.getResponseMessage(),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_how_to_reg),
                    getResources().getColor(R.color.ML_OK));
            EditTextName.getText().clear();
            EditTextPhoneNumber.getText().clear();
            EditTextName.requestFocus();
            LinearLayoutGiant.setBackground(null);
            LinearLayoutNormal.setBackground(null);
            LinearLayoutPeach.setBackground(null);
            //getActivity().onBackPressed();
            return;
        }

        if (action == StaticValues.ML_GetContact) {
            if (AP_contact == null) {
                FinishWaiting();
                ShowContactDialog();
            } else {
                if (dialogContact == null || !dialogContact.isShowing())
                    ShowContactDialog();
                else {
                    md_contacts = vm_addPerson.getMd_contacts();
                    SetContactAdapter();
                }
            }
            return;
        }

        if (action == StaticValues.ML_GetContactFilter) {
            md_contacts = vm_addPerson.getMd_contactsFilter();
            SetContactAdapter();
            return;
        }

        if (action == StaticValues.ML_Error) {
            FinishWaiting();
            ShowMessage(
                    vm_addPerson.getResponseMessage(),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                    getResources().getColor(R.color.ML_Red));
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick

        RelativeLayoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAccessClick()) {
                    StaticFunctions.hideKeyboard(getActivity());
                    ShowWaiting();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            vm_addPerson.GetContact();
                        }
                    }, 1000);

                }
            }
        });

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticFunctions.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });


        LinearLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEmpty()) {
                    StaticFunctions.hideKeyboard(getActivity());
                    ShowLoadingSend();
                    vm_addPerson.AddPerson(
                            EditTextName.getText().toString(),
                            EditTextPhoneNumber.getText().toString(),
                            Degree,
                            panelType
                    );
                }
            }
        });


        LinearLayoutNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutPeach.setBackground(null);
                LinearLayoutGiant.setBackground(null);
                LinearLayoutNormal.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreeNormal;
            }
        });


        LinearLayoutPeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutNormal.setBackground(null);
                LinearLayoutGiant.setBackground(null);
                LinearLayoutPeach.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreePeach;
            }
        });


        LinearLayoutGiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutNormal.setBackground(null);
                LinearLayoutPeach.setBackground(null);
                LinearLayoutGiant.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreeGiant;
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditTextPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditTextPhoneNumber));
        EditTextName.addTextChangedListener(TextChangeForChangeBack(EditTextName));
    }//_____________________________________________________________________________________________ End SetTextWatcher



    private void ShowLoadingSend() {//______________________________________________________________ ShowLoadingSend

        setAccessClick(false);
        ImageViewSend.setVisibility(View.GONE);
        GifViewSend.setVisibility(View.VISIBLE);
        TextViewSend.setText(getContext().getResources().getString(R.string.PleaseWait));

    }//_____________________________________________________________________________________________ ShowLoadingSend



    private void FinishLoadingSend() {//____________________________________________________________ ShowLoadingSend

        setAccessClick(true);
        ImageViewSend.setVisibility(View.VISIBLE);
        GifViewSend.setVisibility(View.GONE);
        TextViewSend.setText(getContext().getResources().getString(R.string.Save));

    }//_____________________________________________________________________________________________ ShowLoadingSend




    private void ShowWaiting() {//__________________________________________________________________ ShowWaiting
        setAccessClick(false);
        GifViewAdd.setVisibility(View.VISIBLE);
        ImageViewAdd.setVisibility(View.GONE);
        TextViewAdd.setText(getContext().getString(R.string.PleaseWait));
        RelativeLayoutAdd.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_bottom_connection));
    }//_____________________________________________________________________________________________ ShowWaiting


    private void FinishWaiting() {//________________________________________________________________ FinishWaiting
        setAccessClick(true);
        GifViewAdd.setVisibility(View.GONE);
        ImageViewAdd.setVisibility(View.VISIBLE);
        TextViewAdd.setText(getContext().getString(R.string.AddFromContact));
        RelativeLayoutAdd.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_bottom));
    }//_____________________________________________________________________________________________ FinishWaiting


    private void ShowContactDialog() {//____________________________________________________________ ShowContactDialog

        if (dialogContact != null)
            if (dialogContact.isShowing())
                dialogContact.dismiss();
        dialogContact = null;
        dialogContact = new Dialog(getContext());
        dialogContact.setCancelable(true);
        dialogContact.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContact.setContentView(R.layout.dialog_contact);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogContact.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        RecyclerViewContact = (RecyclerView)
                dialogContact.findViewById(R.id.RecyclerViewContact);

        md_contacts = vm_addPerson.getMd_contacts();
        SetContactAdapter();

        LinearLayout LinearLayoutCancel = (LinearLayout)
                dialogContact.findViewById(R.id.LinearLayoutCancel);
        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogContact.dismiss();
            }
        });

        EditText EditTextName = (EditText)
                dialogContact.findViewById(R.id.EditTextName);

        disposable.add(RxTextView.textChangeEvents(EditTextName)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

        dialogContact.show();
    }//_____________________________________________________________________________________________ ShowContactDialog


    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {//_____________ searchContactsTextWatcher
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                String text = textViewTextChangeEvent.text().toString();
                vm_addPerson.FilterContact(text);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }//_____________________________________________________________________________________________ searchContactsTextWatcher


    private void SetContactAdapter() {//____________________________________________________________ SetContactAdapter
        AP_contact = new AP_Contact(md_contacts, getContext(), AddPerson.this);
        RecyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewContact.setAdapter(AP_contact);
    }//_____________________________________________________________________________________________ SetContactAdapter



    public void ClickContact(Integer position) {//__________________________________________________ SetContactAdapter
        dialogContact.dismiss();
        dialogContact = null;
        AP_contact = null;
        StaticFunctions.hideKeyboard(getActivity());
        EditTextName.setText(md_contacts.get(position).getName());
        EditTextPhoneNumber.setText(md_contacts.get(position).getPhone());
    }//_____________________________________________________________________________________________ SetContactAdapter


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean name = true;
        boolean mobile = true;

        if (Degree == 0) {
            ShowMessage(
                    getContext().getResources().getString(R.string.ChoosePersonDegree),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                    getResources().getColor(R.color.ML_Red));
            return false;
        }

        if (EditTextPhoneNumber.getText().length() < 11) {
            EditTextPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextPhoneNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
            EditTextPhoneNumber.requestFocus();
            mobile = false;
        }

        if (EditTextName.getText().length() == 0) {
            EditTextName.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextName.setError(getResources().getString(R.string.EmptyContactName));
            EditTextName.requestFocus();
            name = false;
        }

        if (mobile&&name)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty

}
