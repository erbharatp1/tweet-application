package com.csipl.hrms.config;

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.org.Tenant;

public class TokenProvider {
	private Long id;
	private String firstName;
//	private String lastName;
	private String username;
//	private String password;
	private String email;
	private Boolean IsTenantOwner;
	//private List<UserRolePermissionDTO> dto;
	private static TokenProvider tokenProvider;
	private Tenant tenant;
	private String tenantId;
	private Company userCompany;
	//private Persona persona;
	private static ThreadLocal<TokenProvider> tokenProviderLocal = new ThreadLocal<TokenProvider>();
	public static TokenProvider getTokenProvider() {
		return tokenProvider;
	}

	public void setTokenProvider(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsTenantOwner() {
		return IsTenantOwner;
	}

	public void setIsTenantOwner(Boolean isTenantOwner) {
		IsTenantOwner = isTenantOwner;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Company getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(Company userCompany) {
		this.userCompany = userCompany;
	}

	public static ThreadLocal<TokenProvider> getTokenProviderLocal() {
		return tokenProviderLocal;
	}

	public static void setTokenProviderLocal(ThreadLocal<TokenProvider> tokenProviderLocal) {
		TokenProvider.tokenProviderLocal = tokenProviderLocal;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
