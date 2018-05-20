package ru.walkaround.walkaround.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.walkaround.walkaround.IntentUtils;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.fragments.ChooseCityFragment;

public class StartActivity extends AppCompatActivity {

    public static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        double lat = getIntent().getDoubleExtra(IntentUtils.LATITUDE, 0);
        double lng = getIntent().getDoubleExtra(IntentUtils.LONGITUDE, 0);

        Log.i(TAG, String.valueOf(lat));
        Log.i(TAG, String.valueOf(lng));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, ChooseCityFragment.newInstance(lat, lng));
        transaction.commit();
    }
}
