package com.changshuai.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.changshuai.origin.base.DBConnection;

public class db {

	private String dbDriver = DBConnection.dbDriver;
	private String sConnStr = DBConnection.url;
	private String userName = DBConnection.user;
	private String passwd = DBConnection.password;

	public Connection connect = null;
	public ResultSet rs = null;

	public db() {
		try {
			Class.forName(dbDriver).newInstance();
			connect = DriverManager.getConnection(sConnStr, userName, passwd);
		} catch (Exception ex) {
			System.out.println("12121");
		}
	}

	public ResultSet executeQuery(String sql) {
		try {
			connect = DriverManager.getConnection(sConnStr, userName, passwd);
			Statement stmt = connect.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return rs;
	}

	public void executeUpdate(String sql) {
		Statement stmt = null;
		rs = null;
		try {
			connect = DriverManager.getConnection(sConnStr, userName, passwd);
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			connect.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
