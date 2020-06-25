package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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


    //    public List<MD_Person> getPersonList() {//______________________________________________________ getPersonList
//        return personList;
//    }//_____________________________________________________________________________________________ getPersonList
}
