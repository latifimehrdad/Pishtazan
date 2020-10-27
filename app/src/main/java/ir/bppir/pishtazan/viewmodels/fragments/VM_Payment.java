package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MD_Transaction;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.models.MR_Payment;
import ir.bppir.pishtazan.models.MR_Transaction;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Payment extends VM_Primary {

    private String Amount;
    private String url;
    private List<MD_Transaction> md_transactions;

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



    //______________________________________________________________________________________________ getAllTransactions
    public void getAllTransactions() {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAllTransactions(UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_Transaction>() {
            @Override
            public void onResponse(Call<MR_Transaction> call, Response<MR_Transaction> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        md_transactions = response.body().getTransactions();
                        getPublishSubject().onNext(StaticValues.ML_Transaction);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Transaction> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getAllTransactions




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


    //______________________________________________________________________________________________ getUrl
    public List<MD_Transaction> getMd_transactions() {
        if (md_transactions == null)
            md_transactions = new ArrayList<>();
        return md_transactions;
    }
    //______________________________________________________________________________________________ getUrl


}
