package com.example.administrator.myapplication.app;

import com.example.administrator.myapplication.News;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zcm on 2016/3/31.
 * qq:656025633
 * 存放接口
 * 1.http://www.weather.com.cn/data/sk/101010100.html
 */
public interface ApiService {

    @GET("101010100.html")
    Call<News> getWeathe();

    //todo 删除物资信息
    @FormUrlEncoded
    @POST("materialsAction!deleteMaterials.action")
    Observable<BaseResponse> deleteSuppliesInfo(@Field("materials.uuid") String uuid);

}
