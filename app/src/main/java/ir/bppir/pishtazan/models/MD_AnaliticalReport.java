package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_AnaliticalReport {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("FullName")
    String FullName;

    @SerializedName("ResourceName")
    String ResourceName;

    @SerializedName("CustomerConvertPercent")
    double CustomerConvertPercent;

    @SerializedName("PolicyConvertPercent")
    double PolicyConvertPercent;

    @SerializedName("UserConvertPercent")
    double UserConvertPercent;


    public MD_AnaliticalReport(Integer id, String fullName, String resourceName, double customerConvertPercent, double policyConvertPercent, double userConvertPercent) {
        Id = id;
        FullName = fullName;
        ResourceName = resourceName;
        CustomerConvertPercent = customerConvertPercent;
        PolicyConvertPercent = policyConvertPercent;
        UserConvertPercent = userConvertPercent;
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

    public double getCustomerConvertPercent() {
        return CustomerConvertPercent;
    }

    public void setCustomerConvertPercent(double customerConvertPercent) {
        CustomerConvertPercent = customerConvertPercent;
    }

    public double getPolicyConvertPercent() {
        return PolicyConvertPercent;
    }

    public void setPolicyConvertPercent(double policyConvertPercent) {
        PolicyConvertPercent = policyConvertPercent;
    }

    public double getUserConvertPercent() {
        return UserConvertPercent;
    }

    public void setUserConvertPercent(double userConvertPercent) {
        UserConvertPercent = userConvertPercent;
    }
}
