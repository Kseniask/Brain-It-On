package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    DBManager dbManager;
    User activeUser;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView view = (ImageView)findViewById(R.id.brainImg);
        dbManager = new DBManager(this);
        dbManager.open();

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(anim);

        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                cursor = dbManager.fetch_users();
                if (!cursor.moveToFirst()) {
                    startActivity(new Intent(MainActivity.this, LoginPage.class));
                } else {
                    activeUser = dbManager.getActiveUser();
                    if (activeUser !=null) {
                        Intent intent = new Intent(MainActivity.this, FirstPage.class);
                        intent.putExtra("active_user", activeUser);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginPage.class));
                    }
                }
            }
        };
        Timer open = new Timer();
        open.schedule(task,1000);
    }
}
