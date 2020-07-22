package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Primary {

    @SerializedName("Statue")
    Integer Statue;

    @SerializedName("Message")
    String Message;

    @SerializedName("Messages")
    List<String> Messages;

    public MR_Primary(Integer statue, String message, List<String> messages) {
        Statue = statue;
        Message = message;
        Messages = messages;
    }

    public Integer getStatue() {
        return Statue;
    }

    public void setStatue(Integer statue) {
        Statue = statue;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<String> getMessages() {
        return Messages;
    }

    public void setMessages(List<String> messages) {
        Messages = messages;
    }
}
