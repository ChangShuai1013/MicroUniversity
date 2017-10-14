package com.example.changshuai.microuniversity;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.changshuai.microuniversity.listener.AboutMeListener;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AboutMEActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private TextView am_name;
    private TextView am_stuid;
    private TextView am_class;
    private TextView am_sex;
    private TextView am_sign;
    private RelativeLayout am_changePsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("个人信息");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        am_name = (TextView) findViewById(R.id.tv_name);
        am_name.setOnClickListener(new AboutMeListener(this, am_name, "修改昵称"));
        am_stuid = (TextView) findViewById(R.id.tv_stuid);
        am_class = (TextView) findViewById(R.id.tv_class);
        am_sex = (TextView) findViewById(R.id.tv_sex);
        am_sign = (TextView) findViewById(R.id.tv_sign);
        am_changePsd = (RelativeLayout) findViewById(R.id.re_changePsd);

        String stuid = AVUser.getCurrentUser().getString("studentid");
        AVQuery<AVObject> avQuery = new AVQuery<>("Student");
        avQuery.whereEqualTo("StudentId", stuid);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                am_name.setText(list.get(0).getString("StudentName"));
                am_stuid.setText(list.get(0).getString("StudentId"));
                am_class.setText(list.get(0).getString("StudentClass"));
                am_sex.setText(list.get(0).getString("StudentSex"));
                am_sign.setText(list.get(0).getString("StudentSign"));
            }
        });

        am_changePsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AboutMEActivity.this);
                builder.setTitle("修改密码");
                View view = View.inflate(AboutMEActivity.this, R.layout.dialog_aboutme_changepsd, null);
                final EditText newPsd = (EditText) view.findViewById(R.id.aboutme_newPsd);
                final EditText againPsd = (EditText) view.findViewById(R.id.aboutme_againPsd);
                final EditText code = (EditText) view.findViewById(R.id.aboutme_code);
                final Button getcode = (Button) view.findViewById(R.id.aboutme_getcode);
                getcode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (newPsd.getText().toString().equals(againPsd.getText().toString())) {
                            AVUser.getCurrentUser().requestPasswordResetBySmsCodeInBackground(AVUser.getCurrentUser().getMobilePhoneNumber(), new RequestMobileCodeCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        Toast.makeText(AboutMEActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                                        getcode.setText("已发送");
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AboutMEActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                            newPsd.setText("");
                            againPsd.setText("");
                        }
                    }
                });
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        AVUser.getCurrentUser().resetPasswordBySmsCodeInBackground(code.getText().toString(), newPsd.getText().toString(), new UpdatePasswordCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(AboutMEActivity.this, "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    AVUser.getCurrentUser().logOut();
                                    AboutMEActivity.this.finish();
                                    Intent intent = new Intent();
                                    intent.setAction("login");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AboutMEActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
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
        });
    }

}
