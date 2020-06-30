package ir.bppir.pishtazan.views.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import androidx.multidex.MultiDexApplication;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.background.NotificationReceiver;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityModul;
import ir.bppir.pishtazan.daggers.applicationutility.DaggerApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.datepicker.DaggerPersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.daggers.retrofit.DaggerRetrofitComponent;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitModule;

public class PishtazanApplication extends MultiDexApplication {

    private Context context;
    private ApplicationUtilityComponent applicationUtilityComponent;
    private PersianPickerComponent persianPickerComponent;
    private RetrofitComponent retrofitComponent;

    @Override
    public void onCreate() {//______________________________________________________________________ onCreate
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
        ConfigurationApplicationUtility();
        ConfigurationDataPicker();
        ConfigurationRetrofitComponent();
        ConfigurationRealmComponent();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(context.getResources().getString(R.string.ML_Ignore));
        intentFilter.addAction(context.getResources().getString(R.string.ML_Calling));
        intentFilter.addAction(context.getResources().getString(R.string.ML_Later));
        BroadcastReceiver noti = new NotificationReceiver();
        registerReceiver(noti, intentFilter);

//        ConfigurationImageLoader();
    }//_____________________________________________________________________________________________ onCreate

    private void ConfigurationRetrofitComponent() {//_______________________________________________ ConfigurationRetrofitComponent
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }//_____________________________________________________________________________________________ ConfigurationRetrofitComponent



    public static PishtazanApplication getApplication(Context context) {//__________________________ getApplication
        return (PishtazanApplication) context.getApplicationContext();
    }//_____________________________________________________________________________________________ getApplication


    private void ConfigurationCalligraphy() {//_____________________________________________________ ConfigurationCalligraphy
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }//_____________________________________________________________________________________________ ConfigurationCalligraphy


    private void ConfigurationRealmComponent() {//__________________________________________________ ConfigurationCalligraphy
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .name("Pishatazan")
                .schemaVersion(1)
                .build());
    }//_____________________________________________________________________________________________ ConfigurationRealmComponent


    private void ConfigurationApplicationUtility() {//______________________________________________ ConfigurationApplicationUtility
        applicationUtilityComponent = DaggerApplicationUtilityComponent.builder().applicationUtilityModul(new ApplicationUtilityModul()).build();
    }//_____________________________________________________________________________________________ ConfigurationApplicationUtility


    private void ConfigurationDataPicker() {//______________________________________________________ ConfigurationDataPicker
        persianPickerComponent = DaggerPersianPickerComponent.builder().persianPickerModule(new PersianPickerModule()).build();
    }//_____________________________________________________________________________________________ ConfigurationDataPicker


    public ApplicationUtilityComponent getApplicationUtilityComponent() {//_________________________ getApplicationUtilityComponent
        return applicationUtilityComponent;
    }//_____________________________________________________________________________________________ getApplicationUtilityComponent

    public PersianPickerComponent getPersianPickerComponent() {//___________________________________ getPersianPickerComponent
        return persianPickerComponent;
    }//_____________________________________________________________________________________________ getPersianPickerComponent


    public RetrofitComponent getRetrofitComponent() {//_____________________________________________ Start getRetrofitComponent
        return retrofitComponent;
    }//_____________________________________________________________________________________________ End getRetrofitComponent

}
