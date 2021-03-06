package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import androidx.databinding.BaseObservable;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Response;

public class VM_Primary extends BaseObservable {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;
    private Activity context;

    //______________________________________________________________________________________________ VM_Primary
    public VM_Primary() {
        publishSubject = PublishSubject.create();
    }
    //______________________________________________________________________________________________ VM_Primary


    //______________________________________________________________________________________________ cancelRequest
    public void cancelRequest() {
        if (PrimaryCall != null) {
            PrimaryCall.cancel();
            PrimaryCall = null;
        }
    }
    //______________________________________________________________________________________________ cancelRequest


    //______________________________________________________________________________________________ getPrimaryCall
    public Call getPrimaryCall() {
        return PrimaryCall;
    }
    //______________________________________________________________________________________________ getPrimaryCall


    //______________________________________________________________________________________________ setPrimaryCall
    public void setPrimaryCall(Call primaryCall) {
        cancelRequest();
        if (isInternetConnected()) {
            setResponseMessage("");
            PrimaryCall = primaryCall;
        } else {
            setResponseMessage(getContext().getResources().getString(R.string.InternetNotAvailable));
            PrimaryCall = null;
            sendMessageToObservable(StaticValues.ML_InternetAccessFailed);
        }
    }
    //______________________________________________________________________________________________ setPrimaryCall


    //______________________________________________________________________________________________ responseIsOk
    public boolean responseIsOk(Response response) {
        if (response.body() == null) {
            setResponseMessage(responseErrorMessage(response));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            return false;
        } else
            return true;
    }
    //______________________________________________________________________________________________ responseIsOk


    //______________________________________________________________________________________________ responseErrorMessage
    public String responseErrorMessage(Response response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            StringBuilder message = new StringBuilder();
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message.append(temp.getString("message"));
                    message.append("\n");
                }
            } else {
                message.append(jObjError.getString("Message"));
            }
            return message.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    //______________________________________________________________________________________________ responseErrorMessage


    //______________________________________________________________________________________________ getResponseMessages
    public String getResponseMessages(Response<MR_Primary> response) {
        StringBuilder result = new StringBuilder();
        if (response.body().getMessages() != null && response.body().getMessages().size() > 0)
            for (String message : response.body().getMessages())
                result.append(message).append(System.getProperty("line.separator"));
        else
            result = new StringBuilder(response.body().getMessage());

        return result.toString();
    }
    //______________________________________________________________________________________________ getResponseMessages


    //______________________________________________________________________________________________ callIsFailure
    public void callIsFailure() {

        if (getPrimaryCall() == null) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_RequestCancel);
        } else {
            if (getPrimaryCall().isCanceled()) {
                setResponseMessage("");
                sendMessageToObservable(StaticValues.ML_RequestCancel);
            } else {
                setResponseMessage(getContext().getResources().getString(R.string.RequestFailure));
                sendMessageToObservable(StaticValues.ML_ResponseFailure);
            }
        }

    }
    //______________________________________________________________________________________________ callIsFailure


    //______________________________________________________________________________________________ getUserInfo
    public DB_UserInfo getUserInfo() {
        Realm realm = Realm.getDefaultInstance();
        DB_UserInfo db_userInfo = realm.where(DB_UserInfo.class).findFirst();
        realm.close();
        return db_userInfo;
    }
    //______________________________________________________________________________________________ getUserInfo


    //______________________________________________________________________________________________ getUserId
    public Integer getUserId() {

        DB_UserInfo db_userInfo = getUserInfo();
        if (db_userInfo == null)
            return 0;
        else
            return db_userInfo.getId();
    }
    //______________________________________________________________________________________________ getUserId



    //______________________________________________________________________________________________ getColleagueId
    public Integer getColleagueId() {

        DB_UserInfo db_userInfo = getUserInfo();
        if (db_userInfo == null)
            return 0;
        else
            return db_userInfo.getColleagueId();
    }
    //______________________________________________________________________________________________ getColleagueId




    //______________________________________________________________________________________________ userIsNotAuthorization
    public void userIsNotAuthorization() {
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
    }
    //______________________________________________________________________________________________ userIsNotAuthorization


    //______________________________________________________________________________________________ getPublishSubject
    public PublishSubject<Byte> getPublishSubject() {
        return publishSubject;
    }
    //______________________________________________________________________________________________ getPublishSubject


    //______________________________________________________________________________________________ getResponseMessage
    public String getResponseMessage() {
        return ResponseMessage;
    }
    //______________________________________________________________________________________________ getResponseMessage


    //______________________________________________________________________________________________ setResponseMessage
    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }
    //______________________________________________________________________________________________ setResponseMessage


    //______________________________________________________________________________________________ getContext
    public Activity getContext() {
        return context;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ setContext
    public void setContext(Activity context) {
        this.context = context;
    }
    //______________________________________________________________________________________________ setContext


    //______________________________________________________________________________________________ sendMessageToObservable
    public void sendMessageToObservable(Byte action) {
        Handler handler = new Handler();
        handler.postDelayed(() -> publishSubject.onNext(action), 500);

    }
    //______________________________________________________________________________________________ sendMessageToObservable


    //______________________________________________________________________________________________ getFirebaseToken
    public String getFirebaseToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }
    //______________________________________________________________________________________________ getFirebaseToken


    //______________________________________________________________________________________________ isInternetConnected
    public boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null)
            return false;
        return activeNetwork.isConnectedOrConnecting();
    }
    //______________________________________________________________________________________________ isInternetConnected


}