package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.RoleMaster;

public interface RoleMasterRepository extends CrudRepository<RoleMaster, Long> {

	@Query(" from RoleMaster WHERE activeStatus='AC' ")
	public List<RoleMaster> findAllRoleMasters();

	@Query("  from RoleMaster where roleId = ?1")
	public RoleMaster getRoleMasterId(long role);

	@Query(" from RoleMaster where roleId = ?1")
	public RoleMaster getRoleID(Long roleId);

	@Query(value = "select * from RoleMaster WHERE roleDescription  IN(roleDescription='Developer')", nativeQuery = true)
	public List<RoleMaster> getRoleMastersList();

	@Query(value = "select e.firstName,e.lastName,  e.employeeId,e.employeeCode ,e.userId , dept.departmentName,al.roleDescription,al.roleId ,dept.departmentId,u.userRolesSrNo from UserRoles u join Users user on u.userId = user.userId left join RoleMaster al on u.roleId = al.roleId left join Employee e on user.nameOfUser= e.employeeCode  JOIN Department dept on e.departmentId=dept.departmentId  AND  al.roleDescription  IN(al.roleDescription='Developer') and e.companyId=?1  AND e.activeStatus= 'AC' ", nativeQuery = true)
	public List<Object[]> fatchAllEmpRoleMasters(Long companyId);

	@Query(value = "select e.firstName,e.lastName,  e.employeeId,e.employeeCode ,e.userId , dept.departmentName,al.roleDescription,al.roleId ,dept.departmentId,u.userRolesSrNo  from UserRoles u join Users user on u.userId = user.userId  left join RoleMaster al on u.roleId = al.roleId  left join Employee e on user.nameOfUser= e.employeeCode  JOIN Department dept on e.departmentId=dept.departmentId  AND  al.roleDescription  IN(al.roleDescription='Developer') and e.companyId=?1   AND e.activeStatus='AC'  AND e.departmentId=?2  ", nativeQuery = true)
	public List<Object[]> fatchDeptRoleMastersList(Long companyId, Long departmentId);

	@Query(value = "select e.firstName,e.lastName, e.employeeId,e.employeeCode ,e.userId , dept.departmentName,al.roleDescription,al.roleId ,dept.departmentId,  u.userRolesSrNo,e.employeeLogoPath,e.dateOfJoining,desi.designationName,gs.gradesName from UserRoles u join Users user on u.userId = user.userId left join  RoleMaster al on u.roleId = al.roleId left join Employee e on user.nameOfUser= e.employeeCode JOIN Designation desi on e.designationId=desi.designationId  JOIN Grades gs on e.gradesId=gs.gradesId  JOIN Department dept on e.departmentId=dept.departmentId AND al.roleDescription IN(al.roleDescription='Developer') and e.companyId=?1 AND e.activeStatus='AC' and e.employeeId=?2", nativeQuery = true)
	public List<Object[]> fatchEmpRoleMastersList(Long companyId, Long employeeId);

	@Query(value = "select mm.menuName ,sam.title FROM MenuMaster mm JOIN SubMenuMaster smm on mm.menuId=smm.menuId JOIN SubmenuActionMaster sam on smm.subMenuId=sam.submenuId JOIN RoleSubmenuActionMaster ram on ram.submenuActionId=sam.submenuActionId JOIN RoleMaster rm on rm.roleId=ram.roleId WHERE ram.status='AC' AND  ram.roleId=?1", nativeQuery = true)
	public List<Object[]> fatchRolePermission(Long roleId);

}