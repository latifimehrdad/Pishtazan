package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Education;
import ir.bppir.pishtazan.models.MD_EducationFiles;
import ir.bppir.pishtazan.models.MR_EducationFiles;
import ir.bppir.pishtazan.models.MR_LastEducation;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_TutorialMovie extends VM_Primary {

    private List<MD_EducationFiles> md_educationFiles;
    private MD_Education md_education;

    public VM_TutorialMovie(Activity context) {//___________________________________________________ VM_TutorialMovie
        setContext(context);
    }//_____________________________________________________________________________________________ VM_TutorialMovie


    public void GetNewQuiz() {//____________________________________________________________________ GetNewQuiz

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_LAST_EDUCATION(UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_LastEducation>() {
            @Override
            public void onResponse(Call<MR_LastEducation> call, Response<MR_LastEducation> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_education = response.body().getEducation();
                        notifyChange();
                        getPublishSubject().onNext(StaticValues.ML_GetNewQuiz);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_LastEducation> call, Throwable t) {
                callIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetNewQuiz



    public void GetTutorialMovie(Integer tutorialId) {//____________________________________________ GetTutorialMovie

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EDUCATION_FILES(UserInfoId, tutorialId, StaticValues.FileTypeVideo));

        getPrimaryCall().enqueue(new Callback<MR_EducationFiles>() {
            @Override
            public void onResponse(Call<MR_EducationFiles> call, Response<MR_EducationFiles> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1){
                        md_educationFiles = response.body().getEducationFiles();
                        getPublishSubject().onNext(StaticValues.ML_GetTutorialMovie);
                    }
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_EducationFiles> call, Throwable t) {
                callIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetTutorialMovie


    public List<MD_EducationFiles> getMd_educationFiles() {//_______________________________________ getMd_educationFiles
        return md_educationFiles;
    }//_____________________________________________________________________________________________ getMd_educationFiles


    public MD_Education getMd_education() {//_______________________________________________________ getMd_education
        return md_education;
    }//_____________________________________________________________________________________________ getMd_education

}
