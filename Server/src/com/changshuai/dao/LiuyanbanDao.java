package com.changshuai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.changshuai.model.Liuyanban;
import com.changshuai.origin.base.DAOBase;
import com.changshuai.util.Tool;


public class LiuyanbanDao {

	public  void delete( String id ){
			DAOBase dao = new DAOBase(); 
			String sql = " delete from  liuyanban   where  id=" + id  ; 
			dao.executeSql(sql);   	  
	}
	
	public  int  save( Liuyanban object ){

    	DAOBase dao = new DAOBase(); 
		String sql =  "insert into   liuyanban ( " + 
		         			" biaoti, " +  
		         			" neirong, " +  
		         			" shijian, " +  
		         			" uid, " +  
		         			" xingming ) values(' " + 
		         			object.biaoti + "','" + 
		         			object.neirong + "','" + 
		         			Tool.getNowTime() + "','" + 
		         			object.uid + "','" + 
		         			object.xingming + "') ";
		return dao.executeSql(sql);   	 
	}
	
	public  List getAll( String id , String type ){
		
		List<Liuyanban> list = new ArrayList() ;
    	DAOBase dao = new DAOBase();
		try { 
			String sql=" select * from   liuyanban  where 1=1 " ;
			
			if( type != null && !type.equals("") ){
				sql += "  and name = '" + type + "' ";
			}
			sql += " order by id desc  ";  
			
	    	dao.querySql(sql);
    		ResultSet rs = dao.getRes(); 
			while(rs.next()) {
				Liuyanban object = new Liuyanban();
				object.id =   String.valueOf( rs.getInt(1))  ;
         		object.biaoti =   rs.getString("biaoti"); 
         		object.neirong =   rs.getString("neirong"); 
         		object.shijian =   rs.getString("shijian"); 
         		object.uid =   rs.getString("uid"); 
         		object.xingming =   rs.getString("xingming"); 
				list.add(object); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(dao != null)
				dao.release();			
		}
		return list;
	}
	
	
	public  void update( String id , Liuyanban o ){

    	DAOBase dao = new DAOBase(); 
    	String sql = null; 
    	sql = " update   liuyanban  set  " + 
		         			"biaoti =  '" +  o.biaoti + "' , '" + 
		         			"neirong =  '" +  o.neirong + "' , '" + 
		         			"shijian =  '" +  o.shijian + "' , '" + 
		         			"uid =  '" +  o.uid + "' , '" + 
		         			"xingming =  '" +  o.xingming + "' where id = " + id  ; 
		dao.executeSql(sql);   	 
	}

	
	public  Liuyanban  getObjectById( String id ){
		
    	DAOBase dao = new DAOBase();
		Liuyanban  object =  null ; 
		try { 
			String sql = "select * from  liuyanban  where id=" + id ;  
			 
	    	dao.querySql(sql);
    		ResultSet rs = dao.getRes(); 
			if(rs.next()) {

				object = new Liuyanban(); 
				object.id =   String.valueOf( rs.getInt(1))  ;
         		object.biaoti =   rs.getString("biaoti");
         		object.neirong =   rs.getString("neirong");
         		object.shijian =   rs.getString("shijian");
         		object.uid =   rs.getString("uid");
         		object.xingming =   rs.getString("xingming");
				return object ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(dao != null)
				dao.release();			
		}
		return null;
	}
	
		
		
		
}
