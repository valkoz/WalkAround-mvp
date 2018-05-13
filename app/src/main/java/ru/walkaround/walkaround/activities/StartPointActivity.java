package ru.walkaround.walkaround.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
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

public class StartPointActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlaceAutocompleteFragment placeAutoComplete;
    private Marker marker;
    private LinearLayout ll;

    private LatLng initialMarkerPosition;
    private boolean isLocatedNearby;

    private Button goNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_point);

        getWindow().setExitTransition(new Slide(Gravity.LEFT));
        //getWindow().setEnterTransition(new Slide(Gravity.END));

        goNext = findViewById(R.id.go_next_button1);
        ll = findViewById(R.id.choose_start_point_button_layout);


        double lng = getIntent().getDoubleExtra(IntentUtils.LONGITUDE, 0);
        double lat = getIntent().getDoubleExtra(IntentUtils.LATITUDE, 0);
        initialMarkerPosition = new LatLng(lat, lng);
        isLocatedNearby = getIntent().getBooleanExtra(IntentUtils.IS_LOCATIONS_NEARBY, false);

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        goNext.setOnClickListener((view) -> {
            Intent intent = new Intent(StartPointActivity.this, CategoryActivity.class);

            Log.i("OnClick", "GoNext");

            intent.putExtra(IntentUtils.LONGITUDE, marker.getPosition().longitude);
            intent.putExtra(IntentUtils.LATITUDE, marker.getPosition().latitude);


            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
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
        }
        else
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
