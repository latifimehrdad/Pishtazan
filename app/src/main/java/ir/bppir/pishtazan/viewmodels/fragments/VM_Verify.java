package ir.bppir.pishtazan.viewmodels.fragments;

        import android.content.Context;

        import io.realm.Realm;
        import io.realm.RealmResults;
        import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
        import ir.bppir.pishtazan.database.DB_UserInfo;
        import ir.bppir.pishtazan.models.MD_RequestGenerateCode;
        import ir.bppir.pishtazan.models.MD_RequestVerifyCode;
        import ir.bppir.pishtazan.utility.StaticValues;
        import ir.bppir.pishtazan.viewmodels.VM_Primary;
        import ir.bppir.pishtazan.views.application.PishtazanApplication;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class VM_Verify extends VM_Primary {

    private Context context;

    public VM_Verify(Context context) {//___________________________________________________________ VM_Verify
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Verify


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
                } else {
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


    public void VerifyNumber(String PhoneNumber, String VerifyCode) {//_____________________________ SendNumber
        PhoneNumber = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);

        VerifyCode = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(VerifyCode);


        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(context)
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .REQUEST_VERIFY_CODE_CALL(PhoneNumber, VerifyCode));

        getPrimaryCall().enqueue(new Callback<MD_RequestVerifyCode>() {
            @Override
            public void onResponse(Call<MD_RequestVerifyCode> call, Response<MD_RequestVerifyCode> response) {
                if (!ResponseIsOk(response)) {
                    setResponseMessage(ResponseErrorMessage(response));
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                } else {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else
                        SaveLogin(response.body());
                }
            }

            @Override
            public void onFailure(Call<MD_RequestVerifyCode> call, Throwable t) {
                if (getPrimaryCall().isCanceled())
                    getPublishSubject().onNext(StaticValues.ML_RequestCancel);
                else
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            }
        });

    }//_____________________________________________________________________________________________ SendNumber



    private void SaveLogin(MD_RequestVerifyCode md) {//_____________________________________________ SaveLogin
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<DB_UserInfo> delete = realm.where(DB_UserInfo.class).findAll();
            realm.beginTransaction();
            delete.deleteAllFromRealm();
            realm.copyToRealm(md.getUserInfo());
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }
        getPublishSubject().onNext(StaticValues.ML_GotoHome);
    }//_____________________________________________________________________________________________ SaveLogin


}
