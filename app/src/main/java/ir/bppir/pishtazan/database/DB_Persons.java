package ir.bppir.pishtazan.database;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DB_Persons extends RealmObject {

    @PrimaryKey
    private Integer Id;

    private String Name;

    private String PhoneNumber;

    private String Job;

    private String BirthDay;

    private double Lat;

    private double lng;

    private String Address;

    private boolean Gender;

    private String ImgUrl;

    private Byte Degree;

    private int panelType;

    private Byte PersonType;

    public void insert(String name, String phoneNumber, String job, String birthDay, double lat, double lng, String address, boolean gender, String imgUrl, Byte degree, int paneltype, Byte personType) {
        Name = name;
        PhoneNumber = phoneNumber;
        Job = job;
        BirthDay = birthDay;
        Lat = lat;
        this.lng = lng;
        Address = address;
        Gender = gender;
        ImgUrl = imgUrl;
        Degree = degree;
        panelType = paneltype;
        PersonType = personType;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getJob() {
        return Job;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return Address;
    }

    public boolean isGender() {
        return Gender;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public Byte getDegree() {
        return Degree;
    }


    public Byte getPersonType() {
        return PersonType;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setJob(String job) {
        Job = job;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public void setDegree(Byte degree) {
        Degree = degree;
    }

    public void setPersonType(Byte personType) {
        PersonType = personType;
    }

    public int getPanelType() {
        return panelType;
    }

    public void setPanelType(int panelType) {
        this.panelType = panelType;
    }
}
