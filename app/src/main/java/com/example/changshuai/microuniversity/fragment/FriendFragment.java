package com.example.changshuai.microuniversity.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.changshuai.microuniversity.R;

/**
 * Created by apple on 2017/2/7.
 */

public class FriendFragment extends Fragment {

    public static FriendFragment newInstance(String name){
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString("args1", name);
        fragment.setArguments(args);
        return fragment;
    }

    public FriendFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);
        return rootView;
    }
}
