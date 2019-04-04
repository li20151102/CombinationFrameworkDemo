package com.example.administrator.myapplication.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcm on 2016/4/1.
 * qq:656025633
 */
public class ActivityControlUtil {
    private static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);

    }
    public static void removeAll(){
       //Logger.d("random",activities.size());
        //
        for(Activity activity:activities){
           activity.finish();
        }
    }
}
