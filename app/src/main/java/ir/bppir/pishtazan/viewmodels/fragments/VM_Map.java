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

    //______________________________________________________________________________________________ VM_Map
    public VM_Map(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Map


    //______________________________________________________________________________________________ getAddress
    public void getAddress(double lat, double lon) {

        RetrofitComponent retrofitComponent = PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent();

        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";
        
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
                    checkCountry();
                }
            }

            @Override
            public void onFailure(Call<MD_GetAddres> call, Throwable t) {
                CallIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ checkCountry
    public void checkCountry() {

        if (map_Address != null && map_Address.getAddress() != null) {
            String country = map_Address.getAddress().getCountry();
            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {

                if (!country.equalsIgnoreCase("ایران")) {
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

    }
    //______________________________________________________________________________________________ checkCountry


}
