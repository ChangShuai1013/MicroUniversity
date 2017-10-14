package com.example.changshuai.microuniversity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.model.Liuyanban;

public class LiuyanbanInfoActivity extends Activity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private Liuyanban liuyanban;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuyanban_info);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("留言板");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        liuyanban = (Liuyanban) getIntent().getSerializableExtra("object");

        TextView tv_biaoti = (TextView) this.findViewById(R.id.biaoti);
        tv_biaoti.setText("标题 : " + liuyanban.biaoti);
        TextView tv_neirong = (TextView) this.findViewById(R.id.neirong);
        tv_neirong.setText("内容 : " + liuyanban.neirong);
        TextView tv_shijian = (TextView) this.findViewById(R.id.shijian);
        tv_shijian.setText("日期 : " + liuyanban.shijian);
        TextView tv_xingming = (TextView) this.findViewById(R.id.xingming);
        tv_xingming.setText("用户 : " + liuyanban.xingming);
    }
}
