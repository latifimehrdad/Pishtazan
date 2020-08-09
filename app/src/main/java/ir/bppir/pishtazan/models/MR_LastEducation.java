package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_LastEducation extends MR_Primary {

    @SerializedName("Education")
    MD_Education Education;

    public MR_LastEducation(Integer statue, String message, List<String> messages, MD_Education education) {
        super(statue, message, messages);
        Education = education;
    }

    public MD_Education getEducation() {
        return Education;
    }

    public void setEducation(MD_Education education) {
        Education = education;
    }
}
