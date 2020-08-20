package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MD_Update;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Splash extends VM_Primary {

    boolean isLogin = false;
    private MD_Update md_update;

    public VM_Splash(Activity context) {//__________________________________________________________ VM_Home
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Home


    public void CheckLogin() {//____________________________________________________________________ CheckLogin

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_UserInfo> userInfos = realm.where(DB_UserInfo.class).findAll();
        if (userInfos.size() > 0)
            isLogin = true;

        if (!isLogin)
            SendMessageToObservable(StaticValues.ML_GotoSignUp);
        else
            SendMessageToObservable(StaticValues.ML_GotoHome);


    }//_____________________________________________________________________________________________ CheckLogin




    //______________________________________________________________________________________________ getRecourse
    public void getUpdate() {

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_UPDATE());

        getPrimaryCall().enqueue(new Callback<MD_Update>() {
            @Override
            public void onResponse(Call<MD_Update> call, Response<MD_Update> response) {
                if (ResponseIsOk(response)) {
                    if (response.body() != null){
                        md_update = response.body();
                        checkUpdate();
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_Update> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse




    //______________________________________________________________________________________________ checkUpdate
    private void checkUpdate() {
        PackageInfo pInfo;
        int version = 0;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        Integer v = md_update.getVresion();

        if (version < v)
            getPublishSubject().onNext(StaticValues.ML_Update);
        else
            CheckLogin();
    }
    //______________________________________________________________________________________________ checkUpdate


    //______________________________________________________________________________________________ getMd_update
    public MD_Update getMd_update() {
        return md_update;
    }
    //______________________________________________________________________________________________ getMd_update
}
