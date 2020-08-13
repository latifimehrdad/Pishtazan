package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentQuizBinding;
import ir.bppir.pishtazan.models.MD_Answer;
import ir.bppir.pishtazan.models.MD_Question;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Quiz;
import ir.bppir.pishtazan.views.adapters.AP_Question;


public class Quiz extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Question.ClickItemAnswer {


    private VM_Quiz vm_quiz;
    private Integer examId;
    private Integer questionTime;
    private Handler timer;
    private Runnable runnable;
    private int questionPosition;
    private AP_Question ap_question;
    private MD_Question md_question;
    private String movieUrl;
    private NavController navController;

    @BindView(R.id.LinearLayoutStart)
    LinearLayout LinearLayoutStart;

    @BindView(R.id.ImageViewLoading)
    ImageView ImageViewLoading;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.LinearLayoutQuestion)
    LinearLayout LinearLayoutQuestion;

    @BindView(R.id.RecyclerViewQuestion)
    RecyclerView RecyclerViewQuestion;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.TimeElapsed)
    TextView TimeElapsed;

    @BindView(R.id.LinearLayoutPreviousQuestion)
    LinearLayout LinearLayoutPreviousQuestion;

    @BindView(R.id.LinearLayoutNextQuestion)
    LinearLayout LinearLayoutNextQuestion;

    @BindView(R.id.TextViewName)
    TextView TextViewName;

    @BindView(R.id.TextViewTime)
    TextView TextViewTime;

    @BindView(R.id.LinearLayoutExam)
    LinearLayout LinearLayoutExam;

    @BindView(R.id.GifViewLoadingNew)
    GifView GifViewLoadingNew;

    @BindView(R.id.LinearLayoutSend)
    LinearLayout LinearLayoutSend;

    @BindView(R.id.ImageViewLoadingSend)
    ImageView ImageViewLoadingSend;

    @BindView(R.id.GifViewLoadingSend)
    GifView GifViewLoadingSend;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentQuizBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_quiz, container, false);
            vm_quiz = new VM_Quiz(getActivity());
            binding.setQuiz(vm_quiz);
            setView(binding.getRoot());
            examId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            movieUrl = getArguments().getString(getContext().getResources().getString(R.string.ML_MovieUrl), "");
            SetOnClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Quiz.this,
                vm_quiz.getPublishSubject(),
                vm_quiz);
        navController = Navigation.findNavController(getView());
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        questionPosition = 0;
        LinearLayoutStart.setVisibility(View.GONE);
        LinearLayoutQuestion.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_quiz.GetExam(examId);
    }//_____________________________________________________________________________________________ init


    private void SetOnClick() {//___________________________________________________________________ SetOnClick


        LinearLayoutSend.setOnClickListener(v -> {
            ImageViewLoadingSend.setVisibility(View.GONE);
            GifViewLoadingSend.setVisibility(View.VISIBLE);
            SendResultExam();
        });

        LinearLayoutStart.setOnClickListener(v -> {
            ImageViewLoading.setVisibility(View.GONE);
            GifViewLoadingNew.setVisibility(View.VISIBLE);
            vm_quiz.GetQuestion(examId);
        });


        LinearLayoutPreviousQuestion.setOnClickListener(v -> {
            questionPosition--;
            if (questionPosition > -1)
                AnimationChangeQuestion(false);
            else
                questionPosition++;
        });

        LinearLayoutNextQuestion.setOnClickListener(v -> {
            NextQuestion();
        });


    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetExam)) {
            initExam();
            return;
        }

        if (action.equals(StaticValues.ML_GetQuestions)) {
            LinearLayoutStart.setVisibility(View.GONE);
            LinearLayoutQuestion.setVisibility(View.VISIBLE);
            LinearLayoutExam.setVisibility(View.GONE);
            SetAdapterQuestion();
            StartTimer(questionTime);
            return;
        }

        if (action.equals(StaticValues.ML_SendAnswer)){
            Post.ExamResultId = vm_quiz.getExamResult();
            getActivity().onBackPressed();
            getActivity().onBackPressed();
//            getActivity().onBackPressed();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void initExam() {//_____________________________________________________________________ initExam
        LinearLayoutStart.setVisibility(View.VISIBLE);
        LinearLayoutExam.setVisibility(View.VISIBLE);
        questionTime = vm_quiz.getMr_exam().getExam().getExamTime();
        TextViewName.setText(getContext().getString(R.string.Title) + " : " + vm_quiz.getMr_exam().getExam().getTitle());
        TextViewTime.setText(
                getContext().getString(R.string.MovieTime) + " : " +
                        vm_quiz.getMr_exam().getExam().getExamTime() + " " +
                        getContext().getString(R.string.Minute)
                );
    }//_____________________________________________________________________________________________ initExam



    private void AnimationChangeQuestion(boolean next) {//__________________________________________ AnimationChangeQuestion

        Animation animationExit;
        Animation animationEnter;
        if (next) {
            animationExit = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
            animationEnter = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        } else {
            animationExit = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
            animationEnter = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        }
        RecyclerViewQuestion.setAnimation(animationExit);
        RecyclerViewQuestion.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            SetAdapterQuestion();
            RecyclerViewQuestion.setAnimation(null);
            RecyclerViewQuestion.setAnimation(animationEnter);
            RecyclerViewQuestion.setVisibility(View.VISIBLE);

        }, 400);

