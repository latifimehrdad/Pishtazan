package ir.bppir.pishtazan.views.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentAddPersonBinding;
import ir.bppir.pishtazan.databinding.FragmentEditPersonBinding;
import ir.bppir.pishtazan.utility.StaticFunctions;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_AddPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_EditPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Map;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static ir.bppir.pishtazan.utility.StaticFunctions.TextChangeForChangeBack;

public class EditPerson extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private int panelType;
    private VM_EditPerson vm_editPerson;
    private Byte Degree = -1;
    private DisposableObserver<Byte> disposableObserver;
    private String stringDate;

    @BindView(R.id.EditTextName)
    EditText EditTextName;

    @BindView(R.id.EditTextMobileNumber)
    EditText EditTextMobileNumber;

    @BindView(R.id.EditTextPhoneNumber)
    EditText EditTextPhoneNumber;

    @BindView(R.id.EditTextNationalCode)
    EditText EditTextNationalCode;

    @BindView(R.id.EditTextAddress)
    EditText EditTextAddress;

    @BindView(R.id.LinearLayoutCancel)
    LinearLayout LinearLayoutCancel;

    @BindView(R.id.LinearLayoutSave)
    LinearLayout LinearLayoutSave;

    @BindView(R.id.ImageViewSend)
    ImageView ImageViewSend;

    @BindView(R.id.GifViewSend)
    GifView GifViewSend;

    @BindView(R.id.TextViewSend)
    TextView TextViewSend;

    @BindView(R.id.LinearLayoutNormal)
    LinearLayout LinearLayoutNormal;

    @BindView(R.id.LinearLayoutPeach)
    LinearLayout LinearLayoutPeach;

    @BindView(R.id.LinearLayoutGiant)
    LinearLayout LinearLayoutGiant;

    @BindView(R.id.CircleImageViewProfile)
    CircleImageView CircleImageViewProfile;

    @BindView(R.id.LinearLayoutMap)
    LinearLayout LinearLayoutMap;

    @BindView(R.id.TextViewChooseBirthDay)
    TextView TextViewChooseBirthDay;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_editPerson = new VM_EditPerson(getActivity());
            FragmentEditPersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_edit_person, container, false);
            binding.setEditPerson(vm_editPerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTextWatcher();
            SetClick();
            stringDate = "";
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ init
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                EditPerson.this,
                vm_editPerson.getPublishSubject(),
                vm_editPerson);
        panelType = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        if (VM_Map.map_Address != null) {
            vm_editPerson.setAddress(VM_Map.map_Address);
            vm_editPerson.SetAddressString();
        }
        if (panelType == StaticValues.Customer) {

        } else {

        }

    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        FinishLoadingSend();
        if (action == StaticValues.ML_AddPerson) {
            Degree = -1;
            EditTextName.getText().clear();
            EditTextPhoneNumber.getText().clear();
            EditTextName.requestFocus();
            LinearLayoutGiant.setBackground(null);
            LinearLayoutNormal.setBackground(null);
            LinearLayoutPeach.setBackground(null);
            return;
        }

        if (action.equals(StaticValues.ML_AddressFromMap)){
            EditTextAddress.setText(vm_editPerson.getAddressString());
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick


        LinearLayoutMap.setOnClickListener(v -> {navController.navigate(R.id.action_editPerson_to_map);});

        TextViewChooseBirthDay.setOnClickListener(v ->{
            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(persianCalendar.getPersianYear());
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianMonth()));
                    sb2.append("/");
                    sb2.append(String.format("%02d", persianCalendar.getPersianDay()));
                    stringDate = sb2.toString();
                    TextViewChooseBirthDay.setText(stringDate);
                    TextViewChooseBirthDay.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();

        });


        CircleImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (disposableObserver != null)
                    disposableObserver.dispose();
                disposableObserver = null;
                SetObserverToObservable();
                MainActivity.mainPublish.onNext(StaticValues.ML_PictureDialog);
            }
        });

        LinearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticFunctions.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });


        LinearLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = EditTextPhoneNumber.getText().toString();
                phone = phone.replaceAll(" ", "");
                phone = phone.replaceAll("-", "");
                EditTextPhoneNumber.setText(phone);
                if (CheckEmpty()) {
                    StaticFunctions.hideKeyboard(getActivity());
                    ShowLoadingSend();
//                    vm_addPerson.AddPerson(
//                            EditTextName.getText().toString(),
//                            EditTextPhoneNumber.getText().toString(),
//                            Degree,
//                            panelType
//                    );
                }
            }
        });


        LinearLayoutNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutPeach.setBackground(null);
                LinearLayoutGiant.setBackground(null);
                LinearLayoutNormal.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreeNormal;
            }
        });


        LinearLayoutPeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutNormal.setBackground(null);
                LinearLayoutGiant.setBackground(null);
                LinearLayoutPeach.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreePeach;
            }
        });


        LinearLayoutGiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutNormal.setBackground(null);
                LinearLayoutPeach.setBackground(null);
                LinearLayoutGiant.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
                Degree = StaticValues.DegreeGiant;
            }
        });

    }//_____________________________________________________________________________________________ SetClick



    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditTextPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditTextPhoneNumber));
        EditTextName.addTextChangedListener(TextChangeForChangeBack(EditTextName));
        EditTextNationalCode.addTextChangedListener(TextChangeForChangeBack(EditTextNationalCode));
        EditTextAddress.addTextChangedListener(TextChangeForChangeBack(EditTextAddress));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean name = true;
        boolean mobile = true;
        boolean birthday = true;


        if (Degree == -1) {
            ShowMessage(
                    getContext().getResources().getString(R.string.ChoosePersonDegree),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                    getResources().getColor(R.color.ML_Red));
            return false;
        }

        if (stringDate.length() == 0) {
            TextViewChooseBirthDay.setBackgroundResource(R.drawable.dw_edit_empty_background);
            birthday = false;
        }

        if (EditTextPhoneNumber.getText().length() < 11) {
            EditTextPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextPhoneNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
            EditTextPhoneNumber.requestFocus();
            mobile = false;
        }

        if (EditTextName.getText().length() == 0) {
            EditTextName.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextName.setError(getResources().getString(R.string.EmptyContactName));
            EditTextName.requestFocus();
            name = false;
        }

        if (mobile && name && birthday)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void ShowLoadingSend() {//______________________________________________________________ ShowLoadingSend
        ImageViewSend.setVisibility(View.GONE);
        GifViewSend.setVisibility(View.VISIBLE);
        TextViewSend.setText(getContext().getResources().getString(R.string.PleaseWait));

    }//_____________________________________________________________________________________________ ShowLoadingSend


    private void FinishLoadingSend() {//____________________________________________________________ ShowLoadingSend

        ImageViewSend.setVisibility(View.VISIBLE);
        GifViewSend.setVisibility(View.GONE);
        TextViewSend.setText(getContext().getResources().getString(R.string.Save));

    }//_____________________________________________________________________________________________ ShowLoadingSend


    public void SetObserverToObservable() {//_______________________________________________________ SetObserverToObservable

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                actionHandler(aByte);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        MainActivity.mainPublish
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }//_____________________________________________________________________________________________ SetObserverToObservable


    private void actionHandler(Byte action) {//_____________________________________________________ actionHandler
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (action.equals(StaticValues.ML_PictureDialogGetImage)) {
                    CircleImageViewProfile.setImageURI(Uri.parse(new File(MainActivity.ImageUrl).toString()));
                    if (disposableObserver != null)
                        disposableObserver.dispose();
                    disposableObserver = null;
                }

            }
        });
    }//_____________________________________________________________________________________________ actionHandler

}
