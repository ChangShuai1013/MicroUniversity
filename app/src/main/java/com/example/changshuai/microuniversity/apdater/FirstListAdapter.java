package com.example.changshuai.microuniversity.apdater;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.entity.FirstListBean;

import java.util.ArrayList;
import java.util.List;

public class FirstListAdapter extends BaseAdapter {
    private List<FirstListBean> list = new ArrayList<>();
    private Context context;

    public FirstListAdapter(Context context, List<FirstListBean> list) {
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view =null;
        if(convertView==null){
            view = View.inflate(context, R.layout.item_list_first, null);
        }else{
            view = convertView;
        }
        TextView ilf_title = (TextView) view.findViewById(R.id.ilf_title);
        TextView ilf_time = (TextView) view.findViewById(R.id.ilf_time);
        ilf_title.setText(list.get(position).getTitle());
        ilf_time.setText(list.get(position).getTime());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", list.get(position).getHref());
                intent.setAction("first_list_context");
                context.startActivity(intent);
            }
        });
        return view;
    }
}
