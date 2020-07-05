package ir.bppir.pishtazan.views.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.background.ReceiverLunchAppInBackground;
import ir.bppir.pishtazan.databinding.FragmentHomeBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Home;

public class Home extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Home vm_home;

    @BindView(R.id.LinearLayoutColleague)
    LinearLayout LinearLayoutColleagues;

    @BindView(R.id.LinearLayoutCustomer)
    LinearLayout LinearLayoutCustomer;

    @BindView(R.id.LinearLayoutCustomerReport)
    LinearLayout LinearLayoutCustomerReport;

    @BindView(R.id.LinearLayoutColleagueReport)
    LinearLayout LinearLayoutColleagueReport;



    public Home() {//_______________________________________________________________________________ Home

    }//_____________________________________________________________________________________________ Home


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_home = new VM_Home(getContext());
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container,false);
            binding.setHome(vm_home);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            StartService();
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
        setGetMessageFromObservable(
                Home.this,
                vm_home.getPublishSubject(),
                vm_home);
        LinearLayoutColleagues.setVisibility(View.GONE);
        LinearLayoutCustomer.setVisibility(View.GONE);
        LinearLayoutCustomerReport.setVisibility(View.GONE);
        LinearLayoutColleagueReport.setVisibility(View.GONE);
        SetAnimation();

    }//_____________________________________________________________________________________________ init



    private void SetAnimation() {//_________________________________________________________________ SetAnimation

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation inTop = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
                Animation inBottom = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
                LinearLayoutColleagues.setAnimation(inTop);
                LinearLayoutCustomer.setAnimation(inBottom);
                LinearLayoutColleagues.setVisibility(View.VISIBLE);
                LinearLayoutCustomer.setVisibility(View.VISIBLE);
            }
        },400);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation inRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
                Animation inLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
                LinearLayoutCustomerReport.setAnimation(inLeft);
                LinearLayoutColleagueReport.setAnimation(inRight);
                LinearLayoutCustomerReport.setVisibility(View.VISIBLE);
                LinearLayoutColleagueReport.setVisibility(View.VISIBLE);
            }
        },1100);


    }//_____________________________________________________________________________________________ SetAnimation


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________  SetClick

        LinearLayoutColleagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Colleague);
                navController.navigate(R.id.action_home_to_panel, bundle);
            }
        });

        LinearLayoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
                navController.navigate(R.id.action_home_to_panel, bundle);
            }
        });

    }//_____________________________________________________________________________________________ SetClick



    private void StartService() {//_________________________________________________________________ StartService
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getContext().sendBroadcast(new Intent(getContext(), ReceiverLunchAppInBackground.class).setAction("ir.bppir.Lunch"));
                } else {
                    Intent i = new Intent("ir.bppir.Lunch");
                    getContext().sendBroadcast(i);
                }


            }
        }, 1000);
    }//_____________________________________________________________________________________________ StartService

}
