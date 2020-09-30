package ir.bppir.pishtazan.views.fragments;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;

import java.util.Base64;

import butterknife.BindView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FrReportGridBinding;

import ir.bppir.pishtazan.utility.Crypto;
import ir.bppir.pishtazan.viewmodels.fragments.VM_ReportGrid;

public class ReportGrid extends FragmentPrimary implements FragmentPrimary.actionFromObservable {

    private VM_ReportGrid vm_reportGrid;

    private String link = "https://pasargad.bppir.com/";

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;


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
/*            Crypto crypto = new Crypto(link);
            byte[] encrypt = crypto.encrypt(link.getBytes());
            String temp = encrypt.toString();*/


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

            webView.loadUrl(link);
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
