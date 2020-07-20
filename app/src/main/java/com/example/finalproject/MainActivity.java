package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    Cursor cursor;
    boolean found=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView view = (ImageView)findViewById(R.id.brainImg);
        dbManager = new DBManager(this);
        dbManager.open();
        cursor = dbManager.fetch_users();
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(anim);

        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                finish();

                while (cursor.isAfterLast() != true && found == false) {
                    int login =  cursor.getInt(cursor.getColumnIndex("is_logged_in"));
                    if(login ==1){
                        found=true;
                    }
                }
                System.out.println(found);
                if(found ==true){
                    startActivity(new Intent( MainActivity.this, FirstPage.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
            }
        };
        Timer open = new Timer();
        open.schedule(task,1000);
    }
}
