package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;

@Service
public class PayrollDateCalculationServiceImpl implements PayrollDateCalculationService {
	@Autowired
	ReportPayOutRepository repository;
	DateUtils dateUtils = new DateUtils();

	@Override
	public List<Object[]> employeeSalarySlipProcessMonthLastSix(Long employeeId, Long companyId) {
		return repository.lastSixReports(companyId, employeeId);
	}
}

 
