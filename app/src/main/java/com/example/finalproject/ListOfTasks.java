package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class ListOfTasks extends AppCompatActivity {
    ArrayList<String> tasks;
    ArrayList<String> task_id;
    DBManager dbManager;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_tasks);
        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listView2);
//        ArrayList<String> tasks = new ArrayList<String>();
//        tasks.add("Do Final Project");
//        tasks.add("Final exam");
//        tasks.add("Visit Doctor");
//        tasks.add("Call grandma");
//        tasks.add("Water Flowers");
//        tasks.add("Register for IELTS");
//
//        ArrayAdapter adapter = new ArrayAdapter(this,
//                R.layout.view_all_tasks, tasks);
//        listView.setAdapter(adapter);
        dbManager = new DBManager(this);
        dbManager.open();

        Intent intent = getIntent();
        User active_user = dbManager.getUser(dbManager.fetch_active_user());
        cursor = dbManager.fetch_user_tasks(active_user.getUser_id());
        if(!cursor.moveToFirst()){

        }
        else {
            cursor.close();
            tasks = new ArrayList<String>();
            task_id = new ArrayList<String>();
            cursor = dbManager.fetch_due_date_order(active_user.getUser_id());
            int count_tasks = cursor.getCount();
            for(int i=0;i < count_tasks; i++){
                tasks.add(cursor.getString(1));
                task_id.add(cursor.getString(0));
                cursor.moveToNext();
            }

            cursor.close();
        }
        if(tasks!=null) {
            ArrayAdapter adapter = new ArrayAdapter(this,
                    R.layout.view_task_item, tasks);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListOfTasks.this, CreateUpdate.class);
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

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        51, 51)));
                deleteItem.setWidth(130);
                deleteItem.setTitle("Done");
                deleteItem.setTitleSize(13);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(ListOfTasks.this, CreateUpdate.class);
                        switch (position) {
                            case 0:
                                intent.putExtra("task_to_update", task_id.get(0));
                                break;
                            case 1:
                                intent.putExtra("task_to_update", task_id.get(1));
                                break;
                            case 2:
                                intent.putExtra("task_to_update", task_id.get(2));
                                break;
                        }
                        startActivity(intent);
                        break;
                    case 1:
                        String t_id = task_id.get(0);
                        dbManager.update_done(Integer.parseInt(t_id));
                        User active_user = dbManager.getActiveUser();
                        int id = Integer.parseInt(active_user.getUser_id());
                        if(active_user.getLevel() ==1){
                            dbManager.update_level_percent(id,50);
                            active_user.setLevel_percent(active_user.getLevel_percent()+50);
                            if(active_user.getLevel_percent()>= 100){
                                dbManager.update_level(id,2);
                                dbManager.update_level_percent(id,0);
                            }
                        }
                        else if(active_user.getLevel() ==2){
                            active_user.setLevel_percent(active_user.getLevel_percent()+30);
                            if(active_user.getLevel_percent()>= 100){
                                dbManager.update_level_percent(id,0);
                                dbManager.update_level(id,3);
                            }
                            else{
                                dbManager.update_level_percent(id,active_user.getLevel_percent());
                            }
                        }
                        else{
                            active_user.setLevel_percent(active_user.getLevel_percent()+70);
                            if(active_user.getLevel_percent()>= 100){
                                dbManager.update_level_percent(id,0);
                                dbManager.update_level(id,active_user.getLevel()+1);
                            }
                            else{
                                dbManager.update_level_percent(id,active_user.getLevel_percent());
                            }
                        }

                        // done
                        Toast.makeText(ListOfTasks.this, "Great job, fella!", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(ListOfTasks.this, ListOfTasks.class);
                        intent2.putExtra("active_user", active_user);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }
        //if tasks == null
        else{

        }
        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfTasks.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}
