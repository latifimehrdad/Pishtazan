package ir.bppir.pishtazan.viewmodels;

import android.content.Context;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.background.NotificationManagerClass;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary


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
            ShowNotification(context);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 2000);
        } finally {
            if (realm != null)
                realm.close();
        }

//        catch (Exception e) {
//            realm.cancelTransaction();
//            setResponseMessage(context.getResources().getString(R.string.ErrorForSave));
//            getPublishSubject().onNext(StaticValues.ML_Error);
//        }


    }//_____________________________________________________________________________________________ SaveToNotify


    private void ShowNotification(Context context) {

        Realm realm = Realm.getDefaultInstance();

        try {

            DB_Notification results = realm.where(DB_Notification.class).findAll().last();

            NotificationManagerClass managerClass = new NotificationManagerClass(
                    context,
                    false,
                    results
            );
        } finally {
            if (realm != null)
                realm.close();
        }

    }

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