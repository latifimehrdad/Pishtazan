package ir.bppir.pishtazan.daggers.retrofit;


import java.util.Map;

import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.models.MD_RequestGenerateCode;
import ir.bppir.pishtazan.models.MD_RequestGetAllPerson;
import ir.bppir.pishtazan.models.MD_RequestPrimary;
import ir.bppir.pishtazan.models.MD_RequestVerifyCode;
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

    @FormUrlEncoded
    @POST(Version + "/GenerateCode")
    Call<MD_RequestGenerateCode> REQUEST_GENERATE_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber
            );


    @FormUrlEncoded
    @POST(Version + "/VerifyCode")
    Call<MD_RequestVerifyCode> REQUEST_VERIFY_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber,
                    @Field("Code") String Code
            );


    @FormUrlEncoded
    @POST(Version + "/CreateCustomer")
    Call<MD_RequestPrimary> ADD_CUSTOMER
            (
                    @FieldMap Map<String,String> params

            );


    @FormUrlEncoded
    @POST(Version + "/GetAllCustomers")
    Call<MD_RequestGetAllPerson> GET_ALL_CUSTOMERS
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("CustomerStatus") Byte CustomerStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/CreateColleague")
    Call<MD_RequestPrimary> CREATE_COLLEAGUE
            (
                    @FieldMap Map<String,String> params
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllColleagues")
    Call<MD_RequestGetAllPerson> GET_ALL_COLLEAGUES
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("ColleagueStatus") Byte ColleagueStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteCustomer")
    Call<MD_RequestPrimary> DELETE_CUSTOMER
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteColleague")
    Call<MD_RequestPrimary> DELETE_COLLEAGUE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleColleague")
    Call<MD_RequestPrimary> CONVERT_TO_POSSIBLE_COLLEAGUE
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleCustomer")
    Call<MD_RequestPrimary> CONVERT_TO_POSSIBLE_CUSTOMER
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
    Call<MD_RequestGetAllPerson> GET_CUSTOMERS_ID
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );

    @FormUrlEncoded
    @POST(Version + "/GetColleagueById")
    Call<MD_RequestGetAllPerson> GET_COLLEAGUE_ID
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );



    @Multipart
    @POST(Version + "/EditCustomer")
    Call<MD_RequestPrimary> EDIT_CUSTOMER
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
    Call<MD_RequestPrimary> EDIT_COLLEAGUE
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


}
