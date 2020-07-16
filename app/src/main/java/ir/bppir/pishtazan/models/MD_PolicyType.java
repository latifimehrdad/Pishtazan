package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_PolicyType {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Title")
    String Title;

    @SerializedName("Image")
    String Image;

    @SerializedName("Description")
    String Description;

    public MD_PolicyType(Integer id, String title, String image, String description) {
        Id = id;
        Title = title;
        Image = image;
        Description = description;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
