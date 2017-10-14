package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.squareup.picasso.Picasso;

public class SecondHandDetailActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private TextView mName;
    private TextView mDescription;
    private TextView mPrice;
    private ImageView mImage;
    private Button call;

    private String telephone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_detail);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("详细信息");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        mName = (TextView) findViewById(R.id.shd_name);
        mPrice = (TextView) findViewById(R.id.shd_price);
        mDescription = (TextView) findViewById(R.id.shd_description);
        mImage = (ImageView) findViewById(R.id.shd_image);
        call = (Button) findViewById(R.id.shd_btn_call);

        String goodsObjectId = getIntent().getStringExtra("goodsObjectId");
        AVObject avObject = AVObject.createWithoutData("Product", goodsObjectId);
        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                mName.setText(avObject.getString("studentid") == null ? "" : avObject.getString("studentid"));
                mDescription.setText(avObject.getString("description"));
                mPrice.setText(avObject.get("price") == null ? "￥" : "￥ " + avObject.get("price"));
                Picasso.with(SecondHandDetailActivity.this).load(avObject.getAVFile("image") == null ? "www" : avObject.getAVFile("image").getUrl()).into(mImage);
                telephone = avObject.getString("telephone");
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + telephone);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
