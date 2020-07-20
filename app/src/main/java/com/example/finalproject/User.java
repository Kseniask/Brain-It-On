package com.example.finalproject;

import android.database.Cursor;

import java.math.BigInteger;
import java.util.Date;

public class User {


    private String username;
    private String password;
    private String email;
    private String gender;
    private int level;
    private int level_percent;
    private int is_logged_in;
    private Date user_created;
    private Cursor tasks;

    public User(String uname,String pass, String email,String gender,int lev, int lev_percent,int logged_in,Date created, Cursor tasks){
        username =uname;
        password = pass;
        this.email = email;
        this.gender = gender;
        level = lev;
        level_percent = lev_percent;
        is_logged_in = logged_in;
        user_created = created;
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel_percent() {
        return level_percent;
    }

    public void setLevel_percent(int level_percent) {
        this.level_percent = level_percent;
    }

    public int getIs_logged_in() {
        return is_logged_in;
    }

    public void setIs_logged_in(int is_logged_in) {
        this.is_logged_in = is_logged_in;
    }

    public Date getUser_created() {
        return user_created;
    }

    public void setUser_created(Date user_created) {
        this.user_created = user_created;
    }

    public Cursor getTasks() {
        return tasks;
    }

    public void setTasks(Cursor tasks) {
        this.tasks = tasks;
    }

    public boolean Check_Password(String password_to_check){
        String check_md5 = md5_it(password_to_check);
        if (this.password.equals(check_md5)) {
            return true;
        }
        else{
            return false;
        }
    }

    private String md5_it(String text_to_hash){
        byte[] md5Input = password.getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, md5.encryptMD5(md5Input));
        }catch(Exception e){
            e.printStackTrace();
        }
        String md5Password = md5Data.toString();
        return md5Password;
    }

}
