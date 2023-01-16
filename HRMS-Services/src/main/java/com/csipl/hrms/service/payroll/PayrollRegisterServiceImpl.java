package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.payrollprocess.PayRegisterHd;
import com.csipl.hrms.service.payroll.repository.PayrollRegisterRepository;
import com.hrms.org.payrollprocess.dto.DepartmentProcessDTO;

@Service("payrollRegisterService")
public class PayrollRegisterServiceImpl implements PayrollRegisterService{
	@Autowired
	PayrollRegisterRepository payrollRegisterRepository;



	@Override
	public List<PayRegisterHd> getPayrollRegisterList(Long companyID, String payrollMonth) {
		// TODO Auto-generated method stub
		return payrollRegisterRepository.findPayrollRegisterList(companyID,payrollMonth);
	}
	


	@Override
	@Transactional
	public void save(PayRegisterHd payRegisterHd) {
		// TODO Auto-generated method stub
		payrollRegisterRepository.save(payRegisterHd);
	}
	
	

	@Override
	public List<Object[]> getPayrollRegisterListBypayMonth(Long companyID, String payrollMonth) {
		// TODO Auto-generated method stub
		return payrollRegisterRepository.fetchPayrollRegisterList(companyID,payrollMonth);
	}

	@Override
	public List<Object[]> getPayrollRegisterListById(Long payRegisterHdId, String payrollMonth) {
		// TODO Auto-generated method stub
		return payrollRegisterRepository.fetchPayrollRegisterListById(payRegisterHdId, payrollMonth);
	}



	@Override
	public List<Object[]> getPayrollRegisterListForPaymentTransfer(Long Id, String processMonth) {
		// TODO Auto-generated method stub
		return payrollRegisterRepository.fetchPayrollRegisterListPaymentTransfer(Id,processMonth);
	}



	@Override
	@Transactional
	public int updatePayrollLockFlagInPayRegister(List<Long> employeeId, String processMonth) {
		boolean payrollLockFlag=true;
		int count=payrollRegisterRepository.updatePayrollLockFlagInPayRegister(employeeId, processMonth, payrollLockFlag);
		return count;
	}
	
	
	@Override
	public List<Object[]> getSalarySheetByHd(Long longcompanyId, Long hdId, String processMonth) {
 		return payrollRegisterRepository.getSalarySheetByHd(longcompanyId,hdId,processMonth);

	}
	
	
}
