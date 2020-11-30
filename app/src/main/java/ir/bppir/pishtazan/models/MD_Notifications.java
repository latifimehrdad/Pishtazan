package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Notifications {

    @SerializedName("Id")
    private int Id;

    @SerializedName("Title")
    private String Title;

    @SerializedName("Description")
    private String Description;

    @SerializedName("CDateJ")
    private String CDateJ;

    @SerializedName("MDateJ")
    private String MDateJ;

    @SerializedName("Image")
    private String Image;

    public MD_Notifications(int id, String title, String description, String CDateJ, String MDateJ, String image) {
        Id = id;
        Title = title;
        Description = description;
        this.CDateJ = CDateJ;
        this.MDateJ = MDateJ;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCDateJ() {
        return CDateJ;
    }

    public void setCDateJ(String CDateJ) {
        this.CDateJ = CDateJ;
    }

    public String getMDateJ() {
        return MDateJ;
    }

    public void setMDateJ(String MDateJ) {
        this.MDateJ = MDateJ;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
