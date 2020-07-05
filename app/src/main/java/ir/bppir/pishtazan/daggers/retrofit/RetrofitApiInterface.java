package ir.bppir.pishtazan.daggers.retrofit;


import java.util.Map;

import ir.bppir.pishtazan.models.MD_RequestGenerateCode;
import ir.bppir.pishtazan.models.MD_RequestGetAllCustomers;
import ir.bppir.pishtazan.models.MD_RequestPrimary;
import ir.bppir.pishtazan.models.MD_RequestVerifyCode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApiInterface {

    String Version = "/api";

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
    Call<MD_RequestGetAllCustomers> GET_ALL_CUSTOMERS
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("CustomerStatus") Byte CustomerStatus,
                    @Field("IsDeleted") boolean IsDeleted
            );


}
