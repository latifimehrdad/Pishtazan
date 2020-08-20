package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Education {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Title")
    String Title;

    public MD_Education(Integer id, String title) {
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
