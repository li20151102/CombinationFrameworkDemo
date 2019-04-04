package com.example.administrator.myapplication.app;

/**
 * Created by ${shdf} on 17/4/12.
 * wechat：zcm656025633
 * exp：后台返回数据的结构体
 **/

public class BaseResponse<T> {

    private String message;
    private String result;
    private T data;

    //todo 代表请求成功
    public boolean isSuccess(){
        return "true".equals(result);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
