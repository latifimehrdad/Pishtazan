package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Reminder extends MR_Primary {

    @SerializedName("Reminders")
    List<MD_Reminder> Reminders;

    public MR_Reminder(Integer statue, String message, List<String> messages, List<MD_Reminder> reminders) {
        super(statue, message, messages);
        Reminders = reminders;
    }

    public List<MD_Reminder> getReminders() {
        return Reminders;
    }

    public void setReminders(List<MD_Reminder> reminders) {
        Reminders = reminders;
    }
}
