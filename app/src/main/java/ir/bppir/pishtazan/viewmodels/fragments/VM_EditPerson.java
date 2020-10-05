package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;

import java.io.File;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_GetAddres;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_EditPerson extends VM_Primary {

    private MD_GetAddres address;
    private String AddressString;
    private MD_Person person;

    //______________________________________________________________________________________________ VM_EditPerson
    public VM_EditPerson(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_EditPerson


    //______________________________________________________________________________________________ editProfile
    public void editProfile(
            Byte panelId,
            Integer eId,
            String eFullName,
            String eMobileNumber,
            Byte eLevel,
            String ePhoneNumber,
            String eBirthDateJ,
            String eAddress,
            String eLat,
            String eLang,
            String eNationalCode) {

        if (panelId.equals(StaticValues.Customer))
            editCustomerProfile(eId, eFullName, eMobileNumber, eLevel, ePhoneNumber, eBirthDateJ, eAddress, eLat, eLang, eNationalCode);
        else
            editColleagueProfile(eId, eFullName, eMobileNumber, eLevel, ePhoneNumber, eBirthDateJ, eAddress, eLat, eLang, eNationalCode);

    }
    //______________________________________________________________________________________________ editProfile


    //______________________________________________________________________________________________ editCustomerProfile
    public void editCustomerProfile(
            Integer eId,
            String eFullName,
            String eMobileNumber,
            Byte eLevel,
            String ePhoneNumber,
            String eBirthDateJ,
            String eAddress,
            String eLat,
            String eLang,
            String eNationalCode) {

        eMobileNumber = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(eMobileNumber);

        Integer eUserInfoId = getUserId();
        if (eUserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        Uri destination = Uri.fromFile(new File(getContext().getExternalCacheDir(), "cropped.jpg"));
        File file = new File(destination.getPath());
        MultipartBody.Part Image = null;

        if (file.exists())
            Image = MultipartBody
                    .Part
                    .createFormData("Image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        RequestBody Id = RequestBody.create(MediaType.parse("multipart/form-data"), eId.toString());
        RequestBody FullName = RequestBody.create(MediaType.parse("multipart/form-data"), eFullName);
        RequestBody MobileNumber = RequestBody.create(MediaType.parse("multipart/form-data"), eMobileNumber);
        RequestBody UserInfoId = RequestBody.create(MediaType.parse("multipart/form-data"), eUserInfoId.toString());
        RequestBody Level = RequestBody.create(MediaType.parse("multipart/form-data"), eLevel.toString());
        RequestBody PhoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), ePhoneNumber);
        RequestBody BirthDateJ = RequestBody.create(MediaType.parse("multipart/form-data"), eBirthDateJ);
        RequestBody Address = RequestBody.create(MediaType.parse("multipart/form-data"), eAddress);
        RequestBody Lat = RequestBody.create(MediaType.parse("multipart/form-data"), eLat);
        RequestBody Lang = RequestBody.create(MediaType.parse("multipart/form-data"), eLang);
        RequestBody NationalCode = RequestBody.create(MediaType.parse("multipart/form-data"), eNationalCode);

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .EDIT_CUSTOMER(
                        Image,
                        Id,
                        FullName,
                        PhoneNumber,
                        MobileNumber,
                        BirthDateJ,
                        Address,
                        Lat,
                        Lang,
                        UserInfoId,
                        NationalCode,
                        Level));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_EditSuccess);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ editCustomerProfile


    //______________________________________________________________________________________________ editColleagueProfile
    public void editColleagueProfile(
            Integer eId,
            String eFullName,
            String eMobileNumber,
            Byte eLevel,
            String ePhoneNumber,
            String eBirthDateJ,
            String eAddress,
            String eLat,
            String eLang,
            String eNationalCode) {

        eMobileNumber = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(eMobileNumber);

        Integer eUserInfoId = getUserId();
        if (eUserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        Uri destination = Uri.fromFile(new File(getContext().getExternalCacheDir(), "cropped.jpg"));
        File file = new File(destination.getPath());
        MultipartBody.Part Image = null;

        if (file.exists())
            Image = MultipartBody
                    .Part
                    .createFormData("Image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        RequestBody Id = RequestBody.create(MediaType.parse("multipart/form-data"), eId.toString());
        RequestBody FullName = RequestBody.create(MediaType.parse("multipart/form-data"), eFullName);
        RequestBody MobileNumber = RequestBody.create(MediaType.parse("multipart/form-data"), eMobileNumber);
        RequestBody UserInfoId = RequestBody.create(MediaType.parse("multipart/form-data"), eUserInfoId.toString());
        RequestBody Level = RequestBody.create(MediaType.parse("multipart/form-data"), eLevel.toString());
        RequestBody PhoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), ePhoneNumber);
        RequestBody BirthDateJ = RequestBody.create(MediaType.parse("multipart/form-data"), eBirthDateJ);
        RequestBody Address = RequestBody.create(MediaType.parse("multipart/form-data"), eAddress);
        RequestBody Lat = RequestBody.create(MediaType.parse("multipart/form-data"), eLat);
        RequestBody Lang = RequestBody.create(MediaType.parse("multipart/form-data"), eLang);
        RequestBody NationalCode = RequestBody.create(MediaType.parse("multipart/form-data"), eNationalCode);

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .EDIT_COLLEAGUE(
                        Image,
                        Id,
                        FullName,
                        PhoneNumber,
                        MobileNumber,
                        BirthDateJ,
                        Address,
                        Lat,
                        Lang,
                        UserInfoId,
                        NationalCode,
                        Level));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_EditSuccess);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ editColleagueProfile


    //______________________________________________________________________________________________ getCustomer
    public void getCustomer(Integer personId) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_CUSTOMERS_ID(UserInfoId, personId));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        person = response.body().getCustomer();
                        setResponseMessage("");
                        sendMessageToObservable(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getCustomer


    //______________________________________________________________________________________________ getColleague
    public void getColleague(Integer personId) {

        Integer UserInfoId = getUserId();
        if (UserInfoId == 0) {
            userIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_COLLEAGUE_ID(UserInfoId, personId));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        person = response.body().getColleague();
                        setResponseMessage("");
                        sendMessageToObservable(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                callIsFailure();
            }
        });

    }
    //______________________________________________________________________________________________ getColleague


    //______________________________________________________________________________________________ setAddressString
    public void setAddressString() {

        if (address != null && address.getAddress() != null) {
            StringBuilder addressString = new StringBuilder();

            String country = address.getAddress().getCountry();


            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                addressString.append(country);
                addressString.append("، ");

                if (!country.equalsIgnoreCase("ایران")) {
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
        handler.postDelayed(() -> getPublishSubject().onNext(StaticValues.ML_AddressFromMap), 500);


    }
    //______________________________________________________________________________________________ setAddressString


    //______________________________________________________________________________________________ getAddress
    public MD_GetAddres getAddress() {
        return address;
    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ setAddress
    public void setAddress(MD_GetAddres address) {
        this.address = address;
    }
    //______________________________________________________________________________________________ setAddress


    //______________________________________________________________________________________________ getAddressString
    public String getAddressString() {
        return AddressString;
    }
    //______________________________________________________________________________________________ getAddressString


    //______________________________________________________________________________________________ getPerson
    public MD_Person getPerson() {
        if (person == null)
            person = new MD_Person();
        return person;
    }
    //______________________________________________________________________________________________ getPerson


}
