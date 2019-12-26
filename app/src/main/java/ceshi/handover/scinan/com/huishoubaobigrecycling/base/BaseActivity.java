package ceshi.handover.scinan.com.huishoubaobigrecycling.base;





import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;


import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.BaseApplication;


public abstract class BaseActivity extends RxAppCompatActivity {

	Unbinder unbinder;
	private long exitTime;
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		super.onCreate(savedInstanceState);
		setContentView(layoutView());
		ImmersionBar.with(this).init();
	    unbinder=ButterKnife.bind(this);
		/*ImmersionBar.with(this)
				.statusBarColor(R.color.with).statusBarDarkFont(true).flymeOSStatusBarFontColor(R.color.with).init();*/
		initview(savedInstanceState);
		initData();
		initListener();
	}

	public void init() {
	}

	/**
	 *初始化监听
	 * @return
	 */
	public void initListener() {
		// TODO Auto-generated method stub

	}
	/**
	 * 数据的加载
	 * @return
	 */
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 初始化数据
	 * @return
	 */
	public void initview(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//StatusBarCompat.compat(this, Color.parseColor("#055A86"));

	}
/**
 * ENEVBUS加载布局的方法
 * @return
 */

	public abstract int layoutView();
	/*@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventMainThread(PrintMsgEvent event) {
		if (event.type == PrinterMsgType.MESSAGE_TOAST) {
			ToastUtil.showToast(BaseActivity.this,event.msg);
		}
	}*/
	@Override
	public void onDestroy() {
		//将activity从堆栈中移除
		BaseApplication.getInstance().getRequestQueue().cancelAll("VolleyRequest");

		super.onDestroy();

		unbinder.unbind();
		ImmersionBar.with(this).destroy();
	}



	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/

}
