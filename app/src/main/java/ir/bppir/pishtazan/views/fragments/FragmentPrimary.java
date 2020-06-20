package ir.bppir.pishtazan.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class FragmentPrimary extends Fragment {

    private DisposableObserver<Byte> disposableObserver;
    private Context context;
    private View view;
    private GetMessageFromObservable getMessageFromObservable;


    public interface GetMessageFromObservable {//___________________________________________________ GetMessageFromObservable
        void GetMessageFromObservable(Byte action);
    }//_____________________________________________________________________________________________ GetMessageFromObservable

    public FragmentPrimary() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {//_____________________________________________ onCreate
        super.onCreate(savedInstanceState);
        context = getActivity();
    }//_____________________________________________________________________________________________ onCreate


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
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
            PublishSubject<Byte> publishSubject) {//________________________________________________ setGetMessageFromObservable
        this.getMessageFromObservable = getMessageFromObservable;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        SetObserverToObservable(publishSubject);
    }//_____________________________________________________________________________________________ setGetMessageFromObservable


    public void SetObserverToObservable(PublishSubject<Byte> publishSubject) {//____________________ SetObserverToObservable

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getMessageFromObservable.GetMessageFromObservable(aByte);
                            }
                        });
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


}