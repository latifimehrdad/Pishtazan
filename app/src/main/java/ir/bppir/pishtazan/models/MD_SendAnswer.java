package ir.bppir.pishtazan.models;

import java.util.List;

public class MD_SendAnswer {

    private Integer UserInfoId;

    private Integer ExamResultId;

    private List<MD_Answer> Answers;


    public MD_SendAnswer(Integer userInfoId, Integer examResultId, List<MD_Answer> answers) {
        UserInfoId = userInfoId;
        ExamResultId = examResultId;
        Answers = answers;
    }

    public Integer getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        UserInfoId = userInfoId;
    }

    public List<MD_Answer> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<MD_Answer> answers) {
        Answers = answers;
    }

    public Integer getExamResultId() {
        return ExamResultId;
    }

    public void setExamResultId(Integer examResultId) {
        ExamResultId = examResultId;
    }
}
