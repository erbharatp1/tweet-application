package com.csipl.hrms.service.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.dto.authorization.EmployeeRoleMasterDTO;
import com.csipl.hrms.model.authoriztion.RoleMaster;
import com.csipl.hrms.service.authorization.repository.RoleMasterRepository;

@Service("roleMasterService")
public class RoleMasterServiceImpl implements RoleMasterService {

	 @Autowired
	  private RoleMasterRepository roleMasterRepository; 
	
	@Override
	public List<RoleMaster> getAllRoleMasters() {
 	 return roleMasterRepository.findAllRoleMasters();
		//return null;
 	}

	@Override
	public RoleMaster save(RoleMaster roleMaster) {
 		return roleMasterRepository.save(roleMaster);
	}

	@Override
	public RoleMaster update(RoleMaster roleMaster) {
 		return null;
	}

	@Override
	public List<RoleMaster> getRoleMasters() {
		 return roleMasterRepository.getRoleMastersList();
	 
	}

	@Override
	public List<Object[]> getEmpRoleMastersList(Long companyId) {
 		 
		return roleMasterRepository.fatchAllEmpRoleMasters(companyId);

	}

	@Override
	public List<Object[]> getDeptRoleMastersList(Long companyId, Long departmentId) {
		return roleMasterRepository.fatchDeptRoleMastersList(companyId,departmentId);
	}

	@Override
	public List<Object[]> getEmpRoleMasters(Long companyId, Long employeeId) {
		
		return roleMasterRepository.fatchEmpRoleMastersList(companyId, employeeId);
	}

	@Override
	public List<Object[]>  getRolePermission(Long roleId) {
		return roleMasterRepository.fatchRolePermission(roleId);
	}

}
