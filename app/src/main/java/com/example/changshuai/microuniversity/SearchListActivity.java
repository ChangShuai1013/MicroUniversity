package com.example.changshuai.microuniversity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.api.HttpApiAccessor;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.model.Shop;
import com.example.changshuai.microuniversity.utils.AsyncImageLoader;
import com.example.changshuai.microuniversity.utils.Constants;

import java.util.ArrayList;


/**
 * 列表activity
 * @author Administrator
 *
 */
public class SearchListActivity  extends ListActivity {
	private TextView action_bar_title;
	private ImageView action_bar_back;

	private ShopsAdapter adapter = null;
     
    private ArrayList<Shop> list;

    private String search_name , search_type ,  search_info ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_shop_product);

		action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
		action_bar_title.setText("全部商品");
		action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
		action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));
        
        if( getIntent().getExtras().get("search_name") != null )
        	search_name = (String) getIntent().getExtras().get("search_name")  ;
//        加载数据
        new LoadTask().execute();     
    }
    
//    继承自android的AsyncTask异步类
    public class LoadTask extends AsyncTask<Void, Void, Void> {
		
		/**
		 * 后台运行，加载数据
		 */
		protected Void doInBackground(Void... arg0) {
			try {
				list =  HttpApiAccessor.getSearch(-1, -1, search_name) ;
				if (list.size() == 0) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(SearchListActivity.this, "未找到相关商品", Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 在执行时调用，将适配器类传入
		 */
		protected void onPostExecute(Void result) {
			adapter = new ShopsAdapter() ;
			setListAdapter(adapter);
			removeDialog(0);
			super.onPostExecute(result);
		}
		
		protected void onPreExecute() {
			showDialog(0);
			super.onPreExecute();
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i( " onListItemClick "  , "============= shops list ================" );
		Intent intent = new Intent( SearchListActivity.this, SecondProductInfoActivity.class);
		intent.putExtra("shop", list.get(position));
		startActivity(intent);
	}
	
	
    
/**
 * 适配器类
 * @author Administrator
 *
 */ 
	public class ShopsAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;
		
		public int getCount() {
			return list.size();
		}

		
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		
		public long getItemId(int position) {
			return position;
		}

/**
 * 		将每一行设置为shops_list_row中定义的格式，并且赋值
 */
		public View getView(int position, View convertView, ViewGroup parent) {
		
			asyncImageLoader = new AsyncImageLoader();
			 
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_second_product, null);
 
		    TextView name, todayPrice , description , price ;
		    
	        Shop u = list.get(position); 
	        name = (TextView) convertView.findViewById(R.id.name) ;
	        name.setText(u.getShop_name());
	        
	        todayPrice = (TextView) convertView.findViewById(R.id.price);
	        todayPrice.setText( " 原价: " + u.getShop_price() + "   特价: " + String.valueOf(u.getShop_discount_price())  );
	        
	        ImageView iv = (ImageView) convertView.findViewById(R.id.shopPic) ;
	        String picPath = Constants.WEB_APP_URL + "upload/" + u.getShop_pic() ;
	        
	    	Drawable cachedImage = asyncImageLoader.loadDrawable(
	    			picPath , iv , new AsyncImageLoader.ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
                                                ImageView imageView, String imageUrl) {
							imageView.setImageDrawable(imageDrawable);
						}
					});

			if (cachedImage == null) {
				iv.setImageResource(R.drawable.pork);
			} else {
				iv.setImageDrawable(cachedImage);
			} 
			return convertView;
		}
	}
	
	 

	
    
}
