package ceshi.handover.scinan.com.huishoubaobigrecycling.push;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.MainActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.RoleChooseActivity;
import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
//        Toast.makeText(context, customMessage.toString(), Toast.LENGTH_SHORT).show();
        processCustomMessage(context, customMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    private void processCustomMessage(Context context, CustomMessage customMessage) {
        Log.d("JPUSH-=-=--", customMessage.message);
//        if (MainActivity.isShow){
            String message=customMessage.message;
            String extras=customMessage.extra;
            //作为广播消息对象
            Intent msgIntent=new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            //广播携带数据message
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {
                    Log.d("JSON","json exeception");
                    e.printStackTrace();
                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//        if (PushTest.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(PushTest.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(PushTest.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(PushTest.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//                    Log.d("JSON", "json exeception");
//                    e.printStackTrace();
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
    }
}