package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Splash extends VM_Primary {

    boolean isLogin = false;

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

}
