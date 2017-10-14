package com.example.changshuai.microuniversity.listener;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by apple on 2017/3/18.
 */

public class TopBackListener implements View.OnClickListener {
    private Activity activity;
    private ImageView topback;

    public TopBackListener(Activity activity, ImageView topback){
        this.activity = activity;
        this.topback = topback;
    }

    @Override
    public  void onClick(View v) {
        activity.finish();
    }
}
