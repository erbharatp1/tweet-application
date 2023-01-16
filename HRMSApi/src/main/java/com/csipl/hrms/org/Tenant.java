package com.csipl.hrms.org;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the Tenants database table.
 * 
 */
 
public class Tenant   {
	 
	private int id;

	private int countryId;

	 
	private Date createdDate;

	private String identifier;

	 
	private Date joinedDate;

	 
	private Date lastmodifiedDate;

	private String name;

	private BigInteger oltpId;

	private BigInteger reportId;

	private String timezoneId;

	//bi-directional many-to-one association to TenantServerConnection
	@OneToMany(mappedBy="tenant" , cascade = CascadeType.ALL)
	private List<TenantServerConnection> tenantServerConnections;

	public Tenant() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getJoinedDate() {
		return this.joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Date getLastmodifiedDate() {
		return this.lastmodifiedDate;
	}

	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getOltpId() {
		return this.oltpId;
	}

	public void setOltpId(BigInteger oltpId) {
		this.oltpId = oltpId;
	}

	public BigInteger getReportId() {
		return this.reportId;
	}

	public void setReportId(BigInteger reportId) {
		this.reportId = reportId;
	}

	public String getTimezoneId() {
		return this.timezoneId;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public List<TenantServerConnection> getTenantServerConnections() {
		return this.tenantServerConnections;
	}

	public void setTenantServerConnections(List<TenantServerConnection> tenantServerConnections) {
		this.tenantServerConnections = tenantServerConnections;
	}

	public TenantServerConnection addTenantServerConnection(TenantServerConnection tenantServerConnection) {
		getTenantServerConnections().add(tenantServerConnection);
		tenantServerConnection.setTenant(this);

		return tenantServerConnection;
	}

	public TenantServerConnection removeTenantServerConnection(TenantServerConnection tenantServerConnection) {
		getTenantServerConnections().remove(tenantServerConnection);
		tenantServerConnection.setTenant(null);

		return tenantServerConnection;
	}

}