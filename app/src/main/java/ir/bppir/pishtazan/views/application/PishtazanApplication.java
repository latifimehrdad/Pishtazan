package ir.bppir.pishtazan.views.application;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.MultiDexApplication;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.realm.DaggerRealmComponent;
import ir.bppir.pishtazan.daggers.realm.RealmComponent;
import ir.bppir.pishtazan.daggers.realm.RealmModul;
import ir.bppir.pishtazan.views.dialogs.DialogMessage;

public class PishtazanApplication extends MultiDexApplication {

    private Context context;
    private RealmComponent realmComponent;

    @Override
    public void onCreate() {//______________________________________________________________________ onCreate
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
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


    public RealmComponent getRealmComponent() {//___________________________________________________ getRealmComponent
        return realmComponent;
    }//_____________________________________________________________________________________________ getRealmComponent



}
