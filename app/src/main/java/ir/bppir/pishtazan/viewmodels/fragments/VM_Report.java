package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_ReportDetail;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Report extends VM_Primary {

    private List<MD_Report> md_reports;

    public VM_Report(Activity context) {//__________________________________________________________ VM_Report
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Report



    public void GetReport(Byte reportType) {//______________________________________________________ GetReport

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        md_reports = new ArrayList<>();
        for (int j = 0 ; j < 5 ; j++ ) {
            List<MD_ReportDetail> details = new ArrayList<>();
            for (int i = 0; i < 5; i++)
                details.add(new MD_ReportDetail("عنوان " + j + " : " + i, "مقدار " + j + " = " + i));
            md_reports.add(new MD_Report("شخص " + j , details, "http://uupload.ir/files/f17r_1e374385f1c390f86bdc865111ca1285.jpg"));
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetReport), 3000);

    }//_____________________________________________________________________________________________ GetReport


    public List<MD_Report> getMd_reports() {//______________________________________________________ getMd_reports
        return md_reports;
    }//_____________________________________________________________________________________________ getMd_reports


}
