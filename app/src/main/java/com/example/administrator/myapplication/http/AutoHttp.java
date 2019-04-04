package com.example.administrator.myapplication.http;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shdf on 2018/6/5.
 * wechat：zcm656025633
 * exp：
 **/
public abstract class AutoHttp<T> {
     public AutoHttp(Observable obs){
         obs.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new BaseObserver<T>() {
                     @Override
                     protected void onHandleSuccess(T userBean) {
                         handleSuccess(userBean);
                     }

                     @Override
                     protected void onHandleError(String msg) {

                     }
                 });
    }
    public abstract void handleSuccess(T t);
}
