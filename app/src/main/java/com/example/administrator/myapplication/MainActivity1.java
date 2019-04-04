package com.example.administrator.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity1 extends AppCompatActivity {

    private TextView tv_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);

        Log.i("onCreate()","onCreate()");
        tv_textview = (TextView) findViewById(R.id.tv_textview);
        tv_textview.setText("未更新");
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    tv_textview.setText("已更新");
                }
            }
        };
        tv_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      Message message = Message.obtain();
                        message.what=1;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart()","onStart()");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart()","onRestart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume()","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop()","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy()","onDestroy()");
    }

}
