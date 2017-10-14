package com.example.changshuai.microuniversity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.dao.PeripheralShopDao;
import com.example.changshuai.microuniversity.entity.PeripheralShop;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.List;

import static com.example.changshuai.microuniversity.R.id.tv_name;

public class PeripheralShopActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private ListView ps_list;

    private PeripheralShopDao peripheralShopDao;
    private List<PeripheralShop> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral_shop);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("周边店铺");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        peripheralShopDao = new PeripheralShopDao(this);
        if (peripheralShopDao.findAll().size() == 0){
            addFakeData();
        }
        list = peripheralShopDao.findAll();
        ps_list = (ListView) findViewById(R.id.ps_list);
        ps_list.setAdapter(new PSListViewApdater(this));
    }

    public void addFakeData(){
        long basenumber = 13500000000l;
        for(int i = 0;i<10;i++){
            peripheralShopDao.add("张三"+i, "半亩塘"+i, "美容美发"+i, String.valueOf(basenumber+i));
        }
        Toast.makeText(this, "数据添加完毕", Toast.LENGTH_LONG).show();
    }

    private class PSListViewApdater extends BaseAdapter {
        private Context context;
        private TextView ilps_telephone;
        public PSListViewApdater(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
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
            View view =null;
            if(convertView==null){
                view = View.inflate(context, R.layout.item_list_ps, null);
            }else{
                view = convertView;
            }

            TextView ilps_name = (TextView) view.findViewById(R.id.ilps_name);
            TextView ilps_context = (TextView) view.findViewById(R.id.ilps_context);
            TextView ilps_address = (TextView) view.findViewById(R.id.ilps_address);
            ilps_telephone = (TextView) view.findViewById(R.id.ilps_telephone);
            Button ilps_call = (Button) view.findViewById(R.id.ilps_call);
            ilps_name.setText(list.get(position).getName());
            ilps_context.setText(list.get(position).getContext());
            ilps_address.setText(list.get(position).getAddress());
            ilps_telephone.setText(list.get(position).getTelephone());

            ilps_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Telphone = ilps_telephone.getText().toString();
                    Uri uri = Uri.parse("tel:" + Telphone);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}

