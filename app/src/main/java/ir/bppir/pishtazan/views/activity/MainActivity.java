package ir.bppir.pishtazan.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.ActivityMainBinding;
import ir.bppir.pishtazan.viewmodels.activity.VM_Main;

public class MainActivity extends AppCompatActivity {

    private VM_Main vm_main;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ onCreate
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm_main = new VM_Main(this);
        binding.setMain(vm_main);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        SetPermission();
    }//_____________________________________________________________________________________________ onCreate



    public void SetPermission() {//_________________________________________________________________ Start SetPermission

        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionContact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionPhone != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);

        if (permissionWrite != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionContact != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    0);
        }

    }//_____________________________________________________________________________________________ End SetPermission



    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext



    private void WhiteList(){//_____________________________________________________________________ WhiteList
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isIgnoringBatteryOptimizations = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            if(!isIgnoringBatteryOptimizations){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 3);
            }
        }

    }//_____________________________________________________________________________________________ WhiteList


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {//_________________________________________________________________ Start onRequestPermissionsResult
        switch (requestCode) {
            case 0: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    WhiteList();
                }
            }
            case 3:{
                if (requestCode == 3) {
                    PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
                    boolean isIgnoringBatteryOptimizations = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
                    }
                    if(isIgnoringBatteryOptimizations){
                        // Ignoring battery optimization
                    }else{
                        // Not ignoring battery optimization
                    }
                }
            }

        }
    }//_____________________________________________________________________________________________ End onRequestPermissionsResult


}