package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_SpinnerItem extends MR_Primary {

    @SerializedName("Resources")
    List<MD_SpinnerItem> Resources;

    public MR_SpinnerItem(Integer statue, String message, List<String> messages, List<MD_SpinnerItem> resources) {
        super(statue, message, messages);
        Resources = resources;
    }

    public List<MD_SpinnerItem> getResources() {
        return Resources;
    }

    public void setResources(List<MD_SpinnerItem> resources) {
        Resources = resources;
    }
}
