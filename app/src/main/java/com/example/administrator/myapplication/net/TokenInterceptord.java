package com.example.administrator.myapplication.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/12/7.
 * 添加token1
 */

public class TokenInterceptord implements Interceptor {
    private String token;
    public TokenInterceptord(String token){
        this.token = token;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization","Bearer "+token)
                .build();
        return chain.proceed(newRequest);
    }
}
