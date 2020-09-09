package ir.bppir.pishtazan.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

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
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.ActivityMainBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.activity.VM_Main;

public class MainActivity extends AppCompatActivity {

    private VM_Main vm_main;
    private NavController navController;
    static final int CAMERA_RESULT = 7126;
    static final int REQUEST_PICK_Gallery = 7127;
    static final int REQUEST_PICK = 7126;
    private Uri mCurrentPhotoPath;
    final int MAX_WIDTH = 1024;
    final int MAX_HEIGHT = 1024;
    public static PublishSubject<Byte> mainPublish;
    private DisposableObserver<Byte> disposableObserver;
    public static String ImageUrl;
    public static Integer startFromNotify = -1;
    public static MainActivity mainActivity;

    @BindView(R.id.ImageViewMenu)
    ImageView ImageViewMenu;

    @BindView(R.id.relativeLayoutToast)
    RelativeLayout relativeLayoutToast;

    @BindView(R.id.textViewToast)
    TextView textViewToast;

    @BindView(R.id.imageViewToast)
    ImageView imageViewToast;


    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFromNotify = getIntent().getIntExtra(getResources().getString(R.string.ML_personId), -1);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm_main = new VM_Main(this);
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


    //______________________________________________________________________________________________ whiteList
    private void whiteList() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isIgnoringBatteryOptimizations = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            if (!isIgnoringBatteryOptimizations) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 3);
            }
        }

    }
    //______________________________________________________________________________________________ whiteList


    //______________________________________________________________________________________________ onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case 0: {
/*                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    WhiteList();
                }*/
            }
            case 3: {
                if (requestCode == 3) {
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    boolean isIgnoringBatteryOptimizations = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
                    }
                    if (isIgnoringBatteryOptimizations) {
                        // Ignoring battery optimization
                    } else {
                        // Not ignoring battery optimization
                    }
                }
            }

        }
    }
    //______________________________________________________________________________________________ onRequestPermissionsResult


    //______________________________________________________________________________________________ openDialogTakePhotos
    public void openDialogTakePhotos(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_show_take_photo);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        TextView TextViewCancel = (TextView) dialog.findViewById(R.id.TextViewCancel);
        RelativeLayout RelativeLayoutCamera = (RelativeLayout) dialog
                .findViewById(R.id.RelativeLayoutCamera);

        LinearLayout LinearLayoutGallery = (LinearLayout) dialog
                .findViewById(R.id.LinearLayoutGallery);

        LinearLayoutGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "انتخاب عکس"), REQUEST_PICK_Gallery);
                dialog.dismiss();
            }
        });

        RelativeLayoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    showCamera();

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
                    }
                }

                dialog.dismiss();
            }
        });

        TextViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
    private Uri getTempUri() {
        // Create an image file name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dt = sdf.format(new Date());
        File imageFile = null;
        imageFile = new File(Environment.getExternalStorageDirectory()
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
    public void showCustomToast(String title, int color, Drawable icon, int tintColor, Context context) {
        relativeLayoutToast.setVisibility(View.GONE);
        textViewToast.setText(title);
        imageViewToast.setImageDrawable(icon);
        imageViewToast.setColorFilter(tintColor);
        relativeLayoutToast.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
        relativeLayoutToast.setVisibility(View.VISIBLE);
        int delay = 1000;
        int titleLength = title.length();
        if (titleLength > 10)
            titleLength = titleLength / 10;

        delay = 1000 * titleLength;
        delay = delay + 1500;

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            relativeLayoutToast.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
            relativeLayoutToast.setVisibility(View.GONE);
        },delay);
    }
    //______________________________________________________________________________________________ showCustomToast


}