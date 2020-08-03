package ir.bppir.pishtazan.models;

public class MD_Post {

    private Integer Id;

    private String Title;

    public MD_Post(Integer id, String title) {
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
