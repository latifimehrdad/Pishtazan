package ir.bppir.pishtazan.background;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import ir.bppir.pishtazan.R;

public class NotificationReceiver extends BroadcastReceiver {

    public Context context;

    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ onReceive

        this.context = context;

//        String action = intent.getAction();
//        if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Ignore))) {
//            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
//            CancelNotification(id);
//        } else if(action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Calling))){
//            String PhoneNumber = intent.getStringExtra(context.getResources().getString(R.string.ML_PhoneNumber));
//            CallPerson(PhoneNumber);
//            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
//            CancelNotification(id);
//        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Later))) {
//            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
//            CancelNotification(id);
//        }


    }//_____________________________________________________________________________________________ onReceive



    private void CancelNotification(int id) {//_____________________________________________________ CancelNotification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationManagerCompat.from(context).cancel(id);
        else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
                nMgr.cancel(id);
            }
        }
    }//_____________________________________________________________________________________________ CancelNotification



    private void CallPerson(String PhoneNumber) {//_________________________________________________ CallPerson
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + PhoneNumber));
        context.startActivity(call);
    }//_____________________________________________________________________________________________ CallPerson


}