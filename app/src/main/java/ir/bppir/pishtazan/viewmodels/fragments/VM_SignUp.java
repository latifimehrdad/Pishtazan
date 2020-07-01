package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MD_RequestGenerateCode;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_SignUp extends VM_Primary {

    private Context context;

    public VM_SignUp(Context context) {//___________________________________________________________ VM_SignUp
        this.context = context;
    }//_____________________________________________________________________________________________ VM_SignUp


    public void SendNumber(String PhoneNumber) {//__________________________________________________ SendNumber

        PhoneNumber = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);


        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(context)
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                        .getRetrofitApiInterface()
                        .REQUEST_GENERATE_CODE_CALL(PhoneNumber));

        getPrimaryCall().enqueue(new Callback<MD_RequestGenerateCode>() {
            @Override
            public void onResponse(Call<MD_RequestGenerateCode> call, Response<MD_RequestGenerateCode> response) {
                if (!ResponseIsOk(response)) {
                    setResponseMessage(ResponseErrorMessage(response));
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                }
                else {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else
                        getPublishSubject().onNext(StaticValues.ML_GotoVerify);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestGenerateCode> call, Throwable t) {
                if (getPrimaryCall().isCanceled())
                    getPublishSubject().onNext(StaticValues.ML_RequestCancel);
                else
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            }
        });

    }//_____________________________________________________________________________________________ SendNumber

}
