package com.example.changshuai.microuniversity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.changshuai.microuniversity.api.HttpApiAccessor;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.model.Shop;
import com.example.changshuai.microuniversity.utils.AsyncImageLoader;
import com.example.changshuai.microuniversity.utils.Constants;

import java.util.ArrayList;

public class SecondShopProductActivity extends ListActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    //	刷新商品单键值
    private static final int MENU_REFRESH = Menu.FIRST + 2;
    //    退出商品单键值
    private static final int MENU_EXIT = Menu.FIRST + 6;
    //   购物车商品单键值
    public static final int MENU_REPLY = Menu.FIRST + 7;
    private ShopsAdapter adapter = null;
    private ArrayList<Shop> shopsList;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_shop_product);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("全部商品");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        new LoadTask().execute();
    }

    class LoadTask extends AsyncTask<Void, Void, Void> {

        /**
         * 后台运行，加载数据
         */
        protected Void doInBackground(Void... arg0) {
            try {
                shopsList = HttpApiAccessor.getFollowed(-1, -1, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 在执行时调用，将适配器类传入
         */
        protected void onPostExecute(Void result) {
            adapter = new ShopsAdapter();
            setListAdapter(adapter);
            removeDialog(0);
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            showDialog(0);
            super.onPreExecute();
        }
    }


    /**
     * 点击每一行时跳转到ShopInfoActivity
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.i(" onListItemClick ", "============= shops list ================");
        Intent intent = new Intent();
        intent.setAction("second_product_info");
        intent.putExtra("shop", shopsList.get(position));
        startActivity(intent);
    }

    class ShopsAdapter extends BaseAdapter {

        private AsyncImageLoader asyncImageLoader;

        public int getCount() {
            return shopsList.size();
        }


        public Object getItem(int arg0) {
            return shopsList.get(arg0);
        }


        public long getItemId(int position) {
            return position;
        }

        /**
         * 将每一行设置为shops_list_row中定义的格式，并且赋值
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            asyncImageLoader = new AsyncImageLoader();

            convertView = View.inflate(SecondShopProductActivity.this, R.layout.item_second_product, null);

            Shop u = shopsList.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(u.getShop_name());

            TextView todayPrice = (TextView) convertView.findViewById(R.id.price);
            todayPrice.setText(" 原价: " + u.getShop_price() + "   特价: " + String.valueOf(u.getShop_discount_price()));

            ImageView iv = (ImageView) convertView.findViewById(R.id.shopPic);
            String picPath = Constants.WEB_APP_URL + "upload/" + u.getShop_pic();

            Drawable cachedImage = asyncImageLoader.loadDrawable(
                    picPath, iv, new AsyncImageLoader.ImageCallback() {

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