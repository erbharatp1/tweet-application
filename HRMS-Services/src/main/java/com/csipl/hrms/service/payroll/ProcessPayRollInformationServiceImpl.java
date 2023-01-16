package com.csipl.hrms.service.payroll;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.organization.repository.DepartmentRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;
import com.hrms.org.payrollprocess.dto.DepartmentProcessDTO;

@Service
public class ProcessPayRollInformationServiceImpl implements ProcessPayRollInformationService {

	private static final Logger log = LoggerFactory.getLogger(ProcessPayRollInformationServiceImpl.class);

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ReportPayOutRepository reportPayOutRepository;

	@Autowired
	PayrollControlService payrollControlService;

	/**
	 * processPayRollinformation
	 * 
	 * @throws Exception
	 */
	@Override
	public List<DepartmentProcessDTO> processPayRollinformation(Long[] departmentIds, String processMonth,
			Long companyId) throws Exception {

		List<DepartmentProcessDTO> departmentProcess = new ArrayList<DepartmentProcessDTO>();

		PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(companyId, processMonth);

		for (Long deptId : departmentIds) {
			Department dept = departmentRepository.findDeptName(deptId);
			DepartmentProcessDTO departmentProcessDTO = new DepartmentProcessDTO();
			departmentProcessDTO.setDepaertmentId(deptId);
			Long process = reportPayOutRepository.checkPayRollForEmpMonth(processMonth,
					departmentProcessDTO.getDepaertmentId());
			Long total = reportPayOutRepository.checkPayRollForEmployeeTotal(departmentProcessDTO.getDepaertmentId());
			Long remiming = total - process;
			departmentProcessDTO.setProcessCount(process);
			departmentProcessDTO.setTotalEmployee(total);
			departmentProcessDTO.setRemaining(remiming);
			departmentProcessDTO.setDepartmentName(dept.getDepartmentName());
			departmentProcessDTO.setProcessMonth(processMonth);
			departmentProcessDTO.setMonthCount(payrollControl.getPayrollDays());
			departmentProcessDTO.setCalanderDate(payrollControl.getProcessMonth());
			departmentProcess.add(departmentProcessDTO);
		}

		return departmentProcess;
	}

	
	@Override
	public DepartmentProcessDTO getPayRollOverview(String processMonth, Long companyId, Date from, Date toDate) {
		DepartmentProcessDTO departmentProcessDTO = new DepartmentProcessDTO();
		List<Object[]> obj = reportPayOutRepository.getPayRollOverview(companyId, processMonth, from, toDate);
		PayrollControl payrollControl = payrollControlService.findPayrollControlByMonth(companyId, processMonth);
		List<Object[]> ctc= reportPayOutRepository.getPayRollOverviewCTC(companyId, processMonth);
		
		for (Object[] data : obj) {
			BigInteger totalEmployee = data[0] != null ? (BigInteger) data[0] : BigInteger.ZERO;
			BigInteger payRollProcessed = data[1] != null ? (BigInteger) data[1] : BigInteger.ZERO;
  
			departmentProcessDTO.setTotalEmployee(Long.valueOf(totalEmployee.toString()));
			departmentProcessDTO.setProcessCount(Long.valueOf(payRollProcessed.toString()));
			departmentProcessDTO.setRemaining(Long.valueOf(totalEmployee.toString())-Long.valueOf(payRollProcessed.toString()));
			departmentProcessDTO.setProcessMonth(processMonth);
			departmentProcessDTO.setMonthCount(payrollControl.getPayrollDays());
		}
		
		for (Object[] data : ctc) {
			BigDecimal totalGross = data[0] != null ? (BigDecimal) data[0] : BigDecimal.ZERO;
			BigDecimal totalDeduction = data[1] != null ? (BigDecimal) data[1] : BigDecimal.ZERO;
			BigDecimal netPayableAmount = data[2] != null ? (BigDecimal) data[2] : BigDecimal.ZERO; 
			BigDecimal totalCTC = data[3] != null ? (BigDecimal) data[3] : BigDecimal.ZERO; 
			departmentProcessDTO.setTotalGross(totalGross);
			departmentProcessDTO.setNetPayableAmount(netPayableAmount);
			departmentProcessDTO.setTotalCTC(totalCTC);
			departmentProcessDTO.setTotalDeduction(totalDeduction);
		}
		
		
		return departmentProcessDTO;
	}
	
}
