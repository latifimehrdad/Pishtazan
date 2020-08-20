package ir.bppir.pishtazan.views.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.dialogs.DialogMessage;


public class FragmentPrimary extends Fragment {

    private DisposableObserver<Byte> disposableObserver;
    private Activity context;
    private View view;
    private GetMessageFromObservable getMessageFromObservable;
    private VM_Primary vm_primary;


    public interface GetMessageFromObservable {//___________________________________________________ GetMessageFromObservable
        void GetMessageFromObservable(Byte action);
    }//_____________________________________________________________________________________________ GetMessageFromObservable


    public FragmentPrimary() {//____________________________________________________________________ FragmentPrimary

    }//_____________________________________________________________________________________________ FragmentPrimary



    @Override
    public void onCreate(Bundle savedInstanceState) {//_____________________________________________ onCreate
        super.onCreate(savedInstanceState);
        context = getActivity();

    }//_____________________________________________________________________________________________ onCreate


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        StaticValues.isCancel = true;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onDestroy


    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onStop


    @Override
    public Activity getContext() {//________________________________________________________________ getContext
        return context;
    }//_____________________________________________________________________________________________ getContext


    @Override
    public View getView() {//_______________________________________________________________________ getView
        return view;
    }//_____________________________________________________________________________________________ getView


    public void setView(View view) {//______________________________________________________________ setView
        this.view = view;
        ButterKnife.bind(this, getView());
    }//_____________________________________________________________________________________________ setView


    public void setGetMessageFromObservable(
            GetMessageFromObservable getMessageFromObservable,
            PublishSubject<Byte> publishSubject,
            VM_Primary vm_primary) {//______________________________________________________________ setGetMessageFromObservable
        this.getMessageFromObservable = getMessageFromObservable;
        this.vm_primary = vm_primary;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        SetObserverToObservable(publishSubject);
    }//_____________________________________________________________________________________________ setGetMessageFromObservable


    public void SetObserverToObservable(PublishSubject<Byte> publishSubject) {//____________________ SetObserverToObservable

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

    }//_____________________________________________________________________________________________ SetObserverToObservable



    private void actionHandler(Byte action) {//_____________________________________________________ actionHandler
        getActivity()
                .runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getMessageFromObservable.GetMessageFromObservable(action);

                        if (vm_primary.getResponseMessage() == null)
                            return;

                        if (vm_primary.getResponseMessage().equalsIgnoreCase(""))
                            return;

                        if ((action == StaticValues.ML_RequestCancel)
                                || (action == StaticValues.ML_ResponseError)
                                || (action == StaticValues.ML_ResponseFailure))
                            ShowMessage(vm_primary.getResponseMessage(),
                                    getResources().getColor(R.color.ML_White),
                                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                                    getResources().getColor(R.color.ML_HarmonyDark));
                        else
                            ShowMessage(vm_primary.getResponseMessage()
                                    , getResources().getColor(R.color.ML_White),
                                    getResources().getDrawable(R.drawable.ic_baseline_check_circle),
                                    getResources().getColor(R.color.ML_OK));

                    }
                });
    }//_____________________________________________________________________________________________ actionHandler



    public void ShowMessage(String message, int color, Drawable icon, int tintColor) {//___________ ShowMessage

        DialogMessage dialogMessage = new DialogMessage(getContext(), message, color, icon, tintColor);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ ShowMessage


    public void hideKeyboard() {//___________________________________________________________ Start hideKeyboard
        InputMethodManager imm = (InputMethodManager) vm_primary.getContext().getSystemService(vm_primary.getContext().INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = vm_primary.getContext().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(vm_primary.getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }//_____________________________________________________________________________________________ End hideKeyboard

}