package ir.bppir.pishtazan.viewmodels;

import io.reactivex.subjects.PublishSubject;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary

    public PublishSubject<Byte> getPublishSubject() {//_____________________________________________ getPublishSubject
        return publishSubject;
    }//_____________________________________________________________________________________________ getPublishSubject

}