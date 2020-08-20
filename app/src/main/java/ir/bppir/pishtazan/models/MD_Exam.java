package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Exam {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Title")
    String Title;

    @SerializedName("ExamTime")
    Integer ExamTime;

    @SerializedName("QuestionsCount")
    Integer QuestionsCount;

    @SerializedName("MinimumScore")
    Integer MinimumScore;

    @SerializedName("MaximumExamScore")
    Integer MaximumExamScore;

    @SerializedName("MinimumExamRate")
    Integer MinimumExamRate;

    @SerializedName("EducationId")
    Integer EducationId;

/*    @SerializedName("Questions")
    List<MD_Question> Questions;*/

    public MD_Exam(Integer id, String title, Integer examTime, Integer questionsCount, Integer minimumScore, Integer maximumExamScore, Integer minimumExamRate, Integer educationId) {
        Id = id;
        Title = title;
        ExamTime = examTime;
        QuestionsCount = questionsCount;
        MinimumScore = minimumScore;
        MaximumExamScore = maximumExamScore;
        MinimumExamRate = minimumExamRate;
        EducationId = educationId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getExamTime() {
        return ExamTime;
    }

    public void setExamTime(Integer examTime) {
        ExamTime = examTime;
    }

    public Integer getQuestionsCount() {
        return QuestionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        QuestionsCount = questionsCount;
    }

    public Integer getMinimumScore() {
        return MinimumScore;
    }

    public void setMinimumScore(Integer minimumScore) {
        MinimumScore = minimumScore;
    }

    public Integer getMaximumExamScore() {
        return MaximumExamScore;
    }

    public void setMaximumExamScore(Integer maximumExamScore) {
        MaximumExamScore = maximumExamScore;
    }

    public Integer getMinimumExamRate() {
        return MinimumExamRate;
    }

    public void setMinimumExamRate(Integer minimumExamRate) {
        MinimumExamRate = minimumExamRate;
    }

    public Integer getEducationId() {
        return EducationId;
    }

    public void setEducationId(Integer educationId) {
        EducationId = educationId;
    }
}
