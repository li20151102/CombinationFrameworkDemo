package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/12/7.
 */

public class MainActivity3 extends Activity implements View.OnClickListener {
    private LinearLayout ll1, ll2, ll3;    //套住文本标签的布局

  private TextView tv1, tv2, tv3;     //文本标签

  //静态常亮随着程序的关闭而消失，如果要永久的记住颜色可以存储在本地

   public static String seller_id = "1", available = "0",canorder="0";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initView();
    }
    private void initView(){
        ll1 = (LinearLayout) findViewById(R.id.ll1);

    ll1.setOnClickListener(this);

     tv1 = (TextView) findViewById(R.id.tv1);



      ll2 = (LinearLayout) findViewById(R.id.ll2);

    ll2.setOnClickListener(this);

    tv2 = (TextView) findViewById(R.id.tv2);



     ll3 = (LinearLayout) findViewById(R.id.ll3);

     ll3.setOnClickListener(this);

     tv3 = (TextView) findViewById(R.id.tv3);

        //设置颜色

     if (seller_id.equals("1") ) {

      ll1.setBackgroundResource(R.drawable.shape_cart3);

     } else {

         ll2.setBackgroundResource(R.drawable.shape_cart);
         ll3.setBackgroundResource(R.drawable.shape_cart);
     }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

     case R.id.ll1:

       if (seller_id.equals("1")) {

          ll1.setBackgroundResource(R.drawable.shape_cart3);


          seller_id = "0";

       } else {

           ll2.setBackgroundResource(R.drawable.shape_cart);
           ll3.setBackgroundResource(R.drawable.shape_cart);


           seller_id = "1";

       }
        break;
            case R.id.ll2:

      if (available.equals("1") ) {

          ll2.setBackgroundResource(R.drawable.shape_cart3);


           available = "0";

       } else {

          ll1.setBackgroundResource(R.drawable.shape_cart);
          ll3.setBackgroundResource(R.drawable.shape_cart);


           available = "1";

      }
        break;
            case R.id.ll3:

        if (canorder.equals("1") ) {

           ll3.setBackgroundResource(R.drawable.shape_cart3);

            canorder = "0";

        } else {

           ll1.setBackgroundResource(R.drawable.shape_cart);
           ll2.setBackgroundResource(R.drawable.shape_cart);

          canorder = "1";

        }

        break;
        }
    }
}
