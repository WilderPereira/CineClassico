package com.studiotrek.cineclassico.view.activity;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.studiotrek.cineclassico.R;

import java.util.Timer;
import java.util.TimerTask;

public class ReproducaoActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    private EMVideoView emVideoView;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducao);

        emVideoView = (EMVideoView) findViewById(R.id.video_view);
        emVideoView.setVideoURI(Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"));
        emVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        emVideoView.pause();
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 5000, 10000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        View decorView = getWindow().getDecorView();
                        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
                    }
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

}
