package com.changshuai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.changshuai.model.Shop;
import com.changshuai.origin.base.DAOBase;
import com.changshuai.util.Constant;



public class ShopMgrDao {

	
	public  List search( String name ){
		
		List<Shop> list = new ArrayList() ;
    	DAOBase dao = new DAOBase();
		try { 
			String sql=" select * from " + Constant.DB_PREFIX + "shop_info  where shop_name like  '%" + name + "%'   order by shop_id desc  "  ;  
	    	dao.querySql(sql);
    		ResultSet rs = dao.getRes(); 
			while(rs.next()) {
				Shop object = new Shop();
				object.setShop_id(String.valueOf( rs.getInt(1))  );
				object.setShop_name( rs.getString(2) ) ;
				object.setShop_pic( rs.getString(3) ); 
				object.setShop_description( rs.getString(4) ); 
				object.setShop_price(  String.valueOf( rs.getFloat(5) ) );
				object.setShop_discount_price(  String.valueOf( rs.getFloat(6) ) );
				list.add(object); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(dao != null)
				dao.release();			
		}
		return list;
	}
	
	public int getByName(String name) throws SQLException{
		DAOBase dao = new DAOBase(); 
		String sql = " select shop_id from  " + Constant.DB_PREFIX + "shop_info   where  shop_name= '" + name + "'"; 
		dao.querySql(sql);
		ResultSet rs = dao.getRes();
		int id = -1;
		while (rs.next()) {
			id = rs.getInt(1);
		}
		return id;
	} 
	public  void delete( String id ){
		DAOBase dao = new DAOBase(); 
		String sql = " delete from  " + Constant.DB_PREFIX + "shop_info   where  shop_id=" + id  ; 
		dao.executeSql(sql);   	  
	}
	
	
	public  void save( Shop object ){
    	DAOBase dao = new DAOBase(); 
		String sql =  "insert into " + Constant.DB_PREFIX + "shop_info ( shop_name  ,  shop_pic , shop_description , shop_price , shop_discount_price ) values( '" + 
								object.getShop_name()  + "' , '" + object.getShop_pic()  + "' , '" + object.getShop_description() + "' , " + 
								object.getShop_price()  + " , "  + object.getShop_discount_price() + ")";   
		dao.executeSql(sql);   	 
	}

	
	public  List getAll(){
		
		List<Shop> list = new ArrayList() ;
    	DAOBase dao = new DAOBase();
		try { 
			String sql=" select * from " + Constant.DB_PREFIX + "shop_info  order by shop_id desc  ";  
	    	dao.querySql(sql);
    		ResultSet rs = dao.getRes(); 
			while(rs.next()) {
				Shop object = new Shop();
				object.setShop_id(  String.valueOf( rs.getInt(1))  );
				object.setShop_name( rs.getString(2) ) ;
				object.setShop_pic( rs.getString(3) ); 
				object.setShop_description( rs.getString(4) ); 
				object.setShop_price(  String.valueOf( rs.getFloat(5) ) );
				object.setShop_discount_price(  String.valueOf( rs.getFloat(6) ) );
				list.add(object); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(dao != null)
				dao.release();			
		}
		return list;
	}
	
 
//	public  List getShopsByFlag( String flag ){
//		
//		List<Shop> list = new ArrayList() ;
//    	DAOBase dao = new DAOBase();
//		try { 
//			String sql=" select * from " + Constant.DB_PREFIX + "shop_info  where shop_flag=  " + flag + "     order by shop_id desc  "  ;  
//	    	dao.querySql(sql);
//    		ResultSet rs = dao.getRes(); 
//			while(rs.next()) {
//				Shop object = new Shop();
//				object.setShop_id(  String.valueOf( rs.getInt(1))  );
//				object.setShop_name( rs.getString(2) ) ;
//				object.setShop_pic( rs.getString(3) ); 
//				object.setShop_description( rs.getString(4) ); 
//				object.setShop_price(  String.valueOf( rs.getFloat(5) ) );
//				object.setShop_discount_price(  String.valueOf( rs.getFloat(6) ) );
//				list.add(object); 
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			if(dao != null)
//				dao.release();			
//		}
//		return list;
//	}
}
