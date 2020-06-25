package ir.bppir.pishtazan.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentAddPersonBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_AddPerson;
import ir.bppir.pishtazan.views.adapters.AB_Contact;

public class AddPerson extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private Boolean Partner;
    private VM_AddPerson vm_addPerson;
    private Dialog dialogContact;
    private AB_Contact ab_contact;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<MD_Contact> md_contacts;
    private RecyclerView RecyclerViewContact;


    @BindView(R.id.RelativeLayoutAdd)
    RelativeLayout RelativeLayoutAdd;

    @BindView(R.id.ImageViewAdd)
    ImageView ImageViewAdd;

    @BindView(R.id.TextViewAdd)
    TextView TextViewAdd;

    @BindView(R.id.GifViewAdd)
    GifView GifViewAdd;


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
        Partner = getArguments().getBoolean(getContext().getString(R.string.ML_PartnersType), true);
        if (Partner) {

        } else {

        }

    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_Error) {
            FinishWaiting();
            ShowMessage(
                    vm_addPerson.getResponseMessage(),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                    getResources().getColor(R.color.ML_Red));
            return;
        }

        if (action == StaticValues.ML_GetContact) {
            if (ab_contact == null) {
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


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick

        RelativeLayoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAccessClick()) {
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

    }//_____________________________________________________________________________________________ SetClick


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


    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {//_____________ Start searchContactsTextWatcher
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
    }//_____________________________________________________________________________________________ End searchContactsTextWatcher


    private void SetContactAdapter() {//____________________________________________________________ SetContactAdapter
        ab_contact = new AB_Contact(md_contacts, getContext());
        RecyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewContact.setAdapter(ab_contact);
    }//_____________________________________________________________________________________________ SetContactAdapter


}
