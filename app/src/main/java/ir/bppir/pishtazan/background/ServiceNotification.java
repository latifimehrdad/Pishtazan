package ir.bppir.pishtazan.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ServiceNotification extends Service {

    private static android.app.Notification alarmNotify = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//____________________________ Start onStartCommand


        if (alarmNotify == null) {
            Integer id = 6780;
            NotificationManagerClass managerClass =
                    new NotificationManagerClass(getApplicationContext(), id);
            alarmNotify = managerClass.getNotification();
            startForeground(id, alarmNotify);
        }


        Calendar now = Calendar.getInstance();
        Intent intent1 = new Intent(getApplicationContext(), ReceiverReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), 60 * 1000, pendingIntent);

        return Service.START_STICKY;
    }//_____________________________________________________________________________________________ End onStartCommand



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
