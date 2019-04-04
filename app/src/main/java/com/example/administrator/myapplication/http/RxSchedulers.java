package com.example.administrator.myapplication.http;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myapplication.app.AppApplication;
import com.example.administrator.myapplication.app.NetUtil;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shdf on 2018/5/31.
 * wechat：
 * exp：
 **/
public class RxSchedulers {
    private static final String TAG = "RxSchedulers";

    public static <T> ObservableTransformer<T, T> compose() {

        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    Log.e(TAG, "doOnSubscribe: ");
                    if (!NetUtil.isNetworkAvailable()) {
                        Toast.makeText(AppApplication.getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //todo 显示dialog
                     //  ViewUtil.getDialogInstance(AppApplication.getmActivity()).show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
                //.doOnTerminate(() ->ViewUtil.getDialogInstance(AppApplication.getmActivity()).dismiss());
    }
}
