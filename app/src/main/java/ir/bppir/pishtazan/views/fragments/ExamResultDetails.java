package ir.bppir.pishtazan.views.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentExamResultDetailsBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ExamResultDetail;
import ir.bppir.pishtazan.views.adapters.AP_ExamResultDetail;

public class ExamResultDetails extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

    private VM_ExamResultDetail vm_examResultDetail;
    private Integer examResultId;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RecyclerViewExamResult)
    RecyclerView RecyclerViewExamResult;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            Post.ExamResultId = 0;
            FragmentExamResultDetailsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam_result_details, container, false);
            vm_examResultDetail = new VM_ExamResultDetail(getActivity());
            binding.setExamResult(vm_examResultDetail);
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
                ExamResultDetails.this,
                vm_examResultDetail.getPublishSubject(),
                vm_examResultDetail);
        assert getContext() != null;
        getContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GifViewLoading.setVisibility(View.VISIBLE);
        vm_examResultDetail.getExamResultDetails(examResultId);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetExamResultDetail)) {
            if (vm_examResultDetail.getMd_examResultDetails() != null)
                setAdapter();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        AP_ExamResultDetail ap_examResult = new AP_ExamResultDetail(vm_examResultDetail.getMd_examResultDetails());
        RecyclerViewExamResult.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewExamResult.setAdapter(ap_examResult);
    }
    //______________________________________________________________________________________________ setAdapter


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
