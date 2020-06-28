package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.realm.RealmComponent;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class VM_Panel extends VM_Primary {

    private Context context;
//    private List<MD_Person> personList;
    private RealmResults<DB_Persons> db_persons;

    public VM_Panel(Context context) {//____________________________________________________________ VM_Partners
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Partners


    public void GetPerson(Boolean Partner, Byte PersonType) {//_____________________________________ GetPerson

        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();

        db_persons = realm
                .where(DB_Persons.class)
                .equalTo("Partner",Partner)
                .and()
                .equalTo("PersonType", PersonType)
                .findAll();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_GetPerson);
            }
        },500);

    }//_____________________________________________________________________________________________ GetPerson

    public RealmResults<DB_Persons> getDb_persons() {//_____________________________________________ getDb_persons
        return db_persons;
    }//_____________________________________________________________________________________________ getDb_persons



    public void SaveCallReminder(Integer Position) {//______________________________________________ SaveCallReminder

        if (db_persons.get(Position).getPersonType() == 0)
            setResponseMessage(context.getResources().getString(R.string.SaveCallReminderAndConvert));
        else
            setResponseMessage(context.getResources().getString(R.string.SaveCallReminder));

        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();
        realm.beginTransaction();
        db_persons.get(Position).setPersonType(StaticValues.ML_Possible);
        realm.commitTransaction();

        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
    }//_____________________________________________________________________________________________ SaveCallReminder


    public void SaveMeetingReminder(Integer Position) {//___________________________________________ SaveMeetingReminder

        if (db_persons.get(Position).getPersonType() == 0)
            setResponseMessage(context.getResources().getString(R.string.SaveMeetingReminderAndConvert));
        else
            setResponseMessage(context.getResources().getString(R.string.SaveMeetingReminder));

        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();
        realm.beginTransaction();
        db_persons.get(Position).setPersonType(StaticValues.ML_Possible);
        realm.commitTransaction();

        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
    }//_____________________________________________________________________________________________ SaveMeetingReminder



    public void DeletePerson(Integer Position) {//__________________________________________________ DeletePerson
        setResponseMessage(context.getResources().getString(R.string.SuccessDeletePerson));
        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();
        realm.beginTransaction();
        db_persons.get(Position).deleteFromRealm();
        realm.commitTransaction();
        getPublishSubject().onNext(StaticValues.ML_DeletePerson);
    }//_____________________________________________________________________________________________ DeletePerson



    //    public List<MD_Person> getPersonList() {//______________________________________________________ getPersonList
//        return personList;
//    }//_____________________________________________________________________________________________ getPersonList
}
