package com.example.administrator.myapplication.app;

import android.app.Application;
import android.content.Context;

import com.example.administrator.myapplication.app.config.SiteControl;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
//import com.orhanobut.logger.AndroidLogAdapter;
//import com.orhanobut.logger.Logger;


/**
 * Created by kyle
 * wechat：
 * exp：
 **/

public class AppApplication extends Application {
    static Context mAppApplication ;
    static Context activity;


    public static int SIGN_TIME = 0;
    public static String CLOCK_ID = "1";

//    public static SurveyInfo surveyInfo;
//    public static SurveyData surveyData;

//    public static SurveyInfo getSurveyInfo() {
//        return surveyInfo;
//    }
//
//    public static void setSurveyInfo(SurveyInfo surveyInfo) {
//        AppApplication.surveyInfo = surveyInfo;
//    }
//
//    public static SurveyData getSurveyData() {
//        return surveyData;
//    }
//
//    public static void setSurveyData(SurveyData surveyData) {
//        AppApplication.surveyData = surveyData;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
        //初始化stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        SpiderMan.getInstance()
                .init(this)
                .setEnable(true)
                .showCrashMessage(true)
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
            @Override
            public void onCrash(Thread t, Throwable ex, CrashModel model) {
                //todo  log 异常信息
            }
        });

        SiteControl.init(this)
                .withDefaultDir("SiteControl")
                .withDefaultPicDir("pic")
                .withLogger(true)
                .withBaseUrl("url")
                .withActivity(null)
                .configure();


    }
    public static Context getContext() {
        return mAppApplication;
    }
//    public static Context getmActivity(){
//        return ActivityControlUtil.getLastActivity();
//    }

    public static void setActivity(Context context) {
        activity = context;
    }

}
