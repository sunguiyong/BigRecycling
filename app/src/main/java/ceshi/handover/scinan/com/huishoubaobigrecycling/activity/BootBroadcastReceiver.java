package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent1) {
        if (intent1.getAction().equals(ACTION)) {
            Intent intent = new Intent(context, MainActivity.class);  // 要启动的Activity
            //1.如果自启动APP，参数为需要自动启动的应用包名
            //Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            //下面这句话必须加上才能开机自动运行app的界面
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //2.如果自启动Activity
            context.startActivity(intent);
            //3.如果自启动服务
           // context.startService(intent);
        }
    }

}
