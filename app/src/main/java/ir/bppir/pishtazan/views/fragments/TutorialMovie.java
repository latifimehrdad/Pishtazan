package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentTutorialMovieBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_TutorialMovie;
import ir.bppir.pishtazan.views.adapters.AP_Movie;

public class TutorialMovie extends FragmentPrimary implements
        FragmentPrimary.messageFromObservable,
        AP_Movie.ClickItemTutorialMovie {

    private VM_TutorialMovie vm_tutorialMovie;
    private Integer tutorialId;
    private NavController navController;
    private String examType;

    @BindView(R.id.RecyclerViewMovie)
    RecyclerView RecyclerViewMovie;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.GifViewLoadingQuiz)
    GifView GifViewLoadingQuiz;

    @BindView(R.id.LinearLayoutStart)
    LinearLayout LinearLayoutStart;

    @BindView(R.id.ImageViewLoading)
    ImageView ImageViewLoading;

    @BindView(R.id.LinearLayoutExamResult)
    LinearLayout LinearLayoutExamResult;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentTutorialMovieBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_tutorial_movie, container, false);
            vm_tutorialMovie = new VM_TutorialMovie(getActivity());
            binding.setMovie(vm_tutorialMovie);
            setView(binding.getRoot());
            tutorialId = getArguments().getInt(getContext().getResources().getString(R.string.ML_TutorialId), 0);
            examType = getArguments().getString(getContext().getResources().getString(R.string.ML_Type), "");
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                TutorialMovie.this,
                vm_tutorialMovie.getPublishSubject(),
                vm_tutorialMovie);
        navController = Navigation.findNavController(getView());
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        GifViewLoading.setVisibility(View.VISIBLE);
        SetOnClick();
        if (tutorialId != 0)
            vm_tutorialMovie.GetTutorialMovie(tutorialId);
        else
            vm_tutorialMovie.GetNewQuiz();
    }//_____________________________________________________________________________________________ init


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LinearLayoutStart.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_TutorialId), tutorialId);
            bundle.putString(getContext().getResources().getString(R.string.ML_Type), examType);
            navController.navigate(R.id.action_tutorialMovie_to_quiz, bundle);
        });


        LinearLayoutExamResult.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), tutorialId);
            bundle.putString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_ExamHistory));
            navController.navigate(R.id.action_tutorialMovie_to_examResults, bundle);
        });

    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetTutorialMovie)) {
            SetReportAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_GetNewQuiz)) {
            tutorialId = vm_tutorialMovie.getMd_education().getId();
            vm_tutorialMovie.GetTutorialMovie(tutorialId);
            return;
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> getContext().onBackPressed(), 1000);

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_Movie ap_movie = new AP_Movie(vm_tutorialMovie.getMd_educationFiles(), TutorialMovie.this);
        RecyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewMovie.setAdapter(ap_movie);
    }//_____________________________________________________________________________________________ SetReportAdapter


    @Override
    public void clickItemTutorialMovie(Integer Position, View view) {//_____________________________ clickItemTutorialMovie

        Bundle bundle = new Bundle();
        bundle.putString(getContext().getResources().getString(R.string.ML_MovieUrl),
                vm_tutorialMovie.getMd_educationFiles().get(Position).getFilePath());
        navController.navigate(R.id.action_tutorialMovie_to_moviePlayer, bundle);

    }//_____________________________________________________________________________________________ clickItemTutorialMovie


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
