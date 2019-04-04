package com.example.administrator.myapplication.http;

/**
 * code-time: 2018/8/15
 * code-author: by shdf
 * coder-wechat:
 * exp:
 **/
public abstract  class OkBaseObserver<T> extends BaseObserver<T> {
    public abstract void handleComplete();

    @Override
    public void onComplete() {
        super.onComplete();
        handleComplete();
    }
}
