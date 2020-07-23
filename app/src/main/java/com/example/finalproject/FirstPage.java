package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.Serializable;
import java.util.ArrayList;


public class FirstPage extends AppCompatActivity {

    private DBManager dbManager;
    TextView level_txt;
    ProgressBar progress;
    ArrayList<String> tasks;
    ArrayList<String> task_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listView);
        dbManager = new DBManager(this);
        dbManager.open();
        Intent intent = getIntent();
        User active_user = (User) intent.getExtras().getParcelable("active_user");

        if(!dbManager.fetch_user_tasks(active_user.getUser_id()).moveToFirst()){

        }
        else {
            tasks = null;
            task_id=null;
            Cursor cursor = dbManager.fetch_due_date_order(active_user.getUser_id());
            cursor.getString(1);
           tasks = new ArrayList<String>();
            task_id = new ArrayList<String>();

            tasks.add(cursor.getString(1));
            task_id.add(cursor.getString(0));
            if(cursor.moveToNext()) {
                tasks.add(cursor.getString(1));
                task_id.add(cursor.getString(0));
                if(cursor.moveToNext()) {
                    tasks.add(cursor.getString(1));
                    task_id.add(cursor.getString(0));
                }
            }
        }
        level_txt = findViewById(R.id.levelTxt);
        level_txt.setText("Level " + active_user.getLevel());
        progress = findViewById(R.id.progressBar);
        progress.setProgress(active_user.getLevel_percent());
        if(tasks!=null) {
            ArrayAdapter adapter = new ArrayAdapter(this,
                    R.layout.view_task_item, tasks);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(FirstPage.this, CreateUpdate.class);
//                    switch (position) {
//                        case 0:
//                            intent.putExtra("task_to_update", task_id.get(0));
//                            break;
//                        case 1:
//                            intent.putExtra("task_to_update", task_id.get(1));
//                            break;
//                        case 2:
//                            intent.putExtra("task_to_update", task_id.get(2));
//                            break;
//                    }
//                    startActivity(intent);
//
//                }
//            });
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(208, 224,
                            210)));
                    // set item width
                    openItem.setWidth(130);
                    // set item title
                    openItem.setTitle("Edit");
                    // set item title fontsize
                    openItem.setTitleSize(13);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                            51, 51)));
                    // set item width
                    deleteItem.setWidth(130);
                    // set a icon
                    deleteItem.setTitle("Done");
                    // set item title fontsize
                    deleteItem.setTitleSize(13);
                    deleteItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
// set creator
            listView.setMenuCreator(creator);
            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                                    Intent intent = new Intent(FirstPage.this, CreateUpdate.class);
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
                            // delete
                            Toast.makeText(FirstPage.this, "Great job fella!", Toast.LENGTH_LONG).show();
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });
        }
        else{

        }
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.view_task_item, tasks);
//        ListView listView = (ListView)findViewById(R.id.list);
//        listView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, Menu.class);
                startActivity(intent);
            }
        });
    }

}
