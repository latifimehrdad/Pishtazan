package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;
import android.widget.ProgressBar;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.utility.DownloadTask;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Update extends VM_Primary {

    private DownloadZipFileTask downloadZipFileTask;
    private String FileName;
    private ProgressDownload progressDownload;

    public VM_Update(Activity context, ProgressDownload progressDownload) {//_______________________ VM_Update
        setContext(context);
        this.progressDownload = progressDownload;
    }//_____________________________________________________________________________________________ VM_Update


    public interface ProgressDownload {//___________________________________________________________ ProgressDownload

        void onProgress(int progress);
    }//_____________________________________________________________________________________________ ProgressDownload


    //______________________________________________________________________________________________ downloadFile
    public void downloadFile(String url, String filePath, ProgressBar bar) {

        DownloadTask downloadTask = new DownloadTask(getContext(), filePath, bar, getPublishSubject());
        downloadTask.execute(url);
    }
    //______________________________________________________________________________________________ downloadFile



    public void DownloadFile(String url, String fileName) {//_______________________________________ DownloadFile

        FileName = fileName;

        RetrofitComponent retrofitComponent = PishtazanApplication.getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(
                retrofitComponent
                        .getRetrofitApiInterface()
                        .downloadFile(url));

        getPrimaryCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    setResponseMessage("");
                    getPublishSubject().onNext(StaticValues.ML_Success);
                    downloadZipFileTask = new DownloadZipFileTask();
                    downloadZipFileTask.execute(response.body());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }//_____________________________________________________________________________________________ DownloadFile


    private class DownloadZipFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {// DownloadZipFileTask

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getPublishSubject().onNext(StaticValues.ML_FileDownloading);

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            saveToDisk(urls[0], FileName);
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {


            if (progress[0].first == 100)
                getPublishSubject().onNext(StaticValues.ML_FileDownloaded);


            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
                progressDownload.onProgress(currentProgress);

            }

        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }//_____________________________________________________________________________________________ DownloadZipFileTask


    private void saveToDisk(ResponseBody body, String filename) {//_________________________________ saveToDisk

        File file = new File(Environment.getExternalStorageDirectory() + "/pishtazan");
        if (!file.exists()) {
            file.mkdir();
        }

        File destinationFile = new File(Environment.getExternalStorageDirectory() + "/pishtazan/" + filename);

        try (InputStream inputStream = body.byteStream(); OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] data = new byte[4096];
            int count;
            int progress = 0;
            long fileSize = body.contentLength();
            while ((count = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, count);
                progress += count;
                Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                downloadZipFileTask.doProgress(pairs);
            }

            outputStream.flush();

            Pair<Integer, Long> pairs = new Pair<>(100, 100L);
            downloadZipFileTask.doProgress(pairs);
        } catch (IOException e) {
            e.printStackTrace();
            Pair<Integer, Long> pairs = new Pair<>(-1, (long) -1);
            downloadZipFileTask.doProgress(pairs);

        }
    }//_____________________________________________________________________________________________ saveToDisk



    public Uri getTempUri(String filename) {//____________________________________________________________________ Start getTempUri
        // Create an image file name
        File imageFile;
        imageFile = new File(Environment.getExternalStorageDirectory()
                + "/pishtazan/", filename);

//        String imagePath = Environment.getExternalStorageDirectory() + "/MyApp/"
//                + "Camera_" + dt + ".jpg";
        Uri imageUri;// = Uri.fromFile(imageFile);

        imageUri = FileProvider.getUriForFile(
                getContext(),
                "ir.bppir.pishtazan.provider", //(use your app signature + ".provider" )
                imageFile);

        return imageUri;
    }//_____________________________________________________________________________________________ End getTempUri



/*
    public String getFileName() {//_________________________________________________________________ getFileName
        return FileName;
    }//_____________________________________________________________________________________________ getFileName
*/


}