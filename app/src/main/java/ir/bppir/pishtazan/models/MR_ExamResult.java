package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_ExamResult extends MR_Primary {

    @SerializedName("ExamResult")
    MD_ExamResult ExamResult;


    public MR_ExamResult(Integer statue, String message, List<String> messages, MD_ExamResult examResult) {
        super(statue, message, messages);
        ExamResult = examResult;
    }

    public MD_ExamResult getExamResult() {
        return ExamResult;
    }

    public void setExamResult(MD_ExamResult examResult) {
        ExamResult = examResult;
    }
}
