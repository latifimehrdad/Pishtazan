package ir.bppir.pishtazan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MD_EducationFiles {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Title")
    String Title;

    @SerializedName("CDate")
    String CDate;

    @SerializedName("FileType")
    Integer FileType;

    @SerializedName("FileSize")
    String FileSize;

    @SerializedName("FilePath")
    String FilePath;

    @SerializedName("EducationId")
    Integer EducationId;

    @SerializedName("Duration")
    @Expose
    String duration;

    @SerializedName("Thumbnail")
    @Expose
    String thumbnail;

    public MD_EducationFiles(Integer id, String title, String CDate, Integer fileType, String fileSize, String filePath, Integer educationId) {
        Id = id;
        Title = title;
        this.CDate = CDate;
        FileType = fileType;
        FileSize = fileSize;
        FilePath = filePath;
        EducationId = educationId;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCDate() {
        return CDate;
    }

    public void setCDate(String CDate) {
        this.CDate = CDate;
    }

    public Integer getFileType() {
        return FileType;
    }

    public void setFileType(Integer fileType) {
        FileType = fileType;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public Integer getEducationId() {
        return EducationId;
    }

    public void setEducationId(Integer educationId) {
        EducationId = educationId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
