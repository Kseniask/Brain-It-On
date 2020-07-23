package com.example.finalproject;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;   // Key class 1
import android.database.sqlite.SQLiteOpenHelper; // Key class 2

public class DatabaseHelper extends SQLiteOpenHelper {



    // Database Information
    static final String DB_NAME = "FinalProject.DB";
    static final String TABLE_NAME_USERS = "Users";
    static final String TABLE_NAME_TASKS = "Tasks";

    static final String USER_ID = "user_id";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String EMAIL = "email";
    static final String GENDER = "gender";
    static final String LEVEL = "level";
    static final String LEVEL_PERCENT = "level_percent";
    static final String IS_LOGGED_IN = "is_logged_in";
    static final String USER_CREATED = "user_created";

    static final String TASK_ID = "task_id";
    static final String TITLE= "title";
    static final String DESCRIPTION = "description";
    static final String CREATED_AT= "created_at";
    static final String DUE_DATE = "due_date";
    static final String OWNER_ID = "owner_id";
    static final String DONE = "done";
    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_USER = "create table "+TABLE_NAME_USERS+" ("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERNAME +" TEXT NOT NULL, "
            +PASSWORD+" TEXT NOT NULL, " + EMAIL +" TEXT NOT NULL, "+GENDER +" TEXT NOT NULL, "+ LEVEL +" INTEGER NOT NULL, "+ LEVEL_PERCENT +" INTEGER not NULL, " + IS_LOGGED_IN +" INTEGER NOT NULL, "+USER_CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL)";
    private static final String CREATE_TABLE_TASK = "create table "+TABLE_NAME_TASKS+" ("+ TASK_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TITLE+" TEXT NOT NULL, "+ DESCRIPTION +" TEXT NOT NULL," +
            DUE_DATE+ " DATETIME NOT NULL, "+CREATED_AT+" DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "+ OWNER_ID +" TEXT NOT NULL, " + DONE+" INTEGER NOT NULL, FOREIGN KEY("+OWNER_ID+") REFERENCES "+TABLE_NAME_USERS+"("+USER_ID+"))";
    // Create database
    public DatabaseHelper(Context context) {
        // factory is for creating cursor objects, or null for the default factory
        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TASKS);
        onCreate(db);
    }
}