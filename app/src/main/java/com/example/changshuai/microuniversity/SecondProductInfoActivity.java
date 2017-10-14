package com.example.changshuai.microuniversity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.changshuai.microuniversity.db.DbControl;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.model.Shop;
import com.example.changshuai.microuniversity.utils.AsyncImageLoader;
import com.example.changshuai.microuniversity.utils.Constants;

public class SecondProductInfoActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;


    private TextView tv_name;
    private TextView miaoshu;
    private TextView jiage;
    private TextView leibie;
    private ImageView iv_shop;
    private Button btn_add;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_product_info);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("商品详情");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        //获取上一个activity传过来的参数值
        shop = (Shop) getIntent().getSerializableExtra("shop");

        tv_name = (TextView) this.findViewById(R.id.spi_name);
        tv_name.setText(shop.getShop_name());

        miaoshu = (TextView) this.findViewById(R.id.spi_miaoshu);
        miaoshu.setText("简介：" + shop.getShop_description());

        jiage = (TextView) this.findViewById(R.id.spi_jiage);
        jiage.setText("价格：" + shop.shop_discount_price);


        String flag = "";
        switch (shop.shop_flag) {
            case 0:
                flag = "特色";
                break;
            case 1:
                flag = "精品推荐";
                break;
            case 2:
                flag = "普通	";
                break;
        }

        leibie = (TextView) this.findViewById(R.id.spi_leibie);
        leibie.setText("类别：" + flag);


//		从服务器上获取图片，并且显示
        iv_shop = (ImageView) this.findViewById(R.id.spi_shop_img);
        String picPath = Constants.WEB_APP_URL + "upload/" + shop.getShop_pic();
        AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

        Drawable cachedImage = asyncImageLoader.loadDrawable(
                picPath, iv_shop, new AsyncImageLoader.ImageCallback() {

                    public void imageLoaded(Drawable imageDrawable,
                                            ImageView imageView, String imageUrl) {
                        imageView.setImageDrawable(imageDrawable);
                    }
                });

        if (cachedImage == null) {
            iv_shop.setImageResource(R.drawable.pork);
        } else {
            iv_shop.setImageDrawable(cachedImage);
        }

        btn_add = (Button) findViewById(R.id.spi_add);

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//				添加到购物车，购物车数据临时存放在手机自带sqlite的数据库
                String seat = String.valueOf(shop.getShop_id());
                DbControl dbControl = new DbControl(SecondProductInfoActivity.this);
                if (dbControl.addGwc(SecondProductInfoActivity.this, shop.getShop_name(), seat, shop.getShop_discount_price())) {
                    Log.i(" add gwc ", " add gwc success ");
                    finish();
                }

            }
        });

    }

}

