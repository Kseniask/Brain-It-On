package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskDesc extends AppCompatActivity {
String task_id;
Cursor cursor;
DBManager dbManager;
TextView task_title, task_due,descr, days_left;
SimpleDateFormat dateFormat;
Date due_date;
Calendar cal;
int due_day_of_year, today_day_of_year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);
        Menu();

        dbManager = new DBManager(this);
        dbManager.open();

        task_title= findViewById(R.id.tasktitle);
        task_due = findViewById(R.id.dueDate);
        descr = findViewById(R.id.txtDesc);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        cursor = dbManager.get_task_by_id(task_id);
        task_title.setText(cursor.getString(cursor.getColumnIndex("title")));
        descr.setText(cursor.getString(cursor.getColumnIndex("description")));
        try {
            due_date = dateFormat.parse(cursor.getString(cursor.getColumnIndex("due_date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal = Calendar.getInstance();
        cal.setTime(due_date);
        due_day_of_year = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(new Date());
        today_day_of_year = cal.get(Calendar.DAY_OF_YEAR);
        System.out.println();

//      task_due.setText("DUE IN " +(cursor.getString(cursor.getColumnIndex("title")));


    }

    public void Menu() {
        Button menuBtn = (Button) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDesc.this, Menu.class);
                startActivity(intent);
            }
        });
        }
    }
