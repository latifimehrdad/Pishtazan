package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_ExamResultDetail {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("UserAnswer")
    Integer UserAnswer;

    @SerializedName("AnswerStatus")
    Integer AnswerStatus;

    @SerializedName("QuestionId")
    Integer QuestionId;

    @SerializedName("Question")
    MD_Question Question;

    public MD_ExamResultDetail(Integer id, Integer userAnswer, Integer answerStatus, Integer questionId, MD_Question question) {
        Id = id;
        UserAnswer = userAnswer;
        AnswerStatus = answerStatus;
        QuestionId = questionId;
        Question = question;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserAnswer() {
        return UserAnswer;
    }

    public void setUserAnswer(Integer userAnswer) {
        UserAnswer = userAnswer;
    }

    public Integer getAnswerStatus() {
        return AnswerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        AnswerStatus = answerStatus;
    }

    public Integer getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(Integer questionId) {
        QuestionId = questionId;
    }

    public MD_Question getQuestion() {
        return Question;
    }

    public void setQuestion(MD_Question question) {
        Question = question;
    }
}
