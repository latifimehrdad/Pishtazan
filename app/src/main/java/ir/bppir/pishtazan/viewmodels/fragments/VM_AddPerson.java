package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class VM_AddPerson extends VM_Primary {

    private Context context;
    private List<MD_Contact> md_contacts;
    private List<MD_Contact> md_contactsFilter;

    public VM_AddPerson(Context context) {//________________________________________________________ VM_AddPerson
        this.context = context;
    }//_____________________________________________________________________________________________ VM_AddPerson


    public void AddPerson(String Name, String Phone, Byte Degree, boolean Partner) {//______________ AddPerson

        Realm realm = PishtazanApplication
                .getApplication(context)
                .getRealmComponent()
                .getRealm();
        try {
            realm.beginTransaction();
            realm
                    .createObject(DB_Persons.class)
                    .insert(0,
                            Name,
                            Phone,
                            "",
                            "",
                            0.0,
                            0.0,
                            "",
                            true,
                            "",
                            Degree,
                            Partner,
                            (byte) 0);
            realm.commitTransaction();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setResponseMessage(context.getResources().getString(R.string.AddPersonOk));
                    getPublishSubject().onNext(StaticValues.ML_AddPerson);
                }
            },1000);

        } catch (Exception e) {
            realm.cancelTransaction();
            setResponseMessage(context.getResources().getString(R.string.ErrorForSave));
            getPublishSubject().onNext(StaticValues.ML_Error);
        }

    }//_____________________________________________________________________________________________ AddPerson


    public void GetContact() {//____________________________________________________________________ GetContact
        if (md_contacts == null) {
            md_contacts = new ArrayList<>();
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNo = phoneNo.replaceAll("-", "");
                            md_contacts.add(new MD_Contact(name, phoneNo));
                        }
                        pCur.close();
                    }
                }

            }
            if (cur != null) {
                cur.close();
            }
        }

        if (md_contacts != null && md_contacts.size() > 0)
            getPublishSubject().onNext(StaticValues.ML_GetContact);
        else {
            setResponseMessage(context.getResources().getString(R.string.ContactIsEmpty));
            getPublishSubject().onNext(StaticValues.ML_Error);
        }


    }//_____________________________________________________________________________________________ GetContact


    public void FilterContact(String text) {//______________________________________________________ FilterContact
        if (text == null || text.length() == 0) {
            getPublishSubject().onNext(StaticValues.ML_GetContact);
        } else {
            if (md_contactsFilter == null)
                md_contactsFilter = new ArrayList<>();
            else
                md_contactsFilter.clear();

            for (MD_Contact contact : md_contacts) {
                String name = contact.getName();
                String Phone = contact.getPhone();
                if (name.toLowerCase().contains(text.toLowerCase()))
                    md_contactsFilter.add(contact);
                else if (Phone.toLowerCase().contains(text.toLowerCase()))
                    md_contactsFilter.add(contact);
            }
            getPublishSubject().onNext(StaticValues.ML_GetContactFilter);
        }


    }//_____________________________________________________________________________________________ FilterContact


    public List<MD_Contact> getMd_contacts() {//____________________________________________________ getMd_contacts
        return md_contacts;
    }//_____________________________________________________________________________________________ getMd_contacts


    public List<MD_Contact> getMd_contactsFilter() {//______________________________________________ getMd_contactsFilter
        return md_contactsFilter;
    }//_____________________________________________________________________________________________ getMd_contactsFilter
}
