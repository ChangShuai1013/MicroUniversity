package com.example.changshuai.microuniversity.utils;

import com.example.changshuai.microuniversity.entity.FirstListBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by apple on 2017/5/28.
 */

public class FirstListComparator implements Comparator {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    @Override
    public int compare(Object o1, Object o2) {
        FirstListBean f1 = (FirstListBean) o1;
        FirstListBean f2 = (FirstListBean) o2;

        try {
            Date d1 = simpleDateFormat.parse(f1.getTime());
            Date d2 = simpleDateFormat.parse(f2.getTime());
            if (d1.getTime() > d2.getTime()) {
                return -1;
            } else {
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
