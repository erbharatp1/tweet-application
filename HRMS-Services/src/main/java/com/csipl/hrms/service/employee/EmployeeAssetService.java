package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.EmployeeAsset;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.ProfessionalInformation;

public interface EmployeeAssetService {
	
	 public List<EmployeeAsset> saveAll(List<EmployeeAsset> employeeAsset);
	 public List<EmployeeAsset> findAllemployeeAssets(String empId);
	 public List<Object[]> findEmpAssetsByCompanyId(String companyId);
	 public int empAssetsStatusUpdate(String employeeAssetsId);
	 public EmployeeAsset update(EmployeeAsset employeeAsset);
	 public void delete(Long assetsId);
	 public int empAssetsCount(Long employeeId);
	public List<Object[]> findSortedEmpAssetsByCompanyId(String companyId);
	


}
