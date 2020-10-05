package ir.bppir.pishtazan.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_Notification;
import ir.bppir.pishtazan.utility.StaticValues;

public class NotificationNew {

    private boolean ShowAlways;
    private Context context;
    private String CHANNEL_ONE_ID = "ir.bppir.pishtazan.PishtazanNotification";
    private NotificationManager notifyManager;
    private android.app.Notification notification;
    private MD_Notification md_notification;


//******************************************** Show Normal *****************************************

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationNew(Context context, String text) {//____________________ NotificationNew
        Gson gson = new Gson();
        this.md_notification = gson.fromJson(text, MD_Notification.class);
        this.ShowAlways = false;
        this.context = context;
        CreateChannelsEvent();
        ShowNormalNotificationNew();
    }//_____________________________________________________________________________________________ NotificationNew


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ShowNormalNotificationNew() {//____________________________________________________ ShowNormalNotificationNew

        Integer id = md_notification.getId();
        Byte NType = md_notification.getNType().byteValue();
        Byte RType = md_notification.getRType().byteValue();
        Integer PersonId;
        Byte PersonType;
        if (md_notification.getColleagueId() != null) {
            PersonId = md_notification.getColleagueId();
            PersonType = StaticValues.Colleague;
        } else {
            PersonId = md_notification.getCustomerId();
            PersonType = StaticValues.Customer;
        }
        List<Notification.Action> actions = new ArrayList<>();

        if (NType.equals(StaticValues.NTypeNormal)) {
            if (RType.equals(StaticValues.RTypeCall)) {
                actions.add(NotificationNewAction.GetAgainAction(id, context, StaticValues.Call, PersonId, PersonType));
                actions.add(NotificationNewAction.GetIgnoreAction(id, context, StaticValues.Call));
                actions.add(NotificationNewAction.GetCallAction(id, context, StaticValues.Call,PersonId));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            } else if (RType.equals(StaticValues.RTypeMeeting)) {
                actions.add(NotificationNewAction.GetIgnoreAction(id, context, StaticValues.Meeting));
                actions.add(NotificationNewAction.GetAgainAction(id, context, StaticValues.Meeting, PersonId, PersonType));
                actions.add(NotificationNewAction.GetGoMeetingAction(id, context, StaticValues.Meeting));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            }
        } else if (NType.equals(StaticValues.NTypeResponse)) {
            if (RType.equals(StaticValues.RTypeCall)) {
                actions.add(NotificationNewAction.GetFailedAction(id, context, StaticValues.ResponseCall));
                actions.add(NotificationNewAction.GetAgainAction(id, context, StaticValues.Call, PersonId, PersonType));
                actions.add(NotificationNewAction.GetCertainAction(id, context, StaticValues.Call, 0));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);

            } else if (RType.equals(StaticValues.RTypeMeeting)) {
                actions.add(NotificationNewAction.GetFailedAction(id, context, StaticValues.Meeting));
                actions.add(NotificationNewAction.GetAgainAction(id, context, StaticValues.Meeting, PersonId, PersonType));
                actions.add(NotificationNewAction.GetCertainAction(id, context, StaticValues.Meeting, 0));
                BuilderNotification(
                        id,
                        md_notification.getTitle(),
                        md_notification.getBody(),
                        actions);
            }
        }

    }//_____________________________________________________________________________________________ ShowNormalNotificationNew


//******************************************** Show Normal *****************************************


//******************************************** Show Always *****************************************

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationNew(Context context, Integer notifyId) {//___________________________________ NotificationNew
        this.context = context;
        this.ShowAlways = true;
        CreateChannelsEvent();
        ShowAlwaysNotificationNew(notifyId);
    }//_____________________________________________________________________________________________ NotificationNew


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ShowAlwaysNotificationNew(Integer notifyId) {//____________________________________ ShowAlwaysNotificationNew

        BuilderNotification(
                notifyId,
                context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.ServiceRun),
                null);
    }//_____________________________________________________________________________________________ ShowAlwaysNotificationNew

//******************************************** Show Always *****************************************


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuilderNotification(
            Integer notifyId,
            String Title,
            String Message,
            List<Notification.Action> actions) {//__________________________________________________ BuilderNotification

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_pishtazan);
        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ONE_ID)
                .setSmallIcon(R.drawable.logo_pishtazan_small)
                .setOngoing(ShowAlways)
                .setSound(getSound())
                .setContentTitle(Title)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(Message))
                .setLargeIcon(icon)
                .setCategory(context.getResources().getString(R.string.app_name))
                .setPriority(Notification.PRIORITY_HIGH)
                .addPerson(notifyId.toString())
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPublicVersion(notification )
                .setAutoCancel(true);

        if (actions != null) {
            for (Notification.Action action : actions)
                builder.addAction(action);
        }

        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        getManager().notify(notifyId, builder.build());
        notification = builder.build();

    }//_____________________________________________________________________________________________ BuilderNotification


//******************************************** Setting *********************************************

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CreateChannelsEvent() {//___________________________________________________________ CreateChannelsEvent
        String CHANNEL_ONE_NAME = "PishtazanNotification";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setLightColor(context.getResources().getColor(R.color.colorPrimary));
        notificationChannel.setShowBadge(true);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.WHITE);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(notificationChannel);
    }//_____________________________________________________________________________________________ CreateChannelsEvent


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
