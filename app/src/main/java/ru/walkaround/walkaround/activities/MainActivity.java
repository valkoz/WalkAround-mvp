package ru.walkaround.walkaround.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.StubDataUtils;
import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.Route;

//TODO: Refactor
public class MainActivity extends AppCompatActivity {

    private List<Route> routes = new ArrayList<>();
    private MapView mapView;
    private GoogleMap map;
    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.main_map_view);
        mapView.onCreate(savedInstanceState);

        LinearLayout layout = findViewById(R.id.bottom_sheet);

        sheetBehavior = BottomSheetBehavior.from(layout);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

        });

        StubDataUtils.generateDemoRoutes(this, routes); //TODO: Remove in prod

        boolean flag = getIntent().getBooleanExtra("flag", false);
        if (!flag) {
            mapView.getMapAsync(this::setUpMap);
        } else {
            mapView.getMapAsync(this::setUpMapRerouted);
        }
    }

    private void setUpMapRerouted(GoogleMap map) {

    }

    private void setUpMap(GoogleMap map) {
        this.map = map;

        Route chosen = routes.get(0);
        List<Place> chosenPlaces = chosen.getPlaces();

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(chosenPlaces.get(0).getLatLng());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

        map.moveCamera(center);
        map.animateCamera(zoom);

        LatLng previous = null;

        for (Place place : chosenPlaces) {

            map.addMarker(new MarkerOptions()
                    .position(place.getLatLng())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map)));

            if (previous != null) {
                map.addPolyline(new PolylineOptions()
                        .add(previous, place.getLatLng())
                        .width(5)
                        .color(R.color.textColor));
            }

            previous = place.getLatLng();
        }

        map.setOnMarkerClickListener(marker -> {
            map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            /*Toast.makeText(getContext(), "Снизу появляется карточка с краткой информацией о месте: " +
                    "Мини-фото, название, рейтинг, принадлежность к категории в виде соответствующей иконки.", Toast.LENGTH_LONG).show();
            */
            return false;
        });

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
