package ru.walkaround.walkaround.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private List<Place> chosenPlaces = new ArrayList<>();
    private MapView mapView;
    private GoogleMap map;
    private FloatingActionButton fab;

    private RelativeLayout bottomCard;
    private TextView bottomCardPrimaryText;
    private TextView bottomCardSecondaryText;
    private ImageView bottomCardImageView;
    private ImageView bottomCardIcon;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.main_map_view);
        mapView.onCreate(savedInstanceState);

        fab = findViewById(R.id.map_fab);
        bottomCard = findViewById(R.id.bottom_card);
        bottomCardPrimaryText = findViewById(R.id.bottom_card_text_primary);
        bottomCardSecondaryText = findViewById(R.id.bottom_card_text_secondary);
        bottomCardImageView = findViewById(R.id.bottom_card_image);
        bottomCardIcon = findViewById(R.id.bottom_card_icon);
        ratingBar = findViewById(R.id.bottom_card_rating);

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
        chosenPlaces = chosen.getPlaces();

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(chosenPlaces.get(0).getLatLng());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

        map.moveCamera(center);
        map.animateCamera(zoom);

        LatLng previous = null;

        for (Place place : chosenPlaces) {


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(place.getLatLng())
                    .title(place.getName())
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_map));

            Marker marker = map.addMarker(markerOptions);
            marker.hideInfoWindow();

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
            marker.hideInfoWindow();
            bottomCard.setVisibility(View.VISIBLE);
            bottomCardPrimaryText.setText(marker.getTitle());

            for (Place place : chosenPlaces) {
                if (place.getName().equals(marker.getTitle())) {

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(50));

                    Glide.with(this)
                            .load(place.getImage())
                            .apply(requestOptions)
                            .into(bottomCardImageView);

                    bottomCardIcon.setImageResource(place.getType());
                    bottomCardSecondaryText.setText(place.getAddress());
                    ratingBar.setRating(place.getRating());
                }
            }

            return true;
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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
