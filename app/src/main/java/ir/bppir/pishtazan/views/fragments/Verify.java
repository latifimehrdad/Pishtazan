package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentVerifyBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Verify;
import ir.bppir.pishtazan.views.dialogs.DialogProgress;

public class Verify extends FragmentPrimary implements FragmentPrimary.messageFromObservable {

    private NavController navController;
    private VM_Verify vm_verify;
    private String PhoneNumber;
    private boolean ReTryGetSMSClick = false;
    private Handler timer;
    private Runnable runnable;
    private DialogProgress progress;


    @BindView(R.id.VerifyCode1)
    EditText VerifyCode1;

    @BindView(R.id.VerifyCode2)
    EditText VerifyCode2;

    @BindView(R.id.VerifyCode3)
    EditText VerifyCode3;

    @BindView(R.id.VerifyCode4)
    EditText VerifyCode4;

    @BindView(R.id.VerifyCode5)
    EditText VerifyCode5;

    @BindView(R.id.VerifyCode6)
    EditText VerifyCode6;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.TimeElapsed)
    TextView TimeElapsed;

    @BindView(R.id.message)
    TextView message;


    public Verify() {//_____________________________________________________________________________ Verify

    }//_____________________________________________________________________________________________ Verify


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_verify = new VM_Verify(getActivity());
            FragmentVerifyBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_verify,container, false);
            binding.setVerify(vm_verify);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTextChangeListener();
            VerifyCode1.requestFocus();
            progressBar.setProgress(0);
            ReTryGetSMS();
            SetClick();
            StartTimer(60);
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ init
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                Verify.this,
                vm_verify.getPublishSubject(),
                vm_verify);
        PhoneNumber = getArguments().getString(getString(R.string.ML_PhoneNumber));
    }//_____________________________________________________________________________________________ init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_ReTrySensSms) {
            DismissProgress();
            StartTimer(60);
            return;
        }

        if (action == StaticValues.ML_GotoHome) {
            DismissProgress();
            getActivity().onBackPressed();
            getActivity().onBackPressed();
            return;
        }

        if ((action == StaticValues.ML_ResponseError) ||
                (action == StaticValues.ML_ResponseFailure) ||
                (action == StaticValues.ML_RequestCancel)) {
            DismissDialogLoading();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetClick() {//_____________________________________________________________________ Start SetClick

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ReTryGetSMSClick) {
                    ShowProgressDialog();
                    vm_verify.SendNumber(PhoneNumber);
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick



    private void SetTextChangeListener() {//________________________________________________________ SetTextChangeListener

        VerifyCode1.addTextChangedListener(TextChange(VerifyCode2));
        VerifyCode2.addTextChangedListener(TextChange(VerifyCode3));
        VerifyCode3.addTextChangedListener(TextChange(VerifyCode4));
        VerifyCode4.addTextChangedListener(TextChange(VerifyCode5));
        VerifyCode5.addTextChangedListener(TextChange(VerifyCode6));
        VerifyCode6.addTextChangedListener(TextChange(VerifyCode6));

        VerifyCode1.setOnKeyListener(SetKeyBackSpace(VerifyCode1));
        VerifyCode2.setOnKeyListener(SetKeyBackSpace(VerifyCode1));
        VerifyCode3.setOnKeyListener(SetKeyBackSpace(VerifyCode2));
        VerifyCode4.setOnKeyListener(SetKeyBackSpace(VerifyCode3));
        VerifyCode5.setOnKeyListener(SetKeyBackSpace(VerifyCode4));
        VerifyCode6.setOnKeyListener(SetKeyBackSpace(VerifyCode5));


    }//_____________________________________________________________________________________________ SetTextChangeListener


    private View.OnKeyListener SetKeyBackSpace(final EditText view) {//_____________________________ SetKeyBackSpace
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                EditText edit = (EditText) v;
                if (keyCode == 67) {
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;
                    if (edit.getText().length() == 0) {
                        view.setText("");
                        view.requestFocus();
                        SetBackVerifyCode();
                        return true;
                    } else
                        return false;
                }
                return false;
            }
        };
    }//_____________________________________________________________________________________________ SetKeyBackSpace


    private TextWatcher TextChange(final EditText eNext) {//________________________________________ TextChange

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    eNext.requestFocus();

                }
                SetBackVerifyCode();
            }
        };

    }//_____________________________________________________________________________________________ TextChange

    private void SetBackVerifyCode() {//____________________________________________________________ Start SetBackVerifyCode

        Boolean c1 = SetBackVerifyCodeView(VerifyCode1);
        Boolean c2 = SetBackVerifyCodeView(VerifyCode2);
        Boolean c3 = SetBackVerifyCodeView(VerifyCode3);
        Boolean c4 = SetBackVerifyCodeView(VerifyCode4);
        Boolean c5 = SetBackVerifyCodeView(VerifyCode5);
        Boolean c6 = SetBackVerifyCodeView(VerifyCode6);

        if (c1 && c2 && c3 && c4 && c5 && c6) {
            String code = VerifyCode1.getText().toString() +
                    VerifyCode2.getText().toString() +
                    VerifyCode3.getText().toString() +
                    VerifyCode4.getText().toString() +
                    VerifyCode5.getText().toString() +
                    VerifyCode6.getText().toString();

            hideKeyboard();
            ShowProgressDialog();
            vm_verify.VerifyNumber(PhoneNumber, code);
        }

    }//_____________________________________________________________________________________________ End SetBackVerifyCode


    private Boolean SetBackVerifyCodeView(EditText editText) {//____________________________________ Satart SetBackVerifyCodeView

        Boolean ret = false;
        if (editText.getText().length() != 0)
            ret = true;
        return ret;

    }//_____________________________________________________________________________________________ End SetBackVerifyCodeView


    private void ReTryGetSMS() {//__________________________________________________________________ Start ReTryGetSMS
        if (progress != null)
            progress.dismiss();
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }//_____________________________________________________________________________________________ End ReTryGetSMS


    private void StartTimer(int Elapse) {//_________________________________________________________ Start StartTimer

        ReTryGetSMSClick = false;
        TimeElapsed.setVisibility(View.VISIBLE);
        message.setText(getResources().getString(R.string.ElapsedTimeGetSMS));

        Elapse = Elapse * 10;
        progressBar.setMax(Elapse * 2);
        progressBar.setProgress(Elapse);
        TimeElapsed.setVisibility(View.VISIBLE);
        timer = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mili = progressBar.getProgress() + 10;
                int seconds = (int) (mili / 10) % 60;
                int minutes = (int) ((mili / (10 * 60)) % 60);
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    ReTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog
        progress = new DialogProgress(getContext(), null);
        progress.setCancelable(false);
        progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void DismissProgress() {//______________________________________________________________ DismissProgress

        DismissDialogLoading();
        progressBar.setProgress(0);
        StaticValues.isCancel = true;
        if (timer != null && runnable != null) {
            timer.removeCallbacks(runnable);
            timer = null;
            runnable = null;
        }

    }//_____________________________________________________________________________________________ DismissProgress


    private void DismissDialogLoading() {//_________________________________________________________ DismissDialogLoading
        if (progress != null)
            progress.dismiss();
    }//_____________________________________________________________________________________________ DismissDialogLoading



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
