package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.companyname_tv)
    TextView companyTv;
    SharedPreferences preferences;
    private int role;
    public static RoleChooseActivity instance;

    @Override
    public int layoutView() {
        return R.layout.activity_role_choose;
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        hideBottomUIMenu();
        instance = this;
        role = getIntent().getIntExtra("role", 0);
        if (role == 1) {
            recycleBt.setVisibility(View.GONE);
        } else if (role == 3) {
            testBt.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initListener() {
        super.initListener();
        normalBt.setOnClickListener(this);
        recycleBt.setOnClickListener(this);
        testBt.setOnClickListener(this);
        preferences = getSharedPreferences("info", MODE_PRIVATE);
        companyTv.setText("公司：" + preferences.getString("group_name", ""));
        phoneTv.setText("电话：" + preferences.getString("phone", ""));
        areaTv.setText("地址：" + preferences.getString("address", ""));
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
