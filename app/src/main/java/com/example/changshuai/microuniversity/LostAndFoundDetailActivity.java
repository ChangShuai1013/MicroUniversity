package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.squareup.picasso.Picasso;

public class LostAndFoundDetailActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private TextView mName;
    private TextView mDescription;
    private ImageView mImage;
    private Button call;

    String telephone = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_detail);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("失物招领");

        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        mName = (TextView) findViewById(R.id.lafd_name_detail);
        mDescription = (TextView) findViewById(R.id.lafd_description_detail);
        mImage = (ImageView) findViewById(R.id.lafd_image_detail);
        call = (Button) findViewById(R.id.lafd_btn_call);

        String goodsObjectId = getIntent().getStringExtra("goodsObjectId");
        AVObject avObject = AVObject.createWithoutData("Product", goodsObjectId);
        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                String lof = null;
                if (avObject.getInt("LostOrFound") == 1){
                    lof = "丢失";
                } else {
                    lof = "捡到";
                }
                mName.setText(avObject.getString("studentid"));
                mDescription.setText("于" + avObject.getString("time") + "在" + avObject.getString("address") + lof + avObject.getString("thing"));
                Picasso.with(LostAndFoundDetailActivity.this).load(avObject.getAVFile("image") == null ? "www" : avObject.getAVFile("image").getUrl()).into(mImage);
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
}
