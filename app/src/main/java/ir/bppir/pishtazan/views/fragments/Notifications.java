package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentAddPersonBinding;
import ir.bppir.pishtazan.databinding.NotificationsBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_AddPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Notifications;
import ir.bppir.pishtazan.views.adapters.AP_Notifications;

public class Notifications extends FragmentPrimary implements FragmentPrimary.actionFromObservable,
AP_Notifications.clickItemNotification{

    private VM_Notifications vm_notifications;
    private NavController navController;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.gifViewLoading)
    GifView gifViewLoading;


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
        navController = Navigation.findNavController(getView());
        recyclerView.setVisibility(View.GONE);
        gifViewLoading.setVisibility(View.VISIBLE);
        vm_notifications.getNotifications(false);
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        recyclerView.setVisibility(View.VISIBLE);
        gifViewLoading.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetNotifications)) {
            setAdapter();
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable



    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        AP_Notifications ap_notifications = new AP_Notifications(vm_notifications.getMd_notifications(), Notifications.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ap_notifications);
    }
    //______________________________________________________________________________________________ setAdapter



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {

        recyclerView.setVisibility(View.VISIBLE);
        gifViewLoading.setVisibility(View.GONE);
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest



    //______________________________________________________________________________________________ itemClick
    @Override
    public void itemClick(Integer position) {

        Bundle bundle = new Bundle();
        bundle.putString(getContext().getResources().getString(R.string.ML_Title), vm_notifications.getMd_notifications().get(position).getTitle());
        bundle.putString(getContext().getResources().getString(R.string.ML_Description), vm_notifications.getMd_notifications().get(position).getDescription());
        bundle.putString(getContext().getResources().getString(R.string.ML_ImageUrl), vm_notifications.getMd_notifications().get(position).getImage());
        bundle.putString(getContext().getResources().getString(R.string.ML_Date), vm_notifications.getMd_notifications().get(position).getMDateJ());

        navController.navigate(R.id.action_notifications_to_detail, bundle);
    }
    //______________________________________________________________________________________________ itemClick


}
