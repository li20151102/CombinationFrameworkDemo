package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;

/**
 * Created by Administrator on 2019/1/7.
 */

public class MineInputContentActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivback;
    private TextView tvTitle,tvRightSave;
    private EditText etContext;

    @Override
    public void initUI() {

        setContentView(R.layout.activity_mine_inputcontext);
    }

    @Override
    public void initData() {
        ivback= (ImageView) findViewById(R.id.ivBacks);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRightSave = (TextView) findViewById(R.id.tvRightSave);
        etContext = (EditText) findViewById(R.id.et_mineinput_context);
        tvTitle.setText("更改昵称");
        tvRightSave.setVisibility(View.VISIBLE);
        ivback.setOnClickListener(this);
        tvRightSave.setOnClickListener(this);


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
            case R.id.tvRightSave:
                String name = etContext.getText().toString();
                if(!TextUtils.isEmpty(name)){
                    Intent chuanzhi = new Intent();
                    chuanzhi.putExtra("valueName",name);
                    setResult(RESULT_OK, chuanzhi);
//                    Toast.makeText(this,"fdsfsdf"+name,Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"内容不能为空!",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
