package com.example.changshuai.microuniversity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.example.changshuai.microuniversity.MainActivity;
import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.utils.ProperUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class MeFragment extends Fragment {
    private RelativeLayout myinfo;
    private RelativeLayout reportcard;
    private RelativeLayout classschedule;
    private RelativeLayout quit;
    private TextView myinfo_name;
    private TextView myinfo_stuid;
    private RelativeLayout sendmessage;

    private View rootView;
    private String username;
    private String stuid;
    private Context context;
    private String clazz;
    private String url = "http://59.48.197.202:50000/_data/home_login.aspx";
    private String cookie;
    boolean flag = false;
    public static MeFragment newInstance(String name){
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString("args1", name);
        fragment.setArguments(args);
        return fragment;
    }

    public MeFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();

        myinfo = (RelativeLayout) rootView.findViewById(R.id.myinfo_Me);
        reportcard = (RelativeLayout) rootView.findViewById(R.id.reportcard_Me);
        classschedule = (RelativeLayout) rootView.findViewById(R.id.classschedule_Me);
        quit = (RelativeLayout) rootView.findViewById(R.id.quit_Me);

        myinfo_name = (TextView) myinfo.findViewById(R.id.tv_name_Me);
        myinfo_stuid = (TextView) myinfo.findViewById(R.id.tv_stuid_Me);
        sendmessage = (RelativeLayout) rootView.findViewById(R.id.sendmessage_Find);
        sendmessage.setVisibility(View.INVISIBLE);

        stuid = AVUser.getCurrentUser().getString("studentid");
        AVQuery<AVObject> avQuery = new AVQuery<>("Student");
        avQuery.whereEndsWith("StudentId", stuid);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    username = list.get(0).getString("StudentName");
                    myinfo_name.setText(username);
                    myinfo_stuid.setText(stuid);
                }
            }
        });

        AVQuery<AVObject> avQuery1 = new AVQuery<>("Student");
        avQuery1.whereEqualTo("StudentId", stuid);
        avQuery1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    AVObject avObject = list.get(0);
                    flag = avObject.getString("ClassCommittee")==null || avObject.getString("PartyMembers")==null||avObject.getString("StudentAssociation")==null || avObject.getString("ClassCommittee")==null;
                    System.out.println(flag);
                }
                if (!flag){
                    sendmessage.setVisibility(View.VISIBLE);
                }
                sendmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AVUser.getCurrentUser().getBoolean("mobilePhoneVerified")){
                            Intent intent = new Intent();
                            intent.setAction("find_sendmessage");
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("about_me");
                startActivity(intent);
            }
        });
        reportcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请登录教务系统");
                final WebView login = new WebView(context);
                login.getSettings().setJavaScriptEnabled(true);
                login.getSettings().setBuiltInZoomControls(false);
                login.setLayoutParams(new ViewGroup.LayoutParams(350, 300));
                login.setWebViewClient(new WebViewClient(){
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        if (!request.getUrl().toString().equals(url)) {
                            CookieManager cookieManager = CookieManager.getInstance();
                            cookie = cookieManager.getCookie(url);
                            AlertDialog.Builder setBuilder = new AlertDialog.Builder(context);
                            setBuilder.setTitle("选择范围");

                        }
                        login.loadUrl(request.getUrl().getPath());
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        if (!"http://59.48.197.202:50000/_data/home_login.aspx".equals(login.getUrl())) {
                            Intent intent = new Intent();
                            intent.putExtra("cookie", cookie);
                            intent.setAction("choose_date");
                            startActivity(intent);
                        }
                    }
                });
                login.loadUrl(url);
                builder.setView(login);
                builder.create().show();

            }
        });
        classschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请输入班级");
                final EditText editText = new EditText(context);
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clazz = editText.getText().toString();
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("class_schedule");
                        String classProp = ProperUtils.getClazzProperties(context, clazz);
                        System.out.println(classProp);
                        if (classProp != null){
                            intent.putExtra("clazzName", classProp);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "差无此班，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        return;
                    }
                });
                builder.create().show();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser.logOut();
                Intent intent = new Intent();
                intent.setAction("login");
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
