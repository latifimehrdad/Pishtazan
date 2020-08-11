package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Question {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("QuestionText")
    String QuestionText;

    @SerializedName("FirstChoose")
    String FirstChoose;

    @SerializedName("SecondChoose")
    String SecondChoose;

    @SerializedName("ThirdChoose")
    String ThirdChoose;

    @SerializedName("ForthChoose")
    String ForthChoose;

    @SerializedName("CorrectAnswer")
    Integer CorrectAnswer;

    @SerializedName("QuestionScore")
    Integer QuestionScore;

    @SerializedName("ExamId")
    Integer ExamId;

    private Byte userAnswer = -1;


    public MD_Question(Integer id, String questionText, String firstChoose, String secondChoose, String thirdChoose, String forthChoose, Integer correctAnswer, Integer questionScore, Integer examId) {
        Id = id;
        QuestionText = questionText;
        FirstChoose = firstChoose;
        SecondChoose = secondChoose;
        ThirdChoose = thirdChoose;
        ForthChoose = forthChoose;
        CorrectAnswer = correctAnswer;
        QuestionScore = questionScore;
        ExamId = examId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }

    public String getFirstChoose() {
        return FirstChoose;
    }

    public void setFirstChoose(String firstChoose) {
        FirstChoose = firstChoose;
    }

    public String getSecondChoose() {
        return SecondChoose;
    }

    public void setSecondChoose(String secondChoose) {
        SecondChoose = secondChoose;
    }

    public String getThirdChoose() {
        return ThirdChoose;
    }

    public void setThirdChoose(String thirdChoose) {
        ThirdChoose = thirdChoose;
    }

    public String getForthChoose() {
        return ForthChoose;
    }

    public void setForthChoose(String forthChoose) {
        ForthChoose = forthChoose;
    }

    public Integer getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public Integer getQuestionScore() {
        return QuestionScore;
    }

    public void setQuestionScore(Integer questionScore) {
        QuestionScore = questionScore;
    }

    public Integer getExamId() {
        return ExamId;
    }

    public void setExamId(Integer examId) {
        ExamId = examId;
    }

    public Byte getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Byte userAnswer) {
        this.userAnswer = userAnswer;
    }
}
