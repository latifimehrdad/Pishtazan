package ir.bppir.pishtazan.background;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.NotificationManagerCompat;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.activity.RememberAgain;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class NotificationReceiver extends BroadcastReceiver {

    public Context context;
    private Dialog dialog;


    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ onReceive

        this.context = context;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Ignore))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Calling))) {
            String PhoneNumber = intent.getStringExtra(context.getResources().getString(R.string.ML_PhoneNumber));
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
            CallPerson(PhoneNumber);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_LaterCall))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), RememberAgain.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_Id),id);
            context.getApplicationContext().startActivity(intent1);
        }
    }//_____________________________________________________________________________________________ onReceive


    private void CancelNotification(int id) {//_____________________________________________________ CancelNotification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationManagerCompat.from(context).cancel(id);
        else {
            if (Context.NOTIFICATION_SERVICE != null) {
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