package ir.bppir.pishtazan.daggers.datepicker;

import android.content.Context;

import java.util.Calendar;

import dagger.Module;
import dagger.Provides;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

@Module
public class PersianPickerModule {

    public static Context context;

    @Provides
    public PersianDatePickerDialog getPersianDatePickerDialog() {
        return new PersianDatePickerDialog(context)
                .setPositiveButtonString(context.getString(R.string.ChooseDate))
                .setNegativeButton(context.getString(R.string.Cancel))
                .setTodayButton(context.getString(R.string.TodayDate))
                .setTodayButtonVisible(true)
                .setInitDate(getPersianCalendar())
                .setMaxYear(-1)
                .setMinYear(1300)
                .setActionTextColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .setTitleColor(context.getResources().getColor(R.color.colorPrimary))
                .setPickerBackgroundColor(context.getResources().getColor(R.color.ML_White))
                .setBackgroundColor(context.getResources().getColor(R.color.ML_White));
    }

    @Provides
    public PersianCalendar getPersianCalendar() {
        String ChangeDate = PishtazanApplication
                .getApplication(context)
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .MiladiToJalali(Calendar.getInstance().getTime(), "FullJalaliNumber")
                .replaceAll("/", "");
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(Integer.valueOf(ChangeDate.substring(0, 4)).intValue(), Integer.valueOf(ChangeDate.substring(4, 6)).intValue(), Integer.valueOf(ChangeDate.substring(6, 8)).intValue());
        return initDate;
    }
}