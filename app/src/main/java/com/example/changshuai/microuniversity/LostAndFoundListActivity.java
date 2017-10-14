package com.example.changshuai.microuniversity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.changshuai.microuniversity.apdater.LostAndFoundRecyclerAdapter;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LostAndFoundListActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private RecyclerView mRecyclerView;
    private LostAndFoundRecyclerAdapter mRecyclerAdapter;
    private List<AVObject> mList = new ArrayList<>();
    private FloatingActionButton fab;
    String lostorfound = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_list);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("失物招领");

        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        fab = (FloatingActionButton) findViewById(R.id.lafl_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LostAndFoundListActivity.this);
                Dialog dialog = builder.show();
                builder.setTitle("您要发布什么类型的信息");
                builder.setPositiveButton("我丢失了东西", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lostorfound = "丢失";
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("lost_and_found");
                        intent.putExtra("LostOrFound", lostorfound);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("我捡到了东西", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lostorfound = "捡到";
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("lost_and_found");
                        intent.putExtra("LostOrFound", lostorfound);
                        Toast.makeText(LostAndFoundListActivity.this, lostorfound+"", Toast.LENGTH_LONG);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.list_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(LostAndFoundListActivity.this));
        mRecyclerAdapter = new LostAndFoundRecyclerAdapter(mList, LostAndFoundListActivity.this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
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
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    System.out.println("+++++" + list.size());
                    mList.addAll(list);
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
