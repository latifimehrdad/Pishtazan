package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentExamResultBinding;
import ir.bppir.pishtazan.databinding.FragmentExamResultsBinding;
import ir.bppir.pishtazan.models.MD_ExamResult;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ExamResult;
import ir.bppir.pishtazan.views.adapters.AP_ExamResult;
import ir.bppir.pishtazan.views.adapters.AP_Post;

public class ExamResults extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_ExamResult.ClickItemResult {

    private VM_ExamResult vm_examResult;
    private Integer examResultId;
    private String examResultType;
    private NavController navController;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RecyclerViewExamResult)
    RecyclerView RecyclerViewExamResult;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            Post.ExamResultId = 0;
            FragmentExamResultsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam_results, container, false);
            vm_examResult = new VM_ExamResult(getActivity());
            binding.setExamResults(vm_examResult);
            setView(binding.getRoot());
            examResultId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            examResultType = getArguments().getString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_LastExam));
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                ExamResults.this,
                vm_examResult.getPublishSubject(),
                vm_examResult);
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GifViewLoading.setVisibility(View.VISIBLE);
        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam)))
            vm_examResult.GetExamResult(examResultId);
        else
            vm_examResult.GetExamResults(examResultId);
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetExam)) {
            if (vm_examResult.getMd_examResults() != null || vm_examResult.getMd_examResult() != null)
                SetAdapter();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetAdapter() {//___________________________________________________________________ SetAdapter

        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam))) {
            List<MD_ExamResult> examResults = new ArrayList<>();
            examResults.add(vm_examResult.getMd_examResult());
            AP_ExamResult ap_examResult = new AP_ExamResult(examResults, ExamResults.this);
            RecyclerViewExamResult.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            RecyclerViewExamResult.setAdapter(ap_examResult);
        } else {
            AP_ExamResult ap_examResult = new AP_ExamResult(vm_examResult.getMd_examResults(), ExamResults.this);
            RecyclerViewExamResult.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            RecyclerViewExamResult.setAdapter(ap_examResult);
        }

    }//_____________________________________________________________________________________________ SetAdapter


    //______________________________________________________________________________________________ clickItemResult
    @Override
    public void clickItemResult(Integer Position) {
        Bundle bundle = new Bundle();
        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam)))
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                    vm_examResult.getMd_examResult().getId());
        else
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                    vm_examResult.getMd_examResults().get(Position).getId());
        navController.navigate(R.id.action_examResults_to_examResultDetails, bundle);
    }
    //______________________________________________________________________________________________ clickItemResult


}