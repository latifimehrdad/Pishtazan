package ir.bppir.pishtazan.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentReminderBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Reminder;
import ir.bppir.pishtazan.views.adapters.AP_Reminder;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Reminder extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable, AP_Reminder.clickReminderItem {


    private NavController navController;
    private VM_Reminder vm_reminder;
    private Integer personId;
    private Integer reminderType;
    private Byte panelType;
    private Dialog dialog;
    private String stringDate;
    private String stringTime;
    private String fullName;
    private View reminderPositionView;

    @BindView(R.id.gifViewLoading)
    GifView gifViewLoading;

    @BindView(R.id.textViewNothing)
    TextView textViewNothing;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.LinearLayoutAdd)
    LinearLayout LinearLayoutAdd;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_reminder = new VM_Reminder(getActivity());
            FragmentReminderBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_reminder, container, false);
            binding.setReminder(vm_reminder);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            personId = getArguments().getInt(getResources().getString(R.string.ML_personId), 0);
            reminderType = getArguments().getInt(getResources().getString(R.string.ML_Type), StaticValues.RTypeCall);
            panelType = getArguments().getByte(getResources().getString(R.string.ML_PanelType), StaticValues.RTypeCall);
            fullName = getArguments().getString(getResources().getString(R.string.ML_Name), "");

            LinearLayoutAdd.setOnClickListener(v -> showReminderDialog());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                Reminder.this,
                vm_reminder.getPublishSubject(),
                vm_reminder);
        startLoading();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ onStart
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_Reminder)) {
            cancelLoading();
            setAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_SaveReminder)) {
            cancelLoading();
            startLoading();
            return;
        }

        if (action == StaticValues.ML_DeleteReminder) {
            Animation outLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
            reminderPositionView.setAnimation(outLeft);
            reminderPositionView.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> startLoading(), 700);
            return;
        }

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ onStart
    @Override
    public void getActionWhenFailureRequest() {
        cancelLoading();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ startLoading
    private void startLoading() {
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
        textViewNothing.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        gifViewLoading.setVisibility(View.VISIBLE);
        vm_reminder.getAllReminder(personId, reminderType, panelType);
    }
    //______________________________________________________________________________________________ startLoading


    //______________________________________________________________________________________________ cancelLoading
    private void cancelLoading() {
        textViewNothing.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        gifViewLoading.setVisibility(View.GONE);
    }
    //______________________________________________________________________________________________ cancelLoading


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        if (vm_reminder.getMd_reminders().size() == 0) {
            textViewNothing.setVisibility(View.VISIBLE);
        } else {
            AP_Reminder ap_reminder = new AP_Reminder(vm_reminder.getMd_reminders(), Reminder.this);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(ap_reminder);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    //______________________________________________________________________________________________ setAdapter


    //______________________________________________________________________________________________ showReminderDialog
    private void showReminderDialog() {

        if (dialog != null)
            dialog.dismiss();
        dialog = null;

        stringDate = "";
        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reminder);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        ImageView ImageViewSave = (ImageView)
                dialog.findViewById(R.id.ImageViewSave);

        GifView ProgressGif = (GifView)
                dialog.findViewById(R.id.ProgressGif);

        ProgressGif.setVisibility(View.GONE);
        ImageViewSave.setVisibility(View.VISIBLE);

        TimePicker TimePickerReminder = (TimePicker)
                dialog.findViewById(R.id.TimePickerReminder);

        TextView TextViewChooseDate = (TextView)
                dialog.findViewById(R.id.TextViewChooseDate);

        TextViewChooseDate.setOnClickListener(view -> {

            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(persianCalendar.getPersianYear());
                    sb.append("/");
                    sb.append(String.format("%02d", persianCalendar.getPersianMonth()));
                    sb.append("/");
                    sb.append(String.format("%02d", persianCalendar.getPersianDay()));
                    stringDate = sb.toString();
                    TextViewChooseDate.setText(stringDate);
                    TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();
        });

        LinearLayout LinearLayoutCancel = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutCancel);

        LinearLayoutCancel.setOnClickListener(view -> {
            dialog.dismiss();
            dialog = null;
        });

        LinearLayout LinearLayoutSave = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutSave);

        LinearLayoutSave.setOnClickListener(view -> {
            if (stringDate.length() < 8) {
                TextViewChooseDate.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_empty_background));
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%02d", TimePickerReminder.getCurrentHour()));
            sb.append(":");
            sb.append(String.format("%02d", TimePickerReminder.getCurrentMinute()));
            stringTime = sb.toString();
            ProgressGif.setVisibility(View.VISIBLE);
            ImageViewSave.setVisibility(View.GONE);
            if (reminderType == 1)
                SaveCallReminder();
            else
                SaveMeetingReminder();
        });

        dialog.show();

    }
    //______________________________________________________________________________________________ showReminderDialog



    //______________________________________________________________________________________________ SaveCallReminder
    private void SaveCallReminder() {
        if (panelType.equals(StaticValues.Customer))
            vm_reminder.saveCustomerReminder(StaticValues.Call, stringDate, stringTime, fullName, personId);
        else
            vm_reminder.saveColleagueReminder(StaticValues.Call, stringDate, stringTime, fullName, personId);
    }
    //______________________________________________________________________________________________ SaveCallReminder



    //______________________________________________________________________________________________ SaveMeetingReminder
    private void SaveMeetingReminder() {
        if (panelType.equals(StaticValues.Customer))
            vm_reminder.saveCustomerReminder(StaticValues.Meeting, stringDate, stringTime, fullName, personId);
        else
            vm_reminder.saveColleagueReminder(StaticValues.Meeting, stringDate, stringTime, fullName, personId);
    }
    //______________________________________________________________________________________________ SaveMeetingReminder


    //______________________________________________________________________________________________ deleteClick
    @Override
    public void deleteClick(Integer position, View view) {
        ShowDeleteQuestion(position, view);
    }
    //______________________________________________________________________________________________ deleteClick




    //______________________________________________________________________________________________ ShowMeetingReminder
    public void ShowDeleteQuestion(Integer Position, View view) {

        if (dialog != null)
            dialog.dismiss();
        dialog = null;


        dialog = null;
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_question);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        reminderPositionView = view;

        TextView TextViewQuestionTitle = (TextView)
                dialog.findViewById(R.id.TextViewQuestionTitle);

        GifView GifViewQuestion = (GifView) dialog.findViewById(R.id.GifViewQuestion);

        ImageView ImageViewQuestion = (ImageView) dialog.findViewById(R.id.ImageViewQuestion);

        GifViewQuestion.setVisibility(View.GONE);

        ImageViewQuestion.setVisibility(View.VISIBLE);


        StringBuilder sp = new StringBuilder();
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToDeleteIt1));
        sp.append(" ");
        sp.append(getContext().getResources().getString(R.string.AreYouSureYouWantToDeleteIt2));

        TextViewQuestionTitle.setText(sp.toString());

        LinearLayout LinearLayoutNo = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutNo);

        LinearLayoutNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog = null;
            }
        });

        LinearLayout LinearLayoutYes = (LinearLayout)
                dialog.findViewById(R.id.LinearLayoutYes);

        LinearLayoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GifViewQuestion.setVisibility(View.VISIBLE);
                ImageViewQuestion.setVisibility(View.GONE);
                vm_reminder.deleteReminder(vm_reminder.getMd_reminders().get(Position).getId());
            }
        });

        dialog.show();

    }
    //______________________________________________________________________________________________ ShowMeetingReminder



}
