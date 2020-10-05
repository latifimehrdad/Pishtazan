package ir.bppir.pishtazan.background;

import android.app.Notification;
import android.content.Context;
import android.os.Build;

public class NotificationManagerClass {


    private android.app.Notification notification;


    public NotificationManagerClass(Context context, Integer serviceId) {//_________________________ NotificationManagerClass
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationNew aNew = new NotificationNew(context, serviceId);
            notification = aNew.getNotification();
        } else {
            NotificationOld old = new NotificationOld(context, serviceId);
            notification = old.getNotification();
        }

    }//_____________________________________________________________________________________________ NotificationManagerClass


    public NotificationManagerClass(
            Context context,
            String json) {//________________________________________________________________________ NotificationManagerClass
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationNew aNew = new NotificationNew(context, json);
            notification = aNew.getNotification();
        } else {
            NotificationOld old = new NotificationOld(context, json);
            notification = old.getNotification();
        }
    }//_____________________________________________________________________________________________ NotificationManagerClass



    public Notification getNotification() {//_______________________________________________________ getNotification
        return notification;
    }//_____________________________________________________________________________________________  getNotification

}
