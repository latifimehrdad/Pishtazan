package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentTutorialBinding;
import ir.bppir.pishtazan.databinding.FragmentTutorialMovieBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Tutorial;
import ir.bppir.pishtazan.viewmodels.fragments.VM_TutorialMovie;
import ir.bppir.pishtazan.views.adapters.AP_Movie;
import ir.bppir.pishtazan.views.adapters.AP_Tutorial;

public class TutorialMovie extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Movie.ClickItemTutorialMovie {

    private VM_TutorialMovie vm_tutorialMovie;
    private Integer tutorialId;
    private NavController navController;

    @BindView(R.id.RecyclerViewMovie)
    RecyclerView RecyclerViewMovie;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

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
            tutorialId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
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
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_tutorialMovie.GetTutorialMovie(tutorialId);
    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetTutorialMovie))
            SetReportAdapter();

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_Movie ap_movie = new AP_Movie(vm_tutorialMovie.getMd_tutorialMovies(), TutorialMovie.this);
        RecyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewMovie.setAdapter(ap_movie);
    }//_____________________________________________________________________________________________ SetReportAdapter


    @Override
    public void clickItemTutorialMovie(Integer Position, View view) {//_____________________________ clickItemTutorialMovie

        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_tutorialMovie.getMd_tutorialMovies().get(Position).getId());
        bundle.putInt(getContext().getResources().getString(R.string.ML_questionTime), vm_tutorialMovie.getMd_tutorialMovies().get(Position).getQuestionTime());
        navController.navigate(R.id.action_tutorialMovie_to_quiz, bundle);
    }//_____________________________________________________________________________________________ clickItemTutorialMovie

}
