package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {
CheckBox cbMale,cbFemale;
EditText usernameTxt,passwordTxt,emailTxt;
Button registerBtn;
String username,password,gender,email;
DBManager dbManager;
int changed;

    User active_user;
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
        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);
        registerBtn = findViewById(R.id.btnRegister);


        final Intent intent = getIntent();
        if(intent.getStringExtra("active_user") != null && intent.getStringExtra("active_user").equalsIgnoreCase("edit")){
            active_user = dbManager.getUser(dbManager.fetch_active_user());
            usernameTxt.setText(active_user.getUsername());
            emailTxt.setText(active_user.getEmail());
            if(active_user.getGender().equalsIgnoreCase("female"))
            {
                cbFemale.setChecked(true);
                cbMale.setChecked(false);
            }
            else{
                cbFemale.setChecked(false);
                cbMale.setChecked(true);
            }
            registerBtn.setText("UPDATE");
        }

        //register button action

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(usernameTxt.getText().toString().matches("") ||passwordTxt.getText().toString().matches("") ||emailTxt.getText().toString().matches("")){
                        if(!cbMale.isChecked() && !cbFemale.isChecked()) {
                            Toast.makeText(Register.this, "Fill all blanks, please", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //set the values
                        username = usernameTxt.getText().toString();
                        //if there is something in the password field - md5 it and set as password
                        if (!passwordTxt.getText().toString().matches("")) {
                            password = passwordTxt.getText().toString();
                            changed = 1;
                        }
                        //if nothing, use old password
                        else {
                            changed = 0;
                            System.out.println("OLD PASSWORD: " + active_user.getPassword());
                            password = active_user.getPassword();
                        }

                        email = emailTxt.getText().toString();
                        if (cbMale.isChecked()) {
                            gender = "male";
                        } else if (cbFemale.isChecked()) {
                            gender = "female";
                        }
                        String date_created = getDateTime();
                        if (intent.getStringExtra("active_user") != null) {

                            dbManager.update_users(Integer.parseInt(active_user.getUser_id()), username, password, email, gender, changed);
                            startActivity(new Intent(Register.this, Account.class));
                        } else {
                            dbManager.insert_user(username, password, email, gender, 1, 0, 1, date_created);


                            Toast.makeText(Register.this, "created", Toast.LENGTH_LONG).show();
                            User activeUser = dbManager.getUser(dbManager.fetch_active_user());
                            Intent intent = new Intent(Register.this, FirstPage.class);
                            intent.putExtra("active_user", activeUser);
                            startActivity(intent);
                        }
                    }
                }
            }); //register action ends

        //change color on the checkbox click

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);

    }
}
