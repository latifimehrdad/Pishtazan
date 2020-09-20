package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentExamResultsBinding;
import ir.bppir.pishtazan.models.MD_ExamResult;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ExamResult;
import ir.bppir.pishtazan.views.adapters.AP_ExamResult;

public class ExamResults extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable,
        AP_ExamResult.ClickItemResult {

    private VM_ExamResult vm_examResult;
    private Integer examResultId;
    private Integer tutorialId;
    private String examResultType;
    private NavController navController;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RecyclerViewExamResult)
    RecyclerView RecyclerViewExamResult;

    @BindView(R.id.linearLayoutStatus)
    LinearLayout linearLayoutStatus;

    @BindView(R.id.textViewStatus)
    TextView textViewStatus;

    @BindView(R.id.LinearLayoutNewQuiz)
    LinearLayout LinearLayoutNewQuiz;

    @BindView(R.id.textViewExam)
    TextView textViewExam;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            Post.ExamResultId = 0;
            FragmentExamResultsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam_results, container, false);
            vm_examResult = new VM_ExamResult(getActivity());
            binding.setExamResults(vm_examResult);
            setView(binding.getRoot());
            setOnClick();
            assert getArguments() != null;
            assert getContext() != null;
            examResultId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            tutorialId = getArguments().getInt(getContext().getResources().getString(R.string.ML_TutorialId), 0);
            examResultType = getArguments().getString(getContext().getResources().getString(R.string.ML_Type),
                    getContext().getResources().getString(R.string.ML_LastExam));
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        assert getView() != null;
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                ExamResults.this,
                vm_examResult.getPublishSubject(),
                vm_examResult);
        assert getContext() != null;
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        assert getArguments() != null;
        int personId = getArguments().getInt(getContext().getResources().getString(R.string.ML_personId), 0);
        GifViewLoading.setVisibility(View.VISIBLE);
        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam))) {
            linearLayoutStatus.setVisibility(View.VISIBLE);
            vm_examResult.getExamResult(examResultId);
        } else {
            linearLayoutStatus.setVisibility(View.GONE);
            if (personId == 0)
                vm_examResult.getExamResults(examResultId, null);
            else
                vm_examResult.getExamResults(examResultId, personId);
        }
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        LinearLayoutNewQuiz.setOnClickListener(v -> {
            Post.ExamResultId = -1;
            assert getContext() != null;
            getContext().onBackPressed();
        });
    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetExam)) {
            if (vm_examResult.getMd_examResults() != null || vm_examResult.getMd_examResult() != null)
                setAdapter();
        }
    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {

        assert getContext() != null;
        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam))) {
            if (vm_examResult.getMd_examResult().getExamResultStatus() == 0) {
                textViewStatus.setText(getContext().getResources().getString(R.string.YouFailedTheTest));
                textViewStatus.setTextColor(getContext().getResources().getColor(R.color.ML_RedQuestion));
                textViewExam.setText(getContext().getResources().getString(R.string.ReTryQuiz));
                Post.tutorialId = tutorialId;
            } else {
                textViewStatus.setText(getContext().getResources().getString(R.string.YouPassedTheTest));
                textViewStatus.setTextColor(getContext().getResources().getColor(R.color.ML_OK));
                textViewExam.setText(getContext().getResources().getString(R.string.NewLearn));
                Post.tutorialId = 0;
            }
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

    }
    //______________________________________________________________________________________________ setAdapter


    //______________________________________________________________________________________________ clickItemResult
    @Override
    public void clickItemResult(Integer Position) {
        Bundle bundle = new Bundle();
        assert getContext() != null;
        if (examResultType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_LastExam)))
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                    vm_examResult.getMd_examResult().getId());
        else
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                    vm_examResult.getMd_examResults().get(Position).getId());
        navController.navigate(R.id.action_examResults_to_examResultDetails, bundle);
    }
    //______________________________________________________________________________________________ clickItemResult


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
