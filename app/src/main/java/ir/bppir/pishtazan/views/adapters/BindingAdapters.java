package ir.bppir.pishtazan.views.adapters;

import android.annotation.SuppressLint;
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


    //______________________________________________________________________________________________ setViewVisible
    @BindingAdapter(value = "setViewVisible")
    public static void setViewVisible(View view, Integer visible) {
        if (visible == 1)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }
    //______________________________________________________________________________________________ setViewVisible


    //______________________________________________________________________________________________ setLayoutBackExamResult
    @BindingAdapter(value = "setLayoutBackExamResult")
    public static void setLayoutBackExamResult(LinearLayout linearLayout, MD_ExamResultDetail resultDetail) {

        if (resultDetail.getUserAnswer().equals(resultDetail.getQuestion().getCorrectAnswer()))
            linearLayout.setBackground(linearLayout.getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else
            linearLayout.setBackground(linearLayout.getContext().getResources().getDrawable(R.drawable.dw_back_exam_result_wrong));
    }
    //______________________________________________________________________________________________ setLayoutBackExamResult


    //______________________________________________________________________________________________ setYourAnswer
    @BindingAdapter(value = "setYourAnswer")
    public static void setYourAnswer(TextView textView, MD_ExamResultDetail resultDetail) {
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
    //______________________________________________________________________________________________ setYourAnswer


    //______________________________________________________________________________________________ setCorrectAnswer
    @BindingAdapter(value = "setCorrectAnswer")
    public static void setCorrectAnswer(TextView textView, MD_ExamResultDetail resultDetail) {
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
    //______________________________________________________________________________________________ setCorrectAnswer


    //______________________________________________________________________________________________ setTextViewFloat
    @SuppressLint("DefaultLocale")
    @BindingAdapter(value = "setTextViewFloat")
    public static void setTextViewFloat(TextView textView, float value) {

        String v = String.format("%.2f", value);
        textView.setText(v);

    }
    //______________________________________________________________________________________________ setTextViewFloat


    //______________________________________________________________________________________________ setTextViewDouble
    @SuppressLint("DefaultLocale")
    @BindingAdapter(value = "setTextViewDouble")
    public static void setTextViewDouble(TextView textView, double value) {

        String v = String.format("%.1f", value);
        textView.setText(v);

    }
    //______________________________________________________________________________________________ setTextViewDouble


    //______________________________________________________________________________________________ setTextViewExamResultTotal
    @BindingAdapter(value = {"CorrectAnswerCount", "WrongAnswerCount", "NotAnswered"})
    public static void setTextViewExamResultTotal(
            TextView textView,
            Integer CorrectAnswerCount,
            Integer WrongAnswerCount,
            Integer NotAnswered) {

        int total;

        if (CorrectAnswerCount != null && WrongAnswerCount != null && NotAnswered != null)
            total = CorrectAnswerCount + WrongAnswerCount + NotAnswered;
        else
            total = 0;
        textView.setText(String.valueOf(total));

    }
    //______________________________________________________________________________________________ setTextViewExamResultTotal


    //______________________________________________________________________________________________ setValueForRadioButton
    @BindingAdapter(value = {"TextRadioButton", "userAnswerRadioButton", "radioButtonTag"})
    public static void setValueForRadioButton(RadioButton radioButton, String title, Byte userAnswer, Integer radioTag) {
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
    //______________________________________________________________________________________________ setValueForRadioButton


    //______________________________________________________________________________________________ setTextViewText
    @SuppressLint("SetTextI18n")
    @BindingAdapter(value = "setTextViewText")
    public static void setTextViewText(TextView textView, String text) {

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        if (text == null || text.equalsIgnoreCase("null")) {
            text = "";
        }
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
            case "type":
                textView.setText(context.getResources().getString(R.string.Type) + " : " + text);
                break;
            case "insured":
                textView.setText(context.getResources().getString(R.string.insuredName) + " : " + text);
                break;
            case "insuredNationalCode":
                textView.setText(context.getResources().getString(R.string.NationalCode) + " : " + text);
                break;
            case "insuranceNum":
                if (text.equalsIgnoreCase("") || text.equalsIgnoreCase("null"))
                    textView.setVisibility(View.GONE);
                else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(context.getResources().getString(R.string.NationalCode) + " : " + text);
                }
                break;
        }

    }
    //______________________________________________________________________________________________ setTextViewText


    //______________________________________________________________________________________________ setTextViewTextInteger
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @BindingAdapter(value = "SetTextViewInteger")
    public static void setTextViewTextInteger(TextView textView, Integer value) {

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        if ("movieTime".equals(tag)) {
            Integer h = value % 60;
            Integer min = value / 60;
            textView.setText(context.getResources().getString(R.string.MovieTime) + "  " + String.format("%02d", min) + ":" + String.format("%02d", h));
        } else {
            if (value != null)
                textView.setText(value.toString());
            else
                textView.setText("0");
        }

    }
    //______________________________________________________________________________________________ setTextViewTextInteger


    //______________________________________________________________________________________________ setTextViewLong
    @SuppressLint("SetTextI18n")
    @BindingAdapter(value = "setTextViewLong")
    public static void setTextViewLong(TextView textView, Long value) {

        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        if ("amount".equals(tag)) {
            ApplicationUtility utility = PishtazanApplication.getApplication(context)
                    .getApplicationUtilityComponent()
                    .getApplicationUtility();


            textView.setText(context.getResources().getString(R.string.Amount) + " : " + utility.splitNumberOfAmount(value));
        }

    }
    //______________________________________________________________________________________________ setTextViewLong


    //______________________________________________________________________________________________ setDegreePersonImage
    @BindingAdapter(value = "setDegreePersonImage")
    public static void setDegreePersonImage(CircleImageView imageView, Integer degree) {
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
        }
    }
    //______________________________________________________________________________________________ setDegreePersonImage


    //______________________________________________________________________________________________ setPersonImage
    @BindingAdapter(value = "setPersonImage")
    public static void setPersonImage(CircleImageView imageView, String url) {

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

    }
    //______________________________________________________________________________________________ setPersonImage


    //______________________________________________________________________________________________ setPersonImageReport
    @BindingAdapter(value = "setPersonImageReport")
    public static void setPersonImageReport(CircleImageView imageView, String url) {

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

    }
    //______________________________________________________________________________________________ setPersonImageReport


}
