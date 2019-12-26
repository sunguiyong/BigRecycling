package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
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
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result_info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.AreaAdapter;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
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

public class BalanceActivity extends BaseActivity {
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.jifen1)
    TextView jifen1;
    @BindView(R.id.time1)
    TextView time1;
    @BindView(R.id.xiao_jinshu)
    ImageView xiaoJinshu;
    @BindView(R.id.xiao_feizhi)
    ImageView xiaoFeizhi;
    @BindView(R.id.xiao_boli)
    ImageView xiaoBoli;
    @BindView(R.id.xiao_yinliao)
    ImageView xiaoYinliao;
    @BindView(R.id.xiao_yifu)
    ImageView xiaoYifu;
    @BindView(R.id.xxiao_dianzi)
    ImageView xxiaoDianzi;
    @BindView(R.id.xiao_suliao)
    ImageView xiaoSuliao;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.heji_money)
    TextView hejiMoney;
    @BindView(R.id.jiefen)
    TextView jiefen;
    @BindView(R.id.jiesu_toufang)
    Button jiesuToufang;
    @BindView(R.id.jixu_toufang)
    Button jixuToufang;
    private TimeCount timecount;
    List<request_result.ItemsBean> list = new ArrayList<>();
    List<request_result_info.MessageBean.PricesBean> list_message = new ArrayList<>();
    private AreaAdapter adapter;
    double sum_total;
    private String token;
    private MediaPlayer start_mediaPlayer;
    private Subscription onSubscribe;
    public static final String ACTION_BACK = "action.backUI";
    private BackBroadcastReceiver backReceiver;

    @Override
    public int layoutView() {
        return R.layout.activity_balance;
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        timecount = new TimeCount(180000, 1000);
        token = SharePreferenceUtils.gettoekn(UiUtils.getContext());
        String pingzi_number = getIntent().getStringExtra("pingzi");
        String feizhi_number = getIntent().getStringExtra("feizhi");
        String BOTTLE = getIntent().getStringExtra("BOTTLE");
        String PAPER = getIntent().getStringExtra("PAPER");
        String ELECTRON = getIntent().getStringExtra("ELECTRON");
        Info();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(ACTION_BACK);
        backReceiver = new BackBroadcastReceiver();
        registerReceiver(backReceiver, filter1);
        if (BOTTLE != null && PAPER == null && ELECTRON == null) {
            xiaoYinliao.setVisibility(View.VISIBLE);
            request_result.ItemsBean requestResult = new request_result.ItemsBean("BOTTLE", pingzi_number);
            list.add(requestResult);
        } else if (PAPER != null && BOTTLE == null && ELECTRON == null) {
            xiaoFeizhi.setVisibility(View.VISIBLE);
            request_result.ItemsBean requestResult = new request_result.ItemsBean("PAPER", feizhi_number);
            list.add(requestResult);
        } else if (PAPER != null && BOTTLE != null && ELECTRON == null) {
            request_result.ItemsBean requestResult1 = new request_result.ItemsBean("BOTTLE", pingzi_number);
            xiaoFeizhi.setVisibility(View.VISIBLE);
            xiaoYinliao.setVisibility(View.VISIBLE);
            request_result.ItemsBean requestResult = new request_result.ItemsBean("PAPER", feizhi_number);
            list.add(requestResult);
            list.add(requestResult1);
        } else if (PAPER == null && BOTTLE == null && ELECTRON != null) {
            request_result.ItemsBean requestResult3 = new request_result.ItemsBean("ELECTRON", "0");
            list.add(requestResult3);
        } else if (PAPER == null && BOTTLE != null && ELECTRON != null) {
            request_result.ItemsBean requestResult3 = new request_result.ItemsBean("ELECTRON", "0");
            list.add(requestResult3);
            request_result.ItemsBean requestResult1 = new request_result.ItemsBean("BOTTLE", pingzi_number);
            xiaoYinliao.setVisibility(View.VISIBLE);
            list.add(requestResult1);
        } else if (PAPER != null && BOTTLE == null && ELECTRON != null) {
            request_result.ItemsBean requestResult3 = new request_result.ItemsBean("ELECTRON", "0");
            list.add(requestResult3);
            xiaoYinliao.setVisibility(View.VISIBLE);
            request_result.ItemsBean requestResult = new request_result.ItemsBean("PAPER", feizhi_number);
            list.add(requestResult);
        } else if (PAPER != null && BOTTLE != null && ELECTRON != null) {
            request_result.ItemsBean requestResult3 = new request_result.ItemsBean("ELECTRON", "0");
            list.add(requestResult3);
            xiaoYinliao.setVisibility(View.VISIBLE);
            request_result.ItemsBean requestResult = new request_result.ItemsBean("PAPER", feizhi_number);
            request_result.ItemsBean requestResult1 = new request_result.ItemsBean("BOTTLE", pingzi_number);
            xiaoYinliao.setVisibility(View.VISIBLE);
            list.add(requestResult);
            list.add(requestResult1);
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
        request_result requestResult = new request_result(Constant.MAC, list);
        APIWrapper.getInstance().querRequest("Bearer " + token, requestResult)
                .compose(new RxHelper<request_result_info>("正在加载，请稍候").io_no_main(BalanceActivity.this))
                .subscribe(new RxSubscriber<request_result_info>() {
                    @Override
                    public void _onNext(request_result_info response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            List<request_result_info.MessageBean.PricesBean> message = response.getMessage().getPrices();
                            for (int i = 0; i < message.size(); i++) {
                                request_result_info.MessageBean.PricesBean pricesBean = new request_result_info.MessageBean.PricesBean();
                                String name = message.get(i).getName();
                                pricesBean.setName(name);
                                double total = message.get(i).getTotal();
                                sum_total += total;
                                pricesBean.setTotal(total);
                                double count = message.get(i).getCount();
                                pricesBean.setCount(count);
                                double unit_price = message.get(i).getUnit_price();
                                pricesBean.setUnit_price(unit_price);
                                list_message.add(pricesBean);
                            }
                            int points = response.getMessage().getPoints();
                            adapter = new AreaAdapter(list_message);
                            // recycler.addItemDecoration(new ListViewDecoration());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UiUtils.getContext(), LinearLayoutManager.VERTICAL, false);
                            recycle.setLayoutManager(linearLayoutManager);
                            recycle.setAdapter(adapter);
                            hejiMoney.setText(sum_total + "元");
                            jiefen.setText(points + "积分");
                            adapter.notifyDataSetChanged();
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

    public class BackBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(BalanceActivity.this, MainActivity.class);
            timecount.cancel();
            onSubscribe.unsubscribe();
            startActivity(intent1);
            finish();
        }

    }

    public void Info() {
        APIWrapper.getInstance().querUserInfo("Bearer " + token)
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(BalanceActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        TLog.log(status + "");
                        if (status == 200) {
                            Object message = response.getMessage();
                            TLog.log(message + "");
                            if (message != null) {
                                Gson gson = new Gson();
                                User_info1 user_info = gson.fromJson(gson.toJson(message), User_info1.class);
                                String mobile = user_info.getMobile();
                                JPushInterface.setAlias(BalanceActivity.this, 1, mobile);
                                String nick = user_info.getNick();
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

    public void Sync_net() {
        APIWrapper.getInstance().querSync("Bearer " + token, "Back_TO")
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(BalanceActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        TLog.log(status + "");
                        if (status == 200) {
                            Intent intent1 = new Intent(BalanceActivity.this, MainActivity.class);
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

    private void DeviceStates_Net(String mac) {
        APIWrapper.getInstance().querDeviceState("Bearer " + token, mac)
                .compose(new RxHelper<DeviceState_Info>("正在加载，请稍候").io_no_main(BalanceActivity.this))
                .subscribe(new RxSubscriber<DeviceState_Info>() {
                    @Override
                    public void _onNext(DeviceState_Info response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            String page = response.getMessage().getPage();
                            if (page != null) {
                                if ("RECOVERY".equals(page)) {
                                    onSubscribe.unsubscribe();
                                    Intent intent = new Intent(BalanceActivity.this, RecoverActivity.class);
                                    intent.putExtra("RECOVERY", "RECOVERY");
                                    startActivity(intent);
                                    finish();
                                }
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

    @Override
    public void initData() {
        super.initData();
        new Thread() {
            @Override
            public void run() {
                super.run();
                timecount.start();
            }
        }.start();
    }

    private void startmusicopen() {
        try {
            start_mediaPlayer = new MediaPlayer().create(BalanceActivity.this, R.raw.ganxienishiyong);
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

    @OnClick({R.id.jiesu_toufang, R.id.jixu_toufang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiesu_toufang:
                startmusicopen();
                APIWrapper.getInstance().querJieSu("Bearer " + token, Constant.MAC)
                        .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_no_main(BalanceActivity.this))
                        .subscribe(new RxSubscriber<BaseResult>() {
                            @Override
                            public void _onNext(BaseResult response) {
                                int status = response.getStatus();
                                if (status == 200) {
                                    Sync_net();
                                } else if (status == 300) {
                                    ToastUtil.showShort(response.getMessage() + "");
                                }
                            }

                            @Override
                            public void _onError(String msg) {
                                TLog.log(msg + "");
                            }
                        });
                break;
            case R.id.jixu_toufang:
                Intent intent_toufang = new Intent(BalanceActivity.this, RecoverActivity.class);
                timecount.cancel();
                onSubscribe.unsubscribe();
                startActivity(intent_toufang);
                finish();
                break;
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (time1 != null) {
                time1.setClickable(false);
                time1.setText((millisUntilFinished / 1000) + "s");
                time1.setTextColor(Color.parseColor("#ffffff"));
            }
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(BalanceActivity.this, MainActivity.class);
            onSubscribe.unsubscribe();
            timecount.cancel();
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onSubscribe.unsubscribe();
        onSubscribe.unsubscribe();
        unregisterReceiver(backReceiver);
    }
}
