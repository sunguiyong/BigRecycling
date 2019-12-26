package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {

    public static BaseApplication instance;
    public static RequestQueue queues;
    private RequestQueue mRequestQueue = null;

    public static RequestQueue getHttpQueues() {
        return queues;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        queues = Volley.newRequestQueue(getApplicationContext(), (HttpStack) null);

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, null);
        }
        return mRequestQueue;
    }

    public static BaseApplication getInstance() {

        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
