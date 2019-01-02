package ceshi.handover.scinan.com.huishoubaobigrecycling.api.net;











import java.util.concurrent.TimeUnit;

import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIService;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitUtil {
    /**
     * 服务器地址
     */
    private static final String API_TEST_HOST = Constant.BASEURL_IP;
    private static Retrofit mRetrofit;
    private static APIService mAPIService;

    private static Retrofit getRetrofit(int state) {
        if (mRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (state==1){
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
                    mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_TEST_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(genericClient())
                    .build();
            }else if (state==2){
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(API_TEST_HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(genericClient1())
                        .build();
            }
        }
        return mRetrofit;
    }
    public static OkHttpClient genericClient() {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).build();
        return client;
    }
    public static OkHttpClient genericClient1() {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).
                readTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).build();
        return client;
    }
    public static APIService getAPIService(int state) {
        if (mAPIService == null) {
            mAPIService = getRetrofit(state).create(APIService.class);
        }
       return mAPIService;
    }

}
