package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {

	public static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		JPushInterface.init(this);
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志

	}

	public static BaseApplication getInstance() {

		return instance;
	}

}
