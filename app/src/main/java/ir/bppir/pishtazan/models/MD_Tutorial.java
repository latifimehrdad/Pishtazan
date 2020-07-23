package ir.bppir.pishtazan.models;

public class MD_Tutorial {

    private Integer Id;

    private String Title;

    private String image;

    private Integer Level;

    public MD_Tutorial(Integer id, String title, String image, Integer level) {
        Id = id;
        Title = title;
        this.image = image;
        Level = level;
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
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = level;
    }
}
