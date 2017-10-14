package com.example.changshuai.microuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.listener.TopBackListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstListContextActivity extends AppCompatActivity{
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private WebView flc_webview;
    String url = "http://neuc.nuc.edu.cn/info/1016/7607.htm";
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_list_context);
        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("详情");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        flc_webview = (WebView) findViewById(R.id.flc_webview);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(FirstListContextActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                str = res;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc = Jsoup.parse(str);
                            Element vsb_content_501 = doc.getElementById("vsb_content_4");
                            Elements p = doc.getElementsByTag("p");
                            StringBuilder sb = new StringBuilder();
                            for (Element element : p) {
                                if (element.getElementsByTag("img").isEmpty()) {
                                    System.out.println("null");
                                    sb.append(element.toString());
                                }else{
                                    Elements img = element.getElementsByTag("img");
                                    for (Element ele : img) {
                                        String src = "http://neuc.nuc.edu.cn" + ele.attr("src");
                                        ele.attr("src", src);
                                        System.out.println(ele.toString());
                                    }
                                    sb.append(element.toString());
                                }
                            }
                            Elements title = doc.getElementsByClass("titlestyle42794");
                            Elements time = doc.getElementsByClass("timestyle42794");
                            String tempStr = "";
                            str = "";
                            InputStream fis = getResources().openRawResource(R.raw.firstlistcontext);
                            System.out.println(fis.toString());
                            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                            while ((tempStr = br.readLine()) != null)
                                str = str + tempStr ;
                            fis.close();
                            str = str.replaceAll("###title###", title.first().text());
                            str = str.replaceAll("###h1###", title.first().text());
                            str = str.replaceAll("###time###", time.first().text());
                            str = str.replaceAll("###context###", sb.toString());
                            System.out.println(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        flc_webview.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
                    }
                });
            }
        });
    }
}
