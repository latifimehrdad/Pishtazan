package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_SpinnerItem;
import ir.bppir.pishtazan.models.MR_AnalyticalReport;
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
/*
    private List<MD_StatisticalReport> md_statisticalReports;

    private List<MD_AnaliticalReport> md_analiticalReports;*/


    public VM_Report(Activity context) {//__________________________________________________________ VM_Report
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Report


    //______________________________________________________________________________________________ getRecourse
    public void getRecourse() {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_RESOURCES());

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItem>() {
            @Override
            public void onResponse(Call<MR_SpinnerItem> call, Response<MR_SpinnerItem> response) {
                if (responseIsOk(response)) {
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
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse


    //______________________________________________________________________________________________ getReport
    public void getReportStatical(String dateFrom, String dateTo, Integer Difference) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
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
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_reports = response.body().getStatisticalReportVms();
                        getPublishSubject().onNext(StaticValues.ML_GetReport);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_StatisticalReport> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getReport




    //______________________________________________________________________________________________ getReport
    public void AnaliticalReport(String dateFrom, String dateTo, Integer Difference) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        if (Difference == -1)
            Difference = null;

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ANALITICAL_REPORT(UserInfoId, dateFrom, dateTo, Difference));

        getPrimaryCall().enqueue(new Callback<MR_AnalyticalReport>() {
            @Override
            public void onResponse(Call<MR_AnalyticalReport> call, Response<MR_AnalyticalReport> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_reports = response.body().getMd_analyticalReports();
                        getPublishSubject().onNext(StaticValues.ML_GetReport);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_AnalyticalReport> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getReport


    public List<MD_Report> getMd_reports() {//______________________________________________________ getMd_reports
        return md_reports;
    }//_____________________________________________________________________________________________ getMd_reports


    //______________________________________________________________________________________________ getMd_spinnerItems
    public List<MD_SpinnerItem> getMd_spinnerItems() {
        return md_spinnerItems;
    }
    //______________________________________________________________________________________________ getMd_spinnerItems


}
