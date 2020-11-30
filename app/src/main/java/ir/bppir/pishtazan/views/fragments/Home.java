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

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.background.ReceiverLunchAppInBackground;
import ir.bppir.pishtazan.databinding.FragmentHomeBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Home;
import ir.bppir.pishtazan.views.activity.MainActivity;

public class Home extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

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

    @BindView(R.id.linearLayoutNotification)
    LinearLayout linearLayoutNotification;


    //______________________________________________________________________________________________ Home
    public Home() {
    }
    //______________________________________________________________________________________________ Home


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_home = new VM_Home(getActivity());
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container, false);
            binding.setHome(vm_home);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setClick();
            startService();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        init();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        assert getView() != null;
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                Home.this,
                vm_home.getPublishSubject(),
                vm_home);
        LinearLayoutColleagues.setVisibility(View.GONE);
        LinearLayoutCustomer.setVisibility(View.GONE);
        setAnimation();
        if (MainActivity.mainActivity.payment) {
            MainActivity.mainActivity.payment = false;
            navController.navigate(R.id.gotoPayment);
        }

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setAnimation
    private void setAnimation() {


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
        }, 400);

    }
    //______________________________________________________________________________________________ setAnimation


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setClick
    private void setClick() {


        LinearLayoutTutorial.setOnClickListener(v -> {
            Post.ExamResultId = 0;
            Bundle bundle = new Bundle();
            assert getContext() != null;
            bundle.putString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_MyReport));
            navController.navigate(R.id.action_home_to_post, bundle);
        });


        LinearLayoutColleagues.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            assert getContext() != null;
            bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Colleague);
            navController.navigate(R.id.action_home_to_panel, bundle);
        });

        LinearLayoutCustomer.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            assert getContext() != null;
            bundle.putInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
            navController.navigate(R.id.action_home_to_panel, bundle);
        });

        LinearLayoutReports.setOnClickListener(v -> navController.navigate(R.id.action_home_to_reports));

        linearLayoutNotification.setOnClickListener(v -> navController.navigate(R.id.action_home_to_notification));

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ startService
    private void startService() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            assert getContext() != null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getContext().sendBroadcast(new Intent(getContext(), ReceiverLunchAppInBackground.class).setAction("ir.bppir.Lunch"));
            } else {
                Intent i = new Intent("ir.bppir.Lunch");
                getContext().sendBroadcast(i);
            }


        }, 1000);
    }
    //______________________________________________________________________________________________ startService


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
