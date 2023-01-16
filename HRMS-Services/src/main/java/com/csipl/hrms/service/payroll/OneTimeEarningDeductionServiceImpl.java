package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;

import com.csipl.hrms.service.payroll.repository.OneTimeEarningDeductionRepository;
@Service("oneTimeEarningDeductionService")
public class OneTimeEarningDeductionServiceImpl implements OneTimeEarningDeductionService {
	@Autowired
	private OneTimeEarningDeductionRepository oneTimeEarningDeductionRepository;
	@Override
	public void saveAll(List<OneTimeEarningDeduction> oneTimeEarningDeductionList) {
		for (OneTimeEarningDeduction oneTimeEarningDeductionObj : oneTimeEarningDeductionList) {
			oneTimeEarningDeductionObj.setIsEarningDeduction("N");	
		}
		
		oneTimeEarningDeductionRepository.save(oneTimeEarningDeductionList);
	}
	
	@Override
	public List<OneTimeEarningDeduction> getOneTimeEarningList(Long companyId, String payrollMonth) {
		
		return oneTimeEarningDeductionRepository.findOneTimeEarningList(companyId,payrollMonth);
	}
	
	@Override
	public OneTimeEarningDeduction delete(Long ID) {
		// TODO Auto-generated method stub
		 oneTimeEarningDeductionRepository.delete(ID);
		return null;
	}

	@Override
	public List<OneTimeEarningDeduction> getOneTimeDeductionList(Long companyId, String payrollMonth) {
		// TODO Auto-generated method stub
		return oneTimeEarningDeductionRepository.findOneTimeDeductionList(companyId,payrollMonth);
	}

	@Override
	public List<OneTimeEarningDeduction> findOneTimeDeductionForEmployee(Long employeeId, String deductionMonth) {
		// TODO Auto-generated method stub
		return oneTimeEarningDeductionRepository.findOneTimeDeductionForEmployee(employeeId,deductionMonth);
	}

	@Override
	public List<OneTimeEarningDeduction> findOneTimeEarningForEmployee(Long employeeId, String deductionMonth) {
		// TODO Auto-generated method stub
		return oneTimeEarningDeductionRepository.findOneTimeEarningForEmployee(employeeId,deductionMonth);
	}
	
}
