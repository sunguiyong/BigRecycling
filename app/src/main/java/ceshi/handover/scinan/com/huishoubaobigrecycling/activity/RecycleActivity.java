package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leesche.yyyiotlib.entity.CmdResultEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.ResultBean;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.UnitPrice;
import ceshi.handover.scinan.com.huishoubaobigrecycling.control.ControlManagerImplMy;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Arith;

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
    private Gson gson;

    @Override
    public void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_bt: {
                finish();
                break;
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        overBt.setOnClickListener(this);
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

//    private void getDataFromBoard() {
//        /**
//         * 板子数据获取
//         */
//        ControlManagerImplMy.getInstance(this).addControlCallBack(new ControlManagerImplMy.ControlCallBack() {
//            @Override
//            public void onResult(final String result) {
//                CmdResultEntity cmdResultEntity = gson.fromJson(result, CmdResultEntity.class);
//                String value = cmdResultEntity.getValue();
//                switch (cmdResultEntity.getBox_code()) {
//                    case 1: {
//                        if (cmdResultEntity.getFunc_code() == 101 && cmdResultEntity.getValue() != null) {
//                            ResultBean.pingzi = value;
//                            pingziNum++;
//                            Log.d("瓶子个数", pingziNum + "个");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    pingziNumber.setText(pingziNum + "个");
//                                    pingzisum.setText(pingziNum * UnitPrice.pingzi + "");//瓶子小计
//                                    if (pingzisum.getText().toString() != null) {
//                                        sumScore = Integer.parseInt(pingzisum.getText().toString());
//                                    }
//                                }
//                            });
//                        }
//                        break;
//                    }
//                    case 2: {
//                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
//                            ResultBean.suliao = cmdResultEntity.getValue();
//                            double d = Double.parseDouble(ResultBean.suliao);
//                            Log.d("responseSuliao", Arith.div(d, 100.0, 3) + "aaa");
//                            suliaoTv.setText(Arith.div(d, 100.0, 3) + "");
//                            suliaosum.setText(Math.round(Arith.div(d, 100.0, 3) * UnitPrice.suliao) + "");
//                            if (suliaosum.getText().toString() != null) {
//                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.suliao);
//                            }
//                        } else {
//                            suliaoTv.setText("0");
//                        }
//                        break;
//                    }
//                    case 3: {
//                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
//                            ResultBean.boli = cmdResultEntity.getValue();
//                            double d = Double.parseDouble(ResultBean.boli);
//                            Log.d("responseBoli", Arith.div(d, 100.0, 3) + "aaa");
//                            boliTv.setText(Arith.div(d, 100.0, 3) + "");
//                            bolisum.setText(Math.round(Arith.div(d, 100.0, 3) * UnitPrice.boli) + "");
//                            if (bolisum.getText().toString() != null) {
//                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.boli);
//                            }
//                        } else {
//                            boliTv.setText("0");
//                        }
//                        break;
//                    }
//                    case 4: {
//                        if (cmdResultEntity.getFunc_code() == 103 && cmdResultEntity.getValue().length() > 0) {
//                            ResultBean.feizhi = cmdResultEntity.getValue();
//                            double d = Double.parseDouble(ResultBean.feizhi);
//                            Log.d("responseFeizhi", Arith.div(d, 100.0, 3) + "aaa");
//                            zhileiTv.setText(Arith.div(d, 100.0, 3) + "");
//                            feizhisum.setText(Math.round(Arith.div(d, 100.0, 3) * UnitPrice.zhilei) + "");
//                            if (feizhisum.getText().toString() != null) {
//                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.zhilei);
//                            }
//                        } else {
//                            zhileiTv.setText("0");
//                        }
//                        break;
//                    }
//                    case 5: {
//                        if (cmdResultEntity.getFunc_code() == 103 && !cmdResultEntity.getValue().equals("")) {
//                            ResultBean.yiwu = cmdResultEntity.getValue();
//                            double d = Double.parseDouble(ResultBean.yiwu);
//                            Log.d("responseYiwu", Arith.div(d, 100.0, 3) + "aaa");
//                            yiwuTv.setText(Arith.div(d, 100.0, 3) + "");
//                            yiwusum.setText(Math.round(Arith.div(d, 100.0, 3) * UnitPrice.yiwu) + "");
//                            if (yiwusum.getText().toString() != null) {
//                                sumScore = sumScore + Math.round(Arith.div(d, 100.0, 3) * UnitPrice.yiwu);
//                            }
//                        } else {
//                            yiwuTv.setText("0");
//                        }
//                        break;
//                    }
//                    default:
//                        break;
//                }
//                sumTv.setText(sumScore + "");
//            }
//
//            @Override
//            public void onIcResult(String icResult) {
//
//            }
//        });
//    }

}
