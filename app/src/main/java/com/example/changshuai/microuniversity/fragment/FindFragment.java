package com.example.changshuai.microuniversity.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.example.changshuai.microuniversity.MainActivity;
import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.SignupActivity;

import java.util.List;

import butterknife.ButterKnife;

public class FindFragment extends Fragment {
    private RelativeLayout food;
    private RelativeLayout shop;
    private RelativeLayout lostandfound;
    private RelativeLayout secondhand;

    private EditText signup_code;
    private Button signup_getcode;
    private Context context;
    private String[] items = {"我丢失了东西要发布消息", "我捡到了东西要发布消息"};
    private String TAG = FindFragment.class.getSimpleName();
    boolean flag = false;
    public static FindFragment newInstance(String name){
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString("args1", name);
        fragment.setArguments(args);
        return fragment;
    }

    public FindFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        food = (RelativeLayout) rootView.findViewById(R.id.food_Find);
        shop = (RelativeLayout) rootView.findViewById(R.id.shop_Find);
        lostandfound = (RelativeLayout) rootView.findViewById(R.id.lostandfound_Find);
        secondhand = (RelativeLayout) rootView.findViewById(R.id.secondhand_Find);

        String stuid = AVUser.getCurrentUser().getString("studentid");
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("find_food");
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("find_shop");
                startActivity(intent);
            }
        });
        lostandfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("lost_and_found_list");
                startActivity(intent);
            }
        });
        secondhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent();
               intent.setAction("second_hand_shop");
               startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
