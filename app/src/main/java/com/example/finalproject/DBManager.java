package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    String[] columns = {"task_id", "title","description","due_date","created_at","owner_id","done"};
    String[] columns_user = {"user_id", "username","password","email","gender","level","level_percent","is_logged_in","user_created"};

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws android.database.SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert_user(String username, String password, String email,
                            String gender, int level,int level_percent,int is_logged_in, String user_created) {
        // put data in contentvalues first
        byte[] md5Input = password.getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, md5.encryptMD5(md5Input));
        }catch(Exception e){
            e.printStackTrace();
        }
        String md5Password = md5Data.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERNAME, username);
        contentValues.put(DatabaseHelper.PASSWORD, md5Password);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.GENDER, gender);
        contentValues.put(DatabaseHelper.LEVEL, level);
        contentValues.put(DatabaseHelper.LEVEL_PERCENT, level_percent);
        contentValues.put(DatabaseHelper.IS_LOGGED_IN, is_logged_in);
        contentValues.put(DatabaseHelper.USER_CREATED, user_created);

        database.insert(DatabaseHelper.TABLE_NAME_USERS, null, contentValues);
    }

    public void insert_task(String title, String description, Date due_date,Date created_at,  int owner_id,int done){
        String created_at_str = created_at.toString();
        String due_date_str = due_date.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.CREATED_AT, created_at_str);
        contentValues.put(DatabaseHelper.DUE_DATE, due_date_str);
        contentValues.put(DatabaseHelper.OWNER_ID, owner_id);
        contentValues.put(DatabaseHelper.DONE, done);


        database.insert(DatabaseHelper.TABLE_NAME_TASKS, null,contentValues);

    }

    // A Cursor represents the entire result set of the query.
    // Once the query is fetched, a call to cursor.moveToFirst() is made.
    // Calling moveToFirst() does two things:
    // 1). It allows us to test whether the query returned an empty set (by testing the return value)
    // 2). It moves the cursor to the first result (when the set is not empty)
    public Cursor fetch_users() {
        String[] columns = new String[] { DatabaseHelper.USER_ID, DatabaseHelper.USERNAME, DatabaseHelper.PASSWORD,DatabaseHelper.EMAIL,DatabaseHelper.GENDER,DatabaseHelper.LEVEL,DatabaseHelper.LEVEL_PERCENT, DatabaseHelper.IS_LOGGED_IN, DatabaseHelper.USER_CREATED};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_USERS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch_tasks() {
        String[] columns = new String[] { DatabaseHelper.TASK_ID, DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION,DatabaseHelper.DUE_DATE,DatabaseHelper.CREATED_AT,DatabaseHelper.OWNER_ID, DatabaseHelper.DONE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_TASKS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_due_date_order(String id){

        String[] columns = new String[] { DatabaseHelper.TASK_ID, DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION,DatabaseHelper.DUE_DATE,DatabaseHelper.CREATED_AT,DatabaseHelper.OWNER_ID, DatabaseHelper.DONE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_TASKS, columns, DatabaseHelper.OWNER_ID +"='"+id+"'", null, null, null, DatabaseHelper.DUE_DATE);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update_login(long user_id, int login){
        String sql_str = "UPDATE users SET is_logged_in = "+login+" where user_id = " +user_id;
        database.execSQL(sql_str);
    }

    public int update_users(long user_id, String username, String password, String email, String gender, int level,int level_percent, int is_logged_in, String user_created) {
        byte[] md5Input = password.getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, md5.encryptMD5(md5Input));
        }catch(Exception e){
            e.printStackTrace();
        }
        String md5Password = md5Data.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERNAME, username);
        contentValues.put(DatabaseHelper.PASSWORD, md5Password);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.GENDER, gender);
        contentValues.put(DatabaseHelper.LEVEL, level);
        contentValues.put(DatabaseHelper.LEVEL_PERCENT, level_percent);
        contentValues.put(DatabaseHelper.IS_LOGGED_IN, is_logged_in);
        contentValues.put(DatabaseHelper.USER_CREATED,user_created);

        int i = database.update(DatabaseHelper.TABLE_NAME_USERS, contentValues, DatabaseHelper.USER_ID + " = " + user_id, null);
        return i;
    }

    public int update_tasks(int task_id, String title, String description, Date due_date, Date created_at, int owner_id, int done) {
        String created_at_str = created_at.toString();
        String due_date_str = due_date.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.CREATED_AT, created_at_str);
        contentValues.put(DatabaseHelper.DUE_DATE, due_date_str);
        contentValues.put(DatabaseHelper.OWNER_ID, owner_id);
        contentValues.put(DatabaseHelper.DONE, done);


        int i = database.update(DatabaseHelper.TABLE_NAME_TASKS, contentValues, DatabaseHelper.TASK_ID + " = " + task_id, null);
        return i;
    }

    public Cursor fetch_user_tasks(String id){
        String select_tasks = "owner_id = \""+ id+"\"";
        Cursor cursor = database.query("tasks", columns,
                select_tasks, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_user_by_username(String username){
        String select_user = " username = '"+ username +"'";
        Cursor cursor = database.query("users", columns_user,select_user,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor get_task_by_id(String id){
        String select_tasks = "task_id = \""+ id+"\"";
        Cursor cursor = database.query("tasks", columns,
                select_tasks, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_active_user(){
        String select_active = "is_logged_in = 1";
        Cursor cursor = database.query("users", columns_user,
                select_active, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        System.out.println(cursor.getCount());
        return cursor;
    }


    public User getActiveUser(){
        Cursor cursor =  this.fetch_active_user();
        if(cursor.getCount() !=0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_str = cursor.getString(cursor.getColumnIndex("user_created"));
            Date dateCreated = new Date();
            try {
                dateFormat.parse(date_str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String id = cursor.getString(cursor.getColumnIndex("user_id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            int level_percent = cursor.getInt(cursor.getColumnIndex("level_percent"));
            int is_logged_in = cursor.getInt(cursor.getColumnIndex("is_logged_in"));
            String dateCreated_str = cursor.getString(cursor.getColumnIndex("user_created"));
            System.out.println("Day created " + dateCreated_str);

            User activeUser = new User(id, username, password, email, gender, level, level_percent, is_logged_in, dateCreated, null);
            cursor.close();
            return activeUser;
        }
        else{
            return null;
        }
    }

    public User getUser(Cursor cursor){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_str = cursor.getString(cursor.getColumnIndex("user_created"));
        Date dateCreated =new Date();
        try{
            dateFormat.parse(date_str);
        }
        catch(ParseException e){
            e.printStackTrace();
        }

        String id = cursor.getString(cursor.getColumnIndex("user_id"));
        String username = cursor.getString(cursor.getColumnIndex("username"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String gender = cursor.getString(cursor.getColumnIndex("gender"));
        int level =cursor.getInt(cursor.getColumnIndex("level"));
        int level_percent =cursor.getInt(cursor.getColumnIndex("level_percent"));
        int is_logged_in =cursor.getInt(cursor.getColumnIndex("is_logged_in"));
        String dateCreated_str = cursor.getString(cursor.getColumnIndex("user_created"));
        System.out.println("Day created " + dateCreated_str);

        User activeUser = new User(id,username,password,email,gender,level,level_percent,is_logged_in,dateCreated,null);
        cursor.close();
        return activeUser;
    }
    public void delete_user(long user_id) {
        database.delete(DatabaseHelper.TABLE_NAME_USERS, DatabaseHelper.USER_ID + "=" + user_id, null);
    }

    public void delete_task(long task_id) {
        database.delete(DatabaseHelper.TABLE_NAME_TASKS, DatabaseHelper.TASK_ID + "=" + task_id, null);
    }
}
