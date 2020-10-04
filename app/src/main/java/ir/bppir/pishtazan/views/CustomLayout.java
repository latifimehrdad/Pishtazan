package ir.bppir.pishtazan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import ir.bppir.pishtazan.R;

public class CustomLayout extends LinearLayout {

    private EditText editText;
    private Context context;
    private int editWidth;
    private int editInputType;
    private float textSize;
    private int textColor;
    private String editHint;
    private String editTextText;
    private Drawable normalBack;
    private Drawable emptyBack;

    public CustomLayout(Context context) {
        super(context);
        this.context = context;
    }


    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        normalBack = context.getResources().getDrawable(R.drawable.dw_edit_back);
        emptyBack = context.getResources().getDrawable(R.drawable.dw_edit_back_empty);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout);
        editWidth = a.getDimensionPixelSize(R.styleable.CustomLayout_mlWidth, LayoutParams.WRAP_CONTENT);
        editInputType = a.getInt(R.styleable.CustomLayout_mlInputType, InputType.TYPE_NULL);
        textSize = a.getDimension(R.styleable.CustomLayout_mlTextSize, 10);
//        textSize = context.getResources().getDimension((int)textSize);
        textColor = a.getInt(R.styleable.CustomLayout_lmTextColor, 0);
        editHint = a.getString(R.styleable.CustomLayout_mlTextHint);
        editTextText = a.getString(R.styleable.CustomLayout_mlText);
        setBackground(normalBack);
        configEditText();
    }


    private void configEditText() {
        editText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(editWidth, LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(params);
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(editInputType);
/*        textSize = textSize / 2;*/
        editText.setTextSize(textSize);
        editText.setTextColor(textColor);
        editText.setHint(editHint);
        editText.setText(editTextText);
        editText.setBackgroundColor(context.getResources().getColor(R.color.mlWave));
        addView(editText);
    }


    //______________________________________________________________________________________________ getEditText
    public EditText getEditText() {
        return editText;
    }
    //______________________________________________________________________________________________ getEditText


    //______________________________________________________________________________________________ setEditText
    public void setEditText(EditText editText) {
        this.editText = editText;
    }
    //______________________________________________________________________________________________ setEditText


}
