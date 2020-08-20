package ir.bppir.pishtazan.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ir.bppir.pishtazan.background.NotificationManagerClass;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Context context;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {//_________________________ Start onMessageReceived
        super.onMessageReceived(remoteMessage);

        context = getApplicationContext();
        Map<String, String> params = remoteMessage.getData();
        NotificationManagerClass managerClass = new NotificationManagerClass(context, params.toString());

    }//_____________________________________________________________________________________________ End onMessageReceived



}
