package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.content.Context;
import android.view.View;

public class UiUtils {

	public static Context getContext(){
		return BaseApplication.getInstance();
	}

	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
}
