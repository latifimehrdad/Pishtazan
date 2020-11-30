package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentAddPersonBinding;
import ir.bppir.pishtazan.databinding.NotificationsBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_AddPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Notifications;

public class Notifications extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

    private VM_Notifications vm_notifications;


    //______________________________________________________________________________________________ Notifications
    public Notifications() {
    }
    //______________________________________________________________________________________________ Notifications




    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_notifications = new VM_Notifications(getActivity());
            NotificationsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.notifications, container, false);
            binding.setNotification(vm_notifications);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
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
        setObservableForGetAction(
                Notifications.this,
                vm_notifications.getPublishSubject(),
                vm_notifications);
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {


    }
    //______________________________________________________________________________________________ getMessageFromObservable




    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
