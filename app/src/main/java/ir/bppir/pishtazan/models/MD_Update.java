package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_Update {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("UpdateAddress")
    String UpdateAddress;

    @SerializedName("Vresion")
    Integer Vresion;


    public MD_Update(Integer id, String updateAddress, Integer vresion) {
        Id = id;
        UpdateAddress = updateAddress;
        Vresion = vresion;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUpdateAddress() {
        return UpdateAddress;
    }

    public void setUpdateAddress(String updateAddress) {
        UpdateAddress = updateAddress;
    }

    public Integer getVresion() {
        return Vresion;
    }

    public void setVresion(Integer vresion) {
        Vresion = vresion;
    }
}
