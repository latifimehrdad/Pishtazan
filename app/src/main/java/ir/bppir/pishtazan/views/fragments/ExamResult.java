package ir.bppir.pishtazan.views.fragments;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentExamResultBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ExamResult;

public class ExamResult extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable {

    private VM_ExamResult vm_examResult;
    private Integer examResultId;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.LinearLayoutExamResult)
    LinearLayout LinearLayoutExamResult;

    @BindView(R.id.TextViewDate)
    TextView TextViewDate;

    @BindView(R.id.TextViewTotal)
    TextView TextViewTotal;

    @BindView(R.id.TextViewCorrect)
    TextView TextViewCorrect;

    @BindView(R.id.TextViewWrongAnswer)
    TextView TextViewWrongAnswer;

    @BindView(R.id.TextViewNotAnswer)
    TextView TextViewNotAnswer;

    @BindView(R.id.TextViewAverage)
    TextView TextViewAverage;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            Post.ExamResultId = 0;
            FragmentExamResultBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam_result, container, false);
            vm_examResult = new VM_ExamResult(getActivity());
            binding.setExamResult(vm_examResult);
            setView(binding.getRoot());
            assert getArguments() != null;
            assert getContext() != null;
            examResultId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setObservableForGetAction(
                ExamResult.this,
                vm_examResult.getPublishSubject(),
                vm_examResult);
        assert getContext() != null;
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayoutExamResult.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_examResult.getExamResult(examResultId);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @SuppressLint({"SetTextI18n" , "DefaultLocale"})
    @Override
    public void getActionFromObservable(Byte action) {

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetExam)) {
            LinearLayoutExamResult.setVisibility(View.VISIBLE);
            TextViewDate.setText(vm_examResult.getMd_examResult().getExamDateJ());

            int total;

            if (vm_examResult.getMd_examResult().getCorrectAnswerCount() != null
                    && vm_examResult.getMd_examResult().getWrongAnswerCount() != null
                    && vm_examResult.getMd_examResult().getNotAnswered() != null)
                total = vm_examResult.getMd_examResult().getCorrectAnswerCount()
                        + vm_examResult.getMd_examResult().getWrongAnswerCount()
                        + vm_examResult.getMd_examResult().getNotAnswered();
            else
                total = 0;

            TextViewTotal.setText(Integer.toString(total));

            TextViewCorrect.setText(vm_examResult.getMd_examResult().getCorrectAnswerCount().toString());

            TextViewWrongAnswer.setText(vm_examResult.getMd_examResult().getWrongAnswerCount().toString());

            TextViewNotAnswer.setText(vm_examResult.getMd_examResult().getNotAnswered().toString());

            String v = String.format("%.2f", vm_examResult.getMd_examResult().getAverageGrade());

            TextViewAverage.setText(v);
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
