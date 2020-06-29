package ir.bppir.pishtazan.models;

public class MD_Notify {

    private Byte notifyType;

    private Byte personType;

    private String stringDate;

    private Long longDate;

    private String stringTime;

    private Long longTime;

    private String Description;

    private String PersonName;

    private String PhoneNumber;


    public MD_Notify(Byte notifyType, Byte personType, String stringDate, Long longDate, String stringTime, Long longTime, String description, String personName, String phoneNumber) {
        this.notifyType = notifyType;
        this.personType = personType;
        this.stringDate = stringDate;
        this.longDate = longDate;
        this.stringTime = stringTime;
        this.longTime = longTime;
        Description = description;
        PersonName = personName;
        PhoneNumber = phoneNumber;
    }

    public Byte getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Byte notifyType) {
        this.notifyType = notifyType;
    }

    public Byte getPersonType() {
        return personType;
    }

    public void setPersonType(Byte personType) {
        this.personType = personType;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public Long getLongDate() {
        return longDate;
    }

    public void setLongDate(Long longDate) {
        this.longDate = longDate;
    }

    public String getStringTime() {
        return stringTime;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
