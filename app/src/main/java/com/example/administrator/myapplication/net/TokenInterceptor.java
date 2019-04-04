package com.example.administrator.myapplication.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/12/7.
 * 添加token3
 */

public class TokenInterceptor implements Interceptor {
    private  String userToken = "";
    private String token;
    public TokenInterceptor(String token){
        this.token = token;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder().addHeader("Authorization","Bearer "+token)
                            .build();
        Response response = chain.proceed(build);
        if(isTokenExpired(response)){
            String newToken = getNewToken();
            if(TextUtils.isEmpty(newToken)){
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization","Bearer "+newToken)
                        .build();
                Log.i("11111111111>>>>>>",chain.proceed(newRequest).code()+"...");
                return chain.proceed(newRequest);
            }
        }
        Log.i("response.code+++" ,"值为："+response.code());
        return response;
    }

    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }


    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
//        SharedPreferences sharePwd = BaseApplication.instance.getSharedPreferences("loginUP", BaseApplication.instance.MODE_PRIVATE);
//            String  userNames = sharePwd.getString("username","");
//            String  passwords = sharePwd.getString("password","");
//        final Request r = new Request(Contants.IOT_URL_LOGIN);
//        r.put("name", userNames);//用户名（必填）
//        r.put("password", passwords);//密码（必填）
//        r.put("city", "杭州");//区域地址（必填）
//        r.setByteStream(new byte[0]);
//        r.addExtraHead("Content-Type","application/json");
//        r.setListener(new Listener() {
//            @Override
//            public void onResponseListener(String result) {
//                if(result!=null){
//                    try {
//                        EntityBaseDatas<EntityLoginData> data = new Gson().fromJson(result,new TypeToken<EntityBaseDatas<EntityLoginData>>(){}.getType());
//                        EntityLoginData eldata = data.getData();
//                        if(eldata!=null){
//                             userToken =  eldata.getToken();
//                            Log.i("token过期了，新请求的token为：",token);
//                        }
//                    }catch (Exception e){
//                        Log.i("eeeeeeee",e.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onErrorListener(String error) {
//                Log.i("error",error+"...");
//            }
//        });
//        NetQueue.getInstance().netRequest(r);
        return userToken;
    }
}
