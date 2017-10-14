package com.example.changshuai.microuniversity.api;

import com.example.changshuai.microuniversity.model.Shop;
import com.example.changshuai.microuniversity.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author 
 *
 */
public class HttpApiAccessor {

	
	public static ArrayList<Shop> getSearch(long lastId, int pageNo, String search_name) throws Exception {

		String url = Constants.WEB_APP_URL + "shopList.do?method=search";
		
		if(search_name != null && !search_name.equals("") ){
			url += "&search_name=" + search_name;
		}
//		if(search_type != null && !search_type.equals("") ){
//			url += "&search_type=" + search_type;
//		}
//		if(search_info != null && !search_info.equals("") ){
//			url += "&search_info=" + search_info;
//		}

		return getUpdatesList(url,lastId,pageNo);
	}
	
	
	
	public static String saveOrder(HashMap params  ) {
		String url = Constants.WEB_APP_URL + "orderEdit.do?method=save&type=json"  ;
		String result = null ;
		result = BaseAuthenicationHttpClient.doRequest(url, "", "" , params );
		return result ; 
	}
	

//根据flag去获取指定的商品类型
	public static ArrayList<Shop> getFollowed(long lastId, int pageNo, String flag) throws Exception {
		String url = Constants.WEB_APP_URL + "shopList.do?type=json";
		return getUpdatesList(url,lastId,pageNo);
	}

	
//	
	private static ArrayList<Shop> getUpdatesList(String url, long lastId, int pageNo) throws Exception {
		url = appendParams(url, lastId, pageNo);
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");
		JSONArray jsonArray = new JSONArray(jsonString);
		ArrayList<Shop> ret = new ArrayList<Shop>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);
			Shop shop = jsonToShop(json);
			ret.add(shop);
		}
		return ret;
	}
	
//	将json数据解析赋值到shop类
	private static Shop jsonToShop(JSONObject json) throws JSONException {
		Shop shop = new Shop();
		shop.setShop_description(json.getString("shop_description"));
		shop.setShop_discount_price(Float.valueOf(json.get("shop_discount_price").toString()));
//		shop.setShop_flag(json.getInt("shop_flag"));
		shop.setShop_id(json.getInt("shop_id"));
		shop.setShop_name(json.getString("shop_name"));
		shop.setShop_pic(json.getString("shop_pic"));
		shop.setShop_price(Float.valueOf(json.get("shop_price").toString()));
		
//		shop.kouwei =  json.getString("kouwei") ;
//		shop.renqun =  json.getString("renqun") ;
		
		
		return shop;
	}

	
	private static String appendParams(String url, long lastId, int pageNo) {
		if(lastId != -1){
			url = "?last_id=" + lastId;
		}
		if(pageNo != -1){
			url = "&pageNo=" + pageNo;
		}
		return url;
	}

	
	
}



