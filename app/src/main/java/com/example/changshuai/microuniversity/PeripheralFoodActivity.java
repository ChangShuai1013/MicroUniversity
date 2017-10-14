package com.example.changshuai.microuniversity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.dao.PeripheralFoodDao;
import com.example.changshuai.microuniversity.dao.PeripheralShopDao;
import com.example.changshuai.microuniversity.entity.PeripheralShop;
import com.example.changshuai.microuniversity.listener.TopBackListener;

import java.util.List;

public class PeripheralFoodActivity extends AppCompatActivity {
    private TextView action_bar_title;
    private ImageView action_bar_back;

    private ListView pf_list;

    private PeripheralFoodDao peripheralFoodDao;
    private List<PeripheralShop> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral_food);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("周边美食");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        peripheralFoodDao = new PeripheralFoodDao(this);
        if (peripheralFoodDao.findAll().size() == 0){
            addFakeData();
        }
        list = peripheralFoodDao.findAll();
        pf_list = (ListView) findViewById(R.id.pf_list);
        pf_list.setAdapter(new PSListViewApdater(this));
    }

    public void addFakeData(){
        long basenumber = 13500000000l;
        for(int i = 0;i<10;i++){
            peripheralFoodDao.add("张三"+i, "师生苑"+i, "鸡公煲"+i, String.valueOf(basenumber+i));
        }
        Toast.makeText(this, "数据添加完毕", Toast.LENGTH_LONG).show();
    }

    private class PSListViewApdater extends BaseAdapter {
        private Context context;
        private TextView ilpf_telephone;
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

            TextView ilpf_name = (TextView) view.findViewById(R.id.ilps_name);
            TextView ilpf_context = (TextView) view.findViewById(R.id.ilps_context);
            TextView ilpf_address = (TextView) view.findViewById(R.id.ilps_address);
            ilpf_telephone = (TextView) view.findViewById(R.id.ilps_telephone);
            Button ilpf_call = (Button) view.findViewById(R.id.ilps_call);
            ilpf_name.setText(list.get(position).getName());
            ilpf_context.setText(list.get(position).getContext());
            ilpf_address.setText(list.get(position).getAddress());
            ilpf_telephone.setText(list.get(position).getTelephone());

            ilpf_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Telphone = ilpf_telephone.getText().toString();
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

