package ir.bppir.pishtazan.views.adapters;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.bppir.pishtazan.R;

public class BindingAdapters {

    @BindingAdapter(value = "SetDegreePersonImage")
    public static void SetHistoryStateImage(CircleImageView imageView, Byte degree) {

        Context context = imageView.getContext();
        switch (degree) {
            case 1:
                //imageView.setColorFilter(ContextCompat.getColor(context, R.color.ML_Harmony_Yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setImageResource(R.drawable.ic_baseline_account_circle);
                break;

            case 2:
                //imageView.setColorFilter(ContextCompat.getColor(context, R.color.ML_HARMONY), android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setImageResource(R.drawable.peach_icon);
                break;

            case 3:
                //imageView.setColorFilter(ContextCompat.getColor(context, R.color.ML_HARMONY), android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setImageResource(R.drawable.giant_icon);
                break;

        }
    }
}
