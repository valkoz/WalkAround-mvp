package ru.walkaround.walkaround.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.walkaround.walkaround.IntentUtils;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.PlaceAutocompleteAdapter;

public class ChooseCityFragment extends Fragment {

    private static final String TAG = "ChooseCityFragment";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    public static final int MAX_DISTANCE_TO_CITY_CENTER = 15000;

    private LatLng latLng;
    protected GeoDataClient geoDataClient;
    private PlaceAutocompleteAdapter autocompleteAdapter;
    private Location cityLocation;

    @BindView(R.id.autocomplete_city)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.go_next_button)
    Button goNext;

    public ChooseCityFragment() {
    }

    public static ChooseCityFragment newInstance(double lat, double lng) {
        ChooseCityFragment fragment = new ChooseCityFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LNG, lng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latLng = new LatLng(getArguments().getDouble(LAT), getArguments().getDouble(LNG));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);
        ButterKnife.bind(this, view);

        geoDataClient = Places.getGeoDataClient(getActivity());
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        autocompleteAdapter = new PlaceAutocompleteAdapter(getContext(),
                geoDataClient,
                new LatLngBounds(latLng, latLng),
                new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                        .build());
        autoCompleteTextView.setAdapter(autocompleteAdapter);

        goNext.setOnClickListener(v -> onNextButtonClick());

        return view;
    }

    private void onNextButtonClick() {

        Location userLocation = new Location("userLocation");
        userLocation.setLatitude(latLng.latitude);
        userLocation.setLongitude(latLng.longitude);

        Bundle bundle = new Bundle();

        if (userLocation.distanceTo(cityLocation) < MAX_DISTANCE_TO_CITY_CENTER) {
            bundle.putBoolean(IntentUtils.IS_LOCATIONS_NEARBY, true);
            bundle.putDouble(IntentUtils.LONGITUDE, latLng.longitude);
            bundle.putDouble(IntentUtils.LATITUDE, latLng.latitude);
        } else {
            bundle.putBoolean(IntentUtils.IS_LOCATIONS_NEARBY, false);
            bundle.putDouble(IntentUtils.LONGITUDE, cityLocation.getLongitude());
            bundle.putDouble(IntentUtils.LATITUDE, cityLocation.getLatitude());
        }

        changeFragment(bundle);
    }

    private void changeFragment(Bundle bundle) {
        ChooseStartPointFragment chooseStartPointFragment = ChooseStartPointFragment.newInstance();
        chooseStartPointFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.in_from_right,
                R.anim.out_to_left,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.add(R.id.fragment_content, chooseStartPointFragment);
        transaction.hide(ChooseCityFragment.this);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private AdapterView.OnItemClickListener autocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = autocompleteAdapter.getItem(position);

            if (item != null) {
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                Task<PlaceBufferResponse> placeResult = geoDataClient.getPlaceById(placeId);
                placeResult.addOnCompleteListener(updatePlaceDetailsCallback);

                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

                hideKeyboard();
            }
        }
    };

    private OnCompleteListener<PlaceBufferResponse> updatePlaceDetailsCallback
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

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }
}
