package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
Button signup, login;
EditText username_txt,password_txt;
String username, password;
DBManager dbManager;
Cursor cursor;
User found_user,active_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        dbManager = new DBManager(this);
        dbManager.open();

        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Menu.class);
                startActivity(intent);
            }
        });
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_txt = findViewById(R.id.username);
                password_txt = findViewById(R.id.password);
                username = username_txt.getText().toString();
                password = password_txt.getText().toString();

                cursor = dbManager.fetch_user_by_username(username);
                if(cursor != null && cursor.moveToFirst()) {
                    found_user = dbManager.getUser(cursor);
                    if (found_user.Check_Password(password)) {
                        dbManager.update_login(Integer.parseInt(found_user.getUser_id()), 1);
                        active_user = dbManager.getActiveUser();
                        Intent intent = new Intent(LoginPage.this, FirstPage.class);
                        intent.putExtra("active_user", active_user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginPage.this, "Login failed. Check your username or password", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginPage.this, "Login failed. Check your username or password", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Register.class));
            }
        });
    }
}
