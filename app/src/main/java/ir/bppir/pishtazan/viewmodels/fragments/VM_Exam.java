package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_EducationCategoryVms;
import ir.bppir.pishtazan.models.MR_EducationCategoryVms;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Exam  extends VM_Primary {


    private List<MD_EducationCategoryVms> md_educationCategoryVms;


    //______________________________________________________________________________________________ VM_Exam
    public VM_Exam(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Exam


    public void GetPost(Integer personId) {//_______________________________________________________ GetPost

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
                .GET_LevelCategory(UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_EducationCategoryVms>() {
            @Override
            public void onResponse(Call<MR_EducationCategoryVms> call, Response<MR_EducationCategoryVms> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_educationCategoryVms = response.body().getEducationCategoryVms();
                        getPublishSubject().onNext(StaticValues.ML_GetPost);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_EducationCategoryVms> call, Throwable t) {
                callIsFailure();
            }
        });


    }//_____________________________________________________________________________________________ GetPost


    public List<MD_EducationCategoryVms> getMd_educationCategoryVms() {//___________________________ getMd_educationCategoryVms
        return md_educationCategoryVms;
    }//_____________________________________________________________________________________________ getMd_educationCategoryVms



}
