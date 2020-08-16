package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_AnaliticalReport extends MR_Primary {

    @SerializedName("AnalyticalReportVms")
    List<MD_AnaliticalReport> md_analiticalReports;

    public MR_AnaliticalReport(Integer statue, String message, List<String> messages, List<MD_AnaliticalReport> md_analiticalReports) {
        super(statue, message, messages);
        this.md_analiticalReports = md_analiticalReports;
    }

    public List<MD_AnaliticalReport> getMd_analiticalReports() {
        return md_analiticalReports;
    }

    public void setMd_analiticalReports(List<MD_AnaliticalReport> md_analiticalReports) {
        this.md_analiticalReports = md_analiticalReports;
    }
}
