package ir.bppir.pishtazan.views.activity;

import android.annotation.SuppressLint;
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
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
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


    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);

        LinearLayoutReminderAgain = findViewById(R.id.LinearLayoutReminderAgain);

        LinearLayoutCalling = findViewById(R.id.LinearLayoutCalling);


        vm_panel = new VM_Panel(this);
        PersonId = getIntent().getExtras().getInt(getString(R.string.ML_personId), 0);
        NotifyType = getIntent().getByteExtra(getString(R.string.ML_Type), (byte) 0);
        if (NotifyType.equals(StaticValues.Calling)) {
            calling();
        } else {
            reminderAgain();
        }

    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ calling
    private void calling() {
        LinearLayoutReminderAgain.setVisibility(View.GONE);
        LinearLayoutCalling.setVisibility(View.VISIBLE);
        Integer Id = getIntent().getExtras().getInt(getString(R.string.ML_Id), 0);

        RelativeLayout RelativeLayoutGetNumber = findViewById(R.id.RelativeLayoutGetNumber);


        imgProgress = findViewById(R.id.imgProgress);

        ProgressGif = findViewById(R.id.ProgressGif);

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
    @SuppressLint("DefaultLocale")
    private void reminderAgain() {

        LinearLayoutReminderAgain.setVisibility(View.VISIBLE);
        LinearLayoutCalling.setVisibility(View.GONE);

        PersonType = getIntent().getByteExtra(getString(R.string.ML_PanelType), (byte) 0);

        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        setObserverToObservable(vm_panel.getPublishSubject());

        TimePicker TimePickerReminder = findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(view -> {

            PersianPickerModule.context = RememberAgain.this;
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(RememberAgain.this)
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    String sb1 = persianCalendar.getPersianYear() +
                            String.format("%02d", persianCalendar.getPersianMonth()) +
                            String.format("%02d", persianCalendar.getPersianDay());
                    longDate = Long.valueOf(sb1);

                    stringDate = persianCalendar.getPersianYear() +
                            "/" +
                            String.format("%02d", persianCalendar.getPersianMonth()) +
                            "/" +
                            String.format("%02d", persianCalendar.getPersianDay());
                    TextViewChooseDate.setText(stringDate);
                    TextViewChooseDate.setBackground(RememberAgain.this.getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();
        });


        LinearLayout LinearLayoutCancel = findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(view -> finish());


        LinearLayout LinearLayoutSave = findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(view -> {
            if (longDate == 0) {
                TextViewChooseDate.setBackground(RememberAgain.this.getResources().getDrawable(R.drawable.dw_edit_empty_background));
                return;
            }
            stringTime = String.format("%02d", TimePickerReminder.getCurrentHour()) +
                    ":" +
                    String.format("%02d", TimePickerReminder.getCurrentMinute());
            LinearLayoutSave.setAlpha(0.5f);
            if (NotifyType.equals(StaticValues.Call))
                saveCallReminder();
            else
                saveMeetingReminder();
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


    //______________________________________________________________________________________________ saveMeetingReminder
    private void saveMeetingReminder() {
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.saveCustomerReminder(StaticValues.Meeting, null, stringDate, stringTime, "", PersonId);
        else
            vm_panel.saveColleagueReminder(StaticValues.Meeting, null, stringDate, stringTime, "", PersonId);
    }
    //______________________________________________________________________________________________ saveMeetingReminder


    //______________________________________________________________________________________________ saveCallReminder
    private void saveCallReminder() {
        if (PersonType.equals(StaticValues.Customer))
            vm_panel.saveCustomerReminder(StaticValues.Call, null, stringDate, stringTime, "", PersonId);
        else
            vm_panel.saveColleagueReminder(StaticValues.Call, null, stringDate, stringTime, "", PersonId);
    }
    //______________________________________________________________________________________________ saveCallReminder


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable(PublishSubject<Byte> publishSubject) {

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

    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        runOnUiThread(() -> {

            if (action.equals(StaticValues.ML_SaveReminder)) {
                if (disposableObserver != null)
                    disposableObserver.dispose();
                disposableObserver = null;
                finish();
            }
        });
    }
    //______________________________________________________________________________________________ actionHandler


}
