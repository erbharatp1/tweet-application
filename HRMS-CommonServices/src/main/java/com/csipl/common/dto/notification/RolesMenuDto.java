package com.csipl.common.dto.notification;

public class RolesMenuDto {
	String role;
	String menu;
	String subMenu;
	
	
	public RolesMenuDto() {
		
	}
	
	public RolesMenuDto(String role, String menu) {
		super();
		this.role = role;
		this.menu = menu;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}
	
	
   
}
