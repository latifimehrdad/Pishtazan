package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_RequestPolicyType extends MD_RequestPrimary {

    @SerializedName("PolicyTypes")
    List<MD_PolicyType> PolicyTypes;


    public MD_RequestPolicyType(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }

    public List<MD_PolicyType> getPolicyTypes() {
        return PolicyTypes;
    }

    public void setPolicyTypes(List<MD_PolicyType> policyTypes) {
        PolicyTypes = policyTypes;
    }
}
