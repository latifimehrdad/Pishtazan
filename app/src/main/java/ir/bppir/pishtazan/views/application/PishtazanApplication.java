package ir.bppir.pishtazan.views.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.Reference;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.firebase.NotificationReceiver;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.applicationutility.ApplicationUtilityModul;
import ir.bppir.pishtazan.daggers.applicationutility.DaggerApplicationUtilityComponent;
import ir.bppir.pishtazan.daggers.datepicker.DaggerPersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerComponent;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.daggers.imageloader.DaggerImageLoaderComponent;
import ir.bppir.pishtazan.daggers.imageloader.ImageLoaderComponent;
import ir.bppir.pishtazan.daggers.imageloader.ImageLoaderModule;
import ir.bppir.pishtazan.daggers.retrofit.DaggerRetrofitComponent;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitModule;
import ir.bppir.pishtazan.utility.SSLCertificateHandler;


public class PishtazanApplication extends MultiDexApplication {

    private Context context;
    private ApplicationUtilityComponent applicationUtilityComponent;
    private PersianPickerComponent persianPickerComponent;
    private RetrofitComponent retrofitComponent;
    private ImageLoaderComponent imageLoaderComponent;

    @Override
    public void onCreate() {//______________________________________________________________________ onCreate
        super.onCreate();
        this.context = getApplicationContext();
/*        setComponentEnabledSetting();*/
        SSLCertificateHandler.nuke();
        ConfigurationCalligraphy();
        ConfigurationApplicationUtility();
        ConfigurationDataPicker();
        ConfigurationRetrofitComponent();
        ConfigurationRealmComponent();
        ConfigurationImageLoader();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(context.getResources().getString(R.string.ML_Ignore));
        intentFilter.addAction(context.getResources().getString(R.string.ML_Calling));
        intentFilter.addAction(context.getResources().getString(R.string.ML_LaterCall));
        intentFilter.addAction(context.getResources().getString(R.string.ML_GoToMeeting));
        intentFilter.addAction(context.getResources().getString(R.string.ML_LaterMeeting));
        intentFilter.addAction(context.getResources().getString(R.string.ML_Certain));
        intentFilter.addAction(context.getResources().getString(R.string.ML_Failed));
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



    private void ConfigurationImageLoader() {//_____________________________________________________ ConfigurationImageLoader

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(3)
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);

        imageLoaderComponent = DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule()).build();
    }//_____________________________________________________________________________________________ ConfigurationImageLoader()



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


    public RetrofitComponent getRetrofitComponent() {//_____________________________________________ getRetrofitComponent
        return retrofitComponent;
    }//_____________________________________________________________________________________________ getRetrofitComponent


    private void setComponentEnabledSetting() {//___________________________________________________ setComponentEnabledSetting
/*        ComponentName receiver = new ComponentName(this, ReceiverLunchAppInBackground.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/
    }//_____________________________________________________________________________________________ setComponentEnabledSetting


    public ImageLoaderComponent getImageLoaderComponent() {//_______________________________________ getImageLoaderComponent
        return imageLoaderComponent;
    }//_____________________________________________________________________________________________ getImageLoaderComponent

}
