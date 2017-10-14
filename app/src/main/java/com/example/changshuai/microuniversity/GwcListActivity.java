package com.example.changshuai.microuniversity;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.changshuai.microuniversity.api.HttpApiAccessor;
import com.example.changshuai.microuniversity.db.DbControl;
import com.example.changshuai.microuniversity.entity.GwcInfoBean;
import com.example.changshuai.microuniversity.listener.TopBackListener;
import com.example.changshuai.microuniversity.utils.Constants;

import java.util.HashMap;
import java.util.List;

public class GwcListActivity extends ListActivity {
    private static final int DELETE_ID = Menu.FIRST;

    private TextView action_bar_title;
    private ImageView action_bar_back;

    private Button btn_add;
    private NoteAdapter adapter;
    private List<GwcInfoBean> list;
    private HashMap orderMap;

    public static class ViewHolder {
        public TextView tvName;
        public TextView tvPrice;
        public TextView tvId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwc_list);

        action_bar_title = (TextView) findViewById(R.id.action_bar_title1);
        action_bar_title.setText("购物车");
        action_bar_back = (ImageView) findViewById(R.id.action_bar_back);
        action_bar_back.setOnClickListener(new TopBackListener(this, action_bar_back));

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(GwcListActivity.this);
            }
        });

        new LoadTask().execute();
        registerForContextMenu(getListView());
    }


    private void showDialog(Context context) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.submit_order, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        final EditText so_name = (EditText) promptsView.findViewById(R.id.so_name);
        final EditText so_telephone = (EditText) promptsView.findViewById(R.id.so_telephone);
        final EditText so_address = (EditText) promptsView.findViewById(R.id.so_address);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("提交订单")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int pid) {

                                String name = so_name.getText().toString().trim() + "--" + so_telephone.getText().toString().trim();
                                String address = so_address.getText().toString().trim();

                                orderMap = new HashMap();

                                String shop_name = "";
                                double price = 0d;
                                String seat = "";

                                for (int i = 0; i < list.size(); i++) {
                                    shop_name += list.get(i).getShop_name() + "--" + list.get(i).getPrice() + " ,";
                                    seat += list.get(i).getSeat() + "_";
                                    price += Double.valueOf(list.get(i).getPrice());
                                }

                                orderMap.put("order.shop_id", Constants.userId);
                                orderMap.put("order.shop_name", shop_name);
                                orderMap.put("order.seat", name);
                                orderMap.put("order.address", address);
                                orderMap.put("order.price", String.valueOf(price));
                                orderMap.put("shop_ids", seat);
//                                orderMap.put("order.leixing", "外卖");
//                                orderMap.put("order.dizhi", "地址");

//						发送订单信息给服务端
                                new AddTask().execute();
                                new LoadTask().execute();
                            }
                        });

        alertDialogBuilder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
//            	从sqlite数据库清空数据
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                DbControl dbControl = new DbControl(GwcListActivity.this);
                GwcInfoBean object = list.get(Long.valueOf(info.id).intValue());
                dbControl.deleteGwc(GwcListActivity.this, object.getId());
                new LoadTask().execute();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public class LoadTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            DbControl dbControl = new DbControl(GwcListActivity.this);
            list = dbControl.getAllGwc(GwcListActivity.this);
            return null;
        }

        protected void onPostExecute(Void result) {
            adapter = new NoteAdapter();
            setListAdapter(adapter);
            removeDialog(0);
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            showDialog(0);
            super.onPreExecute();
        }
    }

    public class AddTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            String result = HttpApiAccessor.saveOrder(orderMap);
            DbControl dbControl = new DbControl(GwcListActivity.this);
            dbControl.deleteAllGwc(GwcListActivity.this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GwcListActivity.this, "下订单成功！", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        protected void onPostExecute(Void result) {
            adapter = new NoteAdapter();
            setListAdapter(adapter);
            removeDialog(0);
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            showDialog(0);
            super.onPreExecute();
        }
    }



    public class NoteAdapter extends BaseAdapter {

        public int getCount() {
            return list.size();
        }

        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.gwc_item, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.name);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.price);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(list.get(position).getShop_name());
            holder.tvPrice.setText(list.get(position).getPrice());
            return convertView;
        }

    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }


}
