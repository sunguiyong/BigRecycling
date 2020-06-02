package ceshi.handover.scinan.com.huishoubaobigrecycling.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            if (packageName.equals("ceshi.handover.scinan.com.huishoubaobigrecycling")) {
                // 重新启动APP
                Intent intentToStart = context.getPackageManager().getLaunchIntentForPackage(packageName);
                context.startActivity(intentToStart);
            }
        }
    }
}
