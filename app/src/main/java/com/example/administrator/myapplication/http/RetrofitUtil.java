package com.example.administrator.myapplication.http;



import com.example.administrator.myapplication.app.ApiService;
import com.example.administrator.myapplication.app.NetUtil;
import com.example.administrator.myapplication.app.config.ConfigKeys;
import com.example.administrator.myapplication.app.config.SiteControl;
import com.example.administrator.myapplication.http.fastjson.FastJsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by zcm on 2016/4/6.
 * qq:656025633
 * Func：provider a Retrofit Object
 */
public class RetrofitUtil {
    private static final long TIMEOUT = 30;

    static Cache cache = null;

    private static OkHttpClient httpClient;
    //定义共有的url
    private static ApiService apiService;

    private RetrofitUtil() {
    }

    static {
        httpClient = new OkHttpClient.Builder()
                // 添加通用的Header，设置缓存
                .addInterceptor(chain -> {
                            //缓存文件夹
                            File cacheFile = new File(SiteControl.getApplicationContext().getExternalCacheDir().toString(), "cache");
                            //缓存大小为10M
                            int cacheSize = 10 * 1024 * 1024;
                            //创建缓存对象
                            cache = new Cache(cacheFile, cacheSize);
                            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                            cacheBuilder.maxStale(365, TimeUnit.DAYS);
                            CacheControl cacheControl = cacheBuilder.build();
                            //todo 请求设置
                            Request request = chain.request();
                            if (!NetUtil.isNetworkAvailable()) {
                                request = request.newBuilder()
                                        .cacheControl(cacheControl)
                                        .addHeader("token", "123")
                                        .build();
                            }
                            //todo 接收到数据之后的设置
                            Response originalResponse = chain.proceed(request);
                            ResponseBody responseBody = originalResponse.body();
                            //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                            BufferedSource source = responseBody.source();
                            source.request(Long.MAX_VALUE); // Buffer the entire body.
                            //获得返回的数据
                            Buffer buffer = source.buffer();
                            //使用前clone()下，避免直接消耗3
                            //Logger.json(buffer.clone().readString(Charset.forName("UTF-8")));
                            if (NetUtil.isNetworkAvailable()) {
                                int maxAge = 0; // read from cache
                                return originalResponse.newBuilder()
                                        .removeHeader("Pragma")
                                        .header("Cache-Control", "public ,max-age=" + maxAge)
                                        .build();
                            } else {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                                return originalResponse.newBuilder()
                                        .removeHeader("Pragma")
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .build();
                            }
                        }
                /*
                这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
                出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
                 */)
                .addInterceptor(new CustomInterceptor(new CustomInterceptor.Logger() {
                    @Override
                    public void log(String message) {

                    }
                }).setLevel(CustomInterceptor.Level.BODY))
                //.addInterceptor(new HttpLoggingInterceptor(message -> Log.d("Interceptor:", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (RetrofitUtil.class) {
                if (apiService == null) {
                    apiService = new Retrofit
                            .Builder()
                            //不要动这里的url设置代码，如果你想改服务器地址，请到 AppApplication 的SiteControl.withBaseUrl里面修改
                            .baseUrl(((String) SiteControl.getConfiguration(ConfigKeys.BASE_URL)))
                            .client(httpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(FastJsonConverterFactory.create())
                            .build()
                            .create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    public static void resetHttp(){
        apiService = null;
    }
}
