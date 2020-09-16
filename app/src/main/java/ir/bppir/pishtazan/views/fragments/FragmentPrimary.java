package ir.bppir.pishtazan.views.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.activity.MainActivity;


public class FragmentPrimary extends Fragment {

    private DisposableObserver<Byte> disposableObserver;
    private Activity context;
    private View view;
    private messageFromObservable MessageFromObservable;
    private VM_Primary vm_primary;


    //______________________________________________________________________________________________ messageFromObservable
    public interface messageFromObservable {
        void getMessageFromObservable(Byte action);
        void actionWhenFailureRequest();
    }
    //______________________________________________________________________________________________ messageFromObservable


    //______________________________________________________________________________________________ FragmentPrimary
    public FragmentPrimary() {
    }
    //______________________________________________________________________________________________ FragmentPrimary


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        StaticValues.isCancel = true;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onDestroy


    //______________________________________________________________________________________________ onStop
    @Override
    public void onStop() {
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onStop


    //______________________________________________________________________________________________ getContext
    @Override
    public Activity getContext() {
        return context;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ getView
    @Override
    public View getView() {
        return view;
    }
    //______________________________________________________________________________________________ getView


    //______________________________________________________________________________________________ setView
    public void setView(View view) {
        this.view = view;
        ButterKnife.bind(this, getView());
    }
    //______________________________________________________________________________________________ setView


    //______________________________________________________________________________________________ setGetMessageFromObservable
    public void setGetMessageFromObservable(
            messageFromObservable MessageFromObservable,
            PublishSubject<Byte> publishSubject,
            VM_Primary vm_primary) {
        this.MessageFromObservable = MessageFromObservable;
        this.vm_primary = vm_primary;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        setObserverToObservable(publishSubject);
    }
    //______________________________________________________________________________________________ setGetMessageFromObservable


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable(PublishSubject<Byte> publishSubject) {

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                actionHandler(aByte);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        publishSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        getActivity()
                .runOnUiThread(() -> {

                    MessageFromObservable.getMessageFromObservable(action);

                    if (vm_primary.getResponseMessage() == null)
                        return;

                    if (vm_primary.getResponseMessage().equalsIgnoreCase(""))
                        return;

                    if ((action == StaticValues.ML_RequestCancel)
                            || (action == StaticValues.ML_ResponseError)
                            || (action == StaticValues.ML_ResponseFailure)
                            || (action == StaticValues.ML_InternetAccessFailed)) {
                        showMessage(vm_primary.getResponseMessage(),
                                getResources().getColor(R.color.ML_White),
                                getResources().getDrawable(R.drawable.ic_baseline_warning),
                                getResources().getColor(R.color.ML_Harmony));
                        MessageFromObservable.actionWhenFailureRequest();
                    } else
                        showMessage(vm_primary.getResponseMessage()
                                , getResources().getColor(R.color.ML_White),
                                getResources().getDrawable(R.drawable.ic_baseline_check_circle),
                                getResources().getColor(R.color.ML_OK));

                });
    }
    //______________________________________________________________________________________________ actionHandler


    //______________________________________________________________________________________________ showMessage
    public void showMessage(String message, int color, Drawable icon, int tintColor) {
        MainActivity.mainActivity.showCustomToast(message, icon, tintColor, getContext());
/*        DialogMessage dialogMessage = new DialogMessage(getContext(), message, color, icon, tintColor);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);*/
    }
    //______________________________________________________________________________________________ showMessage


    //______________________________________________________________________________________________ hideKeyboard
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) vm_primary.getContext().getSystemService(vm_primary.getContext().INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = vm_primary.getContext().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(vm_primary.getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //______________________________________________________________________________________________ hideKeyboard


    //______________________________________________________________________________________________ textChangeForChangeBack
    public TextWatcher textChangeForChangeBack(final EditText editText) {
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

    }
    //______________________________________________________________________________________________ textChangeForChangeBack


    //______________________________________________________________________________________________ getRes
    public Resources getRes() {
        return getContext().getResources();
    }
    //______________________________________________________________________________________________ getRes


}