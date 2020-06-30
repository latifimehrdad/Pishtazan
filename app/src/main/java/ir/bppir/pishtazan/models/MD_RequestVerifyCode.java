package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MD_RequestVerifyCode extends MD_RequestPrimary {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("FullName")
    String FullName;

    @SerializedName("LocationStateId")
    Integer LocationStateId;

    @SerializedName("PhoneNumber")
    String PhoneNumber;

    @SerializedName("MobileNumber")
    String MobileNumber;

    @SerializedName("MobileNumberConfirmed")
    Boolean MobileNumberConfirmed;

    @SerializedName("Description")
    String Description;

    @SerializedName("CDate")
    Date CDate;

    @SerializedName("MDate")
    Date MDate;

    @SerializedName("DDate")
    Date DDate;

    @SerializedName("BrithDateJ")
    String BrithDateJ;

    @SerializedName("BrithDateM")
    Date BrithDateM;

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

    @SerializedName("NationalCode")
    String NationalCode;

    @SerializedName("IsDelete")
    boolean IsDelete;

    @SerializedName("UserName")
    String UserName;

    @SerializedName("ResourceId")
    Integer ResourceId;

    @SerializedName("ColleagueId")
    Integer ColleagueId;

    @SerializedName("UserId")
    String UserId;


    public MD_RequestVerifyCode(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }


    public Integer getId() {
        return Id;
    }

    public String getFullName() {
        return FullName;
    }

    public Integer getLocationStateId() {
        return LocationStateId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public Boolean getMobileNumberConfirmed() {
        return MobileNumberConfirmed;
    }

    public String getDescription() {
        return Description;
    }

    public Date getCDate() {
        return CDate;
    }

    public Date getMDate() {
        return MDate;
    }

    public Date getDDate() {
        return DDate;
    }

    public String getBrithDateJ() {
        return BrithDateJ;
    }

    public Date getBrithDateM() {
        return BrithDateM;
    }

    public String getAddress() {
        return Address;
    }

    public double getLat() {
        return Lat;
    }

    public double getLang() {
        return Lang;
    }

    public String getImage() {
        return Image;
    }

    public Integer getUserInfoId() {
        return UserInfoId;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public String getUserName() {
        return UserName;
    }

    public Integer getResourceId() {
        return ResourceId;
    }

    public Integer getColleagueId() {
        return ColleagueId;
    }

    public String getUserId() {
        return UserId;
    }
}
