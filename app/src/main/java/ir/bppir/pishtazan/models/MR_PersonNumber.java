package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_PersonNumber extends MR_Primary {

    @SerializedName("MobileNumber")
    String MobileNumber;

    public MR_PersonNumber(Integer statue, String message, List<String> messages, String mobileNumber) {
        super(statue, message, messages);
        MobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
