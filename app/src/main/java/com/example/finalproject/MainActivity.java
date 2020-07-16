package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView view = (ImageView)findViewById(R.id.brainImg);

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(anim);

        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent( MainActivity.this, Menu.class));
            }
        };
        Timer open = new Timer();
        open.schedule(task,5000);
    }
}
