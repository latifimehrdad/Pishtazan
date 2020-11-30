package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.NotificationDetailBinding;
import ir.bppir.pishtazan.databinding.NotificationsBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_NotificationDetail;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Notifications;
import ir.bppir.pishtazan.views.adapters.AP_Notifications;

public class DetailNotification extends FragmentPrimary implements FragmentPrimary.actionFromObservable {


    private VM_NotificationDetail vm_notificationDetail;


    //______________________________________________________________________________________________ DetailNotification
    public DetailNotification() {
    }
    //______________________________________________________________________________________________ DetailNotification




    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_notificationDetail = new VM_NotificationDetail(getActivity());
            NotificationDetailBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.notification_detail, container, false);
            binding.setDetail(vm_notificationDetail);
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
                DetailNotification.this,
                vm_notificationDetail.getPublishSubject(),
                vm_notificationDetail);

        String title = getArguments().getString(getContext().getResources().getString(R.string.ML_Title));
        String description = getArguments().getString(getContext().getResources().getString(R.string.ML_Description));
        String url = getArguments().getString(getContext().getResources().getString(R.string.ML_ImageUrl));
        String date = getArguments().getString(getContext().getResources().getString(R.string.ML_Date));
        vm_notificationDetail.setValue(title,description,url,date);

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
