package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

public class MD_UserInfoVM {

    @SerializedName("Name")
    String Name;

    @SerializedName("Wallet")
    double Wallet;

    @SerializedName("Image")
    String Image;

    @SerializedName("Scores")
    int Scores;

    @SerializedName("privileges")
    int privileges;


    public MD_UserInfoVM(String name, double wallet, String image, int scores, int privileges) {
        Name = name;
        Wallet = wallet;
        Image = image;
        Scores = scores;
        this.privileges = privileges;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getWallet() {
        return Wallet;
    }

    public void setWallet(double wallet) {
        Wallet = wallet;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getScores() {
        return Scores;
    }

    public void setScores(int scores) {
        Scores = scores;
    }

    public int getPrivileges() {
        return privileges;
    }

    public void setPrivileges(int privileges) {
        this.privileges = privileges;
    }
}
