package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.getdata.DataFromServer;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIWrapper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxSubscriber;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.BaseResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.UnitPrice;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Arith;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.SharePreferenceUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.ToastUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;
import rx.Subscription;

import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.boliH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.feizhiH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.pingziH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.suliaoH;
import static ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean.yiwuH;


/**
 * 瓶 塑 玻 纸 纺
 * 用户投递页面
 */
public class RecoverActivity extends BaseActivity {
    int x = R.layout.activity_recover;
    @BindView(R.id.username)
    TextView username;
    //投放结束按钮
    @BindView(R.id.end_bt)
    Button endBt;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.img_feizhi)
    ImageView imgFeizhi;
    @BindView(R.id.img_boli)
    ImageView imgBoli;
    @BindView(R.id.img_yinliao)
    ImageView imgYinliao;
    @BindView(R.id.img_yifu)
    ImageView imgYifu;
    @BindView(R.id.sum_tv)
    TextView sumTv;
    @BindView(R.id.tablayout)
    TableLayout tableLayout;
    @BindView(R.id.img_suliao)
    ImageView imgSuliao;
    @BindView(R.id.lin_number)
    LinearLayout linNumber;
    @BindView(R.id.tv_tixing)
    TextView tvTixing;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.children_bt)
    Button childrenBt;
    @BindView(R.id.type_ll)
    LinearLayout typeLl;
    @BindView(R.id.goon_bt)
    Button goonBt;
    @BindView(R.id.back_bt)
    Button backBt;
    @BindView(R.id.toudi_style)
    TextView toudiStyle;
    @BindView(R.id.pingzi_number)
    TextView pingziNumber;
    @BindView(R.id.lin_pingzi)
    LinearLayout linPingzi;
    @BindView(R.id.feizhi_number)
    TextView feizhiNumber;
    @BindView(R.id.lin_feizhi)
    LinearLayout linFeizhi;
    @BindView(R.id.yinliao_tv)
    TextView yinliaoTv;
    @BindView(R.id.yiwu_tv)
    TextView yiwuTv;
    @BindView(R.id.boli_tv)
    TextView boliTv;
    @BindView(R.id.suliao_tv)
    TextView suliaoTv;
    @BindView(R.id.zhilei_tv)
    TextView zhileiTv;
    @BindView(R.id.sum_ll)
    LinearLayout sumLl;
    boolean b_feizhi;
    boolean b_boli;
    boolean b_yinliao;
    boolean b_yifu;
    boolean b_suliao;
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
    private long sumScore = 0L;
    @BindView(R.id.xuanze_style)
    TextView xuanzeStyle;
    @BindView(R.id.version1)
    TextView version1;
    @BindView(R.id.upingzi)
    TextView upingzi;
    @BindView(R.id.uyiwu)
    TextView uyiwu;
    @BindView(R.id.uboli)
    TextView uboli;
    @BindView(R.id.usuliao)
    TextView usuliao;
    @BindView(R.id.uzhilei)
    TextView uzhilei;
    @BindView(R.id.usuliao1)
    TextView usuliao1;
    @BindView(R.id.uboli1)
    TextView uboli1;
    @BindView(R.id.upingzi1)
    TextView upingzi1;
    @BindView(R.id.uyiwu1)
    TextView uyiwu1;
    @BindView(R.id.uzhilei1)
    TextView uzhilei1;
    @BindView(R.id.pingzisum)
    TextView pingzisum;
    @BindView(R.id.yiwusum)
    TextView yiwusum;
    @BindView(R.id.bolisum)
    TextView bolisum;
    @BindView(R.id.suliaosum)
    TextView suliaosum;
    @BindView(R.id.feizhisum)
    TextView feizhisum;
    @BindView(R.id.deviceid_tv)
    TextView deviceIdTv;
    @BindView(R.id.appversion_tv)
    TextView appversionTv;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.companyname_tv)
    TextView companyTv;
    SharedPreferences preferences;
    public boolean isShow;

    private TimeCount timecount;
    private boolean feizhi_boolean = false;
    private boolean yinliao_boolean = false;
    private String token;
    private String recovery_one = null;
    private Subscription onSubscribe;
    private int pingziNum = 0;
    private Gson gson = new Gson();
    private boolean isTimeStart = true;
    private String deviceNum;

    private int flag = 0;
    private double d1, d2, d3, d4;

    @Override
    public int layoutView() {
        return R.layout.activity_recover;
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        hideBottomUIMenu();
        //获取设备编号
        String str = FileUtils.getFileContent(new File(FileUtils.filePath)).trim();
        deviceNum = str.substring(0, str.indexOf("@"));
        deviceIdTv.setText("设备编号：" + deviceNum);
        timecount = new TimeCount(180 * 1000, 1000);
        timecount.start();
        String userName = SharePreferenceUtils.getUserName(UiUtils.getContext());
        username.setText(userName);
        getDataFromBoard();
        initUnit();
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

    /**
     * 初始化单价显示
     */
    private void initUnit() {
        upingzi.setText(UnitPrice.pingzi + "");
        usuliao.setText(UnitPrice.suliao + "");
        uboli.setText(UnitPrice.boli + "");
        uyiwu.setText(UnitPrice.yiwu + "");
        uzhilei.setText(UnitPrice.zhilei + "");
        upingzi1.setText(UnitPrice.pingzi + "积分/个");
        usuliao1.setText(UnitPrice.suliao + "积分/KG");
        uboli1.setText(UnitPrice.boli + "积分/KG");
        uyiwu1.setText(UnitPrice.yiwu + "积分/KG");
        uzhilei1.setText(UnitPrice.zhilei + "积分/KG");
    }

    private Map<String, String> map = new HashMap<>();
    private Map<String, String> mapH = new HashMap<>();

    /**
     * 上传重量和积分结算数据到服务器
     */
    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/device/settlement",
//                "http://10.1.20.182:8882/garbageClassifyApi_war/api/cabin/receiveStateInfo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("投递完毕", response);
                        if (response.contains("200")) {
                            getDataH();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("投递完毕", "请求失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.clear();
                map.put("token", ResultBean.token);
                map.put("groupId", preferences.getString("group_id", ""));
                map.put("deviceNumber", deviceNum);
                map.put("c1", pingziNum + "");
                map.put("p1", pingziNum * 10 + "");
                map.put("c2", suliaoTv.getText().toString());
                map.put("p2", suliaosum.getText().toString());
                map.put("c3", boliTv.getText().toString());
                map.put("p3", bolisum.getText().toString());
                map.put("c4", zhileiTv.getText().toString());
                map.put("p4", feizhisum.getText().toString());
                map.put("c5", yiwuTv.getText().toString());
                map.put("p5", yiwusum.getText().toString());
                map.put("t", sumTv.getText().toString());
                return map;
            }
        };

        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 上传距离数据到服务器(满仓或未满)
     */
    private void getDataH() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/cabin/receiveStateInfo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getDataH", "onResponse: " + response);
                        if (response.contains("200")) {
                            DialogHelper.stopProgressDlg();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getDataHError", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                mapH.clear();
                mapH.put("token", ResultBean.token);
                mapH.put("groupId", preferences.getString("group_id", ""));
                mapH.put("deviceNumber", deviceNum);
                mapH.put("categoryId1", "1");
                mapH.put("state1", pingziH);//0  1
                mapH.put("weightQuantity1", "0");
                mapH.put("categoryId2", "6");
                mapH.put("state2", suliaoH);
                mapH.put("weightQuantity2", "0");
                mapH.put("categoryId3", "9");
                mapH.put("state3", boliH);
                mapH.put("weightQuantity3", "0");
                mapH.put("categoryId4", "7");
                mapH.put("state4", feizhiH);
                mapH.put("weightQuantity4", "0");
                mapH.put("categoryId5", "8");
                mapH.put("state5", yiwuH);
                mapH.put("weightQuantity5", "0");
                Log.d("hehe", pingziH + "--" + suliaoH + "--" + boliH + "--" + feizhiH + "--" + yiwuH);
                return mapH;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    public void Open_Barn(String name) {
        APIWrapper.getInstance().querOPenBarn("Bearer " + token, name, Constant.MAC)
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_no_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        if (status == 200) {

                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
//                        TLog.log(msg + "");
                    }
                });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time.setClickable(false);
            time.setText((millisUntilFinished / 1000) + "s");
            time.setTextColor(Color.parseColor("#ffffff"));
        }

        @Override
        public void onFinish() {
            //倒计时结束测距、称重、关门、结算、上报仓门状态
            DialogHelper.showProgressDlg1(RecoverActivity.this, "结算中,请稍后...");
            endTimer.start();
            clickable();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendData2Board();
                }
            }).start();

            yinliaoTv.setText(pingziNum + "");
            yiwuTv.setText(ResultBean.yiwu);
            boliTv.setText(ResultBean.boli);
            suliaoTv.setText(ResultBean.suliao);
            zhileiTv.setText(ResultBean.feizhi);
            //todo 上传投递数据到服务器
            tableLayout.setVisibility(View.VISIBLE);
            sumLl.setVisibility(View.VISIBLE);
            endBt.setVisibility(View.INVISIBLE);
            goonBt.setVisibility(View.INVISIBLE);
            backBt.setVisibility(View.VISIBLE);
        }
    }

    public void Sync_net() {
        APIWrapper.getInstance().querSync("Bearer " + token, "HOME")
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
//                        TLog.log(status + "");
                        if (status == 200) {
                            Intent intent1 = new Intent(RecoverActivity.this, MainActivity.class);
                            timecount.cancel();
                            onSubscribe.unsubscribe();
                            startActivity(intent1);
                            finish();
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
//                        TLog.log(msg + "");
                    }
                });
    }

    private boolean isOpen = false;
    private boolean isOpen_suliao = false;
    private boolean isChildren = true;

    private void getBoxWeight() {
        DialogHelper.showProgressDlg1(RecoverActivity.this, "请稍后...");
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

        //关门指令
        uploadCmdToPort(2, 1002, 0, "塑料关门");
        SystemClock.sleep(300);
        uploadCmdToPort(3, 1003, 0, "玻璃关门");
        SystemClock.sleep(300);
        uploadCmdToPort(4, 1004, 0, "纸张关门");
        SystemClock.sleep(300);
        uploadCmdToPort(5, 1005, 0, "衣物关门");
        SystemClock.sleep(300);
        return true;
    }

    @OnClick({R.id.back_bt, R.id.goon_bt, R.id.end_bt, R.id.children_bt, R.id.username,
            R.id.img_feizhi, R.id.img_boli, R.id.img_yinliao, R.id.img_yifu, R.id.img_suliao,
            R.id.lin_number, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goon_bt://继续投放
                //todo 刷新页面重新投递
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case R.id.back_bt://结束
                startActivity(new Intent(RecoverActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.end_bt://投放结束-->dialogshow-->倒计时开始-->发送数据给底板-->倒计时结束后-->请求接口
                DialogHelper.showProgressDlg1(RecoverActivity.this, "结算中,请稍后...");
                endTimer.start();
                isTimeStart = false;
                clickable();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendData2Board();
                    }
                }).start();

                yinliaoTv.setText(pingziNum + "");
                yiwuTv.setText(ResultBean.yiwu);
                boliTv.setText(ResultBean.boli);
                suliaoTv.setText(ResultBean.suliao);
                zhileiTv.setText(ResultBean.feizhi);
                //todo 上传投递数据到服务器
                tableLayout.setVisibility(View.VISIBLE);
                sumLl.setVisibility(View.VISIBLE);
                endBt.setVisibility(View.INVISIBLE);
                goonBt.setVisibility(View.VISIBLE);
                backBt.setVisibility(View.VISIBLE);

                break;
            case R.id.children_bt:
                if (isChildren) {
                    isChildren = false;
                    //切换高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) typeLl.getLayoutParams();
                    layoutParams.topMargin = 600;
                    childrenBt.setText("成人模式");
                } else {
                    isChildren = true;
                    //切换高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) typeLl.getLayoutParams();
                    layoutParams.topMargin = 0;
                    childrenBt.setText("儿童模式");
                }

                break;
            case R.id.username:
                break;
            case R.id.img_feizhi:
                if (b_feizhi == false) {
                    uploadCmdToPort(4, 1, 1, "纸类开门");
                    imgFeizhi.setClickable(false);
                    b_feizhi = true;
                    feizhi_boolean = true;
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    linFeizhi.setVisibility(View.VISIBLE);

                } else {
                    uploadCmdToPort(4, 1004, 0, "纸张关门");
                    b_feizhi = false;
                    imgFeizhi.setImageResource(R.mipmap.feizhi);
                }
                break;
            case R.id.img_boli:
                if (b_boli == false) {
                    uploadCmdToPort(3, 1, 1, "玻璃开门");
                    imgBoli.setClickable(false);
                    b_boli = true;
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                } else {
                    uploadCmdToPort(3, 1003, 0, "玻璃关门");
                    b_boli = false;
                    imgBoli.setImageResource(R.mipmap.boli);
                }
                break;
            case R.id.img_yinliao:
                if (isOpen == false) {
                    uploadCmdToPort(1, 302, 1, "瓶子回收开");
                    SystemClock.sleep(200);
                    uploadCmdToPort(1, 1, 1, "饮料瓶开门");
                    imgYinliao.setClickable(false);
                    isOpen = true;
                    yinliao_boolean = false;//
                    b_yinliao = false;//
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    linPingzi.setVisibility(View.VISIBLE);
                } else {
                    b_yinliao = false;
                    imgYinliao.setImageResource(R.mipmap.yinliao);
                    isOpen = false;
                }
                break;
            case R.id.img_yifu:
                if (b_yifu == false) {
                    uploadCmdToPort(5, 1, 1, "衣服开门");
                    imgYifu.setClickable(false);
                    b_yifu = true;
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                } else {
                    b_yifu = false;
                    uploadCmdToPort(5, 1005, 0, "衣服关门");
                    imgYifu.setImageResource(R.mipmap.yifu);
                }
                break;

            case R.id.img_suliao:
                if (isOpen_suliao == false) {
                    uploadCmdToPort(2, 1, 1, "塑料开门");
                    imgSuliao.setClickable(false);
                    isOpen_suliao = true;
                    b_suliao = true;
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                } else {
                    b_suliao = false;
                    imgSuliao.setImageResource(R.mipmap.suliao);
                    uploadCmdToPort(2, 1002, 0, "塑料关门");
                    isOpen_suliao = false;
                }
                break;
            case R.id.lin_number:
                break;
            case R.id.submit:
                String zhuangtai = submit.getText().toString();
                if ("返回主页".equals(zhuangtai)) {
                    Sync_net();
                } else {
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
        getBoxWeight();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timecount.cancel();
        ResultBean.boli = "";
        ResultBean.feizhi = "";
        ResultBean.pingzi = "";
        ResultBean.suliao = "";
        ResultBean.yiwu = "";
    }

    /**
     * 点击投放结束后设置不可点击
     */
    private void clickable() {
        imgYinliao.setClickable(false);
        imgBoli.setClickable(false);
        imgFeizhi.setClickable(false);
        imgYifu.setClickable(false);
        imgSuliao.setClickable(false);
        endBt.setClickable(false);
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

    /**
     * 点击结算开始倒计时，发送数据给板子和服务器
     */
    CountDownTimer endTimer = new CountDownTimer(7 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            getData();
        }
    };

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
//                    DialogHelper.showProgressDlg1(getApplicationContext(),"警告！warning！");
                    Toast.makeText(getApplicationContext(), "异常报警" + cmdResultEntity.getBox_code(), Toast.LENGTH_SHORT).show();
                    Log.d("烟温", "异常报警" + cmdResultEntity.getBox_code());
                    DataFromServer.postDataWarning(Integer.parseInt(preferences.getString("group_id", "")), SaveData.deviceId, cmdResultEntity.getBox_code(), new DataFromServer.StatusCallBack() {
                        @Override
                        public void success() {
                            Log.d("烟温报警", "success: ");
                            DialogHelper.showProgressDlg1(getApplicationContext(), "警告！警告！");
                        }

                        @Override
                        public void error() {

                        }

                        @Override
                        public void failed() {

                        }
                    });
                }
                switch (cmdResultEntity.getBox_code()) {
                    case 1: {
                        if (cmdResultEntity.getFunc_code() == 1 && cmdResultEntity.getStatus() == 1) {
                            imgYinliao.setImageResource(R.mipmap.select_yinliao);
                        }
                        if (cmdResultEntity.getFunc_code() == 101 && cmdResultEntity.getValue() != null) {
                            ResultBean.pingzi = value;
                            pingziNum++;
                            Log.d("瓶子个数", pingziNum + "个");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pingziNumber.setText(pingziNum + "个");
                                    pingzisum.setText(pingziNum * UnitPrice.pingzi + "");//瓶子小计
                                    if (pingzisum.getText().toString() != null) {
                                        sumScore = Integer.parseInt(pingzisum.getText().toString());
                                    }
                                }
                            });
                        }
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
                        if (cmdResultEntity.getFunc_code() == 1 && cmdResultEntity.getStatus() == 1) {
                            imgSuliao.setImageResource(R.mipmap.select_suliao);
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {
                                ResultBean.suliao = cmdResultEntity.getValue();
                                d1 = Double.parseDouble(ResultBean.suliao);
                            }

                            ResultBean.suliao = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.suliao);
                            suliaoTv.setText(Arith.div(d - d1, 100.0, 3) + "");//数量
                            suliaosum.setText(Math.round(Arith.div(d - d1, 100.0, 3) * UnitPrice.suliao) + "");//积分
                            if (suliaosum.getText().toString() != null) {
                                sumScore = sumScore + Math.round(Arith.div(d - d1, 100.0, 3) * UnitPrice.suliao);
                            }
                            Log.d(TAG, "onResult: " + d);
                            Log.d(TAG, "onResult: " + d1);
                        } else if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() == 0) {
                            suliaoTv.setText("0");
                        }
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            suliaoH = cmdResultEntity.getValue();
                            if (Integer.parseInt(suliaoH) < 20) {
                                suliaoH = "1";
                            } else {
                                suliaoH = "0";
                            }
                        }
                        break;
                    }
                    case 3: {
                        if (cmdResultEntity.getFunc_code() == 1 && cmdResultEntity.getStatus() == 1) {
                            imgBoli.setImageResource(R.mipmap.select_boli);
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {
                                ResultBean.boli = cmdResultEntity.getValue();
                                d2 = Double.parseDouble(ResultBean.boli);
                            }
                            ResultBean.boli = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.boli);
                            Log.d("responseBoli", Arith.div(d, 100.0, 3) + "aaa");
                            boliTv.setText(Arith.div(d - d2, 100.0, 3) + "");
                            bolisum.setText(Math.round(Arith.div(d - d2, 100.0, 3) * UnitPrice.boli) + "");
                            if (bolisum.getText().toString() != null) {
                                sumScore = sumScore + Math.round(Arith.div(d - d2, 100.0, 3) * UnitPrice.boli);
                            }
                        } else if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() == 0) {
                            boliTv.setText("0");
                        }
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            boliH = cmdResultEntity.getValue();
                            if (Integer.parseInt(boliH) < 20) {
                                boliH = "1";
                            } else {
                                boliH = "0";
                            }
                        }
                        break;
                    }
                    case 4: {
                        if (cmdResultEntity.getFunc_code() == 1 && cmdResultEntity.getStatus() == 1) {
                            imgFeizhi.setImageResource(R.mipmap.select_feizhi);
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() > 0) {
                            if (flag == 0) {
                                ResultBean.feizhi = cmdResultEntity.getValue();
                                d3 = Double.parseDouble(ResultBean.feizhi);
                            }
                            ResultBean.feizhi = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.feizhi);
                            Log.d("responseFeizhi", Arith.div(d - d3, 100.0, 3) + "aaa");
                            zhileiTv.setText(Arith.div(d - d3, 100.0, 3) + "");
                            feizhisum.setText(Math.round(Arith.div(d - d3, 100.0, 3) * UnitPrice.zhilei) + "");
                            if (feizhisum.getText().toString() != null) {
                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.zhilei);
                            }
                        } else if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() == 0) {
                            zhileiTv.setText("0");
                        }
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            feizhiH = cmdResultEntity.getValue();
                            if (Integer.parseInt(feizhiH) < 20) {
                                feizhiH = "1";
                            } else {
                                feizhiH = "0";
                            }
                        }
                        break;
                    }
                    case 5: {
                        if (cmdResultEntity.getFunc_code() == 1 && cmdResultEntity.getStatus() == 1) {
                            imgYifu.setImageResource(R.mipmap.select_yifu);
                        }
                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
                            if (flag == 0) {
                                ResultBean.yiwu = cmdResultEntity.getValue();
                                d4 = Double.parseDouble(ResultBean.yiwu);
                            }
                            ResultBean.yiwu = cmdResultEntity.getValue();
                            double d = Double.parseDouble(ResultBean.yiwu);
                            Log.d("responseYiwu", Arith.div(d - d4, 100.0, 3) + "aaa");
                            yiwuTv.setText(Arith.div(d - d4, 100.0, 3) + "");
                            yiwusum.setText(Math.round(Arith.div(d - d4, 100.0, 3) * UnitPrice.yiwu) + "");
                            if (yiwusum.getText().toString() != null) {
                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.yiwu);
                            }
                        } else if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() == 0) {
                            yiwuTv.setText("0");
                        }
                        if (cmdResultEntity.getFunc_code() == 102 && !cmdResultEntity.getValue().equals("")) {
                            yiwuH = cmdResultEntity.getValue();
                            if (Integer.parseInt(yiwuH) < 20) {
                                yiwuH = "1";
                            } else {
                                yiwuH = "0";
                            }
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

                if (isShow) {
                    sumTv.setText(sumScore + "");
                }
            }

            @Override
            public void onIcResult(String icResult) {

            }
        });
    }

    private static final String TAG = "RecoverActivity";

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

    private void setStatus(String heightStatu, TextView textView) {
        if (heightStatu != null) {
            //判断status  0 1 2三种状态
            if (heightStatu.equals("0")) {
                textView.setText("未满");
            } else if (heightStatu.equals("1")) {
                textView.setText("已满");
                textView.setTextColor(getResources().getColor(R.color.red));
            } else {
                textView.setText("异常");
                textView.setTextColor(getResources().getColor(R.color.red));
            }
        }
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

}
