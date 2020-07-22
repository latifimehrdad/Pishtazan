package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentReportBinding;
import ir.bppir.pishtazan.databinding.FragmentSplashBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Report;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Splash;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapters.AP_Report;

public class Report extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_Report vm_report;
    private Byte reportType;


    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RecyclerViewReport)
    RecyclerView RecyclerViewReport;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;

    @BindView(R.id.ImageViewTitle)
    ImageView ImageViewTitle;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentReportBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_report, container, false);
            vm_report = new VM_Report(getActivity());
            binding.setReport(vm_report);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Report.this,
                vm_report.getPublishSubject(),
                vm_report);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        Integer temp = getArguments().getInt(getContext().getString(R.string.ML_Type), StaticValues.StatisticalReport);
        reportType = temp.byteValue();
        if (reportType.equals(StaticValues.StatisticalReport)) {
            TextViewTitle.setText(getContext().getString(R.string.StatisticalReport));
            ImageViewTitle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_goal));
        } else {
            TextViewTitle.setText(getContext().getString(R.string.AnalyticalReport));
            ImageViewTitle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_files_and_folders));
        }

        GifViewLoading.setVisibility(View.VISIBLE);
        vm_report.GetReport(reportType);

    }//_____________________________________________________________________________________________ init



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetReport))
            SetReportAdapter();

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_Report ap_report = new AP_Report(vm_report.getMd_reports(), getContext());
        RecyclerViewReport.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewReport.setAdapter(ap_report);
    }//_____________________________________________________________________________________________ SetReportAdapter

}
