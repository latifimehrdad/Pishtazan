package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_AddPerson extends VM_Primary {

    private List<MD_Contact> md_contacts;
    private List<MD_Contact> md_contactsFilter;

    //______________________________________________________________________________________________ VM_AddPerson
    public VM_AddPerson(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_AddPerson


    //______________________________________________________________________________________________ addPerson
    public void addPerson(String Name, String Phone, Byte Degree, int panelType) {

        if (panelType == StaticValues.Customer)
            addCustomer(Name, Phone, Degree);
        else if (panelType == StaticValues.Colleague)
            addColleague(Name, Phone, Degree);
    }
    //______________________________________________________________________________________________ addPerson


    //______________________________________________________________________________________________ addCustomer
    private void addCustomer(String name, String phone, Byte degree) {

        phone = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(phone);

        Integer ColleagueId = getColleagueId();
        if (ColleagueId == 0) {
            userIsNotAuthorization();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("FullName", name);
        params.put("ColleagueId", ColleagueId.toString());
        params.put("MobileNumber", phone);
        params.put("Level", degree.toString());


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .ADD_CUSTOMER(params));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_AddPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ addCustomer


    //______________________________________________________________________________________________ addColleague
    private void addColleague(String name, String phone, Byte degree) {

        phone = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(phone);

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("FullName", name);
        params.put("UserInfoId", UserInfoId.toString());
        params.put("MobileNumber", phone);
        params.put("Level", degree.toString());


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_COLLEAGUE(params));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_AddPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ addColleague


    //______________________________________________________________________________________________ getContact
    public void getContact() {
        if (md_contacts == null) {
            md_contacts = new ArrayList<>();
            ContentResolver cr = getContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
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
                            String temp = phoneNo.substring(0, 3);
                            if (temp.equals("+98")) {
                                phoneNo = "0" + phoneNo.substring(3);
                            }
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

        if (md_contacts != null && md_contacts.size() > 0) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_GetContact);
        } else {
            setResponseMessage(getContext().getResources().getString(R.string.ContactIsEmpty));
            getPublishSubject().onNext(StaticValues.ML_Error);
        }


    }
    //______________________________________________________________________________________________ getContact


    //______________________________________________________________________________________________ filterContact
    public void filterContact(String text) {
        if (text == null || text.length() == 0) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_GetContact);
        } else {

            text = PishtazanApplication
                    .getApplication(getContext())
                    .getApplicationUtilityComponent()
                    .getApplicationUtility()
                    .PersianToEnglish(text);

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
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_GetContactFilter);
        }

    }
    //______________________________________________________________________________________________ filterContact


    //______________________________________________________________________________________________ getMd_contacts
    public List<MD_Contact> getMd_contacts() {
        return md_contacts;
    }
    //______________________________________________________________________________________________ getMd_contacts


    //______________________________________________________________________________________________ getMd_contactsFilter
    public List<MD_Contact> getMd_contactsFilter() {
        return md_contactsFilter;
    }
    //______________________________________________________________________________________________ getMd_contactsFilter

}
