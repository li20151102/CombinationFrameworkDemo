package com.example.administrator.myapplication.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.utils.LogUtils;
import com.example.administrator.myapplication.utils.SystemUtils;


/**
 * Created by Administrator on 2019/1/9.
 */

public class MineAboutActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivback;
    private TextView tvTitle,tvRightSave,tv_getVersion;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_mine_about);
    }

    @Override
    public void initData() {
        ivback= (ImageView) findViewById(R.id.ivBacks);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRightSave = (TextView) findViewById(R.id.tvRightSave);
        tv_getVersion = (TextView) findViewById(R.id.tv_getVersion);
        tvTitle.setText("关于我们");
        ivback.setOnClickListener(this);
        tv_getVersion.setText("当前版本: v"+SystemUtils.getVersionName(this));
        LogUtils.e(SystemUtils.getVersionName(this)+" ");
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBacks:
                finish();
                break;
        }
    }
}
