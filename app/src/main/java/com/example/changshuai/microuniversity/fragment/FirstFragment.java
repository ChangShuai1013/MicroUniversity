package com.example.changshuai.microuniversity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.changshuai.microuniversity.R;
import com.example.changshuai.microuniversity.apdater.FirstListAdapter;
import com.example.changshuai.microuniversity.apdater.MyViewPagerAdapter;
import com.example.changshuai.microuniversity.entity.FirstListBean;
import com.example.changshuai.microuniversity.utils.FirstListComparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ViewGroup mViewGroup;
    private int[] images = {R.drawable.top_01, R.drawable.top_02, R.drawable.top_03};
    private ImageView[] tips;
    private ImageView[] imageViews;
    private Handler viewPagerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int index = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(index + 1);
            viewPagerHandler.sendEmptyMessageDelayed(1,5000);
        }
    };
    private Context context;
    private ListView mListView;
    private List<FirstListBean> list;
    public static FirstFragment newInstance(String name){
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString("args1", name);
        fragment.setArguments(args);
        return fragment;
    }
    public FirstFragment(){}

//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPager_Main);
        mViewGroup = (ViewGroup) rootView.findViewById(R.id.tips_Main);
        getImages();
        getTips();
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(imageViews);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(imageViews.length * 100);
        viewPagerHandler.sendEmptyMessageDelayed(1, 5000);
        //校区热门消息列表
        mListView = (ListView) rootView.findViewById(R.id.list_Main);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://neuc.nuc.edu.cn/index.htm").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Document doc = Jsoup.parse(res);
                list = new ArrayList<>();
                Elements c42975 = doc.getElementsByClass("c42975");
                Elements timestyle42975 = doc.getElementsByClass("timestyle42975");
                Elements c42976 = doc.getElementsByClass("c42976");
                Elements timestyle42976 = doc.getElementsByClass("timestyle42976");
                String[] title = new String[c42975.size() + c42976.size()];
                int title_int = 0;
                String[] time = new String[timestyle42975.size() + timestyle42976.size()];
                int time_int = 0;
                String[] href = new String[c42975.size() + c42976.size()];
                for (Element element : c42975) {
                    title[title_int] = element.attr("title");
                    href[title_int] = "http://neuc.nuc.edu.cn/" + element.attr("href");
                    title_int++;
                }
                for (Element element : timestyle42975) {
                    time[time_int] = element.text();
                    time_int++;
                }
                for (Element element : c42976) {
                    title[title_int] = element.attr("title");
                    href[title_int] = "http://neuc.nuc.edu.cn/" + element.attr("href");
                    title_int++;
                }
                for (Element element : timestyle42976) {
                    time[time_int] = element.text();
                    time_int++;
                }
                for (int i = 0; i < title.length; i++) {
                    FirstListBean mb = new FirstListBean();
                    mb.setTitle(title[i]);
                    mb.setTime(time[i]);
                    mb.setHref(href[i]);
                    list.add(mb);
                }
                FirstListComparator firstListComparator = new FirstListComparator();
                Collections.sort(list, firstListComparator);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirstListAdapter firstListAdapter = new FirstListAdapter(context, list);
                        mListView.setAdapter(firstListAdapter);
                    }
                });

            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //将图片装载到数组当中
    private void getImages(){
        imageViews = new ImageView[images.length];
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageViews[i] = imageView;
            imageView.setBackgroundResource(images[i]);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //获得ViewPager下方的点，并控制它的变化
    private void getTips(){
        tips = new ImageView[images.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0){
                imageView.setBackgroundResource(R.drawable.tips);
            } else{
                imageView.setBackgroundResource(R.drawable.tips_background);
            }
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(10, 10));
            layout.leftMargin = 5;
            layout.rightMargin = 5;
            mViewGroup.addView(imageView, layout);
        }
    }

    private void setBackground(int selectItems){
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems){
                tips[i].setBackgroundResource(R.drawable.tips);
            } else {
                tips[i].setBackgroundResource(R.drawable.tips_background);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setBackground(position % tips.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
