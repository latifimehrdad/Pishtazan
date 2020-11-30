package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_NotificationDetail extends VM_Primary {


    private String title;
    private String description;
    private String url;
    private String date;


    //______________________________________________________________________________________________ VM_NotificationDetail
    public VM_NotificationDetail(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_NotificationDetail


    //______________________________________________________________________________________________ setValue
    public void setValue(String title, String description, String url, String date) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.date = date;
        notifyChange();
    }
    //______________________________________________________________________________________________ setValue


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
