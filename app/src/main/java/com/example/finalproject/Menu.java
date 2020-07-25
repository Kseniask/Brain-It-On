package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    CardView account;
    CardView calendar;
    CardView addTask;
    CardView myTasks;
    CardView home;
    CardView logout;
    DBManager dbManager;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbManager = new DBManager(this);
        dbManager.open();

        account = findViewById(R.id.account);
        addTask = findViewById(R.id.add);
        logout = findViewById(R.id.log_out);
        myTasks = findViewById(R.id.myTasks);
        home = findViewById(R.id.home);
        calendar = findViewById(R.id.calendar);
        account.setOnClickListener(this);
        logout.setOnClickListener(this);
        addTask.setOnClickListener(this);
        myTasks.setOnClickListener(this);
        home.setOnClickListener(this);
        calendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home:
                startActivity(new Intent(Menu.this, FirstPage.class));
                break;
            case R.id.account:
                startActivity(new Intent(Menu.this, Account.class));
                break;
            case R.id.add:
                startActivity(new Intent(Menu.this, CreateUpdate.class));
                break;
            case R.id.myTasks:
                startActivity(new Intent(Menu.this, ListOfTasks.class));
                break;
            case R.id.calendar:
                startActivity(new Intent(Menu.this, CalendarClass.class));
                break;

            case R.id.log_out:
            cursor = dbManager.fetch_active_user();
            dbManager.update_login(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))),0);
                startActivity(new Intent(Menu.this, LoginPage.class));
                break;
        }
    }
}
