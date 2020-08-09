package ir.bppir.pishtazan.models;

public class MD_QuestionOld {

    private Integer movieId;

    private Integer id;

    private String question;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private Byte userAnswer;


    public MD_QuestionOld(Integer movieId, Integer id, String question, String answerA, String answerB, String answerC, String answerD) {
        this.movieId = movieId;
        this.id = id;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }


    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Byte getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Byte userAnswer) {
        this.userAnswer = userAnswer;
    }
}
