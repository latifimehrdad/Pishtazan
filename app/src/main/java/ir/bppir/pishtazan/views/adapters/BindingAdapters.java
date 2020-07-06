package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

public class BindingAdapters {

    @BindingAdapter(value = "SetDegreePersonImage")
    public static void SetDegreePersonImage(CircleImageView imageView, Integer degree) {//__________ SetDegreePersonImage
        Context context = imageView.getContext();
        if (degree.byteValue() == StaticValues.DegreeNormal){
            imageView.setImageResource(R.drawable.normal_icon);
            return;
        }

        if (degree.byteValue() == StaticValues.DegreePeach){
            imageView.setImageResource(R.drawable.peach_icon);
            return;
        }

        if (degree.byteValue() == StaticValues.DegreeGiant){
            imageView.setImageResource(R.drawable.giant_icon);
            return;
        }
    }//_____________________________________________________________________________________________ SetDegreePersonImage




    @BindingAdapter(value = "SetPersonImage")
    public static void SetPersonImage(CircleImageView imageView, String url) {//____________________ SetPersonImage

        ImageLoader imageLoader = PishtazanApplication
                .getApplication(imageView.getContext())
                .getImageLoaderComponent()
                .getImageLoader();


        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage == null)
                    imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                imageView.setImageResource(R.drawable.image_before_load);
            }
        });

    }//_____________________________________________________________________________________________ SetPersonImage



}
