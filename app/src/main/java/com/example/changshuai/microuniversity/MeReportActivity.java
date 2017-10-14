package com.example.changshuai.microuniversity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.changshuai.microuniversity.apdater.MeReportListAdapter;
import com.example.changshuai.microuniversity.entity.MeReportBean;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.ArrayList;
import java.util.List;

public class MeReportActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;
    MeReportListAdapter meReportListAdapter;
    private ListView lv_mereport;
    String flag = null;
    int size1 = 0;
    int size2 = 0;
    List<MeReportBean> meReportBeanList = new ArrayList<>();
    MeReportBean meReportBean = new MeReportBean();
    private Context context;
    private String studentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_report);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("我发布的");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        lv_mereport = (ListView) findViewById(R.id.lv_mereport);

        studentid = AVUser.getCurrentUser().getString("studentid");
        AVQuery<AVObject> avQuery1 = new AVQuery<>("LostAndFound");
        avQuery1.whereEqualTo("studentid", studentid);
        avQuery1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    for (AVObject avObject : list) {
                        meReportBean.setObjectId(avObject.getObjectId());
                        meReportBean.setTime(avObject.getCreatedAt().toString());
                        meReportBean.setKind(avObject.get("LostOrFound").toString().equals("1")?"丢失":"捡到");
                        meReportBean.setThing(avObject.get("thing").toString());
                        meReportBeanList.add(meReportBean);
                    }
                    System.out.println("===========" + meReportBeanList.size());
                    AVQuery<AVObject> avQuery2 = new AVQuery<>("Product");
                    avQuery2.whereEqualTo("studentid", studentid);
                    avQuery2.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null){
                                for (AVObject avObject : list) {
                                    meReportBean.setObjectId(avObject.getObjectId());
                                    meReportBean.setTime(avObject.getCreatedAt().toString());
                                    meReportBean.setKind("二手交易");
                                    meReportBean.setThing(avObject.get("description").toString());
                                    meReportBeanList.add(meReportBean);
                                }
                                System.out.println("------------------" + meReportBeanList.size());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        meReportListAdapter = new MeReportListAdapter(MeReportActivity.this, meReportBeanList);
                                        lv_mereport.setAdapter(meReportListAdapter);
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });

    }
}
