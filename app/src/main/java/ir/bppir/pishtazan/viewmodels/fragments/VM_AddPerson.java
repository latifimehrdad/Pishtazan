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

    public VM_AddPerson(Activity context) {//_______________________________________________________ VM_AddPerson
        setContext(context);
    }//_____________________________________________________________________________________________ VM_AddPerson


    public void AddPerson(String Name, String Phone, Byte Degree, int panelType) {//______________ AddPerson

        if (panelType == StaticValues.Customer)
            AddCustomer(Name, Phone, Degree);
        else if (panelType == StaticValues.Colleague)
            AddColleague(Name, Phone, Degree);

//        Phone = PishtazanApplication
//                .getApplication(context)
//                .getApplicationUtilityComponent()
//                .getApplicationUtility()
//                .PersianToEnglish(Phone);
//
//        Realm realm = Realm.getDefaultInstance();
//
//        try {
//            realm.beginTransaction();
//
//            int id;
//            Number currentIdNum = realm.where(DB_Persons.class).max("Id");
//            if (currentIdNum == null) {
//                id = 1;
//            } else {
//                id = currentIdNum.intValue() + 1;
//            }
//
//            realm
//                    .createObject(DB_Persons.class, id)
//                    .insert(Name,
//                            Phone,
//                            "",
//                            "",
//                            0.0,
//                            0.0,
//                            "",
//                            true,
//                            "",
//                            Degree,
//                            panelType,
//                            (byte) 0);
//            realm.commitTransaction();
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setResponseMessage(context.getResources().getString(R.string.AddPersonOk));
//                    getPublishSubject().onNext(StaticValues.ML_AddPerson);
//                }
//            }, 2000);
//
//        } finally {
//            if (realm != null)
//                realm.close();
//        }

//        catch (Exception e) {
//            realm.cancelTransaction();
//            setResponseMessage(context.getResources().getString(R.string.ErrorForSave));
//            getPublishSubject().onNext(StaticValues.ML_Error);
//        }

    }//_____________________________________________________________________________________________ AddPerson




    private void AddCustomer(String Name, String Phone, Byte Degree) {//____________________________ AddCustomer

        Phone = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(Phone);

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("FullName" , Name);
        params.put("UserInfoId" , UserInfoId.toString());
        params.put("MobileNumber" , Phone);
        params.put("Level" , Degree.toString());


        setPrimaryCall(PishtazanApplication
        .getApplication(getContext())
        .getRetrofitComponent()
        .getRetrofitApiInterface()
        .ADD_CUSTOMER(params));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_AddPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ AddCustomer



    private void AddColleague(String Name, String Phone, Byte Degree){//____________________________ AddColleague

        Phone = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(Phone);

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("FullName" , Name);
        params.put("UserInfoId" , UserInfoId.toString());
        params.put("MobileNumber" , Phone);
        params.put("Level" , Degree.toString());


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_COLLEAGUE(params));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_AddPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ AddColleague




    public void GetContact() {//____________________________________________________________________ GetContact
        if (md_contacts == null) {
            md_contacts = new ArrayList<>();
            ContentResolver cr = getContext().getContentResolver();
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
                            String temp = phoneNo.substring(0,3);
                            if (temp.equals("+98")) {
                                phoneNo = "0" + phoneNo.substring(3,phoneNo.length());
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


    }//_____________________________________________________________________________________________ GetContact


    public void FilterContact(String text) {//______________________________________________________ FilterContact
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


    }//_____________________________________________________________________________________________ FilterContact


    public List<MD_Contact> getMd_contacts() {//____________________________________________________ getMd_contacts
        return md_contacts;
    }//_____________________________________________________________________________________________ getMd_contacts


    public List<MD_Contact> getMd_contactsFilter() {//______________________________________________ getMd_contactsFilter
        return md_contactsFilter;
    }//_____________________________________________________________________________________________ getMd_contactsFilter
}
