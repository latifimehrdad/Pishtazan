package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.models.MD_Reminder;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.models.MR_Reminder;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Reminder extends VM_Primary {


    private List<MD_Reminder> md_reminders;

    //______________________________________________________________________________________________ VM_Reminder
    public VM_Reminder(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Reminder



    //______________________________________________________________________________________________ getAllReminder
    public void getAllReminder(Integer id, Integer reminderTypes, Byte relationType) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .getAllReminders(id,UserInfoId, reminderTypes, relationType, 0));

        getPrimaryCall().enqueue(new Callback<MR_Reminder>() {
            @Override
            public void onResponse(Call<MR_Reminder> call, Response<MR_Reminder> response) {
                if (responseIsOk(response)) {
                    //setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        setResponseMessage("");
                        md_reminders = response.body().getReminders();
                        getPublishSubject().onNext(StaticValues.ML_Reminder);
                    } else {
                        setResponseMessage(response.body().getMessage());
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Reminder> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getAllReminder


    //______________________________________________________________________________________________ getMd_reminders
    public List<MD_Reminder> getMd_reminders() {
        if (md_reminders == null)
            md_reminders = new ArrayList<>();
        return md_reminders;
    }
    //______________________________________________________________________________________________ getMd_reminders




    //______________________________________________________________________________________________ saveCustomerReminder
    public void saveCustomerReminder(
            Byte ReminderTypes,
            String stringDate,
            String stringTime,
            String Name,
            Integer PersonId) {


        stringDate = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(stringDate);


        stringTime = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(stringTime);

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_REMINDER_CUSTOMER(
                        stringDate,
                        stringTime,
                        Name,
                        StaticValues.Customer,
                        ReminderTypes,
                        0,
                        PersonId,
                        stringDate,
                        Name
                ));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        getPublishSubject().onNext(StaticValues.ML_SaveReminder);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ saveCustomerReminder


    //______________________________________________________________________________________________ saveColleagueReminder
    public void saveColleagueReminder(
            Byte ReminderTypes,
            String stringDate,
            String stringTime,
            String Name,
            Integer PersonId) {


        stringDate = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(stringDate);


        stringTime = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(stringTime);


        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_REMINDER_COLLEAGUE(
                        stringDate,
                        stringTime,
                        Name,
                        StaticValues.Colleague,
                        ReminderTypes,
                        0,
                        PersonId,
                        stringDate,
                        Name
                ));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        getPublishSubject().onNext(StaticValues.ML_SaveReminder);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ saveColleagueReminder



    //______________________________________________________________________________________________ deleteReminder
    public void deleteReminder(Integer id){

        Integer userId = getUserId();
        if (userId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .deleteReminders(id, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_DeleteReminder);
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
    //______________________________________________________________________________________________ deleteReminder



}
