package com.example.administrator.myapplication.app.config;

/**
 * @author
 *         created by on 2018/1/23 0023. 09:34
 **/

public enum ConfigKeys {
    /**
     * 这个是所有的上传和正常请求的url
     */
    API_HOST,
    APPLICATION_CONTEXT,
    CONFIG_READY,
    /**
     * 是否打印log,false就是不打印
     */
    IS_LOGGER,
    /**
     * true请求接口的时候会打印接口请求的interceptor
     */
    IS_DEBUG,
    /**
     * 存activity
     */
    ACTIVITY,
    /**
     * 整个app存放所有文件的默认目录
     */
    DEFAULT_DIR,
    /**
     * 整个app存放照片的默认目录，在DEFAULT_DIR 的下一级
     */
    DEFAULT_PIC_DIR,
    /**
     * 存放登录人信息的
     */
    ACCOUNT,
    /**
     * 请求地址的baseUrl
     */
    BASE_URL
}
