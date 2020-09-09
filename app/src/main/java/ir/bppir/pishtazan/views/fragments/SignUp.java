package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentSignupBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_SignUp;

public class SignUp extends FragmentPrimary implements FragmentPrimary.messageFromObservable {

    private NavController navController;
    private VM_SignUp vm_signUp;


    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.ButtonSignUp)
    RelativeLayout ButtonSignUp;

    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @BindView(R.id.BtnLoginText)
    TextView BtnLoginText;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;


    //______________________________________________________________________________________________ SignUp
    public SignUp() {
    }
    //______________________________________________________________________________________________ SignUp


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_signUp = new VM_SignUp(getActivity());
            FragmentSignupBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_signup, container, false);
            binding.setSignup(vm_signUp);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setClick();
            setTextWatcher();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        FirebaseMessaging.getInstance().subscribeToTopic("all_user");
        init();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        setGetMessageFromObservable(
                SignUp.this,
                vm_signUp.getPublishSubject(),
                vm_signUp);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getMessageFromObservable(Byte action) {

        dismissLoading();
        if (action.equals(StaticValues.ML_GotoVerify)) {
            Bundle bundle = new Bundle();
            bundle.putString(getRes().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
            navController.navigate(R.id.action_signUp_to_verify, bundle);
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ setClick
    private void setClick() {

        ButtonSignUp.setOnClickListener(v -> {
            if (checkEmpty()) {
                showLoading();
                vm_signUp.sendNumber();
            }
        });
    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        hideKeyboard();

        boolean mobile = true;

        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditPhoneNumber.setError(getRes().getString(R.string.EnterPhoneNumber));
            EditPhoneNumber.requestFocus();
            mobile = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
            if (!ZeroNine.equalsIgnoreCase("09")) {
                EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditPhoneNumber.setError(getRes().getString(R.string.EnterPhoneNumber));
                EditPhoneNumber.requestFocus();
                mobile = false;
            }
        }

        return mobile;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditPhoneNumber.addTextChangedListener(textChangeForChangeBack(EditPhoneNumber));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {

        BtnLoginText.setText(getRes().getString(R.string.GetVerifyCode));
        ButtonSignUp.setBackground(getRes().getDrawable(R.drawable.dw_back_bottom));
        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        BtnLoginText.setText(getRes().getString(R.string.Cancel));
        ButtonSignUp.setBackground(getRes().getDrawable(R.drawable.dw_back_bottom_connection));
        ProgressGif.setVisibility(View.VISIBLE);
        imgProgress.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
