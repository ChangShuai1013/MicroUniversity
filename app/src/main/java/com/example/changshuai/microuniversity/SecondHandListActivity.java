package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.changshuai.microuniversity.apdater.SecondHandListRecyclerAdapter;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.ArrayList;
import java.util.List;

public class SecondHandListActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private RecyclerView mRecyclerView;
    private SecondHandListRecyclerAdapter mRecyclerAdapter;
    private List<AVObject> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_list);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("二手信息");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondHandListActivity.this, SecondHandPublishActivity.class));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.list_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SecondHandListActivity.this));
        mRecyclerAdapter = new SecondHandListRecyclerAdapter(mList, SecondHandListActivity.this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Product");
        avQuery.orderByDescending("createdAt");
        avQuery.whereEqualTo("type", 3);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
