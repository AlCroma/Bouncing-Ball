package com.example.bouncingball;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    UpdateThread updateThread;
    MovementView movementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);

        //To add the canvas to Layout
        movementView = new MovementView(this);
        relativeLayout.addView(movementView);

        //Dynamic creation of start and stop button
        Button startButton = new Button(this);
        startButton.setId(R.id.startButton);
        startButton.setText("START");
        startButton.setTextSize(24);
        startButton.setBackgroundColor(Color.DKGRAY);
        startButton.setTextColor(Color.WHITE);

        Button stopButton = new Button(this);
        stopButton.setText("STOP");
        stopButton.setId(R.id.stopButton);
        stopButton.setTextSize(24);
        stopButton.setBackgroundColor(Color.DKGRAY);
        stopButton.setTextColor(Color.WHITE);

        RelativeLayout.LayoutParams layoutParamsStart = new RelativeLayout.LayoutParams(400,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsStart.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        startButton.setLayoutParams(layoutParamsStart);

        RelativeLayout.LayoutParams layoutParamsStop = new RelativeLayout.LayoutParams(400,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsStop.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        stopButton.setLayoutParams(layoutParamsStop);

        //Start Button on Click Listener to make the ball move.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();
                updateThread = new UpdateThread(movementView);
                updateThread.setRunning(true);
                updateThread.start();
            }
        });

        //Stop Button on Click Listener to stop the ball movement.
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_SHORT).show();
                updateThread.setRunning(false);
            }
        });

        //To add both start and stop button on Layout.
        relativeLayout.addView(startButton);
        relativeLayout.addView(stopButton);
    }
}