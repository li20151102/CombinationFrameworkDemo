package com.example.administrator.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<View> viewList;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewList= new ArrayList<View>();
        pager = (ViewPager) findViewById(R.id.pager);

        View view1 = View.inflate(this,R.layout.view1,null);
        View view2 = View.inflate(this,R.layout.view2,null);
        View view3 = View.inflate(this,R.layout.view3,null);
        View view4 = View.inflate(this,R.layout.view4,null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        //创建视频日期
        MyPageAdapter adapter = new MyPageAdapter(viewList);

        pager.setAdapter(adapter);


    }


}
