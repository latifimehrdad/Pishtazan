package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MD_Transaction {

    @SerializedName("Id")
    private int Id;

    @SerializedName("UserInfoId")
    private int UserInfoId;

    @SerializedName("UserInfo")
    private MD_UserInfo UserInfo;

    @SerializedName("DateM")
    private Date DateM;

    @SerializedName("DateJ")
    private String DateJ;

    @SerializedName("Status")
    private boolean Status;

    @SerializedName("Price")
    private Long Price;

    @SerializedName("RefrenceCode")
    private String RefrenceCode;


    public MD_Transaction(int id, int userInfoId, MD_UserInfo userInfo, Date dateM, String dateJ, boolean status, Long price, String refrenceCode) {
        Id = id;
        UserInfoId = userInfoId;
        UserInfo = userInfo;
        DateM = dateM;
        DateJ = dateJ;
        Status = status;
        Price = price;
        RefrenceCode = refrenceCode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        UserInfoId = userInfoId;
    }

    public MD_UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(MD_UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public Date getDateM() {
        return DateM;
    }

    public void setDateM(Date dateM) {
        DateM = dateM;
    }

    public String getDateJ() {
        return DateJ;
    }

    public void setDateJ(String dateJ) {
        DateJ = dateJ;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getRefrenceCode() {
        return RefrenceCode;
    }

    public void setRefrenceCode(String refrenceCode) {
        RefrenceCode = refrenceCode;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }
}
