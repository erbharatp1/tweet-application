package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.payrollprocess.FinancialMonth;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.payroll.repository.FinancialMonthRepository;
import com.csipl.hrms.service.payroll.repository.PayrollPeriodRepository;

@Service("payrollPeriodService")
public class PayrollPeriodServiceImpl implements PayrollPeriodService {
	@Autowired
	private PayrollPeriodRepository payrollPeriodRepository;

	@Autowired
	FinancialMonthRepository financialMonthRepository;

	@Override
	@Transactional
	public ErrorHandling save(FinancialYear financialYear) {

		ErrorHandling errorHandling = new ErrorHandling();

		if (financialYear.getFinancialYearId() != null && financialYear.getFinancialYearId() != 0) {

			FinancialYear financialYearObj = payrollPeriodRepository.checkFinancialYear(
					financialYear.getFinancialYearId(), financialYear.getFinancialYear(),
					financialYear.getCompany().getCompanyId());

			if (financialYearObj == null) {
				payrollPeriodRepository.deletePayrollControlByfinancialYearId(financialYear.getFinancialYearId());
				payrollPeriodRepository.save(financialYear);
				errorHandling.setErrorMessage(StatusMessage.SDB_YEAR_CHECK_CODE);
			} else {
				errorHandling.setErrorMessage(StatusMessage.PDB_YEAR_CHECK_CODE);
			}

		} else {
			payrollPeriodRepository.save(financialYear);
			errorHandling.setErrorMessage(StatusMessage.SDB_YEAR_CHECK_CODE);

		}
		return errorHandling;

	}

	@Override
	public FinancialYear findFinancialYear(String financialYear, Long companyId) {

		return payrollPeriodRepository.findFinancialYear(financialYear, companyId);
	}

	public FinancialYear findLatestFinancialYear(Long companyId) {
		return payrollPeriodRepository.findLatestFinancialYear(companyId);
	}

	@Override
	public List<FinancialYear> getFinancialYearList(Long companyId) {
		// TODO Auto-generated method stub
		return payrollPeriodRepository.getFinancialYearList(companyId);
	}

	@Override
	@Transactional
	public void updateById(FinancialYear financialYear) {

		if (financialYear.getActiveStatus().equals(StatusMessage.ACTIVE_CODE)) {
			payrollPeriodRepository.updateStatusByCompanyId(financialYear.getCompany().getCompanyId(),
					StatusMessage.DEACTIVE_CODE, StatusMessage.CLOSE_CODE);
			payrollPeriodRepository.updateById(financialYear.getFinancialYearId(), financialYear.getActiveStatus(),
					StatusMessage.OPEN_CODE);
		} else {
			payrollPeriodRepository.updateById(financialYear.getFinancialYearId(), financialYear.getActiveStatus(),
					StatusMessage.CLOSE_CODE);

		}

	}

	@Override
	public FinancialMonth getFinancialMonth(Long companyId) {
		// TODO Auto-generated method stub
		return financialMonthRepository.findFinancialMonth(companyId);
	}

}
