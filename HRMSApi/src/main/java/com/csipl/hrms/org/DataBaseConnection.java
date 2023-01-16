package com.csipl.hrms.org;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;

public class DataBaseConnection {

	private static Connection con = null;

	private DataBaseConnection() {

	}
	public static Connection getConnection() throws SQLException {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://35.244.1.55:3306/demo?useSSL=false");
			dataSource.setUsername("demo");
			dataSource.setPassword("XSW@3edc");

//			 DriverManagerDataSource dataSource = new DriverManagerDataSource();
//			 dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//			 dataSource.setUrl("jdbc:mysql://10.1.1.100:3306/super_db?useSSL=false");
//			 dataSource.setUsername("root");
//			 dataSource.setPassword("nWekTMKd&K5NkQ");
			con = dataSource.getConnection();
		return con;
	}

	public static Statement getStatement(String schemaName, Connection c) throws SQLException {
		Statement st = c.createStatement();
		st.execute("START TRANSACTION");
		st.execute("Drop Database if exists `" + schemaName + "` ");
		st.execute("create database `" + schemaName + "` ");
		st.execute("use `" + schemaName + "` ");
		st.execute("COMMIT");

		return st;
	}
	@Transactional
	public static Statement dropDataBase(String schemaName, Connection c) throws SQLException {
		Statement st = c.createStatement();
		st.execute("START TRANSACTION");
		st.execute("Drop Database if exists `" + schemaName + "` ");
		st.execute("COMMIT");
		return st;
	}

	
	
}
