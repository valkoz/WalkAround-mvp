package ru.walkaround.walkaround.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ru.walkaround.walkaround.IntentUtils;
import ru.walkaround.walkaround.R;

public class ChooseStartPointFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportPlaceAutocompleteFragment placeAutoComplete;
    private Marker marker;
    private LinearLayout ll;

    private LatLng initialMarkerPosition;
    private boolean isLocatedNearby;

    private Button goNext;

    public ChooseStartPointFragment() {
    }

    public static ChooseStartPointFragment newInstance() {
        ChooseStartPointFragment fragment = new ChooseStartPointFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.NoActionBar);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View fragmentView = localInflater.inflate(R.layout.fragment_choose_start_point, container, false);

        Bundle inputBundle = getArguments();

        goNext = fragmentView.findViewById(R.id.go_next_button1);
        ll = fragmentView.findViewById(R.id.choose_start_point_button_layout);


        double lng = inputBundle.getDouble(IntentUtils.LONGITUDE, 0);
        double lat = inputBundle.getDouble(IntentUtils.LATITUDE, 0);
        initialMarkerPosition = new LatLng(lat, lng);
        initialMarkerPosition = new LatLng(55.757035, 37.615351); //TODO: remove. Only for demo video
        isLocatedNearby = inputBundle.getBoolean(IntentUtils.IS_LOCATIONS_NEARBY, false);

        placeAutoComplete = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("Выберите начальную точку");
        placeAutoComplete.setBoundsBias(new LatLngBounds(new LatLng(lat, lng), new LatLng(lat, lng)));
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("Maps", "Place selected: " + place.getName());
                setMarker(place.getLatLng());
            }

            @Override
            public void onError(Status status) {

                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        goNext.setOnClickListener((view) -> {
            Log.i("OnClick", "GoNext");

            Bundle bundle = new Bundle();
            bundle.putDouble(IntentUtils.LONGITUDE, marker.getPosition().longitude);
            bundle.putDouble(IntentUtils.LATITUDE, marker.getPosition().latitude);

            ChooseCategoriesFragment chooseCategoriesFragment = ChooseCategoriesFragment.newInstance();
            chooseCategoriesFragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.add(R.id.fragment_content, chooseCategoriesFragment);
            transaction.hide(ChooseStartPointFragment.this);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return fragmentView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMapLongClickListener(latLng -> {
            setMarker(latLng);
            placeAutoComplete.setText("");
        });

        initMarker();
    }

    private void initMarker() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialMarkerPosition));
        if (isLocatedNearby) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            marker = mMap.addMarker(new MarkerOptions().position(initialMarkerPosition)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            //goNext.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        } else
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    private void setMarker(LatLng coordinates) {

        if (marker != null)
            marker.remove();

        marker = mMap.addMarker(new MarkerOptions().position(coordinates)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        //goNext.setVisibility(View.VISIBLE);
        ll.setVisibility(View.VISIBLE);
    }

}

