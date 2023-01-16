package com.csipl.hrms.dto.common;

import java.util.Date;
import java.util.List;

 

public class EmailConfigurationDTO  {
	 
	private Long emailConfigureId;

	private String activeStatus;

	private String auth;
	private String sslName;

	private Long companyId;

	 
	private Date dateCreated;

	private String host;

	private String password;

	private int port;

	private String protocol;

	private String serverType;

	private String starttlsName;

	private String userName;

	 

	public Long getEmailConfigureId() {
		return emailConfigureId;
	}

	public void setEmailConfigureId(Long emailConfigureId) {
		this.emailConfigureId = emailConfigureId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}



	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getSslName() {
		return sslName;
	}

	public void setSslName(String sslName) {
		this.sslName = sslName;
	}

	public String getStarttlsName() {
		return starttlsName;
	}

	public void setStarttlsName(String starttlsName) {
		this.starttlsName = starttlsName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	 

 
	
	}