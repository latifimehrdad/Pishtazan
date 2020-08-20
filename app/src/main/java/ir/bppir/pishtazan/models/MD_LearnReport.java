package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_LearnReport {

    @SerializedName("Title")
    String Title;

    @SerializedName("AverageGradeValue")
    double AverageGradeValue;

    @SerializedName("TotalScoreValue")
    double TotalScoreValue;

    @SerializedName("TotalActivityValue")
    double TotalActivityValue;

    public MD_LearnReport(String title, double averageGradeValue, double totalScoreValue, double totalActivityValue) {
        Title = title;
        AverageGradeValue = averageGradeValue;
        TotalScoreValue = totalScoreValue;
        TotalActivityValue = totalActivityValue;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getAverageGradeValue() {
        return AverageGradeValue;
    }

    public void setAverageGradeValue(double averageGradeValue) {
        AverageGradeValue = averageGradeValue;
    }

    public double getTotalScoreValue() {
        return TotalScoreValue;
    }

    public void setTotalScoreValue(double totalScoreValue) {
        TotalScoreValue = totalScoreValue;
    }

    public double getTotalActivityValue() {
        return TotalActivityValue;
    }

    public void setTotalActivityValue(double totalActivityValue) {
        TotalActivityValue = totalActivityValue;
    }
}
