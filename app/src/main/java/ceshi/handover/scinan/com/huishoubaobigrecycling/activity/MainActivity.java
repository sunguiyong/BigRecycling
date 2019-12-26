package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;
import com.leesche.yyyiotlib.util.ThreadManager;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIWrapper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxSubscriber;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.DeviceInfo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Erweima;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LoginQRCode;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LoginResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Lunbo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.UnitPrice;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Version_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.load.DownloadUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.CloseBarUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.PakageUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.QRCode;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.TLog;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.ToastUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.view.CyclerViewPager;
import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
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
    Gson gson = new Gson();
    List<Lunbo.DataBean> message;
    List<Lunbo.DataBean> message_one = new ArrayList<>();
    public static final String ACTION_UPDATEUI = "action.updateUI";
    //    @BindView(R.id.face)
//    ImageView face;

    private String version;
    String res = "";
    boolean sRecyclingIsOpen = false;
    public static boolean isShow = false;

    //快速点击多次退出APP
    final static int COUNTS = 4;// 点击次数
    final static long DURATION = 1000;// 规定有效时间
    long[] mHits = new long[COUNTS];

    int count = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //todo 发送距离请求到板子
//                    Log.d("执行handler", count++ + "");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
            mHandler.postDelayed(this, 3 * 1000);//延时3秒执行，并重复
        }
    };


    private Map<String, String> mapQRCode = new HashMap<>();

    @Override
    public int layoutView() {
        return R.layout.activity_main;
    }

    //获取登录二维码
    private void getQRCode(final String deviceNumber) {
        StringRequest stringRequestImage = new StringRequest(Request.Method.POST,
                "https://weapp.iotccn.cn/garbageClassifyApi/api/qrcode/getQRCode",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ceshi.handover.scinan.com.huishoubaobigrecycling.mybean.QRCode qrCode = new ceshi.handover.scinan.com.huishoubaobigrecycling.mybean.QRCode();
                        qrCode = gson.fromJson(response, ceshi.handover.scinan.com.huishoubaobigrecycling.mybean.QRCode.class);
                        if (qrCode.getStatus() == 200) {
                            Glide.with(getApplicationContext()).load(qrCode.getData()).into(erweima);
//                            erweima.setImageBitmap(getURLimage("http://pic49.nipic.com/file/20140926/6608733_100333244000_2.jpg"));
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

    private void phoneLogin(final String deviceNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.phoneLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("登录", response);
                        if (response.contains("200")) {
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
                            Intent intent = new Intent(MainActivity.this, RoleChooseActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("登录", "失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.clear();
                map.put("phone", phonenumet.getText().toString());
                map.put("deviceNumber", deviceNumber);
                return map;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    public void initview(Bundle savedInstanceState) {

        super.initview(savedInstanceState);
        //打开串口

        sRecyclingIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.RECYCLING);
        sRecyclingIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.IC_CARD);
        hideBottomUIMenu();//隐藏底部虚拟按钮
        JPushInterface.init(this);
        //设置debug模式
        JPushInterface.setDebugMode(true);
        getBoardData();//底板数据
//        Create_File();
        // readFile();
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
        String str_version = PakageUtil.getAppVersion(UiUtils.getContext());
//        tv_version.setText("版本:v" + str_version);
        phoneloginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号登录
                phoneLogin(deviceId);
            }
        });

//        tv_version.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                finish();
//                return true;
//            }
//        });

        //获取轮播图
        Observable.interval(0, 1200, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.STOP))   //当Activity执行Onstop()方法是解除订阅关系
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
//                        Erweima_Net();
                    }
                });
        LunBO(message, new MyPagerAdapter(message), viewpagerHead, "ad1,ad2,ad3");
        LunBO(message_one, new MyPagerAdapter(message_one), viewpagerFoot, "ad4,ad5,ad6");
//        JPushInterface.setAlias(MainActivity.this, 1, Constant.MAC);
        version = PakageUtil.getAppVersion(this);
//        Version();

        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                // 连接端口
//                sRecyclingIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.RECYCLING);
                //IC读卡器串口
