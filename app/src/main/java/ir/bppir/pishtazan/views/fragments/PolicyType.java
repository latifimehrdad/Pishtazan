package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentPolicyTypeBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_PolicyType;
import ir.bppir.pishtazan.views.adapters.AP_Policy;
import ir.bppir.pishtazan.views.adapters.AP_PolicyType;

public class PolicyType extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable{

//    private NavController navController;
    private VM_PolicyType vm_policyType;

    @BindView(R.id.RecyclerViewPolicyType)
    RecyclerView RecyclerViewPolicyType;


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
                    .inflate(inflater, R.layout.fragment_policy_type,container, false);
            binding.setPolicy(vm_policyType);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ init
        setGetMessageFromObservable(
                PolicyType.this,
                vm_policyType.getPublishSubject(),
                vm_policyType);
        vm_policyType.GetAllPolicyTypes();
//        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ init



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetAllPolicyTypes)) {
            SetAdapterPolicy();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetAdapterPolicy() {//_____________________________________________________________ SetAdapterPolicy

        AP_PolicyType ap_policy = new AP_PolicyType(vm_policyType.getMd_policyTypes());
        RecyclerViewPolicyType.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPolicyType.setAdapter(ap_policy);

    }//_____________________________________________________________________________________________ SetAdapterPolicy



}
