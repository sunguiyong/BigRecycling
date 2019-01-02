package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIWrapper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxSubscriber;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.BaseResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.DeviceState_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.User_info1;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.pingzi_info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.PakageUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.SharePreferenceUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.TLog;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.ToastUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RecoverActivity extends BaseActivity {
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.jifen1)
    TextView jifen1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.img_jinshu)
    ImageView imgJinshu;
    @BindView(R.id.img_feizhi)
    ImageView imgFeizhi;
    @BindView(R.id.img_boli)
    ImageView imgBoli;
    @BindView(R.id.img_yinliao)
    ImageView imgYinliao;
    @BindView(R.id.img_yifu)
    ImageView imgYifu;
    @BindView(R.id.img_dianzi)
    ImageView imgDianzi;
    @BindView(R.id.img_suliao)
    ImageView imgSuliao;
    @BindView(R.id.lin_number)
    LinearLayout linNumber;
    @BindView(R.id.tv_tixing)
    TextView tvTixing;
    @BindView(R.id.submit)
    Button submit;
    boolean b_jinshu;
    boolean b_feizhi;
    boolean b_boli;
    boolean b_yinliao;
    boolean b_yifu;
    boolean b_dianzi;
    boolean b_suliao;
    boolean state = false;
    boolean state1 = false;
    boolean state2 = false;
    @BindView(R.id.xuanze_style)
    TextView xuanzeStyle;
    public final int handler_pingzi_open = 1;
    public final int handler_pingzi_close = 2;
    public final int handler_dianzi_open = 3;
    public final int handler_dianzi_close = 4;
    public final int handler_feizhi_open = 5;
    public final int handler_feizhi_close = 6;
    public final int handler_result_number = 7;
    public final int handler_pingzi_number = 8;
    String pingzi_close = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":2,\"nodetypeid\":58,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";
    String pingzi_open = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":2,\"nodetypeid\":57,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";
    String dianzi_open = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":3,\"nodetypeid\":57,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";
    String dianzi_close = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":3,\"nodetypeid\":58,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";
    String feizhi_open = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":1,\"nodetypeid\":57,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";
    String feizhi_close = "{\"message\":\"1234\",\"taskcount\":1 ,\"node\":[{\"deviceid\":1,\"nodetypeid\":58,\"ctl\":121,\"devicesite\":1,\"value\":\"1\"}]}";

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @SuppressWarnings("unused")
        private WeakReference<RecoverActivity> mActivity;

        @SuppressWarnings("unused")
        public void mHandler(RecoverActivity activity) {
            mActivity = new WeakReference<RecoverActivity>(activity);
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case handler_pingzi_open:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(pingzi_open);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_pingzi_close:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(pingzi_close);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_dianzi_open:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(dianzi_open);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_dianzi_close:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(dianzi_close);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_feizhi_open:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(feizhi_open);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_feizhi_close:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Socket socket = null;
                            try {
                                socket = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                socket.isConnected();
                                OutputStream ops = socket.getOutputStream();
                                OutputStreamWriter opsw = new OutputStreamWriter(ops);
                                BufferedWriter bw = new BufferedWriter(opsw);
                                bw.write(feizhi_close);
                                bw.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case handler_result_number:
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                result = new Socket(Constant.SOCKET_IP, Constant.SOCKET_PORT);
                                InputStream inputStream = result.getInputStream();
                                DataInputStream input = new DataInputStream(inputStream);
                                while (true) {
                                    byte[] b = new byte[1024];
                                    int length = input.read(b);
                                    msg_pingzi = new String(b, 0, length, "gb2312");
                                    TLog.log(msg_pingzi);

                                    if (msg_pingzi != null && !msg_pingzi.isEmpty()) {
                                        handler.sendEmptyMessageDelayed(handler_pingzi_number, 0);
                                    }
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }.start();

                    break;
                case handler_pingzi_number:
                    Gson gson = new Gson();
                    pingzi_info pingzi = gson.fromJson(msg_pingzi, pingzi_info.class);
                    String type = pingzi.getType();
                    if (type.equals("bottle")) {
                        String last = pingzi.getLast();
                        String thisX = pingzi.getThisX();
                        if (last != null && thisX != null) {
                            int last_count = Integer.parseInt(last);
                            int this_count = Integer.parseInt(thisX);
                            int number = this_count - last_count;
                            if (pingziNumber != null) {
                                pingziNumber.setText(number + "个");
                            }
                        }
                    } else if (type.equals("paper")) {
                        String last = pingzi.getLast();
                        String thisX = pingzi.getThisX();
                        if (last != null && !"".equals(last) && thisX != null && !"".equals(thisX)) {
                            String left_last = last.replace(" ", "");
                            TLog.log(left_last + "xxy");
                            int kg_pos = left_last.indexOf("kg");
                            String last_last = left_last.substring(0, kg_pos);
                            TLog.log(last_last + "xxy");
                            String trim_last = last_last.trim();
                            String left_thisX = thisX.replace(" ", "");
                            int kg_this = left_last.indexOf("kg");
                            String thisX_thisX = left_thisX.substring(0, kg_this);
                            String trim_this = thisX_thisX.trim();
                            TLog.log(trim_this);
                            TLog.log(trim_last);
                            float thisX_float = Float.parseFloat(trim_this);
                            float last_float = Float.parseFloat(trim_last);
                            float number_float = thisX_float - last_float;
                            String string_number = String.valueOf(number_float);
                            if (string_number != null && !string_number.isEmpty()) {
                                if (feizhiNumber != null) {
                                    String str = String.format("%.2f", number_float);
                                    feizhiNumber.setText(str + "千克");
                                }
                            } else {
                                try {
                                    result.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            ToastUtil.showLong(UiUtils.getContext(), "设备故障");
                        }
                    } else if (type.equals("fullbox")) {
                        ToastUtil.showLong(UiUtils.getContext(), "仓已满,请等待工作人员清理");
                    }

                    break;
            }
        }
    };
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
    @BindView(R.id.version)
    TextView version;
    private String msg;
    private String msg_pingzi;
    private Socket result;
    private TimeCount timecount;
    private boolean feizhi_boolean = false;
    private boolean yinliao_boolean = false;
    private boolean dianzi_boolean = false;
    private String token;
    private String recovery_one = null;
    private MediaPlayer mediaPlayer;
    private MediaPlayer start_mediaPlayer;
    private MediaPlayer end_mediaPlayer;
    private Subscription onSubscribe;
    private UpdateUIBroadcastReceiver broadcastReceiver;
    private BackBroadcastReceiver backReceiver;

    @Override
    public int layoutView() {
        return R.layout.activity_recover;
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        timecount = new TimeCount(180000, 1000);
        timecount.start();
        token = SharePreferenceUtils.gettoekn(UiUtils.getContext());
        Info();
        startmusic();
        String dianshi_version = PakageUtil.getAppVersion(UiUtils.getContext());
      /*  version.setText("版本:v"+dianshi_version);
        version.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                finish();
                return true;
            }
        });*/
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATEUI);
        broadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(ACTION_BACK);
        backReceiver = new BackBroadcastReceiver();
        registerReceiver(backReceiver, filter1);
        String userName = SharePreferenceUtils.getUserName(UiUtils.getContext());
        recovery_one = getIntent().getStringExtra("RECOVERY");
        if (recovery_one != null) {
        } else {
            APIWrapper.getInstance().querInitializ("Bearer " + token, Constant.MAC)
                    .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_no_main(RecoverActivity.this))
                    .subscribe(new RxSubscriber<BaseResult>() {
                        @Override
                        public void _onNext(BaseResult response) {
                            int status = response.getStatus();
                            if (status == 200) {
                                TLog.log("xuxinyi");
                            } else if (status == 300) {
                                ToastUtil.showShort(response.getMessage() + "");
                            }
                        }

                        @Override
                        public void _onError(String msg) {
                            TLog.log(msg + "");
                        }
                    });
        }
        onSubscribe = Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.STOP))   //当Activity执行Onstop()方法是解除订阅关系
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        DeviceStates_Net(Constant.MAC);
                    }
                });
        username.setText(userName);
    }

    public void Info() {
        APIWrapper.getInstance().querUserInfo("Bearer " + token)
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        TLog.log(status + "");
                        if (status == 200) {
                            Object message = response.getMessage();
                            if (message != null) {
                                Gson gson = new Gson();
                                User_info1 user_info = gson.fromJson(gson.toJson(message), User_info1.class);
                                String nick = user_info.getNick();
                                String mobile = user_info.getMobile();
                                JPushInterface.setAlias(RecoverActivity.this, 1, mobile);
                                username.setText(nick);
                                int achieve = user_info.getAchieve();
                                jifen1.setText(achieve + "");
                            }
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                    }
                });
    }

    private void DeviceStates_Net(String mac) {
        APIWrapper.getInstance().querDeviceState("Bearer " + token, mac)
                .compose(new RxHelper<DeviceState_Info>("正在加载，请稍候").io_no_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<DeviceState_Info>() {
                    @Override
                    public void _onNext(DeviceState_Info response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            List<String> list = response.getMessage().getDoorSet();
                            for (int i = 0; i < list.size(); i++) {
                                String str_state = list.get(i);
                                if ("METAL".equals(str_state)) {
                                    b_jinshu = true;
                                    imgJinshu.setImageResource(R.mipmap.select_jinshu);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                } else if ("PAPER".equals(str_state)) {
                                    b_feizhi = true;
                                    imgFeizhi.setImageResource(R.mipmap.select_feizhi);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                    if (feizhi_boolean == false) {
                                        feizhi_boolean = true;
                                        imgFeizhi.setImageResource(R.mipmap.select_feizhi);
                                        xuanzeStyle.setVisibility(View.INVISIBLE);
                                        Message message = handler.obtainMessage();
                                        message.what = handler_feizhi_open;
                                        handler.sendMessage(message);
                                        Message message1 = handler.obtainMessage();
                                        message1.what = handler_result_number;
                                        handler.sendMessage(message1);
                                        tvTixing.setVisibility(View.INVISIBLE);
                                        submit.setText("结算");
                                        linFeizhi.setVisibility(View.VISIBLE);
                                        timecount.cancel();
                                        timecount.start();
                                    }
                                } else if ("GLASS".equals(str_state)) {
                                    b_boli = true;
                                    imgBoli.setImageResource(R.mipmap.select_boli);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                } else if ("BOTTLE".equals(str_state)) {
                                    b_yinliao = true;
                                    imgYinliao.setImageResource(R.mipmap.select_yinliao);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                    if (yinliao_boolean == false) {
                                        yinliao_boolean = true;
                                        imgYinliao.setImageResource(R.mipmap.select_yinliao);
                                        Message message = handler.obtainMessage();
                                        message.what = handler_pingzi_open;
                                        handler.sendMessage(message);
                                        Message message1 = handler.obtainMessage();
                                        message1.what = handler_result_number;
                                        handler.sendMessage(message1);
                                        xuanzeStyle.setVisibility(View.INVISIBLE);
                                        tvTixing.setVisibility(View.INVISIBLE);
                                        submit.setText("结算");
                                        linPingzi.setVisibility(View.VISIBLE);
                                        Open_Barn("BOTTLE");
                                        timecount.cancel();
                                        timecount.start();
                                    }
                                } else if ("CLOTHES".equals(str_state)) {
                                    b_yifu = true;
                                    imgYifu.setImageResource(R.mipmap.select_yifu);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                } else if ("ELECTRON".equals(str_state)) {
                                    b_dianzi = true;
                                    imgDianzi.setImageResource(R.mipmap.select_dianzi);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                    if (dianzi_boolean == false) {
                                        b_dianzi = true;
                                        dianzi_boolean = true;
                                        imgDianzi.setImageResource(R.mipmap.select_dianzi);
                                        xuanzeStyle.setVisibility(View.INVISIBLE);
                                        tvTixing.setVisibility(View.INVISIBLE);
                                        submit.setText("结算");
                                        Message message = handler.obtainMessage();
                                        message.what = handler_dianzi_open;
                                        handler.sendMessage(message);
                                        timecount.cancel();
                                        timecount.start();
                                    }
                                } else if ("PLASTIC".equals(str_state)) {
                                    b_suliao = true;
                                    imgSuliao.setImageResource(R.mipmap.select_suliao);
                                    submit.setText("结算");
                                    xuanzeStyle.setVisibility(View.INVISIBLE);
                                    tvTixing.setVisibility(View.INVISIBLE);
                                }
                            }
                            String page = response.getMessage().getPage();
                            if ("SETTLEMENT".equals(page)) {
                                Toudi_State();
                            }
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                    }
                });
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
                        TLog.log(msg + "");
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
            String zhuangtai = submit.getText().toString();
            if ("返回主页".equals(zhuangtai)) {
                Sync_net();
            } else {
                CloseDoor();
            }
        }
    }

    public void Sync_net() {
        APIWrapper.getInstance().querSync("Bearer " + token, "HOME")
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        TLog.log(status + "");
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
                        TLog.log(msg + "");
                    }
                });
    }

    private void startmusic() {
        try {
            mediaPlayer = new MediaPlayer().create(RecoverActivity.this, R.raw.huanyingshiyong);
            //3 准备播放
            // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // mediaPlayer.prepare();
            //mediaPlayer.prepareAsync();
            // mediaPlayer.start();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startmusicopen() {
        try {
            start_mediaPlayer = new MediaPlayer().create(RecoverActivity.this, R.raw.cangmenyidakia);
            start_mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (start_mediaPlayer != null && start_mediaPlayer.isPlaying()) {
            start_mediaPlayer.stop();
            start_mediaPlayer.release();
            start_mediaPlayer = null;
        }
    }

    private void startmusicend() {
        try {
            end_mediaPlayer = new MediaPlayer().create(RecoverActivity.this, R.raw.cangmenguanbi);
            end_mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (end_mediaPlayer != null && end_mediaPlayer.isPlaying()) {
            end_mediaPlayer.stop();
            end_mediaPlayer.release();
            end_mediaPlayer = null;
        }
    }

    @OnClick({R.id.version,R.id.username, R.id.jifen1, R.id.img_jinshu, R.id.img_feizhi, R.id.img_boli, R.id.img_yinliao, R.id.img_yifu, R.id.img_dianzi, R.id.img_suliao, R.id.lin_number, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                break;
            case R.id.jifen1:
                break;
            case R.id.img_jinshu:
                if (b_jinshu == false) {
                    b_jinshu = true;
                    imgJinshu.setImageResource(R.mipmap.select_jinshu);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    Open_Barn("METAL");
                } else {
                    /*b_jinshu=false;
                    imgJinshu.setImageResource(R.mipmap.jinshu);*/
                }
                break;
            case R.id.img_feizhi:
                if (b_feizhi == false) {
                    startmusicopen();
                    b_feizhi = true;
                    feizhi_boolean = true;
                    imgFeizhi.setImageResource(R.mipmap.select_feizhi);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    Message message = handler.obtainMessage();
                    message.what = handler_feizhi_open;
                    handler.sendMessage(message);
                    Message message1 = handler.obtainMessage();
                    message1.what = handler_result_number;
                    handler.sendMessage(message1);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    linFeizhi.setVisibility(View.VISIBLE);
                    Open_Barn("PAPER");
                    timecount.cancel();
                    timecount.start();
                } else {
                   /* b_feizhi=false;
                    imgFeizhi.setImageResource(R.mipmap.feizhi);*/
                }
                break;
            case R.id.img_boli:
                if (b_boli == false) {
                    b_boli = true;
                    imgBoli.setImageResource(R.mipmap.select_boli);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    Open_Barn("GLASS");
                } else {
                   /* b_boli=false;
                    imgBoli.setImageResource(R.mipmap.boli);*/
                }
                break;
            case R.id.img_yinliao:
                if (b_yinliao == false) {
                    yinliao_boolean = true;
                    b_yinliao = true;
                    startmusicopen();
                    imgYinliao.setImageResource(R.mipmap.select_yinliao);
                    Message message = handler.obtainMessage();
                    message.what = handler_pingzi_open;
                    handler.sendMessage(message);
                    Message message1 = handler.obtainMessage();
                    message1.what = handler_result_number;
                    handler.sendMessage(message1);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    linPingzi.setVisibility(View.VISIBLE);
                    Open_Barn("BOTTLE");
                    timecount.cancel();
                    timecount.start();
                } else {
                  /*  b_yinliao=false;
                    imgYinliao.setImageResource(R.mipmap.yinliao);*/
                }
                break;
            case R.id.img_yifu:
                if (b_yifu == false) {
                    b_yifu = true;
                    imgYifu.setImageResource(R.mipmap.select_yifu);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    Open_Barn("CLOTHES");
                } else {
                    /*b_yifu=false;
                    imgYifu.setImageResource(R.mipmap.yifu);*/
                }
                break;
            case R.id.img_dianzi:
                if (b_dianzi == false) {
                    b_dianzi = true;
                    dianzi_boolean = true;
                    startmusicopen();
                    imgDianzi.setImageResource(R.mipmap.select_dianzi);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    Message message = handler.obtainMessage();
                    message.what = handler_dianzi_open;
                    handler.sendMessage(message);
                    Open_Barn("ELECTRON");
                    timecount.cancel();
                    timecount.start();
                } else {
                   /* b_dianzi=false;
                    imgDianzi.setImageResource(R.mipmap.dianzi);*/
                }
                break;
            case R.id.img_suliao:
                if (b_suliao == false) {
                    b_suliao = true;
                    imgSuliao.setImageResource(R.mipmap.select_suliao);
                    xuanzeStyle.setVisibility(View.INVISIBLE);
                    tvTixing.setVisibility(View.INVISIBLE);
                    submit.setText("结算");
                    Open_Barn("PLASTIC");
                } else {
                  /*  b_suliao=false;
                    imgSuliao.setImageResource(R.mipmap.suliao);*/
                }
                break;
            case R.id.lin_number:
                break;
            case R.id.submit:
                String zhuangtai = submit.getText().toString();
                if ("返回主页".equals(zhuangtai)) {
                    Sync_net();
                } else {
                    CloseDoor();
                }
                break;
        }
    }

    public static final String ACTION_UPDATEUI = "action.updateUI";
    public static final String ACTION_BACK = "action.backUI";

    //自动更新轮播图
    public class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            CloseDoor();
        }

    }

    public class BackBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TLog.log("xuxinyiback");
            Intent intent1 = new Intent(RecoverActivity.this, MainActivity.class);
            onSubscribe.unsubscribe();
            timecount.cancel();
            startActivity(intent1);
            finish();
        }
    }

    public void CloseDoor() {
        APIWrapper.getInstance().querClose("Bearer " + token, Constant.MAC)
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_no_main(RecoverActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            Toudi_State();
                        } else if (status == 300) {

                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                    }
                });
    }

    public void Toudi_State() {
        Intent intent = new Intent(RecoverActivity.this, BalanceActivity.class);
        String feizhi_jin = feizhiNumber.getText().toString();
        String sub_feizhi = feizhi_jin.substring(0, feizhi_jin.length() - 2);
        String pingzi_ge = pingziNumber.getText().toString();
        String sub_pingzi = pingzi_ge.substring(0, pingzi_ge.length() - 1);
        if (b_yinliao == true && b_dianzi != true && b_feizhi != true) {
            intent.putExtra("pingzi", sub_pingzi);
            intent.putExtra("BOTTLE", "BOTTLE");
            Message message0 = handler.obtainMessage();
            message0.what = handler_pingzi_close;
            handler.sendMessage(message0);
            state = true;
        } else if (b_yinliao != true && b_dianzi == true && b_feizhi != true) {
            intent.putExtra("ELECTRON", "ELECTRON");
            Message message1 = handler.obtainMessage();
            message1.what = handler_dianzi_close;
            handler.sendMessage(message1);
            state1 = true;
        } else if (b_feizhi == true && b_yinliao != true && b_dianzi != true) {
            intent.putExtra("feizhi", sub_feizhi);
            intent.putExtra("PAPER", "PAPER");
            Message message = handler.obtainMessage();
            message.what = handler_feizhi_close;
            handler.sendMessage(message);
            state2 = true;
        } else if (b_feizhi == true && b_yinliao == true && b_dianzi != true) {
            intent.putExtra("pingzi", sub_pingzi);
            intent.putExtra("BOTTLE", "BOTTLE");
            Message message0 = handler.obtainMessage();
            message0.what = handler_pingzi_close;
            handler.sendMessage(message0);
            state = true;
            intent.putExtra("feizhi", sub_feizhi);
            intent.putExtra("PAPER", "PAPER");
            Message message = handler.obtainMessage();
            message.what = handler_feizhi_close;
            handler.sendMessage(message);
            state2 = true;
        } else if (b_feizhi == true && b_yinliao != true && b_dianzi == true) {
            Message message0 = handler.obtainMessage();
            message0.what = handler_dianzi_close;
            handler.sendMessage(message0);
            state1 = true;
            intent.putExtra("feizhi", sub_feizhi);
            intent.putExtra("PAPER", "PAPER");
            Message message = handler.obtainMessage();
            message.what = handler_feizhi_close;
            handler.sendMessage(message);
            state2 = true;
        } else if (b_feizhi != true && b_yinliao == true && b_dianzi == true) {
            intent.putExtra("pingzi", sub_pingzi);
            intent.putExtra("BOTTLE", "BOTTLE");
            Message message0 = handler.obtainMessage();
            message0.what = handler_pingzi_close;
            handler.sendMessage(message0);
            state = true;
            intent.putExtra("ELECTRON", "ELECTRON");
            Message message1 = handler.obtainMessage();
            message1.what = handler_dianzi_close;
            handler.sendMessage(message1);
            state1 = true;
        } else if (b_feizhi == true && b_yinliao == true && b_dianzi == true) {
            intent.putExtra("pingzi", sub_pingzi);
            intent.putExtra("BOTTLE", "BOTTLE");
            Message message0 = handler.obtainMessage();
            message0.what = handler_pingzi_close;
            handler.sendMessage(message0);
            state = true;
            intent.putExtra("ELECTRON", "ELECTRON");
            Message message1 = handler.obtainMessage();
            message1.what = handler_dianzi_close;
            handler.sendMessage(message1);
            state1 = true;
            intent.putExtra("feizhi", sub_feizhi);
            intent.putExtra("PAPER", "PAPER");
            Message message = handler.obtainMessage();
            message.what = handler_feizhi_close;
            handler.sendMessage(message);
            state2 = true;
        }
        if (state == true && state1 != true && state2 != true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state == true && state1 == true && state2 != true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state == true && state1 != true && state2 == true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state != true && state1 == true && state2 == true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state != true && state1 != true && state2 == true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state == true && state1 == true && state2 == true) {
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (state != true && state1 == true && state2 != true) {
            try {
                //  result.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onSubscribe.unsubscribe();
        startmusicend();
        timecount.cancel();
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onSubscribe.unsubscribe();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(backReceiver);
        //backReceiver
    }
}
