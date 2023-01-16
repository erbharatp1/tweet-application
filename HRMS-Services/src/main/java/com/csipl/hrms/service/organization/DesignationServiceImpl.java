package com.csipl.hrms.service.organization;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.organisation.Designation;
import com.csipl.hrms.service.organization.repository.DesignationRepository;

@Service("designationService")
@Transactional
public class DesignationServiceImpl implements DesignationService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(DesignationServiceImpl.class);

	@Autowired
	private DesignationRepository designationRepository;

	
	/**
	 * To get List of designations from Database based on companyId
	 */
	@Override
	public List<Designation> findAllDesignation(Long companyId) {
		logger.info("DesignationServiceImpl.findAllDesignation()" + companyId);

		return designationRepository.findAllDesignations(companyId);
	}

	/**
	 * To get List of designations from Database based on companyId and departmentId
	 */
	@Override
	public List<Object[]> designationListBasedOnDepartmnt(Long companyId, Long departmentId) {
		logger.info("DesignationServiceImpl.designationListBasedOnDepartmnt()");
		return designationRepository.designationListBasedOnDepartmnt(companyId, departmentId);
	}
	

	/**
	 * To get  of designations from Database based on  departmentId
	 */
	@Override
	public Designation findById(Long departmentId) {
		logger.info("DesignationServiceImpl.findById()");
		return designationRepository.findOne(departmentId);
	}

	@Override
	@Transactional
	public Designation save(Designation designation) {
		  if (designation.getDesignationId()!=null && designation.getDesignationId()!=0 ) {
			  designationRepository.deleteDeptDesigById(designation.getDesignationId());
		}
		return   designationRepository.save(designation);
	}

	@Override
	@Transactional
	public void updateById(Designation designation) {
		// TODO Auto-generated method stub
		
		  designationRepository.updateById(designation.getDesignationId(), designation.getActiveStatus() );
	}

	@Override
	
	public List<Object[]> getDesigById(Long designationId) {
		return designationRepository.designationByDesigId(designationId);
	}

	@Override
	public List<Designation> findAllActiveDesignation(Long companyId) {
		
		return designationRepository.findAllActiveDesignations(companyId);
	}

//	@Override
//	public void deleteDeptDesigById(DeptDesignationMapping deptDesignationMapping) {
//		logger.info("DesignationServiceImpl.deleteDeptDesigById()");
//		designationRepository.deleteDeptDesigById(deptDesignationMapping.getDesignation().getDesignationId());
//	}

//	@Override
//	public List<Designation> save(List<Designation> designation) {
//		// TODO Auto-generated method stub
//		List<Designation> designationList=(List<Designation>) designationRepository.save(designation);
//		return  designationList;
//	}
	
	
}
