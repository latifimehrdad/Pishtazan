package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentExamBinding;
import ir.bppir.pishtazan.databinding.FragmentPanelBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Exam;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Panel;
import ir.bppir.pishtazan.views.adapters.AP_Post;

public class Exam extends FragmentPrimary implements FragmentPrimary.actionFromObservable,
        AP_Post.clickItemTutorial{

    private NavController navController;
    private VM_Exam vm_exam;



    @BindView(R.id.RecyclerViewPost)
    RecyclerView RecyclerViewPost;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;



    public Exam() {//_______________________________________________________________________________ Exam

    }//_____________________________________________________________________________________________ Exam


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_exam = new VM_Exam(getActivity());
            FragmentExamBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam, container, false);
            binding.setExam(vm_exam);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            init();
            /*            GetList();*/
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                Exam.this,
                vm_exam.getPublishSubject(),
                vm_exam);


    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        RecyclerViewPost.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_exam.GetPost(null);

    }//_____________________________________________________________________________________________ init



    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetPost)) {
            setAdapter();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick


    }//_____________________________________________________________________________________________ SetClick



    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        AP_Post ap_post = new AP_Post(vm_exam.getMd_educationCategoryVms(), Exam.this);
        RecyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewPost.setAdapter(ap_post);
        RecyclerViewPost.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ setAdapter




    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest




    //______________________________________________________________________________________________ clickItemTutorial
    @Override
    public void clickTutorial(Integer Position, View view) {

            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_exam.getMd_educationCategoryVms().get(Position).getId());
            bundle.putString(getContext().getResources().getString(R.string.ML_Type), getContext().getResources().getString(R.string.ML_ExamHistory));
            bundle.putString(getContext().getResources().getString(R.string.ML_Description), vm_exam.getMd_educationCategoryVms().get(Position).getTitle());
            navController.navigate(R.id.action_exam_to_tutorial, bundle);

    }
    //______________________________________________________________________________________________ clickItemTutorial



}
