package com.csipl.common.services.permission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.ObjectsInSystemInRole;

public interface RolesPermissionRepository extends CrudRepository<ObjectsInSystemInRole, Long> {

//String fetchRolesPermissionList ="SELECT o.objectId,trim(os.objectTechnicalName) as Menu, concat(\"[\",GROUP_CONCAT(concat(\"'\",rs.roleDescription,\"'\")),\"]\") as Roles FROM  ObjectsInSystemInRole o\r\n" + 
//		"join RoleMaster rs on rs.roleId=o.roleId\r\n" + 
//		"join ObjectsInSystem os on os.objectId=o.objectId\r\n" + 
//		"group by o.objectId" ;
//	String fetchRolesPermissionList="SELECT os.menuName as MainMenu ,trim(o.submenuName) as subMenu, concat(\"[\",GROUP_CONCAT(concat(\"'\",rs.roleDescription,\"'\")),\"]\") as Roles\r\n" + 
//			"	FROM  SubMenuMaster o \r\n" + 
//			"				join RoleMaster rs on rs.roleId=o.roleId \r\n" + 
//			"				join MenuMaster os on os.menuId=o.menuId\r\n" + 
//			"	            where o.status=\"AC\"\r\n" + 
//			"				group by o.subMenuId";
	
	
String fetchRolesPermissionList ="SELECT os.menuName as MainMenu ,trim(o.submenuName) as subMenu, GROUP_CONCAT(rs.roleDescription) as Roles\r\n" + 
		"	FROM  SubMenuMaster o \r\n" + 
		"				join RoleMaster rs on rs.roleId=o.roleId \r\n" + 
		"				join MenuMaster os on os.menuId=o.menuId\r\n" + 
		"	            where o.status=\"AC\"\r\n" + 
		"                group by o.menuName" ;
	@Query(value=fetchRolesPermissionList ,nativeQuery = true)
	public List<Object[]> getAllRolesPermission();
	

}
