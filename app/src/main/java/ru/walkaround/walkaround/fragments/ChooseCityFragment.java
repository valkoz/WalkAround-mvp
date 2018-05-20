package ru.walkaround.walkaround.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ContextThemeWrapper;
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

import ru.walkaround.walkaround.IntentUtils;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.PlaceAutocompleteAdapter;

public class ChooseCityFragment extends Fragment {

    private static final String TAG = "ChooseCityFragment";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    public static final int MAX_DISTANCE_TO_CITY_CENTER = 15000;

    private LatLng latLng;
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    private Button goNext;
    private Location userLocation;
    private Location cityLocation;

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

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.NoActionBarTransparent);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.fragment_choose_city, container, false);

        mGeoDataClient = Places.getGeoDataClient(getActivity());

        userLocation = new Location("userLocation");
        userLocation.setLatitude(latLng.latitude);
        userLocation.setLongitude(latLng.longitude);

        mAutocompleteView = view.findViewById(R.id.autocomplete_city);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        mAdapter = new PlaceAutocompleteAdapter(getContext(), mGeoDataClient, new LatLngBounds(latLng, latLng), filter);
        mAutocompleteView.setAdapter(mAdapter);

        goNext = view.findViewById(R.id.go_next_button);
        goNext.setOnClickListener(v -> {

            Log.i("OnClick", String.valueOf(userLocation.distanceTo(cityLocation)));

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

            ChooseStartPointFragment chooseStartPointFragment = ChooseStartPointFragment.newInstance();
            chooseStartPointFragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.add(R.id.fragment_content, chooseStartPointFragment);
            transaction.hide(ChooseCityFragment.this);
            transaction.addToBackStack(null);
            transaction.commit();

        });


        return view;
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

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }
}
