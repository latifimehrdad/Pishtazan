package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Policy extends MR_Primary {

    @SerializedName("Policy")
    MD_Policy Policy;

    @SerializedName("Policies")
    List<MD_Policy> Policies;

    public MR_Policy(Integer statue, String message, List<String> messages, MD_Policy policy, List<MD_Policy> policies) {
        super(statue, message, messages);
        Policy = policy;
        Policies = policies;
    }

    public MD_Policy getPolicy() {
        return Policy;
    }

    public void setPolicy(MD_Policy policy) {
        Policy = policy;
    }

    public List<MD_Policy> getPolicies() {
        return Policies;
    }

    public void setPolicies(List<MD_Policy> policies) {
        Policies = policies;
    }
}
