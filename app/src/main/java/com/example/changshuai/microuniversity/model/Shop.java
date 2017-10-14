package com.example.changshuai.microuniversity.model;

import java.io.Serializable;


/**
 * 商品的pojo
 *
 * @author Administrator
 */
public class Shop implements Serializable {

    public int shop_id = -1;
    public String shop_name;
    public String shop_pic = null;
    public String shop_description;
    public float shop_price;
    public float shop_discount_price;

    public String kouwei, renqun;


    /**
     * flag 	0--特色
     * 1---精品推荐
     * 2---普通
     */
    public int shop_flag;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_pic() {
        return shop_pic;
    }

    public void setShop_pic(String shop_pic) {
        this.shop_pic = shop_pic;
    }

    public String getShop_description() {
        return shop_description;
    }

    public void setShop_description(String shop_description) {
        this.shop_description = shop_description;
    }

    public float getShop_price() {
        return shop_price;
    }

    public void setShop_price(float shop_price) {
        this.shop_price = shop_price;
    }

    public float getShop_discount_price() {
        return shop_discount_price;
    }

    public void setShop_discount_price(float shop_discount_price) {
        this.shop_discount_price = shop_discount_price;
    }

    public int getShop_flag() {
        return shop_flag;
    }

    public void setShop_flag(int shop_flag) {
        this.shop_flag = shop_flag;
    }

    public String getKouwei() {
        return kouwei;
    }

    public void setKouwei(String kouwei) {
        this.kouwei = kouwei;
    }

    public String getRenqun() {
        return renqun;
    }

    public void setRenqun(String renqun) {
        this.renqun = renqun;
    }


}
