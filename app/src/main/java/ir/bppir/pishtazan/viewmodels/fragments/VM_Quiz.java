package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Question;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Quiz extends VM_Primary {

    private List<MD_Question> md_questions;

    public VM_Quiz(Activity activity) {//___________________________________________________________ VM_Quiz
        setContext(activity);
    }//_____________________________________________________________________________________________ VM_Quiz


    public void GetQuestion(Integer quizId) {//_____________________________________________________ GetQuestion

        md_questions = new ArrayList<>();

        for (int i = 0 ; i < 10; i++)
            md_questions.add(new MD_Question(i,i,"سوال " + i , "جواب اول " + i ,"جواب دوم " + i , "جواب سوم " + i , "جواب چهارم " + i ));
        Handler handler = new Handler();
        handler.postDelayed(() -> SendMessageToObservable(StaticValues.ML_GetQuestions), 1500);

    }//_____________________________________________________________________________________________ GetQuestion


    public List<MD_Question> getMd_questions() {//__________________________________________________ getMd_questions
        return md_questions;
    }//_____________________________________________________________________________________________ getMd_questions

}
