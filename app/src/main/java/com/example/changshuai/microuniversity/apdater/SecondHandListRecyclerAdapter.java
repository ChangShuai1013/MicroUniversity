package com.example.changshuai.microuniversity.apdater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.SecondHandDetailActivity;
import com.example.changshuai.microuniversity.leancloud.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apple on 2017/3/29.
 */

public class SecondHandListRecyclerAdapter extends RecyclerView.Adapter<SecondHandListRecyclerAdapter.MainViewHolder> {
    private Context mContext;
    private List<AVObject> mList;

    public SecondHandListRecyclerAdapter(List<AVObject> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_secondhandlist, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.mTitle.setText((CharSequence) mList.get(position).get("thing"));
        holder.mPrice.setText(mList.get(position).get("price") == null ? "￥" : "￥ " + mList.get(position).get("price"));
        holder.mName.setText(mList.get(position).get("studentid") == null ? "" : mList.get(position).getString("studentid"));
        Picasso.with(mContext).load(mList.get(position).getAVFile("image") == null ? "www" : mList.get(position).getAVFile("image").getUrl()).transform(new RoundedTransformation(9, 0)).into(holder.mPicture);
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SecondHandDetailActivity.class);
                intent.putExtra("goodsObjectId", mList.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPrice;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;

        public MainViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name_item_shl);
            mTitle = (TextView) itemView.findViewById(R.id.title_item_shl);
            mPrice = (TextView) itemView.findViewById(R.id.price_item_shl);
            mPicture = (ImageView) itemView.findViewById(R.id.picture_item_shl);
            mItem = (CardView) itemView.findViewById(R.id.item_shl);
        }
    }
}
