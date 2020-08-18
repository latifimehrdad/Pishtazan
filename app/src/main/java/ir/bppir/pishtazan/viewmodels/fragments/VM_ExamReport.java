package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_SpinnerItem;
import ir.bppir.pishtazan.models.MR_SpinnerItem;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_ExamReport extends VM_Primary {


    private List<MD_SpinnerItem> md_spinnerItems;


    //______________________________________________________________________________________________ VM_ExamReport
    public VM_ExamReport(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_ExamReport


    //______________________________________________________________________________________________ getRecourse
    public void getRecourse() {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_RECOURCES());

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItem>() {
            @Override
            public void onResponse(Call<MR_SpinnerItem> call, Response<MR_SpinnerItem> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_spinnerItems = response.body().getResources();
                        getPublishSubject().onNext(StaticValues.ML_GetRecourse);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_SpinnerItem> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse


    //______________________________________________________________________________________________ getMd_spinnerItems
    public List<MD_SpinnerItem> getMd_spinnerItems() {
        return md_spinnerItems;
    }
    //______________________________________________________________________________________________ getMd_spinnerItems




}
