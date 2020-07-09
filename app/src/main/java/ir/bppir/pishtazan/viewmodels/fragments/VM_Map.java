package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.retrofit.RetrofitComponent;
import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Map extends VM_Primary {

    public static MD_GetAddres map_Address;

    public VM_Map(Activity activity) {//____________________________________________________________ VM_Map
        setContext(activity);
    }//_____________________________________________________________________________________________ VM_Map


    public void GetAddress(double lat, double lon) {//____________________________________________________________________ Start GetAddress

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        String url = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";


        setPrimaryCall(
                retrofitComponent
                        .getRetrofitApiInterface()
                        .getAddress(url));

        getPrimaryCall().enqueue(new Callback<MD_GetAddres>() {
            @Override
            public void onResponse(Call<MD_GetAddres> call, Response<MD_GetAddres> response) {
                if (response.body() == null) {
                    map_Address = new MD_GetAddres();
                    map_Address.setLat(String.valueOf(lat));
                    map_Address.setLon(String.valueOf(lon));
                } else {
                    map_Address = response.body();
                    if (map_Address.getAddress() == null) {
                        map_Address.setLat(String.valueOf(lat));
                        map_Address.setLon(String.valueOf(lon));
                    }
                    setResponseMessage("");
                    CheckCountry();
                }
            }

            @Override
            public void onFailure(Call<MD_GetAddres> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ End GetAddress



    public void CheckCountry() {//__________________________________________________________________ Start CheckCountry

        if (map_Address != null && map_Address.getAddress() != null) {
            String country = map_Address.getAddress().getCountry();
            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {

                if(!country.equalsIgnoreCase("ایران")) {
                    setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                    getPublishSubject().onNext(StaticValues.ML_AddressFromMapOutOfIran);
                } else {
                    getPublishSubject().onNext(StaticValues.ML_AddressFromMap);
                }

            } else {
                setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                getPublishSubject().onNext(StaticValues.ML_AddressFromMapOutOfIran);
            }
        }

    }//_____________________________________________________________________________________________ End CheckCountry


}
