package ceshi.handover.scinan.com.huishoubaobigrecycling.utils.download;

import android.app.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIService;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit retrofit;
    private JsDownloadListener listener;
    private String baseUrl;
    private String downloadUrl;
//    private RetrofitHelper retrofitHelper ;
    public DownloadUtils(String baseUrl, JsDownloadListener listener) {
        this.baseUrl = baseUrl;
        this.listener = listener;
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 开始下载
     * @param url
     * @param file
     * @param subscriber
     */
    public void download(@NonNull String url, final File file, Subscriber subscriber) {
        retrofit.create(APIService.class)
                .download(url)
                .subscribeOn(rx.schedulers.Schedulers.io())
                .unsubscribeOn(rx.schedulers.Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {

                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        writeFile(inputStream, file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 将输入流写入文件
     * @param inputString
     * @param file
     */
    private void writeFile(InputStream inputString, File file) {
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b,0,len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            listener.onFail("IOException");
        }

    }

}