/*        Handler handler = new Handler();
        handler.postDelayed(() -> {
            md_question = vm_quiz.getMd_questions().get(questionPosition);
            ap_question.notifyDataSetChanged();
        },600);*/

    }//_____________________________________________________________________________________________ AnimationChangeQuestion


    private void SetAdapterQuestion() {//___________________________________________________________ SetAdapterQuestion
        md_question = vm_quiz.getMd_questions().get(questionPosition);
        ap_question = new AP_Question(md_question, Quiz.this);
        RecyclerViewQuestion.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewQuestion.setAdapter(ap_question);
    }//_____________________________________________________________________________________________ SetAdapterQuestion


    private void StartTimer(int Elapse) {//_________________________________________________________ StartTimer

        Elapse = Elapse * 10;
        Elapse = Elapse * 60;
//        progressBar.setMax(Elapse * 2);
        progressBar.setMax(Elapse);
        progressBar.setProgress(Elapse);
        timer = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mili = progressBar.getProgress() + 10;
                int seconds = (int) (mili / 10) % 60;
                int minutes = (int) ((mili / (10 * 60)) % 60);
                int hours = (int) ((mili / (10 * 60 * 60)) % 60);
                TimeElapsed.setText(String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    SendResultExam();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer



    private void NextQuestion() {//_________________________________________________________________ NextQuestion

        if (vm_quiz.getMd_questions().get(questionPosition).getUserAnswer() != null) {
            questionPosition++;
            if (questionPosition < vm_quiz.getMd_questions().size())
                AnimationChangeQuestion(true);
            else {
                questionPosition--;
            }
        }
    }//_____________________________________________________________________________________________ NextQuestion



    @Override
    public void clickItemAnswer(Integer Answer) {//_________________________________________________ clickItemAnswer

        vm_quiz.getMd_questions().get(questionPosition).setUserAnswer(Answer.byteValue());
        NextQuestion();

    }//_____________________________________________________________________________________________ clickItemAnswer



   private void SendResultExam() {//________________________________________________________________ SendResultExam

       List<MD_Answer> md_answers = new ArrayList<>();
       for (MD_Question question: vm_quiz.getMd_questions()) {
           md_answers.add(new MD_Answer(question.getUserAnswer(), question.getId()));
       }
       LinearLayoutQuestion.setVisibility(View.GONE);
       GifViewLoading.setVisibility(View.VISIBLE);
       vm_quiz.SendAnswer(md_answers, vm_quiz.getExamResult());


   }//______________________________________________________________________________________________ SendResultExam


}
