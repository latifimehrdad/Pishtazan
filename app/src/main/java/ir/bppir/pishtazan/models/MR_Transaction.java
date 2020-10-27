package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Transaction extends MR_Primary{

    @SerializedName("Transactions")
    private List<MD_Transaction> Transactions;

    public MR_Transaction(Integer statue, String message, List<String> messages, List<MD_Transaction> transactions) {
        super(statue, message, messages);
        Transactions = transactions;
    }

    public List<MD_Transaction> getTransactions() {
        return Transactions;
    }

    public void setTransactions(List<MD_Transaction> transactions) {
        Transactions = transactions;
    }
}
