package com.example.changshuai.microuniversity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.view.MyHScrollView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordListActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private ListView lv_recordlist;
    private RecordListAdapter recordListAdapter;
    private RelativeLayout header;

    private String str;
    private List<String> recordList;
    private Map<String, String> formMap;
    private String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("成绩单");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        header = (RelativeLayout) findViewById(R.id.head);
        header.setFocusable(true);
        header.setClickable(true);
        header.setBackgroundColor(Color.parseColor("#b2d235"));
        header.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        formMap = new HashMap<>();
        Intent intent = getIntent();
        kind = intent.getStringExtra("kind");
        System.out.println("+=++++" + intent.getStringExtra("kind"));
        if ("0".equals(kind)){
            formMap.put("SJ", intent.getStringExtra("SJ"));
            System.out.println(intent.getStringExtra("SJ"));
            formMap.put("SelXNXQ", intent.getStringExtra("SelXNXQ"));
            System.out.println(intent.getStringExtra("SelXNXQ"));
        } else if ("1".equals(kind)) {
            formMap.put("SJ", intent.getStringExtra("SJ"));
            System.out.println(intent.getStringExtra("SJ"));
            formMap.put("SelXNXQ", intent.getStringExtra("SelXNXQ"));
            System.out.println(intent.getStringExtra("SelXNXQ"));
            formMap.put("sel_xn", intent.getStringExtra("sel_xn"));
            System.out.println(intent.getStringExtra("sel_xn"));
        } else if ("2".equals(kind)) {
            formMap.put("SJ", intent.getStringExtra("SJ"));
            formMap.put("SelXNXQ", intent.getStringExtra("SelXNXQ"));
            System.out.println(intent.getStringExtra("SelXNXQ"));
            formMap.put("sel_xn", intent.getStringExtra("sel_xn"));
            formMap.put("sel_xq", intent.getStringExtra("sel_xq"));
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("zfx_flag", "0").add("zxf", "0").add("btn_search", "%BC%EC%CB%F7");
        Set<String> set = formMap.keySet();
        for (String key : set) {
            builder.add(key, formMap.get(key));
        }
        final Request request = new Request.Builder().url("http://59.48.197.202:50000/xscj/Stu_MyScore_rpt.aspx")
                .addHeader("Cookie", intent.getStringExtra("cookie"))
                .post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(RecordListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                str = res;
                Document doc = Jsoup.parse(str);
                System.out.println(doc);
                Element table = doc.getElementById("ID_Table");
                if (table != null){
                    Element tbody = table.getElementsByTag("tbody").first();
                    Elements tr = tbody.getElementsByTag("tr");
                    recordList = new ArrayList<>();
                    for (int i = 0; i < tr.size(); i++) {
                        Elements td = tr.get(i).getElementsByTag("td");
                        for (int j = 0; j < td.size(); j++) {
                            recordList.add(td.get(j).text());
                        }
                    }
                    for (int i = 1; i < recordList.size(); i++){
                        System.out.println(recordList.get(i));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv_recordlist = (ListView) findViewById(R.id.lv_recordlist);
                            lv_recordlist.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

                            recordListAdapter = new RecordListAdapter(RecordListActivity.this, R.layout.item_record_list, recordList);
                            lv_recordlist.setAdapter(recordListAdapter);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecordListActivity.this, "无法查询，存在未评价教学记录", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private class RecordListAdapter extends BaseAdapter {
        public List<ViewHolder> mHolderList = new ArrayList<>();

        int id_row_layout;
        LayoutInflater mInflater;
        List<String> list;

        public RecordListAdapter(Context context, int id_row_layout, List<String> list) {
            super();
            this.id_row_layout = id_row_layout;
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return recordList.size()/12;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (RecordListActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();

                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);

                    holder.scrollView = scrollView1;
                    holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
                    holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
                    holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
                    holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
                    holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);
                    holder.txt6 = (TextView) convertView.findViewById(R.id.textView6);
                    holder.txt7 = (TextView) convertView.findViewById(R.id.textView7);
                    holder.txt8 = (TextView) convertView.findViewById(R.id.textView8);
                    holder.txt9 = (TextView) convertView.findViewById(R.id.textView9);
                    holder.txt10 = (TextView) convertView.findViewById(R.id.textView10);
                    holder.txt11 = (TextView) convertView.findViewById(R.id.textView11);
                    MyHScrollView headSrcrollView = (MyHScrollView) header.findViewById(R.id.horizontalScrollView1);
                    headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));
                    convertView.setTag(holder);
                    mHolderList.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            for (int i = 1; i < list.size(); i++) {
                switch (i % 12) {
                    case 1:
                        holder.txt1.setText(list.get(1 + ((position) * 12)));
                        break;
                    case 2:
                        holder.txt2.setText(list.get(2 + ((position) * 12)));
                        break;
                    case 3:
                        holder.txt3.setText(list.get(3 + ((position) * 12)));
                        break;
                    case 4:
                        holder.txt4.setText(list.get(4 + ((position) * 12)));
                        break;
                    case 5:
                        holder.txt5.setText(list.get(5 + ((position) * 12)));
                        break;
                    case 6:
                        holder.txt6.setText(list.get(6 + ((position) * 12)));
                        break;
                    case 7:
                        holder.txt7.setText(list.get(7 + ((position) * 12)));
                        break;
                    case 8:
                        holder.txt8.setText(list.get(8 + ((position) * 12)));
                        break;
                    case 9:
                        holder.txt9.setText(list.get(9 + ((position) * 12)));
                        break;
                    case 10:
                        holder.txt10.setText(list.get(10 + ((position) * 12)));
                        break;
                    case 11:
                        holder.txt11.setText(list.get(11 + ((position) * 12)));
                        break;
                }
            }
            return convertView;
        }

        private class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView scrollView) {
                mScrollViewArg = scrollView;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                mScrollViewArg.smoothScrollTo(l, t);
            }
        }

        class ViewHolder {
            TextView txt1;
            TextView txt2;
            TextView txt3;
            TextView txt4;
            TextView txt5;
            TextView txt6;
            TextView txt7;
            TextView txt8;
            TextView txt9;
            TextView txt10;
            TextView txt11;
            HorizontalScrollView scrollView;
        }
    }

    private class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) header
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(event);
            return false;
        }
    }
}