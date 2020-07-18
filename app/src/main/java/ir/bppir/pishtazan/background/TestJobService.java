package ir.bppir.pishtazan.background;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * JobService to be scheduled by the JobScheduler.
 * start another service
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {
    private static final String TAG = "SyncService";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("meri", "onStartJob");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("meri", "onStopJob");
        return true;
    }

}