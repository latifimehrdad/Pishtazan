package ir.bppir.pishtazan.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverLunchAppInBackground extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ Start onReceive

        context.startService(new Intent(context, ServiceNotification.class));

    }//_____________________________________________________________________________________________ End onReceive


}