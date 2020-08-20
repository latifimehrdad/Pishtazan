package ir.bppir.pishtazan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_Report {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("FullName")
    String FullName;

    @SerializedName("ResourceName")
    String ResourceName;

    @SerializedName("Reports")
    @Expose
    List<MD_ReportDetail> Reports;

    @SerializedName("LearningReports")
    @Expose
    List<MD_LearnReport> LearningReports;

    public MD_Report(Integer id, String fullName, String resourceName, List<MD_ReportDetail> reports, List<MD_LearnReport> learningReports) {
        Id = id;
        FullName = fullName;
        ResourceName = resourceName;
        Reports = reports;
        LearningReports = learningReports;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
    }

    public List<MD_ReportDetail> getReports() {
        return Reports;
    }

    public void setReports(List<MD_ReportDetail> reports) {
        Reports = reports;
    }

    public List<MD_LearnReport> getLearningReports() {
        return LearningReports;
    }

    public void setLearningReports(List<MD_LearnReport> learningReports) {
        LearningReports = learningReports;
    }
}
