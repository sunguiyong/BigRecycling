package ceshi.handover.scinan.com.huishoubaobigrecycling.base;



import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.UiUtils;


public abstract class BaseFragment extends RxFragment {
	private View rootView;
	Unbinder unbinder;
	boolean remeber_stae;
	private Configuration config;
	private Resources resources;
	private DisplayMetrics dm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = UiUtils.inflate(layoutView());
		unbinder= ButterKnife.bind(this,rootView);
		initview(rootView);
		initData();
		initListener();
		return rootView;
	}

	/**
	 * 初始化监听
	 * 
	 * @return
	 */
	public void initListener() {
		// TODO Auto-generated method stub
	}

	/**
	 * 数据的加载
	 * 
	 * @return
	 */
	public void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化数据
	 * @param view 
	 * 
	 * @return
	 */
	public void initview(View view) {
		// TODO Auto-generated method stub


	}

	/**
	 * 加载布局的方法
	 * 
	 * @return
	 */
	public abstract int layoutView();

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbinder.unbind();

	}


}
