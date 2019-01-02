package ceshi.handover.scinan.com.huishoubaobigrecycling.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.BalanceActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.MainActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.RecoverActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.User_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.SharePreferenceUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.TLog;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.ToastUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

	private String title_content;

	@Override
	public void onReceive(final Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			//Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				//Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			//	Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			//rocessCustomMessage(context, bundle);
				String update=bundle.getString(JPushInterface.EXTRA_MESSAGE);
				TLog.log(update);
                if (update!=null) {
					Gson gson=new Gson();
					if (update.contains("message")){
					User_Info user_info=gson.fromJson(update, User_Info.class);
					User_Info.UserBean  message=user_info.getUser();
					String nike=message.getNike();
					String token=user_info.getToken();
					SharePreferenceUtils.saveUserName(nike);
					SharePreferenceUtils.savetoken(token);
					Intent intent_broad = new Intent();
					intent_broad.setAction(MainActivity.ACTION_UPDATEUI);
					context.sendBroadcast(intent_broad);
					}else if (update.contains("APP_SETTLE")){
						Intent intent_broad = new Intent();
						intent_broad.setAction(RecoverActivity.ACTION_UPDATEUI);
						context.sendBroadcast(intent_broad);
					}else if (update.contains("PAGE:HOME")){
						Intent intent_broad = new Intent();
						intent_broad.setAction(RecoverActivity.ACTION_BACK);
						context.sendBroadcast(intent_broad);
					}else if (update.contains("Back_TO")){
						Intent intent_broad = new Intent();
						intent_broad.setAction(BalanceActivity.ACTION_BACK);
						context.sendBroadcast(intent_broad);
					}
				}
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				// 接收通知   title_content = bundle.getString(JPushInterface.EXTRA_ALERT);
				TLog.log(bundle.getString(JPushInterface.EXTRA_ALERT));
				/*Intent intent_broad=new Intent();
				intent_broad.setAction(MainActivity.ACTION_UPDATEUI);
				context.sendBroadcast(intent_broad);
				TLog.log(JPushInterface.EXTRA_MESSAGE);*/
			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			//	Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				//Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				//Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				//Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}
	}





	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					//Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
				//	Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.get(key));
			}
		}
		return sb.toString();
	}
	

}
