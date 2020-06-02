package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.getdata.DataFromServer;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Arith;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtils;

import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.boliH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.feizhiH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.pingziH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.suliaoH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.yiwuH;

/**
 * 回收人员页面
 */
public class RecycleActivity extends BaseActivity implements View.OnClickListener {
    int x = R.layout.activity_recycler;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.version1)
    TextView version1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.xuanze_style)
    TextView xuanzeStyle;
    @BindView(R.id.img_suliao)
    ImageView imgSuliao;
    @BindView(R.id.img_boli)
    ImageView imgBoli;
    @BindView(R.id.img_yinliao)
    ImageView imgYinliao;
    @BindView(R.id.img_yifu)
    ImageView imgYifu;
    @BindView(R.id.img_feizhi)
    ImageView imgFeizhi;
    @BindView(R.id.type_ll)
    LinearLayout typeLl;
    @BindView(R.id.toudi_style)
    TextView toudiStyle;
    @BindView(R.id.lin_number)
    LinearLayout linNumber;
    @BindView(R.id.tv_tixing)
    TextView tvTixing;
    @BindView(R.id.over_bt)
    Button overBt;
    @BindView(R.id.suliaostatus_tv)
    TextView suliaostatusTv;
    @BindView(R.id.bolistatus_tv)
    TextView bolistatusTv;
    @BindView(R.id.pingzistatus_tv)
    TextView pingzistatusTv;
    @BindView(R.id.yiwustatus_tv)
    TextView yiwustatusTv;
    @BindView(R.id.zhileistatus_tv)
    TextView zhileistatusTv;

    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.companyname_tv)
    TextView companyTv;
    SharedPreferences preferences;
    private Gson gson = new Gson();
    private int flag;
    private double d1, d2, d3, d4, d5;
    private double temp1, temp2, temp3, temp4, temp5;
    private Map<String, String> map1 = new HashMap<>();
    private Map<String, String> map2 = new HashMap<>();
    private Map<String, String> map3 = new HashMap<>();
    private Map<String, String> map4 = new HashMap<>();

    private String deviceNum;

    @Override
    public void init() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ResultBean.boli = "";
        ResultBean.feizhi = "";
        ResultBean.pingzi = "";
        ResultBean.suliao = "";
        ResultBean.yiwu = "";
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        String str = FileUtils.getFileContent(new File(FileUtils.filePath)).trim();

        deviceNum = str.substring(0, str.indexOf("@"));

        hideBottomUIMenu();

        getDataFromBoard();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setStatus(pingziH, pingzistatusTv);
                setStatus(suliaoH, suliaostatusTv);
                setStatus(boliH, bolistatusTv);
                setStatus(feizhiH, zhileistatusTv);
                setStatus(yiwuH, yiwustatusTv);
            }
        });

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        companyTv.setText("公司：" + preferences.getString("group_name", ""));
//        phoneTv.setText("电话：" + preferences.getString("phone", ""));
        areaTv.setText("地址：" + preferences.getString("address", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_bt: {//回收结束的关闭按钮
                //1、再次发送获取重量指令
                //2、得到差值temp
                //3、用temp请求接口
                //4、请求成功请求高度接口
                DialogHelper.showProgressDlg1(RecycleActivity.this, "请稍后...");
                endTimer.start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendData2Board();
                    }
                }).start();
                break;
            }
            case R.id.img_yinliao: {
                uploadCmdToPort(1, 302, 1, "瓶子回收开");
                break;
            }
            case R.id.img_suliao: {
                uploadCmdToPort(2, 302, 1, "塑料回收开");
                break;
            }
            case R.id.img_boli: {
                uploadCmdToPort(3, 302, 1, "玻璃回收开");
                break;
            }
            case R.id.img_feizhi: {
                uploadCmdToPort(4, 302, 1, "纸类回收开");
                break;
            }
            case R.id.img_yifu: {
                uploadCmdToPort(5, 302, 1, "衣物回收开");
                break;
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        overBt.setOnClickListener(this);
        imgYinliao.setOnClickListener(this);
        imgSuliao.setOnClickListener(this);
        imgBoli.setOnClickListener(this);
        imgFeizhi.setOnClickListener(this);
        imgYifu.setOnClickListener(this);
    }

    @Override
    public int layoutView() {
        return R.layout.activity_recycler;
    }

    /**
     * @param position 柜子号
     * @param funCode  功能码
     * @param status   状态
     * @param name     柜子名称
     */
    private void uploadCmdToPort(int position, int funCode, int status, String name) {
//        int boxCode = unitEntities.get(position).getUnit_no();//获取配件编号
        String jsonCmd = ControlManagerImplMy.getInstance(getApplicationContext()).testJsonCmdStr(position
                , funCode, status, name);
        Log.d("jsonCmd", jsonCmd);
        ControlManagerImplMy.getInstance(getApplicationContext()).sendCmdToPort(ControlManagerImplMy.RECYCLING, jsonCmd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RoleChooseActivity.instance.finish();
        getBoxWeight();
    }

    /**
     * 板子数据获取
     */
    private void getDataFromBoard() {
        ControlManagerImplMy.getInstance(this).addControlCallBack(new ControlManagerImplMy.ControlCallBack() {
            @Override
            public void onResult(final String result) {
                CmdResultEntity cmdResultEntity = gson.fromJson(result, CmdResultEntity.class);
                String value = cmdResultEntity.getValue();
                if (cmdResultEntity.getFunc_code() == 8058) {
//                    DialogHelper.showProgressDlg(getApplicationContext(),"警告！warning！");
                    Toast.makeText(getApplicationContext(), "异常报警" + cmdResultEntity.getBox_code(), Toast.LENGTH_SHORT).show();
                    Log.d("烟温", "异常报警" + cmdResultEntity.getBox_code());
                    DataFromServer.postDataWarning(Integer.parseInt(preferences.getString("group_id", "")), deviceNum, cmdResultEntity.getBox_code(), new DataFromServer.StatusCallBack() {
                        @Override
                        public void success() {
                            Log.d("烟温报警", "success: ");
                            DialogHelper.showProgressDlg(getApplicationContext(), "警告！警告！");
                        }

                        @Override
                        public void error() {

                        }

                        @Override
                        public void failed() {

                        }
                    });
                }

                //102为测距指令
                switch (cmdResultEntity.getBox_code()) {
                    case 1: {
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            pingziH = cmdResultEntity.getValue();
                            if (Integer.parseInt(pingziH) < 20) {
                                pingziH = "1";
                            } else {
                                pingziH = "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            suliaoH = cmdResultEntity.getValue();
                            if (Integer.parseInt(suliaoH) < 20) {
                                suliaoH = "1";
                            } else {
                                suliaoH = "0";
                            }
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {//页面启动时第一次获取的重量值
                                ResultBean.suliao = cmdResultEntity.getValue();
                                d1 = Double.parseDouble(ResultBean.suliao);
                            }
                            ResultBean.suliao = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.suliao);
                            temp1 = Arith.div(d1 - d, 100.0, 3);
                            Log.d(TAG, "测试重量: " + d1 + d + temp1);
                        }

                        break;
                    }
                    case 3: {
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            boliH = cmdResultEntity.getValue();
                            if (Integer.parseInt(boliH) < 20) {
                                boliH = "1";
                            } else {
                                boliH = "0";
                            }
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {//页面启动时第一次获取的重量值
                                ResultBean.boli = cmdResultEntity.getValue();
                                d2 = Double.parseDouble(ResultBean.boli);
                            }
                            ResultBean.boli = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.boli);
                            temp2 = Arith.div(d2 - d, 100.0, 3);
                        }
                        break;
                    }
                    case 4: {
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            feizhiH = cmdResultEntity.getValue();
                            if (Integer.parseInt(feizhiH) < 20) {
                                feizhiH = "1";
                            } else {
                                feizhiH = "0";
                            }
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {//页面启动时第一次获取的重量值
                                ResultBean.feizhi = cmdResultEntity.getValue();
                                d3 = Double.parseDouble(ResultBean.feizhi);
                            }
                            ResultBean.feizhi = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.feizhi);
                            temp3 = Arith.div(d3 - d, 100.0, 3);
                        }
                        break;
                    }
                    case 5: {
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            yiwuH = cmdResultEntity.getValue();
                            if (Integer.parseInt(yiwuH) < 20) {
                                yiwuH = "1";
                            } else {
                                yiwuH = "0";
                            }
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {//页面启动时第一次获取的重量值
                                ResultBean.yiwu = cmdResultEntity.getValue();
                                d3 = Double.parseDouble(ResultBean.yiwu);
                            }
                            ResultBean.yiwu = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.yiwu);
                            temp4 = Arith.div(d4 - d, 100.0, 3);
                        }
                        break;
                    }
                    default:
                        break;
                }
                changeStatusH(pingziH);
                changeStatusH(suliaoH);
                changeStatusH(boliH);
                changeStatusH(feizhiH);
                changeStatusH(yiwuH);
            }

            @Override
            public void onIcResult(String icResult) {

            }
        });
    }

    private void changeStatusH(String distance) {
        if (distance != null) {
            if (Integer.parseInt(distance) < 20) {
                distance = "1";
            } else {
                distance = "0";
            }
        } else {
            distance = "2";
        }
    }

    private void setStatus(String heightS, TextView textView) {
        if (heightS != null) {
            //判断status  0 1 2三种状态
            if (heightS.equals("0")) {
                textView.setText("未满");
            } else if (heightS.equals("1")) {
                textView.setText("已满");
                textView.setTextColor(getResources().getColor(R.color.red));
            } else {
                textView.setText("异常");
                textView.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }


    private void getBoxWeight() {
        DialogHelper.showProgressDlg1(RecycleActivity.this, "请稍后...");
        flag = 0;
        //称重指令
        uploadCmdToPort(2, 103, 0, "塑料称重");
        SystemClock.sleep(300);
        uploadCmdToPort(3, 103, 0, "玻璃重量");
        SystemClock.sleep(300);
        uploadCmdToPort(4, 103, 0, "纸张重量");
        SystemClock.sleep(300);
        uploadCmdToPort(5, 103, 0, "衣物重量");
        SystemClock.sleep(300);
        DialogHelper.stopProgressDlg();

    }

    //回收结束发送的指令
    private boolean sendData2Board() {
        flag = 1;
        //称重指令
        uploadCmdToPort(2, 103, 0, "塑料称重");
        SystemClock.sleep(300);
        uploadCmdToPort(3, 103, 0, "玻璃重量");
        SystemClock.sleep(300);
        uploadCmdToPort(4, 103, 0, "纸张重量");
        SystemClock.sleep(300);
        uploadCmdToPort(5, 103, 0, "衣物重量");
        SystemClock.sleep(300);
        //测距指令
        uploadCmdToPort(1, 102, 0, "瓶子距离");
        SystemClock.sleep(300);
        uploadCmdToPort(2, 102, 0, "塑料距离");
        SystemClock.sleep(300);
        uploadCmdToPort(3, 102, 0, "玻璃距离");
        SystemClock.sleep(300);
        uploadCmdToPort(4, 102, 0, "纸张距离");
        SystemClock.sleep(300);
        uploadCmdToPort(5, 102, 0, "衣物距离");
        SystemClock.sleep(300);
        return true;
    }


    /**
     * 点击结算开始倒计时，发送数据给板子和服务器
     */

    CountDownTimer endTimer = new CountDownTimer(6 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            getData(temp1 + "", preferences.getInt("6", 0) + "", map1);
            getData(temp2 + "", preferences.getInt("9", 0) + "", map2);
            getData(temp3 + "", preferences.getInt("7", 0) + "", map3);
            getData(temp4 + "", preferences.getInt("8", 0) + "", map4);

        }
    };

    private void getData(final String weight, final String id, final Map<String, String> map) {
        //请求接口重量差值
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/recycleRecord/addRecycleRecord",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        DialogHelper.stopProgressDlg();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.clear();
                map.put("userId", SaveData.userId + "");
                map.put("deviceNumber", deviceNum);
                map.put("garbageWeight", weight + "");
                map.put("cabinId", id);
                Log.d(TAG, "重量 " + weight);
                return map;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }


    /**
     * 隐藏虚拟按键
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window _window = getWindow();
            WindowManager.LayoutParams params = _window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            _window.setAttributes(params);

            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

//            CloseBarUtil.closeBar();

        }
    }

    private static final String TAG = "回收人员";
}
