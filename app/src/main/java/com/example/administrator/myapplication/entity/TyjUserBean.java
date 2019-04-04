package com.example.administrator.myapplication.entity;

import java.io.Serializable;

/**
 * Created by shdf on 2018/6/28.
 * wechat：zcm656025633
 * exp：
 **/
public class TyjUserBean implements Serializable {
    //uid："",uname:"",sid:"",sname:"",bid:"",bname:""
    private String uid;
    private String uname;
    private String sid;
    private String sname;
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    private String bname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
