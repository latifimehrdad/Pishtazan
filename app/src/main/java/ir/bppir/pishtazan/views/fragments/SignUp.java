package ir.bppir.pishtazan.views.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentSignupBinding;
import ir.bppir.pishtazan.utility.StaticFunctions;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_SignUp;
import ir.bppir.pishtazan.views.dialogs.DialogMessage;

import static ir.bppir.pishtazan.utility.StaticFunctions.TextChangeForChangeBack;

public class SignUp extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_SignUp vm_signUp;
    private String PhoneNumber;


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



    public SignUp() {//_____________________________________________________________________________ SignUp
    }//_____________________________________________________________________________________________ SignUp


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_signUp = new VM_SignUp(getContext());
            FragmentSignupBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_signup,container, false);
            binding.setSignup(vm_signUp);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClick();
            SetTextWatcher();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        setGetMessageFromObservable(SignUp.this, vm_signUp.getPublishSubject());
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_GotoVerify) {
            DismissLoading();
            Bundle bundle = new Bundle();
            bundle.putString(getContext()
                    .getString(R.string.ML_PhoneNumber),EditPhoneNumber.getText().toString());
            navController.navigate(R.id.action_signUp_to_verify,bundle);
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetClick() {//_____________________________________________________________________ SetClick

        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticFunctions.hideKeyboard(getActivity());
                if (!StaticFunctions.isInternetConnected(getContext())) {
                    ShowMessage(
                            getResources().getString(R.string.InternetNotAvailable),
                            getResources().getColor(R.color.ML_Dialog),
                            getResources().getDrawable(R.drawable.ic_baseline_warning),
                            getResources().getColor(R.color.ML_Red));
                    return;
                }

                if (StaticValues.isCancel) {
                    if (CheckEmpty()) {
                        ShowLoading();
                        vm_signUp.SendNumber(
                                EditPhoneNumber.getText().toString());
                    }
                } else {
                    DismissLoading();
                }
            }
        });


    }//_____________________________________________________________________________________________ SetClick



    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
    }//_____________________________________________________________________________________________ End SetTextWatcher



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean mobile = true;
        PhoneNumber = EditPhoneNumber.getText().toString();

        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditPhoneNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
            EditPhoneNumber.requestFocus();
            mobile = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
            if(!ZeroNine.equalsIgnoreCase("09")) {
                EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditPhoneNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
                EditPhoneNumber.requestFocus();
                mobile = false;
            }
        }

        if (mobile)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty




    private void DismissLoading() {//_______________________________________________________________ DismissLoading
        StaticValues.isCancel = true;
        BtnLoginText.setText(getResources().getString(R.string.GetVerifyCode));
        ButtonSignUp.setBackground(getResources().getDrawable(R.drawable.dw_back_bottom));
        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ DismissLoading


    private void ShowLoading() {//__________________________________________________________________ ShowLoading
        BtnLoginText.setText(getResources().getString(R.string.Cancel));
        ButtonSignUp.setBackground(getResources().getDrawable(R.drawable.dw_back_bottom_connection));
        ProgressGif.setVisibility(View.VISIBLE);
        imgProgress.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ ShowLoading


}
