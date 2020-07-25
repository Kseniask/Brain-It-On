package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateUpdate extends AppCompatActivity{
EditText title,description;
Button btnDate,save,cancel;
Calendar c,due_date_cal;
DateFormat fmtDate;
String titleTxt,descriptionTxt;
String dueDate, dateCreated;
Date due_date_day;
DatePickerDialog.OnDateSetListener d;
DBManager dbManager;
Cursor cursor, cursor_task;
int owner_id;
User activeUser;
String task_id_to_edit;
SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);
        MenuButton();

        dbManager = new DBManager(this);
        dbManager.open();



        description = findViewById(R.id.description);
        title = findViewById(R.id.title);
        dateFormat =new SimpleDateFormat("yyyy-MM-dd");

        c= Calendar.getInstance();

        //if intent has smth in it,fill the gaps with info on task
        final Intent intent = getIntent();

        if(intent.getStringExtra("task_to_update") != null){

            task_id_to_edit = intent.getStringExtra("task_to_update");
            cursor_task = dbManager.get_task_by_id(task_id_to_edit);
            description.setText(cursor_task.getString(cursor_task.getColumnIndex("description")));
            title.setText(cursor_task.getString(cursor_task.getColumnIndex("title")));

            try {
                due_date_day = dateFormat.parse(cursor_task.getString(cursor_task.getColumnIndex("due_date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            System.out.println("YEAR: "+due_date_day.getYear()+ " MONTH " +(due_date_day.getMonth()+1)+ " " + "\nDATE: " + dueDate.due_date_day());
            c.setTime(due_date_day);
        }


        fmtDate = DateFormat.getDateInstance();
        d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        activeUser = dbManager.getActiveUser();
        btnDate = findViewById(R.id.btnDueDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateUpdate.this,d,c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        save = findViewById(R.id.btnSaveTask);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                titleTxt=title.getText().toString();

                descriptionTxt = description.getText().toString();
                if(titleTxt.isEmpty() || descriptionTxt.isEmpty()){
                    Toast.makeText(CreateUpdate.this,"Please fill in all blanks", Toast.LENGTH_LONG).show();
                }
                else{
                    cursor = dbManager.fetch_active_user();
                    dateCreated = getDateTime();
                    dueDate = dateFormat.format(c.getTime());
                    owner_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                    if(intent.getStringExtra("task_to_update") !=null){

                        dateCreated = cursor_task.getString(cursor_task.getColumnIndex("created_at"));
                        dbManager.update_tasks(Integer.parseInt(task_id_to_edit),titleTxt,descriptionTxt,dueDate,dateCreated,owner_id,0);
                    }
                    else {
                        dbManager.insert_task(titleTxt, descriptionTxt, dueDate, dateCreated, owner_id, 0);
                    }
                    Toast.makeText(CreateUpdate.this, "Task added", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent( CreateUpdate.this, FirstPage.class);
                    intent.putExtra("active_user", activeUser);
                    startActivity(intent);
                }
            }
        });

        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void MenuButton(){
        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUpdate.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}
