package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_ReportDetail;
import ir.bppir.pishtazan.models.MD_Tutorial;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Tutorial extends VM_Primary {


    private List<MD_Tutorial> md_tutorials;

    public VM_Tutorial(Activity context) {//________________________________________________________ VM_Learn
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Learn


    public void GetTutorial() {//___________________________________________________________________ GetTutorial

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        md_tutorials = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            md_tutorials.add(new MD_Tutorial(i, "آموزش " + i, "http://uupload.ir/files/f17r_1e374385f1c390f86bdc865111ca1285.jpg", i));
        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetTutorial), 1500);

    }//_____________________________________________________________________________________________ GetTutorial


    public List<MD_Tutorial> getMd_tutorials() {//__________________________________________________ getMd_tutorials
        return md_tutorials;
    }//_____________________________________________________________________________________________ getMd_tutorials


}
