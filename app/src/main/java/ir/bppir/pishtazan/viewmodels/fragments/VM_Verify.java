package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Verify extends VM_Primary {

    private Context context;

    public VM_Verify(Context context) {//___________________________________________________________ VM_Verify
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Verify


    public void SendNumber(String PhoneNumber) {//__________________________________________________ SendNumber
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_ReTrySensSms);
            }
        },2000);
    }//_____________________________________________________________________________________________ SendNumber


    public void VerifyNumber(String PhoneNumber, String VerifyCode) {//_____________________________ SendNumber
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_GotoHome);
            }
        },2000);
    }//_____________________________________________________________________________________________ SendNumber
}
