package ir.bppir.pishtazan.views.adapters;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.StaticValues;

public class BindingAdapters {

    @BindingAdapter(value = "SetDegreePersonImage")
    public static void SetHistoryStateImage(CircleImageView imageView, Integer degree) {
        Context context = imageView.getContext();
        if (degree.byteValue() == StaticValues.DegreeNormal){
            imageView.setImageResource(R.drawable.ic_baseline_account_circle);
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
    }
}
