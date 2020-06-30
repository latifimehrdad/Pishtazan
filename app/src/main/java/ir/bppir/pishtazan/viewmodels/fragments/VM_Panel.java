package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Panel extends VM_Primary {

    private Context context;
    private List<DB_Persons> personList;

    public VM_Panel(Context context) {//____________________________________________________________ VM_Partners
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Partners


    public void GetPerson(int panelType, Byte PersonType) {//_____________________________________ GetPerson

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<DB_Persons> db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("panelType", panelType)
                    .and()
                    .equalTo("PersonType", PersonType)
                    .findAll();

            personList = new ArrayList<>();
            personList.addAll(realm.copyFromRealm(db_persons));
        } finally {
            if (realm != null)
                realm.close();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_GetPerson);
            }
        }, 1000);

    }//_____________________________________________________________________________________________ GetPerson


    public List<DB_Persons> getPersonList() {//_____________________________________________________ getPersonList
        return personList;
    }//_____________________________________________________________________________________________ getPersonList


    public void SaveCallReminder(
            Integer Position,
            Long longDate,
            String stringDate,
            Long longTime,
            String stringTime) {//__________________________________________________________________ SaveCallReminder

        if (personList.get(Position).getPersonType() == 0)
            setResponseMessage(context.getResources().getString(R.string.SaveCallReminderAndConvert));
        else
            setResponseMessage(context.getResources().getString(R.string.SaveCallReminder));

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            DB_Persons db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("Id", personList.get(Position).getId())
                    .findAll().first();
            if (db_persons != null)
                db_persons.setPersonType(StaticValues.ML_Possible);
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }

        MD_Notify md_notify = new MD_Notify(
                StaticValues.Call,
                personList.get(Position).getPersonType(),
                stringDate,
                longDate,
                stringTime,
                longTime,
                null,
                personList.get(Position).getName(),
                personList.get(Position).getPhoneNumber());
        SaveToNotify(md_notify, context);

    }//_____________________________________________________________________________________________ SaveCallReminder


    public void SaveMeetingReminder(
            Integer Position,
            Long longDate,
            String stringDate,
            Long longTime,
            String stringTime) {//______________________________ ____________________________________ SaveMeetingReminder

        if (personList.get(Position).getPersonType() == 0)
            setResponseMessage(context.getResources().getString(R.string.SaveMeetingReminderAndConvert));
        else
            setResponseMessage(context.getResources().getString(R.string.SaveMeetingReminder));

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            DB_Persons db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("Id", personList.get(Position).getId())
                    .findAll().first();
            if (db_persons != null)
                db_persons.setPersonType(StaticValues.ML_Possible);
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }


        MD_Notify md_notify = new MD_Notify(
                StaticValues.Meeting,
                personList.get(Position).getPersonType(),
                stringDate,
                longDate,
                stringTime,
                longTime,
                null,
                personList.get(Position).getName(),
                personList.get(Position).getPhoneNumber());
        SaveToNotify(md_notify, context);
    }//_____________________________________________________________________________________________ SaveMeetingReminder


    public void DeletePerson(Integer Position) {//__________________________________________________ DeletePerson
        setResponseMessage(context.getResources().getString(R.string.SuccessDeletePerson));
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            DB_Persons db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("Id", personList.get(Position).getId())
                    .findAll().first();
            if (db_persons != null)
                db_persons.deleteFromRealm();
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }
        getPublishSubject().onNext(StaticValues.ML_DeletePerson);
    }//_____________________________________________________________________________________________ DeletePerson


    public void MoveToPossible(Integer Position) {//________________________________________________ MoveToPossible
        setResponseMessage(context.getResources().getString(R.string.SuccessForMoveToPossible));
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            DB_Persons db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("Id", personList.get(Position).getId())
                    .findAll().first();
            if (db_persons != null)
                db_persons.setPersonType(StaticValues.ML_Possible);
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }
        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
    }//_____________________________________________________________________________________________ MoveToPossible


    public void MoveToCertain(Integer Position) {//________________________________________________ MoveToPossible
        setResponseMessage(context.getResources().getString(R.string.SuccessForMoveToCertain));
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            DB_Persons db_persons = realm
                    .where(DB_Persons.class)
                    .equalTo("Id", personList.get(Position).getId())
                    .findAll().first();
            if (db_persons != null)
                db_persons.setPersonType(StaticValues.ML_Certain);
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }
        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
    }//_____________________________________________________________________________________________ MoveToPossible


}
