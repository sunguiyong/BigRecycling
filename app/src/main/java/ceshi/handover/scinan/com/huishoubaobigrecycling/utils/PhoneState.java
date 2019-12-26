package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

public class PhoneState {
    /**
     * 获取手机imei
     */
    public static String getIMEI(Context context, int id) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, id);
            Log.d("IMEI-------->", imei);
            return imei;
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }
}
