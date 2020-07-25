package ir.bppir.pishtazan.views.fragments;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentQuizBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Quiz;
import ir.bppir.pishtazan.views.adapters.AP_Question;


public class Quiz extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_Quiz vm_quiz;
    private Integer movieId;
    private Integer questionTime;
    private Handler timer;
    private Runnable runnable;
    private int questionPosition;

    @BindView(R.id.LinearLayoutStart)
    LinearLayout LinearLayoutStart;

    @BindView(R.id.ImageViewLoading)
    ImageView ImageViewLoading;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RelativeLayoutPlay)
    RelativeLayout RelativeLayoutPlay;

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
            movieId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            questionTime = getArguments().getInt(getContext().getResources().getString(R.string.ML_questionTime), 0);
            init();
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
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        questionPosition = 0;
        SetOnClick();
    }//_____________________________________________________________________________________________ init


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LinearLayoutStart.setOnClickListener(v -> {
            ImageViewLoading.setVisibility(View.GONE);
            GifViewLoading.setVisibility(View.VISIBLE);
            vm_quiz.GetQuestion(movieId);
        });


        LinearLayoutPreviousQuestion.setOnClickListener(v -> {
            questionPosition--;
            if (questionPosition > -1)
                SetAdapterQuestion(false);
            else
                questionPosition++;
        });

        LinearLayoutNextQuestion.setOnClickListener(v -> {
            questionPosition++;
            if (questionPosition < vm_quiz.getMd_questions().size())
                SetAdapterQuestion(true);
            else
                questionPosition--;
        });

    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetQuestions)) {
            LinearLayoutStart.setVisibility(View.GONE);
            RelativeLayoutPlay.setVisibility(View.GONE);
            LinearLayoutQuestion.setVisibility(View.VISIBLE);
            SetAdapterQuestion(true);
            StartTimer(questionTime);
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void

    private void SetAdapterQuestion(boolean next) {//___________________________________________________________ SetAdapterQuestion
        AP_Question ap_question = new AP_Question(vm_quiz.getMd_questions().get(questionPosition));
        RecyclerViewQuestion.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewQuestion.setAdapter(ap_question);

        Animation animation;
        if (next)
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        else
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);

        RecyclerViewQuestion.setAnimation(animation);

    }//_____________________________________________________________________________________________ SetAdapterQuestion


    private void StartTimer(int Elapse) {//_________________________________________________________ Start StartTimer

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
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
//                else
//                    ReTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer


}
