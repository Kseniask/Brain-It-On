package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;


public class FirstPage extends AppCompatActivity {
//    String[] tasks = {"Do Final Project", "Final exam", "Visit Doctor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listView);
        ArrayList<String> tasks = new ArrayList<String>();
        tasks.add("Do Final Project");
        tasks.add("Final exam");
        tasks.add("Visit Doctor");

        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.view_task_item, tasks);
        listView.setAdapter(adapter);

//        swipe menu code
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
                        // open
                        startActivity(new Intent(FirstPage.this, CreateUpdate.class));
                        break;
                    case 1:
                        // delete
                        Toast.makeText(FirstPage.this,"Great job fella!",Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
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
