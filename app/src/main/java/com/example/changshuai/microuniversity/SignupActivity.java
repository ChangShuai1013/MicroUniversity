package com.example.changshuai.microuniversity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private EditText signup_phone;
    private EditText signup_psd;
    private EditText signup_code;
    private EditText signup_checkpsd;
    private EditText signup_stuid;
    private Button signup_getcode;
    private Button signup_done;

    private String code;
    private String name;
    AVUser avUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("注册");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        signup_phone = (EditText) findViewById(R.id.signup_phone);
        signup_psd = (EditText) findViewById(R.id.signup_psd);
        signup_stuid = (EditText) findViewById(R.id.signup_stuid);
        signup_checkpsd = (EditText) findViewById(R.id.signup_checkpsd);
        signup_done = (Button) findViewById(R.id.signup_done);

        signup_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avUser = new AVUser();
                avUser.setMobilePhoneNumber(signup_phone.getText().toString());
                if (signup_checkpsd.getText().toString().equals(signup_psd.getText().toString())) {
                    System.out.println(signup_checkpsd.getText().equals(signup_psd.getText()));
                } else {
                    System.out.println(signup_checkpsd.getText().equals(signup_psd.getText()));
                    signup_checkpsd.setText("");
                    Toast.makeText(SignupActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                avUser.setPassword(signup_psd.getText().toString());
                if (signup_stuid.getText().toString().matches("^\\d{10}$")){
                    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");
                    avQuery.whereEqualTo("StudentId", signup_stuid.getText().toString());
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            name = list.get(0).getString("StudentName");
                            avUser.setUsername(name);
                            avUser.put("studentid", signup_stuid.getText().toString());
                            avUser.signUpInBackground(new SignUpCallback() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void done(AVException e) {
                                    if (e == null){
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                        builder.setTitle("请验证手机号码");
                                        View view = View.inflate(SignupActivity.this, R.layout.dialog_signup_code, null);
                                        signup_code = (EditText) view.findViewById(R.id.signup_code);
                                        builder.setView(view);
                                        builder.setPositiveButton("验证", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                AVUser.verifyMobilePhoneInBackground(signup_code.getText().toString(), new AVMobilePhoneVerifyCallback() {
                                                    @Override
                                                    public void done(AVException e) {
                                                        if (e == null) {
                                                            dialog.dismiss();
                                                            Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                            avUser.loginByMobilePhoneNumberInBackground(signup_phone.getText().toString(), signup_psd.getText().toString(), new LogInCallback<AVUser>() {
                                                                @Override
                                                                public void done(AVUser avUser, AVException e) {
                                                                    Toast.makeText(SignupActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                                    SignupActivity.this.finish();

                                                                }
                                                            });
                                                        } else {
                                                            signup_code.setText("");
                                                            Toast.makeText(SignupActivity.this, "验证码错误,请重新输入", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                        builder.show();
                                    }else{
                                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(SignupActivity.this, "学号输入有误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
