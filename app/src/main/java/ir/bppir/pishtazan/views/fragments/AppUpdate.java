package ir.bppir.pishtazan.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import java.io.File;

import butterknife.BindView;
import ir.bppir.pishtazan.BuildConfig;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitApis;
import ir.bppir.pishtazan.databinding.FragmentUpdateBinding;
import ir.bppir.pishtazan.utility.DownloadTask;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Update;

public class AppUpdate extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        VM_Update.ProgressDownload{


    private VM_Update vm_update;
    private String fileName;

    @BindView(R.id.TextViewProgress)
    TextView TextViewProgress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.ImageViewDownload)
    ImageView ImageViewDownload;

    @BindView(R.id.ButtonInstall)
    Button ButtonInstall;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {//________________________________________________ onCreateView
        if (getView() == null) {
            vm_update = new VM_Update(getContext(), AppUpdate.this);
            FragmentUpdateBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_update, container, false);
            binding.setUpdate(vm_update);
            setView(binding.getRoot());
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.PleaseWait));
            progressBar.setProgress(0);
            ButtonInstall.setVisibility(View.GONE);
            SetOnClick();
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                AppUpdate.this,
                vm_update.getPublishSubject(),
                vm_update);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        if (getContext() != null && getArguments() != null) {
            String url = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateUrl), "");
            fileName = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateFile), "");
            url = RetrofitApis.Host + url;


            if (!url.equalsIgnoreCase(""))
                if (!fileName.equalsIgnoreCase(""))
                    vm_update.downloadFile(url,fileName, progressBar);
                    //vm_update.DownloadFile(url, fileName);
        }

    }//_____________________________________________________________________________________________ init

    private void SetOnClick() {//___________________________________________________________________ SetOnClick
        ButtonInstall.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = vm_update.getTempUri(fileName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //dont forget add this line
                if (getContext() != null)
                    getContext().startActivity(intent);
            } else {
                File apkFile;
                apkFile = new File(Environment.getExternalStorageDirectory()
                        + "/pishtazan/", fileName);
                Uri apkUri = Uri.fromFile(apkFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }

        });
    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ getMessageFromObservable

        if (action.equals(StaticValues.ML_Success)) {
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.DownloadingFile));
            return;
        }

        if (action.equals(StaticValues.ML_FileDownloading)) {
            progressBar.setProgress(0);
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
            return;
        }

        if (action.equals(StaticValues.ML_FileDownloaded)) {
            progressBar.setProgress(0);
            ImageViewDownload.setAnimation(null);
            ButtonInstall.setVisibility(View.VISIBLE);
            ImageViewDownload.setVisibility(View.GONE);
            TextViewProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
        }

    }//_____________________________________________________________________________________________ getMessageFromObservable


    @Override
    public void onProgress(int progress) {//________________________________________________________ onProgress
        progressBar.setProgress(progress);
        TextViewProgress.setText(progress + " %");
    }//_____________________________________________________________________________________________ onProgress


}