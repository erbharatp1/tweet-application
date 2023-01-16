package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.dto.payroll.HoldSalaryDTO;
import com.csipl.hrms.model.payroll.HoldSalary;
import com.csipl.hrms.service.payroll.repository.HoldSalaryPagingAndFilterRepository;
import com.csipl.hrms.service.payroll.repository.HoldSalaryRepository;
@Service("holdSalaryService")
public class HoldSalaryServiceImpl implements HoldSalaryService{


	@Autowired
	private HoldSalaryPagingAndFilterRepository holdSalaryPagingAndFilterRepository;
	
	@Autowired
	private HoldSalaryRepository holdSalaryRepository;
	
//	@Autowired
//	private HoldSalaryPagingAndFilterRepository holdSalaryPagingAndFilterRepository;
//	
	@Override
	public HoldSalary save(HoldSalary holdSalary) {
	
		return holdSalaryRepository.save(holdSalary);
	}

//	@Override
//	public List<HoldSalary> findAllHoldSalary(Long companyId) {
//	
//		return holdSalaryRepository.findAllHoldSalary(companyId);
//	}

	@Override
	public HoldSalary findHoldSalaryById(Long holdSalaryId) {
	
		return holdSalaryRepository.findHoldSalaryById(holdSalaryId);
	}

	@Override
	public void deleteById(Long holdSalaryId) {
		// TODO Auto-generated method stub
		 holdSalaryRepository.delete(holdSalaryId);
	}

	@Override
	public List<Object[]> findAllHoldSalary(Long companyId, String payrollMonth) {
		// TODO Auto-generated method stub
		return holdSalaryRepository.findAllHoldSalary(companyId,payrollMonth);
	}

	@Override
	public List<Object[]> holdSalarySearch(HoldSalaryDTO holdSalarySearchDto) {
		// TODO Auto-generated method stub
		return holdSalaryPagingAndFilterRepository.holdSalarySearch(holdSalarySearchDto);
	}

	@Override
	public List<HoldSalary> searchEmployeeHoldDetails(Long employeeId) {
		
		return holdSalaryRepository.searchEmployeeHoldDetails(employeeId);
	}

//	@Override
//	public List<Object[]> holdSalarySearch(HoldSalaryDTO holdSalarySearchDto) {
//		// TODO Auto-generated method stub
//		return holdSalaryPagingAndFilterRepository.holdSalarySearch(holdSalarySearchDto);
//	}

//	@Override
//	public List<Object[]> holdSalarySearch(Long companyId, HoldSalaryDTO holdSalarySearchDto) {
//	//	return holdSalaryRepository.holdSalarySearch(companyId, holdSalarySearchDto);
//		return null;
//	}



	

}
