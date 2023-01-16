package com.csipl.hrms.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Statement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Created by JavaDeveloperZone on 22-01-2017.
 */
@Component
public class MasterService {

 

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	public static Map<Object, Object> getDataSourceHashMap(Environment environment) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		HashMap hashMap = new HashMap();
		RestTemplate restTemplate = new RestTemplate();

//		DriverManagerDataSource db = new DriverManagerDataSource();
//		 db.setDriverClassName("com.mysql.jdbc.Driver");
//		 db.setUrl("jdbc:mysql://35.244.1.55:3306/super_db?useSSL=false");
//		 db.setUsername("demo");
//		 db.setPassword("XSW@3edc");
//		db.setDriverClassName("com.mysql.jdbc.Driver");
//		db.setUrl("jdbc:mysql://10.1.1.100:3306/super_db?useSSL=false");
//		db.setUsername("root");
//		db.setPassword("nWekTMKd&K5NkQ");

		DriverManagerDataSource db = new DriverManagerDataSource();
		db.setDriverClassName(environment.getProperty("db.driver"));
		db.setUrl(environment.getProperty("db.url"));
		db.setUsername(environment.getProperty("db.username"));
		db.setPassword(environment.getProperty("db.password"));
		
		
		
		Connection c = null;
		TenantDTO dto = new TenantDTO();

		try {
			c = db.getConnection();
			PreparedStatement st = null;
			
			String data =environment.getProperty("application.deploy");
			
			if (environment.getProperty("application.deploy").equals("prod")) {
				st = c.prepareStatement(
						"select tt.id,tt.identifier,tsc.schemaServer ,tsc.schemaName  ,tsc.schemaServerPort,tsc.schemaUsername,"
						+ "tsc.schemaPassword FROM super_db.Tenants tt LEFT JOIN super_db.TenantServerConnections tsc ON tsc.tenantId = tt.id "
						+ " join super_db.CustomerRegistration cr on cr.domainName=tt.identifier where cr.activeStatus='AC' ");
			} else if (environment.getProperty("application.deploy").equals("demo")) {
				st = c.prepareStatement(
						"select tt.id,tt.identifier,tsc.schemaServer ,tsc.schemaName  ,tsc.schemaServerPort,tsc.schemaUsername,tsc.schemaPassword FROM super_db.ProductTrialTenant tt LEFT JOIN super_db.ProductTrialTenantServerConnection tsc ON tsc.tenantId = tt.id ");
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				int a = rs.getInt("id");
				String identifier = rs.getString("identifier");
				String schemaName = rs.getString("schemaName");
				String serverPort = rs.getString("schemaServerPort");
				String userName = rs.getString("schemaUsername");
				String password = rs.getString("schemaPassword");
				String schemaServer = rs.getString("schemaServer");
				String url = "jdbc:mysql://" + schemaServer + ":" + serverPort + "/" + schemaName + "?useSSL=false";
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl(url);
				dataSource.setUsername(userName);
				dataSource.setPassword(password);
				System.out.println("===========Data=====" + identifier);
				hashMap.put(identifier, dataSource);
				// dto.setTenants(tenants);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		hashMap.entrySet().forEach(action -> {
			System.out.println(action.toString());
		});

		return hashMap;
	}

	public TenantDTO getTenantDTO(DriverManagerDataSource db) throws SQLException {
		db.setDriverClassName("com.mysql.jdbc.Driver");
		db.setUrl("jdbc:mysql://10.1.1.100:3306/super_db");
		db.setUsername("root");
		db.setPassword("nWekTMKd&K5NkQ");
		Connection c = null;
		TenantDTO dto = new TenantDTO();

		try {
			c = db.getConnection();
			PreparedStatement st = c.prepareStatement(
					"select tt.id,tt.identifier,tsc.schemaServer ,tsc.schemaName  ,tsc.schemaServerPort,tsc.schemaUsername,tsc.schemaPassword FROM super_db.Tenants tt LEFT JOIN super_db.TenantServerConnections tsc ON tsc.tenantId = tt.id ");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				int a = rs.getInt("id");
				String identifier = rs.getString("identifier");
				String schemaName = rs.getString("schemaName");
				String serverPort = rs.getString("schemaServerPort");
				String userName = rs.getString("schemaUsername");
				String password = rs.getString("schemaPassword");
				String schemaServer = rs.getString("schemaServer");
				String url = "jdbc:mysql://" + schemaServer + ":" + serverPort + "/" + schemaName;
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl(url);
				dataSource.setUsername(userName);
				dataSource.setPassword(password);

				// hashMap.put(identifier), dataSource);
				// dto.setTenants(tenants);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}

		return null;
	}
	
	  public static Map<Object, Object> getDataSourceHashMap()  {
		  
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        HttpEntity <String> entity = new HttpEntity<String>(headers);
	        
	        HashMap hashMap = new HashMap();
	        RestTemplate restTemplate = new RestTemplate();
			 
			
	        DriverManagerDataSource db = new DriverManagerDataSource();
//	        db.setDriverClassName("com.mysql.jdbc.Driver");
//	      		db.setUrl("jdbc:mysql://35.244.1.55:3306/super_db?useSSL=false");
//	      		db.setUsername("demo");
//	      		db.setPassword("XSW@3edc");
			db.setDriverClassName("com.mysql.jdbc.Driver");
			db.setUrl("jdbc:mysql://10.1.1.100:3306/super_db?useSSL=false");
			db.setUsername("root");
			db.setPassword("nWekTMKd&K5NkQ");
	      		
	      		Connection c=null;
	      		TenantDTO dto = new TenantDTO();
	      		
	      		try {
	      			c = db.getConnection();
	      			PreparedStatement  st = c.prepareStatement("select tt.id,tt.identifier,tsc.schemaServer ,tsc.schemaName  ,tsc.schemaServerPort,tsc.schemaUsername,tsc.schemaPassword FROM super_db.Tenants tt LEFT JOIN super_db.TenantServerConnections tsc ON tsc.tenantId = tt.id ");
	      			ResultSet rs=st.executeQuery();
	      			while(rs.next()) {
	      				DriverManagerDataSource dataSource = new DriverManagerDataSource();
	      			int a=	rs.getInt("id");
	      			String identifier = rs.getString("identifier");
	      			String schemaName = rs.getString("schemaName");
	      			String serverPort = rs.getString("schemaServerPort");
	      			String userName = rs.getString("schemaUsername");
	      			String password =rs.getString("schemaPassword");
	      			String schemaServer = rs.getString("schemaServer");
	      			String url = "jdbc:mysql://" + schemaServer + ":" + serverPort +"/"+ schemaName+"?useSSL=false";
	      			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      			dataSource.setUrl(url);
	      			dataSource.setUsername(userName);
	      			dataSource.setPassword(password);
	      			System.out.println("===========Data====="+identifier);
	      			 hashMap.put(identifier, dataSource);
	      			//dto.setTenants(tenants);
	      			}
	      			
	      		} catch (SQLException e) {
	      			// TODO Auto-generated catch block
	      			e.printStackTrace();
	      		}
	      		finally {
	      			try {
						c.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	      		}
	        
	        
			 hashMap.entrySet().forEach(action->{
				 System.out.println(action.toString());
			 });
	 
	        return hashMap;
	    }

}

