package com.example.changshuai.microuniversity.utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class ProperUtils {
    public static String getClazzProperties(Context c, String clazz){
        Properties props = new Properties();
        try {
            InputStream in = c.getAssets().open("clazzConfig.properties");
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String clazzName = props.getProperty(clazz);
        return clazzName;
    }

    public static String getSendMessageProperties(Context c, String send){
        Properties props = new Properties();
        try {
            InputStream in = c.getAssets().open("sendtoConfig.properties");
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sendName = props.getProperty(send);
        return sendName;
    }
}
