package ru.walkaround.walkaround.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ru.walkaround.walkaround.IntentUtils;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.PlaceAutocompleteAdapter;

public class ChooseCityActivity extends AppCompatActivity {

    public static final String TAG = "SampleActivityBase";
    public static final int MAX_DISTANCE_TO_CITY_CENTER = 15000;

    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private LatLngBounds bounds;

    private Button goNext;

    private Location userLocation;

    private Location cityLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ChooseCityActivity", "started");

        /* Нужна тема NoActionBarTransparent, которая сломана*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        double lat = getIntent().getDoubleExtra(IntentUtils.LATITUDE, 0);
        double lng = getIntent().getDoubleExtra(IntentUtils.LONGITUDE, 0);

        Log.i(TAG, String.valueOf(lat));
        Log.i(TAG, String.valueOf(lng));

        userLocation = new Location("userLocation");
        userLocation.setLatitude(lat);
        userLocation.setLongitude(lng);

        bounds = new LatLngBounds(new LatLng(lat, lng), new LatLng(lat, lng));


        mGeoDataClient = Places.getGeoDataClient(this);

        setContentView(R.layout.activity_choose_city);
        mAutocompleteView = findViewById(R.id.autocomplete_city);


        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, bounds, filter);
        mAutocompleteView.setAdapter(mAdapter);

        goNext = findViewById(R.id.go_next_button);
        goNext.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseCityActivity.this, StartPointActivity.class);

            Log.i("OnClick", String.valueOf(userLocation.distanceTo(cityLocation)));

            if (userLocation.distanceTo(cityLocation) < MAX_DISTANCE_TO_CITY_CENTER) {
                intent.putExtra(IntentUtils.IS_LOCATIONS_NEARBY, true);
                intent.putExtra(IntentUtils.LONGITUDE, userLocation.getLongitude());
                intent.putExtra(IntentUtils.LATITUDE, userLocation.getLatitude());
            } else {
                intent.putExtra(IntentUtils.IS_LOCATIONS_NEARBY, false);
                intent.putExtra(IntentUtils.LONGITUDE, cityLocation.getLongitude());
                intent.putExtra(IntentUtils.LATITUDE, cityLocation.getLatitude());
            }

            startActivity(intent);
        });

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);

            if (item != null) {
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);


                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
                placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);

                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

                hideKeyboard();
            }
        }
    };

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @Override
        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places = task.getResult();

                final Place city = places.get(0);

                Log.i(TAG, "Place coordinates received: " + city.getLatLng());

                cityLocation = new Location("cityLocation");
                cityLocation.setLatitude(city.getLatLng().latitude);
                cityLocation.setLongitude(city.getLatLng().longitude);

                places.release();
                goNext.setVisibility(View.VISIBLE);
            } catch (RuntimeRemoteException e) {
                Log.e(TAG, "Place query did not complete.", e);
                return;
            }
        }
    };

}