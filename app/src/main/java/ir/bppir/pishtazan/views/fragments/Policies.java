package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentPolicyListBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_PolicyList;
import ir.bppir.pishtazan.views.adapters.AP_Policy;

public class Policies extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Policy.ClickItemPolicy {


    private VM_PolicyList vm_policyList;
    private NavController navController;
    private Integer PersonId;

    @BindView(R.id.ButtonNew)
    Button ButtonNew;

    @BindView(R.id.RecyclerViewList)
    RecyclerView RecyclerViewList;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentPolicyListBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_policy_list, container, false);
            vm_policyList = new VM_PolicyList(getActivity());
            binding.setPolicy(vm_policyList);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetOnClick();

            //vm_policyList.GetAllPolicies(PersonId, StaticValues.PolicyStatusQuestionnaire);
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Policies.this,
                vm_policyList.getPublishSubject(),
                vm_policyList);

        navController = Navigation.findNavController(getView());
        PersonId = getArguments().getInt(getContext().getResources().getString(R.string.ML_personId), 0);
        Integer temp = getArguments().getInt(getContext().getResources().getString(R.string.ML_Type), StaticValues.PolicyStatusQuestionnaire);
        Byte type = temp.byteValue();
        if (type.equals(StaticValues.PolicyStatusQuestionnaire))
            vm_policyList.GetAllPolicies(PersonId, StaticValues.PolicyStatusQuestionnaire);
        else {
            ButtonNew.setVisibility(View.GONE);
            vm_policyList.GetAllPolicies(PersonId, StaticValues.PolicyStatusInsurance);
        }

    }//_____________________________________________________________________________________________ onStart


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetAllPolicy)) {
            SetAdapterPolicies();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        ButtonNew.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_personId), PersonId);
            bundle.putBoolean(getContext().getResources().getString(R.string.ML_Type), false);
            navController.navigate(R.id.action_policies_to_policyType, bundle);
        });

    }//_____________________________________________________________________________________________ SetOnClick


    private void SetAdapterPolicies() {//___________________________________________________________ SetAdapterPolicies

        AP_Policy ap_policy = new AP_Policy(vm_policyList.getMd_policies(), Policies.this);
        RecyclerViewList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewList.setAdapter(ap_policy);

    }//_____________________________________________________________________________________________ SetAdapterPolicies


    @Override
    public void clickItemPolicy(Integer Position) {//_______________________________________________ clickItemPolicy

        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getResources().getString(R.string.ML_personId), PersonId);
        bundle.putBoolean(getContext().getResources().getString(R.string.ML_Type), true);

        bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                vm_policyList.getMd_policies().get(Position).getId());

        bundle.putInt(getContext().getResources().getString(R.string.ML_PolicyTypeId),
                vm_policyList.getMd_policies().get(Position).getPolicyTypeId());

        bundle.putLong(getContext().getResources().getString(R.string.ML_Amount),
                vm_policyList.getMd_policies().get(Position).getPolicyAmont());

        bundle.putString(getContext().getResources().getString(R.string.ML_Description),
                vm_policyList.getMd_policies().get(Position).getDescription());

        navController.navigate(R.id.action_policies_to_policyType, bundle);

    }//_____________________________________________________________________________________________ clickItemPolicy


}
