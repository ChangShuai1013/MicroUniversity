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
import com.example.changshuai.microuniversity.LostAndFoundDetailActivity;
import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.leancloud.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LostAndFoundRecyclerAdapter extends RecyclerView.Adapter<LostAndFoundRecyclerAdapter.MainViewHolder> {
  private Context mContext;
  private List<AVObject> mList;

  public LostAndFoundRecyclerAdapter(List<AVObject> list, Context context) {
    this.mContext = context;
    this.mList = list;
  }

  @Override
  public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_main, parent, false));
  }

  @Override
  public void onBindViewHolder(MainViewHolder holder, final int position) {
    holder.mTitle.setText((CharSequence) mList.get(position).get("thing"));
    holder.mName.setText(mList.get(position).getString("studentid") == null ? "" : mList.get(position).getString("studentid"));
    Picasso.with(mContext).load(mList.get(position).getAVFile("image") == null ? "www" : mList.get(position).getAVFile("image").getUrl()).transform(new RoundedTransformation(9, 0)).into(holder.mPicture);
    holder.mItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(mContext, LostAndFoundDetailActivity.class);
        intent.putExtra("goodsObjectId", mList.get(position).getObjectId());
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    System.out.println("========" + mList.size());
    return mList.size();
  }

  class MainViewHolder extends RecyclerView.ViewHolder {
    private TextView mName;
    private TextView mTitle;
    private CardView mItem;
    private ImageView mPicture;

    public MainViewHolder(View itemView) {
      super(itemView);
      mName = (TextView) itemView.findViewById(R.id.name_item_main);
      mTitle = (TextView) itemView.findViewById(R.id.title_item_main);
      mPicture = (ImageView) itemView.findViewById(R.id.picture_item_main);
      mItem = (CardView) itemView.findViewById(R.id.item_main);
    }
  }
}
