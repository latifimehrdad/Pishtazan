package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
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
import ir.bppir.pishtazan.databinding.FragmentPostBinding;
import ir.bppir.pishtazan.databinding.FragmentTutorialBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Post;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Tutorial;
import ir.bppir.pishtazan.views.adapters.AP_Post;
import ir.bppir.pishtazan.views.adapters.AP_Tutorial;

public class Post extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable,
        AP_Post.ClickItemTutorial{


    private VM_Post vm_post;
    private NavController navController;

    @BindView(R.id.RecyclerViewPost)
    RecyclerView RecyclerViewPost;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.LinearLayoutNewQuiz)
    LinearLayout LinearLayoutNewQuiz;

    @BindView(R.id.ImageViewNew)
    ImageView ImageViewNew;

    @BindView(R.id.GifViewLoadingNew)
    GifView GifViewLoadingNew;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentPostBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_post, container, false);
            vm_post = new VM_Post(getActivity());
            binding.setPost(vm_post);
            setView(binding.getRoot());
            init();
            SetClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Post.this,
                vm_post.getPublishSubject(),
                vm_post);
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_post.GetPost();
    }//_____________________________________________________________________________________________ init




    private void SetClick() {//_____________________________________________________________________ SetClick

        LinearLayoutNewQuiz.setOnClickListener(v -> {

            ImageViewNew.setVisibility(View.GONE);
            GifViewLoadingNew.setVisibility(View.VISIBLE);
            vm_post.GetNewQuiz();
        });
    }//_____________________________________________________________________________________________ SetClick



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        ImageViewNew.setVisibility(View.VISIBLE);
        GifViewLoadingNew.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetPost)) {
            SetAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_GetNewQuiz)) {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), 0);
            bundle.putInt(getContext().getResources().getString(R.string.ML_questionTime), 15);
            bundle.putString(getContext().getResources().getString(R.string.ML_MovieUrl), "http://8upload.ir/uploads/f798030714.mp4");
            navController.navigate(R.id.action_post_to_quiz, bundle);
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetAdapter() {//___________________________________________________________________ SetAdapter
        AP_Post ap_post = new AP_Post(vm_post.getMd_posts(), Post.this);
        RecyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPost.setAdapter(ap_post);
    }//_____________________________________________________________________________________________ SetAdapter

    @Override
    public void clickItemTutorial(Integer Position, View view) {//__________________________________ clickItemTutorial
        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_post.getMd_posts().get(Position).getId());
        navController.navigate(R.id.action_post_to_tutorial, bundle);
    }//_____________________________________________________________________________________________ clickItemTutorial


}
