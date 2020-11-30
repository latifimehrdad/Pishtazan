package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Notifications;
import ir.bppir.pishtazan.models.MR_EducationCategoryVms;
import ir.bppir.pishtazan.models.MR_Notifications;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Notifications extends VM_Primary {


    private List<MD_Notifications> md_notifications;


    //______________________________________________________________________________________________ VM_Notifications
    public VM_Notifications(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Notifications


    //______________________________________________________________________________________________ getNotifications
    public void getNotifications(boolean isDeleted) {

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GetNotifList(isDeleted));

        getPrimaryCall().enqueue(new Callback<MR_Notifications>() {
            @Override
            public void onResponse(Call<MR_Notifications> call, Response<MR_Notifications> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_notifications = response.body().getNotifications();
                        getPublishSubject().onNext(StaticValues.ML_GetNotifications);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Notifications> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getNotifications


    //______________________________________________________________________________________________ getMd_notifications
    public List<MD_Notifications> getMd_notifications() {
        return md_notifications;
    }
    //______________________________________________________________________________________________ getMd_notifications


}
