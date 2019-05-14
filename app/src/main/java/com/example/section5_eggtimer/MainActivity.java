package com.example.section5_eggtimer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    long timeToCountdown;
    SeekBar timerSeekbar;
    CountDownTimer timer;
    TextView timerTextView;
    int maxSeconds = 600;
    int startingSecondsPosition = 300;
    boolean running = true;


    public void showTime() {
        timerTextView = findViewById(R.id.timerTextView);
        @SuppressLint("DefaultLocale") String timeString = String.format("%02d:%02d", timeToCountdown / 60, timeToCountdown % 60);
        timerTextView.setText(timeString);
    }

    @SuppressLint("SetTextI18n")
    public void eggTimerButton(View view) {
        Button eggTimerButton = findViewById(R.id.startStopButton);
        if (running) {
            running = false;
            eggTimerButton.setText("PAUSE!");
            timerSeekbar.setEnabled(false);
            countDownTimer();
        } else {
            running = true;
            eggTimerButton.setText("START!");
            timerSeekbar.setEnabled(true);
            timerPause();
        }
    }

    public void timerPause() {
        timer.cancel();
    }

    public void seekBarChange() {
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeToCountdown = i;
                showTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void countDownTimer() {
        timer = new CountDownTimer(timeToCountdown * 1000, 1000) {
            public void onTick(long l) {
                timeToCountdown = l / 1000;
                timerSeekbar.setProgress((int)timeToCountdown);
                showTime();
            }

            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this ,R.raw.beep);
                mediaPlayer.start();
            }
        };
        timer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerSeekbar.setMax(maxSeconds);
        timerSeekbar.setProgress(startingSecondsPosition);
        timeToCountdown = startingSecondsPosition;
        showTime();
        seekBarChange();
    }

}
