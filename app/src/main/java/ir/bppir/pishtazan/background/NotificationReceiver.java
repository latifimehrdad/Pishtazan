package ir.bppir.pishtazan.background;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import ir.bppir.pishtazan.R;

public class NotificationReceiver extends BroadcastReceiver {

    public Context context;

    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ onReceive

        this.context = context;

        int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
        String PhoneNumber = intent.getStringExtra(context.getResources().getString(R.string.ML_PhoneNumber));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationManagerCompat.from(context).cancel(id);
        else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
                nMgr.cancel(id);
            }
        }

    }//_____________________________________________________________________________________________ onReceive
}