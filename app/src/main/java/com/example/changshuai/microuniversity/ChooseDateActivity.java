package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.listener.TopBackListener;

public class ChooseDateActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private TextView SJ;
    private TextView sel_xn;
    private TextView sel_xq;
    private Button btn_cd;

    private String cookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("请选择范围");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        SJ = (TextView) findViewById(R.id.SJ);
        sel_xn = (TextView) findViewById(R.id.sel_xn);
        sel_xq = (TextView) findViewById(R.id.sel_xq);
        btn_cd = (Button) findViewById(R.id.btn_cd);

        cookie = getIntent().getStringExtra("cookie");

        SJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ChooseDateActivity.this, SJ);
                getMenuInflater().inflate(R.menu.popup_menu_1,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_type_rxyl:
                                SJ.setText(item.getTitle());
                                sel_xn.setClickable(false);
                                sel_xq.setClickable(false);
                                break;
                            case R.id.menu_type_xn:
                                SJ.setText(item.getTitle());
                                sel_xn.setClickable(true);
                                sel_xq.setClickable(true);
                                break;
                            case R.id.menu_type_xq:
                                SJ.setText(item.getTitle());
                                sel_xn.setClickable(true);
                                sel_xq.setClickable(true);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        sel_xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ChooseDateActivity.this, sel_xn);
                getMenuInflater().inflate(R.menu.popup_menu_2,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_type_2012:
                                sel_xn.setText(item.getTitle());
                                break;
                            case R.id.menu_type_2013:
                                sel_xn.setText(item.getTitle());
                                break;
                            case R.id.menu_type_2014:
                                sel_xn.setText(item.getTitle());
                                break;
                            case R.id.menu_type_2015:
                                sel_xn.setText(item.getTitle());
                                break;
                            case R.id.menu_type_2016:
                                sel_xn.setText(item.getTitle());
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        sel_xq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ChooseDateActivity.this, sel_xq);
                getMenuInflater().inflate(R.menu.popup_menu_3,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_type_1xq:
                                sel_xq.setText(item.getTitle());
                                break;
                            case R.id.menu_type_2xq:
                                sel_xq.setText(item.getTitle());
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        btn_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("入学以来／学年／学期".equals(SJ.getText())){
                    Toast.makeText(ChooseDateActivity.this, "请选择范围1", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("学年".equals(SJ.getText()) && "学年".equals(sel_xn.getText())){
                    Toast.makeText(ChooseDateActivity.this, "请选择范围2", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("学期".equals(SJ.getText()) && "学年".equals(sel_xn.getText()) && "学期".equals(sel_xn.getText())) {
                    Toast.makeText(ChooseDateActivity.this, "请选择范围3", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("cookie", cookie);
                    if ("入学以来".equals(SJ.getText())){
                        intent.putExtra("kind", "0");
                        intent.putExtra("SJ", "0");
                        intent.putExtra("SelXNXQ", "0");
                    } else if ("学年".equals(SJ.getText())){
                        intent.putExtra("kind", "1");
                        intent.putExtra("SJ", "1");
                        intent.putExtra("SelXNXQ", "1");
                        intent.putExtra("sel_xn", sel_xn.getText().toString().substring(0, 4));
                    } else {
                        intent.putExtra("SJ", "1");
                        intent.putExtra("kind", "2");
                        intent.putExtra("SelXNXQ", "2");
                        intent.putExtra("sel_xn", sel_xn.getText().toString().substring(0, 4));
                        intent.putExtra("sel_xq", "第一学期".equals(sel_xq.getText())?"0":"1 ");
                    }
                    intent.setAction("record_list");
                    startActivity(intent);
                }
            }
        });
    }
}
