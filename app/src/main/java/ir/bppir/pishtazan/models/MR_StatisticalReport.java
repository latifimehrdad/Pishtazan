package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_StatisticalReport extends MR_Primary {

    @SerializedName("StatisticalReportVms")
    List<MD_Report> StatisticalReportVms;

    public MR_StatisticalReport(Integer statue, String message, List<String> messages, List<MD_Report> statisticalReportVms) {
        super(statue, message, messages);
        StatisticalReportVms = statisticalReportVms;
    }

    public List<MD_Report> getStatisticalReportVms() {
        return StatisticalReportVms;
    }

    public void setStatisticalReportVms(List<MD_Report> statisticalReportVms) {
        StatisticalReportVms = statisticalReportVms;
    }
}
