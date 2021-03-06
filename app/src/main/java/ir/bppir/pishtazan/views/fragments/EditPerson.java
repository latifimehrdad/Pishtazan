package ir.bppir.pishtazan.views.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.daggers.datepicker.PersianPickerModule;
import ir.bppir.pishtazan.databinding.FragmentEditPersonBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_EditPerson;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Map;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.application.PishtazanApplication;
import ir.bppir.pishtazan.views.dialogs.DialogProgress;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static ir.bppir.pishtazan.daggers.retrofit.RetrofitApis.Host;

public class EditPerson extends FragmentPrimary implements
        FragmentPrimary.actionFromObservable {

    private NavController navController;
    private Byte panelType;
    private VM_EditPerson vm_editPerson;
    private Byte Degree = -1;
    private DisposableObserver<Byte> disposableObserver;
    private String stringDate;
    private DialogProgress progress;
    private String Lat;
    private String Lng;

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

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.GifViewLoading)
    GifView GifViewLoading;

    @BindView(R.id.switchMaterialBirthDay)
    SwitchMaterial switchMaterialBirthDay;

    @BindView(R.id.linearLayoutDescription)
    LinearLayout linearLayoutDescription;

    @BindView(R.id.linearLayoutNationalCode)
    LinearLayout linearLayoutNationalCode;

    @BindView(R.id.linearLayoutDegree)
    LinearLayout linearLayoutDegree;

    @BindView(R.id.linearLayoutMobile)
    LinearLayout linearLayoutMobile;

    @BindView(R.id.editTextDescription)
    EditText editTextDescription;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            VM_Map.map_Address = null;
            vm_editPerson = new VM_EditPerson(getActivity());
            FragmentEditPersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_edit_person, container, false);
            binding.setEditPerson(vm_editPerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setTextWatcher();
            setClick();
            stringDate = "";
            getPersonInfo();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        init();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getPersonInfo
    private void getPersonInfo() {
        scrollView.setVisibility(View.GONE);
        GifViewLoading.setVisibility(View.VISIBLE);

        assert getArguments() != null;
        assert getContext() != null;
        int panel = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        panelType = (byte) panel;
        Integer personId = getArguments().getInt(getContext().getString(R.string.ML_personId), 0);
        if (panelType.equals(StaticValues.Customer)) {
            vm_editPerson.getCustomer(personId);
        } else if (panelType.equals(StaticValues.Colleague)){
            vm_editPerson.getColleague(personId);
        } else
            vm_editPerson.getUser();
    }
    //______________________________________________________________________________________________ getPersonInfo


    //______________________________________________________________________________________________ init
    private void init() {
        assert getView() != null;
        navController = Navigation.findNavController(getView());
        setObservableForGetAction(
                EditPerson.this,
                vm_editPerson.getPublishSubject(),
                vm_editPerson);

        if (panelType.equals(StaticValues.Colleague))
            switchMaterialBirthDay.setVisibility(View.GONE);

        if (panelType.equals(StaticValues.ML_User)) {
            linearLayoutNationalCode.setVisibility(View.GONE);
            switchMaterialBirthDay.setVisibility(View.GONE);
            linearLayoutDegree.setVisibility(View.GONE);
            linearLayoutMobile.setVisibility(View.GONE);
        } else {
            linearLayoutDescription.setVisibility(View.GONE);
        }

        if (VM_Map.map_Address != null) {
            vm_editPerson.setAddress(VM_Map.map_Address);
            vm_editPerson.setAddressString();
            Lat = vm_editPerson.getAddress().getLat();
            Lng = vm_editPerson.getAddress().getLon();
        }

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (progress != null)
            progress.dismiss();
        progress = null;
        GifViewLoading.setVisibility(View.GONE);

        finishLoadingSend();
        if (action.equals(StaticValues.ML_EditSuccess)) {
            Uri destination = Uri.fromFile(new File(getContext().getExternalCacheDir(), "cropped.jpg"));
            File file = new File(destination.getPath());
            if (file.exists())
                file.delete();
            Degree = -1;
            EditTextName.getText().clear();
            EditTextPhoneNumber.getText().clear();
            EditTextName.requestFocus();
            LinearLayoutGiant.setBackground(null);
            LinearLayoutNormal.setBackground(null);
            LinearLayoutPeach.setBackground(null);
            vm_editPerson.getUserInfoVM();
            return;
        }

        if (action.equals(StaticValues.ML_GotoHome)){
            assert getContext() != null;
            getContext().onBackPressed();
            return;
        }

        if (action.equals(StaticValues.ML_AddressFromMap)) {
            EditTextAddress.setText(vm_editPerson.getAddressString());
            return;
        }

        if (action.equals(StaticValues.ML_GetPerson)) {
            scrollView.setVisibility(View.VISIBLE);
            EditTextName.setText(vm_editPerson.getPerson().getFullName());
            EditTextMobileNumber.setText(vm_editPerson.getPerson().getMobileNumber());
            EditTextPhoneNumber.setText(vm_editPerson.getPerson().getPhoneNumber());
            EditTextNationalCode.setText(vm_editPerson.getPerson().getNationalCode());
            EditTextAddress.setText(vm_editPerson.getPerson().getAddress());
            editTextDescription.setText(vm_editPerson.getPerson().getDescription());
            switchMaterialBirthDay.setChecked(vm_editPerson.getPerson().isSendSMS());
            String bDate = vm_editPerson.getPerson().getBirthDateJ();
            if (bDate == null || bDate.isEmpty())
                TextViewChooseBirthDay.setText(getContext().getResources().getString(R.string.ChooseBirthDay));
            else
                TextViewChooseBirthDay.setText(vm_editPerson.getPerson().getBirthDateJ());
            if (!panelType.equals(StaticValues.ML_User)) {
                Byte level = vm_editPerson.getPerson().getLevel().byteValue();
                setPersonDegree(level);
            }
            setPersonImage(CircleImageViewProfile, vm_editPerson.getPerson().getImage());
            stringDate = vm_editPerson.getPerson().getBirthDateJ();

            Lat = String.valueOf(vm_editPerson.getPerson().getLat());
            Lng = String.valueOf(vm_editPerson.getPerson().getLang());

        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ setClick
    private void setClick() {


        LinearLayoutMap.setOnClickListener(v -> {
            if (MainActivity.mainActivity.setPermission())
                navController.navigate(R.id.action_editPerson_to_map);
        });

        TextViewChooseBirthDay.setOnClickListener(v -> {
            assert getContext() != null;
            PersianPickerModule.context = getContext();
            PersianDatePickerDialog persianCalendar = PishtazanApplication
                    .getApplication(getContext())
                    .getPersianPickerComponent()
                    .getPersianDatePickerDialog();

            persianCalendar.setListener(new Listener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    stringDate = persianCalendar.getPersianYear() +
                            "/" +
                            String.format("%02d", persianCalendar.getPersianMonth()) +
                            "/" +
                            String.format("%02d", persianCalendar.getPersianDay());
                    TextViewChooseBirthDay.setText(stringDate);
                    assert getContext() != null;
                    TextViewChooseBirthDay.setBackground(getContext().getResources().getDrawable(R.drawable.dw_edit_back));
                }

                @Override
                public void onDismissed() {

                }
            });
            persianCalendar.show();

        });


        CircleImageViewProfile.setOnClickListener(view -> {

            if (!MainActivity.mainActivity.setPermission())
                return;

            if (disposableObserver != null)
                disposableObserver.dispose();
            disposableObserver = null;
            assert getContext() != null;
/*            Uri destination = Uri.fromFile(new File(getContext().getExternalCacheDir(), "cropped.jpg"));
            File file = new File(Objects.requireNonNull(destination.getPath()));
            if (file.exists()) {
                boolean d = file.delete();
            }*/
            setObserverToObservable();
            MainActivity.mainPublish.onNext(StaticValues.ML_PictureDialog);
        });

        LinearLayoutCancel.setOnClickListener(view -> {
            hideKeyboard();
            assert getContext() != null;
            getContext().onBackPressed();
        });


        LinearLayoutSave.setOnClickListener(view -> {
            String phone = EditTextPhoneNumber.getText().toString();
            phone = phone.replaceAll(" ", "");
            phone = phone.replaceAll("-", "");
            EditTextPhoneNumber.setText(phone);
            if (checkEmpty()) {
                hideKeyboard();
                showLoadingSend();
                vm_editPerson.editProfile(
                        panelType,
                        vm_editPerson.getPerson().getId(),
                        EditTextName.getText().toString(),
                        EditTextMobileNumber.getText().toString(),
                        Degree,
                        EditTextPhoneNumber.getText().toString(),
                        stringDate,
                        EditTextAddress.getText().toString(),
                        Lat,
                        Lng,
                        EditTextNationalCode.getText().toString(),
                        switchMaterialBirthDay.isChecked(),
                        editTextDescription.getText().toString()
                );
            }
        });


        LinearLayoutNormal.setOnClickListener(view -> setPersonDegree(StaticValues.DegreeNormal));


        LinearLayoutPeach.setOnClickListener(view -> setPersonDegree(StaticValues.DegreePeach));


        LinearLayoutGiant.setOnClickListener(view -> setPersonDegree(StaticValues.DegreeGiant));

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ setPersonDegree
    private void setPersonDegree(Byte type) {
        LinearLayoutNormal.setBackground(null);
        LinearLayoutPeach.setBackground(null);
        LinearLayoutGiant.setBackground(null);
        assert getContext() != null;
        if (type.equals(StaticValues.DegreeNormal))
            LinearLayoutNormal.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else if (type.equals(StaticValues.DegreePeach))
            LinearLayoutPeach.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else
            LinearLayoutGiant.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        Degree = type;
    }
    //______________________________________________________________________________________________ setPersonDegree


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditTextPhoneNumber.addTextChangedListener(textChangeForChangeBack(EditTextPhoneNumber));
        EditTextName.addTextChangedListener(textChangeForChangeBack(EditTextName));
        EditTextNationalCode.addTextChangedListener(textChangeForChangeBack(EditTextNationalCode));
        EditTextAddress.addTextChangedListener(textChangeForChangeBack(EditTextAddress));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean name = true;
        boolean mobile = true;
        boolean phoneNumber = true;
        boolean national = true;
        boolean birthday = true;
        boolean degree = true;


        if (EditTextMobileNumber.getText().length() != 11) {
            EditTextMobileNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextMobileNumber.setError(getResources().getString(R.string.EmptyMobileNumber));
            EditTextMobileNumber.requestFocus();
            mobile = false;
        } else {
            String ZeroNine = EditTextMobileNumber.getText().subSequence(0, 2).toString();
            if (!ZeroNine.equalsIgnoreCase("09")) {
                EditTextMobileNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditTextMobileNumber.setError(getResources().getString(R.string.EmptyMobileNumber));
                EditTextMobileNumber.requestFocus();
                mobile = false;
            }
        }


        if (EditTextPhoneNumber.getText().length() > 0)
            if (EditTextPhoneNumber.getText().length() < 8) {
                EditTextPhoneNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditTextPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
                EditTextPhoneNumber.requestFocus();
                phoneNumber = false;
            }

        if (EditTextNationalCode.getText().length() > 0)
            if (EditTextNationalCode.getText().length() < 10) {
                EditTextNationalCode.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditTextNationalCode.setError(getResources().getString(R.string.EmptyNational));
                EditTextNationalCode.requestFocus();
                national = false;
            }

        if (EditTextName.getText().length() == 0) {
            EditTextName.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextName.setError(getResources().getString(R.string.EmptyContactName));
            EditTextName.requestFocus();
            name = false;
        }

        if (panelType.equals(StaticValues.Customer))
            if (switchMaterialBirthDay.isChecked())
                if (stringDate == null || stringDate.length() == 0) {
                    TextViewChooseBirthDay.setBackgroundResource(R.drawable.dw_edit_empty_background);
                    birthday = false;
                }

        if (!panelType.equals(StaticValues.ML_User))
            if (Degree == -1) {
                degree = false;
                assert getContext() != null;
                showMessage(
                        getContext().getResources().getString(R.string.ChoosePersonDegree),
                        getResources().getColor(R.color.ML_Dialog),
                        getResources().getDrawable(R.drawable.ic_baseline_warning),
                        getResources().getColor(R.color.ML_Red));
            }


        return mobile && name && phoneNumber && national && birthday && degree;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ showLoadingSend
    private void showLoadingSend() {
        ImageViewSend.setVisibility(View.GONE);
        GifViewSend.setVisibility(View.VISIBLE);
        assert getContext() != null;
        TextViewSend.setText(getContext().getResources().getString(R.string.PleaseWait));

    }
    //______________________________________________________________________________________________ showLoadingSend


    //______________________________________________________________________________________________ finishLoadingSend
    private void finishLoadingSend() {

        ImageViewSend.setVisibility(View.VISIBLE);
        GifViewSend.setVisibility(View.GONE);
        assert getContext() != null;
        TextViewSend.setText(getContext().getResources().getString(R.string.Save));

    }
    //______________________________________________________________________________________________ finishLoadingSend


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable() {

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

    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        assert getContext() != null;
        getContext().runOnUiThread(() -> {

            if (action.equals(StaticValues.ML_PictureDialogGetImage)) {
                CircleImageViewProfile.setImageURI(Uri.parse(new File(MainActivity.ImageUrl).toString()));
                if (disposableObserver != null)
                    disposableObserver.dispose();
                disposableObserver = null;
            }

        });
    }
    //______________________________________________________________________________________________ actionHandler


    //______________________________________________________________________________________________ setPersonImage
    public void setPersonImage(CircleImageView imageView, String url) {

        ImageLoader imageLoader = PishtazanApplication
                .getApplication(imageView.getContext())
                .getImageLoaderComponent()
                .getImageLoader();

        url = Host + url;

        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage == null)
                    imageView.setImageResource(R.drawable.image_before_load);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                imageView.setImageResource(R.drawable.image_before_load);
            }
        });

    }
    //______________________________________________________________________________________________ setPersonImage


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void getActionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
