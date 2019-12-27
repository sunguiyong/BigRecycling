package ceshi.handover.scinan.com.huishoubaobigrecycling.control;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.deemons.serialportlib.SerialPort;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leesche.yyyiotlib.common.IotConstants;
import com.leesche.yyyiotlib.entity.CmdEntity;
import com.leesche.yyyiotlib.entity.CmdResultEntity;
import com.leesche.yyyiotlib.entity.MessageEntity;
import com.leesche.yyyiotlib.serial.CmdConstants;
import com.leesche.yyyiotlib.serial.CmdHelper;
import com.leesche.yyyiotlib.serial.manager.IControlManager;
import com.leesche.yyyiotlib.socket.manager.ClientConnectManager;
import com.leesche.yyyiotlib.socket.manager.SessionManager;
import com.leesche.yyyiotlib.util.DataConvertUtil;
import com.leesche.yyyiotlib.util.LogUtil;
import com.leesche.yyyiotlib.util.ThreadManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import ceshi.handover.scinan.com.huishoubaobigrecycling.entity.SaveData;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DataConvertUtilMy;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ControlManagerImplMy implements IControlManager {

    public static final String TAG = ControlManagerImplMy.class.getSimpleName();
    public static int RECYCLING = 0;
    public static int SALE = 1;
    public static int IC_CARD = 2;
    private SerialPort mSerialPort;
    private SerialPort mSerialPortIC;
    private boolean isInterrupted;
    private Disposable mReceiveDisposable;
    private ObservableEmitter<byte[]> mEmitter;
    private Disposable mSendDisposable;
    private ControlManagerImplMy.ControlCallBack controlCallBack;
    private Thread mSaleReceiverThread;
    LocalBroadcastManager mLocalBroadcastManager;
    private static AtomicInteger count = new AtomicInteger(0);
    int send_serial_num = 0;
    byte[] sendBytes = null;
    private Context context;
    Gson gson = new Gson();
    private static ControlManagerImplMy controlManager;

    private int func_code_save;
    //广播
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.leesche.mina.success")) {
                ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(IotConstants.SERIAL_NUM, (byte) -124, 60928, CmdConstants.IDENTITY_CODE_FLAG);
                ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
            }

            if (intent.getAction().equals("com.leesche.mina.msg")) {
                byte[] receiverData = intent.getByteArrayExtra("msg");
                int socket_id = intent.getIntExtra("socket_id", 0);
                ControlManagerImplMy.count.incrementAndGet();
                ControlManagerImplMy.this.send_serial_num = ControlManagerImplMy.count.get();
                switch (socket_id) {
                    case 6001:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6004, receiverData);
                        break;
                    case 6006:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6009, receiverData);
                        break;
                    case 6011:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6014, receiverData);
                        break;
                    case 6016:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6019, receiverData);
                        break;
                    case 6021:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6024, receiverData);
                        break;
                    case 6026:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6029, receiverData);
                        break;
                    case 6031:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6034, receiverData);
                        break;
                    case 6036:
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, (byte) 2, 6039, receiverData);
                }

                ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
            }

            if (intent.getAction().equals("com.leesche.mina.sendmsg")) {
                ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(IotConstants.SERIAL_NUM, (byte) -124, 60928, CmdConstants.IDENTITY_CODE_FLAG);
                ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
            }

            if (intent.getAction().equals("com.leesche.mina.close")) {
                if (IotConstants.IS_SEND_REPOSE_CMD) {
                    ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(IotConstants.SERIAL_NUM, (byte) -124, 60928, CmdConstants.IDENTITY_CODE_FLAG);
                    ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                }

                IotConstants.IS_SEND_REPOSE_CMD = true;
            }

        }
    };

    public ControlManagerImplMy(Context context) {
        this.context = context;
        this.registerLocalBroadcastReceiver(context);
    }

    public static ControlManagerImplMy getInstance(Context context) {
        if (controlManager == null) {
            controlManager = new ControlManagerImplMy(context);
        }
        return controlManager;
    }

    public void addControlCallBack(ControlManagerImplMy.ControlCallBack controlCallBack) {
        this.controlCallBack = controlCallBack;
    }

    private void registerLocalBroadcastReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.leesche.mina.success");
        filter.addAction("com.leesche.mina.msg");
        filter.addAction("com.leesche.mina.sendmsg");
        filter.addAction("com.leesche.mina.close");
        filter.setPriority(1000);
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
        this.mLocalBroadcastManager.registerReceiver(this.mReceiver, filter);
    }

    /**
     * 打开串口
     *
     * @param mPath
     * @param mBaudRate
     * @return
     */
    public boolean open(String mPath, int mBaudRate) {
        try {
            SerialPort.setSuPath("/system/bin/su");
            this.mSerialPort = new SerialPort(new File(mPath), mBaudRate, 0, 8, 1, 0);
        } catch (IOException var4) {
            var4.printStackTrace();
            Log.e(TAG, "打开失败！请尝试其它串口！");
        } catch (SecurityException var5) {
            var5.printStackTrace();
            return false;
        }

        if (this.mSerialPort != null) {
            this.isInterrupted = false;
            this.onReceiveSubscribe();
            this.onSendSubscribe();
            Log.e(TAG, "打开串口成功！");
        }

        return this.mSerialPort != null;
    }

    /**
     * 判断IC读卡串口是否开启成功
     *
     * @param mPath
     * @param mBaudRate
     * @return
     */
    public boolean openIC(String mPath, int mBaudRate) {
        try {
            this.mSerialPortIC = new SerialPort(new File(mPath), mBaudRate, 0, 8, 1, 0);
        } catch (IOException var4) {
            var4.printStackTrace();
            Log.e(TAG, "打开IC串口失败！请尝试其它串口！");
            return false;
        } catch (SecurityException var5) {
            var5.printStackTrace();
            return false;
        }

        if (this.mSerialPortIC != null) {
            this.isInterrupted = false;
            this.startReceiveThread();
            Log.e(TAG, "打开IC串口成功！");
        }

        return this.mSerialPortIC != null;
    }

    //固件版本文件路径
    private String filePath = "/storage/sdcard0/Download" + "/STM32L073_RTT_Example.bin";

    /**
     * 更新固件版本
     */
    public void sendUpdate() {
        Log.d("更新文件路径为：", filePath);
        Xmodem xmodem = new Xmodem(mSerialPort.getInputStream(), mSerialPort.getOutputStream());
        xmodem.send(filePath);
    }

    /**
     * IC卡读取线程
     */
    private void startReceiveThread() {
        if (this.mSaleReceiverThread == null) {
            this.mSaleReceiverThread = new ControlManagerImplMy.SaleReceiveThread();
        }

        this.mSaleReceiverThread.start();
    }

    /**
     * 读卡回调方法(废弃)
     *
     * @param resultBytes
     */
    private void handlerIcCardCallBack(byte[] resultBytes) {
        byte[] bytes = new byte[]{resultBytes[1], resultBytes[2], resultBytes[3], resultBytes[4]};
        int ID = DataConvertUtil.bytesToIntBig(bytes, 0);
        this.controlCallBack.onIcResult(ID + "");
    }

    private void onSendSubscribe() {
        //Observable被观察者
        this.mSendDisposable = Observable.create(new ObservableOnSubscribe<byte[]>() {
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                ControlManagerImplMy.this.mEmitter = emitter;
            }
        }).filter(new Predicate<byte[]>() {
            public boolean test(byte[] s) throws Exception {
                return s != null;
            }
        }).doOnNext(new Consumer<byte[]>() {
            public void accept(byte[] sendData) throws Exception {
                ControlManagerImplMy.this.sendDataBySubPackage(sendData);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<byte[]>() {
            public void accept(byte[] s) throws Exception {
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    private void onReceiveSubscribe() {

        this.mReceiveDisposable = Flowable.create(new FlowableOnSubscribe<byte[]>() {
            //subscribe订阅
            public void subscribe(FlowableEmitter<byte[]> emitter) throws Exception {
                InputStream mInputStream = ControlManagerImplMy.this.mSerialPort.getInputStream();
                int maxLength = 100;
                byte[] buffer = new byte[maxLength];
                int available = 0;
                int currentLength = 0;

                //升级用
                byte[] buffer1 = new byte[2];

                //!ControlManagerImplMy.this.isInterrupted &&
                for (byte headerLength = 10; ControlManagerImplMy.this.mSerialPort != null && mInputStream != null; SystemClock.sleep(2L)) {
                    synchronized (this) {
                        try {
                            int availablex = mInputStream.available();
                            if (availablex > 0) {
                                if (availablex > maxLength - currentLength) {
                                    availablex = maxLength - currentLength;
                                }
                                //读数据
                                mInputStream.read(buffer, currentLength, availablex);
                                currentLength += availablex;
                                Log.e("CmdHelper-----", DataConvertUtil.byte2HexStr(buffer));

                            }
                        } catch (Exception var14) {
                            var14.printStackTrace();
                        }

                        int cursor = 0;
                        //67是十进制，转十六进制是0x43
                        //从设备下位机发送C（xmodem协议），即可开始发送更新数据
                        if (buffer[cursor] == 67) {

//                            sendUpdate();
                            return;
                        }
                        label85:
                        while (true) {

                            while (true) {
                                if (currentLength < headerLength) {
                                    break label85;
                                }


                                //16进制0x71~76  7E   代表
                                if (buffer[cursor] == 113 || buffer[cursor] == 114 || buffer[cursor] == 115 || buffer[cursor] == 116 || buffer[cursor] == 117 || buffer[cursor] == 118 || buffer[cursor] == 126) {
                                    int contentLength = CmdHelper.getInstance().parseLen(buffer, cursor);

                                    // 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
                                    if (contentLength <= 0 || contentLength > maxLength) {
                                        currentLength = 0;
                                        break label85;
                                    }

                                    // 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
                                    if (currentLength < contentLength) {
                                        break label85;
                                    }

                                    // 一个完整包即产生
                                    byte[] realPackBytes = new byte[contentLength];
                                    System.arraycopy(buffer, cursor, realPackBytes, 0, contentLength);
                                    emitter.onNext(realPackBytes);
                                    currentLength -= contentLength;
                                    cursor += contentLength;

//                                    Log.d("完整包", realPackBytes + "");

                                } else {
                                    --currentLength;
                                    ++cursor;
                                    continue;//
                                }
                            }
                        }
                        // 残留字节移到缓冲区首
                        if (currentLength > 0 && cursor > 0) {
                            System.arraycopy(buffer, cursor, buffer, 0, currentLength);
                        }
                    }
                    SystemClock.sleep(2);
                }

                ControlManagerImplMy.this.close();
            }
        }, BackpressureStrategy.MISSING)
                .retry()
                .map(new Function<byte[], MessageEntity>() {
                    public MessageEntity apply(byte[] bytes) throws Exception {
                        return CmdHelper.getInstance().transToReadableData(bytes);
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                //需要14位十六进制数据
                .subscribe(new Consumer<MessageEntity>() {
                    public void accept(MessageEntity messageEntity) throws Exception {
                        ControlManagerImplMy.this.handlerResultFormPort(messageEntity);
                        Log.d("messageEntity.func", messageEntity.getFunc_code() + "");
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Throwable", "执行了");
                        throwable.printStackTrace();
                    }
                });
    }

    private void onReceived() {

        mReceiveDisposable = Flowable.create(new FlowableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(FlowableEmitter<byte[]> emitter) throws Exception {
                InputStream mInputStream = mSerialPort.getInputStream();
                // 定义一个包的最大长度
                int maxLength = 2048;
                byte[] buffer = new byte[maxLength];
                // 每次收到实际长度
                int available = 0;
                // 当前已经收到包的总长度
                int currentLength = 0;
                // 协议头长度10个字节
                int headerLength = 10;

                while (!isInterrupted && mSerialPort != null && mInputStream != null) {
                    synchronized (this) {
                        try {
                            available = mInputStream.available();
                            if (available > 0) {
                                // 防止超出数组最大长度导致溢出
                                if (available > maxLength - currentLength) {
                                    available = maxLength - currentLength;
                                }
                                mInputStream.read(buffer, currentLength, available);
                                currentLength += available;
                                Log.e("CmdHelper-----", DataConvertUtil.byte2HexStr(buffer));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        int cursor = 0;
                        // 如果当前收到包大于头的长度，则解析当前包
                        while (currentLength >= headerLength) {
                            // 取到头部第一个字节
                            if (buffer[cursor] == 0x71
                                    || buffer[cursor] == 0x72
                                    || buffer[cursor] == 0x73
                                    || buffer[cursor] == 0x74
                                    || buffer[cursor] == 0x75
                                    || buffer[cursor] == 0x76
                                    || buffer[cursor] == 0x7E) {
                                int contentLength = CmdHelper.getInstance().parseLen(buffer, cursor);
                                // 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
                                if (contentLength <= 0 || contentLength > maxLength) {
                                    currentLength = 0;
                                    break;
                                }

                                // 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
                                int factPackLen = contentLength;
                                if (currentLength < contentLength) {
                                    break;
                                }

                                // 一个完整包即产生
                                byte[] realPackBytes = new byte[factPackLen];
                                System.arraycopy(buffer, cursor, realPackBytes, 0, factPackLen);
                                emitter.onNext(realPackBytes);
                                currentLength -= factPackLen;
                                cursor += factPackLen;
//                                Toast.makeText(context,realPackBytes.toString(),Toast.LENGTH_LONG);
                            } else {
                                --currentLength;
                                ++cursor;
                                continue;
                            }
                        }
                        // 残留字节移到缓冲区首
                        if (currentLength > 0 && cursor > 0) {
                            System.arraycopy(buffer, cursor, buffer, 0, currentLength);
                        }
                    }
                    SystemClock.sleep(2);
                }
                close();
            }
        }, BackpressureStrategy.MISSING)
                .retry()
                .map(new Function<byte[], MessageEntity>() {
                    @Override
                    public MessageEntity apply(byte[] bytes) throws Exception {

                        return CmdHelper.getInstance().transToReadableData(bytes);
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MessageEntity>() {
                    @Override
                    public void accept(MessageEntity messageEntity) throws Exception {
//                        mView.addData(messageEntity);
                        ControlManagerImplMy.this.handlerResultFormPort(messageEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Throwable", "invoked");
                        throwable.printStackTrace();
                    }
                });
    }

    public void close() {
        this.isInterrupted = true;
        this.disposable(this.mReceiveDisposable);
        this.disposable(this.mSendDisposable);
        this.mEmitter = null;
        this.mSerialPort = null;
        this.mSerialPortIC = null;
        Log.d("串口关闭", "执行了");
    }

    public void sendMsg(byte[] sendBytes) {
        if (this.mEmitter != null) {
            this.mEmitter.onNext(sendBytes);
        } else {
            Log.e(TAG, "请先打开串口(回收机)！");
        }

    }

    public void onDestroy() {
        this.close();
    }

    private void disposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

    }

    private void sendDataBySubPackage(byte[] data) {
        int dataLength = data.length;
        if (dataLength > 1024) {
            int sendCount = 0;
            int send_counter = 0;

            while (sendCount < dataLength) {
                ++send_counter;
                byte[] sendBytes;
                if (sendCount + 1024 > dataLength) {
                    sendBytes = new byte[dataLength - sendCount];
                } else {
                    sendBytes = new byte[1024];
                }

                System.arraycopy(data, sendCount == 0 ? 0 : sendCount, sendBytes, 0, sendBytes.length);
                sendCount += sendBytes.length;
                Log.e("发送次数：", send_counter + "");
                if (send_counter % 2 == 0) {
                    LogUtil.i(TAG, "准备发送到从设备端消息(" + sendBytes.length + ")：" + DataConvertUtil.byte2HexStr(sendBytes));
                } else {
                    LogUtil.e(TAG, "准备发送到从设备端消息(" + sendBytes.length + ")：" + DataConvertUtil.byte2HexStr(sendBytes));
                }

                this.send(sendBytes);
                LogUtil.i(TAG, "本次已发送到从设备端消息长度(" + sendCount + ")");
            }
        } else {
            LogUtil.e(TAG, "准备发送到从设备端消息(" + data.length + ")：" + DataConvertUtil.byte2HexStr(data));
            this.send(data);
        }
    }

    public void send(byte[] bOutArray) {
        if (this.mSerialPort != null) {
            try {
                this.mSerialPort.getOutputStream().write(bOutArray);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }
    }

    //ttyS4
    public boolean linkToPort(int port_num) {
        if (port_num == RECYCLING) {
            return this.open("/dev/ttyS4", 9600);
        } else {
            ///dev/ttyS4
//            return this.open("/dev/ttyUSB11",9600);
            return port_num == IC_CARD ? this.openIC("/dev/ttyS3", 9600) : false;
        }
    }

    public boolean unlinkToPort() {
        this.close();
        SessionManager.getInstance().closeSession();
        this.mLocalBroadcastManager.unregisterReceiver(this.mReceiver);
        return true;
    }

    /**
     * String jsonCmd = ControlManagerImplMy.getInstance(getActivity()).testJsonCmdStr(boxCode
     * , funCode, status, name);
     *
     * @param port_num 传入的是RECYCLING==0
     * @param cmdStr   传入的是jsonCmd
     */
    public void sendCmdToPort(int port_num, String cmdStr) {
        if (port_num == RECYCLING) {
            this.handlerRecyclingSendCmd(cmdStr);
        }

    }

    /**
     * @param cmdStr 传入的是jsonCmd
     */
    private void handlerRecyclingSendCmd(String cmdStr) {
        count.incrementAndGet();//新值，加1后的值
        this.send_serial_num = count.get();//     * Gets the current value.
        //json字符串cmdStr转为CmdEntity对象
        CmdEntity cmdEntity = (CmdEntity) this.gson.fromJson(cmdStr, CmdEntity.class);

        func_code_save = cmdEntity.getFunc_code();

        Log.d("getFunc_code", cmdEntity.getFunc_code() + "");
        switch (cmdEntity.getFunc_code()) {
            case 1:
            case 2:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), //其实就是position
                        (byte) 2, 5603, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                this.send(this.sendBytes);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 13:
                //单次开启
            case 14:
                Log.d("单次开启5603", "invoked");
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num,
                        cmdEntity.getBox_code(), //柜子号
                        (byte) 2,
                        5603,
                        DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                this.send(this.sendBytes);
                break;
            //单次关闭
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
                Log.d("单次关闭", "invoked");
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 2, 5606, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                this.send(this.sendBytes);
                break;
            //压缩
            case 10:
                Log.d("压缩", "invoked");
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 2, 8059, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                this.send(this.sendBytes);
                break;
            case 100:
                Log.d("压缩释放", "invoked");
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num,
                        2,
                        (byte) 2, 8060,
                        DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                this.send(this.sendBytes);
                break;
            //开关风扇
            case 8:
                this.openAndCloseFan(cmdEntity);
                break;
            //开关灯
            case 9:
                this.openAndCloseLight(cmdEntity);
                break;
            //测距
            case 102:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 1, 8051, "");
                this.send(this.sendBytes);
                break;
            //重量
            case 103:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 1, 8052, "");
                this.send(this.sendBytes);
                break;
            //温湿度
            case 301:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 1, 8054, "");
                this.send(this.sendBytes);
                break;
            //开锁
            case 302:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, cmdEntity.getBox_code(), (byte) 1, 8021, "");
                this.send(this.sendBytes);
                break;
            //重启设备
            case 201:
                for (int i = 1; i < IotConstants.BOX_COUNT + 1; ++i) {
                    this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, i, (byte) 2, 5016, CmdConstants.DEVICE_RESET);
                    this.send(this.sendBytes);
                    SystemClock.sleep(5L);
                }

                return;
            //类型
            case 202:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, (byte) 1, 5007, "");
                this.send(this.sendBytes);
                break;
            //版本信息
            case 203:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, (byte) 1, 5006, "");
                this.send(this.sendBytes);
                break;
            case 999:
                this.sendBytes = CmdHelper.getInstance().transToRawData(this.send_serial_num, (byte) 1, 9999, "");
                this.send(this.sendBytes);
                break;
        }

    }

    private void openAndCloseFan(final CmdEntity cmdEntity) {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            public void run() {
                if (cmdEntity.getStatus() == 1) {
                    for (int i = 1; i < IotConstants.BOX_COUNT + 1; ++i) {
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8066, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                        ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                        int j = 0;

//                        while (j < 16000) {
//                            ++j;
//                            SystemClock.sleep(5L);
//                        }
                        SystemClock.sleep(100L);

                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8068, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                        ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                        SystemClock.sleep(200L);
                    }
                } else {
//                    ControlManagerImplMy.this.openAndCloseLight(cmdEntity);
                    for (int i = 1; i < IotConstants.BOX_COUNT + 1; ++i) {
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8067, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                        ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                        int j = 0;

//                        while (j < 16000) {
//                            ++j;
//                            SystemClock.sleep(5L);
//                        }
                        SystemClock.sleep(100L);

                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8069, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code()));
                        ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                        SystemClock.sleep(200L);
                    }
                }

            }
        });
    }

    /**
     * 开关灯
     *
     * @param cmdEntity
     */
    private void openAndCloseLight(final CmdEntity cmdEntity) {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            public void run() {
                for (int i = 1; i < 6; ++i) {
                    if (cmdEntity.getStatus() == 1) {
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8057, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code() - 5));
                    } else {
                        ControlManagerImplMy.this.sendBytes = CmdHelper.getInstance().transToRawData(ControlManagerImplMy.this.send_serial_num, i, (byte) 2, 8057, DataConvertUtil.intToBytesLittle(cmdEntity.getFunc_code() - 6));
                    }

                    ControlManagerImplMy.this.send(ControlManagerImplMy.this.sendBytes);
                    SystemClock.sleep(200L);
                }

            }
        });
    }

    private void handlerResultFormPort(MessageEntity messageEntity) {
        switch (messageEntity.getFunc_code()) {
            case -127://FF 255
                this.handlerRecruitFlag(messageEntity);
            case -126://254
            case -63:
            case -62:
            case 3:
            default:
                break;
            case 4:
                this.handlerUploadCmd(this.context, messageEntity);
        }

    }

    public String testJsonCmdStr(int box_code, int func_code, int status, String name) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("box_code", box_code);
        jsonObject.addProperty("func_code", func_code);
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("name", name);
        return jsonObject.toString();
    }

    public void handlerUploadCmd(Context context, MessageEntity messageEntity) {
        int identify_id = messageEntity.getIdentify_id();
        CmdResultEntity cmdResultEntity = null;
        switch (identify_id) {
            case 5024:
            case 6000:
            default:
                break;
            //开启成功
            case 5604:
                this.handlerResultToBack(messageEntity, 1, 1);
                Log.d("5604", "开启成功");
                break;
            //开启失败
            case 5605:
                this.handlerResultToBack(messageEntity, 1, 0);
                Log.d("5605", "开启失败");
                break;
            //关闭成功
            case 5607:
                this.handlerResultToBack(messageEntity, 0, 1);
                break;
            //关闭失败
            case 5608:
                this.handlerResultToBack(messageEntity, 0, 0);
                break;
            case 6001:
            case 6006:
            case 6011:
            case 6016:
            case 6021:
            case 6026:
            case 6031:
            case 6036:
                String[] configInfo = messageEntity.getContent().trim().split("\\|");
                if ("0".equals(configInfo[0])) {
                    ClientConnectManager.getInstance().connect(context, identify_id, configInfo[2], Integer.valueOf(configInfo[1]));
                    IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                }

                if ("1".equals(configInfo[0])) {
                }
                break;
            case 6003:
                SessionManager.getInstance().writeToServer(messageEntity, 6001);
                break;
            case 6005:
                SessionManager.getInstance().closeSessionById(6001);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6008:
                SessionManager.getInstance().writeToServer(messageEntity, 6006);
                break;
            case 6010:
                SessionManager.getInstance().closeSessionById(6006);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6013:
                SessionManager.getInstance().writeToServer(messageEntity, 6011);
                break;
            case 6015:
                SessionManager.getInstance().closeSessionById(6011);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6018:
                SessionManager.getInstance().writeToServer(messageEntity, 6016);
                break;
            case 6020:
                SessionManager.getInstance().closeSessionById(6016);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6023:
                SessionManager.getInstance().writeToServer(messageEntity, 6021);
                break;
            case 6025:
                SessionManager.getInstance().closeSessionById(6021);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6028:
                SessionManager.getInstance().writeToServer(messageEntity, 6026);
                break;
            case 6030:
                SessionManager.getInstance().closeSessionById(6026);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6033:
                SessionManager.getInstance().writeToServer(messageEntity, 6031);
                break;
            case 6035:
                SessionManager.getInstance().closeSessionById(6031);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 6038:
                SessionManager.getInstance().writeToServer(messageEntity, 6036);
                break;
            case 6040:
                SessionManager.getInstance().closeSessionById(6036);
                IotConstants.SERIAL_NUM = messageEntity.getSerial_num();
                break;
            case 8053:
                this.handlerOtherResultToBack(messageEntity, 101);
                break;
            case 8051:
                this.handlerOtherResultToBack(messageEntity, 102);
                break;
            case 8052:
                this.handlerOtherResultToBack(messageEntity, 103);
                break;
            case 8068:
                this.handlerOtherResultToBack(messageEntity, 8);
                break;
            //压缩
            case 8059:
                this.handlerResultToBack(messageEntity, 1, 1);
                break;
            //解压
            case 8060:
                this.handlerResultToBack(messageEntity, 0, 1);
                break;
            //温湿度
            case 8054: {
                this.handlerOtherResultToBack(messageEntity, 301);
                break;
            }
            //回收门开启成功
            case 8021: {
                this.handlerResultToBack(messageEntity, 1, 1);
                break;
            }
            //回收门开启失败
            case 8022: {
                this.handlerResultToBack(messageEntity, 1, 0);
                break;
            }
            //更新固件成功
            case 9999: {
                this.handlerResultToBack(messageEntity, 1, 1);
                break;
            }
            //更新固件失败
            case 9998: {
                this.handlerResultToBack(messageEntity, 1, 0);
                break;
            }
            //固件版本
            case 5006: {
                this.handlerOtherResultToBack(messageEntity, 203);
                break;
            }
            //设备类型
            case 5007: {
                this.handlerOtherResultToBack(messageEntity, 202);
                break;
            }
        }

    }

    /**
     * 升级--监听从设备的数据
     */
    private void handlerUpdateResultToBack(MessageEntity messageEntity, int func_code_save) {
        Log.d("update", "执行" + func_code_save);
        CmdResultEntity cmdResultEntity = new CmdResultEntity();
        cmdResultEntity.setBox_code(messageEntity.getBox_code());
        String value = "";
    }

    /**
     * 带返回值value的
     * 结果回调显示到主页面
     * 称重、计数、测距等
     *
     * @param messageEntity
     * @param func_code
     */
    private void handlerOtherResultToBack(MessageEntity messageEntity, int func_code) {
        Log.d("回调数据", "执行" + func_code);
        CmdResultEntity cmdResultEntity = new CmdResultEntity();
        cmdResultEntity.setBox_code(messageEntity.getBox_code());
        String value = "";
        //解压
//        if (func_code==100){
//            value = DataConvertUtil.bytesToIntLittle(messageEntity.getContent_bytes(), 0) + "";
//        }
        //计数101
        if (func_code == 101) {
            value = DataConvertUtilMy.bytesToIntLittleCopy(messageEntity.getContent_bytes(), 0) + "";
        }
        //测距 高位在前，低位在后
        if (func_code == 102) {
//            value = DataConvertUtil.bytesToIntLittle(messageEntity.getContent_bytes(), 0) + "";
            value = DataConvertUtilMy.bytesToIntLittle(messageEntity.getContent_bytes(), 0) + "";
        }

        //称重
        if (func_code == 103) {
            value = DataConvertUtilMy.bytesToIntLittleCopy1(messageEntity.getContent_bytes(), 1) + "";
        }
        //温湿度
        if (func_code == 301) {
            value = "T" + messageEntity.getContent_bytes()[0] + "H" + messageEntity.getContent_bytes()[1];
        }

        //固件版本
        if (func_code == 203) {
            String a = DataConvertUtilMy.bytesToIntLittleCopy2(messageEntity.getContent_bytes(), 0);
            SaveData.version = a;
            value = a;
        }
        if (func_code == 202) {
            value = DataConvertUtilMy.bytesToIntLittlType(messageEntity.getContent_bytes(), 0) + "";
        }
        String name = this.transFuncCodeToName(func_code);
        Log.d("我的名字", name + func_code);
        cmdResultEntity.setFunc_code(func_code);
        cmdResultEntity.setName(name);
        cmdResultEntity.setStatus(-1);
        cmdResultEntity.setValue(value);
        cmdResultEntity.setOperate_id(-1);
        this.controlCallBack.onResult(this.gson.toJson(cmdResultEntity));
    }

    //开启返回的状态
    //开启失败是operate_id=1,status=0
    private void handlerResultToBack(MessageEntity messageEntity, int operate_id, int status) {
        CmdResultEntity cmdResultEntity = new CmdResultEntity();
        cmdResultEntity.setBox_code(messageEntity.getBox_code());
        Log.d("content_bytes()", messageEntity.getContent_bytes() + "");
//        int funcCode = DataConvertUtil.bytesToIntLittle(messageEntity.getContent_bytes(), 0);
        String name = this.transFuncCodeToName(func_code_save);
        cmdResultEntity.setFunc_code(func_code_save);
        cmdResultEntity.setName(name);
        cmdResultEntity.setStatus(status);
        cmdResultEntity.setValue("");
        cmdResultEntity.setOperate_id(operate_id);

        this.controlCallBack.onResult(this.gson.toJson(cmdResultEntity));

    }

    /**
     * 展示在页面json中的名字
     *
     * @param func_code
     * @return
     */
    private String transFuncCodeToName(int func_code) {
        String name = "";
        switch (func_code) {
            case 1:
            case 1001:
                name = "投递门1";
                break;
            case 2:
            case 1002:
                name = "投递门2";
                break;
            case 3:
            case 1003:
                name = "投递门3";
                break;
            case 4:
            case 1004:
                name = "投递门4";
                break;
            case 5:
            case 1005:
                name = "投递门5";
                break;
            case 6:
                name = "锁6";
                break;
            case 7:
                name = "柜内灯";
                break;
            case 8:
                name = "风扇";
                break;
            case 9:
                name = "柜外灯";
                break;
            case 10:
                name = "压缩";
                break;
            case 11:
                name = "人体感应";
                break;
            case 12:
                name = "烟雾感应";
                break;
            case 13:
                name = "温度感应";
                break;
            case 14:
                name = "喷香水";
                break;
            case 101:
                name = "计数";
                break;
            case 102:
                name = "红外测距";
                break;
            case 103:
                name = "称重";
                break;
            case 111:
                name = "关门";
                break;
            case 100:
                name = "压缩释放";
                break;
            case 301:
                name = "温湿度";
                break;
            case 302:
                name = "回收门";
                break;
            case 9999:
                name = "固件更新";
                break;

        }

        return name;
    }

    private void handlerRecruitFlag(MessageEntity messageEntity) {
        switch (messageEntity.getIdentify_id()) {
            case 5000:
            default:
                break;
            case 5006:
                String versionInfo = messageEntity.getContent().trim();
                Log.e("versionInfo", versionInfo);
                //固件版本
                if (versionInfo != null && versionInfo.length() > 13) {
                    messageEntity.setContent(versionInfo.substring(9, 12));
                    this.handlerOtherResultToBack(messageEntity, 203);
                }
                break;
            case 5007:
                this.handlerOtherResultToBack(messageEntity, 202);
                break;
            case 5612:
                int var3 = DataConvertUtil.bytesToIntLittle(messageEntity.getContent_bytes(), 0);
                break;
            //测距
//            case 8051:
//                this.handlerOtherResultToBack(messageEntity, 102);
//                break;
            //称重
            case 8052:
                this.handlerOtherResultToBack(messageEntity, 103);
                break;

            case 8060:
                this.handlerOtherResultToBack(messageEntity, 100);
                break;
        }

    }

    /**
     * 读卡数据回调  使用中
     * @param resultBytes
     */
    private void handlerIcCardCallBack(int[] resultBytes) {
        int[] bytes = new int[]{resultBytes[3], resultBytes[4], resultBytes[5], resultBytes[6]};
//        int ID = DataConvertUtil.bytesToIntBig(bytes, 0);
        String[] strHex = new String[4];
        for (int i = 0; i < bytes.length; i++) {
            strHex[i] = Integer.toHexString(bytes[i]);
        }
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < strHex.length; i++) {
            s = s.append(strHex[i]);
        }
        this.controlCallBack.onIcResult("" + s);
    }


    /**
     * IC卡读取线程
     */
    private class SaleReceiveThread extends Thread {
        private SaleReceiveThread() {
        }

        public void run() {
            super.run();
            InputStream inputStream = mSerialPortIC.getInputStream();
            while (true) {
                try {
                    if (inputStream.available() > 0) {
                        Thread.sleep(200);
                        byte[] bytes = new byte[inputStream.available()];
                        int[] temp = new int[inputStream.available()];


                        int size = inputStream.read(bytes);
                        String str = new String(bytes);
                        for (int i = 0; i < bytes.length; i++) {
                            temp[i] = bytes[i] & 0xff;
                        }
                        ControlManagerImplMy.this.handlerIcCardCallBack(temp);

                        Log.d(TAG, "inputStream-run: " + size + "===" + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            InputStream mInputStream = ControlManagerImplMy.this.mSerialPortIC.getInputStream();
//            int maxLength = 512;
//            byte[] buffer = new byte[maxLength];
//            boolean availablex = false;
//            int currentLength = 0;
//
//            for (boolean var6 = true; ControlManagerImplMy.this.mSerialPortIC != null && mInputStream != null; SystemClock.sleep(2L)) {
//                synchronized (this) {
//                    try {
//                        int available = mInputStream.available();
//                        if (available > 0) {
//                            if (available > maxLength - currentLength) {
//                                available = maxLength - currentLength;
//                            }
//
//                            mInputStream.read(buffer, currentLength, available);
//                            currentLength += available;
//                        }
//                    } catch (Exception var12) {
//                        var12.printStackTrace();
//                    }
//
//                    int cursor = 0;
//
//                    while (true) {
//                        while (currentLength >= 6) {
//                            if (buffer[cursor] != 2) {
//                                --currentLength;
//                                ++cursor;
//                            } else {
//                                int factPackLen = 0;
//
//                                for (int i = 0; i < buffer.length; ++i) {
//                                    if (buffer[i] == 3) {
//                                        factPackLen = i + 1;
//                                    }
//                                }
//
//                                byte[] realPackBytes = new byte[factPackLen];
//                                System.arraycopy(buffer, cursor, realPackBytes, 0, factPackLen);
//                                ControlManagerImplMy.this.handlerIcCardCallBack(realPackBytes);
//                                currentLength -= factPackLen;
//                                cursor += factPackLen;
//                            }
//                        }
//
//                        if (currentLength > 0 && cursor > 0) {
//                            System.arraycopy(buffer, cursor, buffer, 0, currentLength);
//                        }
//
//                        SystemClock.sleep(2L);
//                        break;
//                    }
//                }
//            }
//
//            ControlManagerImplMy.this.close();
        }
    }

    public interface ControlCallBack {
        void onResult(String var1);

        void onIcResult(String var1);
    }
}
