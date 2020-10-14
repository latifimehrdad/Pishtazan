package ir.bppir.pishtazan.views.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
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


public class PishtazanApplication extends MultiDexApplication {

    private Context context;
    private ApplicationUtilityComponent applicationUtilityComponent;
    private PersianPickerComponent persianPickerComponent;
    private RetrofitComponent retrofitComponent;
    private ImageLoaderComponent imageLoaderComponent;

    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        Fresco.initialize(context);
        configurationCalligraphy();
        configurationApplicationUtility();
        configurationDataPicker();
        configurationRetrofitComponent();
        configurationRealmComponent();
        configurationImageLoader();

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
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ configurationRetrofitComponent
    private void configurationRetrofitComponent() {
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }
    //______________________________________________________________________________________________ configurationRetrofitComponent


    //______________________________________________________________________________________________ configurationImageLoader
    private void configurationImageLoader() {

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
    }
    //______________________________________________________________________________________________ configurationImageLoader


    //______________________________________________________________________________________________ getApplication
    public static PishtazanApplication getApplication(Context context) {
        return (PishtazanApplication) context.getApplicationContext();
    }
    //______________________________________________________________________________________________ getApplication


    //______________________________________________________________________________________________ configurationCalligraphy
    private void configurationCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
    //______________________________________________________________________________________________ configurationCalligraphy


    //______________________________________________________________________________________________ configurationRealmComponent
    private void configurationRealmComponent() {
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .name("Pishatazan")
                .schemaVersion(1)
                .build());
    }
    //______________________________________________________________________________________________ configurationRealmComponent


    //______________________________________________________________________________________________ configurationApplicationUtility
    private void configurationApplicationUtility() {
        applicationUtilityComponent = DaggerApplicationUtilityComponent.builder().applicationUtilityModul(new ApplicationUtilityModul()).build();
    }
    //______________________________________________________________________________________________ configurationApplicationUtility


    //______________________________________________________________________________________________ configurationDataPicker
    private void configurationDataPicker() {
        persianPickerComponent = DaggerPersianPickerComponent.builder().persianPickerModule(new PersianPickerModule()).build();
    }
    //______________________________________________________________________________________________ configurationDataPicker


    //______________________________________________________________________________________________ getApplicationUtilityComponent
    public ApplicationUtilityComponent getApplicationUtilityComponent() {
        return applicationUtilityComponent;
    }
    //______________________________________________________________________________________________ getApplicationUtilityComponent


    //______________________________________________________________________________________________ getPersianPickerComponent
    public PersianPickerComponent getPersianPickerComponent() {
        return persianPickerComponent;
    }
    //______________________________________________________________________________________________ getPersianPickerComponent


    //______________________________________________________________________________________________ getRetrofitComponent
    public RetrofitComponent getRetrofitComponent() {
        return retrofitComponent;
    }
    //______________________________________________________________________________________________ getRetrofitComponent


    //______________________________________________________________________________________________ getImageLoaderComponent
    public ImageLoaderComponent getImageLoaderComponent() {
        return imageLoaderComponent;
    }
    //______________________________________________________________________________________________ getImageLoaderComponent

}
