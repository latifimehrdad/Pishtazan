package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_QuestionOld;
import ir.bppir.pishtazan.models.MR_EducationFiles;
import ir.bppir.pishtazan.models.MR_Exam;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Quiz extends VM_Primary {


    private MR_Exam mr_exam;

    public VM_Quiz(Activity activity) {//___________________________________________________________ VM_Quiz
        setContext(activity);
    }//_____________________________________________________________________________________________ VM_Quiz


    public void GetQuestion(Integer quizId) {//_____________________________________________________ GetQuestion

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
                        getPublishSubject().onNext(StaticValues.ML_GetQuestions);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Exam> call, Throwable t) {
                CallIsFailure();
            }
        });


    }//_____________________________________________________________________________________________ GetQuestion


    public MR_Exam getMr_exam() {//_________________________________________________________________ getMr_exam
        return mr_exam;
    }//_____________________________________________________________________________________________ getMr_exam


}
