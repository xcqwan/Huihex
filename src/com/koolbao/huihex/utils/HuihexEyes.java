package com.koolbao.huihex.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public class HuihexEyes {
	private static HuihexEyes singleton = null;
	private Context mContext;
	private HandlerThread mWorker;
	private Handler mHandler;
	
	private HuihexEyes(Context context) {
		mContext = context;
	}
	
	public static HuihexEyes getInstance(Context context) {
		if (singleton == null) {
			singleton = new HuihexEyes(context);
		}
		return singleton;
	}
	
	/**
	 * 初始化HuiHex对象
	 */
	public void initHuiHex() {
		mWorker = new HandlerThread(HuihexConstant.WORKER_NAME);
		mWorker.start();
		mHandler = new Handler(mWorker.getLooper());
	}
	
	/**
	 * 销毁HuiHex对象
	 */
	public void destroyHuiHex() {
		mWorker.quit();
	}
	
	public void punch(String shop_id, String mvtype, String mvcid, String mvsid) {
		final String adxURL = HuihexConstant.getADX(shop_id, mvtype, mvcid, mvsid);
		if (adxURL == null) {
			Log.i(HuihexConstant.HuiHex_Tag, "ADX constant damaged...");
			return;
		}
		
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				HuihexNetWork.with(mContext).get(adxURL);
			}
		});
	}
	
}
