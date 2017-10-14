package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.example.changshuai.microuniversity.fragment.FindFragment;
import com.example.changshuai.microuniversity.fragment.FirstFragment;
import com.example.changshuai.microuniversity.fragment.FriendFragment;
import com.example.changshuai.microuniversity.fragment.MeFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    //底部导航栏的变量定义
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private FirstFragment mFirstFragment;
    private FriendFragment mFriendFragment;
    private FindFragment mFindFragment;
    private MeFragment mMeFragment;

    public MainActivity(){}

    public MainActivity(BottomNavigationBar bottomNavigationBar, FirstFragment mFirstFragment, FriendFragment mFriendFragment, FindFragment mFindFragment, MeFragment mMeFragment) {
        this.bottomNavigationBar = bottomNavigationBar;
        this.mFirstFragment = mFirstFragment;
        this.mFriendFragment = mFriendFragment;
        this.mFindFragment = mFindFragment;
        this.mMeFragment = mMeFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            // 跳转到首页
        } else {
            //缓存用户对象为空时，可打开用户注册界面…
            Intent intent = new Intent();
            intent.setAction("login");
            startActivity(intent);
        }
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "首页").setActiveColor("#1aad19"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "发现").setActiveColor("#1aad19"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_find_replace_white_24dp, "我").setActiveColor("#1aad19"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        //首次进入时设置默认的fragment
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        mFirstFragment = FirstFragment.newInstance("首页");
        transaction.replace(R.id.tb, mFirstFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected() called with:" + "position = [" + position + "]");
        android.app.FragmentManager fm = this.getFragmentManager();
        //开启事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        switch (position){
            case 0:
                if (mFirstFragment == null){
                    mFirstFragment = FirstFragment.newInstance("首页");
                }
                transaction.replace(R.id.tb, mFirstFragment);
                break;
            case 1:
                if (mFriendFragment == null){
                    mFindFragment = FindFragment.newInstance("发现");
                }
                transaction.replace(R.id.tb, mFindFragment);
                break;
            case 2:
                if (mMeFragment == null){
                    mMeFragment = MeFragment.newInstance("我");
                }
                transaction.replace(R.id.tb, mMeFragment);
                break;
        }
        //事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }

}
