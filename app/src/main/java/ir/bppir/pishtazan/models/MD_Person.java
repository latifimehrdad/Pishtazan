package ir.bppir.pishtazan.models;

import com.google.android.gms.maps.model.LatLng;

public class MD_Person {

    private Integer Id;

    private String Name;

    private String PhoneNumber;

    private String Job;

    private String BirthDay;

    private LatLng Location;

    private String Address;

    private boolean Gender;

    private String ImgUrl;

    public MD_Person(Integer id, String name, String phoneNumber, String job, String birthDay, LatLng location, String address, boolean gender, String imgUrl) {
        Id = id;
        Name = name;
        PhoneNumber = phoneNumber;
        Job = job;
        BirthDay = birthDay;
        Location = location;
        Address = address;
        Gender = gender;
        ImgUrl = imgUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public LatLng getLocation() {
        return Location;
    }

    public void setLocation(LatLng location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
