package ir.bppir.pishtazan.views.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;

import java.util.Base64;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.databinding.FrReportGridBinding;

import ir.bppir.pishtazan.utility.Crypto;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ReportGrid;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ReportGrid extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

    private VM_ReportGrid vm_reportGrid;
    private String type;
    private String dateFrom;
    private String dateTo;
//
//    private String link = "https://pasargad.bppir.com/";

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.linearLayoutDates)
    LinearLayout linearLayoutDates;

    @BindView(R.id.linearLayoutTo)
    LinearLayout linearLayoutTo;

    @BindView(R.id.linearLayoutFrom)
    LinearLayout linearLayoutFrom;

    @BindView(R.id.TextViewFrom)
    TextView TextViewFrom;

    @BindView(R.id.TextViewTo)
    TextView TextViewTo;

    @BindView(R.id.relativeLayoutReport)
    RelativeLayout relativeLayoutReport;

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
            type = getArguments().getString(getContext().getResources().getString(R.string.ML_Type)
                    , getContext().getResources().getString(R.string.ML_Wage));

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
        configReport();
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


    //______________________________________________________________________________________________ configReport
    private void configReport() {
        webView.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.GONE);
        linearLayoutDates.setVisibility(View.VISIBLE);
        dateFrom = null;
        if (type.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_Wage))) {
            linearLayoutTo.setVisibility(View.GONE);
            dateTo  = "";
            TextViewFrom.setHint(getContext().getResources().getString(R.string.Date));
        } else {
            linearLayoutTo.setVisibility(View.VISIBLE);
            dateTo = null;
            TextViewFrom.setHint(getContext().getResources().getString(R.string.DateFrom));
        }
        setOnClick();
    }
    //______________________________________________________________________________________________ configReport


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        linearLayoutFrom.setOnClickListener(v -> {

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

        linearLayoutTo.setOnClickListener(v -> {

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


        relativeLayoutReport.setOnClickListener(v -> {

            if (dateFrom == null) {
                TextViewFrom.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back_empty));
                return;
            }

            if (dateTo == null) {
                TextViewTo.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back_empty));
                return;
            }

            showReport();

        });
    }
    //______________________________________________________________________________________________ setOnClick

    //______________________________________________________________________________________________ showReport
    private void showReport() {

        webView.setVisibility(View.VISIBLE);
        linearLayoutDates.setVisibility(View.GONE);

        String url = "";
        if (type.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_Wage))) {
            String month = dateFrom.substring(5,7);
            String Year = dateFrom.substring(0,4);
            url = "https://pasargad.bppir.com/ExcelEntries/ExcelExport2?Month="+month+"&Year="+Year+"&NationalCode="+getNationalCode();
        } else {
            url = "https://pasargad.bppir.com/ExcelEntries/ExcelExport3?StartDate="+dateFrom+"&EndDate="+dateTo+"&NationalCode="+getNationalCode();
        }

        GifViewLoading.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getContext(), description, Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                GifViewLoading.setVisibility(View.GONE);
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });



        webView.loadUrl(url);

    }
    //______________________________________________________________________________________________ showReport


    //______________________________________________________________________________________________ getNationalCode
    private String getNationalCode() {
//        return "4890025421";
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_UserInfo> userInfo = realm.where(DB_UserInfo.class).findAll();
        if (userInfo.size() > 0)
            return userInfo.first().getNationalCode();
        else
            return "";
    }
    //______________________________________________________________________________________________ getNationalCode


}
