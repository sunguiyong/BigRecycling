package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;
import com.leesche.yyyiotlib.util.ThreadManager;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.getdata.DataFromServer;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIWrapper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxSubscriber;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.CompanyInfo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.DeviceInfo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LoginQRCode;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LoginResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LunboV;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.UnitPrice;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.VersionApk;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.mybean.QRCode;
import ceshi.handover.scinan.com.huishoubaobigrecycling.receiver.ScreenOffAdminReceiver;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.CustomerVideoView;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.KeyBoardUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.PackageUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.download.DownloadUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.download.JsDownloadListener;
import ceshi.handover.scinan.com.huishoubaobigrecycling.view.CyclerViewPager;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    int x = R.layout.activity_main;
    @BindView(R.id.appversion_tv)
    TextView appversionTv;
    @BindView(R.id.viewpager_head)
    CyclerViewPager viewpagerHead;
    @BindView(R.id.viewpager_foot)
    CyclerViewPager viewpagerFoot;
    @BindView(R.id.erweima)
    ImageView erweima;
    @BindView(R.id.phonelogin_bt)
    Button phoneloginBt;
    @BindView(R.id.phonenum_et)
    EditText phonenumet;
    @BindView(R.id.deviceid_tv)
    TextView deviceidTv;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.companyname_tv)
    TextView companyTv;
    @BindView(R.id.keyboard)
    LinearLayout keyboard;
    @BindView(R.id.keyboard_kv)
    KeyboardView keyboardKv;
    static Gson gson = new Gson();
    List<LunboV.DataBean.ListBean> message;
    List<LunboV.DataBean.ListBean> message_one;

    boolean sRecyclingIsOpen = false;
    public static boolean isShow = false;

    //快速点击多次退出APP
    final static int COUNTS = 4;// 点击次数
    final static long DURATION = 1000;// 规定有效时间
    long[] mHits = new long[COUNTS];
    boolean icCardIsOpen = false;
    private boolean icCheck = false;

    private Map<String, String> mapQRCode = new HashMap<>();
    private SharedPreferences preferences;
    private String versionStr;
    private KeyBoardUtil keyBoardUtil;
    Timer timer;
    private DevicePolicyManager policyManager;
    private ComponentName adminReceiver;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private int duration;

    @Override
    public int layoutView() {
        return R.layout.activity_main;
    }

    @SuppressLint("HandlerLeak")
    private Handler screenHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 二维码获取
     *
     * @param deviceNumber
     */
    private void getQRCode(final String deviceNumber) {
        StringRequest stringRequestImage = new StringRequest(Request.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/qrcode/getQRCode",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        QRCode qrCode;
                        qrCode = gson.fromJson(response, QRCode.class);
                        if (qrCode.getStatus() == 200) {
                            Glide.with(getApplicationContext()).load(qrCode.getData()).into(erweima);
                        }
                        Log.d("imageResponse", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("imageResponse", "请求失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                mapQRCode.clear();
                mapQRCode.put("deviceNumber", deviceNumber);
                return mapQRCode;
            }
        };
        stringRequestImage.setTag("stringRequestImage");
        BaseApplication.getHttpQueues().add(stringRequestImage);
    }

    /**
     * ic卡登录
     *
     * @param deviceId
     * @param groupId
     * @param icNumber
     */
    private void icLogin(final String deviceId, final String groupId, final String icNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.phoneLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("IC login", "onResponse: " + response);
                        if (response.contains("\"status\":200")) {
                            icCheck = false;
                            LoginResult loginResult;
                            loginResult = gson.fromJson(response, LoginResult.class);
                            for (int i = 0; i < loginResult.getData().getUser().getCategory().size(); i++) {
                                String name = loginResult.getData().getUser().getCategory().get(i).getName();
                                if (name.equals("塑料瓶")) {
                                    UnitPrice.pingzi = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("塑料")) {
                                    UnitPrice.suliao = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("纸类")) {
                                    UnitPrice.zhilei = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("纺织物")) {
                                    UnitPrice.yiwu = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("玻璃")) {
                                    UnitPrice.boli = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                            }
                            ResultBean.token = loginResult.getData().getUser().getToken();
                            chooseRole(loginResult.getData().getUser().getType());
                        } else {
                            Log.d("IC login", "onResponse: " + response);
                            icCheck = true;
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                icCheck = true;
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.clear();
                map.put("icNumber", icNumber);
                map.put("deviceNumber", deviceId);
                map.put("groupId", groupId);
                return map;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 手机号登录
     *
     * @param deviceNumber
     */
    private void phoneLogin(final String deviceNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.phoneLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("登录", response);
                        if (response.contains("\"status\":200")) {
                            LoginResult loginResult = new LoginResult();
                            loginResult = gson.fromJson(response, LoginResult.class);
                            for (int i = 0; i < loginResult.getData().getUser().getCategory().size(); i++) {
                                String name = loginResult.getData().getUser().getCategory().get(i).getName();
                                if (name.equals("塑料瓶")) {
                                    UnitPrice.pingzi = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("塑料")) {
                                    UnitPrice.suliao = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("纸类")) {
                                    UnitPrice.zhilei = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("纺织物")) {
                                    UnitPrice.yiwu = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                                if (name.equals("玻璃")) {
                                    UnitPrice.boli = loginResult.getData().getUser().getCategory().get(i).getPoint();
                                }
                            }
                            ResultBean.token = loginResult.getData().getUser().getToken();
                            chooseRole(loginResult.getData().getUser().getType());
                        } else {
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("登录请求", "失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.clear();
                map.put("phone", phonenumet.getText().toString());
                map.put("deviceNumber", deviceNumber);
                map.put("groupId", preferences.getString("group_id", ""));
                return map;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    public static boolean keyboardCheck = true;

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        //息屏相关
        adminReceiver = new ComponentName(MainActivity.this, ScreenOffAdminReceiver.class);
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        policyManager = (DevicePolicyManager) MainActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);

        //sharepreferences初始化
        preferences = getSharedPreferences("info", MODE_PRIVATE);
        //app版本号显示
        versionStr = PackageUtil.getAppVersion(getApplicationContext());
        appversionTv.setText(versionStr);
        //隐藏底部虚拟按钮
        hideBottomUIMenu();

        //权限校验
        String[] perms = {Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(MainActivity.this, perms)) {
            // 已经申请过权限，做想做的事
        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "申请存储权限",
                    0, perms);
        }
        message = new ArrayList<>();
        message_one = new ArrayList<>();
        phoneloginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号登录
                phoneLogin(deviceId);
            }
        });

        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                // 连接端口
                sRecyclingIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.RECYCLING);
                //IC读卡器串口
                icCardIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.IC_CARD);
                String jsonCmd = ControlManagerImplMy.getInstance(MainActivity.this).testJsonCmdStr(1, 203, -1, "固件版本");
                ControlManagerImplMy.getInstance(MainActivity.this).sendCmdToPort(ControlManagerImplMy.RECYCLING, jsonCmd);
            }
        });
        registerMessageReceiver();
        doFile();
        companyTv.setText("公司：" + preferences.getString("group_name", ""));
        phoneTv.setText("电话：" + preferences.getString("phone", ""));
        areaTv.setText("地址：" + preferences.getString("address", ""));

        keyBoardUtil = new KeyBoardUtil(keyboardKv, phonenumet);
        keyboardKv.setVisibility(View.VISIBLE);
        phonenumet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardCheck) {
                    keyboard.setVisibility(View.VISIBLE);
                    keyBoardUtil.showKeyboard();
                    keyboardKv.setVisibility(View.VISIBLE);
                    keyboardCheck = false;
                } else {
                    keyBoardUtil.hideKeyboard();
                    keyboard.setVisibility(View.VISIBLE);
                    keyboardKv.setVisibility(View.VISIBLE);
                    keyboardCheck = true;
                }
            }
        });
        mCurrentPositions = new HashMap<Integer, Integer>();

        //获取轮播图
        getLunbo();
    }

    static Map<Integer, Integer> mCurrentPositions;

    /**
     * 获取轮播图
     */
    private void getLunbo() {
//        LunBO(message, new MyPagerAdapter(message), viewpagerHead);
//        LunBO(message_one, new MyPagerAdapter(message_one), viewpagerFoot);
    }

    public CountDownTimer countDownTimer;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                //抬起时启动定时
