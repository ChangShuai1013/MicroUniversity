package com.example.changshuai.microuniversity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.changshuai.microuniversity.api.LiuyanbanHttpAdapter;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.model.Liuyanban;
import com.example.changshuai.microuniversity.utils.AsyncImageLoader;

import java.util.ArrayList;


public class LiuyanbanListActivity extends ListActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private LiuyanbanAdapter adapter = null;
    private ArrayList<Liuyanban> liuyanbanList;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuyanban_list);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("留言板");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        Button right_button = (Button) findViewById(R.id.btn_add_lyb);
        right_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiuyanbanListActivity.this, LiuyanbanInfoAddActivity.class);
                startActivity(intent);
                LiuyanbanListActivity.this.finish();
            }
        });

        new LoadTask().execute();
    }

    /**
     * 异步加载所有资源
     */
    public class LoadTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            try {
                liuyanbanList = LiuyanbanHttpAdapter.getAllLiuyanbanList(-1, -1, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            adapter = new LiuyanbanAdapter();
            setListAdapter(adapter);
            removeDialog(0);
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            showDialog(0);
            super.onPreExecute();
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(LiuyanbanListActivity.this, LiuyanbanInfoActivity.class);
        intent.putExtra("object", liuyanbanList.get(position));
        startActivity(intent);
    }

    public class LiuyanbanAdapter extends BaseAdapter {

        private AsyncImageLoader asyncImageLoader;

        public int getCount() {
            return liuyanbanList.size();
        }

        public Object getItem(int arg0) {
            return liuyanbanList.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            asyncImageLoader = new AsyncImageLoader();
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_list_liuyanban, null);
            Liuyanban u = liuyanbanList.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(u.biaoti);
            TextView shijian = (TextView) convertView.findViewById(R.id.shijian);
            shijian.setText(u.shijian);
            TextView neirong = (TextView) convertView.findViewById(R.id.neirong);
            neirong.setText(u.neirong);
            return convertView;
        }
    }


}
