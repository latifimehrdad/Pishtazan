package ir.bppir.pishtazan.daggers.retrofit;


import java.util.Map;

import ir.bppir.pishtazan.models.MD_RequestGenerateCode;
import ir.bppir.pishtazan.models.MD_RequestGetAllPerson;
import ir.bppir.pishtazan.models.MD_RequestPrimary;
import ir.bppir.pishtazan.models.MD_RequestVerifyCode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

}
