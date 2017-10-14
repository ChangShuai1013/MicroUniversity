package com.example.changshuai.microuniversity.apdater;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.changshuai.microuniversity.MeReportActivity;
import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.entity.MeReportBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/5/6.
 */

public class MeReportListAdapter extends BaseAdapter {
    List<MeReportBean> meReportBeanList = new ArrayList<>();
    private Context context;
    int position;
    View view;
    public MeReportListAdapter(Context context,List<MeReportBean> meReportBeanList) {
        this.context = context;
        this.meReportBeanList = meReportBeanList;
    }

    @Override
    public int getCount() {
        System.out.println(meReportBeanList.size());
        return meReportBeanList.size();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view =null;
        if(convertView==null){
            view = View.inflate(context, R.layout.item_list_mereport, null);
        }else{
            view = convertView;
        }
        this.position = position;
        TextView ilmp_kind = (TextView) view.findViewById(R.id.ilmp_kind);
        TextView ilmp_time = (TextView) view.findViewById(R.id.ilmp_time);
        TextView ilmp_context = (TextView) view.findViewById(R.id.ilmp_context);
        Button ilmp_delete = (Button) view.findViewById(R.id.ilmp_delete);
        ilmp_kind.setText(meReportBeanList.get(position).getKind());
        ilmp_time.setText(meReportBeanList.get(position).getTime());
        ilmp_context.setText(meReportBeanList.get(position).getThing());
        ilmp_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView) convertView.findViewById(R.id.ilmp_kind)).getText().equals("二手交易")){
                    AVObject meReport = new AVObject("Product");
                    meReport.setObjectId(meReportBeanList.get(position).getObjectId());
                    meReport.deleteInBackground();
                } else if (((TextView) convertView.findViewById(R.id.ilmp_kind)).getText().equals("丢失")){
                    AVObject meReport = new AVObject("LostAndFound");
                    meReport.setObjectId(meReportBeanList.get(position).getObjectId());
                    meReport.deleteInBackground();
                } else if (((TextView) convertView.findViewById(R.id.ilmp_kind)).getText().equals("捡到")){
                    AVObject meReport = new AVObject("LostAndFound");
                    meReport.setObjectId(meReportBeanList.get(position).getObjectId());
                    meReport.deleteInBackground();
                }
            }
        });
        return view;
    }
}
