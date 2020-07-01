package ir.bppir.pishtazan.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;

public class NotificationManagerClass {

    private Context context;
    private Boolean ShowAlways;
    private DB_Notification db_notification;
    private NotificationManager notifManager;
    private String CHANNEL_ONE_NAME = "New Event";
    private String CHANNEL_ONE_ID = "com.ngra.trafficcontroller.Event";


    public NotificationManagerClass(
            Context context,
            Boolean showAlways,
            DB_Notification db_notification) {//____________________________________________________ NotificationManagerClass
        this.context = context;
        ShowAlways = showAlways;
        this.db_notification = db_notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CreateChannelsEvent();
            ShowNotificationNew(db_notification);
        } else {
            ShowNotificationOld(db_notification);
        }
    }//_____________________________________________________________________________________________ NotificationManagerClass



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ShowNotificationNew(DB_Notification db_notification) {//________________________________ Start ShowNotificationNew

        String Title;
        String Message;
        int id = db_notification.getId();

        Intent IgnoreIntent = new Intent();
        IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
        IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
        PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, 7126, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent AgainIntent = new Intent();
        AgainIntent.setAction(context.getString(R.string.ML_Later));
        AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
        PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, 7126, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_pishtazan);
        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ONE_ID)
                .addAction(R.drawable.ic_baseline_cancel, context.getResources().getString(R.string.Cancel), IgnorePendingIntent)
                .addAction(R.drawable.ic_baseline_contact_phone, context.getResources().getString(R.string.RemindAgain), AgainPendingIntent)
                .setSmallIcon(R.drawable.logo_pishtazan_small)
                .setOngoing(ShowAlways)
                .setLargeIcon(icon)
                .setSubText(context.getResources().getString(R.string.ShowDetail))
                .setAutoCancel(true);


        if (db_notification.getNotifyType() == StaticValues.Call) {
            Intent CallIntent = new Intent();
            CallIntent.setAction(context.getString(R.string.ML_Calling));
            CallIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
            CallIntent.putExtra(context.getResources().getString(R.string.ML_PhoneNumber), db_notification.getPhoneNumber());
            PendingIntent CallingPendingIntent = PendingIntent.getBroadcast(context, 7127, CallIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Title = context.getResources().getString(R.string.RememberCall);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.CallWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            Message = stringBuilder.toString();
            builder.setContentText(Title);
            builder.setStyle(new Notification.BigTextStyle()
                    .bigText(Message));
            builder.addAction(R.drawable.ic_baseline_phone_android, context.getResources().getString(R.string.Calling), CallingPendingIntent);

        }
        else {
            Title = context.getResources().getString(R.string.RememberMeeting);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.MeetingWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            Message = stringBuilder.toString();
            builder.setContentText(Title);
            builder.setStyle(new Notification.BigTextStyle()
                    .bigText(Message));
        }

        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(id, builder.build());
    }//_____________________________________________________________________________________________ End ShowNotificationNew


    private void ShowNotificationOld(DB_Notification db_notification) {//___________________________ Start ShowNotificationOld


        String Title;
        String Message;
        int id = db_notification.getId();

        Intent IgnoreIntent = new Intent();
        IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
        IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
        PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, 7126, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent AgainIntent = new Intent();
        AgainIntent.setAction(context.getString(R.string.ML_Later));
        AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
        PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, 7126, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo_pishtazan_small)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(0)
                .setColor(context.getResources().getColor(R.color.ML_Dialog))
                .setAutoCancel(true)
                .setContentText(context.getString(R.string.app_name))
                .setSound(getSound())
                .addAction(R.drawable.ic_baseline_cancel, context.getResources().getString(R.string.Cancel), IgnorePendingIntent)
                .addAction(R.drawable.ic_baseline_contact_phone, context.getResources().getString(R.string.RemindAgain), AgainPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        if (db_notification.getNotifyType() == StaticValues.Call) {
            Title = context.getResources().getString(R.string.RememberCall);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.CallWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            Message = stringBuilder.toString();
            builder.setContentTitle(Title);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(Message);
            builder.setStyle(bigText);
            Intent CallIntent = new Intent();
            CallIntent.setAction(context.getString(R.string.ML_Calling));
            CallIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
            CallIntent.putExtra(context.getResources().getString(R.string.ML_PhoneNumber), db_notification.getPhoneNumber());
            PendingIntent CallingPendingIntent = PendingIntent.getBroadcast(context, 7127, CallIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(R.drawable.ic_baseline_phone_android, context.getResources().getString(R.string.Calling), CallingPendingIntent);

        }
        else {
            Title = context.getResources().getString(R.string.RememberMeeting);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.MeetingWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            Message = stringBuilder.toString();
            builder.setContentTitle(Title);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(Message);
            builder.setStyle(bigText);
        }

//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
//        remoteViews.setTextViewText(R.id.title,Title);
//        remoteViews.setTextViewText(R.id.text,Message);
//        remoteViews.setOnClickPendingIntent(R.id.image,IgnorePendingIntent);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.logo_splash)
//                .setTicker(context.getString(R.string.app_name))
//                .setAutoCancel(true)
//                .setContent(remoteViews);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText(Message);
//        builder.setSmallIcon(R.drawable.logo_splash)
//                .setTicker(context.getString(R.string.app_name))
//                .setWhen(0)
//                .setColor(context.getResources().getColor(R.color.ML_Dialog))
//                .setAutoCancel(true)
//                .setContentTitle(Title)
//                .setContentText(context.getString(R.string.app_name))
//                .setSound(getSound())
//                .setStyle(bigText)
//                .addAction(R.drawable.ic_baseline_cancel, context.getResources().getString(R.string.Cancel), IgnorePendingIntent)
//                .addAction(R.drawable.ic_baseline_phone_android, context.getResources().getString(R.string.Calling), CallingPendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .build();
        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(id, builder.build());
    }//_____________________________________________________________________________________________ End ShowNotificationOld


    public Uri getSound() {//_______________________________________________________________________ getSound
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }//_____________________________________________________________________________________________ getSound


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CreateChannelsEvent() {//___________________________________________________________ CreateChannelsEvent
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, notifManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(context.getResources().getColor(R.color.colorPrimary));
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(notificationChannel);
    }//_____________________________________________________________________________________________ End CreateChannelsEvent



    private NotificationManager getManager() {//____________________________________________________ Start getManager
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }//_____________________________________________________________________________________________ End getManager

}