//                icCardIsOpen = ControlManagerImplMy.getInstance(MainActivity.this).linkToPort(ControlManagerImplMy.IC_CARD);
//                if(sRecyclingIsOpen){
//                    //获取设备编号
//                    String jsonCmd = ControlManagerImplMy.getInstance(MainActivity.this).testJsonCmdStr(1
//                            , 202, -1, "设备编号");
//                    ControlManagerImplMy.getInstance(MainActivity.this).sendCmdToPort(ControlManagerImplMy.RECYCLING, jsonCmd);
//
//                    //获取固件版本
//                    jsonCmd  = ControlManagerImplMy.getInstance(MainActivity.this).testJsonCmdStr(1
//                            , 203, -1, "固件版本");
//                    ControlManagerImplMy.getInstance(MainActivity.this).sendCmdToPort(ControlManagerImplMy.RECYCLING, jsonCmd);
//                }
            }
        });

//        PhoneState.getIMEI(this, 0);
        registerMessageReceiver();
        doFile();
    }

    /**
     * 获取轮播图
     */
    private void getLunbo() {
        //获取轮播图
        Observable.interval(0, 1200, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.STOP))   //当Activity执行Onstop()方法是解除订阅关系
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
//                        Erweima_Net();
                    }
                });
        LunBO(message, new MyPagerAdapter(message), viewpagerHead, "ad1,ad2,ad3");
        LunBO(message_one, new MyPagerAdapter(message_one), viewpagerFoot, "ad4,ad5,ad6");
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
        isShow = true;
