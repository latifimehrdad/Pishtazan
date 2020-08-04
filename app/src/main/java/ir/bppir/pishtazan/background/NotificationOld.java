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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notification;
import ir.bppir.pishtazan.utility.StaticValues;

public class NotificationOld {

    private boolean ShowAlways;
    private Context context;
    private NotificationManager notifyManager;
    private android.app.Notification notification;
    private MD_Notification md_notification;


//******************************************** Show Normal *****************************************


    public NotificationOld(Context context, String text) {//____________________ NotificationOld
        Gson gson = new Gson();
        this.ShowAlways = false;
        this.context = context;
        this.md_notification = gson.fromJson(text, MD_Notification.class);
        ShowNormalNotificationOld();
    }//_____________________________________________________________________________________________ NotificationOld



    private void ShowNormalNotificationOld() {//____________________________________________________ ShowNormalNotificationOld
        Integer id = md_notification.getId();
        Byte NType = md_notification.getNType().byteValue();
        Byte RType = md_notification.getRType().byteValue();
        List<NotificationCompat.Action> actions = new ArrayList<>();

        if (NType.equals(StaticValues.NTypeNormal)) {
            if (RType.equals(StaticValues.RTypeCall)) {
                actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Call));
                actions.add(NotificationOldAction.GetIgnoreAction(id, context, StaticValues.Call));
                actions.add(NotificationOldAction.GetCallAction(id, context, StaticValues.Call));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            } else if (RType.equals(StaticValues.RTypeMeeting)) {
                actions.add(NotificationOldAction.GetIgnoreAction(id, context, StaticValues.Meeting));
                actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Meeting));
                actions.add(NotificationOldAction.GetGoMeetingAction(id, context, StaticValues.Meeting));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            }
        } else if (NType.equals(StaticValues.NTypeResponse)) {
            if (RType.equals(StaticValues.RTypeCall)) {
                actions.add(NotificationOldAction.GetFailedAction(id, context, StaticValues.ResponseCall));
                actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Call));
                actions.add(NotificationOldAction.GetCertainAction(id, context, StaticValues.Call, 0));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);

            } else if (RType.equals(StaticValues.RTypeMeeting)) {
                actions.add(NotificationOldAction.GetFailedAction(id, context, StaticValues.Meeting));
                actions.add(NotificationOldAction.GetAgainAction(id, context, StaticValues.Meeting));
                actions.add(NotificationOldAction.GetCertainAction(id, context, StaticValues.Meeting, 0));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            }
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
