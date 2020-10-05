package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_SignUp extends VM_Primary {

    private String nationalCode;

    //______________________________________________________________________________________________ VM_SignUp
    public VM_SignUp(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_SignUp


    //______________________________________________________________________________________________ sendNumber
    public void sendNumber() {

        nationalCode = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(nationalCode);


        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .REQUEST_GENERATE_CODE_CALL(nationalCode));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_GenerateCode>() {
            @Override
            public void onResponse(Call<MR_GenerateCode> call, Response<MR_GenerateCode> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_GotoVerify);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_GenerateCode> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ sendNumber



    //______________________________________________________________________________________________ getPhoneNumber
    public String getNationalCode() {
        return nationalCode;
    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
    //______________________________________________________________________________________________ setPhoneNumber
}
