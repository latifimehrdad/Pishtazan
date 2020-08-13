package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_EducationCategoryVms {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Title")
    String Title;

    public MD_EducationCategoryVms(Integer id, String title) {
        Id = id;
        Title = title;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
