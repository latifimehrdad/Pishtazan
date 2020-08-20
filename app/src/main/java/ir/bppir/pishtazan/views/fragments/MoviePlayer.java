package ir.bppir.pishtazan.views.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitApis;
import ir.bppir.pishtazan.databinding.FragmentMoviePlayerBinding;
import ir.bppir.pishtazan.databinding.FragmentQuizBinding;
import ir.bppir.pishtazan.viewmodels.fragments.VM_MoviePlayer;

public class MoviePlayer extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_MoviePlayer vm_moviePlayer;
    private String movieUrl;

    @BindView(R.id.VideoViewMovie)
    VideoView VideoViewMovie;

    @BindView(R.id.universalVideoViewMovie)
    UniversalVideoView universalVideoViewMovie;

    @BindView(R.id.universalMediaController)
    UniversalMediaController universalMediaController;

    @BindView(R.id.video_layout)
    FrameLayout video_layout;


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
            movieUrl = RetrofitApis.Host + movieUrl;
            //getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        universalVideoViewMovie.setMediaController(universalMediaController);
        universalVideoViewMovie.setVideoURI(uri);
        universalVideoViewMovie.start();


        universalVideoViewMovie.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            private boolean isFullscreen;

            @Override
            public void onScaleChange(boolean isFullscreen) {
                this.isFullscreen = isFullscreen;
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = video_layout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    video_layout.setLayoutParams(layoutParams);
                    //GONE the unconcerned views to leave room for video and controller
                    //mBottomLayout.setVisibility(View.GONE);
                } else {
                    ViewGroup.LayoutParams layoutParams = video_layout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    layoutParams.height = this.cachedHeight;
                    video_layout.setLayoutParams(layoutParams);
                    //mBottomLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
                Log.i("meri", "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
                Log.i("meri", "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
                Log.i("meri", "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading
                Log.i("meri", "onBufferingEnd UniversalVideoView callback");
            }


        });


//        VideoViewMovie.setVideoURI(uri);
//        VideoViewMovie.start();
    }//_____________________________________________________________________________________________ init


}
