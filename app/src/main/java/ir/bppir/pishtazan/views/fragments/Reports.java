package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentReportsBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Reports;

public class Reports extends FragmentPrimary implements FragmentPrimary.MessageFromObservable {


    private VM_Reports vm_reports;
    private NavController navController;

    @BindView(R.id.LinearLayoutStatisticalReport)
    LinearLayout LinearLayoutStatisticalReport;

    @BindView(R.id.LinearLayoutAnalyticalReport)
    LinearLayout LinearLayoutAnalyticalReport;

    @BindView(R.id.LinearLayoutExamReport)
    LinearLayout LinearLayoutExamReport;

    @BindView(R.id.LinearLayoutReportGrid)
    LinearLayout LinearLayoutReportGrid;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentReportsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_reports, container, false);
            vm_reports = new VM_Reports(getActivity());
            binding.setReports(vm_reports);
            setView(binding.getRoot());
            setOnClick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                Reports.this,
                vm_reports.getPublishSubject(),
                vm_reports);
        navController = Navigation.findNavController(getView());
    }
    //______________________________________________________________________________________________ onStart



    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getMessageFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        LinearLayoutStatisticalReport.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Type), StaticValues.StatisticalReport);
            navController.navigate(R.id.action_reports_to_report, bundle);
        });

        LinearLayoutAnalyticalReport.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Type), StaticValues.AnalyticalReport);
            navController.navigate(R.id.action_reports_to_report, bundle);
        });

        LinearLayoutExamReport.setOnClickListener(v -> navController.navigate(R.id.action_reports_to_examReport));

        LinearLayoutReportGrid.setOnClickListener(v -> navController.navigate(R.id.action_reports_to_reportGrid));
    }
    //______________________________________________________________________________________________ setOnClick





}
