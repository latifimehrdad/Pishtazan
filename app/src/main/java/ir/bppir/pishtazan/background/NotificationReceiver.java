package ir.bppir.pishtazan.background;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;


import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.activity.RememberAgain;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class NotificationReceiver extends BroadcastReceiver {

    public Context context;
    private boolean first = false;


    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ onReceive

        this.context = context;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Ignore))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Calling))) {
            first = true;
            String PhoneNumber = intent.getStringExtra(context.getResources().getString(R.string.ML_PhoneNumber));
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            SetResponseCall(id);
            CallPerson(PhoneNumber);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_LaterCall))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), RememberAgain.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_Id), id);
            intent1.putExtra(context.getResources().getString(R.string.ML_Type), StaticValues.Call);
            context.getApplicationContext().startActivity(intent1);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_LaterMeeting))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), RememberAgain.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_Id), id);
            intent1.putExtra(context.getResources().getString(R.string.ML_Type), StaticValues.Meeting);
            context.getApplicationContext().startActivity(intent1);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_GoToMeeting))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            SetResponseMeeting(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Certain))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            Byte type = intent.getByteExtra(context.getResources().getString(R.string.ML_Type),(byte) 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_Type), type);
            context.getApplicationContext().startActivity(intent1);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Failed))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
            /*
             *
             *
             *
             * */
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


    private void SetResponseCall(int id) {//________________________________________________________ SetResponseCall

        Realm realm = Realm.getDefaultInstance();

        DB_Notification notification = realm
                .where(DB_Notification.class)
                .equalTo("Id", id)
                .findFirst();

        if (notification != null) {

            try {
                Calendar TimeCheck = Calendar.getInstance();
                TimeCheck.add(Calendar.MINUTE, 5);

                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                Long longTime = Long.valueOf(sdf.format(TimeCheck.getTime()));

                sdf = new SimpleDateFormat("HH:mm");
                String stringTime = sdf.format(TimeCheck.getTime());

                String stringDate = PishtazanApplication
                        .getApplication(context)
                        .getApplicationUtilityComponent()
                        .getApplicationUtility()
                        .MiladiToJalali(TimeCheck.getTime(), "FullJalaliNumber");

                String tempDate = stringDate;
                if (tempDate != null)
                    tempDate = tempDate.replaceAll("/", "");
                else
                    return;

                Long longDate = Long.valueOf(tempDate);

                MD_Notify md_notify = new MD_Notify(
                        StaticValues.ResponseCall,
                        notification.getPersonType(),
                        stringDate,
                        longDate,
                        stringTime,
                        longTime,
                        null,
                        notification.getPersonName(),
                        notification.getPhoneNumber());
                SaveToNotify(md_notify);
            } finally {
                if (realm != null && !realm.isClosed())
                    realm.close();
                CancelNotification(id);
            }

        }
    }//_____________________________________________________________________________________________ SetResponseCall


    private void SetResponseMeeting(int id) {//_____________________________________________________ SetResponseMeeting

        Realm realm = Realm.getDefaultInstance();
        DB_Notification notification = realm
                .where(DB_Notification.class)
                .equalTo("Id", id)
                .findFirst();

        if (notification != null) {

            try {
                Calendar TimeCheck = Calendar.getInstance();
                TimeCheck.add(Calendar.MINUTE, 5);

                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                Long longTime = Long.valueOf(sdf.format(TimeCheck.getTime()));

                sdf = new SimpleDateFormat("HH:mm");
                String stringTime = sdf.format(TimeCheck.getTime());

                String stringDate = PishtazanApplication
                        .getApplication(context)
                        .getApplicationUtilityComponent()
                        .getApplicationUtility()
                        .MiladiToJalali(TimeCheck.getTime(), "FullJalaliNumber");

                String tempDate = stringDate;
                if (tempDate != null)
                    tempDate = tempDate.replaceAll("/", "");
                else
                    return;

                Long longDate = Long.valueOf(tempDate);

                MD_Notify md_notify = new MD_Notify(
                        StaticValues.ResponseMeeting,
                        notification.getPersonType(),
                        stringDate,
                        longDate,
                        stringTime,
                        longTime,
                        null,
                        notification.getPersonName(),
                        notification.getPhoneNumber());
                SaveToNotify(md_notify);
            } finally {
                if (realm != null && !realm.isClosed())
                    realm.close();
                CancelNotification(id);
            }
        }
    }//_____________________________________________________________________________________________ SetResponseMeeting


    private void SaveToNotify(MD_Notify md_notify) {//______________________________________________ SaveToNotify
        Realm realm = Realm.getDefaultInstance();
        try {
            int id;
            Number currentIdNum = realm.where(DB_Notification.class).max("Id");
            if (currentIdNum == null) {
                id = 1;
            } else {
                id = currentIdNum.intValue() + 1;
            }
            realm.beginTransaction();
            realm.createObject(DB_Notification.class, id).insert(md_notify);
            realm.commitTransaction();
        } finally {
            if (realm != null && !realm.isClosed())
                realm.close();
        }

    }//_____________________________________________________________________________________________ SaveToNotify


}