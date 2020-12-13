package ir.bppir.pishtazan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import butterknife.BindView;

public class MD_Person {

    Integer Id;

    @SerializedName("FullName")
    String FullName;

    @SerializedName("LocationStateId")
    Integer LocationStateId;

    @SerializedName("PhoneNumber")
    String PhoneNumber;

    @SerializedName("MobileNumber")
    String MobileNumber;

    @SerializedName("Description")
    String Description;

    @SerializedName("CDate")
    String CDate;

    @SerializedName("MDate")
    String MDate;

    @SerializedName("DDate")
    String DDate;

    @SerializedName("BirthDateJ")
    String BirthDateJ;

    @SerializedName("BirthDateM")
    String BirthDateM;

    @SerializedName("Address")
    String Address;

    @SerializedName("Lat")
    double Lat;

    @SerializedName("Lang")
    double Lang;

    @SerializedName("Image")
    String Image;

    @SerializedName("UserInfoId")
    Integer UserInfoId;

    @SerializedName("UserInfo")
    MD_UserInfo UserInfo;

    @SerializedName("NationalCode")
    String NationalCode;

    @SerializedName("IsDelete")
    boolean IsDelete;

    @SerializedName("Level")
    Integer Level;

    @SerializedName("CompletenessPercent")
    float CompletenessPercent;

    @SerializedName("Policies")
    List<MD_Policy> Policies;


    @SerializedName("ApiRecieved")
    boolean ApiRecieved;

    @SerializedName("CustomerStatus")
    @Expose
    Integer CustomerStatus;

    @SerializedName("ColleagueStatus")
    @Expose
    Integer ColleagueStatus;

    @SerializedName("SendSMS")
    boolean SendSMS;

    public MD_Person() {
    }

    public MD_Person(Integer id, String fullName, Integer locationStateId, String phoneNumber, String mobileNumber, String description, String CDate, String MDate, String DDate, String birthDateJ, String birthDateM, String address, double lat, double lang, String image, Integer userInfoId, MD_UserInfo userInfo, String nationalCode, boolean isDelete, Integer level, float completenessPercent, List<MD_Policy> policies, boolean apiRecieved, Integer customerStatus, Integer colleagueStatus, boolean sendSMS) {
        Id = id;
        FullName = fullName;
        LocationStateId = locationStateId;
        PhoneNumber = phoneNumber;
        MobileNumber = mobileNumber;
        Description = description;
        this.CDate = CDate;
        this.MDate = MDate;
        this.DDate = DDate;
        BirthDateJ = birthDateJ;
        BirthDateM = birthDateM;
        Address = address;
        Lat = lat;
        Lang = lang;
        Image = image;
        UserInfoId = userInfoId;
        UserInfo = userInfo;
        NationalCode = nationalCode;
        IsDelete = isDelete;
        Level = level;
        CompletenessPercent = completenessPercent;
        Policies = policies;
        ApiRecieved = apiRecieved;
        CustomerStatus = customerStatus;
        ColleagueStatus = colleagueStatus;
        SendSMS = sendSMS;
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

    public Integer getLocationStateId() {
        return LocationStateId;
    }

    public void setLocationStateId(Integer locationStateId) {
        LocationStateId = locationStateId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getColleagueStatus() {
        return ColleagueStatus;
    }

    public void setColleagueStatus(Integer colleagueStatus) {
        ColleagueStatus = colleagueStatus;
    }

    public String getCDate() {
        return CDate;
    }

    public void setCDate(String CDate) {
        this.CDate = CDate;
    }

    public String getMDate() {
        return MDate;
    }

    public void setMDate(String MDate) {
        this.MDate = MDate;
    }

    public String getDDate() {
        return DDate;
    }

    public void setDDate(String DDate) {
        this.DDate = DDate;
    }

    public String getBirthDateJ() {
        return BirthDateJ;
    }

    public void setBirthDateJ(String birthDateJ) {
        BirthDateJ = birthDateJ;
    }

    public String getBirthDateM() {
        return BirthDateM;
    }

    public void setBirthDateM(String birthDateM) {
        BirthDateM = birthDateM;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLang() {
        return Lang;
    }

    public void setLang(double lang) {
        Lang = lang;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Integer getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        UserInfoId = userInfoId;
    }

    public MD_UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(MD_UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = level;
    }

    public float getCompletenessPercent() {
        return CompletenessPercent;
    }

    public void setCompletenessPercent(float completenessPercent) {
        CompletenessPercent = completenessPercent;
    }

    public List<MD_Policy> getPolicies() {
        return Policies;
    }

    public void setPolicies(List<MD_Policy> policies) {
        Policies = policies;
    }

    public boolean isApiRecieved() {
        return ApiRecieved;
    }

    public void setApiRecieved(boolean apiRecieved) {
        ApiRecieved = apiRecieved;
    }

    public Integer getCustomerStatus() {
        return CustomerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
        CustomerStatus = customerStatus;
    }

    public boolean isSendSMS() {
        return SendSMS;
    }

    public void setSendSMS(boolean sendSMS) {
        SendSMS = sendSMS;
    }
}
