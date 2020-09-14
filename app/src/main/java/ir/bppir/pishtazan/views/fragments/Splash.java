package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentSplashBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Splash;
import ir.bppir.pishtazan.views.activity.MainActivity;

public class Splash extends FragmentPrimary implements FragmentPrimary.messageFromObservable {


    private VM_Splash vm_splash;
    private NavController navController;

    @BindView(R.id.ImageViewSplash)
    ImageView ImageViewSplash;

    @BindView(R.id.linearLayoutLoading)
    LinearLayout linearLayoutLoading;

    @BindView(R.id.linearLayoutRefresh)
    LinearLayout linearLayoutRefresh;


    //______________________________________________________________________________________________ Splash
    public Splash() {
    }
    //______________________________________________________________________________________________ Splash


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentSplashBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_splash, container, false);
            vm_splash = new VM_Splash(getActivity());
            binding.setSplash(vm_splash);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setOnClick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                Splash.this,
                vm_splash.getPublishSubject(),
                vm_splash);
        navController = Navigation.findNavController(getView());
        if (VM_Splash.logOut)
            vm_splash.deleteUserAndLogOut();
        else
            startAnimationSplash();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ startAnimationSplash
    private void startAnimationSplash() {
        ImageViewSplash.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        linearLayoutLoading.setVisibility(View.VISIBLE);
        linearLayoutRefresh.setVisibility(View.GONE);
        vm_splash.getUpdate();
    }
    //______________________________________________________________________________________________ startAnimationSplash


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getMessageFromObservable(Byte action) {

        if (action == StaticValues.ML_GotoSignUp) {
            navController.navigate(R.id.action_splash_to_signUp);
            return;
        }

        if (action == StaticValues.ML_GotoHome) {
            if (MainActivity.startFromNotify == -1)
                navController.navigate(R.id.action_splash_to_home);
            else {
                Bundle bundle = new Bundle();
                bundle.putInt(getContext().getResources().getString(R.string.ML_personId), MainActivity.startFromNotify);
                bundle.putBoolean(getContext().getResources().getString(R.string.ML_Type), false);
                navController.navigate(R.id.action_splash_to_policyType, bundle);
            }
            return;
        }

        if (action.equals(StaticValues.ML_Update)) {
            Bundle bundle = new Bundle();
            bundle.putString(getContext().getResources().getString(R.string.ML_UpdateUrl),
                    vm_splash.getMd_update().getUpdateAddress());
            bundle.putString(getContext().getResources().getString(R.string.ML_UpdateFile), "NewVersion.apk");
            navController.navigate(R.id.action_splash_to_appUpdate, bundle);
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ImageViewSplash.setAnimation(null);
        linearLayoutLoading.setVisibility(View.GONE);
        linearLayoutRefresh.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {
        linearLayoutRefresh.setOnClickListener(v -> startAnimationSplash());
    }
    //______________________________________________________________________________________________ setOnClick

}
