package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ListOfTasks extends ListActivity {
String[] tasks = {"Water Flowers", "4050 Lab", "Print Photos", "Make a Report","Call Grandma","Quiz Preparation"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks));

    }
}
