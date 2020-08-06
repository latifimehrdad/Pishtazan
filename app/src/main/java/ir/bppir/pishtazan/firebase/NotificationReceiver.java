package ir.bppir.pishtazan.firebase;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MR_PersonNumber;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.activity.RememberAgain;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {

    public Context context;
    private boolean first = false;


    @Override
    public void onReceive(Context context, Intent intent) {//_______________________________________ onReceive

        this.context = context;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Ignore))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Calling))) {
            first = true;
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            GetPersonNumber(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_LaterCall))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            int PersonId = intent.getIntExtra(context.getResources().getString(R.string.ML_personId), 0);
            byte PersonType = intent.getByteExtra(context.getResources().getString(R.string.ML_PanelType) , (byte) 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), RememberAgain.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_personId), PersonId);
            intent1.putExtra(context.getResources().getString(R.string.ML_Type), StaticValues.Call);
            intent1.putExtra(context.getResources().getString(R.string.ML_PanelType), PersonType);
            context.getApplicationContext().startActivity(intent1);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_LaterMeeting))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            int PersonId = intent.getIntExtra(context.getResources().getString(R.string.ML_personId), 0);
            byte PersonType = intent.getByteExtra(context.getResources().getString(R.string.ML_PanelType) , (byte) 0);
            CancelNotification(id);
            Intent intent1 = new Intent(context.getApplicationContext(), RememberAgain.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.putExtra(context.getResources().getString(R.string.ML_personId), PersonId);
            intent1.putExtra(context.getResources().getString(R.string.ML_PanelType), PersonType);
            intent1.putExtra(context.getResources().getString(R.string.ML_Type), StaticValues.Meeting);
            context.getApplicationContext().startActivity(intent1);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_GoToMeeting))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            CancelNotification(id);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Certain))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            ChangeState(id, StaticValues.Certain);
        } else if (action.equalsIgnoreCase(context.getResources().getString(R.string.ML_Failed))) {
            int id = intent.getIntExtra(context.getResources().getString(R.string.ML_Id), 0);
            ChangeState(id, StaticValues.Failed);
        }
    }//_____________________________________________________________________________________________ onReceive


    private void CancelNotification(int id) {//_____________________________________________________ CancelNotification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationManagerCompat.from(context).cancel(id);
        else {
            if (Context.NOTIFICATION_SERVICE != null) {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
                nMgr.cancel(id);
            }
        }
    }//_____________________________________________________________________________________________ CancelNotification


    private void CallPerson(String PhoneNumber) {//_________________________________________________ CallPerson
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + PhoneNumber));
        context.startActivity(call);
    }//_____________________________________________________________________________________________ CallPerson



    private void GetPersonNumber(Integer Id) {//____________________________________________________ GetPersonNumber


        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(context)
                .getRetrofitComponent();

        Integer UserInfoId = GetUserId();

        retrofitComponent
                .getRetrofitApiInterface()
                .GET_MOBILE_NUMBER(Id, UserInfoId)
                .enqueue(new Callback<MR_PersonNumber>() {
                    @Override
                    public void onResponse(Call<MR_PersonNumber> call, Response<MR_PersonNumber> response) {
                        if (response.body() != null) {
                            CancelNotification(Id);
                            if (response.body().getStatue() == 1)
                                CallPerson(response.body().getMobileNumber());
                        }
                    }

                    @Override
                    public void onFailure(Call<MR_PersonNumber> call, Throwable t) {

                    }
                });

    }//_____________________________________________________________________________________________ GetPersonNumber


    public Integer GetUserId() {//__________________________________________________________________ GetUserId

        DB_UserInfo db_userInfo = GetUserInfo();
        if (db_userInfo == null)
            return 0;
        else
            return db_userInfo.getId();
    }//_____________________________________________________________________________________________ GetUserId



    public DB_UserInfo GetUserInfo() {//____________________________________________________________ GetUserInfo

        Realm realm = Realm.getDefaultInstance();
        DB_UserInfo db_userInfo = realm.where(DB_UserInfo.class).findFirst();
        realm.close();
        return db_userInfo;

    }//_____________________________________________________________________________________________ GetUserInfo



    private void ChangeState(Integer Id, Byte state) {//____________________________________________ ChangeState
        CancelNotification(Id);
    }//_____________________________________________________________________________________________ ChangeState
}