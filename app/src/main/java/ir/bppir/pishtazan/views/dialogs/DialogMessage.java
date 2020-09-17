package ir.bppir.pishtazan.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.DialogMessageBinding;

public class DialogMessage extends DialogFragment {

    private Context context;
    private String Title;
    private int color;
    private int tintColor;
    private Drawable icon;

    @BindView(R.id.LinearLayoutAdd)
    LinearLayout LinearLayoutAdd;

    @BindView(R.id.DialogTitle)
    TextView DialogTitle;

    @BindView(R.id.layout)
    LinearLayout layout;

    @BindView(R.id.DialogImg)
    ImageView DialogImg;


    //______________________________________________________________________________________________ DialogMessage
    public DialogMessage(
            Context context,
            String title,
            int color,
            Drawable icon,
            int tintColor) {
        this.context = context;
        Title = title;
        this.color = color;
        this.icon = icon;
        this.tintColor = tintColor;

    }
    //______________________________________________________________________________________________ DialogMessage


    //______________________________________________________________________________________________ onCreateDialog
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view;
        DialogMessageBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                                .from(this.context),
                        R.layout.dialog_message,
                        null,
                        false);
        binding.setTitel("");
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        layout.setBackgroundColor(color);
        DialogTitle.setText(Title);
        DialogImg.setImageDrawable(icon);
        DialogImg.setColorFilter(tintColor);
        LinearLayoutAdd.setOnClickListener(view1 -> DialogMessage.this.dismiss());
        Dialog dialog = new AlertDialog.Builder(context).setView(view).create();
        Animation buttom = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        view.setAnimation(buttom);
        return dialog;
    }
    //______________________________________________________________________________________________ onCreateDialog

}