package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_StatisticalReport {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("FullName")
    String FullName;

    @SerializedName("ResourceName")
    String ResourceName;

    @SerializedName("NewDailyCallAction")
    Integer NewDailyCallAction;

    @SerializedName("NewDailySessionAction")
    Integer NewDailySessionAction;

    @SerializedName("CallTracking")
    Integer CallTracking;

    @SerializedName("SessionTracking")
    Integer SessionTracking;

    @SerializedName("TotalPoliciesPrice")
    Integer TotalPoliciesPrice;

    @SerializedName("PolicyCount")
    Integer PolicyCount;

    @SerializedName("PossibleCustomerCount")
    Integer PossibleCustomerCount;

    @SerializedName("PossibleColleagueCount")
    Integer PossibleColleagueCount;

    @SerializedName("CertainCustomerCount")
    Integer CertainCustomerCount;

    @SerializedName("CertainColleagueCount")
    Integer CertainColleagueCount;

    @SerializedName("SubUserCount")
    Integer SubUserCount;

    public MD_StatisticalReport(Integer id, String fullName, String resourceName, Integer newDailyCallAction, Integer newDailySessionAction, Integer callTracking, Integer sessionTracking, Integer totalPoliciesPrice, Integer policyCount, Integer possibleCustomerCount, Integer possibleColleagueCount, Integer certainCustomerCount, Integer certainColleagueCount, Integer subUserCount) {
        Id = id;
        FullName = fullName;
        ResourceName = resourceName;
        NewDailyCallAction = newDailyCallAction;
        NewDailySessionAction = newDailySessionAction;
        CallTracking = callTracking;
        SessionTracking = sessionTracking;
        TotalPoliciesPrice = totalPoliciesPrice;
        PolicyCount = policyCount;
        PossibleCustomerCount = possibleCustomerCount;
        PossibleColleagueCount = possibleColleagueCount;
        CertainCustomerCount = certainCustomerCount;
        CertainColleagueCount = certainColleagueCount;
        SubUserCount = subUserCount;
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

    public Integer getNewDailyCallAction() {
        return NewDailyCallAction;
    }

    public void setNewDailyCallAction(Integer newDailyCallAction) {
        NewDailyCallAction = newDailyCallAction;
    }

    public Integer getNewDailySessionAction() {
        return NewDailySessionAction;
    }

    public void setNewDailySessionAction(Integer newDailySessionAction) {
        NewDailySessionAction = newDailySessionAction;
    }

    public Integer getCallTracking() {
        return CallTracking;
    }

    public void setCallTracking(Integer callTracking) {
        CallTracking = callTracking;
    }

    public Integer getSessionTracking() {
        return SessionTracking;
    }

    public void setSessionTracking(Integer sessionTracking) {
        SessionTracking = sessionTracking;
    }

    public Integer getTotalPoliciesPrice() {
        return TotalPoliciesPrice;
    }

    public void setTotalPoliciesPrice(Integer totalPoliciesPrice) {
        TotalPoliciesPrice = totalPoliciesPrice;
    }

    public Integer getPolicyCount() {
        return PolicyCount;
    }

    public void setPolicyCount(Integer policyCount) {
        PolicyCount = policyCount;
    }

    public Integer getPossibleCustomerCount() {
        return PossibleCustomerCount;
    }

    public void setPossibleCustomerCount(Integer possibleCustomerCount) {
        PossibleCustomerCount = possibleCustomerCount;
    }

    public Integer getPossibleColleagueCount() {
        return PossibleColleagueCount;
    }

    public void setPossibleColleagueCount(Integer possibleColleagueCount) {
        PossibleColleagueCount = possibleColleagueCount;
    }

    public Integer getCertainCustomerCount() {
        return CertainCustomerCount;
    }

    public void setCertainCustomerCount(Integer certainCustomerCount) {
        CertainCustomerCount = certainCustomerCount;
    }

    public Integer getCertainColleagueCount() {
        return CertainColleagueCount;
    }

    public void setCertainColleagueCount(Integer certainColleagueCount) {
        CertainColleagueCount = certainColleagueCount;
    }

    public Integer getSubUserCount() {
        return SubUserCount;
    }

    public void setSubUserCount(Integer subUserCount) {
        SubUserCount = subUserCount;
    }
}
