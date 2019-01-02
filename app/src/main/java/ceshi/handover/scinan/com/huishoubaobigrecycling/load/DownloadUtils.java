package ceshi.handover.scinan.com.huishoubaobigrecycling.load;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by breaktian on 2016/9/2.
 */
public class DownloadUtils {

	private static DownloadUtils instance;

	private final static Object lockObj = new Object();
	private final OkHttpClient mClient;

	private DownLoadListener listener;

	private DownloadUtils() {
		mClient = new OkHttpClient.Builder().connectTimeout(3 * 60, TimeUnit.SECONDS)
				.readTimeout(3 * 60, TimeUnit.SECONDS)
				.addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						// 拦截
						Response originalResponse = chain.proceed(chain.request());
						// 包装响应体并返回
						return originalResponse.newBuilder()
								.body(new ProgressResponseBody(originalResponse.body(), new ProgressResponseListener() {
											@Override
											public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
												if (DownloadUtils.this.listener != null) {
													DownloadUtils.this.listener.onProgress(bytesRead, contentLength, done);
												}

											}
										}))
								.build();
					}
				}).build();
	}

	public static DownloadUtils get() {
		if (instance == null) {
			synchronized (lockObj) {
				if (instance == null) {
					instance = new DownloadUtils();
				}
			}
		}
		return instance;
	}
	public void downloadFile(String uri, final Context context){
		// uri 是你的下载地址，可以使用Uri.parse("http://")包装成Uri对象
		Uri url=Uri.parse(uri);
		DownloadManager.Request req = new DownloadManager.Request(url);

// 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载，
// 也可以使用setAllowedOverRoaming方法，它更加灵活
//		req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

// 此方法表示在下载过程中通知栏会一直显示该下载，在下载完成后仍然会显示，
// 直到用户点击该通知或者消除该通知。还有其他参数可供选择
		req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//		req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
		req.setVisibleInDownloadsUi(true);
// 设置下载文件存放的路径，同样你可以选择以下方法存放在你想要的位置。
// setDestinationUri
// setDestinationInExternalPublicDir
//		req.setDestinationInExternalFilesDir(context, com.zt.miaozan.utils.FileUtil.getDownLoadDir(), "爱秒赞");
		req.setDestinationInExternalFilesDir(UiUtils.getContext(),FileUtil.getSDPath(), "大鱼环保");

// 设置一些基本显示信息
		req.setTitle("大鱼环保.apk");
		req.setDescription("下载完后请点击打开");
		//File file = new File(filename);
		/*Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);*/
		String type = "application/vnd.android.package-archive";
		/*intent.setDataAndType(Uri.fromFile(file), type);
		context.startActivity(intent);*/
//// Ok go!
		try {
		DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			final long downloadId = dm.enqueue(req);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("下载设置");
			builder.setMessage("是否到设置界面打开下载功能");
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String packageName = "com.android.providers.downloads";
					Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					intent.setData(Uri.parse("package:" + packageName));
					context.startActivity(intent);
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}

	public void downloadFile(String url, final File file, DownLoadListener listener) {
		this.listener = listener;

		Request request = new Request.Builder().url(url).build();

		mClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				if (DownloadUtils.this.listener != null)
					DownloadUtils.this.listener.onFail(file);

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				// LogUtils.d("onResponse "+response.body().string());
				InputStream is = null;
				byte[] buf = new byte[2048];
				int len = 0;
				FileOutputStream fos = null;
				try {
					is = response.body().byteStream();
					fos = new FileOutputStream(file);
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					fos.flush();
					if (DownloadUtils.this.listener != null) {
						DownloadUtils.this.listener.onSucess(file);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (DownloadUtils.this.listener != null) {
						DownloadUtils.this.listener.onFail(file);
					}
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (IOException e) {
					}
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
					}
				}

			}
		});

	}

}
