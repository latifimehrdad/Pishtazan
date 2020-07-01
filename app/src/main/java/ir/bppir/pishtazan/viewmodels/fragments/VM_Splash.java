package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Splash extends VM_Primary {

    private Context context;
    boolean isLogin = false;

    public VM_Splash(Context context) {//___________________________________________________________ VM_Home
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Home


    public void CheckLogin() {//____________________________________________________________________ CheckLogin

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_UserInfo> userInfos = realm.where(DB_UserInfo.class).findAll();
        if (userInfos.size() > 0)
            isLogin = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isLogin)
                    getPublishSubject().onNext(StaticValues.ML_GotoSignUp);
                else
                    getPublishSubject().onNext(StaticValues.ML_GotoHome);
            }
        }, 3000);

    }//_____________________________________________________________________________________________ CheckLogin

}
