package com.csipl.hrms.service.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.organisation.Designation;

public interface DesignationRepository extends CrudRepository<Designation, Long> {
	public static final String GET_DEGINATION_BY_ID = "SELECT d.designationId ,d .designationName,d.companyId ,d.groupId , dept.departmentId,dept.departmentName,dept.userId,dept.activeStatus ,d.userIdUpdate FROM Designation d join DeptDesignationMapping dm on d.designationId=dm.designationId join Department dept on dept.departmentId=dm.departmentId where d.designationId=?1 ";
	public static final String DELETE_DEPT_DESIGNATION_BY_ID = "Delete  From DeptDesignationMapping Where designationId=?1";

	@Query("from Designation where companyId=?1 ORDER BY  designationId  DESC ")
	public List<Designation> findAllDesignations(Long companyId);

	public static final String GET_DESIGNATIONLIST_BASED_ON_DEPTID = "SELECT d.designationId, d.designationName,"
			+ " dd.departmentId ,d.companyId,d.userId,d.activeStatus from Designation d join DeptDesignationMapping dd "
			+ " on d.designationId=dd.designationId and dd.activeStatus='" + StatusMessage.ACTIVE_CODE + "'"
			+ " where d.companyId=?1 and dd.departmentId=?2" + " and d.activeStatus='" + StatusMessage.ACTIVE_CODE
			+ "' GROUP by d.designationId";

	@Query(value = GET_DESIGNATIONLIST_BASED_ON_DEPTID, nativeQuery = true)
	public List<Object[]> designationListBasedOnDepartmnt(Long companyId, Long departmentId);
//	
//	@Query("from Designation where companyId=?1 And departmentId=?2 ORDER BY  designationId  DESC ")
//	public List<Designation> designationListBasedOnDepartmnt(Long companyId, Long designationId);

	@Query(value = GET_DEGINATION_BY_ID, nativeQuery = true)
	public List<Object[]> designationByDesigId(Long designationId);

	@Modifying
	@Query("Update Designation d SET d.activeStatus=:status WHERE d.designationId=:designationId")
	// @Query("Update activeStatus=?2 Designation d where designationId=?1 ")
	public void updateById(@Param("designationId") Long designationId, @Param("status") String status);

	@Modifying
	@Query(value = DELETE_DEPT_DESIGNATION_BY_ID, nativeQuery = true)
	public void deleteDeptDesigById(Long designationId);

	@Query("from Designation where companyId=?1 and activeStatus='" + StatusMessage.ACTIVE_CODE
			+ "' ORDER BY designationId  DESC ")
	public List<Designation> findAllActiveDesignations(Long companyId);
}
