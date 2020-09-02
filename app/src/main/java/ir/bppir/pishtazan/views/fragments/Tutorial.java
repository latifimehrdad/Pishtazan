package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.os.Handler;
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
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Tutorial;
import ir.bppir.pishtazan.views.adapters.AP_Tutorial;

public class Tutorial extends FragmentPrimary implements
        FragmentPrimary.MessageFromObservable,
        AP_Tutorial.ClickItemTutorial {

    private VM_Tutorial vm_tutorial;
    private NavController navController;
    private Integer postId;
    private String postName;
    private String examType;

    @BindView(R.id.RecyclerViewTutorial)
    RecyclerView RecyclerViewTutorial;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentTutorialBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_tutorial, container, false);
            vm_tutorial = new VM_Tutorial(getActivity());
            binding.setTutorial(vm_tutorial);
            setView(binding.getRoot());
            postId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            postName = getArguments().getString(getContext().getResources().getString(R.string.ML_Description), "");
            examType = getArguments().getString(getContext().getResources().getString(R.string.ML_Type), "");
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Tutorial.this,
                vm_tutorial.getPublishSubject(),
                vm_tutorial);
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_tutorial.GetTutorial(postId);
    }//_____________________________________________________________________________________________ init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetTutorial)) {
            SetReportAdapter();
            return;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().onBackPressed();
            }
        },1000);

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_Tutorial ap_tutorial = new AP_Tutorial(vm_tutorial.getMd_educations(), Tutorial.this, postName);
        RecyclerViewTutorial.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewTutorial.setAdapter(ap_tutorial);
    }//_____________________________________________________________________________________________ SetReportAdapter

    @Override
    public void clickItemTutorial(Integer Position, View view) {//__________________________________ clickItemTutorial
        if (!examType.equalsIgnoreCase(getString(R.string.ML_MySubsetReport))) {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_tutorial.getMd_educations().get(Position).getId());
            bundle.putString(getContext().getResources().getString(R.string.ML_Type), examType);
            navController.navigate(R.id.action_tutorial_to_tutorialMovie, bundle);
        } else {
            Integer personId = getArguments().getInt(getContext().getResources().getString(R.string.ML_personId));
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_personId), personId);
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_tutorial.getMd_educations().get(Position).getId());
            bundle.putString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_ExamHistory));
            navController.navigate(R.id.action_tutorial_to_examResults, bundle);
        }
    }//_____________________________________________________________________________________________ clickItemTutorial


}
