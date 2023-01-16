package com.csipl.hrms.service.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.service.employee.repository.EmployeePersonalInformationRepository;
import com.csipl.hrms.service.payroll.repository.TdsDeductionEmployeePagingAndFilterRepository;
import com.csipl.hrms.service.payroll.repository.TdsDeductionRepository;

@Service("tdsDeduction")
public class TdsDeductionServiceImpl implements TdsDeductionService {

	@Autowired
	private TdsDeductionRepository tdsDeductionRepository;

	@Autowired
	private TdsDeductionEmployeePagingAndFilterRepository tdsDeductionEmployeePagingAndFilterRepository;
	
	@Autowired
	private EmployeePersonalInformationRepository employeePersonalInformationRepository;
	
	@Override
	@Transactional
	public TdsDeduction save(TdsDeduction tdsDeduction) {
		if (tdsDeduction.getTdsDeductionId() != null && (tdsDeduction.getTdsDeductionId() != 0)) {
			 if(tdsDeductionRepository.deleteTdsDeduction(tdsDeduction.getEmployee().getEmployeeId(), tdsDeduction.getFinancialYear().getFinancialYearId())>0) {
				 tdsDeduction.setTdsDeductionId(0l);
			 }
		}
		return tdsDeductionRepository.save(tdsDeduction);
	}

	@Override
	public List<Object[]> findAlltdsDeduction(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		return tdsDeductionRepository.findAlltdsDeduction(companyId, financialYearId);
	}

	@Override
	public TdsDeduction getTdsDeduction(Long companyId, Long financialYearId, Long employeeId) {
		
		return tdsDeductionRepository.getTdsDeduction(companyId, financialYearId, employeeId);
	}

	@Override
	public List<Object[]> findAllEmployee(EmployeeSearchDTO employeeSearchDto) {
		// TODO Auto-generated method stub
		return tdsDeductionEmployeePagingAndFilterRepository.findAllEmployees(employeeSearchDto);
	}

	@Override
	@Transactional
	public int updateTdsLockUnlockStatus(List<EmployeeDTO> employeeDTOList, String tdsLockUnlockStatus) {
		// TODO Auto-generated method stub

		List<Long> ids = new ArrayList<Long>();
		for (EmployeeDTO employeeDTO : employeeDTOList) {
			ids.add(employeeDTO.getEmployeeId());
		}

		if (tdsLockUnlockStatus.equalsIgnoreCase("Unlocked")) {
			String status = "Initiated";
			return tdsDeductionRepository.updateTdsUnlockStatus(ids, tdsLockUnlockStatus, status);
		} else if (tdsLockUnlockStatus.equalsIgnoreCase("Locked")) {
			return tdsDeductionRepository.updateTdsLockUnlockStatus(ids, tdsLockUnlockStatus);
		} else {
			return 0;
		}

	}

	@Override
	public List<Object[]> findAllEmployeesWithTdsStatus(EmployeeSearchDTO employeeSearchDto, Long financialYearId) {
		// TODO Auto-generated method stub
		 return tdsDeductionEmployeePagingAndFilterRepository.findAllEmployeesWithTdsStatus(employeeSearchDto, financialYearId);
	}

	@Override
	public String getTdsLockUnlockStatus(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return tdsDeductionRepository.getTdsLockUnlockStatus(companyId, employeeId);
	}

	@Override
	public List<Object[]> getTdsSummary(Long companyId, Long financialYearId, Long employeeId) {
		// TODO Auto-generated method stub
		return tdsDeductionRepository.getTdsSummary( companyId,  financialYearId,  employeeId);
	}

	@Override
	@Transactional
	public void resetTdsScheme(List<EmployeeDTO> employeeDTOList) {
		String status=null;
		List<Long> employeeIdList = employeeDTOList.stream().map(pm ->pm.getEmployeeId()).collect(Collectors.toList());  
		employeePersonalInformationRepository.updateTdsPlanTypeStatus(status, employeeIdList);
	}
	
}
