package com.changshuai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.changshuai.model.Shop;
import com.changshuai.order.Order;
import com.changshuai.origin.base.DAOBase;
import com.changshuai.util.Constant;
import com.changshuai.util.Tool;

public class OrderDao {

	public void delete(String id) {
		DAOBase dao = new DAOBase();
		String sql = " delete from  orders  where  id=" + id;
		dao.executeSql(sql);
	}

	public void save(Order object) {

		DAOBase dao = new DAOBase();
		String sql = "insert into  orders  (  shop_id   , seat , description , order_date , address  , price ) values( '"
				+ object.getShop_id() + "' , '" + object.getSeat() + "' , '" + object.getDesc() + "' , '"
				+ Tool.getNowTime() + "' , '" + object.address + "' , '" + object.price + "')";
		dao.executeSql(sql);
	}

	public void updateOrder(String id, String state) {

		DAOBase dao = new DAOBase();
		String sql = " update orders  set  state = '" + state + "'  where  id=" + id;
		dao.executeSql(sql);
	}

	public List getAll() {

		List<Order> list = new ArrayList();
		DAOBase dao = new DAOBase();
		try {
			String sql = " select * from orders order by id desc ";
			dao.querySql(sql);
			ResultSet rs = dao.getRes();
			while (rs.next()) {
				Order object = new Order();
				object.setId(String.valueOf(rs.getInt(1)));

				object.setShop_id(rs.getString("shop_id"));

				object.setSeat(rs.getString(3));
				object.setDesc(rs.getString(4));
				object.setOrder_date(rs.getString(5));

				object.address = rs.getString(6);
				object.price = rs.getString(7);
				object.state = rs.getString("state");

				list.add(object);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dao != null)
				dao.release();
		}
		return list;
	}

	public String tongji(String startDate, String endDate) {

		String sum_price = null;
		DAOBase dao = new DAOBase();
		try {
			String sql = " select sum(price) as sum_price  from orders where order_date > '" + startDate
					+ "' and  order_date < '" + endDate + "'";
			dao.querySql(sql);
			ResultSet rs = dao.getRes();
			while (rs.next()) {
				sum_price = rs.getString("sum_price");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dao != null)
				dao.release();
		}
		return sum_price;
	}

	public Shop getShopById(String shop_id) {

		Shop object = null;
		DAOBase dao = new DAOBase();
		try {
			String sql = " select * from " + Constant.DB_PREFIX + "shop_info  where shop_id=  '" + shop_id
					+ "'     order by shop_id desc  ";
			dao.querySql(sql);
			ResultSet rs = dao.getRes();
			while (rs.next()) {
				object = new Shop();

				object.setShop_id(rs.getString("shop_id"));

				object.setShop_name(rs.getString(2));
				object.setShop_pic(rs.getString(3));
				object.setShop_description(rs.getString(4));
				object.setShop_price(String.valueOf(rs.getFloat(5)));
				object.setShop_discount_price(String.valueOf(rs.getFloat(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dao != null)
				dao.release();
		}
		return object;
	}

}
