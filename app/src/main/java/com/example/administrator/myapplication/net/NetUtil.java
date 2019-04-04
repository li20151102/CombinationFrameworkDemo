package com.example.administrator.myapplication.net;


import com.example.administrator.myapplication.APi;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/21.
 */

public class NetUtil {

    public  static APi requestDatas(){  //无token


        APi retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/data/sk/") //Contants.BASE_IOT_URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APi.class);

        return retrofit;
    }

    public  static Retrofit requestData(String token){
        Interceptor interceptor = new TokenInterceptord(token);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("") //Contants.BASE_IOT_URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }



    public  static Retrofit requestOtherData(String token){
        Interceptor interceptor = new TokenAndContentTypeIntercepter(token);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("") //Contants.BASE_IOT_URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit requestReturnNewTOken(String token){
        Interceptor interceptor = new TokenInterceptor(token);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("") //Contants.BASE_IOT_URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
