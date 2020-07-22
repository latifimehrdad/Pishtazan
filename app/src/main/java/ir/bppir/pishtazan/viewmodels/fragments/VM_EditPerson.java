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

    public VM_EditPerson(Activity context) {//______________________________________________________ VM_EditPerson
        setContext(context);
    }//_____________________________________________________________________________________________ VM_EditPerson



    public void EditProfile(
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
            String eNationalCode) {//_______________________________________________________________ EditProfile

        if (panelId.equals(StaticValues.Customer))
            EditCustomerProfile(eId,eFullName,eMobileNumber,eLevel,ePhoneNumber,eBirthDateJ,eAddress,eLat,eLang,eNationalCode);
        else
            EditColleagueProfile(eId,eFullName,eMobileNumber,eLevel,ePhoneNumber,eBirthDateJ,eAddress,eLat,eLang,eNationalCode);

    }//_____________________________________________________________________________________________ EditProfile



    public void EditCustomerProfile(
            Integer eId,
            String eFullName,
            String eMobileNumber,
            Byte eLevel,
            String ePhoneNumber,
            String eBirthDateJ,
            String eAddress,
            String eLat,
            String eLang,
            String eNationalCode) {//_______________________________________________________________ EditCustomerProfile

        eMobileNumber = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(eMobileNumber);

        Integer eUserInfoId = GetUserId();
        if (eUserInfoId == 0) {
            UserIsNotAuthorization();
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_EditSuccess);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ EditCustomerProfile



    public void EditColleagueProfile(
            Integer eId,
            String eFullName,
            String eMobileNumber,
            Byte eLevel,
            String ePhoneNumber,
            String eBirthDateJ,
            String eAddress,
            String eLat,
            String eLang,
            String eNationalCode) {//_______________________________________________________________ EditColleagueProfile

        eMobileNumber = PishtazanApplication
                .getApplication(getContext())
                .getApplicationUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(eMobileNumber);

        Integer eUserInfoId = GetUserId();
        if (eUserInfoId == 0) {
            UserIsNotAuthorization();
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_EditSuccess);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ EditColleagueProfile




    public void GetCustomer(Integer personId) {//___________________________________________________ GetCustomer

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        person = response.body().getCustomer();
                        setResponseMessage("");
                        SendMessageToObservable(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetCustomer


    public void GetColleague(Integer personId) {//__________________________________________________ GetCustomer

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
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
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        person = response.body().getColleague();
                        setResponseMessage("");
                        SendMessageToObservable(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetCustomer


    public void SetAddressString() {//______________________________________________________________ SetAddressString

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPublishSubject().onNext(StaticValues.ML_AddressFromMap);
            }
        }, 500);


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


    public MD_Person getPerson() {//________________________________________________________________ getPerson
        if (person == null)
            person = new MD_Person();
        return person;
    }//_____________________________________________________________________________________________ getPerson


}
