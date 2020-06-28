package ir.bppir.pishtazan.views.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityModul;
import ir.bppir.pishtazan.daggers.applicationutility.DaggerApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.datepicker.DaggerPersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.daggers.realm.DaggerRealmComponent;
import ir.bppir.pishtazan.daggers.realm.RealmComponent;
import ir.bppir.pishtazan.daggers.realm.RealmModul;

public class PishtazanApplication extends MultiDexApplication {

    private Context context;
    private RealmComponent realmComponent;
    private ApplicationUtilityComponent applicationUtilityComponent;
    private PersianPickerComponent persianPickerComponent;

    @Override
    public void onCreate() {//______________________________________________________________________ onCreate
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
        ConfigurationApplicationUtility();
        ConfigurationDataPicker();
//        ConfigrationRetrofitComponent();
        ConfigurationRealmComponent();
//        ConfigurationImageLoader();
    }//_____________________________________________________________________________________________ onCreate


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
                .schemaVersion(1).build());
        realmComponent = DaggerRealmComponent
                .builder()
                .realmModul(new RealmModul())
                .build();
    }//_____________________________________________________________________________________________ ConfigurationRealmComponent


    private void ConfigurationApplicationUtility() {//______________________________________________ ConfigurationApplicationUtility
        applicationUtilityComponent = DaggerApplicationUtilityComponent.builder().applicationUtilityModul(new ApplicationUtilityModul()).build();
    }//_____________________________________________________________________________________________ ConfigurationApplicationUtility


    private void ConfigurationDataPicker() {//______________________________________________________ ConfigurationDataPicker
        persianPickerComponent = DaggerPersianPickerComponent.builder().persianPickerModule(new PersianPickerModule()).build();
    }//_____________________________________________________________________________________________ ConfigurationDataPicker


    public RealmComponent getRealmComponent() {//___________________________________________________ getRealmComponent
        return realmComponent;
    }//_____________________________________________________________________________________________ getRealmComponent


    public ApplicationUtilityComponent getApplicationUtilityComponent() {//_________________________ getApplicationUtilityComponent
        return applicationUtilityComponent;
    }//_____________________________________________________________________________________________ getApplicationUtilityComponent

    public PersianPickerComponent getPersianPickerComponent() {//___________________________________ getPersianPickerComponent
        return persianPickerComponent;
    }//_____________________________________________________________________________________________ getPersianPickerComponent
}
