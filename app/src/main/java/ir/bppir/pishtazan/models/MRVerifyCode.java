package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.bppir.pishtazan.database.DB_UserInfo;

public class MRVerifyCode extends MR_Primary {

    @SerializedName("UserInfo")
    DB_UserInfo UserInfo;


    public MRVerifyCode(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }


    public DB_UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(DB_UserInfo userInfo) {
        UserInfo = userInfo;
    }
}
