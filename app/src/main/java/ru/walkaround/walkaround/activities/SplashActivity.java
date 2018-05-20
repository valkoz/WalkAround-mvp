package ru.walkaround.walkaround.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static ru.walkaround.walkaround.IntentUtils.LATITUDE;
import static ru.walkaround.walkaround.IntentUtils.LONGITUDE;

public class SplashActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    private final int REQUEST_LOCATION = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("SplashActivity", "started");

        askLocationPermission();
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
        } else {
            Log.i("SplashActivity", "Permission has already been granted");
            startChooseCity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("SplashActivity", "PermissionGranted");
                    startChooseCity();

                } else {
                    Log.i("SplashActivity", "PermissionDenied");
                    startChooseCity();
                }
                return;
            }

        }
    }

    private void startChooseCity() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final Intent intent = new Intent(this, StartActivity.class);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
            finish();
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {

                    if (location != null) {
                        Log.i("Success:", String.valueOf(location));
                        intent.putExtra(LATITUDE, location.getLatitude());
                        intent.putExtra(LONGITUDE, location.getLongitude());
                    } else {
                        Log.i("Success:", "Location == null");
                    }

                    startActivity(intent);
                    finish();
                });
    }

}
