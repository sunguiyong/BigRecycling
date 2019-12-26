package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;

public class RoleChooseActivity extends BaseActivity implements View.OnClickListener {
    int x = R.layout.activity_role_choose;
    @BindView(R.id.normal_bt)
    Button normalBt;
    @BindView(R.id.recycle_bt)
    Button recycleBt;
    @BindView(R.id.test_bt)
    Button testBt;

    @Override
    public int layoutView() {
        return R.layout.activity_role_choose;
    }


    @Override
    public void initListener() {
        super.initListener();
        normalBt.setOnClickListener(this);
        recycleBt.setOnClickListener(this);
        testBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normal_bt: {
                Intent intent = new Intent(this, RecoverActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.recycle_bt: {
                Intent intent = new Intent(this, RecycleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.test_bt: {
                Intent intent = new Intent(this, TestFunctionActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
