package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.EmployeeAsset;
import com.csipl.hrms.model.employee.EmployeeIdProof;



public interface EmployeeAssetRepository extends CrudRepository<EmployeeAsset, Long> {
	@Query(" from EmployeeAsset where employeeId=?1 AND activeStatus='"+StatusMessage.ACTIVE_CODE+"'")
    public List<EmployeeAsset> findAllEmpAssets(Long employeeId);
	
	@Query(nativeQuery=true, value="SELECT  DISTINCT(ea.employeeId), emp.firstName , emp.lastName, desg.designationName ,emp.employeeCode FROM  EmployeeAssets ea  JOIN Employee emp on ea.employeeId=emp.employeeId join Designation desg  on emp.designationId=desg.designationId where ea.companyId=?1 And emp.activeStatus = '"+StatusMessage.ACTIVE_CODE+"' ORDER BY LENGTH(emp.employeeCode),emp.employeeCode ASC")
    public List<Object[]> findEmpAssetsByCompanyId(Long CompanyId);
	
    @Modifying
	@Query("Update EmployeeAsset e SET e.activeStatus=:status WHERE e.employeeAssetsId=:employeeAssetsId")
	public int empAssetsStatusUpdate(@Param("employeeAssetsId") Long employeeAssetsId, @Param("status") String status);
    
    @Query(nativeQuery=true, value="SELECT count(ea.employeeAssetsId) FROM EmployeeAssets ea WHERE ea.employeeId=?1 and ea.dateTo IS null")
    public int empAssetsCount(Long employeeId);
    
	@Query(nativeQuery=true, value="SELECT  DISTINCT(ea.employeeId), emp.firstName , emp.lastName, desg.designationName ,emp.employeeCode FROM  EmployeeAssets ea  JOIN Employee emp on ea.employeeId=emp.employeeId join Designation desg  on emp.designationId=desg.designationId where ea.companyId=?1 And emp.activeStatus = '"+StatusMessage.ACTIVE_CODE+"' ORDER BY emp.firstName ASC,emp.lastName ASC")
	public List<Object[]> findSortedEmpAssetsByCompanyId(Long CompanyId);
}
