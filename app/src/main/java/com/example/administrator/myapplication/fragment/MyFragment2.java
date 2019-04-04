package com.example.administrator.myapplication.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.broadcast.MyReceiver;
import com.example.administrator.myapplication.chartviewutils.LineChartView;
import com.example.administrator.myapplication.chartviewutils.PinChart;
import com.example.administrator.myapplication.utils.StatusBarUtil;

/**
 * Created by Administrator on 2018/12/13.
 */

@SuppressLint("ValidFragment")
public class MyFragment2 extends Fragment implements View.OnClickListener{

    private ImageView ivback;
    private TextView tvTitle;
    private String content;
    private PinChart pinChart;
    private LineChartView lineChartView;

    private MyReceiver recevier;
    private IntentFilter intentFilter;

    public MyFragment2(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2,container,false);
        ivback= (ImageView) view.findViewById(R.id.ivBacks);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(content);
        ivback.setVisibility(View.GONE);
        recevier = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //当网络发生变化的时候，系统广播会发出值为android.net.conn.CONNECTIVITY_CHANGE这样的一条广播
        getActivity().registerReceiver(recevier,intentFilter);

//        Intent intent = new Intent();
//        intent.setAction("com.example.mymessage");
        Intent intent = new Intent("com,example.mymessage");
        //也可以像注释这样写
        getActivity().sendBroadcast(intent);//发送标准广播

        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(content);

//        lineChartView=view.findViewById(R.id.lineChartView);
//        lineChartView.start(10);
        pinChart=view.findViewById(R.id.pinChartView);
        pinChart.start(10);

        recevier.onReceive(getActivity(),intent);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(recevier);
    }
}
