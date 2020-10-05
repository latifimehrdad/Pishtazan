package ir.bppir.pishtazan.background;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class ReceiverReminder extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ GetCurrentLocation

        String CurrentDateString = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .MiladiToJalali(Calendar.getInstance().getTime(), "FullJalaliNumber");

        if (CurrentDateString != null)
            CurrentDateString = CurrentDateString.replaceAll("/", "");
        else
            return;

        Long CurrentDate = Long.valueOf(CurrentDateString);

        Calendar TimeCheck = Calendar.getInstance();
        TimeCheck.add(Calendar.MINUTE, -3);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
//        String TimeBefore = sdf.format(TimeCheck.getTime());
        long TimeBefore = Long.parseLong(sdf.format(TimeCheck.getTime()));

        TimeCheck = Calendar.getInstance();
        TimeCheck.add(Calendar.MINUTE, 3);

//        String TimeNext = sdf.format(TimeCheck.getTime());

        long TimeNext = Long.parseLong(sdf.format(TimeCheck.getTime()));

        Byte[] type = new Byte[2];
        type[0] = StaticValues.Call;
        type[1] = StaticValues.ResponseCall;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_Notification> notifications =
                realm.where(DB_Notification.class)
                        .equalTo("longDate", CurrentDate)
                        .and()
                        .between("longTime", TimeBefore, TimeNext)
                        .and()
                        .equalTo("showAlarm", false)
                        .and()
                        .in("notifyType", type)
                        .findAll();


        Log.i("meri", "**************** onReceive **************** ");
        Log.i("meri", "Date : " + CurrentDateString + " TimeBefore : " + TimeBefore + " TimeNext : " + TimeNext);
        Log.i("meri", "notifications Size : " + notifications.size() + " Call");
        if (notifications.size() > 0) {
            //Log.i("meri", "notifications Size : " + notifications.size() + " Name : " + notifications.first().getPersonName());
            for(DB_Notification notification : notifications) {
                realm.beginTransaction();
//                NotificationManagerClass managerClass = new NotificationManagerClass(context, notification);
                notification.setShowAlarm(true);
                realm.commitTransaction();
            }
        }

        TimeCheck = Calendar.getInstance();
        TimeCheck.add(Calendar.MINUTE, 15);
        TimeNext = Long.parseLong(sdf.format(TimeCheck.getTime()));


        type[0] = StaticValues.Meeting;
        type[1] = StaticValues.ResponseMeeting;

        RealmResults<DB_Notification> notificationsMeeting =
                realm.where(DB_Notification.class)
                        .equalTo("longDate", CurrentDate)
                        .and()
                        .between("longTime", TimeBefore, TimeNext)
                        .and()
                        .equalTo("showAlarm", false)
                        .and()
                        .in("notifyType", type)
                        .findAll();


        Log.i("meri", "notifications Size : " + notificationsMeeting.size() + " Meeting");
        Log.i("meri", "Date : " + CurrentDateString + " TimeBefore : " + TimeBefore + " TimeNext : " + TimeNext);
        if (notificationsMeeting.size() > 0) {
            //Log.i("meri", "notifications Size : " + notificationsMeeting.size() + " Name : " + notificationsMeeting.first().getPersonName());
            for(DB_Notification notification : notificationsMeeting) {
                realm.beginTransaction();
//                NotificationManagerClass managerClass = new NotificationManagerClass(context, notification);
                notification.setShowAlarm(true);
                realm.commitTransaction();
            }
        }

        realm.close();


    }//_____________________________________________________________________________________________ GetCurrentLocation
}
