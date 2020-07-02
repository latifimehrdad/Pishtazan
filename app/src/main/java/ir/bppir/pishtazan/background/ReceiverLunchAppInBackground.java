package ir.bppir.pishtazan.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ReceiverLunchAppInBackground extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ Start onReceive

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, ServiceNotification.class));
        } else {
            context.startService(new Intent(context, ServiceNotification.class));
        }

    }//_____________________________________________________________________________________________ End onReceive


}