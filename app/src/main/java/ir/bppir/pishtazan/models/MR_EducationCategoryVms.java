package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_EducationCategoryVms extends MR_Primary {

    @SerializedName("Resources")
    List<MD_EducationCategoryVms> EducationCategoryVms;

    public MR_EducationCategoryVms(Integer statue, String message, List<String> messages, List<MD_EducationCategoryVms> educationCategoryVms) {
        super(statue, message, messages);
        EducationCategoryVms = educationCategoryVms;
    }

    public List<MD_EducationCategoryVms> getEducationCategoryVms() {
        return EducationCategoryVms;
    }

    public void setEducationCategoryVms(List<MD_EducationCategoryVms> educationCategoryVms) {
        EducationCategoryVms = educationCategoryVms;
    }
}
