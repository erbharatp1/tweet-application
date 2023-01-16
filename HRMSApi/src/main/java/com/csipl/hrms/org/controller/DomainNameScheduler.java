package com.csipl.hrms.org.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.csipl.hrms.org.DataBaseConnection;

@Component
public class DomainNameScheduler {
	public List<String> domainNameList = new ArrayList<String>();
	@Autowired
	Environment environment;

//	 @Scheduled(cron = "*/13 * * * * *")
	@Scheduled(cron = "0 59 0 * * ?")
	@PostConstruct
	public List<String> getCustomerResgistrationData() throws SQLException {
		PreparedStatement st = null;
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("db.driver"));
		dataSource.setUrl(environment.getProperty("db.url"));
		dataSource.setUsername(environment.getProperty("db.username"));
		dataSource.setPassword(environment.getProperty("db.password"));
		
		
		Connection c = dataSource.getConnection();
		String data = environment.getProperty("application.deploy");
		domainNameList=new ArrayList<String>(); 
		if (environment.getProperty("application.deploy").equals("prod")) {
			st = c.prepareStatement(
					"select cr.domainName FROM super_db.CustomerRegistration cr where  cr.activeStatus='AC' ");
		} else if (environment.getProperty("application.deploy").equals("demo")) {
			st = c.prepareStatement(
					"select cr.domainName FROM super_db.ProductTrialRegistration cr where  cr.customerFlag='Active' and cr.activeStatus='AC' ");
		}
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			String domainName = rs.getString("domainName");
			domainNameList.add(domainName);
			System.out.println("===========This DomainName Addeddd In List=====" + domainName);
			System.out.println("Schedular Runing==============");
			// dto.setTenants(tenants);
		}
		st.close();
		c.close();
		return domainNameList;
	}
}
