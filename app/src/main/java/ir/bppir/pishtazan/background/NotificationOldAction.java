package ir.bppir.pishtazan.background;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.StaticValues;

public class NotificationOldAction {

    public static NotificationCompat.Action GetAgainAction(Integer notifyId, Context context, Byte type, Integer PersonId, Byte PersonType) {// GetAgainAction
        Intent AgainIntent = new Intent();
        if (type.equals(StaticValues.Call))
            AgainIntent.setAction(context.getString(R.string.ML_LaterCall));
        else if (type.equals(StaticValues.Meeting))
            AgainIntent.setAction(context.getString(R.string.ML_LaterMeeting));
        AgainIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        AgainIntent.putExtra(context.getResources().getString(R.string.ML_PanelType), PersonType);
        AgainIntent.putExtra(context.getResources().getString(R.string.ML_personId), PersonId);
        PendingIntent AgainPendingIntent = PendingIntent.getBroadcast(context, notifyId + 10, AgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.RemindAgain), AgainPendingIntent);
    }//_____________________________________________________________________________________________ GetAgainAction


    public static NotificationCompat.Action GetCallAction(Integer notifyId, Context context, Byte type) {// GetCallAction
        Intent CallIntent = new Intent();
        CallIntent.setAction(context.getString(R.string.ML_Calling));
        CallIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        PendingIntent CallingPendingIntent = PendingIntent.getBroadcast(context, notifyId + 20, CallIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.Calling), CallingPendingIntent);
    }//_____________________________________________________________________________________________ GetCallAction


    public static NotificationCompat.Action GetGoMeetingAction(Integer notifyId, Context context, Byte type) {// GetGoMeetingAction
        Intent GoMeetingIntent = new Intent();
        GoMeetingIntent.setAction(context.getString(R.string.ML_GoToMeeting));
        GoMeetingIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        PendingIntent GoMeetingPendingIntent = PendingIntent.getBroadcast(context, notifyId + 30, GoMeetingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.GotoMeeting), GoMeetingPendingIntent);
    }//_____________________________________________________________________________________________ GetGoMeetingAction


    public static NotificationCompat.Action GetIgnoreAction(Integer notifyId, Context context, Byte type) {// GetIgnoreAction
        Intent IgnoreIntent = new Intent();
        IgnoreIntent.setAction(context.getString(R.string.ML_Ignore));
        IgnoreIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        PendingIntent IgnorePendingIntent = PendingIntent.getBroadcast(context, notifyId + 40, IgnoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.Cancel), IgnorePendingIntent);
    }//_____________________________________________________________________________________________ GetIgnoreAction


    public static NotificationCompat.Action GetCertainAction(
            Integer notifyId,
            Context context,
            Byte type,
            Integer UserId) {//_____________________________________________________________________ GetCertainAction
        Intent CertainIntent = new Intent();
        CertainIntent.setAction(context.getString(R.string.ML_Certain));
        CertainIntent.putExtra(context.getResources().getString(R.string.ML_Type), type);
        CertainIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        CertainIntent.putExtra(context.getResources().getString(R.string.ML_personId), UserId);
        PendingIntent CertainPendingIntent = PendingIntent.getBroadcast(context, notifyId + 50, CertainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.Certain), CertainPendingIntent);
    }//_____________________________________________________________________________________________ GetCertainAction


    public static NotificationCompat.Action GetFailedAction(Integer notifyId, Context context, Byte type) {// GetFailedAction
        Intent FailedIntent = new Intent();
        FailedIntent.setAction(context.getString(R.string.ML_Failed));
        FailedIntent.putExtra(context.getResources().getString(R.string.ML_Id), notifyId);
        PendingIntent FailedPendingIntent = PendingIntent.getBroadcast(context, notifyId + 60, FailedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, context.getResources().getString(R.string.Failed), FailedPendingIntent);
    }//_____________________________________________________________________________________________ GetFailedAction

    
}
