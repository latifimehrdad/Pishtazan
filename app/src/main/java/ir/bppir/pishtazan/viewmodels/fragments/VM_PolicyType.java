package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MD_PolicyType;
import ir.bppir.pishtazan.models.MR_AddPersonal;
import ir.bppir.pishtazan.models.MR_PolicyType;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_PolicyType extends VM_Primary {


    private List<MD_PolicyType> md_policyTypes;
    private int customerId = 0;

    //______________________________________________________________________________________________ VM_PolicyType
    public VM_PolicyType(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_PolicyType


    //______________________________________________________________________________________________ getAllPolicyTypes
    public void getAllPolicyTypes() {

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .GET_ALL_POLICY_TYPES(0));


        getPrimaryCall().enqueue(new Callback<MR_PolicyType>() {
            @Override
            public void onResponse(Call<MR_PolicyType> call, Response<MR_PolicyType> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        md_policyTypes = response.body().getPolicyTypes();
                        sendMessageToObservable(StaticValues.ML_GetAllPolicyTypes);
                    } else
                        sendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_PolicyType> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getAllPolicyTypes


    //______________________________________________________________________________________________ createPolicy
    public void createPolicy(
            String PolicyTypeId,
            String CustomerId,
            String PolicyAmount,
            String Description,
            String Insured,
            String InsuredNationalCode,
            String DeliveryToBranchDateJ,
            int SeriNumber,
            String TransactionCode,
            boolean Customer) {


        DeliveryToBranchDateJ = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(DeliveryToBranchDateJ);


        Integer ColleagueId;
        if (Customer) {
            ColleagueId = getColleagueId();
            if (ColleagueId == 0) {
                userIsNotAuthorization();
                return;
            }
        } else {
            ColleagueId = Integer.valueOf(CustomerId);
            CustomerId = String.valueOf(customerId);
        }

        PolicyAmount = PolicyAmount.replaceAll("," , "");
        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_POLICY(
                        PolicyTypeId,
                        CustomerId,
                        PolicyAmount,
                        ColleagueId.toString(),
                        Description,
                        Insured,
                        InsuredNationalCode,
                        DeliveryToBranchDateJ,
                        SeriNumber,
                        TransactionCode));


        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        sendMessageToObservable(StaticValues.ML_ResponseError);
                    else {
                        sendMessageToObservable(StaticValues.ML_Success);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ createPolicy


    //______________________________________________________________________________________________ editPolicy
    public void editPolicy(
            Integer Id,
            Integer PolicyTypeId,
            Integer CustomerId,
            Long PolicyAmount,
            String Description,
            String SuggestionDateM,
            String Insured,
            String InsuredNationalCode,
            String DeliveryToBranchDateJ,
            int SeriNumber,
            String TransactionCode) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .EDIT_POLICY(
                        Id,
                        PolicyTypeId,
                        CustomerId,
                        PolicyAmount,
                        UserInfoId,
                        Description,
                        SuggestionDateM,
                        Insured,
                        InsuredNationalCode,
                        DeliveryToBranchDateJ,
                        SeriNumber,
                        TransactionCode));


        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        sendMessageToObservable(StaticValues.ML_ResponseError);
                    else {
                        sendMessageToObservable(StaticValues.ML_EditSuccess);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ editPolicy



    //______________________________________________________________________________________________ addCustomer
    public void addCustomer(
            String PolicyTypeId,
            String CustomerId,
            String PolicyAmount,
            String Description,
            String Insured,
            String InsuredNationalCode,
            String DeliveryToBranchDateJ,
            int SeriNumber,
            String TransactionCode,
            String name,
            String phone,
            String nationalCode) {

        phone = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(phone);


        Map<String, String> params = new HashMap<>();
        params.put("FullName", name);
        params.put("ColleagueId", CustomerId);
        params.put("MobileNumber", phone);
        params.put("NationalCode", nationalCode);
        params.put("Level", "0");
        params.put("CustomerStatus", "1");

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .ADD_CUSTOMER(params));

        getPrimaryCall().enqueue(new Callback<MR_AddPersonal>() {
            @Override
            public void onResponse(Call<MR_AddPersonal> call, Response<MR_AddPersonal> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        customerId = response.body().getCustomerId();
                        createPolicy(PolicyTypeId, CustomerId, PolicyAmount, Description, Insured, InsuredNationalCode, DeliveryToBranchDateJ
                                , SeriNumber, TransactionCode, false);
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_AddPersonal> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ addCustomer



    //______________________________________________________________________________________________ getMd_policyTypes
    public List<MD_PolicyType> getMd_policyTypes() {
        return md_policyTypes;
    }
    //______________________________________________________________________________________________ getMd_policyTypes

}
