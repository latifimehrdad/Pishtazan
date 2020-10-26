package ir.bppir.pishtazan.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentEditPersonBinding;
import ir.bppir.pishtazan.databinding.FragmentPaymentBinding;
import ir.bppir.pishtazan.utility.ApplicationUtility;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_EditPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Map;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Payment;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Payment extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable {

    private VM_Payment vm_payment;

    @BindView(R.id.editTextAmount)
    EditText editTextAmount;

    @BindView(R.id.relativeLayoutPayment)
    RelativeLayout relativeLayoutPayment;

    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            VM_Map.map_Address = null;
            vm_payment = new VM_Payment(getActivity());
            FragmentPaymentBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_payment, container, false);
            binding.setPayment(vm_payment);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setTextWatcher();
            setClick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setObservableForGetAction(
                Payment.this,
                vm_payment.getPublishSubject(),
                vm_payment);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        imgProgress.setVisibility(View.VISIBLE);
        ProgressGif.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_Payment)) {
            getContext().onBackPressed();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(vm_payment.getUrl()));
            startActivity(i);
        }
    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
        imgProgress.setVisibility(View.VISIBLE);
        ProgressGif.setVisibility(View.GONE);
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {

        ApplicationUtility utility = PishtazanApplication.getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility();
        editTextAmount.addTextChangedListener(utility.SetTextWatcherSplitting(editTextAmount));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ setClick
    private void setClick() {

        relativeLayoutPayment.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                imgProgress.setVisibility(View.GONE);
                ProgressGif.setVisibility(View.VISIBLE);
                vm_payment.sendAmount();
            }
        });
    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ CheckEmpty
    private Boolean CheckEmpty() {

        boolean amount;

        if (editTextAmount.getText().length() < 1) {
            editTextAmount.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editTextAmount.setError(getResources().getString(R.string.EmptyAmount));
            editTextAmount.requestFocus();
            amount = false;
        } else
            amount = true;

        return amount;

    }
    //______________________________________________________________________________________________ CheckEmpty


}
