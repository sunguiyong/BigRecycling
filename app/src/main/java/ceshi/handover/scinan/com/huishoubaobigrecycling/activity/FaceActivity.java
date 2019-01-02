package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.APIWrapper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxHelper;
import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RxSubscriber;
import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseActivity;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.BaseResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Face_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BitMapUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.FileUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.PictureUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.SharePreferenceUtils;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.TLog;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.ToastUtil;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.FotoapparatSwitcher;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static io.fotoapparat.log.Loggers.fileLogger;
import static io.fotoapparat.log.Loggers.logcat;
import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.lensPosition;
import static io.fotoapparat.result.transformer.SizeTransformers.scaled;

public class FaceActivity extends BaseActivity {
    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    TextView noPermission;
    @BindView(R.id.camera_view)
    CameraView cameraView;
    @BindView(R.id.rectanglesView)
    RectanglesView rectanglesView;
    @BindView(R.id.fanhui)
    LinearLayout fanhui;
    @BindView(R.id.time)
    TextView time;
    private boolean hasCameraPermission;
    private FotoapparatSwitcher fotoapparatSwitcher;
    private Fotoapparat frontFotoapparat;
    private Fotoapparat backFotoapparat;
    private Fotoapparat fotoapparat;
    private PhotoResult photoResult;
    boolean face_boolean;
    private TimeCount timecount;
    private String path_three;
    private boolean request=false;
    private String path;

    @Override
    public int layoutView() {
        return R.layout.activity_face;
    }
    @Override
    public void initview(Bundle savedInstanceState) {
        super.initview(savedInstanceState);
        timecount = new TimeCount(180000, 1000);
        face_boolean=true;
        hasCameraPermission = permissionsDelegate.hasCameraPermission();
        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }
        TLog.log(hasCameraPermission + "");
        frontFotoapparat = createFotoapparat(LensPosition.FRONT);
        fotoapparatSwitcher = FotoapparatSwitcher.withDefault(frontFotoapparat);
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(){
            @Override
            public void run() {
                super.run();
                timecount.start();
            }
        }.start();

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            time.setClickable(false);
            time.setText((millisUntilFinished / 1000)+"s");
            time.setTextColor(Color.parseColor("#ffffff"));
        }
        @Override
        public void onFinish() {
             Intent intent=new Intent(FaceActivity.this,MainActivity.class);
              timecount.cancel();
              startActivity(intent);
             finish();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            fotoapparatSwitcher.start();
            cameraView.setVisibility(View.VISIBLE);
        }
    }
    private Fotoapparat createFotoapparat(LensPosition position) {
        fotoapparat = Fotoapparat
                .with(this)
                .into(cameraView)
                .lensPosition(lensPosition(position))
                .frameProcessor(
                        FaceDetectorProcessor.with(this)
                                .listener(new FaceDetectorProcessor.OnFacesDetectedListener() {
                                    @Override
                                    public void onFacesDetected(List<Rectangle> faces) {
                                        Log.d("&&&", "Detected faces: " + faces.size());
                                      /*  if (faces.size() > 0) {*/
                                            if (face_boolean == true) {
                                                face_boolean = false;
                                                TLog.log("111111111");
                                                rectanglesView.setRectangles(faces);
                                                photoResult = frontFotoapparat.takePicture();
                                                photoResult.toBitmap(scaled(0.4f)).whenDone(new PendingResult.Callback<BitmapPhoto>() {
                                                    @SuppressLint("NewApi")
                                                    @Override
                                                    public void onResult(final BitmapPhoto bitmapPhoto) {
                                                        if (Objects.equals(bitmapPhoto, null)) {
                                                            return;
                                                        }
                                                         Bitmap bm = bitmapPhoto.bitmap;
                                                        Matrix matrix = new Matrix();
                                                        matrix.preRotate(270);
                                                        bm = Bitmap.createBitmap(bm ,0,0, bm .getWidth(), bm .getHeight(),matrix,true);
                                                        saveBitmap(bm);
                                                        if (!path_three.isEmpty()&&path_three!=null&&request==true){
                                                            request=false;
                                                            FaceNet(path_three);
                                                            path_three=null;
                                                        }
                                                    }
                                                });
                                            }
                                       // }
                                    }
                                })
                                .build()
                ).logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .build();
        return fotoapparat;
    }
    public void FaceNet(String path1){
        RequestBody params = RequestBody.create(MediaType.parse("text/plain"), "uploadimg");
        Map<String, RequestBody> p = new HashMap<>();
        RequestBody requestbody1 = RequestBody.create(MediaType.parse("image/jpg"), new File(path1));
        // p.put("name", params);
        p.put("file\"filename=\""+ "renlian" +".jpg", requestbody1);
        APIWrapper.getInstance().querFace("index/login_face",p)
                .compose(new RxHelper<BaseResult>("正在加载，请稍候").io_main(FaceActivity.this))
                .subscribe(new RxSubscriber<BaseResult>() {
                    @Override
                    public void _onNext(BaseResult response) {
                        int status = response.getStatus();
                        if (status == 200) {
                            Object message=response.getMessage();
                            Gson gson=new Gson();
                            face_boolean = false;
                            timecount.cancel();
                            ceshi.handover.scinan.com.huishoubaobigrecycling.load.FileUtil.deleteFile(path);
                            Face_Info info=gson.fromJson(gson.toJson(message),Face_Info.class);
                            String token=info.getToken();
                            SharePreferenceUtils.savetoken(token);
                            Intent intent = new Intent(FaceActivity.this, RecoverActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (status == 300) {
                            face_boolean = true;
                            ToastUtil.showShort(response.getMessage()+"");
                        }
                    }
                    @Override
                    public void _onError(String msg) {
                        TLog.log(msg + "");
                        face_boolean = true;
                      //  ToastUtil.showShort("网络错误");
                    }
                } );
    }
    public void saveBitmap( Bitmap bm) {
        // 直接存在Environment.getExternalStorageDirectory()下，能够找到图片，加上后面的目录就没图片了
        String mSdCardDir = Environment.getExternalStorageDirectory()+ "/data/local/";
        File dirFile = new File(mSdCardDir);
        if (!dirFile .exists()) {           //如果不存在，那就建立这个文件夹
            dirFile .mkdirs();
        }
      File  newfile = new File(dirFile,new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date())+".jpg");
        try {
            FileOutputStream out = new FileOutputStream(newfile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            path = newfile.toString();
            path_three = PictureUtil.compressImageTo200KB(this, path);
            request=true;
            out.flush();
            out.close();
            Log.i("xuxinyi", "已经保存");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            fotoapparatSwitcher.start();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        hasCameraPermission = permissionsDelegate.hasCameraPermission();
        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {
            fotoapparatSwitcher.stop();
        }
    }
    @OnClick(R.id.fanhui)
    public void onClick() {
        Intent intent=new Intent(FaceActivity.this,MainActivity.class);
        timecount.cancel();
        startActivity(intent);
        finish();
    }
}
