package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;

public class VM_EditPerson extends VM_Primary {

    private MD_GetAddres address;
    private String AddressString;

    public VM_EditPerson(Activity context) {//______________________________________________________ VM_EditPerson
        setContext(context);
    }//_____________________________________________________________________________________________ VM_EditPerson


    public void SetAddressString() {//______________________________________________________________ SetAddressString

        if (address != null && address.getAddress() != null) {
            StringBuilder addressString = new StringBuilder();

            String country = address.getAddress().getCountry();


            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                addressString.append(country);
                addressString.append("، ");

                if(!country.equalsIgnoreCase("ایران")) {
                    setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                    getPublishSubject().onNext(StaticValues.ML_AddressFromMapOutOfIran);
                    return;
                }

            } else {
                setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                getPublishSubject().onNext(StaticValues.ML_AddressFromMapOutOfIran);
                return;
            }

            String state = address.getAddress().getState();
            if (state != null &&
                    !state.equalsIgnoreCase("null") &&
                    !state.equalsIgnoreCase("")) {
                addressString.append(state);
                addressString.append("، ");
            }

            String county = address.getAddress().getCounty();
            if (county != null &&
                    !county.equalsIgnoreCase("null") &&
                    !county.equalsIgnoreCase("")) {
                addressString.append(county);
                addressString.append("، ");
            }

            String city = address.getAddress().getCity();
            if (city != null &&
                    !city.equalsIgnoreCase("null") &&
                    !city.equalsIgnoreCase("")) {
                addressString.append("شهر");
                addressString.append(" ");
                addressString.append(city);
                addressString.append("، ");
            }

            String neighbourhood = address.getAddress().getNeighbourhood();
            if (neighbourhood != null &&
                    !neighbourhood.equalsIgnoreCase("null") &&
                    !neighbourhood.equalsIgnoreCase("")) {
                addressString.append(neighbourhood);
                addressString.append("، ");
            }

            String suburb = address.getAddress().getSuburb();
            if (suburb != null &&
                    !suburb.equalsIgnoreCase("null") &&
                    !suburb.equalsIgnoreCase("") &&
                    !suburb.equalsIgnoreCase(neighbourhood)) {
                addressString.append(suburb);
                addressString.append("، ");
            }

            String road = address.getAddress().getRoad();
            if (road != null &&
                    !road.equalsIgnoreCase("null") &&
                    !road.equalsIgnoreCase("")) {
                addressString.append("خیابان");
                addressString.append(" ");
                addressString.append(road);
            }

            AddressString = addressString.toString();
        } else {
            AddressString = "";
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_AddressFromMap);
            }
        },500);


    }//_____________________________________________________________________________________________ SetAddressString

    public MD_GetAddres getAddress() {//____________________________________________________________ getAddress
        return address;
    }//_____________________________________________________________________________________________ getAddress


    public void setAddress(MD_GetAddres address) {//____________________________________________________________ setAddress
        this.address = address;
    }//_____________________________________________________________________________________________ setAddress


    public String getAddressString() {//____________________________________________________________ getAddressString
        return AddressString;
    }//_____________________________________________________________________________________________ getAddressString


    public void setAddressString(String addressString) {//__________________________________________ setAddressString
        AddressString = addressString;
    }//_____________________________________________________________________________________________ setAddressString
}
