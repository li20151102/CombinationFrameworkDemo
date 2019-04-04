package com.example.administrator.myapplication.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.MineAboutActivity;
import com.example.administrator.myapplication.activity.MineInputContentActivity;
import com.example.administrator.myapplication.broadcast.DataSynEvent;
import com.example.administrator.myapplication.citypickerview.GetJsonDataUtil;
import com.example.administrator.myapplication.citypickerview.JsonBean;
import com.example.administrator.myapplication.utils.ImageUtil;
import com.example.administrator.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Administrator on 2018/12/13.
 */

@SuppressLint("ValidFragment")
public class MyFragment4 extends Fragment implements View.OnClickListener {

    private String content;
    private ImageView ivback, ivmenu, ivHeadPic;
    private TextView tvTitle, tvName, tvSex, tvEmail, tvAddress, tvAbout, tvVersionUpdates;
    private LinearLayout llHeadPic, ll_Name, ll_sex, ll_Email,
            ll_Address, ll_About, ll_VersionUpdates;
    private Button btnExit;

    public static final int REQUESTCODE_PICK = 1;//从相册选取
    public static final int REQUESTCODE_CUTTING = 2; //结果
    public static final int REQUESTCODE_NAME = 3; //修改昵称
    public static final int REQUESTCODE_EMAIL = 4; //修改昵称
    //图片存储的路径
    private String filePath;
    private File FS;
    private String[] sexArry = new String[]{"女", "男"};// 性别选择
    private int sexWhich = 1; //1默认性别

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    //默认选择区域 浙江 杭州 西湖
    private int o1 = 10;
    private int o2 = 0;
    private int o3 = 1;

    public MyFragment4(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content4, container, false);
        EventBus.getDefault().register(this);//订阅
        ivback = view.findViewById(R.id.ivBacks);
        ivmenu = view.findViewById(R.id.ivRightMenus);
        llHeadPic = view.findViewById(R.id.ll_headPic);
        ivHeadPic = view.findViewById(R.id.ivHeadPic);
        ll_Name = view.findViewById(R.id.ll_Name);
        tvName = view.findViewById(R.id.tvName);
        ll_sex = view.findViewById(R.id.ll_sex);
        tvSex = view.findViewById(R.id.tvSex);
        ll_Email = view.findViewById(R.id.ll_Email);
        tvEmail = view.findViewById(R.id.tvEmail);
        ll_Address = view.findViewById(R.id.ll_Address);
        tvAddress = view.findViewById(R.id.tvAddress);
        ll_About = view.findViewById(R.id.ll_About);
        tvAbout = view.findViewById(R.id.tvAbout);
        ll_VersionUpdates = view.findViewById(R.id.ll_VersionUpdates);
        tvVersionUpdates = view.findViewById(R.id.tvVersionUpdates);
        btnExit = view.findViewById(R.id.btnExit);

        ivback.setVisibility(View.GONE);
        ivmenu.setVisibility(View.GONE);
        tvTitle = view.findViewById(R.id.tvTitle);

        tvTitle.setText("个人中心");
        llHeadPic.setOnClickListener(this);
        ll_Name.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        ll_Email.setOnClickListener(this);
        ll_Address.setOnClickListener(this);
        ll_About.setOnClickListener(this);
        ll_VersionUpdates.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        initJsonData();
        return getView(view);
    }


    @NonNull
    private View getView(View view) {

        setEventBuss();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_headPic:
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                break;
            case R.id.ll_Name:
                startActivityForResult(new Intent(getActivity(), MineInputContentActivity.class), REQUESTCODE_NAME);
                break;
            case R.id.ll_sex:
                showSexChooseDialog();
                break;
            case R.id.ll_Email:
                startActivityForResult(new Intent(getActivity(), MineInputContentActivity.class), REQUESTCODE_EMAIL);
                break;
            case R.id.ll_Address:
                showPickerView();
                break;
            case R.id.ll_About:
                startActivity(new Intent(getActivity(), MineAboutActivity.class));
                break;
            case R.id.ll_VersionUpdates:
                ToastUtils.showToast("当前已是最新版本!");
                break;
            case R.id.btnExit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("   确定退出?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().finish();
                            }
                        });
                builder1.show();
                break;
        }

    }

    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, sexWhich, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                sexWhich = which;
                tvSex.setText(sexArry[sexWhich]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        startPhotoZoom(uri);//裁剪
                        filePath = ImageUtil.getImagePathForOldSdk(getActivity(), uri);
//                        FS = new File(filePath);
//                        Log.i("路径：", "" + FS);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;

            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    ivHeadPic.setImageBitmap(bitmap);
//                    Log.i("路径3：", "" + FS);
//                    upLodaPicToView(FS);//上传
                }
                break;
            case REQUESTCODE_NAME:
                if (data != null) {
                    String name = data.getExtras().getString("valueName");
                    tvName.setText(name);
                }
                break;
            case REQUESTCODE_EMAIL:
                if (data != null) {
                    String name = data.getExtras().getString("valueName");
                    tvEmail.setText(name);
                }
                break;

        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG/PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                o1 = options1;
                o2 = options2;
                o3 = options3;
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"  " +
                        options2Items.get(options1).get(options2) +"  "+
                        options3Items.get(options1).get(options2).get(options3);
                tvAddress.setText(tx + "");
//                ToastUtils.showToast(getActivity(),options1+" "+options2+" "+options3+" ");
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSelectOptions(o1, o2, o3)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getActivity(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

//        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    private void setEventBuss() {
        DataSynEvent dataSynEvent = new DataSynEvent();
        dataSynEvent.setCount(66);
        EventBus.getDefault().post(dataSynEvent);//发布
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行  接收消息
    public void onDataSynEvent(DataSynEvent event) {
        Log.e("Fragment3", "event---->" + event.getCount());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
