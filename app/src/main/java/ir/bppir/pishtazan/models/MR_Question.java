package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Question extends MR_Primary {

    @SerializedName("Questions")
    private List<MD_Question> Questions;

    @SerializedName("ExamResultId")
    private Integer ExamResultId;


    public MR_Question(Integer statue, String message, List<String> messages, List<MD_Question> questions, Integer examResultId) {
        super(statue, message, messages);
        Questions = questions;
        ExamResultId = examResultId;
    }


    public List<MD_Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<MD_Question> questions) {
        Questions = questions;
    }

    public Integer getExamResultId() {
        return ExamResultId;
    }

    public void setExamResultId(Integer examResultId) {
        ExamResultId = examResultId;
    }
}
