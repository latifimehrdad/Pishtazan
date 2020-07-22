package ir.bppir.pishtazan.models;

import java.util.List;

public class MD_Report {

    private String personName;

    private String image;

    private List<MD_ReportDetail> details;

    public MD_Report(String personName, List<MD_ReportDetail> details, String image) {
        this.personName = personName;
        this.details = details;
        this.image = image;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<MD_ReportDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MD_ReportDetail> details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
