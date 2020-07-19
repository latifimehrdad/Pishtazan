package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import java.util.List;

import ir.bppir.pishtazan.models.MD_Policy;
import ir.bppir.pishtazan.models.MD_RequestPolicy;
import ir.bppir.pishtazan.models.MD_RequestPrimary;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_PolicyList extends VM_Primary {

    private List<MD_Policy> md_policies;


    public VM_PolicyList(Activity context) {//______________________________________________________ VM_PolicyList
        setContext(context);
    }//_____________________________________________________________________________________________ VM_PolicyList


    public void GetAllPolicies(Integer CustomerId, Byte PolicyStatus) {//___________________________ GetAllPolicies

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ALL_POLICIES(
                        UserInfoId,
                        CustomerId,
                        PolicyStatus,
                        false));


        getPrimaryCall().enqueue(new Callback<MD_RequestPolicy>() {
            @Override
            public void onResponse(Call<MD_RequestPolicy> call, Response<MD_RequestPolicy> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        SendMessageToObservable(StaticValues.ML_ResponseError);
                    else {
                        md_policies = response.body().getPolicies();
                        SendMessageToObservable(StaticValues.ML_GetAllPolicy);
                    }
                }
            }

            @Override
            public void onFailure(Call<MD_RequestPolicy> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetAllPolicies


    public List<MD_Policy> getMd_policies() {//_____________________________________________________ getMd_policies
        return md_policies;
    }//_____________________________________________________________________________________________ getMd_policies
}
