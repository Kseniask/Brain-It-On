package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ListActivity;
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

public class ListOfTasks extends AppCompatActivity {
//String[] tasks = {"Water Flowers", "4050 Lab", "Print Photos", "Make a Report","Call Grandma","Quiz Preparation"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_tasks);
        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listView2);
        ArrayList<String> tasks = new ArrayList<String>();
        tasks.add("Do Final Project");
        tasks.add("Final exam");
        tasks.add("Visit Doctor");
        tasks.add("Call grandma");
        tasks.add("Water Flowers");
        tasks.add("Register for IELTS");

        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.view_all_tasks, tasks);
        listView.setAdapter(adapter);

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
                        startActivity(new Intent(ListOfTasks.this, CreateUpdate.class));
                        break;
                    case 1:
                        Toast.makeText(ListOfTasks.this,"Great job fella!",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

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
