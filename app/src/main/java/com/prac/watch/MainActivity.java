package com.prac.watch;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockView = findViewById(R.id.clockView);

        // Start a thread or use a Handler to update the clock
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateClock();
                handler.postDelayed(this, 1000); // Update every second
            }
        });
    }

    private void updateClock() {
        // Assuming you have a method in ClockView to update the time
        clockView.setCurrentTime();
    }
}
