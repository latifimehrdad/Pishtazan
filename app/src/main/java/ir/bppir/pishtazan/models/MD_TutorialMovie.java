package ir.bppir.pishtazan.models;

public class MD_TutorialMovie {

    private Integer Id;

    private String Title;

    private String Image;

    private String MovieUrl;

    private String movieTime;

    private Integer questionTime;


    public MD_TutorialMovie(Integer id, String title, String image, String movieUrl, String movieTime, Integer questionTime) {
        Id = id;
        Title = title;
        Image = image;
        MovieUrl = movieUrl;
        this.movieTime = movieTime;
        this.questionTime = questionTime;
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

    public String getMovieUrl() {
        return MovieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        MovieUrl = movieUrl;
    }

    public Integer getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Integer questiontime) {
        questionTime = questiontime;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }
}
