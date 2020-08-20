package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Education;
import ir.bppir.pishtazan.models.MR_Education;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Tutorial extends VM_Primary {


    private List<MD_Education> md_educations;

    public VM_Tutorial(Activity context) {//________________________________________________________ VM_Learn
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Learn


    public void GetTutorial(Integer postId) {//_____________________________________________________ GetTutorial

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_EDUCATION_LIST(postId, UserInfoId));

        getPrimaryCall().enqueue(new Callback<MR_Education>() {
            @Override
            public void onResponse(Call<MR_Education> call, Response<MR_Education> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        md_educations = response.body().getEducations();
                        getPublishSubject().onNext(StaticValues.ML_GetTutorial);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Education> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetTutorial


    public List<MD_Education> getMd_educations() {//________________________________________________ getMd_educations
        return md_educations;
    }//_____________________________________________________________________________________________ getMd_educations
}
