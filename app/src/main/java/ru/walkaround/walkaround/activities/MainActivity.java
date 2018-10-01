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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.StubDataUtils;
import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.Route;

//TODO: Refactor
public class MainActivity extends AppCompatActivity {

    private List<Route> routes = new ArrayList<>();
    private List<Place> chosenPlaces = new ArrayList<>();
    private GoogleMap map;

    @BindView(R.id.main_map_view)
    MapView mapView;
    @BindView(R.id.map_fab)
    FloatingActionButton fab;
    @BindView(R.id.bottom_card)
    RelativeLayout bottomCard;
    @BindView(R.id.bottom_card_text_primary)
    TextView bottomCardPrimaryText;
    @BindView(R.id.bottom_card_text_secondary)
    TextView bottomCardSecondaryText;
    @BindView(R.id.bottom_card_image)
    ImageView bottomCardImageView;
    @BindView(R.id.bottom_card_icon)
    ImageView bottomCardIcon;
    @BindView(R.id.bottom_card_rating)
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mapView = findViewById(R.id.main_map_view);
        mapView.onCreate(savedInstanceState);

        StubDataUtils.generateDemoRoutes(this, routes); //TODO: Remove in prod

        boolean flag = getIntent().getBooleanExtra("flag", false);
        if (!flag) {
            mapView.getMapAsync(googleMap -> setUpMap(googleMap, 0));
        } else {
            mapView.getMapAsync(googleMap -> setUpMap(googleMap, 1));
        }

        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChangeOptionsActivity.class)));
    }

    private void setUpMap(GoogleMap map, int routeIndex) {
        if (routeIndex == 0) {
            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(55.756221, 37.617069), new LatLng(55.754267, 37.620739))
                    .add(new LatLng(55.754267, 37.620739), new LatLng(55.7548273, 37.6209261))
                    .add(new LatLng(55.7548273, 37.6209261), new LatLng(55.755586, 37.619868))
                    .add(new LatLng(55.755586, 37.619868), new LatLng(55.758993, 37.625046))
                    .add(new LatLng(55.758993, 37.625046), new LatLng(55.759348, 37.625468))
                    .add(new LatLng(55.759348, 37.625468), new LatLng(55.7598306, 37.6257178))
                    .width(5)
                    .color(R.color.textColor));

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(55.7570495, 37.6214716));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            map.moveCamera(center);
            map.animateCamera(zoom);
        } else {
            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(55.756221, 37.617069), new LatLng(55.756358, 37.615704))
                    .add(new LatLng(55.756358, 37.615704), new LatLng(55.756925, 37.615625))
                    .add(new LatLng(55.756925, 37.615625), new LatLng(55.757464, 37.616638))
                    .add(new LatLng(55.757464, 37.616638), new LatLng(55.758472, 37.618278))
                    .add(new LatLng(55.758472, 37.618278), new LatLng(55.758656, 37.619372))
                    .add(new LatLng(55.758656, 37.619372), new LatLng(55.759725, 37.618823))
                    .add(new LatLng(55.759725, 37.618823), new LatLng(55.759871, 37.619551))
                    .add(new LatLng(55.759871, 37.619551), new LatLng(55.760956, 37.619083))
                    .add(new LatLng(55.760956, 37.619083), new LatLng(55.761610, 37.618255))
                    .add(new LatLng(55.761610, 37.618255), new LatLng(55.7603913, 37.6151306))
                    .width(5)
                    .color(R.color.textColor));

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(55.758860, 37.616556));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
            map.moveCamera(center);
            map.animateCamera(zoom);
        }

        this.map = map;
        chosenPlaces = routes.get(routeIndex).getPlaces();

        /*CameraUpdate center =
                CameraUpdateFactory.newLatLng(chosenPlaces.get(0).getLatLng());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

        map.moveCamera(center);
        map.animateCamera(zoom);*/

//        LatLng previous = null;

        for (Place place : chosenPlaces) {


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(place.getLatLng())
                    .title(place.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            //.icon(bitmapDescriptorFromVector(this, R.drawable.ic_map));

            Marker marker = map.addMarker(markerOptions);
            marker.hideInfoWindow();

            /*if (previous != null) {
                map.addPolyline(new PolylineOptions()
                        .add(previous, place.getLatLng())
                        .width(5)
                        .color(R.color.textColor));
            }

            previous = place.getLatLng();*/
        }

        map.setOnMarkerClickListener(marker -> {
            map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
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
