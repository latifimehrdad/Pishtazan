package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MD_Update;
import ir.bppir.pishtazan.models.MR_UserInfoVM;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Splash extends VM_Primary {

    boolean isLogin = false;
    private MD_Update md_update;
    public static boolean logOut;

    //______________________________________________________________________________________________ VM_Splash
    public VM_Splash(Activity context) {
        logOut = false;
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Splash


    //______________________________________________________________________________________________ checkLogin
    public void checkLogin() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_UserInfo> userInfo = realm.where(DB_UserInfo.class).findAll();
        isLogin = userInfo.size() > 0;

        if (!isLogin)
            sendMessageToObservable(StaticValues.ML_GotoSignUp);
        else
            getUserInfoVM();

    }
    //______________________________________________________________________________________________ checkLogin


    //______________________________________________________________________________________________ checkLogin
    public void deleteUserAndLogOut() {

        try {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<DB_UserInfo> userInfo = realm.where(DB_UserInfo.class).findAll();
            realm.beginTransaction();
            userInfo.deleteAllFromRealm();
            realm.commitTransaction();
        } finally {
            VM_Splash.logOut = false;
            sendMessageToObservable(StaticValues.ML_GotoSignUp);
        }
    }
    //______________________________________________________________________________________________ checkLogin


    //______________________________________________________________________________________________ getRecourse
    public void getUpdate() {

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_UPDATE());

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MD_Update>() {
            @Override
            public void onResponse(Call<MD_Update> call, Response<MD_Update> response) {
                if (responseIsOk(response)) {
                    if (response.body() != null) {
                        md_update = response.body();
                        checkUpdate();
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_Update> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse


    //______________________________________________________________________________________________ checkUpdate
    private void checkUpdate() {
        PackageInfo pInfo;
        float versionName = 0;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            versionName = Float.parseFloat(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        float v = md_update.getVresion();

        if (versionName < v)
            getPublishSubject().onNext(StaticValues.ML_Update);
        else
            checkLogin();
    }
    //______________________________________________________________________________________________ checkUpdate


    //______________________________________________________________________________________________ getMd_update
    public MD_Update getMd_update() {
        return md_update;
    }
    //______________________________________________________________________________________________ getMd_update




    //______________________________________________________________________________________________ getUserInfoVM
    public void getUserInfoVM() {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            userIsNotAuthorization();
            return;
        }


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .userSidebarInformation(userInfoId));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_UserInfoVM>() {
            @Override
            public void onResponse(Call<MR_UserInfoVM> call, Response<MR_UserInfoVM> response) {
                if (responseIsOk(response)) {
                    if (response.body() != null) {
                        if (response.body().getStatue() == 1) {
                            if (response.body().getUserInfoVM() != null) {
                                MainActivity.mainActivity.md_userInfoVM = response.body().getUserInfoVM();
                                sendMessageToObservable(StaticValues.ML_GotoHome);
                            } else
                                deleteUserAndLogOut();
                        } else
                            deleteUserAndLogOut();
                    } else
                        deleteUserAndLogOut();
                }
            }

            @Override
            public void onFailure(Call<MR_UserInfoVM> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getUserInfoVM



}