//                startAD();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLunbo();
//        phonenumet.setText("");
        keyboardKv.setVisibility(View.VISIBLE);
        keyboardCheck = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getBoardData();//底板数据
            }
        }).start();
        SaveData.binUpdate = false;
        isShow = true;
        icCheck = true;

//        checkVersionApk();
    }

    @Override
    protected void onPause() {
        super.onPause();
        icCheck = false;

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        //当activity不在前台是停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isShow = false;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(permsRequestCode, permissions, grantResults, this);
    }

    //自动更新轮播图
    public class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent_recover = new Intent(MainActivity.this, RecoverActivity.class);
            startActivity(intent_recover);
            finish();
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    private static final String TAG = "MainActivity";

    //获取轮播图
    public void LunBO(final List<LunboV.DataBean.ListBean> list, final PagerAdapter adapter, final ViewPager viewPager1) {
        APIWrapper.getInstance().getAdList(preferences.getString("group_id", ""), preferences.getString("zone_id", ""))
                .compose(new RxHelper<LunboV>("正在加载，请稍候").io_main(MainActivity.this))
                .subscribe(new RxSubscriber<LunboV>() {
                    @Override
                    public void _onNext(LunboV lunboV) {
                        if (lunboV.getStatus() == 200) {
                            Log.d(TAG, "_onNext: " + lunboV.toString());
                            SaveData.timeList.clear();
                            for (int i = 0; i < lunboV.getData().get(0).getList().size(); i++) {
                                list.add(lunboV.getData().get(0).getList().get(i));
                                SaveData.timeList.add(lunboV.getData().get(0).getList().get(i).getPlayTime());
                            }
                            viewPager1.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        Log.d("getAdList", "_onError: " + msg);
                    }
                });
    }

    /**
     * 轮播适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        // viewpager 页码数量
        public List<LunboV.DataBean.ListBean> mDataList = new ArrayList<>();

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyPagerAdapter(List<LunboV.DataBean.ListBean> dataList) {
            mDataList = dataList;
        }

        public void shuxin() {
            notifyDataSetChanged();
        }

        //设置ViewPager有几个滑动页面
        @Override
        public int getCount() {
            return mDataList.size(); // [ABCD] ---> [DABCDA]
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void startUpdate(@NonNull ViewGroup container) {
            super.startUpdate(container);
        }

        //当前滑动到的viewpager页面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            final CustomerVideoView videoView = new CustomerVideoView(getApplicationContext());
            videoView.setBackgroundColor(getResources().getColor(R.color.white));
            String path = mDataList.get(position).getPath();
            //视频准备好的监听
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration = videoView.getDuration();//获取视频长度
                    Log.d(TAG, "onPrepared: " + duration / 1000);
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                                videoView.setBackgroundColor(Color.TRANSPARENT);//设置视频未显示时候的背景色
                            return true;
                        }
                    });
                }
            });
            //监听视频播放完的代码
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mPlayer) {
                    // TODO Auto-generated method stub
                    mPlayer.start();
                    mPlayer.setLooping(true);
                }
            });

            if (path.contains("mp4")) {
                container.addView(videoView);
                videoView.setVideoURI(Uri.parse(path));
                Log.d(TAG, "instantiateItem: " + "视频");
                videoView.start();
                return videoView;
            } else {
                Log.d(TAG, "instantiateItem: " + "图片");
                container.addView(imageView);
                Glide.with(MainActivity.this).load(path).into(imageView);
                return imageView;
            }
        }

        //每次划出当前页面的时候就销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogHelper.stopProgressDlg();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(messageReceiver);
        //        ControlManagerImplMy.getInstance(this).unlinkToPort();
        //销毁时停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public static final String MESSAGE_RECEIVED_ACTION = "ceshi.handover.scinan.com.huishoubaobigrecycling.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    //
    MessageReceiver messageReceiver;

    /**
     * 广播注册
     */
    public void registerMessageReceiver() {
        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver, filter);
    }

    /**
     * 自定义广播接收器
     * 极光接收消息处理
     */
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    String msgType = messge.substring(0, 3);
                    String msgContent = messge.substring(3, messge.length());

                    if (msgType.equals("001")) {//扫码登录
                        LoginQRCode loginQRCode;
                        loginQRCode = gson.fromJson(msgContent, LoginQRCode.class);
                        ResultBean.token = loginQRCode.getData().getUser().getToken();

                        for (int i = 0; i < loginQRCode.getData().getUser().getCategory().size(); i++) {
                            String name = loginQRCode.getData().getUser().getCategory().get(i).getName();
                            if (name.equals("塑料瓶")) {
                                UnitPrice.pingzi = loginQRCode.getData().getUser().getCategory().get(i).getPoint();
                            }
                            if (name.equals("塑料")) {
                                UnitPrice.suliao = loginQRCode.getData().getUser().getCategory().get(i).getPoint();
                            }
                            if (name.equals("纸类")) {
                                UnitPrice.zhilei = loginQRCode.getData().getUser().getCategory().get(i).getPoint();
                            }
                            if (name.equals("纺织物")) {
                                UnitPrice.yiwu = loginQRCode.getData().getUser().getCategory().get(i).getPoint();
                            }
                            if (name.equals("玻璃")) {
                                UnitPrice.boli = loginQRCode.getData().getUser().getCategory().get(i).getPoint();
                            }
                        }
                        chooseRole(loginQRCode.getData().getUser().getType());

                    } else if (msgType.equals("002") && isShow && msgContent.equals(deviceId.trim())) {//重启android系统
                        try {
                            Runtime.getRuntime().exec("su -c reboot");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (msgType.equals("003") && isShow && msgContent.equals(deviceId.trim())) {//关闭android系统
//                        Runtime.getRuntime().exec(new String[]{"su", "-c", "shutdown"});
                        Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                    } else if (msgType.equals("004") && isShow) {//请求轮播图更新
                        getLunbo();
                    } else if (msgType.equals("005") && isShow && msgContent.equals(deviceId.trim())) {//apk更新
                        DialogHelper.showProgressDlg1(getApplicationContext(), "APP更新中...");
                        checkVersionApk("huishou.apk");
                    } else if (msgType.equals("006") && isShow && msgContent.equals(deviceId.trim())) {//bin文件更新 固件更新
                        // TODO: 2019/12/30 校验版本
                        DialogHelper.showProgressDlg1(getApplicationContext(), "固件更新中...");
                        SaveData.binUpdate = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkVersionApk("STM32L073_RTT_Example.bin");
                            }
                        }, 1000);

                    } else if (msgType.equals("007")) {//设备首次获取公司信息
                        shutdownOrstart();
                        final CompanyInfo companyInfo;
                        companyInfo = gson.fromJson(msgContent, CompanyInfo.class);
                        Log.d("companyInfo", "onReceive: " + msgType + msgContent);
                        if (companyInfo.getNumber().equals(deviceId.trim()) && isShow) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                                    editor.putString("address", companyInfo.getAddress());
                                    editor.putString("phone", companyInfo.getPhone());
                                    editor.putString("group_name", companyInfo.getGroup_name());
                                    editor.putString("group_id", companyInfo.getGroup_id() + "");
                                    //todo
                                    editor.putString("zone_id", companyInfo.getZone_id() + "");
                                    editor.apply();
                                    areaTv.setText(companyInfo.getAddress());
                                    phoneTv.setText(companyInfo.getPhone());
                                    companyTv.setText(companyInfo.getGroup_name());
                                }
                            });
                        }

                    }
                    if (msgType.equals("008")) {
                        Runtime.getRuntime().exec("input keyevent 26");
                    }
                    Log.d("myJpush success", messge);
                }
            } catch (Exception e) {
            }
        }

    }

    public static String deviceId = "";

    /**
     * config.txt文件相关操作
     */
    private void doFile() {
        //APP启动后判断config.txt文件是否存在，不存在则进行以下操作
        //1、发送获取类型的指令
        //2、得到指令返回结果后，将结果作为参数请求后台接口
        //3、得到接口返回数据后，解析得到deviceId
        //4、将deviceId以config.txt存储

        //若APP启动后config.txt文件存在，则读取config.txt内容，保存在静态变量中
        if (FileUtils.checkFile()) {//config.txt文件存在
            String str = FileUtils.getFileContent(new File(FileUtils.filePath)).trim();
            deviceId = str.substring(0, str.indexOf("@"));
            Log.d("读取到config文件中设备编号", deviceId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deviceidTv.setText("设备编号：" + deviceId);
                }
            });
            getQRCode(deviceId);
        } else {//config.txt文件不存在
            Log.d("文件", "不存在");
            //1、发送获取类型指令到板子
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uploadCmdToPort(1, 202, 1, "类型");
                }
            }).start();
        }
    }

    /**
     * @param boxId   柜子号
     * @param funCode 功能码
     * @param status  状态
     * @param name    柜子名称
     */
    private void uploadCmdToPort(int boxId, int funCode, int status, String name) {
//        int boxCode = unitEntities.get(position).getUnit_no();//获取配件编号
        String jsonCmd = ControlManagerImplMy.getInstance(getApplicationContext()).testJsonCmdStr(boxId
                , funCode, status, name);
        Log.d("jsonCmd", jsonCmd);
        ControlManagerImplMy.getInstance(getApplicationContext()).sendCmdToPort(ControlManagerImplMy.RECYCLING, jsonCmd);
    }

    /**
     * 获取底板数据
     */
    private void getBoardData() {
        ControlManagerImplMy.getInstance(this).addControlCallBack(new ControlManagerImplMy.ControlCallBack() {
            @Override
            public void onResult(final String result) {
                CmdResultEntity cmdResultEntity = gson.fromJson(result, CmdResultEntity.class);
                if (cmdResultEntity.getFunc_code() == 8058) {
                    Toast.makeText(getApplicationContext(), "异常报警" + cmdResultEntity.getBox_code(), Toast.LENGTH_SHORT).show();
                    Log.d("烟温", "异常报警" + cmdResultEntity.getBox_code());
                    DataFromServer.postDataWarning(Integer.parseInt(preferences.getString("group_id", "")), deviceId, cmdResultEntity.getBox_code(), new DataFromServer.StatusCallBack() {
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
                //判断202获取类型功能码的value值
                if (cmdResultEntity.getFunc_code() == 202) {
                    Log.d("板子类型", "onResult: " + cmdResultEntity.getValue());
                    switch (cmdResultEntity.getValue()) {
                        case "1":
                            SaveData.doorType = "A";
                            break;
                        case "2":
                            SaveData.doorType = "B";
                            break;
                        case "3":
                            SaveData.doorType = "C";
                            break;
                    }
                    //请求获取deviceId接口
                    getDeviceId(SaveData.doorType);
                }
                if (cmdResultEntity.getFunc_code() == 203) {
                    Log.d("固件版本号---->", "onResult: " + cmdResultEntity.getValue());
                }

            }

            @Override
            public void onIcResult(final String icResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), icResult + "", Toast.LENGTH_SHORT).show();
                        if (icCheck) {
                            icLogin(deviceId, preferences.getString("group_id", ""), icResult);
                        }
                    }
                });

            }
        });
    }

    private Map<String, String> map = new HashMap<>();
    final Map<String, String> map1 = new HashMap<>();

    /**
     * 获取deviceId
     *
     * @param doorType 设备类型
     */
    private void getDeviceId(final String doorType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/device/createDeviceNumber",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getDeviceId", "onResponse: " + response);
                        if (response.contains("\"status\":200")) {
                            DeviceInfo deviceInfo;
                            deviceInfo = gson.fromJson(response, DeviceInfo.class);
                            //把接口获得的deviceId存入sd卡
                            FileUtils.writeTxtToFile(deviceInfo.getData().getDeviceNumber() + "@" + deviceInfo.getData().getDeviceCheckCode(), "/storage/sdcard0/smartrecovery", "config.txt");
                            //todo 得到deviceId之后，写入SD卡的同时，请求获取二维码的接口
                            deviceId = deviceInfo.getData().getDeviceNumber().trim();
                            SaveData.deviceId = deviceId;
                            SaveData.deviceCheckId = deviceInfo.getData().getDeviceCheckCode();
                            deviceidTv.setText("设备编号：" + deviceId);
                            getQRCode(deviceId);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map1.clear();
                map1.put("deviceType", "A");
                return map1;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    @OnClick({R.id.appversion_tv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.appversion_tv: {
                continuousClick(COUNTS, DURATION);
                break;
            }
            default:
                break;
        }
    }

    private void continuousClick(int count, long time) {
        //每次点击 数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            System.exit(0);//退出APP
        }
    }

    /**
     * 隐藏底部虚拟按键，滑动也不能显示
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

    /**
     * 隐藏底部虚拟按键，滑动可显示
     */
    protected void hideBottomUIMenuCopy() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 固件和apk更新使用的url
     */
    private String testUrl = "version/downLoadFile?path=C:/energySystem/tomcat-8084/webapps/garbageClassifyManageSystem/resources/testupload/";
    private String baseUrl = "http://114.116.37.87:8084/garbageClassifyManageSystem/";

    /**
     * 下载apk或bin文件
     * /storage/sdcard0/Download/STM32L073_RTT_Example.bin
     */
    private void checkVersionApk(final String name) {
        DownloadUtils downloadUtils = new DownloadUtils(baseUrl, new JsDownloadListener() {
            @Override
            public void onStartDownload(long length) {
                Log.d("onStartDownload", "onStartDownload: ");
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onFail(String errorInfo) {

            }
        });
        downloadUtils.download(testUrl + name, new File("/storage/sdcard0/Download", name), new Subscriber() {
            @Override
            public void onCompleted() {
                if (name.equals("huishou.apk")) {
                    downSuccess();
                } else {
                    for (int i = 1; i <= 5; i++) {
                        SaveData.binUpdate = true;
                        uploadCmdToPort(i, 999, 1, "更新固件");
                        Log.d("柜子号——》》", "run: " + i);
                        SystemClock.sleep(1000 * 120);
                    }
                    DialogHelper.stopProgressDlg();
                }
                Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(getApplicationContext(), "下载中...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ProgressDialog progressDialog;

    //下载apk操作
    public void downFileDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);    //进度条，在下载的时候实时更新进度，提高用户友好度
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//STYLE_HORIZONTAL
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍候...");
//        progressDialog.setProgress(0);
        progressDialog.show();
        Log.d("SettingActivity", "downFile: ");
    }

    public void downSuccess() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("下载完成");
        builder.setMessage("请点击安装");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //android N的权限问题
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授权读权限
                    Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "ceshi.handover.scinan.com.huishoubaobigrecycling.fileprovider",
                            new File("/storage/sdcard0/Download", "huishou.apk"));//注意修改
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(new File("/storage/sdcard0/Download", "huishou.apk")), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    /**
     * 检查apk版本，若不一致则更新
     */
    private void checkVersionApk() {
        APIWrapper.getInstance().versionNumber(preferences.getString("group_id", ""))
                .compose(new RxHelper<VersionApk>("正在加载，请稍候").io_main(MainActivity.this))
                .subscribe(new RxSubscriber<VersionApk>() {
                    @Override
                    public void _onNext(VersionApk versionApk) {
                        if (versionApk.getStatus() == 200 && !versionApk.getData().getVersion().equals(versionStr)) {
                            DialogHelper.showProgressDlg1(MainActivity.this, "APP更新中...");
                            checkVersionApk("huishou.apk");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        Log.d("versionNumber", "_onError: ");
                    }
                });
    }

    /**
     * 不同角色不同功能
     * 0 普通用户
     * 1 测试人员
     * 3 回收人员
     */
    private void chooseRole(int role) {
        switch (role) {
            case 0://普通用户
                Intent intent = new Intent(MainActivity.this, RecoverActivity.class);
                startActivity(intent);
                break;
            case 1://测试人员
                Intent intent1 = new Intent(MainActivity.this, RoleChooseActivity.class);
                intent1.putExtra("role", 1);
                startActivity(intent1);
                break;
            case 3://回收人员
                Intent intent2 = new Intent(MainActivity.this, RoleChooseActivity.class);
                intent2.putExtra("role", 3);
                startActivity(intent2);
                break;
        }
    }

    /**
     * 自动开关机
     */
    private void shutdownOrstart() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }
}
