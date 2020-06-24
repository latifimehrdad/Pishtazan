package ir.bppir.pishtazan.viewmodels.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_AddPerson extends VM_Primary {

    private Context context;
    private List<MD_Contact> md_contacts;

    public VM_AddPerson(Context context) {//________________________________________________________ VM_AddPerson
        this.context = context;
    }//_____________________________________________________________________________________________ VM_AddPerson


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


    public List<MD_Contact> getMd_contacts() {//____________________________________________________ getMd_contacts
        return md_contacts;
    }//_____________________________________________________________________________________________ getMd_contacts

}
