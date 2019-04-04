package com.example.administrator.myapplication.http;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myapplication.app.config.SiteControl;
import com.google.gson.JsonParseException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by kyle on 2019/1/23.
 * wechat：
 * exp：
 * 1。 封装一个加载动画
 * 2。 生命周期的管理
 **/
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;
    private Dialog dialog;
    private String error;

    protected BaseObserver() {
        this.mContext = SiteControl.getApplicationContext();
    }

    /**
     * 如果需要显示dialog，在此处传入
     *
     * @param dialog
     */
    protected BaseObserver(Dialog dialog) {
        this.mContext = SiteControl.getApplicationContext();
        this.dialog = dialog;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    //todo 正常请求到数据之后的问题
    @Override
    public void onNext(BaseResponse<T> value) {
        if (dialog != null)
            dialog.dismiss();
        if (value.isSuccess()) {
            T t = value.getData();
            if (t instanceof String || t == null) {
                if (!TextUtils.isEmpty((value.getMessage()))) {
                    onHandleError(value.getMessage());
                }
            }
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMessage());
        }
    }

    //todo 在请求的过程中出现问题
    @Override
    public void onError(Throwable e) {
        if (dialog != null)
            dialog.dismiss();
        //todo 分类集中常见的异常
        if (e instanceof SocketTimeoutException) {
            error = "网络连接超时";
        } else if (e instanceof ConnectException) {
            error = "网络断开";
        } else if (e instanceof UnknownHostException) {
            error = "主机名错误";
        } else if (e instanceof ClassCastException) {
            error = "类转换错误";
        } else if (e instanceof JsonParseException) {
            error = "数据解析错误";
        } else {
            error = "未知错误";
        }
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
        //todo 隐藏进度条
        onHandleError(null != e ? e.getMessage() : "未知错误");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        //todo 隐藏进度条
    }


    protected abstract void onHandleSuccess(T t);

    //todo 服务器请求到数据的code不正确
    protected abstract void onHandleError(String msg);
}
