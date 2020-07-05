package ir.bppir.pishtazan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_RequestGetAllPerson extends MD_RequestPrimary {

    @SerializedName("Customer")
    @Expose
    MD_Person Customer;

    @SerializedName("Customers")
    @Expose
    List<MD_Person> Customers;

    @SerializedName("Colleague")
    @Expose
    MD_Person Colleague;

    @SerializedName("Colleagues")
    @Expose
    List<MD_Person> Colleagues;


    public MD_RequestGetAllPerson(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }


    public MD_Person getCustomer() {
        return Customer;
    }

    public void setCustomer(MD_Person customer) {
        Customer = customer;
    }

    public List<MD_Person> getCustomers() {
        return Customers;
    }

    public void setCustomers(List<MD_Person> customers) {
        Customers = customers;
    }

    public MD_Person getColleague() {
        return Colleague;
    }

    public void setColleague(MD_Person colleague) {
        Colleague = colleague;
    }

    public List<MD_Person> getColleagues() {
        return Colleagues;
    }

    public void setColleagues(List<MD_Person> colleagues) {
        Colleagues = colleagues;
    }
}
