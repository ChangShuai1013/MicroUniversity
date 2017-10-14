package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;

public class LoginActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private EditText login_phone;
    private EditText login_psd;
    private Button done;
    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("登录");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        login_phone = (EditText) findViewById(R.id.login_phone);
        login_psd = (EditText) findViewById(R.id.login_psd);
        done = (Button) findViewById(R.id.login_done);
        signup = (Button) findViewById(R.id.login_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("signup");
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser.loginByMobilePhoneNumberInBackground(login_phone.getText().toString(),
                        login_psd.getText().toString(),
                        new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e == null) {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    LoginActivity.this.finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}
