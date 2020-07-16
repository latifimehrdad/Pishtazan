package ir.bppir.pishtazan.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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

public class NotificationOld {

    private DB_Notification db_notification;
    private boolean ShowAlways;
    private Context context;
    private NotificationManager notifyManager;
    private android.app.Notification notification;


//******************************************** Show Normal *****************************************


    public NotificationOld(DB_Notification db_notification, Context context) {//____________________ NotificationOld
        this.db_notification = db_notification;
        this.ShowAlways = false;
        this.context = context;
        ShowNormalNotificationOld();
    }//_____________________________________________________________________________________________ NotificationOld



    private void ShowNormalNotificationOld() {//____________________________________________________ ShowNormalNotificationOld
        Integer id = db_notification.getId();
        Byte notifyType = db_notification.getNotifyType();
        List<NotificationCompat.Action> actions = new ArrayList<>();
        if (notifyType.equals(StaticValues.Call)) {
            actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Call));
            actions.add(NotificationOldAction.GetCallAction(id, context, db_notification.getPhoneNumber(), StaticValues.Call));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.CallWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            BuilderNotification(
                    id,
                    context.getResources().getString(R.string.RememberCall),
                    stringBuilder.toString(),
                    actions);
        } else if (notifyType.equals(StaticValues.Meeting)) {
            actions.add(NotificationOldAction.GetIgnoreAction(id, context, StaticValues.Meeting));
            actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Meeting));
            actions.add(NotificationOldAction.GetGoMeetingAction(id, context, StaticValues.Meeting));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.MeetingWith));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" در ");
            stringBuilder.append(context.getResources().getString(R.string.Clock));
            stringBuilder.append(" ");
            stringBuilder.append(db_notification.getStringTime());
            BuilderNotification(
                    id,
                    context.getResources().getString(R.string.RememberMeeting),
                    stringBuilder.toString(),
                    actions);
        } else if (notifyType.equals(StaticValues.ResponseCall)) {
            actions.add(NotificationOldAction.GetIgnoreAction(id, context, StaticValues.ResponseCall));
            actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Call));
            actions.add(NotificationOldAction.GetCertainAction(id, context, StaticValues.Call));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.ResponseCall));
            stringBuilder.append(" با ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" ");
            stringBuilder.append(context.getResources().getString(R.string.HowWasIt));
            BuilderNotification(
                    id,
                    context.getResources().getString(R.string.ResponseCall),
                    stringBuilder.toString(),
                    actions);
        } else if (notifyType.equals(StaticValues.ResponseMeeting)) {
            actions.add(NotificationOldAction.GetFailedAction(id, context, StaticValues.Meeting));
            actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Meeting));
            actions.add(NotificationOldAction.GetCertainAction(id, context, StaticValues.Meeting));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.ResponseMeeting));
            stringBuilder.append(" با ");
            stringBuilder.append(db_notification.getPersonName());
            stringBuilder.append(" ");
            stringBuilder.append(context.getResources().getString(R.string.HowWasIt));
            BuilderNotification(
                    id,
                    context.getResources().getString(R.string.ResponseMeeting),
                    stringBuilder.toString(),
                    actions);
        }

    }//_____________________________________________________________________________________________ ShowNormalNotificationOld


//******************************************** Show Normal *****************************************





//******************************************** Show Always *****************************************


    public NotificationOld(Context context, Integer notifyId) {//___________________________________ NotificationOld
        this.context = context;
        this.ShowAlways = true;
        ShowAlwaysNotificationOld(notifyId);
    }//_____________________________________________________________________________________________ NotificationOld



    private void ShowAlwaysNotificationOld(Integer notifyId) {//____________________________________ ShowAlwaysNotificationOld

        BuilderNotification(
                notifyId,
                context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.ServiceRun),
                null);
    }//_____________________________________________________________________________________________ ShowAlwaysNotificationOld

//******************************************** Show Always *****************************************



    private void BuilderNotification(
            Integer notifyId,
            String Title,
            String Message,
            List<NotificationCompat.Action> actions) {//____________________________________________ BuilderNotification

//        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_pishtazan);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        bigText.bigText(Message);
        builder.setStyle(bigText);
        builder.setSmallIcon(R.drawable.logo_pishtazan_small)
                .setTicker(context.getString(R.string.app_name))
                .setOngoing(ShowAlways)
                .setWhen(0)
                .setColor(context.getResources().getColor(R.color.ML_Dialog))
                .setAutoCancel(true)
                .setContentTitle(Title)
                .setSound(getSound())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        if (actions != null) {
            for (NotificationCompat.Action action : actions)
                builder.addAction(action);
        }

        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(notifyId, builder.build());
        notification = builder.build();

    }//_____________________________________________________________________________________________ BuilderNotification




//******************************************** Setting *********************************************

    private NotificationManager getManager() {//____________________________________________________ getManager
        if (notifyManager == null) {
            notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifyManager;
    }//_____________________________________________________________________________________________ getManager


    public Uri getSound() {//_______________________________________________________________________ getSound
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }//_____________________________________________________________________________________________ getSound



    public Notification getNotification() {//_______________________________________________________ getNotification
        return notification;
    }//_____________________________________________________________________________________________  getNotification

//******************************************** Setting *********************************************



}
