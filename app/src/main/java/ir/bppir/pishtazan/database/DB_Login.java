package ir.bppir.pishtazan.database;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;

public class DB_Login extends RealmObject {

    Integer Id;

    String FullName;

    Integer LocationStateId;

    String PhoneNumber;

    String MobileNumber;

    Boolean MobileNumberConfirmed;

    String Description;

    Date CDate;

    Date MDate;

    Date DDate;

    String BrithDateJ;

    Date BrithDateM;

    String Address;

    double Lat;

    double Lang;

    String Image;

    Integer UserInfoId;

    String NationalCode;

    boolean IsDelete;

    String UserName;

    Integer ResourceId;

    Integer ColleagueId;

    String UserId;

    public void insert(Integer id, String fullName, Integer locationStateId, String phoneNumber, String mobileNumber, Boolean mobileNumberConfirmed, String description, Date CDate, Date MDate, Date DDate, String brithDateJ, Date brithDateM, String address, double lat, double lang, String image, Integer userInfoId, String nationalCode, boolean isDelete, String userName, Integer resourceId, Integer colleagueId, String userId) {
        Id = id;
        FullName = fullName;
        LocationStateId = locationStateId;
        PhoneNumber = phoneNumber;
        MobileNumber = mobileNumber;
        MobileNumberConfirmed = mobileNumberConfirmed;
        Description = description;
        this.CDate = CDate;
        this.MDate = MDate;
        this.DDate = DDate;
        BrithDateJ = brithDateJ;
        BrithDateM = brithDateM;
        Address = address;
        Lat = lat;
        Lang = lang;
        Image = image;
        UserInfoId = userInfoId;
        NationalCode = nationalCode;
        IsDelete = isDelete;
        UserName = userName;
        ResourceId = resourceId;
        ColleagueId = colleagueId;
        UserId = userId;
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

    public Boolean getMobileNumberConfirmed() {
        return MobileNumberConfirmed;
    }

    public void setMobileNumberConfirmed(Boolean mobileNumberConfirmed) {
        MobileNumberConfirmed = mobileNumberConfirmed;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCDate() {
        return CDate;
    }

    public void setCDate(Date CDate) {
        this.CDate = CDate;
    }

    public Date getMDate() {
        return MDate;
    }

    public void setMDate(Date MDate) {
        this.MDate = MDate;
    }

    public Date getDDate() {
        return DDate;
    }

    public void setDDate(Date DDate) {
        this.DDate = DDate;
    }

    public String getBrithDateJ() {
        return BrithDateJ;
    }

    public void setBrithDateJ(String brithDateJ) {
        BrithDateJ = brithDateJ;
    }

    public Date getBrithDateM() {
        return BrithDateM;
    }

    public void setBrithDateM(Date brithDateM) {
        BrithDateM = brithDateM;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getResourceId() {
        return ResourceId;
    }

    public void setResourceId(Integer resourceId) {
        ResourceId = resourceId;
    }

    public Integer getColleagueId() {
        return ColleagueId;
    }

    public void setColleagueId(Integer colleagueId) {
        ColleagueId = colleagueId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
