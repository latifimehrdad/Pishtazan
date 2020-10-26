package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.models.MR_Payment;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Payment extends VM_Primary {

    private String Amount;
    private String url;

    //______________________________________________________________________________________________ VM_Payment
    public VM_Payment(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Payment


    //______________________________________________________________________________________________ sendAmount
    public void sendAmount() {

        Amount = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(Amount);

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        Long amount = Long.valueOf(Amount.replaceAll(",", ""));

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Payment(UserInfoId, amount));

        getPrimaryCall().enqueue(new Callback<MR_Payment>() {
            @Override
            public void onResponse(Call<MR_Payment> call, Response<MR_Payment> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        url = response.body().getUrlReturn();
                        getPublishSubject().onNext(StaticValues.ML_Payment);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Payment> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ sendAmount


    //______________________________________________________________________________________________ getAmount
    public String getAmount() {
        return Amount;
    }
    //______________________________________________________________________________________________ getAmount



    //______________________________________________________________________________________________ setAmount
    public void setAmount(String amount) {
        Amount = amount;
    }
    //______________________________________________________________________________________________ setAmount


    //______________________________________________________________________________________________ getUrl
    public String getUrl() {
        return url;
    }
    //______________________________________________________________________________________________ getUrl
}
