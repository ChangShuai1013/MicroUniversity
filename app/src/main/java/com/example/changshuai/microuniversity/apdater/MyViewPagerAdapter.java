package com.example.changshuai.microuniversity.apdater;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyViewPagerAdapter extends PagerAdapter {
    private ImageView[] imageViews;
    public MyViewPagerAdapter(ImageView[] imageViews) {
        super();
        this.imageViews = imageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews[position % imageViews.length]);
        return imageViews[position % imageViews.length];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews[position % imageViews.length]);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
