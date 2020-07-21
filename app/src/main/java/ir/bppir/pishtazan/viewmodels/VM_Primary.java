package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;
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
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Response;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;
    private Activity context;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary


    public void CancelRequest() {//_________________________________________________________________ CancelRequest
        if (PrimaryCall != null) {
            PrimaryCall.cancel();
            PrimaryCall = null;
        }
    }//_____________________________________________________________________________________________ CancelRequest


    public Call getPrimaryCall() {//________________________________________________________________ getPrimaryCall
        return PrimaryCall;
    }//_____________________________________________________________________________________________ getPrimaryCall

    public void setPrimaryCall(Call primaryCall) {//________________________________________________ setPrimaryCall
        CancelRequest();
        PrimaryCall = primaryCall;
    }//_____________________________________________________________________________________________ setPrimaryCall


    public void SaveToNotify(MD_Notify md_notify) {//_______________________________________________ SaveToNotify
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
//            ShowNotification(context);
        } finally {
            if (realm != null)
                realm.close();
        }


    }//_____________________________________________________________________________________________ SaveToNotify



    public boolean ResponseIsOk(Response response) {//______________________________________________ ResponseIsOk
        if (response.body() == null) {
            setResponseMessage(ResponseErrorMessage(response));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            return false;
        } else
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
                message = message + jObjError.getString("Message");
            }
            return message;
        } catch (Exception ex) {
            return ex.toString();
        }
    }//_____________________________________________________________________________________________ ResponseErrorMessage



    public void CallIsFailure() {//_________________________________________________________________ CallIsFailure

        if (getPrimaryCall() == null) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_RequestCancel);
            return;
        } else {
            if (getPrimaryCall().isCanceled()) {
                setResponseMessage("");
                SendMessageToObservable(StaticValues.ML_RequestCancel);
            } else {
                setResponseMessage(getContext().getResources().getString(R.string.RequestFailure));
                SendMessageToObservable(StaticValues.ML_ResponseFailure);
            }
        }

    }//_____________________________________________________________________________________________ CallIsFailure



    public DB_UserInfo GetUserInfo() {//____________________________________________________________ GetUserInfo

        Realm realm = Realm.getDefaultInstance();
        DB_UserInfo db_userInfo = realm.where(DB_UserInfo.class).findFirst();
        realm.close();
        return db_userInfo;

    }//_____________________________________________________________________________________________ GetUserInfo



    public Integer GetUserId() {//__________________________________________________________________ GetUserId

        DB_UserInfo db_userInfo = GetUserInfo();
        if (db_userInfo == null)
            return 0;
        else
            return db_userInfo.getId();
    }//_____________________________________________________________________________________________ GetUserId



    public void UserIsNotAuthorization() {//________________________________________________________ GetUserId
        setResponseMessage(getContext().getResources().getString(R.string.UserIsNotAuthorization));
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<DB_UserInfo> userInfos = realm.where(DB_UserInfo.class).findAll();
            realm.beginTransaction();
            userInfos.deleteAllFromRealm();
            realm.commitTransaction();
        } finally {
            realm.close();
            getPublishSubject().onNext(StaticValues.ML_UserIsNotAuthorization);
        }
    }//_____________________________________________________________________________________________ GetUserId

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

    public Activity getContext() {//________________________________________________________________ getContext
        return context;
    }//_____________________________________________________________________________________________ getContext

    public void setContext(Activity context) {//____________________________________________________ setContext
        this.context = context;
    }//_____________________________________________________________________________________________ setContext


    public void SendMessageToObservable(Byte action) {//____________________________________________ SendMessageToObservable
        Handler handler = new Handler();
        handler.postDelayed(() -> publishSubject.onNext(action),200);

    }//_____________________________________________________________________________________________ SendMessageToObservable

}