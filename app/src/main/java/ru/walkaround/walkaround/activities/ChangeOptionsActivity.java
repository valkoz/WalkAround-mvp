package ru.walkaround.walkaround.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import ru.walkaround.walkaround.R;

public class ChangeOptionsActivity extends AppCompatActivity {

    private SeekBar timeSeekBar;
    private SeekBar distanceSeekBar;
    private TextView time;
    private TextView distance;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_options);


        timeSeekBar = findViewById(R.id.change_options_seekbar_time);
        distanceSeekBar = findViewById(R.id.change_options_seekbar_distance);
        time = findViewById(R.id.change_options_textview_time);
        distance = findViewById(R.id.change_options_textview_distance);
        button = findViewById(R.id.change_options_refresh_routes);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 1)
                    time.setText(String.valueOf(progress) + " час");
                else if (progress > 1 && progress < 5)
                    time.setText(String.valueOf(progress) + " часа");
                else
                    time.setText(String.valueOf(progress) + " часов");
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 1)
                    distance.setText(String.valueOf(progress) + " километр");
                else if (progress > 1 && progress < 5)
                    distance.setText(String.valueOf(progress) + " километра");
                else
                    distance.setText(String.valueOf(progress) + " километров");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(ChangeOptionsActivity.this, MainActivity.class);
            intent.putExtra("flag", true);
            startActivity(intent);
        });
    }
}
