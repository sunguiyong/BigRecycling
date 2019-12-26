package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;

public class TestFunctionActivity extends BaseActivity implements View.OnClickListener {
    int x = R.layout.activity_test_function;
    @BindView(R.id.suliaoopen)
    Button suliaoopen;
    @BindView(R.id.suliaoclose)
    Button suliaoclose;
    @BindView(R.id.suliaohuishou)
    Button suliaohuishou;
    @BindView(R.id.suliaozhongliang)
    Button suliaozhongliang;
    @BindView(R.id.suliaodistance)
    Button suliaodistance;
    @BindView(R.id.suliaowenshidu)
    Button suliaowenshidu;
    @BindView(R.id.boliopen)
    Button boliopen;
    @BindView(R.id.boliclose)
    Button boliclose;
    @BindView(R.id.bolihuishou)
    Button bolihuishou;
    @BindView(R.id.bolizhongliang)
    Button bolizhongliang;
    @BindView(R.id.bolidistance)
    Button bolidistance;
    @BindView(R.id.boliwenshidu)
    Button boliwenshidu;
    @BindView(R.id.ylopen)
    Button ylopen;
    @BindView(R.id.ylclose)
    Button ylclose;
    @BindView(R.id.ylhuishou)
    Button ylhuishou;
    @BindView(R.id.ylzhongliang)
    Button ylzhongliang;
    @BindView(R.id.yldistance)
    Button yldistance;
    @BindView(R.id.ylwenshidu)
    Button ylwenshidu;
    @BindView(R.id.zopen)
    Button zopen;
    @BindView(R.id.zclose)
    Button zclose;
    @BindView(R.id.zhuishou)
    Button zhuishou;
    @BindView(R.id.zzhongliang)
    Button zzhongliang;
    @BindView(R.id.zdistance)
    Button zdistance;
    @BindView(R.id.zwenshidu)
    Button zwenshidu;
    @BindView(R.id.ywopen)
    Button ywopen;
    @BindView(R.id.ywclose)
    Button ywclose;
    @BindView(R.id.ywhuishou)
    Button ywhuishou;
    @BindView(R.id.ywzhongliang)
    Button ywzhongliang;
    @BindView(R.id.ywdistance)
    Button ywdistance;
    @BindView(R.id.ywwenshidu)
    Button ywwenshidu;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.systemrestart)
    Button systemrestart;
    @BindView(R.id.apprestart)
    Button apprestart;
    @BindView(R.id.ggopen)
    Button ggopen;
    @BindView(R.id.ggclose)
    Button ggclose;
    @BindView(R.id.neidengopen)
    Button neidengopen;
    @BindView(R.id.neidengclose)
    Button neidengclose;
    @BindView(R.id.result_tv)
    TextView resultTv;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ylopen: {
                uploadCmdToPort(1, 1, 1, "饮料瓶");
                break;
            }
            case R.id.ylhuishou: {
                uploadCmdToPort(1, 302, 1, "饮料回收");
                break;
            }
            case R.id.yldistance: {
                uploadCmdToPort(1, 102, 0, "距离");
                break;
            }
            case R.id.ylwenshidu: {
                uploadCmdToPort(1, 301, 1, "温湿度");
                break;
            }
            case R.id.ggopen: {
                uploadCmdToPort(1, 9, 1, "柜外灯");
                break;
            }
            case R.id.ggclose: {
                uploadCmdToPort(1, 9, 0, "柜外灯");
                break;
            }
            case R.id.apprestart: {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
                        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(LaunchIntent);
                    }
                }, 100);// 1秒钟后重启应用
                break;
            }
            case R.id.suliaoopen: {
                uploadCmdToPort(2, 1, 1, "塑料开门");
                break;
            }
            case R.id.suliaoclose: {
                uploadCmdToPort(2, 1002, 0, "塑料关门");
                break;
            }
            case R.id.suliaodistance: {
                uploadCmdToPort(2, 102, 0, "塑料距离");
                break;
            }
            case R.id.suliaozhongliang: {
                uploadCmdToPort(2, 103, 0, "塑料称重");
                break;
            }
            case R.id.suliaowenshidu: {
                uploadCmdToPort(2, 301, 1, "塑料温湿度");
                break;
            }
            case R.id.suliaohuishou: {
                uploadCmdToPort(2, 302, 1, "塑料回收");
                break;
            }
            case R.id.boliopen: {
                uploadCmdToPort(3, 1, 1, "玻璃开门");
                break;
            }
            case R.id.boliclose: {
                uploadCmdToPort(3, 1003, 0, "玻璃关门");
                break;
            }
            case R.id.bolidistance: {
                uploadCmdToPort(3, 102, 0, "玻璃距离");
                break;
            }
            case R.id.bolizhongliang: {
                uploadCmdToPort(3, 103, 0, "玻璃重量");
                break;
            }
            case R.id.boliwenshidu: {
                uploadCmdToPort(3, 301, 1, "玻璃温湿度");
                break;
            }
            case R.id.bolihuishou: {
                uploadCmdToPort(3, 302, 1, "玻璃回收");
                break;
            }
            case R.id.zopen: {
                uploadCmdToPort(4, 1, 1, "纸张开门");
                break;
            }
            case R.id.zclose: {
                uploadCmdToPort(4, 1004, 0, "纸张关门");
                break;
            }
            case R.id.zdistance: {
                uploadCmdToPort(4, 102, 0, "纸张距离");
                break;
            }
            case R.id.zhuishou: {
                uploadCmdToPort(4, 302, 1, "纸张回收");
                break;
            }
            case R.id.zzhongliang: {
                uploadCmdToPort(4, 103, 0, "纸张重量");
                break;
            }
            case R.id.ywopen: {
                uploadCmdToPort(5, 1, 1, "衣物开门");
                break;
            }
            case R.id.ywclose: {
                uploadCmdToPort(5, 1005, 0, "衣物关门");
                break;
            }
            case R.id.ywdistance: {
                uploadCmdToPort(5, 102, 0, "衣物距离");
                break;
            }
            case R.id.ywzhongliang: {
                uploadCmdToPort(5, 103, 0, "衣物重量");
                break;
            }

        }

    }

    @Override
    public int layoutView() {
        return R.layout.activity_test_function;
    }

    Gson gson = new Gson();

    @Override
    public void initListener() {
        ControlManagerImplMy.getInstance(this).addControlCallBack(new ControlManagerImplMy.ControlCallBack() {
            @Override
            public void onResult(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTv.setText(result);
                        CmdResultEntity cmdResultEntity = gson.fromJson(result, CmdResultEntity.class);
                    }
                });

            }

            @Override
            public void onIcResult(String icResult) {

            }
        });
        super.initListener();
        suliaoopen.setOnClickListener(this);
        suliaoclose.setOnClickListener(this);
        suliaohuishou.setOnClickListener(this);
        suliaozhongliang.setOnClickListener(this);
        suliaodistance.setOnClickListener(this);
        suliaowenshidu.setOnClickListener(this);

        boliopen.setOnClickListener(this);
        boliclose.setOnClickListener(this);
        bolihuishou.setOnClickListener(this);
        bolizhongliang.setOnClickListener(this);
        bolidistance.setOnClickListener(this);
        boliwenshidu.setOnClickListener(this);

        ylopen.setOnClickListener(this);
        ylclose.setOnClickListener(this);
        ylhuishou.setOnClickListener(this);
        ylzhongliang.setOnClickListener(this);
        yldistance.setOnClickListener(this);
        ylwenshidu.setOnClickListener(this);

        zopen.setOnClickListener(this);
        zclose.setOnClickListener(this);
        zhuishou.setOnClickListener(this);
        zzhongliang.setOnClickListener(this);
        zdistance.setOnClickListener(this);
        zwenshidu.setOnClickListener(this);

        ywopen.setOnClickListener(this);
        ywclose.setOnClickListener(this);
        ywhuishou.setOnClickListener(this);
        ywzhongliang.setOnClickListener(this);
        ywdistance.setOnClickListener(this);
        ywwenshidu.setOnClickListener(this);

        reset.setOnClickListener(this);
        systemrestart.setOnClickListener(this);
        apprestart.setOnClickListener(this);
        ggopen.setOnClickListener(this);
        ggclose.setOnClickListener(this);
        neidengopen.setOnClickListener(this);
        neidengclose.setOnClickListener(this);
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
}
