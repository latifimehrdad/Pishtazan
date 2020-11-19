package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_UserInfoVM extends MR_Primary{

    @SerializedName("UserInfoVM")
    MD_UserInfoVM UserInfoVM;

    public MR_UserInfoVM(Integer statue, String message, List<String> messages, MD_UserInfoVM userInfoVM) {
        super(statue, message, messages);
        UserInfoVM = userInfoVM;
    }

    public MD_UserInfoVM getUserInfoVM() {
        return UserInfoVM;
    }

    public void setUserInfoVM(MD_UserInfoVM userInfoVM) {
        UserInfoVM = userInfoVM;
    }
}
