package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.util.Date;

public class DBManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

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

    public void insert_task(String title, String description, Date created_at, Date due_date, int owner_id){
        String created_at_str = created_at.toString();
        String due_date_str = due_date.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.CREATED_AT, created_at_str);
        contentValues.put(DatabaseHelper.DUE_DATE, due_date_str);
        contentValues.put(DatabaseHelper.OWNER_ID, owner_id);

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
        String[] columns = new String[] { DatabaseHelper.TASK_ID, DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION,DatabaseHelper.DUE_DATE,DatabaseHelper.CREATED_AT,DatabaseHelper.OWNER_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_TASKS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_due_date_order(){
        String[] columns = new String[] { DatabaseHelper.TASK_ID, DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION,DatabaseHelper.DUE_DATE,DatabaseHelper.CREATED_AT,DatabaseHelper.OWNER_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_TASKS, columns, null, null, null, null, DatabaseHelper.DUE_DATE);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
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

    public int update_tasks(long task_id, String title, String description, Date created_at, Date due_date, int owner_id) {
        String created_at_str = created_at.toString();
        String due_date_str = due_date.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.CREATED_AT, created_at_str);
        contentValues.put(DatabaseHelper.DUE_DATE, due_date_str);
        contentValues.put(DatabaseHelper.OWNER_ID, owner_id);

        int i = database.update(DatabaseHelper.TABLE_NAME_TASKS, contentValues, DatabaseHelper.TASK_ID + " = " + task_id, null);
        return i;
    }

    public void delete_user(long user_id) {
        database.delete(DatabaseHelper.TABLE_NAME_USERS, DatabaseHelper.USER_ID + "=" + user_id, null);
    }

    public void delete_task(long task_id) {
        database.delete(DatabaseHelper.TABLE_NAME_TASKS, DatabaseHelper.TASK_ID + "=" + task_id, null);
    }
}
