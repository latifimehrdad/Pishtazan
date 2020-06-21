package ir.bppir.pishtazan.views.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.bppir.pishtazan.R;

public class PishtazanApplication extends MultiDexApplication {

    private Context context;

    @Override
    public void onCreate() {//______________________________________________________________________ Start onCreate()
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
//        ConfigrationRetrofitComponent();
//        ConfigrationRealmComponent();
//        ConfigurationImageLoader();
    }//_____________________________________________________________________________________________ End onCreate()


    private void ConfigurationCalligraphy() {//_____________________________________________________ Start ConfigurationCalligraphy()
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }//_____________________________________________________________________________________________ End ConfigurationCalligraphy()





}
