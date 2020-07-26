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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentPolicyTypeBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_PolicyType;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapters.AP_Policy;
import ir.bppir.pishtazan.views.adapters.AP_PolicyType;
import ir.bppir.pishtazan.views.dialogs.searchspinner.MLSpinnerDialog;

public class PolicyType extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable {

    //    private NavController navController;
    private VM_PolicyType vm_policyType;
    private MLSpinnerDialog policyTypeDialog;
    private Integer PolicyTypeId = -1;
    private boolean ClickPolicyType = false;
    private Integer PersonId;
    private boolean editMode = false;
    private String textLoading;
    private String SuggestionDateM;
    private Integer Id;

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
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        PersonId = getArguments().getInt(getContext().getResources().getString(R.string.ML_personId), 0);
        editMode = getArguments().getBoolean(getContext().getResources().getString(R.string.ML_Type), false);
        SuggestionDateM = getArguments().getString(getContext().getResources().getString(R.string.ML_Date), "");
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
            Long amount = getArguments().getLong(getContext().getResources().getString(R.string.ML_Amount), 0);
            String description = getArguments().getString(getContext().getResources().getString(R.string.ML_Description), "0");
            if (amount > 0)
                EditTextAmount.setText(amount.toString());

            if (!description.equalsIgnoreCase("null"))
                EditTextDescription.setText(description);
        }
        init();
        TextViewLoading.setText(textLoading);

    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        setGetMessageFromObservable(
                PolicyType.this,
                vm_policyType.getPublishSubject(),
                vm_policyType);
        if (!editMode)
            vm_policyType.GetAllPolicyTypes();
        ClickPolicyType = false;
//        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

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
            return;
        }

        if (action.equals(StaticValues.ML_EditSuccess)) {
            getContext().onBackPressed();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LayoutPolicyType.setOnClickListener(v -> {
            ClickPolicyType = true;
            if ((vm_policyType.getMd_policyTypes() == null) || (vm_policyType.getMd_policyTypes().size() == 0))
                vm_policyType.GetAllPolicyTypes();
            else
                SetPolicyType();
        });


        RelativeLayoutSend.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                ShowLoading();
                if (editMode)
                    vm_policyType.EditPolicy(
                            Id,
                            PolicyTypeId,
                            PersonId,
                            Long.valueOf(EditTextAmount.getText().toString()),
                            EditTextDescription.getText().toString(),
                            SuggestionDateM
                    );
                else
                    vm_policyType.CreatePolicy(
                            PolicyTypeId.toString(),
                            PersonId.toString(),
                            EditTextAmount.getText().toString(),
                            EditTextDescription.getText().toString());
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


        return amount && type;

    }//_____________________________________________________________________________________________ CheckEmpty


}