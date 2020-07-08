package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
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

    public VM_SignUp(Activity context) {//__________________________________________________________ VM_SignUp
        setContext(context);
    }//_____________________________________________________________________________________________ VM_SignUp


    public void SendNumber(String PhoneNumber) {//__________________________________________________ SendNumber

        PhoneNumber = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);


        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                        .getRetrofitApiInterface()
                        .REQUEST_GENERATE_CODE_CALL(PhoneNumber));

        getPrimaryCall().enqueue(new Callback<MD_RequestGenerateCode>() {
            @Override
            public void onResponse(Call<MD_RequestGenerateCode> call, Response<MD_RequestGenerateCode> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_GotoVerify);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestGenerateCode> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ SendNumber

}
