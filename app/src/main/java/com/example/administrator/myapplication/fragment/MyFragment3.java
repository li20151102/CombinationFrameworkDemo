package com.example.administrator.myapplication.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.broadcast.DataSynEvent;
import com.example.administrator.myapplication.entity.CommonAdapter;
import com.example.administrator.myapplication.entity.TyjUserBean;
import com.example.administrator.myapplication.entity.ViewHolder;
import com.example.administrator.myapplication.http.RetrofitUtil;
import com.example.administrator.myapplication.http.RxSchedulers;
import com.example.administrator.myapplication.refreshutil.EasyRefreshLayout;
import com.example.administrator.myapplication.refreshutil.RefreshHeaderView;
import com.example.administrator.myapplication.utils.StatusBarUtil;
import com.example.administrator.myapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/13.
 */

@SuppressLint("ValidFragment")
public class MyFragment3 extends Fragment {

    private String content;
    private RecyclerView mRecyclerView;
    private List<TyjUserBean> datas;
    private CommonAdapter<TyjUserBean> mAdapter;
    private EasyRefreshLayout easyRefreshLayout;

    public MyFragment3(String content) {
        this.content = content;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content3,container,false);
        easyRefreshLayout = (EasyRefreshLayout) view.findViewById(R.id.easylayout);
        easyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(getActivity()));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);

        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TyjUserBean bean = new TyjUserBean();
            bean.setUid("uid"+i);
            bean.setUname("uname"+i);
            bean.setSid("sid"+i);
            bean.setSname("sname"+i);
            bean.setBid("bid"+i);
            bean.setBname("bname"+i);
            datas.add(bean);
        }
        mAdapter = new CommonAdapter<TyjUserBean>(getActivity(),R.layout.user_iterm,datas) {
            @Override
            protected void convert(ViewHolder holder, TyjUserBean tyjUserBean, int position) {
                TextView uname = holder.getView(R.id.uname);
                TextView sname = holder.getView(R.id.sname);

                TextView bname = holder.getView(R.id.bname);

                uname.setText(tyjUserBean.getUname());

                sname.setText(tyjUserBean.getSname());

                bname.setText(tyjUserBean.getBname());

            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        initListener();


        return view;
    }


    private void initListener() {
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {  //上拉加载

                final List<TyjUserBean> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    TyjUserBean bean = new TyjUserBean();
                    bean.setUid("uid"+i);
                    bean.setUname("uname"+i);
                    bean.setSid("sid"+i);
                    bean.setSname("sname"+i);
                    bean.setBid("bid"+i);
                    bean.setBname("bname"+i);
                    datas.add(bean);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        easyRefreshLayout.closeLoadView();
                        int postion = mAdapter.getDatas().size();
                        mAdapter.getDatas().addAll(list);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(postion);
                    }
                }, 500);

            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() { //下拉刷新
                        datas.clear();
                        List<TyjUserBean> list = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            TyjUserBean bean = new TyjUserBean();
                            bean.setUid("uid"+i);
                            bean.setUname("uname"+i);
                            bean.setSid("sid"+i);
                            bean.setSname("sname"+i);
                            bean.setBid("bid"+i);
                            bean.setBname("bname"+i);
                            datas.add(bean);
                        }
                        mAdapter.getDatas().addAll(list);
                        easyRefreshLayout.refreshComplete();
                        Toast.makeText(getActivity(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });
    }

    @SuppressLint("CheckResult")
    private void setConfirmDetele() { //请求删除实例
        RetrofitUtil.getApiService().deleteSuppliesInfo("参数值")
                .compose(RxSchedulers.compose())
//                .compose(bindToLifecycle())
                .subscribe(baseResponse -> {
                    if (baseResponse != null) {
                        String message = baseResponse.getMessage();
                        ToastUtils.showToast(message);
                        getActivity().finish();
                    }
                }, throwable -> Logger.e("error" + throwable.getMessage()));
    }


}
