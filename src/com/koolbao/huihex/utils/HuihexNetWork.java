package com.koolbao.huihex.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class HuihexNetWork {
	private static HuihexNetWork singleton = null;
	private String versionName;
	private int versionCode;
	private String packageName;
	
	private HuihexNetWork(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
			versionCode = info.versionCode;
			packageName = info.packageName;
		} catch (NameNotFoundException e) {
			Log.i(HuihexConstant.HuiHex_Tag, "get app info faild");
			versionName = "unknow";
			versionCode = 1;
			packageName = "unknow";
		}
	}
	
	public static HuihexNetWork with(Context context) {
		if (singleton == null) {
			singleton = new HuihexNetWork(context);
		}
		return singleton;
	}
	
	public void get(String path) {
		try {
			// 新建HttpPost对象
			HttpGet httpGet = new HttpGet(path);
			// 获取HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, versionName + "#" + versionCode + "#" + packageName);
			// 请求超时
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			// 读取超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			int try_times = 0;
			while (try_times < 3) {
				// 获取HttpResponse实例
				HttpResponse httpResp = httpClient.execute(httpGet);
				// 判断是够请求成功
				if (httpResp.getStatusLine().getStatusCode() == 200) {
					Log.i(HuihexConstant.HuiHex_Tag, "Http request success");
					return;
				} else {
					Log.i(HuihexConstant.HuiHex_Tag, "Http request faild " + httpResp.getStatusLine().getStatusCode());
					try_times++;
				}
			}
		} catch (Exception e) {
			Log.i(HuihexConstant.HuiHex_Tag, "Error network");
		}
	}
}
