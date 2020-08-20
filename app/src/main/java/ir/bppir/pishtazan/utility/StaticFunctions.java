package ir.bppir.pishtazan.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import ir.bppir.pishtazan.R;

public class StaticFunctions {

    public static String getFirebaseToken() {
        String token  = FirebaseInstanceId.getInstance().getToken();
        return token;
    }

    public static String getFirebaseId() {
        String token = FirebaseInstanceId.getInstance().getId();
        return token;
    }


    public static void hideKeyboard(Activity activity) {//__________________________________________ Start hideKeyboard
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }//_____________________________________________________________________________________________ End hideKeyboard


    public static boolean isInternetConnected(Context context) {//__________________________________ Start isInternetConnected
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }//_____________________________________________________________________________________________ End isInternetConnected


    public static TextWatcher TextChangeForChangeBack(final EditText editText) {//__________________ TextChangeForChangeBack

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                editText.setBackgroundResource(R.drawable.dw_edit_back);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }//_____________________________________________________________________________________________ TextChangeForChangeBack


}
