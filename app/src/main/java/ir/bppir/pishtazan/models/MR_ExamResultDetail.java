package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_ExamResultDetail extends MR_Primary {

    @SerializedName("Answers")
    List<MD_ExamResultDetail> Answers;

    public MR_ExamResultDetail(Integer statue, String message, List<String> messages, List<MD_ExamResultDetail> answers) {
        super(statue, message, messages);
        Answers = answers;
    }

    public List<MD_ExamResultDetail> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<MD_ExamResultDetail> answers) {
        Answers = answers;
    }
}
