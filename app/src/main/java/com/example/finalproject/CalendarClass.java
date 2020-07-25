package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarClass extends AppCompatActivity {
DBManager dbManager;
MCalendarView calendarView;
Cursor cursor;
User active_user;
Date dueDate;
String chosenDate;
SimpleDateFormat dateFormat;
Calendar cal;
ArrayList<String> tasks,task_id;
    SwipeMenuListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        listView = (SwipeMenuListView)findViewById(R.id.listView);
        dbManager = new DBManager(this);
        dbManager.open();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        active_user = dbManager.getActiveUser();
        cal = Calendar.getInstance();
        tasks = new ArrayList<String>();
        task_id = new ArrayList<String>();
        adapter = new ArrayAdapter(this,
                R.layout.view_task_item, tasks);

        //work with calendar
        calendarView = ((MCalendarView)findViewById(R.id.calendarView));
        Menu();

        cursor = dbManager.select_distinct_days(Integer.parseInt(active_user.getUser_id()));
        ArrayList<DateData> dates=new ArrayList<>();
        if(cursor!=null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                try {
                    dueDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex("due_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(dueDate);

                dates.add(new DateData(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH)+1, cal.get(java.util.Calendar.DAY_OF_MONTH)));
                cursor.moveToNext();
            }
            cursor.close();
            for (int i = 0; i < dates.size(); i++) {
                calendarView.markDate(dates.get(i).getYear(), dates.get(i).getMonth(), dates.get(i).getDay()).setMarkedStyle(MarkStyle.DOT);//mark multiple dates with this code.
            }
            calendarView.setOnDateClickListener(new OnDateClickListener() {
                @Override
                public void onDateClick(View view, DateData date) {
                    String month;
                    tasks.clear();
                    task_id.clear();

                    if(date.getMonth() < 10){
                        month= "0"+date.getMonth();
                    }
                    else{
                        month = Integer.toString(date.getMonth());
                    }
                    chosenDate = date.getYear()+ "-"+ month + "-"+ date.getDay();
                    System.out.println(chosenDate);
                    cursor = dbManager.fetch_tasks_day(chosenDate, Integer.parseInt(active_user.getUser_id()));
                    if (cursor.getCount() !=0) {
                        int count_tasks = cursor.getCount();
                        for (int i = 0; i < count_tasks; i++) {
                            tasks.add(cursor.getString(1));
                            task_id.add(cursor.getString(0));
                            cursor.moveToNext();
                        }
                        adapter.notifyDataSetChanged();

                    }else{
                    System.out.println("NOTHING FOUND");}
//                    System.out.println(cursor.getString(cursor.getColumnIndex("title")));
                    cursor.close();
                }
            });

        if(tasks!=null) {

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CalendarClass.this, CreateUpdate.class);
                    intent.putExtra("task_to_update", task_id.get(position));
                    startActivity(intent);

                }
            });
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getApplicationContext());
                    openItem.setBackground(new ColorDrawable(Color.rgb(208, 224,
                            210)));
                    openItem.setWidth(130);
                    openItem.setTitle("Edit");
                    openItem.setTitleSize(13);
                    openItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(openItem);
                }
            };

            listView.setMenuCreator(creator);

            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                            Intent intent = new Intent(CalendarClass.this, CreateUpdate.class);
                            intent.putExtra("task_to_update", task_id.get(position));
                            startActivity(intent);
                    return false;
                }
            });
        }
        }
    }
public void Menu(){
    Button menuBtn = (Button)findViewById(R.id.menuBtn);
    menuBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CalendarClass.this, Menu.class);
            startActivity(intent);
        }
    });
}
}
