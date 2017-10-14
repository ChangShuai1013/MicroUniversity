package com.example.changshuai.microuniversity;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

public class MyMicroUniversityApp extends Application {
    private final String APP_ID = "yVseq6oWpeUfBPgE5O4uz9xk-gzGzoHsz";
    private final String APP_KEY = "HxejOpQ6iJbJuddvbSKNHhFu";

    @Override
    public void onCreate(){
        super.onCreate();
        //初始化LeanCloud参数，上下文，AppId，AppKey
        AVOSCloud.initialize(this,"yVseq6oWpeUfBPgE5O4uz9xk-gzGzoHsz","HxejOpQ6iJbJuddvbSKNHhFu");
        //开启调试日志
        AVOSCloud.setDebugLogEnabled(true);
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
    }

}
