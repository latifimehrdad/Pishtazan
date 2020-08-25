package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MD_PolicyType;
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1) {
                        md_policyTypes = response.body().getPolicyTypes();
                        SendMessageToObservable(StaticValues.ML_GetAllPolicyTypes);
                    } else
                        SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_PolicyType> call, Throwable t) {
                CallIsFailure();
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
            String InsuredNationalCode) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_POLICY(
                        PolicyTypeId,
                        CustomerId,
                        PolicyAmount,
                        UserInfoId.toString(),
                        Description,
                        Insured,
                        InsuredNationalCode));


        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        SendMessageToObservable(StaticValues.ML_ResponseError);
                    else {
                        SendMessageToObservable(StaticValues.ML_Success);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
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
            String InsuredNationalCode) {

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
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
                        InsuredNationalCode));


        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        SendMessageToObservable(StaticValues.ML_ResponseError);
                    else {
                        SendMessageToObservable(StaticValues.ML_EditSuccess);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ editPolicy


    //______________________________________________________________________________________________ getMd_policyTypes
    public List<MD_PolicyType> getMd_policyTypes() {
        return md_policyTypes;
    }
    //______________________________________________________________________________________________ getMd_policyTypes

}
