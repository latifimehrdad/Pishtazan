package ir.bppir.pishtazan.models;

public class MD_Notification {

    private Integer id;

    private String body;

    private Integer NType;

    private Integer RType;

    private String title;

    private Integer PersonId;

    private Byte PersonType;


    public MD_Notification(Integer id, String body, Integer NType, Integer RType, String title, Integer personId, Byte personType) {
        this.id = id;
        this.body = body;
        this.NType = NType;
        this.RType = RType;
        this.title = title;
        PersonId = personId;
        PersonType = personType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getNType() {
        return NType;
    }

    public void setNType(Integer NType) {
        this.NType = NType;
    }

    public Integer getRType() {
        return RType;
    }

    public void setRType(Integer RType) {
        this.RType = RType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPersonId() {
        return PersonId;
    }

    public void setPersonId(Integer personId) {
        PersonId = personId;
    }

    public Byte getPersonType() {
        return PersonType;
    }

    public void setPersonType(Byte personType) {
        PersonType = personType;
    }
}
