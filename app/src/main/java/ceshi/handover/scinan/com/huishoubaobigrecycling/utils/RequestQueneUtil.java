package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sunguiyong on 2020/5/30
 */
public class RequestQueneUtil {
    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            synchronized (RequestQueue.class) {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return requestQueue;
    }
}
