package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_ReportDetail {


    @SerializedName("Title")
    private String Title;

    @SerializedName("Value")
    private double Value;

    public MD_ReportDetail(String title, float value) {
        Title = title;
        Value = value;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }
}
