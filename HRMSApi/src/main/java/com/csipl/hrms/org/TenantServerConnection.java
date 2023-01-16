package com.csipl.hrms.org;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the TenantServerConnections database table.
 * 
 */
 
public class TenantServerConnection   {
	private int tenantServerid;

	private byte autoUpdate;

	private int deadlockMaxRetries;

	private int deadlockMaxRetryInterval;

	private int poolAbandonWhenPercentageFull;

	private int poolInitialSize;

	private byte poolLogAbandoned;

	private int poolMaxActive;

	private int poolMaxIdle;

	private int poolMinEvictableIdleTimeMillis;

	private int poolMinIdle;

	private byte poolRemoveAbandoned;

	private int poolRemoveAbandonedTimeout;

	private int poolSuspectTimeout;

	private byte poolTestOnBorrow;

	private int poolTimeBetweenEvictionRunsMillis;

	private int poolValidationInterval;

	private String schemaName;

	private String schemaPassword;

	private String schemaServer;

	private String schemaServerPort;

	private String schemaUsername;

	//bi-directional many-to-one association to Tenant
 
	private Tenant tenant;

	public TenantServerConnection() {
	}

	public int getTenantServerid() {
		return this.tenantServerid;
	}

	public void setTenantServerid(int tenantServerid) {
		this.tenantServerid = tenantServerid;
	}

	public byte getAutoUpdate() {
		return this.autoUpdate;
	}

	public void setAutoUpdate(byte autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public int getDeadlockMaxRetries() {
		return this.deadlockMaxRetries;
	}

	public void setDeadlockMaxRetries(int deadlockMaxRetries) {
		this.deadlockMaxRetries = deadlockMaxRetries;
	}

	public int getDeadlockMaxRetryInterval() {
		return this.deadlockMaxRetryInterval;
	}

	public void setDeadlockMaxRetryInterval(int deadlockMaxRetryInterval) {
		this.deadlockMaxRetryInterval = deadlockMaxRetryInterval;
	}

	public int getPoolAbandonWhenPercentageFull() {
		return this.poolAbandonWhenPercentageFull;
	}

	public void setPoolAbandonWhenPercentageFull(int poolAbandonWhenPercentageFull) {
		this.poolAbandonWhenPercentageFull = poolAbandonWhenPercentageFull;
	}

	public int getPoolInitialSize() {
		return this.poolInitialSize;
	}

	public void setPoolInitialSize(int poolInitialSize) {
		this.poolInitialSize = poolInitialSize;
	}

	public byte getPoolLogAbandoned() {
		return this.poolLogAbandoned;
	}

	public void setPoolLogAbandoned(byte poolLogAbandoned) {
		this.poolLogAbandoned = poolLogAbandoned;
	}

	public int getPoolMaxActive() {
		return this.poolMaxActive;
	}

	public void setPoolMaxActive(int poolMaxActive) {
		this.poolMaxActive = poolMaxActive;
	}

	public int getPoolMaxIdle() {
		return this.poolMaxIdle;
	}

	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}

	public int getPoolMinEvictableIdleTimeMillis() {
		return this.poolMinEvictableIdleTimeMillis;
	}

	public void setPoolMinEvictableIdleTimeMillis(int poolMinEvictableIdleTimeMillis) {
		this.poolMinEvictableIdleTimeMillis = poolMinEvictableIdleTimeMillis;
	}

	public int getPoolMinIdle() {
		return this.poolMinIdle;
	}

	public void setPoolMinIdle(int poolMinIdle) {
		this.poolMinIdle = poolMinIdle;
	}

	public byte getPoolRemoveAbandoned() {
		return this.poolRemoveAbandoned;
	}

	public void setPoolRemoveAbandoned(byte poolRemoveAbandoned) {
		this.poolRemoveAbandoned = poolRemoveAbandoned;
	}

	public int getPoolRemoveAbandonedTimeout() {
		return this.poolRemoveAbandonedTimeout;
	}

	public void setPoolRemoveAbandonedTimeout(int poolRemoveAbandonedTimeout) {
		this.poolRemoveAbandonedTimeout = poolRemoveAbandonedTimeout;
	}

	public int getPoolSuspectTimeout() {
		return this.poolSuspectTimeout;
	}

	public void setPoolSuspectTimeout(int poolSuspectTimeout) {
		this.poolSuspectTimeout = poolSuspectTimeout;
	}

	public byte getPoolTestOnBorrow() {
		return this.poolTestOnBorrow;
	}

	public void setPoolTestOnBorrow(byte poolTestOnBorrow) {
		this.poolTestOnBorrow = poolTestOnBorrow;
	}

	public int getPoolTimeBetweenEvictionRunsMillis() {
		return this.poolTimeBetweenEvictionRunsMillis;
	}

	public void setPoolTimeBetweenEvictionRunsMillis(int poolTimeBetweenEvictionRunsMillis) {
		this.poolTimeBetweenEvictionRunsMillis = poolTimeBetweenEvictionRunsMillis;
	}

	public int getPoolValidationInterval() {
		return this.poolValidationInterval;
	}

	public void setPoolValidationInterval(int poolValidationInterval) {
		this.poolValidationInterval = poolValidationInterval;
	}

	public String getSchemaName() {
		return this.schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getSchemaPassword() {
		return this.schemaPassword;
	}

	public void setSchemaPassword(String schemaPassword) {
		this.schemaPassword = schemaPassword;
	}

	public String getSchemaServer() {
		return this.schemaServer;
	}

	public void setSchemaServer(String schemaServer) {
		this.schemaServer = schemaServer;
	}

	public String getSchemaServerPort() {
		return this.schemaServerPort;
	}

	public void setSchemaServerPort(String schemaServerPort) {
		this.schemaServerPort = schemaServerPort;
	}

	public String getSchemaUsername() {
		return this.schemaUsername;
	}

	public void setSchemaUsername(String schemaUsername) {
		this.schemaUsername = schemaUsername;
	}
	@JsonIgnore
	public Tenant getTenant() {
		return this.tenant;
	}
	@JsonIgnore
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

}