package ir.bppir.pishtazan.views.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.database.DB_Notification;
import ir.bppir.pishtazan.models.MD_Notify;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RememberAgain extends AppCompatActivity {

    private Integer Id;
    private Long longDate;
    private String stringDate;
    private Long longTime;
    private String stringTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reminder);
        Id = getIntent().getExtras().getInt(getString(R.string.ML_Id), 0);

        TimePicker TimePickerReminder = (TimePicker)
                findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = (TextView)
                findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianPickerModule.context = RememberAgain.this;
                PersianDatePickerDialog persianCalendar = PishtazanApplication
                        .getApplication(RememberAgain.this)
                        .getPersianPickerComponent()
                        .getPersianDatePickerDialog();

                persianCalendar.setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(persianCalendar.getPersianYear());
                        sb1.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb1.append(String.format("%02d", persianCalendar.getPersianDay()));
                        longDate = Long.valueOf(sb1.toString());

                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(persianCalendar.getPersianYear());
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                        sb2.append("/");
                        sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                        stringDate = sb2.toString();
                        TextViewChooseDate.setText(stringDate);
                        TextViewChooseDate.setBackground(RememberAgain.this.getResources().getDrawable(R.drawable.dw_edit_back));
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
                persianCalendar.show();
            }
        });



        LinearLayout LinearLayoutCancel = (LinearLayout)
                findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayout LinearLayoutSave = (LinearLayout)
                findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (longDate == 0) {
                    TextViewChooseDate.setBackground(RememberAgain.this.getResources().getDrawable(R.drawable.dw_edit_empty_background));
                    return;
                }
                StringBuilder sb1 = new StringBuilder();
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb1.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                longTime = Long.valueOf(sb1.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
                sb2.append(":");
                sb2.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
                stringTime = sb2.toString();
                SaveCallReminder(Id);
            }
        });



    }


    private void SaveCallReminder(Integer Id) {//___________________________________________________ SaveCallReminder
        SaveCallReminder(Id, longDate, stringDate, longTime, stringTime);
    }//_____________________________________________________________________________________________ SaveCallReminder


    public void SaveCallReminder(
            Integer Id,
            Long longDate,
            String stringDate,
            Long longTime,
            String stringTime) {//__________________________________________________________________ SaveCallReminder

        Realm realm = Realm.getDefaultInstance();
        DB_Notification notification = realm
                .where(DB_Notification.class)
                .equalTo("Id", Id)
                .findFirst();

        if (notification != null) {

            MD_Notify md_notify = new MD_Notify(
                    StaticValues.Call,
                    notification.getPersonType(),
                    stringDate,
                    longDate,
                    stringTime,
                    longTime,
                    null,
                    notification.getPersonName(),
                    notification.getPhoneNumber());
            SaveToNotify(md_notify);
        }

    }//_____________________________________________________________________________________________ SaveCallReminder



    private void SaveToNotify(MD_Notify md_notify) {//______________________________________________ SaveToNotify
        Realm realm = Realm.getDefaultInstance();
        try {
            int id;
            Number currentIdNum = realm.where(DB_Notification.class).max("Id");
            if (currentIdNum == null) {
                id = 1;
            } else {
                id = currentIdNum.intValue() + 1;
            }
            realm.beginTransaction();
            realm.createObject(DB_Notification.class, id).insert(md_notify);
            realm.commitTransaction();
        } finally {
            if (realm != null)
                realm.close();
        }

    }//_____________________________________________________________________________________________ SaveToNotify



}
