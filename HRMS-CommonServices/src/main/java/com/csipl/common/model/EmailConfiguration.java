package com.csipl.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the EmailConfugration database table.
 * 
 */
@Entity
@NamedQuery(name="EmailConfiguration.findAll", query="SELECT e FROM EmailConfiguration e")
public class EmailConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long emailConfigureId;

	private String activeStatus;

	private String auth;

	private Long companyId;

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	private String host;

	private String password;

	private int port;

	private String protocol;

	private String serverType;

	private String sslName;

	private String starttlsName;

	private String userName;

	 
	 
	public Long getEmailConfigureId() {
		return this.emailConfigureId;
	}

	public void setEmailConfigureId(Long emailConfigureId) {
		this.emailConfigureId = emailConfigureId;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAuth() {
		return this.auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServerType() {
		return this.serverType;
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

	@Override
	public String toString() {
		return "EmailConfiguration [emailConfigureId=" + emailConfigureId + ", activeStatus=" + activeStatus + ", auth="
				+ auth + ", companyId=" + companyId + ", dateCreated=" + dateCreated + ", host=" + host + ", password="
				+ password + ", port=" + port + ", protocol=" + protocol + ", serverType=" + serverType + ", sslName="
				+ sslName + ", starttlsName=" + starttlsName + ", userName=" + userName + "]";
	}
 
	 
}