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

    @BindView(R.id.LinearLayoutTutorial)
    LinearLayout LinearLayoutTutorial;

    @BindView(R.id.LinearLayoutReports)
    LinearLayout LinearLayoutReports;


    public Home() {//_______________________________________________________________________________ Home

    }//_____________________________________________________________________________________________ Home


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_home = new VM_Home(getActivity());
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
        SetAnimation();

    }//_____________________________________________________________________________________________ init


    private void SetAnimation() {//_________________________________________________________________ SetAnimation


        Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        LinearLayoutTutorial.setAnimation(bounce);

        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            Animation inTop = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
            Animation inBottom = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
            LinearLayoutColleagues.setAnimation(inTop);
            LinearLayoutCustomer.setAnimation(inBottom);
            LinearLayoutColleagues.setVisibility(View.VISIBLE);
            LinearLayoutCustomer.setVisibility(View.VISIBLE);
        },400);

    }//_____________________________________________________________________________________________ SetAnimation


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________  SetClick

        LinearLayoutTutorial.setOnClickListener(v -> {
            Post.ExamResultId = 0;
            Bundle bundle = new Bundle();
            bundle.putString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_MyReport));
            navController.navigate(R.id.action_home_to_post, bundle);
        });


        LinearLayoutColleagues.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Colleague);
            navController.navigate(R.id.action_home_to_panel, bundle);
        });

        LinearLayoutCustomer.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
            navController.navigate(R.id.action_home_to_panel, bundle);
        });

        LinearLayoutReports.setOnClickListener(v -> navController.navigate(R.id.action_home_to_reports));

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
