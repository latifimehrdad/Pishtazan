package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_RequestGetAllCustomers extends MD_RequestPrimary {

    @SerializedName("Customer")
    MD_Customer Customer;

    @SerializedName("Customers")
    List<MD_Customer> Customers;


    public MD_RequestGetAllCustomers(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }

    public MD_Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(MD_Customer customer) {
        Customer = customer;
    }

    public List<MD_Customer> getCustomers() {
        return Customers;
    }

    public void setCustomers(List<MD_Customer> customers) {
        Customers = customers;
    }
}
