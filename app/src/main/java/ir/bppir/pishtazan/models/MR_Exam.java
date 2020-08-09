package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Exam extends MR_Primary {

    @SerializedName("Exam")
    MD_Exam Exam;

    public MR_Exam(Integer statue, String message, List<String> messages, MD_Exam exam) {
        super(statue, message, messages);
        Exam = exam;
    }

    public MD_Exam getExam() {
        return Exam;
    }

    public void setExam(MD_Exam exam) {
        Exam = exam;
    }
}
