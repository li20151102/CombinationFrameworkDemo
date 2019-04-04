package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.administrator.myapplication.http.RetrofitUtil;
import com.example.administrator.myapplication.net.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Administrator on 2018/12/7.
 */

public class RetrofitActivity extends Activity {
    private static final String TAG= "RetrofitAct";
    ProgressDialog pd;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                Log.e(TAG,"1111");
                pd.dismiss();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_activity);
        Button buttons = (Button) findViewById(R.id.button);
        Button buttons1 = (Button) findViewById(R.id.buttons);

        initThread();

        buttons.setOnClickListener(View ->initView());

        buttons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initViewss();
                initViewss22();
            }
        });

    }

    private void initThread() {
        pd = new ProgressDialog(this);
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在刷新");
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    handler.sendEmptyMessage(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initViewss22() {
        RetrofitUtil.getApiService().getWeathe().enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News dd = response.body();
                News.WeatherinfoBean weainfo = dd.getWeatherinfo();
                weainfo.getCity();
                Log.e(TAG,dd.getWeatherinfo().toString());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG,"网络异常，1请检查网络...");
            }
        });
    }

    private void initViewss() {
        Retrofit retrofit = new Retrofit.Builder()
                //使用自定义的mGsonConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.weather.com.cn/data/sk/")
                .build();
        retrofit.create(APi.class).getDatas().enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News dd = response.body();
                News.WeatherinfoBean weainfo = dd.getWeatherinfo();
                weainfo.getCity();
                Log.e(TAG,dd.getWeatherinfo().toString());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG,t.getMessage().toString());
            }
        });
    }

    private void initView() {

        NetUtil.requestDatas().getDatas()
                .enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News dd = response.body();
                News.WeatherinfoBean weainfo = dd.getWeatherinfo();
                weainfo.getCity();
                Log.e(TAG,dd.getWeatherinfo().toString());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG,"网络异常，请检查网络...");
            }
        });


//        Retrofit retrofit = new Retrofit.Builder()
//                //使用自定义的mGsonConverterFactory
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://218.203.172.125:8080/GSPdwV2.0.8/")
//                .build();
//        APi mApi = retrofit.create(APi.class);
//        Call<News> news = mApi.getNews("27031552", "password1");
//        Log.e(TAG,"dddd");
//        news.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                News dd = response.body();
//                Log.e(TAG,dd.toString());
//                dd.toString();
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
//
//                Log.e(TAG,t.getMessage());
//            }
//        });

    }
}
