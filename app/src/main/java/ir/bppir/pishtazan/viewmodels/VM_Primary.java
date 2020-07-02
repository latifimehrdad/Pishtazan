package ir.bppir.pishtazan.viewmodels;

import android.content.Context;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.background.NotificationManagerClass;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Response;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary


    public void CancelRequest() {//_________________________________________________________________ CancelRequest
        if (PrimaryCall != null)
            PrimaryCall.cancel();
    }//_____________________________________________________________________________________________ CancelRequest


    public Call getPrimaryCall() {//________________________________________________________________ getPrimaryCall
        return PrimaryCall;
    }//_____________________________________________________________________________________________ getPrimaryCall

    public void setPrimaryCall(Call primaryCall) {//________________________________________________ setPrimaryCall
        PrimaryCall = null;
        PrimaryCall = primaryCall;
    }//_____________________________________________________________________________________________ setPrimaryCall


    public void SaveToNotify(MD_Notify md_notify, Context context) {//______________________________ SaveToNotify

        Realm realm = Realm.getDefaultInstance();

        try {

            int id;
            Number currentIdNum = realm.where(DB_Notification.class).max("Id");
            if (currentIdNum == null) {
                id = 1;
            } else {
                id = currentIdNum.intValue() + 1;
            }

            realm.beginTransaction();
            realm.createObject(DB_Notification.class, id).insert(md_notify);
            realm.commitTransaction();
            getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
            //ShowNotification(context);
        } finally {
            if (realm != null)
                realm.close();
        }


    }//_____________________________________________________________________________________________ SaveToNotify



    public boolean ResponseIsOk(Response response) {//______________________________________________ ResponseIsOk
        if (response.body() == null)
            return false;
        else
            return true;
    }//_____________________________________________________________________________________________ ResponseIsOk


    public String ResponseErrorMessage(Response response) {//_______________________________________ ResponseErrorMessage
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            String message = "";
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message = message + temp.getString("message");
                    message = message + "\n";
                }
            } else {
                message = message + jObjError.getString("message");
            }
            return message;
        } catch (Exception ex) {
            return ex.toString();
        }
    }//_____________________________________________________________________________________________ ResponseErrorMessage




//    private void ShowNotification(Context context) {
//
//        Realm realm = Realm.getDefaultInstance();
//
//        try {
//
//            DB_Notification results = realm.where(DB_Notification.class).findAll().last();
//
//            NotificationManagerClass managerClass = new NotificationManagerClass(
//                    context,
//                    false,
//                    results
//            );
//        } finally {
//            if (realm != null)
//                realm.close();
//        }
//
//    }

    public PublishSubject<Byte> getPublishSubject() {//_____________________________________________ getPublishSubject
        return publishSubject;
    }//_____________________________________________________________________________________________ getPublishSubject


    public String getResponseMessage() {//__________________________________________________________ getResponseMessage
        return ResponseMessage;
    }//_____________________________________________________________________________________________ getResponseMessage

    public void setResponseMessage(String responseMessage) {//______________________________________ setResponseMessage
        ResponseMessage = responseMessage;
    }//_____________________________________________________________________________________________ setResponseMessage
}