package ir.bppir.pishtazan.views.fragments;

import android.graphics.Bitmap;
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
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
import ir.bppir.pishtazan.views.dialogs.DialogProgress;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static ir.bppir.pishtazan.daggers.retrofit.RetrofitApis.Host;
import static ir.bppir.pishtazan.utility.StaticFunctions.TextChangeForChangeBack;

public class EditPerson extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private Byte panelType;
    private Integer personId;
    private VM_EditPerson vm_editPerson;
    private Byte Degree = -1;
    private DisposableObserver<Byte> disposableObserver;
    private String stringDate;
    private DialogProgress progress;
    private String Lat;
    private String Lng;
    private String Address;

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
            VM_Map.map_Address = null;
            vm_editPerson = new VM_EditPerson(getActivity());
            FragmentEditPersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_edit_person, container, false);
            binding.setEditPerson(vm_editPerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTextWatcher();
            SetClick();
            stringDate = "";
            GetPersonInfo();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        init();
    }//_____________________________________________________________________________________________ onStart


    private void GetPersonInfo() {//________________________________________________________________ GetPersonInfo
        Integer panel = getArguments().getInt(getContext().getString(R.string.ML_PanelType), StaticValues.Customer);
        panelType = panel.byteValue();
        personId = getArguments().getInt(getContext().getString(R.string.ML_personId), 0);
        if (panelType == StaticValues.Customer) {
            vm_editPerson.GetCustomer(personId);
        } else {
            vm_editPerson.GetColleague(personId);
        }
    }//_____________________________________________________________________________________________ GetPersonInfo


    private void init() {//_________________________________________________________________________ init
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                EditPerson.this,
                vm_editPerson.getPublishSubject(),
                vm_editPerson);

        if (VM_Map.map_Address != null) {
            vm_editPerson.setAddress(VM_Map.map_Address);
            vm_editPerson.SetAddressString();

            double slat = vm_editPerson.getPerson().getLat();
            double slng = vm_editPerson.getPerson().getLang();
            Lat = String.valueOf(slat);
            Lng = String.valueOf(slng);
            Address = vm_editPerson.getPerson().getAddress();
        }

    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (progress != null)
            progress.dismiss();
        progress = null;

        FinishLoadingSend();
        if (action == StaticValues.ML_EditSuccess) {
            Degree = -1;
            EditTextName.getText().clear();
            EditTextPhoneNumber.getText().clear();
            EditTextName.requestFocus();
            LinearLayoutGiant.setBackground(null);
            LinearLayoutNormal.setBackground(null);
            LinearLayoutPeach.setBackground(null);
            getContext().onBackPressed();
            return;
        }

        if (action.equals(StaticValues.ML_AddressFromMap)){
            EditTextAddress.setText(vm_editPerson.getAddressString());
            return;
        }

        if (action.equals(StaticValues.ML_GetPerson)) {
            EditTextName.setText(vm_editPerson.getPerson().getFullName());
            EditTextMobileNumber.setText(vm_editPerson.getPerson().getMobileNumber());
            EditTextPhoneNumber.setText(vm_editPerson.getPerson().getPhoneNumber());
            EditTextNationalCode.setText(vm_editPerson.getPerson().getNationalCode());
            EditTextAddress.setText(vm_editPerson.getPerson().getAddress());
            TextViewChooseBirthDay.setText(vm_editPerson.getPerson().getBirthDateJ());
            Byte level = vm_editPerson.getPerson().getLevel().byteValue();
            SetPersonDegree(level);
            SetPersonImage(CircleImageViewProfile, vm_editPerson.getPerson().getImage());
            stringDate = vm_editPerson.getPerson().getBirthDateJ();
            double slat = vm_editPerson.getPerson().getLat();
            double slng = vm_editPerson.getPerson().getLang();
            Lat = String.valueOf(slat);
            Lng = String.valueOf(slng);
            Address = vm_editPerson.getPerson().getAddress();
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
                Uri destination = Uri.fromFile(new File(getContext().getExternalCacheDir(), "cropped.jpg"));
                File file = new File(destination.getPath());
                if (file.exists())
                    file.delete();
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
                    vm_editPerson.EditProfile(
                            panelType,
                            vm_editPerson.getPerson().getId(),
                            EditTextName.getText().toString(),
                            EditTextMobileNumber.getText().toString(),
                            Degree,
                            EditTextPhoneNumber.getText().toString(),
                            TextViewChooseBirthDay.getText().toString(),
                            EditTextAddress.getText().toString(),
                            Lat,
                            Lng,
                            EditTextNationalCode.getText().toString()
                    );
                }
            }
        });


        LinearLayoutNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetPersonDegree(StaticValues.DegreeNormal);
            }
        });


        LinearLayoutPeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetPersonDegree(StaticValues.DegreePeach);
            }
        });


        LinearLayoutGiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetPersonDegree(StaticValues.DegreeGiant);
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetPersonDegree(Byte type) {//_____________________________________________________ SetPersonDegree
        LinearLayoutNormal.setBackground(null);
        LinearLayoutPeach.setBackground(null);
        LinearLayoutGiant.setBackground(null);
        if (type.equals(StaticValues.DegreeNormal))
            LinearLayoutNormal.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else if (type.equals(StaticValues.DegreePeach))
            LinearLayoutPeach.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        else
            LinearLayoutGiant.setBackground(getContext().getResources().getDrawable(R.drawable.dw_back_recycler));
        Degree = type;
    }//_____________________________________________________________________________________________ SetPersonDegree


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


        if (EditTextMobileNumber.getText().length() != 11) {
            EditTextMobileNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextMobileNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
            EditTextMobileNumber.requestFocus();
            mobile = false;
        } else {
            String ZeroNine = EditTextMobileNumber.getText().subSequence(0, 2).toString();
            if(!ZeroNine.equalsIgnoreCase("09")) {
                EditTextMobileNumber.setBackgroundResource(R.drawable.dw_edit_empty_background);
                EditTextMobileNumber.setError(getResources().getString(R.string.EnterPhoneNumber));
                EditTextMobileNumber.requestFocus();
                mobile = false;
            }
        }

        if (EditTextName.getText().length() == 0) {
            EditTextName.setBackgroundResource(R.drawable.dw_edit_empty_background);
            EditTextName.setError(getResources().getString(R.string.EmptyContactName));
            EditTextName.requestFocus();
            name = false;
        }

        if (stringDate.length() == 0) {
            TextViewChooseBirthDay.setBackgroundResource(R.drawable.dw_edit_empty_background);
            birthday = false;
        }

        if (Degree == -1) {
            ShowMessage(
                    getContext().getResources().getString(R.string.ChoosePersonDegree),
                    getResources().getColor(R.color.ML_Dialog),
                    getResources().getDrawable(R.drawable.ic_baseline_warning),
                    getResources().getColor(R.color.ML_Red));
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



    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog
        progress = new DialogProgress(getContext(), null);
        progress.setCancelable(false);
        progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog




    public void SetPersonImage(CircleImageView imageView, String url) {//____________________ SetPersonImage

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

    }//_____________________________________________________________________________________________ SetPersonImage


}
