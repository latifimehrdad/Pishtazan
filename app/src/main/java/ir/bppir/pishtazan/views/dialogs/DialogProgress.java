package ir.bppir.pishtazan.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.DialogProgressBinding;

public class DialogProgress extends DialogFragment {

    private Context context;
    private String Title;
    public  boolean StaticFunctions;


    @BindView(R.id.DialogProgressIgnor)
    Button DialogProgressIgnore;

    @BindView(R.id.DialogProgressTitle)
    TextView DialogProgressTitle;

    //______________________________________________________________________________________________ DialogProgress
    public DialogProgress(Context context, String title) {
        this.context = context;
        Title = title;
        StaticFunctions = false;
    }
    //______________________________________________________________________________________________ DialogProgress


    //______________________________________________________________________________________________ onCreateDialog
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view;
        DialogProgressBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                                .from(this.context),
                        R.layout.dialog_progress,
                        null,
                        false);
        binding.setString("null");
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        DialogProgressTitle.setText(Title);
        DialogProgressIgnore.setOnClickListener(view1 -> {
            StaticFunctions = true;
            DialogProgress.this.dismiss();
        });
        return new AlertDialog.Builder(context).setView(view).create();
    }
    //______________________________________________________________________________________________ onCreateDialog

}