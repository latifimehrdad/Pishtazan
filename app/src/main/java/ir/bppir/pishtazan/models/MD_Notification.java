package ir.bppir.pishtazan.models;

public class MD_Notification {


    private Integer id;

    private String body;

    private String title;

    private Integer NType;

    private Integer RType;

    private Integer CustomerId;

    private Integer ColleagueId;

    public MD_Notification(Integer id, String body, String title, Integer NType, Integer RType, Integer customerId, Integer colleagueId) {
        this.id = id;
        this.body = body;
        this.title = title;
        this.NType = NType;
        this.RType = RType;
        CustomerId = customerId;
        ColleagueId = colleagueId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Integer customerId) {
        CustomerId = customerId;
    }

    public Integer getColleagueId() {
        return ColleagueId;
    }

    public void setColleagueId(Integer colleagueId) {
        ColleagueId = colleagueId;
    }
}
