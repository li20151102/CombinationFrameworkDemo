package com.example.administrator.myapplication.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.GridViewItemsActivity;
import com.example.administrator.myapplication.entity.BannerModel;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/13.
 */

@SuppressLint("ValidFragment")
public class MyFragment1 extends Fragment implements AdapterView.OnItemClickListener {

    private String content;
    private Banner mBanner;

    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.sbgz, R.mipmap.tgps, R.mipmap.sbss,
            R.mipmap.sbgj, R.mipmap.sbbx, R.mipmap.sbgd,
            R.mipmap.wdgd, R.mipmap.sbdb, R.mipmap.sbls};
    private String[] iconName = {"故障", "评审", "设施", "告警", "报修", "新派", "工单",
            "代办", "历史"};
    View view;
    private List<BannerModel> mDatas = new ArrayList<>();
    public MyFragment1(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_content1, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(content);

        initViewBanner(view);//banner图片设置
        initGridView(view); //九宫格设置


        return view;
    }

    private void initViewBanner(View view) {
        //可以在布局里面写
        mBanner = (Banner) view.findViewById(R.id.id_banner);

        BannerAdapter adapter = new BannerAdapter<BannerModel>(mDatas) {
            @Override
            protected void bindTips(TextView tv, BannerModel bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, BannerModel bannerModel) {
                Glide.with(getActivity())
                        .load(bannerModel.getImageUrl())
                        .into(imageView);
            }

        };
        mBanner.setBannerAdapter(adapter);
        mBanner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(getActivity(), "position:" + position , Toast.LENGTH_SHORT).show();
            }
        });
        gotData();
//        //设置图片url和图片标题
//        mBanner.initBanner(getImageUrlData(), getContentData());
//        //设置图片加载器
//        mBanner.setImageLoader(new EasyBanner.ImageLoader() {
//            @Override
//            public void loadImage(ImageView imageView, String url) {
//                Glide.with(getActivity()).load(url)
//                        .into(imageView);
//            }
//        });
//        mBanner.start();//启动轮播
//        //监听banner的item点击事件
//        mBanner.setOnItemClickListener(new EasyBanner.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, String title) {
//                Toast.makeText(getActivity(), "position:" + position + "   title:" + title, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initGridView(View view) {
        gview = (GridView) view.findViewById(R.id.gview);
        gview.setOnItemClickListener(this);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.gridview_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
//        gview.setSelector(new ColorDrawable(Color.TRANSPARENT));// 去掉默认点击背景
    }

    public List<Map<String, Object>> getData() { //九宫格
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //九宫格

        String dd=iconName[position];
//        Toast.makeText(getActivity(), "position:" +dd + "  ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), GridViewItemsActivity.class);
        intent.putExtra("key",dd);
        startActivity(intent);

//        DataSynEvent dataSynEvent = new DataSynEvent();
//        dataSynEvent.getCount();
//        if(dataSynEvent.getCount()!=0){
//
//
//            Toast.makeText(getActivity(), "position:" + dataSynEvent.getCount() + 1 + "  ", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getActivity(), "position:" + position + 1 + "   " , Toast.LENGTH_SHORT).show();
//
//        }
    }

//    private List<String> getImageUrlData() {//banner图
//        List<String> imageList = new ArrayList<>();
//        imageList.add("http://img.027cgb.com/613085/tu1.png");
//        imageList.add("http://img.027cgb.com/613085/tu2.png");
//        imageList.add("http://img.027cgb.com/613085/tu3.png");
//        imageList.add("http://img.027cgb.com/613085/tu4.png");
//        imageList.add("http://img.027cgb.com/613085/tu5.png");
//        return imageList;
//    }
//
//    private List<String> getContentData() { //banner图片上的文字
//        List<String> contentList = new ArrayList<>();
//        contentList.add("");
//        contentList.add("");
//        contentList.add("");
//        contentList.add("");
//        contentList.add("");
//        return contentList;
//    }

    //得到的图片资源
    private void gotData() {
        mDatas.clear();
        BannerModel model = new BannerModel();
//        model.setImg(R.mipmap.ic_launcher);
        model.setImageUrl("http://img.027cgb.com/613085/dw_home_bg.jpg");
//        model.setTips("这是页面1");
        mDatas.add(model);
        BannerModel model2 = new BannerModel();
//        model2.setImg(R.mipmap.ic_launcher);
        model2.setImageUrl("http://img.027cgb.com/613085/dw_home_bg1.jpg");
//        model.setTips("这是页面2");
        mDatas.add(model2);
        mBanner.notifyDataHasChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        gotData();
//        getImageUrlData();
    }
}
