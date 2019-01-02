package ceshi.handover.scinan.com.huishoubaobigrecycling.load;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ABC on 2017/6/20.
 */

public  class DownloadReceiver extends BroadcastReceiver {

    private String filename;

    @Override
    public void onReceive(Context context, Intent intent) {

        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if(c.moveToFirst()) {
                //获取文件下载路径
                filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if(filename != null){
                    Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                            .setDataAndType(Uri.parse("file://" + filename), "application/vnd.android.package-archive");
                    // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
                    promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(promptInstall);
                    FileUtil.deleteFile(filename);
                }
            }
        }else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())){
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            //点击通知栏取消下载
            manager.remove(ids);
        }

    }

}
