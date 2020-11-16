package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Reminder {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("NotificationDateTimeJ")
    String notificationDateTimeJ;

    @SerializedName("NotificationTime")
    String notificationTime;

    @SerializedName("Title")
    String title;

    @SerializedName("ReminderResult")
    Byte ReminderResult;


    public MD_Reminder(Integer id, String notificationDateTimeJ, String notificationTime, String title, Byte reminderResult) {
        Id = id;
        this.notificationDateTimeJ = notificationDateTimeJ;
        this.notificationTime = notificationTime;
        this.title = title;
        ReminderResult = reminderResult;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNotificationDateTimeJ() {
        return notificationDateTimeJ;
    }

    public void setNotificationDateTimeJ(String notificationDateTimeJ) {
        this.notificationDateTimeJ = notificationDateTimeJ;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getReminderResult() {
        return ReminderResult;
    }

    public void setReminderResult(Byte reminderResult) {
        ReminderResult = reminderResult;
    }
}
