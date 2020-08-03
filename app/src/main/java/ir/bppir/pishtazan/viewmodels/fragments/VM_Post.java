package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Post;
import ir.bppir.pishtazan.models.MD_Tutorial;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Post extends VM_Primary {

    private List<MD_Post> md_posts;

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

        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetNewQuiz), 1500);

    }//_____________________________________________________________________________________________ GetNewQuiz



    public List<MD_Post> getMd_posts() {//__________________________________________________________ getMd_posts
        return md_posts;
    }//_____________________________________________________________________________________________ getMd_posts


}
