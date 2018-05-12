package ru.walkaround.walkaround.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.main_map_view);
        mapView.onCreate(savedInstanceState);

        //LinearLayout layout = findViewById(R.id.bottom_sheet);
        fab = findViewById(R.id.map_fab);

        /*sheetBehavior = BottomSheetBehavior.from(layout);

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

        });*/

        StubDataUtils.generateDemoRoutes(this, routes); //TODO: Remove in prod

        boolean flag = getIntent().getBooleanExtra("flag", false);
        if (!flag) {
            mapView.getMapAsync(this::setUpMap);
        } else {
            mapView.getMapAsync(this::setUpMapRerouted);
        }

        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChangeOptionsActivity.class)));
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
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_map)));

            if (previous != null) {
                map.addPolyline(new PolylineOptions()
                        .add(previous, place.getLatLng())
                        .width(5)
                        .color(R.color.textColor));
            }

            previous = place.getLatLng();
        }

        /*map.setOnMarkerClickListener(marker -> {
            map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            *//*Toast.makeText(getContext(), "Снизу появляется карточка с краткой информацией о месте: " +
                    "Мини-фото, название, рейтинг, принадлежность к категории в виде соответствующей иконки.", Toast.LENGTH_LONG).show();
            *//*
            return false;
        });*/

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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
