package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_PolicyType extends MR_Primary {

    @SerializedName("PolicyTypes")
    List<MD_PolicyType> PolicyTypes;


    public MR_PolicyType(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }

    public List<MD_PolicyType> getPolicyTypes() {
        return PolicyTypes;
    }

    public void setPolicyTypes(List<MD_PolicyType> policyTypes) {
        PolicyTypes = policyTypes;
    }
}
