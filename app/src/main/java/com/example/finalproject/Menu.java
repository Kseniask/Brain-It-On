package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {
    CardView account;
    CardView calendar;
    CardView addTask;
    CardView myTasks;
    CardView findFriends;
    CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        account = findViewById(R.id.account);
        addTask = findViewById(R.id.add);
        logout = findViewById(R.id.log_out);
        myTasks = findViewById(R.id.myTasks);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Account.class));
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, CreateUpdate.class));
            }
        });
        myTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ListOfTasks.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, LoginPage.class));
            }
        });
    }
}
