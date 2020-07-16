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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.utility.StaticValues;

public class NotificationManagerClass {

    private Context context;
    private Boolean ShowAlways;
    private DB_Notification db_notification;
    private NotificationManager notifManager;
    private String CHANNEL_ONE_NAME = "Reminder1";
    private String CHANNEL_ONE_ID = "com.ngra.trafficcontroller.reminder1";
    private android.app.Notification notification;
    private Integer ServiceId;


    public NotificationManagerClass(Context context, Integer serviceId) {//_________________________ NotificationManagerClass
        this.context = context;
        ServiceId = serviceId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationNew aNew = new NotificationNew(context, serviceId);
            notification = aNew.getNotification();
        }

    }//_____________________________________________________________________________________________ NotificationManagerClass


    public NotificationManagerClass(
            Context context,
            DB_Notification db_notification) {//____________________________________________________ NotificationManagerClass
        this.context = context;
        this.db_notification = db_notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationNew aNew = new NotificationNew(db_notification, context);
            notification = aNew.getNotification();
        }
    }//_____________________________________________________________________________________________ NotificationManagerClass



    public Notification getNotification() {//_______________________________________________________ getNotification
        return notification;
    }//_____________________________________________________________________________________________  getNotification


}


/*


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SetNotificationNew(DB_Notification db_notification) {//_____________________________ SetNotificationNew

        List<Notification.Action> actions = new ArrayList<>();

        if (ShowAlways) {
            ShowNotificationNew(
                    ServiceId,
                    context.getResources().getString(R.string.app_name),
                    context.getResources().getString(R.string.ServiceRun),
                    null);
        } else {
            Integer id = db_notification.getId();
            if (db_notification.getNotifyType() == StaticValues.Call) {

                Intent AgainIntent = new Intent();
                AgainIntent.setAction(context.getString(R.string.ML_LaterCall));
                AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, id + 20, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.RemindAgain),AgainPendingIntent));

                Intent CallIntent = new Intent();
                CallIntent.setAction(context.getString(R.string.ML_Calling));
                CallIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                CallIntent.putExtra(context.getResources().getString(R.string.ML_PhoneNumber), db_notification.getPhoneNumber());
                PendingIntent CallingPendingIntent = PendingIntent.getBroadcast(context, id + 10, CallIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0, context.getResources().getString(R.string.Calling),CallingPendingIntent));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(context.getResources().getString(R.string.CallWith));
                stringBuilder.append(" ");
                stringBuilder.append(db_notification.getPersonName());
                stringBuilder.append(" در ");
                stringBuilder.append(context.getResources().getString(R.string.Clock));
                stringBuilder.append(" ");
                stringBuilder.append(db_notification.getStringTime());

                ShowNotificationNew(
                        id,
                        context.getResources().getString(R.string.RememberCall),
                        stringBuilder.toString(),
                        actions);

            } else if (db_notification.getNotifyType() == StaticValues.Meeting) {

                Intent GoMeetingIntent = new Intent();
                GoMeetingIntent.setAction(context.getString(R.string.ML_GoToMeeting));
                GoMeetingIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent GoMeetingPendingIntent = PendingIntent.getBroadcast(context, id + 40, GoMeetingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.GotoMeeting),GoMeetingPendingIntent));


                Intent AgainIntent = new Intent();
                AgainIntent.setAction(context.getString(R.string.ML_LaterMeeting));
                AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, id + 20, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.RemindAgain),AgainPendingIntent));


                Intent IgnoreIntent = new Intent();
                IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
                IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, id + 30, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.Cancel),IgnorePendingIntent));



                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(context.getResources().getString(R.string.MeetingWith));
                stringBuilder.append(" ");
                stringBuilder.append(db_notification.getPersonName());
                stringBuilder.append(" در ");
                stringBuilder.append(context.getResources().getString(R.string.Clock));
                stringBuilder.append(" ");
                stringBuilder.append(db_notification.getStringTime());

                ShowNotificationNew(
                        id,
                        context.getResources().getString(R.string.RememberMeeting),
                        stringBuilder.toString(),
                        actions);

            } else if (db_notification.getNotifyType() == StaticValues.ResponseCall) {

                Intent AgainIntent = new Intent();
                AgainIntent.setAction(context.getString(R.string.ML_LaterCall));
                AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, id + 20, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.RemindAgain),AgainPendingIntent));

                Intent IgnoreIntent = new Intent();
                IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
                IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, id + 30, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.Cancel),IgnorePendingIntent));

                Intent CertainIntent = new Intent();
                CertainIntent.setAction(context.getString(R.string.ML_Certain));
                CertainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent CertainPendingIntent = PendingIntent.getBroadcast(context, id + 100, CertainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.Certain),CertainPendingIntent));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(context.getResources().getString(R.string.ResponseCall));
                stringBuilder.append(" با ");
                stringBuilder.append(db_notification.getPersonName());
                stringBuilder.append(" ");
                stringBuilder.append(context.getResources().getString(R.string.HowWasIt));

                ShowNotificationNew(
                        id,
                        context.getResources().getString(R.string.ResponseCall),
                        stringBuilder.toString(),
                        actions);


            } else if (db_notification.getNotifyType() == StaticValues.ResponseMeeting) {

                Intent FailedIntent = new Intent();
                FailedIntent.setAction(context.getString(R.string.ML_Failed));
                FailedIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent FailedPendingIntent = PendingIntent.getBroadcast(context, id + 50, FailedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.Failed),FailedPendingIntent));

                Intent AgainIntent = new Intent();
                AgainIntent.setAction(context.getString(R.string.ML_LaterMeeting));
                AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, id + 20, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.RemindAgain),AgainPendingIntent));

                Intent CertainIntent = new Intent();
                CertainIntent.setAction(context.getString(R.string.ML_Certain));
                CertainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
                PendingIntent CertainPendingIntent = PendingIntent.getBroadcast(context, id + 100, CertainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                actions.add(new Notification.Action(0,context.getResources().getString(R.string.Certain),CertainPendingIntent));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(context.getResources().getString(R.string.ResponseMeeting));
                stringBuilder.append(" با ");
                stringBuilder.append(db_notification.getPersonName());
                stringBuilder.append(" ");
                stringBuilder.append(context.getResources().getString(R.string.HowWasIt));

                ShowNotificationNew(
                        id,
                        context.getResources().getString(R.string.ResponseMeeting),
                        stringBuilder.toString(),
                        actions);
            }

        }

    }//_____________________________________________________________________________________________ SetNotificationNew



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ShowNotificationNew(
            Integer notifyId,
            String Title,
            String Message,
            List<Notification.Action> actions) {//__________________________________________________ ShowNotificationNew

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_pishtazan);
        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ONE_ID)
                .setSmallIcon(R.drawable.logo_pishtazan_small)
                .setOngoing(ShowAlways)
                .setSound(getSound())
                .setContentTitle(Title)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(Message))
                .setLargeIcon(icon)
                .setAutoCancel(true);

        if (actions != null) {
            for (Notification.Action action : actions)
                builder.addAction(action);
        }

        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(notifyId, builder.build());
        notification = builder.build();

    }//_____________________________________________________________________________________________ ShowNotificationNew






    private void ShowNotificationOld(DB_Notification db_notification) {//___________________________ ShowNotificationOld


        String Title;
        String Message;
        int id;
        NotificationCompat.Builder builder;
        List<Notification.Action> actions = new ArrayList<>();

        if (ShowAlways) {
            id = ServiceId;
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            builder = new NotificationCompat.Builder(context);
            bigText.bigText(context.getResources().getString(R.string.ServiceRun));
            builder.setStyle(bigText);
            builder.setSmallIcon(R.drawable.logo_pishtazan_small)
                    .setTicker(context.getString(R.string.app_name))
                    .setWhen(0)
                    .setColor(context.getResources().getColor(R.color.ML_Dialog))
                    .setAutoCancel(true)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setSound(getSound())
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .build();

        } else {
            id = db_notification.getId();
            Intent IgnoreIntent = new Intent();
            IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
            IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
            PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, id + 10, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Intent AgainIntent = new Intent();
            AgainIntent.setAction(context.getString(R.string.ML_LaterCall));
            AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), id);
            PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, id + 20, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.logo_pishtazan_small)
                    .setTicker(context.getString(R.string.app_name))
                    .setWhen(0)
                    .setColor(context.getResources().getColor(R.color.ML_Dialog))
                    .setAutoCancel(true)
                    .setContentText(context.getString(R.string.app_name))
                    .setSound(getSound())
                    .addAction(0, context.getResources().getString(R.string.Cancel), IgnorePendingIntent)
                    .addAction(0, context.getResources().getString(R.string.RemindAgain), AgainPendingIntent)
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
                PendingIntent CallingPendingIntent = PendingIntent.getBroadcast(context, id + 30, CallIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.addAction(0, context.getResources().getString(R.string.Calling), CallingPendingIntent);

            } else {
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

        }

        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(id, builder.build());
        notification = builder.build();
    }//_____________________________________________________________________________________________ ShowNotificationOld





//*************************************** Actions PendingIntent ************************************






//*************************************** Actions PendingIntent ************************************




//****************************************** Show Always *******************************************
/*
    public NotificationManagerClass(
            Context context,
            Integer ServiceId) {//____________________________________________________ NotificationManagerClass
        this.context = context;
        this.ServiceId = ServiceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CreateChannelsEvent();
            ShowAlwaysNotificationNew();
        } else {
            ShowAlwaysNotificationOld();
        }
    }//_____________________________________________________________________________________________ NotificationManagerClass



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ShowAlwaysNotificationNew() {//____________________________________________________ ShowAlwaysNotificationNew

        ShowNotificationNew(
                ServiceId,
                context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.ServiceRun),
                null);
    }//_____________________________________________________________________________________________ ShowAlwaysNotificationNew



    private void ShowAlwaysNotificationOld() {//____________________________________________________ ShowAlwaysNotificationOld


    }//_____________________________________________________________________________________________ ShowAlwaysNotificationOld


 */

//****************************************** Show Always *******************************************


/*
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
    }//_____________________________________________________________________________________________ CreateChannelsEvent


    private NotificationManager getManager() {//____________________________________________________ getManager
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }//_____________________________________________________________________________________________ getManager



}
*/