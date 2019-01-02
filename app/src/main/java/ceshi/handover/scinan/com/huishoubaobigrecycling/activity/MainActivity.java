package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Erweima;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LunBo_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Version_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.load.DownloadUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.Constant;
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
    @BindView(R.id.viewpager_head)
    CyclerViewPager viewpagerHead;
    @BindView(R.id.viewpager_foot)
    CyclerViewPager viewpagerFoot;
    List<Integer> list = new ArrayList<>();
    @BindView(R.id.erweima)
    ImageView erweima;
    List<LunBo_Info.MessageBean> message;
    List<LunBo_Info.MessageBean> message_one = new ArrayList<>();
    public static final String ACTION_UPDATEUI = "action.updateUI";
    @BindView(R.id.face)
    ImageView face;
    @BindView(R.id.version)
    TextView tv_version;
    private UpdateUIBroadcastReceiver broadcastReceiver;
    private String version;
    String res = "";

    @Override
    public int layoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        JPushInterface.init(this);
        //设置debug模式
        JPushInterface.setDebugMode(true);
        Create_File();
        // readFile();
        String[] perms = {Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(MainActivity.this, perms)) {
            // 已经申请过权限，做想做的事
        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "申请存储权限",
                    0, perms);
        }
        message = new ArrayList<>();
        String str_version=PakageUtil.getAppVersion(UiUtils.getContext());
        tv_version.setText("版本:v"+str_version);
        tv_version.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                finish();
                return true;
            }
        });
        Observable.interval(0, 1200, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.STOP))   //当Activity执行Onstop()方法是解除订阅关系
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Erweima_Net();
                    }
                });
        LunBO("2", message, new MyPagerAdapter(message), viewpagerHead);
        LunBO("3", message_one, new MyPagerAdapter(message_one), viewpagerFoot);
        JPushInterface.setAlias(MainActivity.this, 1, Constant.MAC);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATEUI);
        broadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
        version = PakageUtil.getAppVersion(this);
        Version();
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
                        ToastUtil.showShort("网络错误");
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
    @OnClick(R.id.face)
    public void onClick() {
        Intent intent = new Intent(MainActivity.this, FaceActivity.class);
        startActivity(intent);
        finish();
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

    public void Lgin_net() {

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
                        ToastUtil.showShort("网络错误");
                    }
                });
    }

    @Override
    public void initData() {
        super.initData();

    }

    public void LunBO(String style, final List<LunBo_Info.MessageBean> list, final PagerAdapter adapter, final ViewPager viewPager1) {
        APIWrapper.getInstance().queryOneLunBO(style)
                .compose(new RxHelper<LunBo_Info>("正在加载，请稍候").io_main(MainActivity.this))
                .subscribe(new RxSubscriber<LunBo_Info>() {
                    @Override
                    public void _onNext(LunBo_Info response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            List<LunBo_Info.MessageBean> messageBeans = response.getMessage();
                            for (int i = 0; i < messageBeans.size(); i++) {
                                LunBo_Info.MessageBean messageBean = new LunBo_Info.MessageBean();
                                String url = messageBeans.get(i).getUrl();
                                messageBean.setUrl(url);
                                list.add(messageBean);
                            }
                            viewPager1.setAdapter(adapter);
                        } else if (status == 300) {
                            ToastUtil.showShort(response.getMessage() + "");
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                        ToastUtil.showShort("网络错误");
                    }
                });
    }

    public class MyPagerAdapter extends PagerAdapter {
        // viewpager 页码数量
        public List<LunBo_Info.MessageBean> mDataList = new ArrayList<>();

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyPagerAdapter(List<LunBo_Info.MessageBean> dataList) {
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
            String url = mDataList.get(position).getUrl();
            Glide.with(MainActivity.this).load(Constant.YUMING + url).into(imageView);
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
        unregisterReceiver(broadcastReceiver);
    }
}
