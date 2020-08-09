package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_EducationFiles extends MR_Primary{

    @SerializedName("EducationFiles")
    List<MD_EducationFiles> EducationFiles;

    public MR_EducationFiles(Integer statue, String message, List<String> messages, List<MD_EducationFiles> educationFiles) {
        super(statue, message, messages);
        EducationFiles = educationFiles;
    }

    public List<MD_EducationFiles> getEducationFiles() {
        return EducationFiles;
    }

    public void setEducationFiles(List<MD_EducationFiles> educationFiles) {
        EducationFiles = educationFiles;
    }
}
