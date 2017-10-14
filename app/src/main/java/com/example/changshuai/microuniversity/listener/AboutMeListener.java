package com.example.changshuai.microuniversity.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

public class AboutMeListener implements View.OnClickListener {
    private Context context;
    private View view;
    private String name;

    public AboutMeListener(Context context, View view, String name) {
        this.context = context;
        this.view = view;
        this.name = name;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(name);
        final EditText editText = new EditText(context);
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AVUser.getCurrentUser().setUsername(editText.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
