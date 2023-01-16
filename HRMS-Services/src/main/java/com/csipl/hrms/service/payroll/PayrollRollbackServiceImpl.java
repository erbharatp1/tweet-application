package com.csipl.hrms.service.payroll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.service.payroll.repository.LoanEMIRepository;
import com.csipl.hrms.service.payroll.repository.LoanIssueRepository;
import com.csipl.hrms.service.payroll.repository.PayOutRepository;
import com.csipl.hrms.service.payroll.repository.PayrollRollbackRepository;
import com.csipl.hrms.service.payroll.repository.ReportPayOutRepository;

@Transactional
@Service("payrollRollbackService")
public class PayrollRollbackServiceImpl implements PayrollRollbackService {

	@Autowired
	PayrollRollbackRepository payrollRollbackRepository;

	@Autowired
	LoanIssueRepository loanIssueRepository;

	@Autowired
	ReportPayOutRepository reportPayOutRepository;
	@Autowired
	PayOutRepository payOutRepository;

	@Autowired
	LoanEMIRepository loanEMIRepository;

//nidhi for payroll rollback
	@Override
	public List<Object[]> getPayRollRegisterProcessMonth(Long companyId) {
		System.out.println("PayrollRegisterServiceImpl.getPayRollRegisterProcessMonth()" + companyId);
		return payrollRollbackRepository.fetchPayrollRegistProcessMonthForRollBack(companyId);
	}

	@Override
	public List<Object[]> getPayrollRegisterListbyHdId(Long Id, String processMonth) {
// TODO Auto-generated method stub
		return payrollRollbackRepository.fetchPayrollRegisterListByHdId(Id, processMonth);
	}

	@Override
	public List<Object[]> fetchEmployeePayrollRegisterListForRollback(Long hdId) {
		return payrollRollbackRepository.fetchEmployeePayrollRegisterListForRollback(hdId);
	}

//For Rollback of Payroll 3 table  will affected 1.ReportPayout -delete entry,2. payout -delete entry, 3.Payregister- simple deactive the entry
	@Override
	public void processRollbackPayroll(List<Long> employeeIds, String processMonth,Long payReghdId)
			throws PayRollProcessException, ErrorHandling, Exception {
		String code = StatusMessage.DEACTIVE_CODE;
		List<String> loanList = new ArrayList<String>();

		if (employeeIds.size() != 0) {
			for (Long l : employeeIds) {

				String loanId = payOutRepository.getLoanID(l, processMonth);
				if (loanId != null) {
					String[] values = loanId.split(",");
					loanList = Arrays.asList(values);

					for (String s : loanList) {
						if (!s.equals("")) {
							Long transId = Long.parseLong(s);
							loanEMIRepository.deleteFromLoanEMI(transId, processMonth);
							//loanIssueRepository.upadteLoanIssueAfterRollback(transId, l);
						}
					}
				}
				payrollRollbackRepository.deleteEmpFromPayout(l,processMonth);
				payrollRollbackRepository.deleteEmpFromReportPayOut(l,processMonth);
			
				payrollRollbackRepository.deactiveEmpFromPayRegister(code, l,payReghdId);

			}
		}

	}
}