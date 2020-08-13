package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_ExamResults extends MR_Primary {

    @SerializedName("ExamResults")
    List<MD_ExamResult> ExamResults;


    public MR_ExamResults(Integer statue, String message, List<String> messages, List<MD_ExamResult> examResults) {
        super(statue, message, messages);
        ExamResults = examResults;
    }

    public List<MD_ExamResult> getExamResults() {
        return ExamResults;
    }

    public void setExamResults(List<MD_ExamResult> examResults) {
        ExamResults = examResults;
    }
}
