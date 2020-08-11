package ir.bppir.pishtazan.models;

public class MD_Answer {

    private Byte UserAnswer;

    private Integer QuestionId;


    public MD_Answer(Byte userAnswer, Integer questionId) {
        UserAnswer = userAnswer;
        QuestionId = questionId;
    }

    public Byte getUserAnswer() {
        return UserAnswer;
    }

    public void setUserAnswer(Byte userAnswer) {
        UserAnswer = userAnswer;
    }

    public Integer getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(Integer questionId) {
        QuestionId = questionId;
    }
}
