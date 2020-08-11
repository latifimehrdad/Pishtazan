package ir.bppir.pishtazan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_Question extends MR_Primary {

    @SerializedName("Questions")
    private List<MD_Question> Questions;

    public MR_Question(Integer statue, String message, List<String> messages, List<MD_Question> questions) {
        super(statue, message, messages);
        Questions = questions;
    }

    public List<MD_Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<MD_Question> questions) {
        Questions = questions;
    }
}
