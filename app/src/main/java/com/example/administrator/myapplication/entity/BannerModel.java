package com.example.administrator.myapplication.entity;

/**
 * Created by xiwen on 2019/1/16.
 */
public class BannerModel {

    private String imageUrl;
    private String mTips;
    private int img;

    public String getTips() {
        return mTips;
    }

    public void setTips(String tips) {
        mTips = tips;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
