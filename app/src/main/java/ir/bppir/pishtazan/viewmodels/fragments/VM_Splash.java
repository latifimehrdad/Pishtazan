package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Splash extends VM_Primary {

    private Context context;

    public VM_Splash(Context context) {//___________________________________________________________ VM_Home
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Home


    public void CheckLogin() {//____________________________________________________________________ CheckLogin

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!StaticValues.isLogin)
                    getPublishSubject().onNext(StaticValues.ML_GotoSplash);
                else
                    getPublishSubject().onNext(StaticValues.ML_GotoHome);
            }
        }, 5000);

    }//_____________________________________________________________________________________________ CheckLogin

}
