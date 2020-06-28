package ir.bppir.pishtazan.viewmodels;

import android.content.Context;

import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
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

        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();

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

    }//_____________________________________________________________________________________________ SaveToNotify


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