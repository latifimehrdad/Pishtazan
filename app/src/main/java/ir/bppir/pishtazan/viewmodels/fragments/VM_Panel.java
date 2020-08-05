package ir.bppir.pishtazan.viewmodels.fragments;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.models.MR_GetAllPerson;
import ir.bppir.pishtazan.models.MR_Primary;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.VM_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Panel extends VM_Primary {

    private List<MD_Person> personList;

    public VM_Panel(Activity context) {//___________________________________________________________ VM_Panel
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Panel


    public void GetPerson(int panelType, Byte PersonType) {//_______________________________________ GetPerson

        CancelRequest();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (panelType == StaticValues.Customer)
                GetAllCustomers(PersonType);
            else
                GetAllColleagues(PersonType);
        }, 200);

    }//_____________________________________________________________________________________________ GetPerson


    private void GetAllCustomers(Byte PersonType) {//_______________________________________________ GetAllCustomers

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ALL_CUSTOMERS(UserInfoId, PersonType, false));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        personList = response.body().getCustomers();
                        getPublishSubject().onNext(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });


    }//_____________________________________________________________________________________________ GetAllCustomers


    private void GetAllColleagues(Byte PersonType) {//______________________________________________ GetAllColleagues

        Integer UserInfoId = GetUserId();
        if (UserInfoId == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .GET_ALL_COLLEAGUES(UserInfoId, PersonType, false));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        personList = response.body().getColleagues();
                        getPublishSubject().onNext(StaticValues.ML_GetPerson);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ GetAllColleagues


    public List<MD_Person> getPersonList() {//______________________________________________________ getPersonList
        if (personList == null)
            personList = new ArrayList<>();
        return personList;
    }//_____________________________________________________________________________________________ getPersonList


    public void SaveCustomerReminder(
            Byte ReminderTypes,
            Integer Position,
            String stringDate,
            String stringTime,
            String Name,
            Integer PersonId) {//__________________________________________________________________ SaveCustomerReminder

        StringBuilder title = new StringBuilder();
        if (ReminderTypes.equals(StaticValues.Call))
            title.append(getContext().getResources().getString(R.string.CallWith));
        else
            title.append(getContext().getResources().getString(R.string.MeetingWith));
        title.append(" ");
        if (Position == null) {
            title.append(Name);
        } else {
            PersonId = personList.get(Position).getId();
            title.append(personList.get(Position).getFullName());
        }
        title.append(" در ");
        title.append(getContext().getResources().getString(R.string.Clock));
        title.append(" ");
        title.append(stringTime);

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_REMINDER_CUSTOMER(
                        stringDate,
                        stringTime,
                        title.toString(),
                        StaticValues.Customer,
                        ReminderTypes,
                        0,
                        PersonId,
                        stringDate
                ));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        getPublishSubject().onNext(StaticValues.ML_SaveReminder);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ SaveCustomerReminder


    public void SaveColleagueReminder(
            Byte ReminderTypes,
            Integer Position,
            String stringDate,
            String stringTime,
            String Name,
            Integer PersonId) {//__________________________________________________________________ SaveColleagueReminder

        StringBuilder title = new StringBuilder();
        if (ReminderTypes.equals(StaticValues.Call))
            title.append(getContext().getResources().getString(R.string.CallWith));
        else
            title.append(getContext().getResources().getString(R.string.MeetingWith));
        title.append(" ");
        if (Position == null) {
            title.append(Name);
        } else {
            PersonId = personList.get(Position).getId();
            title.append(personList.get(Position).getFullName());
        }
        title.append(" در ");
        title.append(getContext().getResources().getString(R.string.Clock));
        title.append(" ");
        title.append(stringTime);

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CREATE_REMINDER_COLLEAGUE(
                        stringDate,
                        stringTime,
                        title.toString(),
                        StaticValues.Colleague,
                        ReminderTypes,
                        0,
                        PersonId,
                        stringDate
                ));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        getPublishSubject().onNext(StaticValues.ML_SaveReminder);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ SaveColleagueReminder


    public void DeletePerson(int panelType, Integer Position) {//___________________________________ DeletePerson
        if (panelType == StaticValues.Customer)
            DeleteCustomer(Position);
        else
            DeleteColleague(Position);
    }//_____________________________________________________________________________________________ DeletePerson


    private void DeleteCustomer(Integer Position) {//_______________________________________________ DeleteCustomer

        Integer Id = GetUserId();
        if (Id == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .DELETE_CUSTOMER(personList.get(Position).getId(), Id));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_DeletePerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ DeleteCustomer


    private void DeleteColleague(Integer Position) {//_______________________________________________ DeleteColleague

        Integer Id = GetUserId();
        if (Id == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .DELETE_COLLEAGUE(personList.get(Position).getId(), Id));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_DeletePerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ DeleteColleague


    public void MoveToPossible(int panelType, Integer Position) {//_________________________________ MoveToPossible
        if (panelType == StaticValues.Customer)
            MoveToPossibleCustomer(Position);
        else
            MoveToPossibleColleague(Position);
    }//_____________________________________________________________________________________________ MoveToPossible


    public void MoveToPossibleColleague(Integer Position) {//_______________________________________ MoveToPossibleColleague

        Integer Id = GetUserId();
        if (Id == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CONVERT_TO_POSSIBLE_COLLEAGUE(personList.get(Position).getId(), Id));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ MoveToPossibleColleague


    public void MoveToPossibleCustomer(Integer Position) {//________________________________________ MoveToPossibleCustomer

        Integer Id = GetUserId();
        if (Id == 0) {
            UserIsNotAuthorization();
            return;
        }

        setPrimaryCall(PishtazanApplication
                .getApplication(getContext())
                .getRetrofitComponent()
                .getRetrofitApiInterface()
                .CONVERT_TO_POSSIBLE_CUSTOMER(personList.get(Position).getId(), Id));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (ResponseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(StaticValues.ML_ConvertPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                CallIsFailure();
            }
        });

    }//_____________________________________________________________________________________________ MoveToPossibleCustomer

}
