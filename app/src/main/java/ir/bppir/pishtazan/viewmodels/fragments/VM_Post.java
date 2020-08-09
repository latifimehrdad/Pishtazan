package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Education;
import ir.bppir.pishtazan.models.MD_Post;
import ir.bppir.pishtazan.models.MD_Tutorial;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_LastEducation;
import ir.bppir.pishtazan.models.MR_PersonNumber;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Post extends VM_Primary {

    private List<MD_Post> md_posts;

    private MD_Education md_education;

    public VM_Post(Activity context) {//____________________________________________________________ VM_Post
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Post



    public void GetPost() {//_______________________________________________________________________ GetPost

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        md_posts = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            md_posts.add(new MD_Post(i, "سمت : " + i));
        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetPost), 1500);

    }//_____________________________________________________________________________________________ GetPost



    public void GetNewQuiz() {//____________________________________________________________________ GetNewQuiz

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_education = response.body().getEducation();
                        getPublishSubject().onNext(StaticValues.ML_GetNewQuiz);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_LastEducation> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetNewQuiz



    public List<MD_Post> getMd_posts() {//__________________________________________________________ getMd_posts
        return md_posts;
    }//_____________________________________________________________________________________________ getMd_posts


    public MD_Education getMd_education() {//_______________________________________________________ getMd_education
        return md_education;
    }//_____________________________________________________________________________________________ getMd_education
}
