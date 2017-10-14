package com.example.changshuai.microuniversity.utils;


import com.avos.avoscloud.AVUser;

public class Constants {

     
//	0——表示特色商品标识
    public static final String FLAG_ZEOR = "0";
//    1——精品商品标识
    public static final String FLAG_TOP = "1";
//    2——全部商品标识
    public static final String FLAG_ALL = "2";

	public static String userId = AVUser.getCurrentUser().getString("studentid");
	
	public static final String IP = "10.101.68.194";
	public static final String PORT = "8080";
	
	public static final String SERVER = "http://" + IP + ":" + PORT + "/server";
    
	public static final String WEB_APP_URL = SERVER + "/" ;

}
