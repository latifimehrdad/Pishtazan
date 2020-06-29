package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class VM_SignUp extends VM_Primary {

    private Context context;

    public VM_SignUp(Context context) {//___________________________________________________________ VM_SignUp
        this.context = context;
    }//_____________________________________________________________________________________________ VM_SignUp


    public void SendNumber(String PhoneNumber) {//__________________________________________________ SendNumber

        PhoneNumber = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StaticValues.isLogin = true;
                getPublishSubject().onNext(StaticValues.ML_GotoVerify);
            }
        },2000);

    }//_____________________________________________________________________________________________ SendNumber

}
