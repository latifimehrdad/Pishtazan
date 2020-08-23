package ir.bppir.pishtazan.views.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cunoraz.gifview.library.GifView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.models.MR_PersonNumber;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Panel;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RememberAgain extends AppCompatActivity {

    private Integer PersonId;
    private Byte NotifyType;
    private Byte PersonType;
    private Long longDate;
    private String stringDate;
    private String stringTime;
    private VM_Panel vm_panel;
    private DisposableObserver<Byte> disposableObserver;
    private LinearLayout LinearLayoutReminderAgain;
    private LinearLayout LinearLayoutCalling;
    private ImageView imgProgress;
    private GifView ProgressGif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);

        LinearLayoutReminderAgain = (LinearLayout)
                findViewById(R.id.LinearLayoutReminderAgain);

        LinearLayoutCalling = (LinearLayout)
                findViewById(R.id.LinearLayoutCalling);


        vm_panel = new VM_Panel(this);
        PersonId = getIntent().getExtras().getInt(getString(R.string.ML_personId), 0);
        NotifyType = getIntent().getByteExtra(getString(R.string.ML_Type), (byte) 0);
        if (NotifyType.equals(StaticValues.Calling)) {
            calling();
        } else {
            reminderAgain();
        }


    }


    //______________________________________________________________________________________________ calling
    private void calling() {
        LinearLayoutReminderAgain.setVisibility(View.GONE);
        LinearLayoutCalling.setVisibility(View.VISIBLE);
        Integer Id = getIntent().getExtras().getInt(getString(R.string.ML_Id), 0);

        RelativeLayout RelativeLayoutGetNumber = (RelativeLayout)
                findViewById(R.id.RelativeLayoutGetNumber);


        imgProgress= (ImageView)
                findViewById(R.id.imgProgress);

        ProgressGif = (GifView) findViewById(R.id.ProgressGif);

        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

        RelativeLayoutGetNumber.setOnClickListener(v -> {
            ProgressGif.setVisibility(View.VISIBLE);
            imgProgress.setVisibility(View.GONE);
            getPersonNumber(PersonId, Id);
        });

    }
    //______________________________________________________________________________________________ calling


    //______________________________________________________________________________________________ reminderLater
    private void reminderAgain() {

        LinearLayoutReminderAgain.setVisibility(View.VISIBLE);
        LinearLayoutCalling.setVisibility(View.GONE);

        PersonType = getIntent().getByteExtra(getString(R.string.ML_PanelType), (byte) 0);

        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        SetObserverToObservable(vm_panel.getPublishSubject());

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
            LinearLayoutSave.setAlpha(0.5f);
            if (NotifyType.equals(StaticValues.Call))
                SaveCallReminder();
            else
                SaveMeetingReminder();
        });


    }
    //______________________________________________________________________________________________ reminderLater


    //______________________________________________________________________________________________ getPersonNumber
    private void getPersonNumber(Integer personId, Integer Id) {

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(RememberAgain.this)
                .getRetrofitComponent();


        retrofitComponent
                .getRetrofitApiInterface()
                .GET_MOBILE_NUMBER(Id, personId)
                .enqueue(new Callback<MR_PersonNumber>() {
                    @Override
                    public void onResponse(Call<MR_PersonNumber> call, Response<MR_PersonNumber> response) {
                        if (response.body() != null) {
                            if (response.body().getStatue() == 1)
                                callPerson(response.body().getMobileNumber());
                        }
                    }

                    @Override
                    public void onFailure(Call<MR_PersonNumber> call, Throwable t) {
                        ProgressGif.setVisibility(View.VISIBLE);
                        imgProgress.setVisibility(View.GONE);
                    }
                });

    }
    //______________________________________________________________________________________________ getPersonNumber



    //______________________________________________________________________________________________ callPerson
    private void callPerson(String PhoneNumber) {
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + PhoneNumber));
        startActivity(call);
        finish();
    }
    //______________________________________________________________________________________________ callPerson



    private void SaveMeetingReminder() {//__________________________________________________________ SaveMeetingReminder
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.saveCustomerReminder(StaticValues.Meeting, null, stringDate, stringTime, "", PersonId);
        else
            vm_panel.saveColleagueReminder(StaticValues.Meeting, null, stringDate, stringTime, "", PersonId);
    }//_____________________________________________________________________________________________ SaveMeetingReminder


    private void SaveCallReminder() {//_____________________________________________________________ SaveCallReminder
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.saveCustomerReminder(StaticValues.Call, null, stringDate, stringTime, "", PersonId);
        else
            vm_panel.saveColleagueReminder(StaticValues.Call, null, stringDate, stringTime, "", PersonId);
    }//_____________________________________________________________________________________________ SaveCallReminder


    public void SetObserverToObservable(PublishSubject<Byte> publishSubject) {//____________________ SetObserverToObservable

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                actionHandler(aByte);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        publishSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }//_____________________________________________________________________________________________ SetObserverToObservable


    private void actionHandler(Byte action) {//_____________________________________________________ actionHandler
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (action == StaticValues.ML_SaveReminder) {
                    if (disposableObserver != null)
                        disposableObserver.dispose();
                    disposableObserver = null;
                    finish();
                }
            }
        });
    }//_____________________________________________________________________________________________ actionHandler


}
