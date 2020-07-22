package ir.bppir.pishtazan.daggers.retrofit;


import java.util.Map;

import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.models.MR_GenerateCode;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_Policy;
import ir.bppir.pishtazan.models.MR_PolicyType;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.models.MRVerifyCode;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface RetrofitApiInterface {

    String Version = "/api";
    String Colleague = "Colleague";
    String Policy = "Policy.";

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
                    @Field("Code") String Code
            );


    @FormUrlEncoded
    @POST(Version + "/CreateCustomer")
    Call<MR_Primary> ADD_CUSTOMER
            (
                    @FieldMap Map<String,String> params

            );


    @FormUrlEncoded
    @POST(Version + "/GetAllCustomers")
    Call<MR_GetAllPerson> GET_ALL_CUSTOMERS
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("CustomerStatus") Byte CustomerStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/CreateColleague")
    Call<MR_Primary> CREATE_COLLEAGUE
            (
                    @FieldMap Map<String,String> params
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllColleagues")
    Call<MR_GetAllPerson> GET_ALL_COLLEAGUES
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("ColleagueStatus") Byte ColleagueStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteCustomer")
    Call<MR_Primary> DELETE_CUSTOMER
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
                    @Field(Policy + "Description") String Description
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
                    @Field(Policy + "SuggestionDateM") String SuggestionDateM
            );

}
