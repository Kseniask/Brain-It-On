package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Account extends AppCompatActivity {
DBManager dbManager;
ImageView imgLogo;
TextView todo,done,username,level,email;
Cursor cursor;
Button edit,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        dbManager = new DBManager(this);
        dbManager.open();

        final User active_user = dbManager.getUser(dbManager.fetch_active_user());
        imgLogo = findViewById(R.id.acc_img);
        if(active_user.getGender().equalsIgnoreCase("male")){
            imgLogo.setImageResource(R.drawable.man);
        }
        todo = findViewById(R.id.todo_count);
        done =findViewById(R.id.done_count);
        username = findViewById(R.id.username_txt);
        level = findViewById(R.id.level_acc_txt);
        email = findViewById(R.id.email_txt);

        username.setText(active_user.getUsername());
        level.setText("Level "+active_user.getLevel());
        email.setText(active_user.getEmail());
        cursor = dbManager.fetch_user_tasks(active_user.getUser_id());
        todo.setText(""+cursor.getCount());
        cursor.close();
        cursor = dbManager.fetch_user_tasks_done(active_user.getUser_id());
        done.setText(""+cursor.getCount());
        cursor.close();

        edit = findViewById(R.id.btnEdit_acc);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Register.class);
                intent.putExtra("active_user", "edit");
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.btnLogout_acc);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.update_login(Integer.parseInt(active_user.getUser_id()),0);
                startActivity(new Intent(Account.this, LoginPage.class));
            }
        });
        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}
