package ir.bppir.pishtazan.views.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Panel;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RememberAgain extends AppCompatActivity {

    private Integer PersonId;
    private Byte NotifyType;
    private Byte PersonType;
    private Long longDate;
    private String stringDate;
    private String stringTime;
    private VM_Panel vm_panel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);
        vm_panel = new VM_Panel(this);
        PersonId = getIntent().getExtras().getInt(getString(R.string.ML_personId), 0);
        NotifyType = getIntent().getByteExtra(getString(R.string.ML_Type),(byte)0);
        PersonType = getIntent().getByteExtra(getString(R.string.ML_PanelType),(byte)0);
        TimePicker TimePickerReminder = (TimePicker)
                findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = (TextView)
                findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(view -> {

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
        });



        LinearLayout LinearLayoutCancel = (LinearLayout)
                findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(view -> finish());


        LinearLayout LinearLayoutSave = (LinearLayout)
                findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(view -> {
            if (longDate == 0) {
                TextViewChooseDate.setBackground(RememberAgain.this.getResources().getDrawable(R.drawable.dw_edit_empty_background));
                return;
            }
            StringBuilder sb1 = new StringBuilder();
            sb1.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
            sb1.append(":");
            sb1.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
            stringTime = sb1.toString();
            if (NotifyType.equals(StaticValues.Call))
                SaveCallReminder();
            else
                SaveMeetingReminder();
        });



    }


    private void SaveMeetingReminder() {//__________________________________________________________ SaveMeetingReminder
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.SaveCustomerReminder(StaticValues.Meeting, null, stringDate, stringTime, "", 0);
        else
            vm_panel.SaveColleagueReminder(StaticValues.Meeting, null, stringDate, stringTime, "", 0);
    }//_____________________________________________________________________________________________ SaveMeetingReminder



    private void SaveCallReminder() {//_____________________________________________________________ SaveCallReminder
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.SaveCustomerReminder(StaticValues.Call, null, stringDate, stringTime,"", PersonId);
        else
            vm_panel.SaveColleagueReminder(StaticValues.Call, null, stringDate, stringTime, "", PersonId);
    }//_____________________________________________________________________________________________ SaveCallReminder


}
