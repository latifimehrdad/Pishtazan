package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Answer;
import ir.bppir.pishtazan.models.MD_ExamResult;
import ir.bppir.pishtazan.models.MD_Question;
import ir.bppir.pishtazan.models.MD_SendAnswer;
import ir.bppir.pishtazan.models.MR_Exam;
import ir.bppir.pishtazan.models.MR_ExamResult;
import ir.bppir.pishtazan.models.MR_Question;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Quiz extends VM_Primary {


    private MR_Exam mr_exam;
    private List<MD_Question> md_questions;
    private Integer examResult;

    private MD_ExamResult md_examResult;


    //______________________________________________________________________________________________ VM_Quiz
    public VM_Quiz(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Quiz


    //______________________________________________________________________________________________ getExam
    public void getExam(Integer quizId) {

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EXAM(quizId));

        getPrimaryCall().enqueue(new Callback<MR_Exam>() {
            @Override
            public void onResponse(Call<MR_Exam> call, Response<MR_Exam> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        mr_exam = response.body();
                        getPublishSubject().onNext(StaticValues.ML_GetExam);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Exam> call, Throwable t) {
                CallIsFailure();
            }
        });


    }
    //______________________________________________________________________________________________ getExam


    //______________________________________________________________________________________________ getQuestion
    public void getQuestion(Integer quizId) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_QUESTION(quizId, UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_Question>() {
            @Override
            public void onResponse(Call<MR_Question> call, Response<MR_Question> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_questions = response.body().getQuestions();
                        examResult = response.body().getExamResultId();
                        getPublishSubject().onNext(StaticValues.ML_GetQuestions);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Question> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getQuestion


    //______________________________________________________________________________________________ sendAnswer
    public void sendAnswer(List<MD_Answer> answers, Integer examResult) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        MD_SendAnswer md_sendAnswer = new MD_SendAnswer(UserInfoId, examResult, answers);
        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .SEND_ANSWER(md_sendAnswer));

        getPrimaryCall().enqueue(new Callback<MR_ExamResult>() {
            @Override
            public void onResponse(Call<MR_ExamResult> call, Response<MR_ExamResult> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_examResult = response.body().getExamResult();
                        getPublishSubject().onNext(StaticValues.ML_SendAnswer);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_ExamResult> call, Throwable t) {
                CallIsFailure();
            }
        });


    }
    //______________________________________________________________________________________________ sendAnswer


    //______________________________________________________________________________________________ getMr_exam
    public MR_Exam getMr_exam() {
        return mr_exam;
    }
    //______________________________________________________________________________________________ getMr_exam


    //______________________________________________________________________________________________ getMd_questions
    public List<MD_Question> getMd_questions() {
        return md_questions;
    }
    //______________________________________________________________________________________________ getMd_questions


    //______________________________________________________________________________________________ getExamResult
    public Integer getExamResult() {
        return examResult;
    }
    //______________________________________________________________________________________________ getExamResult

}
