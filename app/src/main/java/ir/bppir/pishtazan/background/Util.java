package ir.bppir.pishtazan.background;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class Util {

    // schedule the start of the service every 10 - 30 seconds
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void scheduleJob(Context context) {
        ComponentName componentName = new ComponentName(context,TestJobService.class);
        JobInfo info = new JobInfo.Builder(1,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // change this later to wifi
                .setPersisted(true)
                .setPeriodic(1*60*10000)
                .build();

        JobScheduler scheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
//
//            ComponentName serviceComponent = new ComponentName(context, TestJobService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
//        builder.setPeriodic(2 * 60 * 1000, flexMillis);
//        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
//        //builder.setRequiresDeviceIdle(true); // device should be idle
//        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
//        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
//        jobScheduler.schedule(builder.build());
    }



}