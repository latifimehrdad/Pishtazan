package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.models.MRVerifyCode;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Verify extends VM_Primary {

    public VM_Verify(Activity context) {//__________________________________________________________ VM_Verify
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Verify


    public void sendNationalCode(String nationalCode) {//__________________________________________________ sendNationalCode

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

    }//_____________________________________________________________________________________________ sendNationalCode


    public void verifyNationalCode(String nationalCode, String VerifyCode) {//_____________________________ verifyNationalCode
        nationalCode = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(nationalCode);

        VerifyCode = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(VerifyCode);

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .REQUEST_VERIFY_CODE_CALL(nationalCode, getFirebaseToken(), VerifyCode));

        getPrimaryCall().enqueue(new Callback<MRVerifyCode>() {
            @Override
            public void onResponse(Call<MRVerifyCode> call, Response<MRVerifyCode> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        SaveLogin(response.body());
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MRVerifyCode> call, Throwable t) {
                callIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ verifyNationalCode


    private void SaveLogin(MRVerifyCode md) {//_____________________________________________ SaveLogin
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
