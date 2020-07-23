package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {
CheckBox cbMale,cbFemale;
EditText usernameTxt,passwordTxt,emailTxt;
Button registerBtn;
String username,password,gender,email;
DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button menuBtn = (Button)findViewById(R.id.menuBtn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Menu.class);
                startActivity(intent);
            }
        });

        dbManager = new DBManager(this);
        dbManager.open();

        //set the textEdit views
        usernameTxt = findViewById(R.id.username);
        passwordTxt = findViewById(R.id.password);
        emailTxt = findViewById(R.id.email);
        //register button action

        registerBtn = findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the values
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                email = emailTxt.getText().toString();
               if(cbMale.isChecked()){
                   gender = "male";
               }
               else if(cbFemale.isChecked()){
                   gender = "female";
               }
               String date_created = getDateTime().toString();
               dbManager.insert_user(username,password,email,gender,1,0,1,date_created);
//                Toast.makeText(Register.this,"Values are: "+username + ", " +password
//                       +", " + email +", " +gender,Toast.LENGTH_LONG).show();

                Toast.makeText(Register.this,"created",Toast.LENGTH_LONG).show();
                User activeUser = dbManager.getActiveUser();
                Intent intent = new Intent(Register.this, FirstPage.class);
                intent.putExtra("active_user", activeUser);
                startActivity(intent);

            }
        }); //register action ends

        //change color on the checkbox click
        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);
        cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMale.isChecked()){
                    cbMale.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    cbMale.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                }
            }
        }); //change checkbox color ends
    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        return dateFormat.format(date);

    }
}
