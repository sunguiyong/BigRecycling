package ceshi.handover.scinan.com.huishoubaobigrecycling.load;

/**
 * Created by breaktian on 2016/9/2.
 */
/**
 * 响应体进度回调接口，比如用于文件下载中
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-09-02
 * Time: 17:16
 */
public interface ProgressResponseListener {
	void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
