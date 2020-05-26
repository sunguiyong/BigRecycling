package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;

import butterknife.BindView;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.activity.getdata.DataFromServer;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;

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
    private Gson gson;

    @Override
    public void init() {
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
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
        phoneTv.setText("电话：" + preferences.getString("phone", ""));
        areaTv.setText("地址：" + preferences.getString("address", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_bt: {
                finish();
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
                    DataFromServer.postDataWarning(Integer.parseInt(preferences.getString("group_id", "")), SaveData.deviceId, cmdResultEntity.getBox_code(), new DataFromServer.StatusCallBack() {
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
}
