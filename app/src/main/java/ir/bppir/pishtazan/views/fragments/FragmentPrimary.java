package ir.bppir.pishtazan.views.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

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
    private Context context;
    private View view;
    private GetMessageFromObservable getMessageFromObservable;
    private boolean AccessClick;
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
        AccessClick = true;

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
    public Context getContext() {//_________________________________________________________________ getContext
        return context;
    }//_____________________________________________________________________________________________ getContext


    @Override
    public View getView() {//_______________________________________________________________________ getView
        return view;
    }//_____________________________________________________________________________________________ getView


    public void setView(View view) {//______________________________________________________________ setView
        this.view = view;
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

                        setAccessClick(true);

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



    public boolean isAccessClick() {//______________________________________________________________ isAccessClick
        return AccessClick;
    }//_____________________________________________________________________________________________ isAccessClick


    public void setAccessClick(boolean accessClick) {//_____________________________________________ setAccessClick
        AccessClick = accessClick;
    }//_____________________________________________________________________________________________ setAccessClick
}