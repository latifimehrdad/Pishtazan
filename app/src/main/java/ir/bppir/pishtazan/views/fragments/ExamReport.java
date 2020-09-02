package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import ir.bppir.pishtazan.databinding.FragmentExamReportBinding;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_SpinnerItem;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ExamReport;
import ir.bppir.pishtazan.views.adapters.AP_LearnReport;

public class ExamReport extends FragmentPrimary implements FragmentPrimary.MessageFromObservable,
        AP_LearnReport.ClickItemDetail {


    private VM_ExamReport vm_examReport;
    private Integer rankOfCompanyId;
    private Integer sortPosition;
    private List<MD_Report> md_reports;
    private CompositeDisposable disposable = new CompositeDisposable();
    private NavController navController;


    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.LinearLayoutFiltering)
    LinearLayout LinearLayoutFiltering;

    @BindView(R.id.LinearLayoutReport)
    LinearLayout LinearLayoutReport;

    @BindView(R.id.GifViewReport)
    GifView GifViewReport;

    @BindView(R.id.ImageViewReport)
    ImageView ImageViewReport;

    @BindView(R.id.MaterialSpinnerType)
    MaterialSpinner MaterialSpinnerType;

    @BindView(R.id.RelativeLayoutReport)
    RelativeLayout RelativeLayoutReport;

    @BindView(R.id.MaterialSpinnerSort)
    MaterialSpinner MaterialSpinnerSort;


    @BindView(R.id.RecyclerViewReport)
    RecyclerView RecyclerViewReport;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentExamReportBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_exam_report, container, false);
            vm_examReport = new VM_ExamReport(getActivity());
            binding.setReport(vm_examReport);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                ExamReport.this,
                vm_examReport.getPublishSubject(),
                vm_examReport);
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        disposable.add(RxTextView.textChangeEvents(editTextSearch)
                .skipInitialValue()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

        rankOfCompanyId = -1;
        sortPosition = -1;
        GifViewLoading.setVisibility(View.VISIBLE);
        LinearLayoutFiltering.setVisibility(View.GONE);
        LinearLayoutReport.setVisibility(View.GONE);
        setOnClick();
        vm_examReport.getRecourse();

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
        for (MD_Report report : vm_examReport.getMd_reports()) {
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

        MaterialSpinnerType.setOnItemSelectedListener((view, position, id, item) -> {
            if (rankOfCompanyId == -1) {
                rankOfCompanyId = vm_examReport.getMd_spinnerItems().get(position - 1).getId();
                MaterialSpinnerType.getItems().remove(0);
                MaterialSpinnerType.setSelectedIndex(MaterialSpinnerType.getItems().size() - 1);
                MaterialSpinnerType.setSelectedIndex(position - 1);
            } else
                rankOfCompanyId = vm_examReport.getMd_spinnerItems().get(position).getId();

            if (getContext() != null) {
                MaterialSpinnerType.setBackgroundColor(getContext().getResources().getColor(R.color.ML_White));
            }

            setMaterialSpinnerSorting();

        });


        MaterialSpinnerSort.setOnItemSelectedListener((view, position, id, item) -> {
            sortPosition = position;

        });


        RelativeLayoutReport.setOnClickListener(v -> {

            if (rankOfCompanyId == -1)
                return;

            GifViewReport.setVisibility(View.VISIBLE);
            ImageViewReport.setVisibility(View.GONE);


            vm_examReport.getLearnReport(rankOfCompanyId, sortPosition);

        });

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ setMaterialSpinnerType
    private void setMaterialSpinnerType() {
        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getContext().getResources().getString(R.string.RankOfCompany));
        for (MD_SpinnerItem item : vm_examReport.getMd_spinnerItems())
            buildingTypes.add(item.getTitle());
        MaterialSpinnerType.setItems(buildingTypes);
        LinearLayoutFiltering.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerType


    //______________________________________________________________________________________________ setMaterialSpinnerSorting
    private void setMaterialSpinnerSorting() {

        List<String> sorting = new ArrayList<>();
        sorting.add(getContext().getResources().getString(R.string.SortingNeither));
        sorting.add(getContext().getResources().getString(R.string.SortingAverageGrade));
        sorting.add(getContext().getResources().getString(R.string.SortingTotalScore));
        sorting.add(getContext().getResources().getString(R.string.SortingTotalActivity));
        sortPosition = 0;
        MaterialSpinnerSort.setItems(sorting);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerSorting


    private void SetReportAdapter() {//_____________________________________________________________ SetReportAdapter
        AP_LearnReport ap_report = new AP_LearnReport(md_reports, getContext(), ExamReport.this);
        RecyclerViewReport.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewReport.setAdapter(ap_report);
    }//_____________________________________________________________________________________________ SetReportAdapter


    //______________________________________________________________________________________________ clickItemPerson
    @Override
    public void clickItemDetail(Integer Position) {
        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getResources().getString(R.string.ML_Id),
                vm_examReport.getMd_reports().get(Position).getId());
        bundle.putString(getContext().getResources().getString(R.string.ML_Type),
                getContext().getResources().getString(R.string.ML_MySubsetReport));
        navController.navigate(R.id.action_examReport_to_post, bundle);
    }
    //______________________________________________________________________________________________ clickItemPerson


}
