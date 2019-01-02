package ceshi.handover.scinan.com.huishoubaobigrecycling.load;

import android.os.Environment;
import android.text.TextUtils;




import java.io.File;
import java.text.DecimalFormat;

import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.TLog;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;

/**
 * Created by xingzy on 2016/4/13.
 */
public class FileUtil {
    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 内部存储，私有
     */
    public static String getPrivateFileRootdir() {
            File sd = UiUtils.getContext().getApplicationContext().getFilesDir();
            return sd.getPath() +"/";
    }
    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 内部存储，私有
     */

    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 共享存储
     */
    /*public static String getFileRootdir() {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在

        if (sdCardExist) {
            File sd = Environment.getExternalStorageDirectory();
            path = sd.getPath() + "/" + APPUtil.getPackageName(ApplicationHelper.getInstance().getApplicationContext()) + "/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            return path;
        } else {
            return path;
        }
    }*/

    /**
     * 下载根目录
     *
     * @return
     */


    /**
     * 在当前应用根目录下新建文件夹
     * @param name：文件相对路径名
     * @return
     */


    /**
     * 获得目录下文件的大小
     *
     * @param fileDir 文件目录
     * @return 文件的大小
     */
    public static long getFileSize(File fileDir) {
        long size = 0;
        File flist[] = fileDir.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /***
     * 转换文件大小单位(b/kb/mb/gb)
     ***/
    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        if (fileSizeString.startsWith(".")) {
            fileSizeString = "0" + fileSizeString;
        }
        return fileSizeString;
    }

    /**
     * 判断文件是否存在
     *
     * @param s：文件的绝对路径
     * @return
     */
    public static boolean isExist(String s) {
        File file = new File(s);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath：文件的绝对路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        TLog.log(sdDir.toString());

        return sdDir.toString();
    }
}
