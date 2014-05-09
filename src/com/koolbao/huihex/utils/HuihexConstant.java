package com.koolbao.huihex.utils;

public class HuihexConstant {
	private static final String Adx_BASE_URL = "http://s.ztcadx.com/event/tb?t=app";
	public static final String WORKER_NAME = "huihex_worker";
	public static final String HuiHex_Tag = "HuiHex";
	
	public static String getADX(String shop_id, String mvtype, String mvcid, String mvsid) {
		if (shop_id == null || mvtype == null || mvcid == null || mvsid == null) {
			return null;
		}
		
		return Adx_BASE_URL + "&shop_id=" + shop_id + "&mvtype=" + mvtype + "&mvcid=" + mvcid+ "&mvsid=" + mvsid;
	}
}
