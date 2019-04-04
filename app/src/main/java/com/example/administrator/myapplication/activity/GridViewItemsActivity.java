package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.utils.ToastUtils;

/**
 * Created by Administrator on 2019/1/11.
 */

public class GridViewItemsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivback;
    private TextView tvTitle,tvRightSave;

    @Override
    public void initUI() {

        setContentView(R.layout.activity_gridview_item);
    }

    @Override
    public void initData() {
        String keyName = getIntent().getStringExtra("key");
        ToastUtils.showToast(keyName+"");
        ivback= (ImageView) findViewById(R.id.ivBacks);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRightSave = (TextView) findViewById(R.id.tvRightSave);
        tvTitle.setText(keyName);
        ivback.setOnClickListener(this);


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
