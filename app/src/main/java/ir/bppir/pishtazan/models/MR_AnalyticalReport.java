package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_AnalyticalReport extends MR_Primary {

    @SerializedName("AnalyticalReportVms")
    List<MD_Report> md_analyticalReports;

    public MR_AnalyticalReport(Integer statue, String message, List<String> messages, List<MD_Report> md_analyticalReports) {
        super(statue, message, messages);
        this.md_analyticalReports = md_analyticalReports;
    }

    public List<MD_Report> getMd_analyticalReports() {
        return md_analyticalReports;
    }

    public void setMd_analyticalReports(List<MD_Report> md_analyticalReports) {
        this.md_analyticalReports = md_analyticalReports;
    }
}
