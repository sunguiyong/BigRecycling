package ceshi.handover.scinan.com.huishoubaobigrecycling.load;

import java.io.File;

/**
 * Created by breaktian on 2016/9/2.
 */
public interface DownLoadListener {
	void onFail(File file);

	void onSucess(File file);

	void onProgress(long bytesRead, long contentLength, boolean done);

}
