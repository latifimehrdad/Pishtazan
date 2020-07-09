package ir.bppir.pishtazan.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentMapBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Map;

public class Map extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable,
        OnMapReadyCallback {

    private VM_Map vm_map;
    private GoogleMap mMap;
    private boolean getLocation = false;


    @BindView(R.id.textChoose)
    TextView textChoose;

    @BindView(R.id.MarkerGif)
    GifView MarkerGif;

    @BindView(R.id.LayoutChoose)
    RelativeLayout LayoutChoose;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_map = new VM_Map(getActivity());
            FragmentMapBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_map, container, false);
            binding.setMap(vm_map);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetOnClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Map.this,
                vm_map.getPublishSubject(),
                vm_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_AddressFromMap)){
            getContext().onBackPressed();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LayoutChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textChoose.getVisibility() == View.GONE)
                    return;
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
                LatLng agaring = mMap.getCameraPosition().target;
                vm_map.GetAddress(agaring.latitude, agaring.longitude);

//                vm_mapFragment.GetAddress(29.479993 , -139.565491);
//                LatLng negra = mMap.getCameraPosition().target;
//                Intent intent = new Intent(context, BackgroundServiceLocation.class);
//                BackgroundServiceLocation.getResult = MapFragment.this;
//                intent.putExtra("jobtype", "TextAddress");
//                intent.putExtra("lat", negra.latitude);
//                intent.putExtra("log", negra.longitude);
//                context.startService(intent);
            }
        });
    }//_____________________________________________________________________________________________ SetOnClick



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {//_________________________________________________ Start Void onMapReady
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                getLocation = true;
                LatLng current = new LatLng(35.831350, 50.998434);
                float zoom = (float) 13;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (getLocation) {
                    textChoose.setVisibility(View.VISIBLE);
                    MarkerGif.setVisibility(View.GONE);
                }
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

    }//_____________________________________________________________________________________________ End Void onMapReady



}
