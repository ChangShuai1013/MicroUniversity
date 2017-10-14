package com.example.changshuai.microuniversity.api;

import com.example.changshuai.microuniversity.model.Liuyanban;
import com.example.changshuai.microuniversity.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class  LiuyanbanHttpAdapter {

	public static ArrayList<Liuyanban> getAllLiuyanbanList(long lastId, int pageNo, String flag) throws Exception {
		String url = Constants.WEB_APP_URL + "liuyanban.do?method=list&type=json";
		if( flag != null && !flag.equals("") ){
			try {
				flag  = URLEncoder.encode(flag, "utf-8") ;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			url += "&f=" + flag;
		}
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");
		JSONArray jsonArray = new JSONArray(jsonString);
		ArrayList<Liuyanban> ret = new ArrayList<Liuyanban>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);
			Liuyanban liuyanban = new Liuyanban();
			liuyanban.id =  json.getString("id");
     		liuyanban.biaoti = json.getString("biaoti");
     		liuyanban.neirong = json.getString("neirong");
     		liuyanban.shijian = json.getString("shijian");
     		liuyanban.uid = json.getString("uid");
     		liuyanban.xingming = json.getString("xingming");
			ret.add(liuyanban);
		}
		return ret;
	}
}



