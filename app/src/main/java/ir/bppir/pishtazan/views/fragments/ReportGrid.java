package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FrReportGridBinding;

import ir.bppir.pishtazan.viewmodels.fragments.VM_ReportGrid;

public class ReportGrid extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

    private VM_ReportGrid vm_reportGrid;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FrReportGridBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_report_grid, container, false);
            vm_reportGrid = new VM_ReportGrid(getActivity());
            binding.setGrid(vm_reportGrid);
            setView(binding.getRoot());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setObservableForGetAction(
                ReportGrid.this,
                vm_reportGrid.getPublishSubject(),
                vm_reportGrid);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ GetMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ GetMessageFromObservable



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
