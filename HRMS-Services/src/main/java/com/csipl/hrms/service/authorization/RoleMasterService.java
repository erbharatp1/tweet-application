package com.csipl.hrms.service.authorization;

import java.util.List;

import com.csipl.hrms.dto.authorization.EmployeeRoleMasterDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;

public interface RoleMasterService {
 	public List<RoleMaster> getAllRoleMasters();

	 public RoleMaster save(RoleMaster roleMaster);
	 public RoleMaster update(RoleMaster roleMaster);

	public List<RoleMaster> getRoleMasters();

	public List<Object[]> getEmpRoleMastersList(Long companyId);

	public List<Object[]> getDeptRoleMastersList(Long companyId, Long departmentId);
	
	public List<Object[]> getEmpRoleMasters(Long companyId, Long employeeId);

	public List<Object[]> getRolePermission(Long roleId);

}
