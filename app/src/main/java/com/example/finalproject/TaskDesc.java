package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class TaskDesc extends AppCompatActivity {
String task_id;
Cursor cursor;
DBManager dbManager;
TextView task_title, task_due,descr, days_left;
SimpleDateFormat dateFormat;
Date due_date,date_scheduled;
Calendar cal;
int due_day_of_year, today_day_of_year;
Button edit, done;
MCalendarView calendarView;
MethodHelper helper;
User active_user;

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
        calendarView = ((MCalendarView) findViewById(R.id.calendarView2));
        helper = new MethodHelper();
        //get the taskl
        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        cursor = dbManager.get_task_by_id(task_id);
        active_user = dbManager.getUser(dbManager.fetch_active_user());
        task_title.setText(cursor.getString(cursor.getColumnIndex("title")));
        descr.setText(cursor.getString(cursor.getColumnIndex("description")));
        try {
            due_date = dateFormat.parse(cursor.getString(cursor.getColumnIndex("due_date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();

        cursor = dbManager.select_distinct_days(Integer.parseInt(active_user.getUser_id()));
        ArrayList<DateData> dates=new ArrayList<>();

        if(cursor.moveToFirst()) {
            //add dates to the list
            for (int i = 0; i < cursor.getCount(); i++) {
                try {
                    date_scheduled = dateFormat.parse(cursor.getString(cursor.getColumnIndex("due_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(date_scheduled);
                dates.add(new DateData(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1, cal.get(java.util.Calendar.DAY_OF_MONTH)));
                cursor.moveToNext();
            }
            cursor.close();
        }

            //for each date highlight it in the calendar
            for (int j = 0;  j< dates.size(); j++) {
                System.out.println(dates.get(j).getYear() +" "+ dates.get(j).getMonth()+" "+  dates.get(j).getDay());
                calendarView.unMarkDate(dates.get(j).getYear(), dates.get(j).getMonth(), dates.get(j).getDay());
            }

            cal.setTime(due_date);
        calendarView.markDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)).setMarkedStyle(MarkStyle.DOT);//mark multiple dates with this code.

        due_day_of_year = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(new Date());
        today_day_of_year = cal.get(Calendar.DAY_OF_YEAR);

      task_due.setText("Due in " +(due_day_of_year-today_day_of_year) + " days");
       edit = findViewById(R.id.btnEdit);
       done = findViewById(R.id.btnDone);

       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(TaskDesc.this, CreateUpdate.class);
               intent.putExtra("task_to_update", task_id);
               startActivity(intent);
           }
       });

       done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            helper.updateDone(dbManager, task_id);

               Toast.makeText(TaskDesc.this, "Great job, fella!", Toast.LENGTH_LONG).show();
               Intent intent2 = new Intent(TaskDesc.this, FirstPage.class);
               intent2.putExtra("active_user", dbManager.getUser(dbManager.fetch_active_user()));
               startActivity(intent2);
           }
       });
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
