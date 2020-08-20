package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Policy {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("PolicyTypeId")
    Integer PolicyTypeId;

    @SerializedName("PolicyType")
    MD_PolicyType PolicyType;

    @SerializedName("CustomerId")
    Integer CustomerId;

    @SerializedName("SuggestionDateJ")
    String SuggestionDateJ;

    @SerializedName("SuggestionDateM")
    String SuggestionDateM;

    @SerializedName("Description")
    String Description;

    @SerializedName("PolicyAmont")
    Long PolicyAmont;

    @SerializedName("Insured")
    String Insured;

    @SerializedName("InsuredNationalCode")
    String InsuredNationalCode;


    public MD_Policy(Integer id, Integer policyTypeId, MD_PolicyType policyType, Integer customerId, String suggestionDateJ, String suggestionDateM, String description, Long policyAmont, String insured, String insuredNationalCode) {
        Id = id;
        PolicyTypeId = policyTypeId;
        PolicyType = policyType;
        CustomerId = customerId;
        SuggestionDateJ = suggestionDateJ;
        SuggestionDateM = suggestionDateM;
        Description = description;
        PolicyAmont = policyAmont;
        Insured = insured;
        InsuredNationalCode = insuredNationalCode;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getPolicyTypeId() {
        return PolicyTypeId;
    }

    public void setPolicyTypeId(Integer policyTypeId) {
        PolicyTypeId = policyTypeId;
    }

    public MD_PolicyType getPolicyType() {
        return PolicyType;
    }

    public void setPolicyType(MD_PolicyType policyType) {
        PolicyType = policyType;
    }

    public Integer getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Integer customerId) {
        CustomerId = customerId;
    }

    public String getSuggestionDateJ() {
        return SuggestionDateJ;
    }

    public void setSuggestionDateJ(String suggestionDateJ) {
        SuggestionDateJ = suggestionDateJ;
    }

    public String getSuggestionDateM() {
        return SuggestionDateM;
    }

    public void setSuggestionDateM(String suggestionDateM) {
        SuggestionDateM = suggestionDateM;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Long getPolicyAmont() {
        return PolicyAmont;
    }

    public void setPolicyAmont(Long policyAmont) {
        PolicyAmont = policyAmont;
    }

    public String getInsured() {
        return Insured;
    }

    public void setInsured(String insured) {
        Insured = insured;
    }

    public String getInsuredNationalCode() {
        return InsuredNationalCode;
    }

    public void setInsuredNationalCode(String insuredNationalCode) {
        InsuredNationalCode = insuredNationalCode;
    }
}
