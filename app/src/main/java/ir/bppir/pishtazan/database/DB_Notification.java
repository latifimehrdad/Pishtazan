package ir.bppir.pishtazan.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.bppir.pishtazan.models.MD_Notify;

public class DB_Notification extends RealmObject {

    @PrimaryKey
    private Integer Id;

    private Byte notifyType;

    private Byte personType;

    private String stringDate;

    private Long longDate;

    private String stringTime;

    private Long longTime;

    private String Description;

    private String PersonName;

    private String PhoneNumber;

    public void insert(MD_Notify md_notify) {
        setNotifyType(md_notify.getNotifyType());
        setPersonType(md_notify.getPersonType());
        setStringDate(md_notify.getStringDate());
        setLongDate(md_notify.getLongDate());
        setStringTime(md_notify.getStringTime());
        setLongTime(md_notify.getLongTime());
        setDescription(getDescription());
        setPersonName(md_notify.getPersonName());
        setPhoneNumber(md_notify.getPhoneNumber());
    }


    public Integer getId() {
        return Id;
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
