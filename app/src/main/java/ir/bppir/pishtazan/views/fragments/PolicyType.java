package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentPolicyTypeBinding;
import ir.bppir.pishtazan.models.MD_PolicyType;
import ir.bppir.pishtazan.utility.ApplicationUtility;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_PolicyType;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.bppir.pishtazan.views.dialogs.searchspinner.MLSpinnerDialog;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class PolicyType extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable {

    //    private NavController navController;
    private VM_PolicyType vm_policyType;
    private MLSpinnerDialog policyTypeDialog;
    private MLSpinnerDialog SeriesDialog;
    private Integer PolicyTypeId = -1;
    private Integer SeriesId = -1;
    private boolean ClickPolicyType = false;
    private boolean ClickSeries = false;
    private Integer PersonId;
    private boolean editMode = false;
    private String textLoading;
    private String SuggestionDateM;
    private Integer Id;
    private String stringDate;
    private Byte panelType;

    @BindView(R.id.LayoutPolicyType)
    LinearLayout LayoutPolicyType;

    @BindView(R.id.TextPolicy)
    TextView TextPolicy;

    @BindView(R.id.EditTextAmount)
    EditText EditTextAmount;

    @BindView(R.id.EditTextDescription)
    EditText EditTextDescription;

    @BindView(R.id.LinearLayoutDate)
    LinearLayout LinearLayoutDate;

    @BindView(R.id.TextViewDate)
    TextView TextViewDate;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    @BindView(R.id.TextViewLoading)
    TextView TextViewLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.LinearLayoutPolicyTypePrimary)
    LinearLayout LinearLayoutPolicyTypePrimary;

    @BindView(R.id.EditTextName)
    EditText EditTextName;

    @BindView(R.id.EditTextNationalCode)
    EditText EditTextNationalCode;

    @BindView(R.id.EditTextTrackingCode)
    EditText EditTextTrackingCode;

    @BindView(R.id.LayoutSeries)
    LinearLayout LayoutSeries;

    @BindView(R.id.TextSeries)
    TextView TextSeries;

    @BindView(R.id.linearLayoutCustomer)
    LinearLayout linearLayoutCustomer;

    @BindView(R.id.editTextInsurerName)
    EditText editTextInsurerName;

    @BindView(R.id.editTextInsurerMobile)
    EditText editTextInsurerMobile;

    @BindView(R.id.editTextInsurerNational)
    EditText editTextInsurerNational;

    public PolicyType() {//_________________________________________________________________________ PolicyType
    }//_____________________________________________________________________________________________ PolicyType


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {//________________________________________________ onCreateView
        if (getView() == null) {
            vm_policyType = new VM_PolicyType(getContext());
            FragmentPolicyTypeBinding binding = DataBindingUtil
                    .inflate(inflater, R.layout.fragment_policy_type, container, false);
            binding.setPolicy(vm_policyType);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetOnClick();
            SetTextWatcher();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        PersonId = getArguments().getInt(getContext().getResources().getString(R.string.ML_personId), 0);
        editMode = getArguments().getBoolean(getContext().getResources().getString(R.string.ML_Type), false);
        SuggestionDateM = getArguments().getString(getContext().getResources().getString(R.string.ML_Date), "");
        panelType = getArguments().getByte(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);

        if (panelType.equals(StaticValues.Colleague))
            linearLayoutCustomer.setVisibility(View.VISIBLE);
        else
            linearLayoutCustomer.setVisibility(View.GONE);

//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        try {
//            SuggestionDateM = df.parse(themp);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        textLoading = getResources().getString(R.string.SavePolicy);
        if (editMode) {
            textLoading = getResources().getString(R.string.EditPolicy);
            LinearLayoutPolicyTypePrimary.setVisibility(View.GONE);
            Id = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            PolicyTypeId = getArguments().getInt(getContext().getResources().getString(R.string.ML_PolicyTypeId), 0);
            SeriesId = getArguments().getInt(getContext().getResources().getString(R.string.ML_SeriesId), 0);
            Long amount = getArguments().getLong(getContext().getResources().getString(R.string.ML_Amount), 0);
            String insured = getArguments().getString(getContext().getResources().getString(R.string.ML_Insured), "");
            String insuredNationalCode = getArguments().getString(getContext().getResources().getString(R.string.ML_InsuredNationalCode), "");
            String description = getArguments().getString(getContext().getResources().getString(R.string.ML_Description), "0");
            if (amount > 0)
                EditTextAmount.setText(amount.toString());

            if (!description.equalsIgnoreCase("null"))
                EditTextDescription.setText(description);

            EditTextName.setText(insured);
            EditTextNationalCode.setText(insuredNationalCode);
        }
        init();
        TextViewLoading.setText(textLoading);

    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        setObservableForGetAction(
                PolicyType.this,
                vm_policyType.getPublishSubject(),
                vm_policyType);
        if (!editMode)
            vm_policyType.getAllPolicyTypes();
        ClickPolicyType = false;
        ClickSeries = false;
//        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ init


    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissLoading();

        if (action.equals(StaticValues.ML_GetAllPolicyTypes)) {
            SetPolicyType();
            return;
        }

        if (action.equals(StaticValues.ML_Success)) {
            MainActivity.startFromNotify = -1;
            TextPolicy.setText(getResources().getString(R.string.ChoosePolicyType));
            EditTextAmount.getText().clear();
            EditTextDescription.getText().clear();
            EditTextNationalCode.getText().clear();
            EditTextTrackingCode.getText().clear();
            TextViewDate.setText("");
            TextSeries.setText("");
            EditTextName.getText().clear();
            editTextInsurerName.getText().clear();
            editTextInsurerMobile.getText().clear();
            editTextInsurerNational.getText().clear();
            return;
        }

        if (action.equals(StaticValues.ML_EditSuccess)) {
            getContext().onBackPressed();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LinearLayoutDate.setOnClickListener(v -> {
            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(persianCalendar.getPersianYear());
                    sb.append("/");
                    sb.append(String.format("%02d", persianCalendar.getPersianMonth()));
                    sb.append("/");
                    sb.append(String.format("%02d", persianCalendar.getPersianDay()));
                    stringDate = sb.toString();
                    TextViewDate.setText(stringDate);
                    LinearLayoutDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();
        });


        LayoutPolicyType.setOnClickListener(v -> {
            ClickPolicyType = true;
            if ((vm_policyType.getMd_policyTypes() == null) || (vm_policyType.getMd_policyTypes().size() == 0))
                vm_policyType.getAllPolicyTypes();
            else
                SetPolicyType();
        });


        LayoutSeries.setOnClickListener(v -> {
            ClickSeries = true;
            SetSeries();
        });


        RelativeLayoutSend.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                ShowLoading();
                if (editMode)
                    vm_policyType.editPolicy(
                            Id,
                            PolicyTypeId,
                            PersonId,
                            Long.valueOf(EditTextAmount.getText().toString().replaceAll(",", "")),
                            EditTextDescription.getText().toString(),
                            SuggestionDateM,
                            EditTextName.getText().toString(),
                            EditTextNationalCode.getText().toString(),
                            stringDate,
                            SeriesId,
                            EditTextTrackingCode.getText().toString()
                    );
                else {
                    if (panelType.equals(StaticValues.Customer))
                        vm_policyType.createPolicy(
                                PolicyTypeId.toString(),
                                PersonId.toString(),
                                EditTextAmount.getText().toString(),
                                EditTextDescription.getText().toString(),
                                EditTextName.getText().toString(),
                                EditTextNationalCode.getText().toString(),
                                stringDate,
                                SeriesId,
                                EditTextTrackingCode.getText().toString(),
                                true);
                    else
                        vm_policyType.addCustomer(
                                PolicyTypeId.toString(),
                                PersonId.toString(),
                                EditTextAmount.getText().toString(),
                                EditTextDescription.getText().toString(),
                                EditTextName.getText().toString(),
                                EditTextNationalCode.getText().toString(),
                                stringDate,
                                SeriesId,
                                EditTextTrackingCode.getText().toString(),
                                editTextInsurerName.getText().toString(),
                                editTextInsurerMobile.getText().toString(),
                                editTextInsurerNational.getText().toString());

                }
            }

        });

    }//_____________________________________________________________________________________________ SetOnClick


    private void SetPolicyType() {//________________________________________________________________ SetPolicyType

        TextPolicy.setText(getResources().getString(R.string.ChoosePolicyType));
        PolicyTypeId = -1;
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        policyTypeDialog = new MLSpinnerDialog(
                getActivity(),
                vm_policyType.getMd_policyTypes(),
                getResources().getString(R.string.SearchPolicyType),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Close));// With 	Animation
        policyTypeDialog.setCancellable(true); // for cancellable
        policyTypeDialog.setShowKeyboard(false);// for open keyboard by default
        policyTypeDialog.bindOnSpinerListener((item, position) -> {
            TextPolicy.setText(item);
            PolicyTypeId = vm_policyType.getMd_policyTypes().get(position).getId();
            LayoutPolicyType.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        if (ClickPolicyType)
            policyTypeDialog.showSpinerDialog();

    }//_____________________________________________________________________________________________ SetPolicyType


    private void SetSeries() {//________________________________________________________________ SetSeries

        TextSeries.setText(getResources().getString(R.string.ChooseSeries));
        SeriesId = -1;
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        List<MD_PolicyType> items = new ArrayList<>();
        items.add(new MD_PolicyType(0, "ماهانه", "", ""));
        items.add(new MD_PolicyType(1, "سه ماهه", "", ""));
        items.add(new MD_PolicyType(2, "شش ماهه", "", ""));
        items.add(new MD_PolicyType(3, "سالانه", "", ""));

        SeriesDialog = new MLSpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.SearchPolicyType),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Close));// With 	Animation
        SeriesDialog.setCancellable(true); // for cancellable
        SeriesDialog.setShowKeyboard(false);// for open keyboard by default
        SeriesDialog.bindOnSpinerListener((item, position) -> {
            TextSeries.setText(item);
            SeriesId = items.get(position).getId();
            LayoutSeries.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        if (ClickSeries)
            SeriesDialog.showSpinerDialog();

    }//_____________________________________________________________________________________________ SetSeries


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        TextViewLoading.setText(textLoading);
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.dw_back_bottom));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        TextViewLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean amount;
        boolean type;
        boolean national;
        boolean name;
        boolean date;
        boolean trackingCode;
        boolean series;

        boolean insurerName = true;
        boolean insurerMobile = true;
        boolean insurerNational = true;

        if (SeriesId == -1) {
            LayoutSeries.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            series = false;
        } else
            series = true;


        if (panelType.equals(StaticValues.Colleague)) {
            if (editTextInsurerNational.getText().length() < 1) {
                editTextInsurerNational.setBackgroundResource(R.drawable.dw_edit_back_empty);
                editTextInsurerNational.setError(getResources().getString(R.string.EmptyNationalCode));
                editTextInsurerNational.requestFocus();
                insurerNational = false;
            } else
                insurerNational = true;


            if (editTextInsurerMobile.getText().length() < 1) {
                editTextInsurerMobile.setBackgroundResource(R.drawable.dw_edit_back_empty);
                editTextInsurerMobile.setError(getResources().getString(R.string.EmptyMobileNumber));
                editTextInsurerMobile.requestFocus();
                insurerMobile = false;
            } else
                insurerMobile = true;


            if (editTextInsurerName.getText().length() < 1) {
                editTextInsurerName.setBackgroundResource(R.drawable.dw_edit_back_empty);
                editTextInsurerName.setError(getResources().getString(R.string.EmptyMobileNumber));
                editTextInsurerName.requestFocus();
                insurerName = false;
            } else
                insurerName = true;
        }



        if (EditTextTrackingCode.getText().length() < 1) {
            EditTextTrackingCode.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextTrackingCode.setError(getResources().getString(R.string.EmptyTrackingCode));
            EditTextTrackingCode.requestFocus();
            trackingCode = false;
        } else
            trackingCode = true;


        if (stringDate == null) {
            LinearLayoutDate.setBackgroundResource(R.drawable.dw_edit_back_empty);
            date = false;
        } else
            date = true;

        if (EditTextNationalCode.getText().length() < 1) {
            EditTextNationalCode.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextNationalCode.setError(getResources().getString(R.string.EmptyNationalCode));
            EditTextNationalCode.requestFocus();
            national = false;
        } else
            national = true;


        if (EditTextName.getText().length() < 1) {
            EditTextName.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextName.setError(getResources().getString(R.string.EmptyContactName));
            EditTextName.requestFocus();
            name = false;
        } else
            name = true;


        if (EditTextAmount.getText().length() < 1) {
            EditTextAmount.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextAmount.setError(getResources().getString(R.string.EmptyAmount));
            EditTextAmount.requestFocus();
            amount = false;
        } else
            amount = true;


        if (PolicyTypeId == -1) {
            LayoutPolicyType.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            type = false;
        } else
            type = true;



        return amount && type && name && national && date && trackingCode && series && insurerMobile && insurerName && insurerNational;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditTextName.addTextChangedListener(textChangeForChangeBack(EditTextName));
        EditTextNationalCode.addTextChangedListener(textChangeForChangeBack(EditTextNationalCode));
        EditTextTrackingCode.addTextChangedListener(textChangeForChangeBack(EditTextTrackingCode));

        editTextInsurerNational.addTextChangedListener(textChangeForChangeBack(editTextInsurerNational));
        editTextInsurerMobile.addTextChangedListener(textChangeForChangeBack(editTextInsurerMobile));
        editTextInsurerName.addTextChangedListener(textChangeForChangeBack(editTextInsurerName));

        ApplicationUtility utility = PishtazanApplication.getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility();

        EditTextAmount.addTextChangedListener(utility.SetTextWatcherSplitting(EditTextAmount));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
