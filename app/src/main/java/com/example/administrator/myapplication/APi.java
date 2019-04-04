package com.example.administrator.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/12/7.
 */

public interface APi {

//    @Headers("apikey:81bf9da930c7f9825a3c3383f1d8d766")
    @POST("Login!login.action")
    Call<News> getNews(@Field("username") String num, @Field("password") String page);

    @GET("101010100.html")
    Call<News> getDatas();

    @POST("weather")
    Call<News> getWeathDatas(@Field("cityid") String ciid,@Field("key") String key);
}

