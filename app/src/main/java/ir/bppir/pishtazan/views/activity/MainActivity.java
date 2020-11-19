package ir.bppir.pishtazan.views.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.annotations.SerializedName;
import com.yalantis.ucrop.UCrop;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_UserInfo;
import ir.bppir.pishtazan.databinding.ActivityMainBinding;
import ir.bppir.pishtazan.models.MD_UserInfoVM;
import ir.bppir.pishtazan.utility.ApplicationUtility;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.activity.VM_Main;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Splash;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

import static ir.bppir.pishtazan.daggers.retrofit.RetrofitApis.Host;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    static final int CAMERA_RESULT = 7126;
    static final int REQUEST_PICK_Gallery = 7127;
    static final int REQUEST_PICK = 7126;
    private Uri mCurrentPhotoPath;
    public boolean payment;
    final int MAX_WIDTH = 1024;
    final int MAX_HEIGHT = 1024;
    public static PublishSubject<Byte> mainPublish;
    private DisposableObserver<Byte> disposableObserver;
    public static String ImageUrl;
    public static Integer startFromNotify = -1;
    public static MainActivity mainActivity;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean preLogin = false;
    private VM_Main vm_main;
    public MD_UserInfoVM md_userInfoVM;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.ImageViewMenu)
    ImageView ImageViewMenu;

    @BindView(R.id.relativeLayoutToast)
    RelativeLayout relativeLayoutToast;

    @BindView(R.id.textViewToast)
    TextView textViewToast;

    @BindView(R.id.imageViewToast)
    ImageView imageViewToast;

    @BindView(R.id.textViewVersion)
    TextView textViewVersion;

    @BindView(R.id.simpleDraweeViewProfile)
    SimpleDraweeView simpleDraweeViewProfile;

    @BindView(R.id.linearLayoutLogOut)
    LinearLayout linearLayoutLogOut;

    @BindView(R.id.linearLayoutAddAmount)
    LinearLayout linearLayoutAddAmount;

    @BindView(R.id.textViewScore)
    TextView textViewScore;

    @BindView(R.id.textViewPrivileges)
    TextView textViewPrivileges;

    @BindView(R.id.textViewUserName)
    TextView textViewUserName;

    @BindView(R.id.textViewWallet)
    TextView textViewWallet;


    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFromNotify = getIntent().getIntExtra(getResources().getString(R.string.ML_personId), -1);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm_main = new VM_Main();
        binding.setMain(vm_main);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mainPublish = PublishSubject.create();
        setPermission();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        setObserverToObservable();
        relativeLayoutToast.setVisibility(View.GONE);
        MainActivity.mainActivity = this;

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(getResources().getColor(R.color.colorAccent), 3);
        roundingParams.setCornersRadii(30, 30, 0, 0);
        simpleDraweeViewProfile.setActualImageResource(R.drawable.logo_pishtazan);
        simpleDraweeViewProfile.getHierarchy().setRoundingParams(roundingParams);

        PackageInfo pInfo;
        float versionName;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = Float.parseFloat(pInfo.versionName);
            textViewVersion.setText(getResources().getString(R.string.PowerBy) + " V : " + versionName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        setListener();

        payment = getIntent().getBooleanExtra(getString(R.string.ML_Payment), false);

    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable() {

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

        MainActivity.mainPublish
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        runOnUiThread(() -> {
            if (action.equals(StaticValues.ML_PictureDialog))
                openDialogTakePhotos(MainActivity.this);
        });
    }
    //______________________________________________________________________________________________ actionHandler


    //______________________________________________________________________________________________ setPermission
    public void setPermission() {

        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionContact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionPhone != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);

        if (permissionWrite != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionContact != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);

        if (permissionCamera != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.CAMERA);

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    0);
        }

    }
    //______________________________________________________________________________________________ setPermission


    //______________________________________________________________________________________________ attachBaseContext
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    //______________________________________________________________________________________________ attachBaseContext


    //______________________________________________________________________________________________ onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NotNull String[] permissions,
            @NotNull int[] grantResults) {
    }
    //______________________________________________________________________________________________ onRequestPermissionsResult


    //______________________________________________________________________________________________ openDialogTakePhotos
    public void openDialogTakePhotos(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_show_take_photo);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        if (window != null) {
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        TextView TextViewCancel = dialog.findViewById(R.id.TextViewCancel);
        RelativeLayout RelativeLayoutCamera = dialog
                .findViewById(R.id.RelativeLayoutCamera);

        LinearLayout LinearLayoutGallery = dialog
                .findViewById(R.id.LinearLayoutGallery);

        LinearLayoutGallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "انتخاب عکس"), REQUEST_PICK_Gallery);
            dialog.dismiss();
        });

        RelativeLayoutCamera.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showCamera();

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
                }
            }

            dialog.dismiss();
        });

        TextViewCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    //______________________________________________________________________________________________ openDialogTakePhotos


    //______________________________________________________________________________________________ showCamera
    private void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        startActivityForResult(cameraIntent, REQUEST_PICK);
    }
    //______________________________________________________________________________________________ showCamera


    //______________________________________________________________________________________________ getTempUri
    @SuppressLint("SimpleDateFormat")
    private Uri getTempUri() {
        // Create an image file name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dt = sdf.format(new Date());
        File imageFile = new File(Environment.getExternalStorageDirectory()
                + "/MyApp/", "Camera_" + dt + ".jpg");

        File file = new File(Environment.getExternalStorageDirectory() + "/MyApp");
        if (!file.exists()) {
            file.mkdir();
        }
//        String imagePath = Environment.getExternalStorageDirectory() + "/MyApp/"
//                + "Camera_" + dt + ".jpg";
        Uri imageUri;// = Uri.fromFile(imageFile);

        imageUri = FileProvider.getUriForFile(
                MainActivity.this,
                "ir.bppir.pishtazan.provider", //(use your app signature + ".provider" )
                imageFile);

        mCurrentPhotoPath = imageUri;

        return imageUri;
    }
    //______________________________________________________________________________________________ getTempUri


    //______________________________________________________________________________________________ onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_PICK) {
            Uri uri;
            if (data == null)
                uri = mCurrentPhotoPath;
            else
                uri = data.getData();
            beginCrop(uri);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_PICK_Gallery) {
            Uri uri = data.getData();
            beginCrop(uri);
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            Uri uri = UCrop.getOutput(data);
            if (uri != null)
                ImageUrl = uri.toString();
            mainPublish.onNext(StaticValues.ML_PictureDialogGetImage);
//            ImageViewMenu.setImageURI(Uri.parse(new File(MainActivity.ImageUrl).toString()));
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    //______________________________________________________________________________________________ onActivityResult


    //______________________________________________________________________________________________ beginCrop
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getExternalCacheDir(), "cropped.jpg"));
        UCrop.of(source, destination)
                .withMaxResultSize(MAX_WIDTH, MAX_HEIGHT)
                .withAspectRatio(1, 1)
                .start(MainActivity.this);
    }
    //______________________________________________________________________________________________ beginCrop


    //______________________________________________________________________________________________ showCustomToast
    public void showCustomToast(String title, Drawable icon, int tintColor, Context context) {
        relativeLayoutToast.setVisibility(View.GONE);
        textViewToast.setText(title);
        imageViewToast.setImageDrawable(icon);
        imageViewToast.setColorFilter(tintColor);
        relativeLayoutToast.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
        relativeLayoutToast.setVisibility(View.VISIBLE);
        int delay;
        int titleLength = title.length();
        if (titleLength > 10)
            titleLength = titleLength / 10;

        delay = 1000 * titleLength;
        delay = delay + 1500;

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            relativeLayoutToast.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
            relativeLayoutToast.setVisibility(View.GONE);
        }, delay);
    }
    //______________________________________________________________________________________________ showCustomToast


    //______________________________________________________________________________________________ onBackPressed
    @Override
    public void onBackPressed() {

        NavDestination navDestination = navController.getCurrentDestination();
        if (navDestination != null)
            if (navDestination.getLabel() != null) {
                String fragment = navDestination.getLabel().toString();
                if ((!fragment.equalsIgnoreCase("Home"))) {
                    super.onBackPressed();
                    return;
                }
            }

        if (VM_Splash.logOut) {
            super.onBackPressed();
            return;
        }


        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }
    //______________________________________________________________________________________________ onBackPressed


    //______________________________________________________________________________________________ setListener
    @SuppressLint("RtlHardcoded")
    private void setListener() {

        linearLayoutAddAmount.setOnClickListener(v -> {
            drawer_layout.closeDrawer(Gravity.RIGHT, true);
            navController.navigate(R.id.gotoPayment);
        });

        linearLayoutLogOut.setOnClickListener(v -> {
            deleteUserAndLogOut();
        });

        ImageViewMenu.setOnClickListener(v -> {
            drawer_layout.openDrawer(Gravity.RIGHT, true);
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            String fragment = "";
            if (destination.getLabel() != null)
                fragment = destination.getLabel().toString();

            if ((fragment.equalsIgnoreCase("Splash")) ||
                    (fragment.equalsIgnoreCase("SignUp")) ||
                    (fragment.equalsIgnoreCase("Verify")) ||
                    (fragment.equalsIgnoreCase("AppUpdate")) ||
                    (fragment.equalsIgnoreCase("Payment"))) {
                if (!preLogin) {
                    ImageViewMenu.setVisibility(View.GONE);
                    preLogin = true;
                    lockDrawer();
                }

            } else {
                if (preLogin) {
                    ImageViewMenu.setVisibility(View.VISIBLE);
                    unLockDrawer();
                    preLogin = false;
                }
            }

        });
    }
    //______________________________________________________________________________________________ setListener


    //______________________________________________________________________________________________ checkLogin
    public void deleteUserAndLogOut() {

        try {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<DB_UserInfo> userInfo = realm.where(DB_UserInfo.class).findAll();
            realm.beginTransaction();
            userInfo.deleteAllFromRealm();
            realm.commitTransaction();
        } finally {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent mStartActivity = new Intent(this, MainActivity.class);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                int mPendingIntentId = 7126;
                PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) MainActivity.this.getSystemService(this.ALARM_SERVICE);
                if (mgr != null)
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            }, 1000);
        }
    }
    //______________________________________________________________________________________________ checkLogin


    //______________________________________________________________________________________________ unLockDrawer
    public void unLockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        textViewScore.setText(getResources().getString(R.string.totalScore) + System.lineSeparator() + md_userInfoVM.getScores());
        textViewPrivileges.setText(getResources().getString(R.string.totalPrivileges) + System.lineSeparator() + md_userInfoVM.getPrivileges());
        if (md_userInfoVM.getName() != null)
            textViewUserName.setText(md_userInfoVM.getName());

        ApplicationUtility utility = PishtazanApplication.getApplication(this)
                .getApplicationUtilityComponent()
                .getApplicationUtility();

        Long walet = Math.round(md_userInfoVM.getWallet());
        textViewWallet.setText(utility.splitNumberOfAmount(walet) + " " + getResources().getString(R.string.toman));

        if (md_userInfoVM.getImage() != null && !md_userInfoVM.getImage().isEmpty()) {
            String url = Host + md_userInfoVM.getImage();
            simpleDraweeViewProfile.setImageURI(url);
        }
    }
    //______________________________________________________________________________________________ unLockDrawer


    //______________________________________________________________________________________________ lockDrawer
    public void lockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    //______________________________________________________________________________________________ lockDrawer


}