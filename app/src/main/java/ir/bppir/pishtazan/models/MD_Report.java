package ir.bppir.pishtazan.models;

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
    List<MD_ReportDetail> Reports;


    public MD_Report(Integer id, String fullName, String resourceName, List<MD_ReportDetail> reports) {
        Id = id;
        FullName = fullName;
        ResourceName = resourceName;
        Reports = reports;
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
}
