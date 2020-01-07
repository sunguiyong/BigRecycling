package ceshi.handover.scinan.com.huishoubaobigrecycling.activity.getdata;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtils;

/**
 * 上报异常状态
 * 1、烟温探测器
 */
public class DataFromServer {
    private static Map<String, String> mapH = new HashMap<>();

    public static void postDataWarning(final int groupId, String deviceNumber, final int boxcode, final StatusCallBack statusCallBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                "https://weapp.iotccn.cn/garbageClassifyApi/api/cabin/receiveStateInfo",
                "http://10.1.20.182:8882/garbageClassifyApi_war/api/cabin/receiveStateInfo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getDataH", "onResponse: " + response);
                        if (response.contains("200")) {
                            statusCallBack.success();
//                            DialogHelper.stopProgressDlg();
//                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("getDataHError", "onErrorResponse: " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                mapH.clear();
                mapH.put("groupId", groupId + "");
                mapH.put("deviceNumber", FileUtils.getFileContent(new File(FileUtils.filePath)));
                mapH.put("categoryId1", "1");
                if (boxcode == 1) {
                    mapH.put("state1", "2");
                } else {
                    mapH.put("state1", "");
                }
                mapH.put("weightQuantity1", "0");
                mapH.put("categoryId2", "6");
                if (boxcode == 2) {
                    mapH.put("state2", "2");
                } else {
                    mapH.put("state2", "");
                }
                mapH.put("weightQuantity2", "0");
                mapH.put("categoryId3", "9");
                if (boxcode == 3) {
                    mapH.put("state3", "2");
                } else {
                    mapH.put("state3", "");
                }
                mapH.put("weightQuantity3", "0");
                mapH.put("categoryId4", "7");
                if (boxcode == 4) {
                    mapH.put("state4", "2");
                } else {
                    mapH.put("state4", "");
                }
                mapH.put("weightQuantity4", "0");
                mapH.put("categoryId5", "8");
                if (boxcode == 5) {
                    mapH.put("state5", "2");
                } else {
                    mapH.put("state5", "");
                }
                mapH.put("weightQuantity5", "0");
                return mapH;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    public interface StatusCallBack {
        void success();

        void error();

        void failed();
    }
}
