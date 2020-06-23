package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_Panel extends VM_Primary {

    private Context context;
    private List<MD_Person> personList;

    public VM_Panel(Context context) {//____________________________________________________________ VM_Partners
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Partners


    public void GetPerson(Boolean Partner, Byte PersonType) {//_____________________________________ GetPerson
        personList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            personList.add(new MD_Person(i,
                    "نام"+i,
                    "شماره"+i,
                    "شغل"+i,
                    "تولد"+i,
                    null,
                    "آدرس"+i,
                    true,
                    "عکس"+i));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_GetPerson);
            }
        },2000);

    }//_____________________________________________________________________________________________ GetPerson


    public List<MD_Person> getPersonList() {//______________________________________________________ getPersonList
        return personList;
    }//_____________________________________________________________________________________________ getPersonList
}
