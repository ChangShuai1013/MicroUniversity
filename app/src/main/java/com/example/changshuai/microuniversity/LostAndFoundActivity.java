package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LostAndFoundActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private TextView tv_time;
    private TextView tv_addr;
    private EditText lafp_time;
    private EditText lafp_addr;
    private EditText lafp_telephone;
    private EditText lafp_thing;
    private Button lafp_done;
    private Button lafp_select;
    private ImageView lafp_image;
    private byte[] lafp_image_bytes = null;
    private ProgressBar lafp_progress;
    private String lostorfound;
    private int lof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("信息发布");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        lafp_progress = (ProgressBar) findViewById(R.id.alfr_progess);
        Intent intent = getIntent();
        lostorfound = intent.getStringExtra("LostOrFound");

        lafp_time = (EditText) findViewById(R.id.alfr_et_time);
        lafp_addr = (EditText) findViewById(R.id.alfr_et_addr);
        lafp_telephone = (EditText) findViewById(R.id.alfr_et_telephone);
        lafp_thing = (EditText) findViewById(R.id.alfr_et_thing);
        tv_time = (TextView) findViewById(R.id.alfr_tv_temp_time);
        tv_addr = (TextView) findViewById(R.id.alfr_tv_temp_addr);
        lafp_image = (ImageView) findViewById(R.id.alfr_iv_select_publish);
        lafp_select = (Button) findViewById(R.id.alfr_btn_select_publish);
        lafp_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });
        lafp_done = (Button) findViewById(R.id.alfr_done);
        if (lostorfound.equals("捡到")) {
            tv_time.setText("捡到时间");
            tv_addr.setText("捡到地点");
            lof = 2;
        } else {
            lof = 1;
        }
        lafp_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVObject laf = new AVObject("Product");
                laf.put("time", lafp_time.getText().toString());
                laf.put("address", lafp_addr.getText().toString());
                laf.put("telephone", lafp_telephone.getText().toString());
                laf.put("studentid", AVUser.getCurrentUser().getString("studentid"));
                laf.put("thing", lafp_thing.getText().toString());
                laf.put("type", lof);
                laf.put("image", new AVFile("lafPic", lafp_image_bytes));
                laf.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            lafp_progress.setVisibility(View.GONE);
                            LostAndFoundActivity.this.finish();
                        } else {
                            lafp_progress.setVisibility(View.GONE);
                            Toast.makeText(LostAndFoundActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            try {
                lafp_image.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                lafp_image_bytes = getBytes(getContentResolver().openInputStream(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
