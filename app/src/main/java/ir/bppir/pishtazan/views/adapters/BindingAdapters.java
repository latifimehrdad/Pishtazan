package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_ExamResultDetail;
import ir.bppir.pishtazan.utility.ApplicationUtility;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.application.PishtazanApplication;

import static ir.bppir.pishtazan.daggers.retrofit.RetrofitApis.Host;

public class BindingAdapters {



    @BindingAdapter(value = "SetLayoutBackExamResult")
    public static void SetLayoutBackExamResult(LinearLayout linearLayout, MD_ExamResultDetail resultDetail){

        if (resultDetail.getUserAnswer() == resultDetail.getQuestion().getCorrectAnswer())
            linearLayout.setBackground(linearLayout.getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else
            linearLayout.setBackground(linearLayout.getContext().getResources().getDrawable(R.drawable.dw_back_exam_result_wrong));
    }


    @BindingAdapter(value = "SetYourAnswer")
    public static void SetYourAnswer(TextView textView, MD_ExamResultDetail resultDetail){
        switch (resultDetail.getUserAnswer()) {
            case 1:
                textView.setText(resultDetail.getQuestion().getFirstChoose());
                break;
            case 2:
                textView.setText(resultDetail.getQuestion().getSecondChoose());
                break;
            case 3:
                textView.setText(resultDetail.getQuestion().getThirdChoose());
                break;
            case 4:
                textView.setText(resultDetail.getQuestion().getForthChoose());
                break;
        }
    }


    @BindingAdapter(value = "SetCorrectAnswer")
    public static void SetCorrectAnswer(TextView textView, MD_ExamResultDetail resultDetail){
        switch (resultDetail.getQuestion().getCorrectAnswer()) {
            case 1:
                textView.setText(resultDetail.getQuestion().getFirstChoose());
                break;
            case 2:
                textView.setText(resultDetail.getQuestion().getSecondChoose());
                break;
            case 3:
                textView.setText(resultDetail.getQuestion().getThirdChoose());
                break;
            case 4:
                textView.setText(resultDetail.getQuestion().getForthChoose());
                break;
        }
    }


    @BindingAdapter(value = "SetTextViewFloat")
    public static void SetTextViewFloat(TextView textView, float value) {//___________________________ SetTextViewText

        String v = String.format("%.2f", value);
        textView.setText(v);

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = "SetTextViewDouble")
    public static void SetTextViewDouble(TextView textView, double value) {//___________________________ SetTextViewText

        String v = String.format("%.1f", value);
        textView.setText(v);

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = {"CorrectAnswerCount", "WrongAnswerCount", "NotAnswered"})
    public static void SetTextViewExamResultTotal(
            TextView textView,
            Integer CorrectAnswerCount,
            Integer WrongAnswerCount,
            Integer NotAnswered) {//_________________ SetTextViewText

        Integer total;

        if (CorrectAnswerCount != null && WrongAnswerCount != null && NotAnswered != null)
            total = CorrectAnswerCount + WrongAnswerCount + NotAnswered;
        else
            total = 0;
        textView.setText(total.toString());

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = {"TextRadioButton", "userAnswerRadioButton", "radioButtonTag"})
    public static void SetValueForRadioButton(RadioButton radioButton, String title, Byte userAnswer, Integer radioTag) {
        radioButton.setText(title);
        if (userAnswer == null) {
            radioButton.setChecked(false);
            return;
        }

        if (userAnswer == radioTag.byteValue())
            radioButton.setChecked(true);
        else
            radioButton.setChecked(false);
    }


    @BindingAdapter(value = "SetTextViewLevel")
    public static void SetTextViewLevel(TextView textView, Integer level) {//_______________________ SetTextViewLevel

        Context context = textView.getContext();
        textView.setText(context.getResources().getString(R.string.Level) + " " + level.toString());

    }//_____________________________________________________________________________________________ SetTextViewLevel


    @BindingAdapter(value = "SetTextViewText")
    public static void SetTextViewText(TextView textView, String text) {//__________________________ SetTextViewText

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        if (text == null || text.equalsIgnoreCase("null"))
            text = "";
        switch (tag) {
            case "date":
                textView.setText(context.getResources().getString(R.string.Date) + " : " + text);
                break;
            case "title":
                textView.setText(context.getResources().getString(R.string.Title) + " : " + text);
                break;

            case "description":
                textView.setText(context.getResources().getString(R.string.Description) + " : " + text);
                break;
            case "movieTime":
                textView.setText(context.getResources().getString(R.string.MovieTime) + " : " + text);
                break;
            case "type" :
                textView.setText(context.getResources().getString(R.string.Type) + " : " + text);
                break;
            case "insured" :
                textView.setText(context.getResources().getString(R.string.insuredName) + " : " + text);
                break;
            case "insuredNationalCode":
                textView.setText(context.getResources().getString(R.string.NationalCode) + " : " + text);
                break;
            case "insuranceNum":
                if (text == null || text.equalsIgnoreCase("") || text.equalsIgnoreCase("null"))
                    textView.setVisibility(View.GONE);
                else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(context.getResources().getString(R.string.NationalCode) + " : " + text);
                }
                break;
        }

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = "SetTextViewInteger")
    public static void SetTextViewTextInteger(TextView textView, Integer value) {//_________________ SetTextViewText

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        switch (tag) {
            case "movieTime":
                Integer h = value % 60;
                Integer min = value / 60;
                textView.setText(context.getResources().getString(R.string.MovieTime) + "  " + String.format("%02d", min) + ":" + String.format("%02d", h));
                break;
            default:
                if (value != null)
                    textView.setText(value.toString());
                else
                    textView.setText("0");
                break;
        }

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = "SetTextViewLong")
    public static void SetTextViewText(TextView textView, Long value) {//___________________________ SetTextViewText

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        switch (tag) {
            case "amount":
                ApplicationUtility utility = PishtazanApplication.getApplication(context)
                        .getApplicationUtilityComponent()
                        .getApplicationUtility();


                textView.setText(context.getResources().getString(R.string.Amount) + " : " + utility.splitNumberOfAmount(value));
                break;
        }

    }//_____________________________________________________________________________________________ SetTextViewText


    @BindingAdapter(value = "SetDegreePersonImage")
    public static void SetDegreePersonImage(CircleImageView imageView, Integer degree) {//__________ SetDegreePersonImage
        Context context = imageView.getContext();
        if (degree.byteValue() == StaticValues.DegreeNormal) {
            imageView.setImageResource(R.drawable.normal_icon);
            return;
        }

        if (degree.byteValue() == StaticValues.DegreePeach) {
            imageView.setImageResource(R.drawable.peach_icon);
            return;
        }

        if (degree.byteValue() == StaticValues.DegreeGiant) {
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

        url = Host + url;

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


    @BindingAdapter(value = "SetPersonImageReport")
    public static void SetPersonImageReport(CircleImageView imageView, String url) {//____________________ SetPersonImage

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
