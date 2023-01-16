package com.csipl.hrms.service.organization;

import java.util.List;

import com.csipl.hrms.model.organisation.Designation;

public interface DesignationService {
//	public List<Designation> save(List<Designation>  designation);
	public  Designation  save( Designation  designation);
 	public List<Designation> findAllDesignation(Long companyId);
 	public List<Object[]> designationListBasedOnDepartmnt(Long companyId,Long departmentId);
 	
 	public Designation findById(Long departmentId );
 	
	public void updateById(Designation designation);
	
	public List<Object[]> getDesigById(Long designationId );
	
	public List<Designation> findAllActiveDesignation(Long companyId);
	
//	public void deleteDeptDesigById(DeptDesignationMapping deptDesignationMapping);
}
