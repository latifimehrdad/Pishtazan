package ir.bppir.pishtazan.daggers.retrofit;


import java.util.Map;

import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.models.MD_SendAnswer;
import ir.bppir.pishtazan.models.MD_Update;
import ir.bppir.pishtazan.models.MR_AnalyticalReport;
import ir.bppir.pishtazan.models.MR_Education;
import ir.bppir.pishtazan.models.MR_EducationCategoryVms;
import ir.bppir.pishtazan.models.MR_EducationFiles;
import ir.bppir.pishtazan.models.MR_Exam;
import ir.bppir.pishtazan.models.MR_ExamResult;
import ir.bppir.pishtazan.models.MR_ExamResultDetail;
import ir.bppir.pishtazan.models.MR_ExamResults;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_LastEducation;
import ir.bppir.pishtazan.models.MR_Notifications;
import ir.bppir.pishtazan.models.MR_Payment;
import ir.bppir.pishtazan.models.MR_PersonNumber;
import ir.bppir.pishtazan.models.MR_Policy;
import ir.bppir.pishtazan.models.MR_PolicyType;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.models.MRVerifyCode;
import ir.bppir.pishtazan.models.MR_Question;
import ir.bppir.pishtazan.models.MR_Reminder;
import ir.bppir.pishtazan.models.MR_SpinnerItem;
import ir.bppir.pishtazan.models.MR_StatisticalReport;
import ir.bppir.pishtazan.models.MR_Transaction;
import ir.bppir.pishtazan.models.MR_UserInfoVM;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApiInterface {

    String Version = "/api";
    String Policy = "Policy.";
    String Reminder = "Reminder.";
    String Customer = "Customer.";

    @FormUrlEncoded
    @POST(Version + "/GenerateCode")
    Call<MR_GenerateCode> REQUEST_GENERATE_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber
            );


    @FormUrlEncoded
    @POST(Version + "/VerifyCode")
    Call<MRVerifyCode> REQUEST_VERIFY_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber,
                    @Field("TokenId") String TokenId,
                    @Field("Code") String Code
            );


    @FormUrlEncoded
    @POST(Version + "/CreateCustomer")
    Call<MR_Primary> ADD_CUSTOMER
            (
                    @FieldMap Map<String, String> params

            );


    @GET(Version + "/GetAllCustomers")
    Call<MR_GetAllPerson> GET_ALL_CUSTOMERS
            (
                    @Query("UserInfoId") Integer UserInfoId,
                    @Query("CustomerStatus") Byte CustomerStatus,
                    @Query("IsDeleted") boolean IsDeleted,
                    @Query("FullName") String FullName,
                    @Query("SortByLevel") boolean SortByLevel
            );


    @FormUrlEncoded
    @POST(Version + "/CreateColleague")
    Call<MR_Primary> CREATE_COLLEAGUE
            (
                    @FieldMap Map<String, String> params
            );


    @GET(Version + "/GetAllColleagues")
    Call<MR_GetAllPerson> GET_ALL_COLLEAGUES
            (
                    @Query("UserInfoId") Integer UserInfoId,
                    @Query("ColleagueStatus") Byte ColleagueStatus,
                    @Query("IsDeleted") boolean IsDeleted,
                    @Query("FullName") String FullName,
                    @Query("SortByLevel") boolean SortByLevel
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteCustomer")
    Call<MR_Primary> DELETE_CUSTOMER
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/UnDeleteCustomer")
    Call<MR_Primary> DELETE_CUSTOMER_ARCHIVE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/UnDeleteColleague")
    Call<MR_Primary> DELETE_COLLEAGUE_ARCHIVE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteColleague")
    Call<MR_Primary> DELETE_COLLEAGUE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleColleague")
    Call<MR_Primary> CONVERT_TO_POSSIBLE_COLLEAGUE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleCustomer")
    Call<MR_Primary> CONVERT_TO_POSSIBLE_CUSTOMER
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/CheckToCertainColleague")
    Call<MR_Primary> CONVERT_TO_CERTAIN_COLLEAGUE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @FormUrlEncoded
    @POST(Version + "/CheckToCertainCustomer")
    Call<MR_Primary> CONVERT_TO_CERTAIN_CUSTOMER
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @GET()
    Call<MD_GetAddres> getAddress(
            @Url String url
    );


    @FormUrlEncoded
    @POST(Version + "/GetCustomerById")
    Call<MR_GetAllPerson> GET_CUSTOMERS_ID
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );

    @FormUrlEncoded
    @POST(Version + "/GetColleagueById")
    Call<MR_GetAllPerson> GET_COLLEAGUE_ID
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );


    @Multipart
    @POST(Version + "/EditCustomer")
    Call<MR_Primary> EDIT_CUSTOMER
            (
                    @Part MultipartBody.Part Image,
                    @Part("Id") RequestBody Id,
                    @Part("FullName") RequestBody FullName,
                    @Part("PhoneNumber") RequestBody PhoneNumber,
                    @Part("MobileNumber") RequestBody MobileNumber,
                    @Part("BirthDateJ") RequestBody BirthDateJ,
                    @Part("Address") RequestBody Address,
                    @Part("Lat") RequestBody Lat,
                    @Part("Lang") RequestBody Lang,
                    @Part("UserInfoId") RequestBody UserInfoId,
                    @Part("NationalCode") RequestBody NationalCode,
                    @Part("Level") RequestBody Level
            );


    @Multipart
    @POST(Version + "/EditColleague")
    Call<MR_Primary> EDIT_COLLEAGUE
            (
                    @Part MultipartBody.Part Image,
                    @Part("Id") RequestBody Id,
                    @Part("FullName") RequestBody FullName,
                    @Part("PhoneNumber") RequestBody PhoneNumber,
                    @Part("MobileNumber") RequestBody MobileNumber,
                    @Part("BirthDateJ") RequestBody BirthDateJ,
                    @Part("Address") RequestBody Address,
                    @Part("Lat") RequestBody Lat,
                    @Part("Lang") RequestBody Lang,
                    @Part("UserInfoId") RequestBody UserInfoId,
                    @Part("NationalCode") RequestBody NationalCode,
                    @Part("Level") RequestBody Level
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllPolicyTypes")
    Call<MR_PolicyType> GET_ALL_POLICY_TYPES
            (
                    @Field("Id") Integer id
            );


    @FormUrlEncoded
    @POST(Version + "/CreatePolicy")
    Call<MR_Primary> CREATE_POLICY
            (
                    @Field(Policy + "PolicyTypeId") String PolicyTypeId,
                    @Field(Policy + "CustomerId") String CustomerId,
                    @Field(Policy + "PolicyAmont") String PolicyAmont,
                    @Field(Policy + "UserInfoId") String UserInfoId,
                    @Field(Policy + "Description") String Description,
                    @Field(Policy + "Insured") String Insured,
                    @Field(Policy + "InsuredNationalCode") String InsuredNationalCode,
                    @Field(Policy + "DeliveryToBranchDateJ") String DeliveryToBranchDateJ,
                    @Field(Policy + "SeriNumber") int SeriNumber,
                    @Field(Policy + "TransactionCode") String TransactionCode
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllPolicies")
    Call<MR_Policy> GET_ALL_POLICIES
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("CustomerId") Integer CustomerId,
                    @Field("PolicyStatus") Byte PolicyStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/EditPolicy")
    Call<MR_Primary> EDIT_POLICY
            (
                    @Field(Policy + "Id") Integer Id,
                    @Field(Policy + "PolicyTypeId") Integer PolicyTypeId,
                    @Field(Policy + "CustomerId") Integer CustomerId,
                    @Field(Policy + "PolicyAmont") Long PolicyAmont,
                    @Field(Policy + "UserInfoId") Integer UserInfoId,
                    @Field(Policy + "Description") String Description,
                    @Field(Policy + "SuggestionDateM") String SuggestionDateM,
                    @Field(Policy + "Insured") String Insured,
                    @Field(Policy + "InsuredNationalCode") String InsuredNationalCode,
                    @Field(Policy + "DeliveryToBranchDateJ") String DeliveryToBranchDateJ,
                    @Field(Policy + "SeriNumber") int SeriNumber,
                    @Field(Policy + "TransactionCode") String TransactionCode
            );


    @FormUrlEncoded
    @POST(Version + "/CreateReminder")
    Call<MR_Primary> CREATE_REMINDER_CUSTOMER
            (
                    @Field(Reminder + "NotificationDateTimeJ") String NotificationDateTimeJ,
                    @Field(Reminder + "NotificationTime") String Time,
                    @Field(Reminder + "Title") String Title,
                    @Field(Reminder + "RelationType") Byte RelationType,
                    @Field(Reminder + "ReminderType") Byte ReminderType,
                    @Field(Reminder + "ReminderResult") Integer ReminderResult,
                    @Field(Reminder + "CustomerId") Integer CustomerId,
                    @Field(Reminder + "RelationDateTimeJ") String RelationDateTime,
                    @Field(Reminder + "ResultTitle") String ResultTitle
            );


    @FormUrlEncoded
    @POST(Version + "/CreateReminder")
    Call<MR_Primary> CREATE_REMINDER_COLLEAGUE
            (
                    @Field(Reminder + "NotificationDateTimeJ") String NotificationDateTimeJ,
                    @Field(Reminder + "NotificationTime") String Time,
                    @Field(Reminder + "Title") String Title,
                    @Field(Reminder + "RelationType") Byte RelationType,
                    @Field(Reminder + "ReminderType") Byte ReminderType,
                    @Field(Reminder + "ReminderResult") Integer ReminderResult,
                    @Field(Reminder + "ColleagueId") Integer ColleagueId,
                    @Field(Reminder + "RelationDateTimeJ") String RelationDateTime,
                    @Field(Reminder + "ResultTitle") String ResultTitle
            );


    @FormUrlEncoded
    @POST(Version + "/ChangeReminderResult")
    Call<MR_Primary> CHANGE_REMINDER_RESULT
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("ReminderResult") Byte ReminderResult
            );


    @FormUrlEncoded
    @POST(Version + "/GetMobileNumberByReminderId")
    Call<MR_PersonNumber> GET_MOBILE_NUMBER
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetLastEducation")
    Call<MR_LastEducation> GET_LAST_EDUCATION
            (
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetEducationFiles")
    Call<MR_EducationFiles> GET_EDUCATION_FILES
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id,
                    @Field("FileType") Byte FileType
            );


    @FormUrlEncoded
    @POST(Version + "/GetExam")
    Call<MR_Exam> GET_EXAM
            (
                    @Field("Id") Integer Id
            );


    @FormUrlEncoded
    @POST(Version + "/GetQuestions")
    Call<MR_Question> GET_QUESTION
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/SetReminderResultDatetime")
    Call<MR_Primary> SET_REMINDER_RESULT_DATETIME
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @POST(Version + "/GetAnswers")
    Call<MR_ExamResult> SEND_ANSWER
            (
                    @Body MD_SendAnswer sendAnswer
            );

    @FormUrlEncoded
    @POST(Version + "/GetExamResultById")
    Call<MR_ExamResult> GET_EXAM_RESULT
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetCategories")
    Call<MR_EducationCategoryVms> GET_LevelCategory
            (
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetEducationList")
    Call<MR_Education> GET_EDUCATION_LIST
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetExamResultList")
    Call<MR_ExamResults> GET_EXAM_RESULTS
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @FormUrlEncoded
    @POST(Version + "/GetExamResultAnswers")
    Call<MR_ExamResultDetail> GET_EXAM_RESULT_DETAILS
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/StatisticalReport")
    Call<MR_StatisticalReport> GET_STATISTICAL_REPORT
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("fromdate") String fromdate,
                    @Field("todate") String todate,
                    @Field("SortingResourceId") Integer SortingResourceId
            );


    @FormUrlEncoded
    @POST(Version + "/AnaliticalReport")
    Call<MR_AnalyticalReport> GET_ANALITICAL_REPORT
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("fromdate") String fromdate,
                    @Field("todate") String todate,
                    @Field("SortingResourceId") Integer SortingResourceId
            );


    @GET(Version + "/GetRecources")
    Call<MR_SpinnerItem> GET_RESOURCES
            ();


    @GET(Version + "/hi")
    Call<MD_Update> GET_UPDATE
            ();


    @FormUrlEncoded
    @POST(Version + "/LearningReport")
    Call<MR_StatisticalReport> GET_LEARN_REPORT
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("SortingResourceId") Integer SortingResourceId
            );

    @FormUrlEncoded
    @POST(Version + "/LearningReport")
    Call<MR_StatisticalReport> GET_LEARN_REPORT_SortByAverageGrade
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("SortingResourceId") Integer SortingResourceId,
                    @Field("SortByAverageGrade") boolean SortByAverageGrade
            );


    @FormUrlEncoded
    @POST(Version + "/LearningReport")
    Call<MR_StatisticalReport> GET_LEARN_REPORT_SortByTotalScore
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("SortingResourceId") Integer SortingResourceId,
                    @Field("SortByTotalScore") boolean SortByTotalScore
            );

    @FormUrlEncoded
    @POST(Version + "/LearningReport")
    Call<MR_StatisticalReport> GET_LEARN_REPORT_SortByTotalActivity
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("SortingResourceId") Integer SortingResourceId,
                    @Field("SortByTotalActivity") boolean SortByTotalActivity
            );


    @FormUrlEncoded
    @POST(Version + "/Payment")
    Call<MR_Payment> Payment
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Price") Long Price
            );

    @FormUrlEncoded
    @POST(Version + "/GetAllTransactions")
    Call<MR_Transaction> getAllTransactions
            (
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllReminders")
    Call<MR_Reminder> getAllReminders
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("ReminderTypes") Integer ReminderTypes,
                    @Field("RelationType") Byte RelationType,
                    @Field("IsDeleted") Integer IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteReminder")
    Call<MR_Primary> deleteReminders
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @GET()
    Call<MR_UserInfoVM> userSidebarInformation(
            @Url String url
    );


    @GET(Version + "/GetNotifList")
    Call<MR_Notifications> GetNotifList(
            @Query("IsDeleted") boolean IsDeleted
    );

}
