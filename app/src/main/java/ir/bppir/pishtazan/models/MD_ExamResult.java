package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_ExamResult {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("ExamDateJ")
    String ExamDateJ;

    @SerializedName("CorrectAnswerCount")
    Integer CorrectAnswerCount;

    @SerializedName("WrongAnswerCount")
    Integer WrongAnswerCount;

    @SerializedName("AcceptancePercentage")
    float AverageGrade;

    @SerializedName("ExamResultStatus")
    Integer ExamResultStatus;

    @SerializedName("EducationId")
    Integer EducationId;

    @SerializedName("NotAnswered")
    Integer NotAnswered;

    Integer TotalQuestion;

    public MD_ExamResult(Integer id, String examDateJ, Integer correctAnswerCount, Integer wrongAnswerCount, float averageGrade, Integer examResultStatus, Integer educationId, Integer notAnswered) {
        Id = id;
        ExamDateJ = examDateJ;
        CorrectAnswerCount = correctAnswerCount;
        WrongAnswerCount = wrongAnswerCount;
        AverageGrade = averageGrade;
        ExamResultStatus = examResultStatus;
        EducationId = educationId;
        NotAnswered = notAnswered;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getExamDateJ() {
        return ExamDateJ;
    }

    public void setExamDateJ(String examDateJ) {
        ExamDateJ = examDateJ;
    }

    public Integer getCorrectAnswerCount() {
        return CorrectAnswerCount;
    }

    public void setCorrectAnswerCount(Integer correctAnswerCount) {
        CorrectAnswerCount = correctAnswerCount;
    }

    public Integer getWrongAnswerCount() {
        return WrongAnswerCount;
    }

    public void setWrongAnswerCount(Integer wrongAnswerCount) {
        WrongAnswerCount = wrongAnswerCount;
    }

    public float getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        AverageGrade = averageGrade;
    }

    public Integer getExamResultStatus() {
        return ExamResultStatus;
    }

    public void setExamResultStatus(Integer examResultStatus) {
        ExamResultStatus = examResultStatus;
    }

    public Integer getEducationId() {
        return EducationId;
    }

    public void setEducationId(Integer educationId) {
        EducationId = educationId;
    }

    public Integer getNotAnswered() {
        return NotAnswered;
    }

    public void setNotAnswered(Integer notAnswered) {
        NotAnswered = notAnswered;
    }
}
