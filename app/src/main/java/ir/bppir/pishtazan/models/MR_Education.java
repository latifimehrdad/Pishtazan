package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Education extends MR_Primary {

    @SerializedName("Educations")
    List<MD_Education> Educations;

    public MR_Education(Integer statue, String message, List<String> messages, List<MD_Education> educations) {
        super(statue, message, messages);
        Educations = educations;
    }

    public List<MD_Education> getEducations() {
        return Educations;
    }

    public void setEducations(List<MD_Education> educations) {
        Educations = educations;
    }
}
