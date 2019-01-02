package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {
	public static void saveUserName(String guid){
		SharedPreferences share = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString("username", guid);
		editor.commit();
	}
	public static String getUserName(Context context){
		SharedPreferences sp = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString("username", "");
	}
	public static void saveUserWord(String guid){
		SharedPreferences share = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString("userword", guid);
		editor.commit();
	}
	public static String getUserWord(Context context){
		SharedPreferences sp = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString("userword", "");
	}
	public static void saveId(String guid){
		SharedPreferences share = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString("id", guid);
		editor.commit();
	}
	public static String getId(Context context){
		SharedPreferences sp = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString("id", "");
	}
	public static void savetoken(String guid){
		SharedPreferences share = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString("token", guid);
		editor.commit();
	}
	public static String gettoekn(Context context){
		SharedPreferences sp = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString("token", "");
	}
	public static void saveMac(String guid){
		SharedPreferences share = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString("Mac", guid);
		editor.commit();
	}
	public static String getMac(Context context){
		SharedPreferences sp = UiUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString("Mac", "");
	}
}
