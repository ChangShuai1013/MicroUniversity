package com.example.changshuai.microuniversity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.changshuai.microuniversity.apdater.AbsGridAdapter;
import com.example.changshuai.microuniversity.apdater.MyAdapter;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClassScheduleActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private GridView detailCource;
    private String[][] contents;
    private AbsGridAdapter secondAdapter;
    private List<String> dataList;
    public Map<Integer, String> map;
    private String clazzName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("课程信息");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        clazzName = getIntent().getStringExtra("clazzName");

        detailCource = (GridView) findViewById(R.id.courceDetail);
        init();
    }

    /**
     * 准备数据
     */
    private Map<Integer, String> init() {
        map = new HashMap<Integer, String>();
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formbody = new FormBody.Builder()
                .add("Sel_XNXQ", "20161")
                .add("Sel_XZBJ", clazzName)
                .add("Submit01", "%BC%EC%CB%F7")
                .add("type", "1")
                .build();
        final Request request = new Request.Builder()
                .url("http://59.48.197.202:50000/ZNPK/KBFB_ClassSel_rpt.aspx")
                .post(formbody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ClassScheduleActivity.this, "Post Faild", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Document doc = Jsoup.parse(res);
                Elements tds = doc.select("[valign=top]");
                for (int i = 0; i < tds.size(); i++) {
                    map.put(i, tds.get(i).text());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fillStringArray();
                        secondAdapter = new AbsGridAdapter(ClassScheduleActivity.this);
                        secondAdapter.setContent(contents, 6, 7);
                        detailCource.setAdapter(secondAdapter);
                    }
                });

            }
        });
        return map;
    }

    public void fillStringArray() {
        contents = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (contents[i][j] == null) {
                    contents[i][j] = "";
                } else {
                    //do nothing
                }
            }
        }
        for (Integer in : map.keySet()) {
            contents[(in / 7)][(in % 7)] = map.get(in);
            System.err.println(contents[(in / 7)][(in % 7)].toString());
        }
    }
}
