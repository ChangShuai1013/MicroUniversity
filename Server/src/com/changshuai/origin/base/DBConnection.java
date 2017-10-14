package com.changshuai.origin.base;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 


public class DBConnection {
	public Connection conn = null;

	public static final String dbDriver = "com.mysql.jdbc.Driver";
	public static final String url =  "jdbc:mysql://localhost:3306/secondHand_shop?useUnicode=true&characterEncoding=utf-8" ; 
	public static final String user = "root";
	public static final String password = "root";


	public Connection getInstance() throws SQLException {

		try {	
			if(conn == null){
				Class.forName( dbDriver ).newInstance();
				conn = DriverManager.getConnection(url,user,password); 
  			}

		}  catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;	
	}

    public void close(){
    	try {
			if(conn != null && !conn.isClosed())
    		conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
