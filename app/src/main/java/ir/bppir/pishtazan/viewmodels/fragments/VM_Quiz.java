package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Answer;
import ir.bppir.pishtazan.models.MD_Education;
import ir.bppir.pishtazan.models.MD_ExamResult;
import ir.bppir.pishtazan.models.MD_Question;
import ir.bppir.pishtazan.models.MD_QuestionOld;
import ir.bppir.pishtazan.models.MR_EducationFiles;
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

    private MD_ExamResult md_examResult;


    public VM_Quiz(Activity activity) {//___________________________________________________________ VM_Quiz
        setContext(activity);
    }//_____________________________________________________________________________________________ VM_Quiz


    public void GetExam(Integer quizId) {//_________________________________________________________ GetExam

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


    }//_____________________________________________________________________________________________ GetExam




    public void GetQuestion(Integer quizId) {//_____________________________________________________ GetQuestion

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
                        getPublishSubject().onNext(StaticValues.ML_GetQuestions);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Question> call, Throwable t) {
                CallIsFailure();
            }
        });


    }//_____________________________________________________________________________________________ GetQuestion




    public void SendAnswer(List<MD_Answer> answers) {//_____________________________________________ SendAnswer

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .SEND_ANSWER(UserInfoId, answers));

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


    }//_____________________________________________________________________________________________ SendAnswer



    public MR_Exam getMr_exam() {//_________________________________________________________________ getMr_exam
        return mr_exam;
    }//_____________________________________________________________________________________________ getMr_exam


    public List<MD_Question> getMd_questions() {//__________________________________________________ getMd_questions
        return md_questions;
    }//_____________________________________________________________________________________________ getMd_questions
}
