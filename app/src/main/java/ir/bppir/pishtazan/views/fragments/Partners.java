package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentPartnersBinding;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Partners;

public class Partners extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Partners vm_partners;
    private Boolean Partner;

    @BindView(R.id.LinearLayoutParent)
    LinearLayout LinearLayoutParent;

    public Partners() {//___________________________________________________________________________ Partners

    }//_____________________________________________________________________________________________ Partners


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_partners = new VM_Partners(getContext());
            FragmentPartnersBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_partners, container,false);
            binding.setPartners(vm_partners);
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
        setGetMessageFromObservable(Partners.this, vm_partners.getPublishSubject());
        Partner = getArguments().getBoolean(getContext().getString(R.string.ML_PartnersType), true);
        if (Partner)
            LinearLayoutParent.setBackgroundColor(getContext().getResources().getColor(R.color.ML_Harmony));
        else
            LinearLayoutParent.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ Start SetClick

    }//_____________________________________________________________________________________________ End SetClick

}
