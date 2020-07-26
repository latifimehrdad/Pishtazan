package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentMoviePlayerBinding;
import ir.bppir.pishtazan.databinding.FragmentQuizBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_MoviePlayer;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Quiz;

public class MoviePlayer extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_MoviePlayer vm_moviePlayer;
    private String movieUrl;

    @BindView(R.id.VideoViewMovie)
    VideoView VideoViewMovie;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentMoviePlayerBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_movie_player, container, false);
            vm_moviePlayer = new VM_MoviePlayer(getActivity());
            binding.setMovie(vm_moviePlayer);
            setView(binding.getRoot());
            movieUrl = getArguments().getString(getContext().getResources().getString(R.string.ML_MovieUrl), "");
            getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                MoviePlayer.this,
                vm_moviePlayer.getPublishSubject(),
                vm_moviePlayer);
        Handler handler = new Handler();
        handler.postDelayed(() -> {init();}, 1000);
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void init() {//_________________________________________________________________________ init

        Uri uri=Uri.parse(movieUrl);
        VideoViewMovie.setVideoURI(uri);
        VideoViewMovie.start();
    }//_____________________________________________________________________________________________ init


}
