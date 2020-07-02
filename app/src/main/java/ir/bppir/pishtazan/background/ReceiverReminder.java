package ir.bppir.pishtazan.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.database.DB_Notification;
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

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
//        String TimeBefore = sdf.format(TimeCheck.getTime());
        Long TimeBefore = Long.valueOf(sdf.format(TimeCheck.getTime()));

        TimeCheck = Calendar.getInstance();
        TimeCheck.add(Calendar.MINUTE, 3);

//        String TimeNext = sdf.format(TimeCheck.getTime());

        Long TimeNext = Long.valueOf(sdf.format(TimeCheck.getTime()));

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_Notification> notifications =
                realm.where(DB_Notification.class)
                        .equalTo("longDate", CurrentDate)
                        .and()
                        .between("longTime", TimeBefore, TimeNext)
                        .findAll();


        Log.i("meri", "**************** onReceive **************** ");
        Log.i("meri", "Date : " + CurrentDateString + " TimeBefore : " + TimeBefore + " TimeNext : " + TimeNext);
        if (notifications.size() > 0) {
            Log.i("meri", "notifications Size : " + notifications.size() + " Name : " + notifications.first().getPersonName());

            for(DB_Notification notification : notifications) {
                NotificationManagerClass managerClass = new NotificationManagerClass(
                        context,
                        false,
                        notification
                );
            }
        }
        else
            Log.i("meri", "notifications Size : " + notifications.size());


    }//_____________________________________________________________________________________________ GetCurrentLocation
}
