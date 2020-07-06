package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;

import java.util.List;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.models.MD_RequestGetAllPerson;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Panel extends VM_Primary {

    private List<MD_Person> personList;

    public VM_Panel(Context context) {//____________________________________________________________ VM_Panel
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Panel


    public void GetPerson(int panelType, Byte PersonType) {//_______________________________________ GetPerson

        CancelRequest();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (panelType == StaticValues.Customer)
                    GetAllCustomers(PersonType);
                else
                    GetAllColleagues(PersonType);
            }
        }, 200);


//        Realm realm = Realm.getDefaultInstance();
//        try {
//            RealmResults<DB_Persons> db_persons = realm
//                    .where(DB_Persons.class)
//                    .equalTo("panelType", panelType)
//                    .and()
//                    .equalTo("PersonType", PersonType)
//                    .findAll();
//
//            personList = new ArrayList<>();
//            personList.addAll(realm.copyFromRealm(db_persons));
//        } finally {
//            if (realm != null)
//                realm.close();
//        }
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getPublishSubject().onNext(StaticValues.ML_GetPerson);
//            }
//        }, 1000);

    }//_____________________________________________________________________________________________ GetPerson


    private void GetAllCustomers(Byte PersonType) {//_______________________________________________ GetAllCustomers

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ALL_CUSTOMERS(UserInfoId, PersonType, false));

        getPrimaryCall().enqueue(new Callback<MD_RequestGetAllPerson>() {
            @Override
            public void onResponse(Call<MD_RequestGetAllPerson> call, Response<MD_RequestGetAllPerson> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        personList = response.body().getCustomers();
                        getPublishSubject().onNext(StaticValues.ML_GetPerson);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestGetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });


    }//_____________________________________________________________________________________________ GetAllCustomers


    private void GetAllColleagues(Byte PersonType) {//______________________________________________ GetAllColleagues

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ALL_COLLEAGUES(UserInfoId, PersonType, false));

        getPrimaryCall().enqueue(new Callback<MD_RequestGetAllPerson>() {
            @Override
            public void onResponse(Call<MD_RequestGetAllPerson> call, Response<MD_RequestGetAllPerson> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        personList = response.body().getColleagues();
                        getPublishSubject().onNext(StaticValues.ML_GetPerson);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestGetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetAllColleagues


    public List<MD_Person> getPersonList() {//______________________________________________________ getPersonList
        return personList;
    }//_____________________________________________________________________________________________ getPersonList





/*
    public void SaveCallReminder(
            Integer Position,
            Long longDate,
            String stringDate,
            Long longTime,
            String stringTime) {//__________________________________________________________________ SaveCallReminder

        if (personList.get(Position).getPersonType() == 0)
            setResponseMessage(getContext().getResources().getString(R.string.SaveCallReminderAndConvert));
        else
            setResponseMessage(getContext().getResources().getString(R.string.SaveCallReminder));

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
        SaveToNotify(md_notify);

    }//_____________________________________________________________________________________________ SaveCallReminder


    public void SaveMeetingReminder(
            Integer Position,
            Long longDate,
            String stringDate,
            Long longTime,
            String stringTime) {//______________________________ ____________________________________ SaveMeetingReminder

        if (personList.get(Position).getPersonType() == 0)
            setResponseMessage(getContext().getResources().getString(R.string.SaveMeetingReminderAndConvert));
        else
            setResponseMessage(getContext().getResources().getString(R.string.SaveMeetingReminder));

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
        SaveToNotify(md_notify);
    }//_____________________________________________________________________________________________ SaveMeetingReminder
*/

    public void DeletePerson(Integer Position) {//__________________________________________________ DeletePerson
        setResponseMessage(getContext().getResources().getString(R.string.SuccessDeletePerson));
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
        setResponseMessage(getContext().getResources().getString(R.string.SuccessForMoveToPossible));
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
        setResponseMessage(getContext().getResources().getString(R.string.SuccessForMoveToCertain));
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
