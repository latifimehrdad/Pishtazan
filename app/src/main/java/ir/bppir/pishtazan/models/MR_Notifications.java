package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Notifications extends  MR_Primary {

    @SerializedName("Notifications")
    private List<MD_Notifications> Notifications;

    public MR_Notifications(Integer statue, String message, List<String> messages, List<MD_Notifications> notifications) {
        super(statue, message, messages);
        Notifications = notifications;
    }


    public List<MD_Notifications> getNotifications() {
        return Notifications;
    }

    public void setNotifications(List<MD_Notifications> notifications) {
        Notifications = notifications;
    }
}
