package com.example.changshuai.microuniversity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.utils.ProperUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMessageActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private EditText sendto;
    private EditText message;
    private Button send;

    String[] select_item = new String[5];
    String[] sendthing = new String[2];

    String sendtoProp;
    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("发送通知");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        sendto = (EditText) findViewById(R.id.et_sendto);
        message = (EditText) findViewById(R.id.et_message);
        send = (Button) findViewById(R.id.btn_sendmessage);

        sendto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SendMessageActivity.this, sendto);
                getMenuInflater().inflate(R.menu.popup_menu_sendmessage,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_sm_sa01:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "1").toString();
                                System.out.println(sendtoProp);
                                sendto.setText(sendtoProp.substring(0, 6));
                                sendthing[0] = sendtoProp.substring(7, 9);
                                sendthing[1] = sendtoProp.substring(10);
                                break;
                            case R.id.menu_sm_sa001:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "2");
                                sendto.setText(sendtoProp.substring(0, 6));
                                sendthing[0] = sendtoProp.substring(7, 9);
                                sendthing[1] = sendtoProp.substring(10);
                                break;
                            case R.id.menu_sm_sa002:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "3");
                                sendto.setText(sendtoProp.substring(0, 6));
                                sendthing[0] = sendtoProp.substring(7, 9);
                                sendthing[1] = sendtoProp.substring(10);
                                break;
                            case R.id.menu_sm_ylc001:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "4");
                                sendto.setText(sendtoProp.substring(0, 5));
                                sendthing[0] = sendtoProp.substring(6, 9);
                                sendthing[1] = sendtoProp.substring(10);
                                break;
                            case R.id.menu_sm_ylc002:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "5");
                                sendto.setText(sendtoProp.substring(0, 5));
                                sendthing[0] = sendtoProp.substring(6, 9);
                                sendthing[1] = sendtoProp.substring(10);
                                break;
                            case R.id.menu_sm_ccthis:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "6");
                                sendto.setText(sendtoProp.substring(0, 4));
                                sendthing[0] = sendtoProp.substring(5, 7);
                                sendthing[1] = sendtoProp.substring(8);
                                break;
                            case R.id.menu_sm_cc001:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "7");
                                sendto.setText(sendtoProp.substring(0, 4));
                                sendthing[0] = sendtoProp.substring(5, 7);
                                sendthing[1] = sendtoProp.substring(8);
                                break;
                            case R.id.menu_sm_cc002:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "8");
                                sendto.setText(sendtoProp.substring(0, 4));
                                sendthing[0] = sendtoProp.substring(5, 7);
                                sendthing[1] = sendtoProp.substring(8);
                                break;
                            case R.id.menu_sm_pm001:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "9");
                                sendto.setText(sendtoProp.substring(0, 7));
                                sendthing[0] = sendtoProp.substring(8, 10);
                                sendthing[1] = sendtoProp.substring(11);
                                break;
                            case R.id.menu_sm_pm002:
                                sendtoProp = ProperUtils.getSendMessageProperties(SendMessageActivity.this, "10");
                                sendto.setText(sendtoProp.substring(0, 7));
                                sendthing[0] = sendtoProp.substring(8, 10);
                                sendthing[1] = sendtoProp.substring(11);
                                break;
                        }
                        System.out.println(sendthing[0] + "--------" + sendthing[1]);
                        return false;
                    }
                });
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AVQuery<AVObject>> list = new ArrayList<>();
                if ("sc".equals(sendthing[0])) {
//                    final AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");
                    avQuery.whereEqualTo("StudentAssociation", sendthing[1]);
                    list.add(avQuery);
                } else if ("ylc".equals(sendthing[0])) {
                    final AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");
                    avQuery.whereEqualTo("YouthLeagueCommittee", sendthing[1]);
                    list.add(avQuery);
                } else if ("cc".equals(sendthing[0])) {
                    final AVQuery<AVObject> avQuery1 = new AVQuery<AVObject>("Student");
                    avQuery1.whereEqualTo("ClassCommittee", sendthing[1]);
                    if ("this".equals(sendthing[1])) {
                        final AVQuery<AVObject> avQuery2 = new AVQuery<AVObject>("Student");
                        avQuery2.whereEqualTo("StudentClass", AVUser.getCurrentUser().getString("studentid").substring(0, 8));
                        AVQuery<AVObject> avQuery = AVQuery.and(Arrays.asList(avQuery1, avQuery2));
                        list.add(avQuery);
                    } else {
                        list.add(avQuery1);
                    }
                } else if ("pm".equals(sendthing[0])) {
                    final AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");
                    avQuery.whereEqualTo("PartyMembers", sendthing[1]);
                    list.add(avQuery);
                }
//                for (int i = 0; i < select_item.length; i++){
//                    if (select_item[i] != null){
//                        final AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Student");
//                        avQuery.whereEqualTo(select_item[i], true);
//                        list.add(avQuery);
//                    }
//                }
//                AVQuery<AVObject> avQuery = AVQuery.or(list);
                avQuery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (AVObject avObject : list) {
                            Map<String, Object> parameters = new HashMap<String, Object>();
                            parameters.put("message", message.getText().toString());
                            parameters.put("time", sdf.format(new Date().toString()));
                            AVOSCloud.requestSMSCodeInBackground(avObject.get("telephone").toString(), "order_message", parameters, new RequestMobileCodeCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        Toast.makeText(getBaseContext(), "发送成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getBaseContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
