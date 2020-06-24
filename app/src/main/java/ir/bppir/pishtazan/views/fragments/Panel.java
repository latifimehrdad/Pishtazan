package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentPanelBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Panel;
import ir.bppir.pishtazan.views.adapters.AB_Person;

public class Panel extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Panel vm_panel;
    private Boolean Partner;
    private AB_Person ab_person;
    private Byte PersonType = 0;

    @BindView(R.id.LinearLayoutParent)
    LinearLayout LinearLayoutParent;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;

    @BindView(R.id.RecyclerViewPanel)
    RecyclerView RecyclerViewPanel;

    @BindView(R.id.LinearLayoutAdd)
    LinearLayout LinearLayoutAdd;


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
            binding.setPartners(vm_panel);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            PersonType = 0;
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
        GetList();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(Panel.this, vm_panel.getPublishSubject());
        Partner = getArguments().getBoolean(getContext().getString(R.string.ML_PartnersType), true);
        if (Partner) {
            LinearLayoutParent.setBackgroundColor(getContext().getResources().getColor(R.color.ML_White));
            TextViewTitle.setText(getContext().getResources().getString(R.string.Partners));
        } else {
            LinearLayoutParent.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            TextViewTitle.setText(getContext().getResources().getString(R.string.Customer));
        }

    }//_____________________________________________________________________________________________ init


    private void GetList() {//______________________________________________________________________ GetList
        if (ab_person == null)
            vm_panel.GetPerson(Partner, PersonType);
    }//_____________________________________________________________________________________________ GetList


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_GetPerson) {
            SetAdapterPerson();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick

        LinearLayoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(getContext().getString(R.string.ML_PartnersType), Partner);
                navController.navigate(R.id.action_panel_to_addPerson, bundle);
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetAdapterPerson() {//_____________________________________________________________ SetAdapterPerson
        ab_person = new AB_Person(vm_panel.getPersonList(), getContext());
        RecyclerViewPanel.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPanel.setAdapter(ab_person);
    }//_____________________________________________________________________________________________ SetAdapterPerson

}
