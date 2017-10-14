package com.example.changshuai.microuniversity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.changshuai.microuniversity.listener.TopBackListener;

public class SecondHandShopActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private RelativeLayout product_shs;
    private RelativeLayout searchproduct_shs;
    private RelativeLayout shoppingcart_shs;
    private RelativeLayout messagecenter_shs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_shop);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("官方二手店");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        product_shs = (RelativeLayout) findViewById(R.id.product_shs);
        searchproduct_shs = (RelativeLayout) findViewById(R.id.searchproduct_shs);
        shoppingcart_shs = (RelativeLayout) findViewById(R.id.shoppingcart_shs);
        messagecenter_shs = (RelativeLayout) findViewById(R.id.messagecenter_shs);

        product_shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("second_shop_product");
                startActivity(intent);
            }
        });
        searchproduct_shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(SecondHandShopActivity.this);
            }
        });
        shoppingcart_shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondHandShopActivity.this, GwcListActivity.class);
                startActivity(intent);
            }
        });
        messagecenter_shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondHandShopActivity.this, LiuyanbanListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog(Context context) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts_search, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        final EditText et_search_name = (EditText) promptsView.findViewById(R.id.so_name);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("搜索")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SecondHandShopActivity.this, SearchListActivity.class);
                                intent.putExtra("search_name", et_search_name.getText().toString().trim());
                                startActivity(intent);
                            }
                        });

        alertDialogBuilder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
