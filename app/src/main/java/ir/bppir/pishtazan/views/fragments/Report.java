package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentReportBinding;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_SpinnerItem;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Report;
import ir.bppir.pishtazan.views.adapters.AP_Report;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Report extends FragmentPrimary implements FragmentPrimary.MessageFromObservable {


    private VM_Report vm_report;
    private Byte reportType;
    private String dateFrom;
    private String dateTo;
    private Integer rankOfCompanyId;
    private List<MD_Report> md_reports;
    private CompositeDisposable disposable = new CompositeDisposable();


    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.RecyclerViewReport)
    RecyclerView RecyclerViewReport;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;

    @BindView(R.id.ImageViewTitle)
    ImageView ImageViewTitle;

    @BindView(R.id.TextViewFrom)
    TextView TextViewFrom;

    @BindView(R.id.TextViewTo)
    TextView TextViewTo;

    @BindView(R.id.MaterialSpinnerType)
    MaterialSpinner MaterialSpinnerType;

    @BindView(R.id.LinearLayoutFiltering)
    LinearLayout LinearLayoutFiltering;

    @BindView(R.id.ImageViewReport)
    ImageView ImageViewReport;

    @BindView(R.id.GifViewReport)
    GifView GifViewReport;

    @BindView(R.id.RelativeLayoutReport)
    RelativeLayout RelativeLayoutReport;

    @BindView(R.id.LinearLayoutReport)
    LinearLayout LinearLayoutReport;

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.LinearLayoutNewReport)
    LinearLayout LinearLayoutNewReport;

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

        disposable.add(RxTextView.textChangeEvents(editTextSearch)
                .skipInitialValue()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

        rankOfCompanyId = -1;
        GifViewLoading.setVisibility(View.VISIBLE);
        LinearLayoutFiltering.setVisibility(View.GONE);
        LinearLayoutReport.setVisibility(View.GONE);
        setOnClick();
        vm_report.getRecourse();

    }//_____________________________________________________________________________________________ init


    //______________________________________________________________________________________________ searchContactsTextWatcher
    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                searchName(textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
    //______________________________________________________________________________________________ searchContactsTextWatcher


    //______________________________________________________________________________________________ searchName
    private void searchName(String name) {

        md_reports = new ArrayList<>();
        for (MD_Report report : vm_report.getMd_reports()){
            if (name == null || name.equalsIgnoreCase(""))
                md_reports.add(report);
            else {
                if (report.getFullName().contains(name))
                    md_reports.add(report);
            }
        }

        SetReportAdapter();
    }
    //______________________________________________________________________________________________ searchName


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        GifViewLoading.setVisibility(View.GONE);
        GifViewReport.setVisibility(View.GONE);
        ImageViewReport.setVisibility(View.VISIBLE);

        if (action.equals(StaticValues.ML_GetReport)) {
            LinearLayoutFiltering.setVisibility(View.GONE);
            LinearLayoutReport.setVisibility(View.VISIBLE);
            searchName(null);
            SetReportAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_GetRecourse)) {
            setMaterialSpinnerType();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        LinearLayoutNewReport.setOnClickListener(v -> {
            LinearLayoutFiltering.setVisibility(View.VISIBLE);
            LinearLayoutReport.setVisibility(View.GONE);
        });

        TextViewFrom.setOnClickListener(v -> {

            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(persianCalendar.getPersianYear());
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                    dateFrom = sb2.toString();
                    TextViewFrom.setText(dateFrom);
                    TextViewFrom.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();

        });

        TextViewTo.setOnClickListener(v -> {

            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(persianCalendar.getPersianYear());
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                    dateTo = sb2.toString();
                    TextViewTo.setText(dateTo);
                    TextViewTo.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();

        });

        MaterialSpinnerType.setOnItemSelectedListener((view, position, id, item) -> {
            if (rankOfCompanyId == -1) {
                rankOfCompanyId = vm_report.getMd_spinnerItems().get(position - 1).getId();
                MaterialSpinnerType.getItems().remove(0);
                MaterialSpinnerType.setSelectedIndex(MaterialSpinnerType.getItems().size() - 1);
                MaterialSpinnerType.setSelectedIndex(position - 1);
            } else
                rankOfCompanyId = vm_report.getMd_spinnerItems().get(position).getId();

            if (getContext() != null) {
                MaterialSpinnerType.setBackgroundColor(getContext().getResources().getColor(R.color.ML_White));
            }
        });


        RelativeLayoutReport.setOnClickListener(v -> {
            GifViewReport.setVisibility(View.VISIBLE);
            ImageViewReport.setVisibility(View.GONE);
            if (reportType.equals(StaticValues.StatisticalReport))
                vm_report.getReportStatical(dateFrom, dateTo, rankOfCompanyId);
            else
                vm_report.AnaliticalReport(dateFrom, dateTo, rankOfCompanyId);
        });

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ setMaterialSpinnerType
    private void setMaterialSpinnerType() {
        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getContext().getResources().getString(R.string.RankOfCompany));
        for (MD_SpinnerItem item : vm_report.getMd_spinnerItems())
            buildingTypes.add(item.getTitle());
        MaterialSpinnerType.setItems(buildingTypes);
        LinearLayoutFiltering.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerType


    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_Report ap_report = new AP_Report(md_reports, getContext());
        RecyclerViewReport.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewReport.setAdapter(ap_report);
    }//_____________________________________________________________________________________________ SetReportAdapter

}