//        mHandler.postDelayed(task, 3000);//首次调用延时3秒执行

    }

    @Override
    protected void onPause() {
        super.onPause();
        //当activity不在前台是停止定时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isShow = false;
        mHandler.removeCallbacksAndMessages(null);

    }


    public void Create_File() {
        File sdCardDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "User");
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!sdCardDir.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            sdCardDir.mkdirs();
        }
        File saveFile = new File(sdCardDir, "user.txt");

        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(saveFile);//获得FileOutputStream
            //将要写入的字符串转换为byte数组
            byte[] bytes = Constant.MAC.getBytes();
            fos.write(bytes);//将byte数组写入文件
            fos.close();//关闭文件输出流

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        String file = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/User/user.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            TLog.log(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void Version() {
        APIWrapper.getInstance().querVersion("3")
                .compose(new RxHelper<Version_Info>("正在加载，请稍候").io_no_main(MainActivity.this))
                .subscribe(new RxSubscriber<Version_Info>() {
                    @Override
                    public void _onNext(Version_Info response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            String version_late = response.getMessage().getVersion();
                            String download = response.getMessage().getDownload();
                            if (!version_late.equals(version)) {
                                DownloadUtils.get().downloadFile(download, MainActivity.this);
                            }
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                        ToastUtil.showShort("版本网络错误");
                    }
                });
    }

    /*private void installApk(String filename) {
        File file = new File(filename);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), type);
        startActivity(intent);

    }*/
//    @OnClick(R.id.face)
//    public void onClick() {
//        Intent intent = new Intent(MainActivity.this, FaceActivity.class);
//        startActivity(intent);
//        finish();
//    }

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

    private void Erweima_Net() {
        APIWrapper.getInstance().queryErweima()
                .compose(new RxHelper<Erweima>("正在加载，请稍候").io_main(MainActivity.this))
                .subscribe(new RxSubscriber<Erweima>() {
                    @Override
                    public void _onNext(Erweima response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            Erweima.MessageBean erweimas = response.getMessage();
                            String val = erweimas.getVal();
                            String id = erweimas.getId();
                            erweima.setImageBitmap(QRCode.createQRCode(" {\n" + " \"id\":\"" + id + "\",\n" + "\"val\":\"" + val + "\",\n" + "\"mac\":\"123456\"\n" + "}"));
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                        ToastUtil.showShort("二维码网络错误");
                    }
                });
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void LunBO(final List<Lunbo.DataBean> list, final PagerAdapter adapter, final ViewPager viewPager1, String imgPosition) {
        APIWrapper.getInstance().queryOneLunBO("", "3", "2", imgPosition)
                .compose(new RxHelper<Lunbo>("正在加载，请稍候").io_main(MainActivity.this))
                .subscribe(new RxSubscriber<Lunbo>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void _onNext(Lunbo response) {
                        Log.d("onNext", "invoked");
                        Log.d("response", response.toString());
                        int status = response.getStatus();
                        if (status == 200) {
                            List<Lunbo.DataBean> messageBeans = response.getData();
                            for (int i = 0; i < messageBeans.size(); i++) {
                                Lunbo.DataBean messageBean = new Lunbo.DataBean();
                                String url = messageBeans.get(i).getFileUrl();
                                messageBean.setFileUrl(url);
                                list.add(messageBean);
                            }
                            viewPager1.setAdapter(adapter);
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        Log.d("_onError", "invoked");
//                        TLog.log(msg + "");
                        ToastUtil.showShort("轮播网络错误");
                    }
                });
    }

    /**
     * 轮播适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        // viewpager 页码数量
        public List<Lunbo.DataBean> mDataList = new ArrayList<>();

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyPagerAdapter(List<Lunbo.DataBean> dataList) {
            mDataList = dataList;
        }

        public void shuxin() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDataList.size(); // [ABCD] ---> [DABCDA]
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = mDataList.get(position).getFileUrl();
            Glide.with(MainActivity.this).load(url).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                        LoginQRCode loginQRCode = new LoginQRCode();
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
                        Intent intent1 = new Intent(context, RoleChooseActivity.class);
                        startActivity(intent1);
                    } else if (msgType.equals("002") && isShow) {//重启android系统
                        try {
                            Runtime.getRuntime().exec("su -c reboot");//重启android系统
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (msgType.equals("003") && isShow) {
//                        Runtime.getRuntime().exec(new String[]{"su", "-c", "shutdown"});
                        Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                    } else if (msgType.equals("004") && isShow) {
                        getLunbo();
                    }
                    Toast.makeText(context, "myJpush success" + messge, Toast.LENGTH_SHORT).show();
                    Log.d("myJpush success", messge);
//                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");

//                    if (!ExampleUtil.isEmpty(extras)) {
//                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//                    }
//                    setCostomMsg(showMsg.toString());
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
            deviceId = FileUtils.getFileContent(new File(FileUtils.filePath));
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
            uploadCmdToPort(1, 202, 1, "类型");
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
                Log.d("类型！", result);
                CmdResultEntity cmdResultEntity = gson.fromJson(result, CmdResultEntity.class);
                //上报距离到服务器
//                if (cmdResultEntity.getBox_code() == 1 && cmdResultEntity.getFunc_code() == 102) {
//                    cmdResultEntity.getValue();
//                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.uploadStatus,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            map.put()
//                            return ;
//                        }
//                    }
//                }
                //判断202获取类型功能码的value值
                if (cmdResultEntity.getFunc_code() == 202) {
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
            }

            @Override
            public void onIcResult(String icResult) {

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
                        DeviceInfo deviceInfo = new DeviceInfo();
                        deviceInfo = gson.fromJson(response, DeviceInfo.class);
                        //把接口获得的deviceId存入sd卡
                        FileUtils.writeTxtToFile(deviceInfo.getData(), "/storage/sdcard0/Download", "config.txt");
                        //todo 得到deviceId之后，写入SD卡的同时，请求获取二维码的接口
                        deviceId = deviceInfo.getData();
                        deviceidTv.setText("设备编号：" + deviceId);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map1.clear();
                map1.put("token", "");
                map1.put("deviceType", doorType);
                map1.put("groupId", "3");
                return map1;
            }
        };
        stringRequest.setTag("stringRequest");
        BaseApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 发送获取距离的指令到底板
     */
    private void getDataFromBoard() {
        uploadCmdToPort(1, 102, 0, "距离");
        SystemClock.sleep(200);
        uploadCmdToPort(2, 102, 0, "塑料距离");
        SystemClock.sleep(200);
        uploadCmdToPort(3, 102, 0, "玻璃距离");
        SystemClock.sleep(200);
        uploadCmdToPort(4, 102, 0, "纸张距离");
        SystemClock.sleep(200);
        uploadCmdToPort(5, 102, 0, "衣物距离");

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
}
