package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Payment extends MR_Primary {

    @SerializedName("UrlReturn")
    String UrlReturn;


    public MR_Payment(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }

    public String getUrlReturn() {
        return UrlReturn;
    }
}
