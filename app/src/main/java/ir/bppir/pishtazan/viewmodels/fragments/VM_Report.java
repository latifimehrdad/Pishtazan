package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_AnaliticalReport;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_ReportDetail;
import ir.bppir.pishtazan.models.MD_SpinnerItem;
import ir.bppir.pishtazan.models.MD_StatisticalReport;
import ir.bppir.pishtazan.models.MR_AnaliticalReport;
import ir.bppir.pishtazan.models.MR_EducationFiles;
import ir.bppir.pishtazan.models.MR_SpinnerItem;
import ir.bppir.pishtazan.models.MR_StatisticalReport;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Report extends VM_Primary {

    private List<MD_Report> md_reports;

    private List<MD_SpinnerItem> md_spinnerItems;

    private List<MD_StatisticalReport> md_statisticalReports;

    private List<MD_AnaliticalReport> md_analiticalReports;


    public VM_Report(Activity context) {//__________________________________________________________ VM_Report
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Report


    //______________________________________________________________________________________________ getRecourse
    public void getRecourse() {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_RECOURCES());

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItem>() {
            @Override
            public void onResponse(Call<MR_SpinnerItem> call, Response<MR_SpinnerItem> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_spinnerItems = response.body().getResources();
                        getPublishSubject().onNext(StaticValues.ML_GetRecourse);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_SpinnerItem> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse


    //______________________________________________________________________________________________ getReport
    public void getReportStatical(String dateFrom, String dateTo, Integer Difference) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        if (Difference == -1)
            Difference = null;

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_STATISTICAL_REPORT(UserInfoId, dateFrom, dateTo, Difference));

        getPrimaryCall().enqueue(new Callback<MR_StatisticalReport>() {
            @Override
            public void onResponse(Call<MR_StatisticalReport> call, Response<MR_StatisticalReport> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_statisticalReports = response.body().getStatisticalReportVms();
                        getPublishSubject().onNext(StaticValues.ML_StatisticalReport);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_StatisticalReport> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getReport




    //______________________________________________________________________________________________ getReport
    public void AnaliticalReport(String dateFrom, String dateTo, Integer Difference) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        if (Difference == -1)
            Difference = null;

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ANALITICAL_REPORT(UserInfoId, dateFrom, dateTo, Difference));

        getPrimaryCall().enqueue(new Callback<MR_AnaliticalReport>() {
            @Override
            public void onResponse(Call<MR_AnaliticalReport> call, Response<MR_AnaliticalReport> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_analiticalReports = response.body().getMd_analiticalReports();
                        getPublishSubject().onNext(StaticValues.ML_AnaliticalReport);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_AnaliticalReport> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getReport



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



    //______________________________________________________________________________________________ getMd_analiticalReports
    public List<MD_AnaliticalReport> getMd_analiticalReports() {
        return md_analiticalReports;
    }
    //______________________________________________________________________________________________ getMd_analiticalReports

    public List<MD_Report> getMd_reports() {//______________________________________________________ getMd_reports
        return md_reports;
    }//_____________________________________________________________________________________________ getMd_reports


    //______________________________________________________________________________________________ getMd_spinnerItems
    public List<MD_SpinnerItem> getMd_spinnerItems() {
        return md_spinnerItems;
    }
    //______________________________________________________________________________________________ getMd_spinnerItems


    //______________________________________________________________________________________________ getMd_statisticalReports
    public List<MD_StatisticalReport> getMd_statisticalReports() {
        return md_statisticalReports;
    }
    //______________________________________________________________________________________________ getMd_statisticalReports
}
