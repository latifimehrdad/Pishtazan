package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Tutorial;
import ir.bppir.pishtazan.models.MD_TutorialMovie;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_TutorialMovie extends VM_Primary {

    private List<MD_TutorialMovie> md_tutorialMovies;

    public VM_TutorialMovie(Activity context) {//___________________________________________________ VM_TutorialMovie
        setContext(context);
    }//_____________________________________________________________________________________________ VM_TutorialMovie


    public void GetTutorialMovie(Integer tutorialId) {//____________________________________________ GetTutorialMovie

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        md_tutorialMovies = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            md_tutorialMovies.add(new MD_TutorialMovie(i, "ویدیو " + tutorialId + " - " + i, "http://uupload.ir/files/f17r_1e374385f1c390f86bdc865111ca1285.jpg", "http://8upload.ir/uploads/f798030714.mp4", "00:35", 15 * i));
        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetTutorialMovie), 1500);

    }//_____________________________________________________________________________________________ GetTutorialMovie


    public List<MD_TutorialMovie> getMd_tutorialMovies() {//________________________________________ getMd_tutorialMovies
        return md_tutorialMovies;
    }//_____________________________________________________________________________________________ getMd_tutorialMovies



}
