package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;


public class MethodHelper {

    public ArrayList<String> tasks,task_id;
    public String t_id;
    public Cursor cursor;

    // METHOD TASK DONE
    public void updateDone(DBManager dbManager, String t_id){
        dbManager.update_done(Integer.parseInt(t_id));
        User active_user = dbManager.getUser(dbManager.fetch_active_user());
        int id = Integer.parseInt(active_user.getUser_id());
        if(active_user.getLevel() ==1){
            dbManager.update_level_percent(id,50);
            active_user.setLevel_percent(active_user.getLevel_percent()+50);
            if(active_user.getLevel_percent()>= 100){
                dbManager.update_level(id,2);
                dbManager.update_level_percent(id,0);
            }
        }
        else if(active_user.getLevel() ==2){
            active_user.setLevel_percent(active_user.getLevel_percent()+30);
            if(active_user.getLevel_percent()>= 100){
                dbManager.update_level_percent(id,0);
                dbManager.update_level(id,3);
            }
            else{
                dbManager.update_level_percent(id,active_user.getLevel_percent());
            }
        }
        else{
            active_user.setLevel_percent(active_user.getLevel_percent()+20);
            if(active_user.getLevel_percent()>= 100){
                dbManager.update_level_percent(id,0);
                dbManager.update_level(id,active_user.getLevel()+1);
            }
            else{
                dbManager.update_level_percent(id,active_user.getLevel_percent());
            }
        }

    }// done ends

}
