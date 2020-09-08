package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_ExamResult;
import ir.bppir.pishtazan.models.MR_ExamResult;
import ir.bppir.pishtazan.models.MR_ExamResults;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_ExamResult extends VM_Primary {

    private MD_ExamResult md_examResult;

    private List<MD_ExamResult> md_examResults;

    //______________________________________________________________________________________________ VM_ExamResult
    public VM_ExamResult(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_ExamResult


    //______________________________________________________________________________________________ getExamResult
    public void getExamResult(Integer examResultId) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EXAM_RESULT(examResultId, UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_ExamResult>() {
            @Override
            public void onResponse(Call<MR_ExamResult> call, Response<MR_ExamResult> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_examResult = response.body().getExamResult();
                        getPublishSubject().onNext(StaticValues.ML_GetExam);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_ExamResult> call, Throwable t) {
                callIsFailure();
            }
        });
    }
    //______________________________________________________________________________________________ getExamResult


    //______________________________________________________________________________________________ getExamResults
    public void getExamResults(Integer examId, Integer personId) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        if (personId != null)
            UserInfoId = personId;

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EXAM_RESULTS(examId, UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_ExamResults>() {
            @Override
            public void onResponse(Call<MR_ExamResults> call, Response<MR_ExamResults> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_examResults = response.body().getExamResults();
                        getPublishSubject().onNext(StaticValues.ML_GetExam);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_ExamResults> call, Throwable t) {
                callIsFailure();
            }
        });
    }
    //______________________________________________________________________________________________ getExamResults


    //______________________________________________________________________________________________ getMd_examResult
    public MD_ExamResult getMd_examResult() {
        return md_examResult;
    }
    //______________________________________________________________________________________________ getMd_examResult


    //______________________________________________________________________________________________ getMd_examResults
    public List<MD_ExamResult> getMd_examResults() {
        return md_examResults;
    }
    //______________________________________________________________________________________________ getMd_examResults
}
