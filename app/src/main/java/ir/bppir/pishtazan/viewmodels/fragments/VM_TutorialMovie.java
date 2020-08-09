package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.List;

import ir.bppir.pishtazan.models.MD_EducationFiles;
import ir.bppir.pishtazan.models.MR_EducationFiles;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_TutorialMovie extends VM_Primary {

    private List<MD_EducationFiles> md_educationFiles;

    public VM_TutorialMovie(Activity context) {//___________________________________________________ VM_TutorialMovie
        setContext(context);
    }//_____________________________________________________________________________________________ VM_TutorialMovie


    public void GetTutorialMovie(Integer tutorialId) {//____________________________________________ GetTutorialMovie

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EDUCATION_FILES(UserInfoId,tutorialId,StaticValues.FileTypeVideo));

        getPrimaryCall().enqueue(new Callback<MR_EducationFiles>() {
            @Override
            public void onResponse(Call<MR_EducationFiles> call, Response<MR_EducationFiles> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_educationFiles = response.body().getEducationFiles();
                        getPublishSubject().onNext(StaticValues.ML_GetTutorialMovie);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_EducationFiles> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetTutorialMovie


    public List<MD_EducationFiles> getMd_educationFiles() {//_______________________________________ getMd_educationFiles
        return md_educationFiles;
    }//_____________________________________________________________________________________________ getMd_educationFiles


}
