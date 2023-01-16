package com.csipl.hrms.payroll.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.PayRegisterHdDTO;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.adaptor.PayrollRegisterAdaptor;
import com.csipl.hrms.service.payroll.PayrollControlService;
import com.csipl.hrms.service.payroll.PayrollRollbackService;
@RequestMapping("/rollBackPayroll")
@RestController
public class PayrollProcessRollbackController {
	private static final Logger logger = LoggerFactory.getLogger(PayrollProcessRollbackController.class);
	@Autowired
	PayrollRollbackService payrollRollbackService;
	@Autowired
	PayrollControlService payrollControlService;
	
	PayrollRegisterAdaptor payrollRegisterAdaptor = new PayrollRegisterAdaptor();
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */

	
	@RequestMapping(value="/getProcessMonthList/{companyId}",method = RequestMethod.GET)
	public @ResponseBody  List<PayRegisterHdDTO> payrollRollbackProcessMonthList(@PathVariable("companyId") Long companyId) {
		logger.info("payrollProcessMonthList is calling : " + " : companyId " + companyId);
		//Long companyID = Long.parseLong(companyId);
		
		List<Object[]> payrollRegisterProcessMonthList =payrollRollbackService.getPayRollRegisterProcessMonth(companyId);
		logger.info("payrollProcessMonthList is end  :" + "PayrollRegisterProcessMonthList" + payrollRegisterProcessMonthList);
		return payrollRegisterAdaptor.payRollRegisterProcessMonthList(payrollRegisterProcessMonthList);
	}
	
	@RequestMapping(value="/getPayRegisterList/{companyId}/{processMonth}",method = RequestMethod.GET)
	public List<PayRegisterHdDTO> payrollRegisterList(@PathVariable("companyId") Long companyId,@PathVariable("processMonth") String processMonth) {
		logger.info("payrollRegisterList is calling : " + " : companyId " + companyId + "processMonth " + processMonth);
		//Long companyID = Long.parseLong(companyId);
		 List<Object[]> payRegisterList=	payrollRollbackService.getPayrollRegisterListbyHdId(companyId,processMonth);
		 
		logger.info("payrollRegisterList is end  :" + "payrollRegisterList" + payRegisterList);
		return payrollRegisterAdaptor.payRollRegisterList(payRegisterList);
	}

	@RequestMapping(value="/getEmployeeRegisterList/{hdId}",method = RequestMethod.GET)
	public List<PayRegisterHdDTO> employeePayrollRegisterList(@PathVariable("hdId") Long hdId) {
		logger.info("payrollEmployeeRegisterList is calling : " + " : companyId " + hdId);
		 List<Object[]> employeePayRegisterList=	payrollRollbackService.fetchEmployeePayrollRegisterListForRollback(hdId);
		 
		logger.info("payrollEmployeeRegisterList is end  :" + "payrollEmployeeRegisterList" + employeePayRegisterList);
		return payrollRegisterAdaptor.employeePayrollRegisterRollback(employeePayRegisterList);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void rollbackPayrollProcess(@RequestBody PayRegisterHdDTO payRegisterDTO, HttpServletRequest req)
			throws ErrorHandling, Exception {
		logger.info("rollbackPayrollProcess is calling : " + " : payRegisterDTO " + payRegisterDTO);

		if(payRegisterDTO.getEmployeeIds().size() !=0) {
			String processMonth = payRegisterDTO.getProcessMonth();
			Long payReghdId=payRegisterDTO.getPayRegisterHdId();
			
			//here we will check process month is locked or not if locked throw exception
			List<PayrollControl> unlockedPsMonthList = payrollControlService.findPCBypIsLockn(processMonth);
			if(unlockedPsMonthList == null || unlockedPsMonthList.size()==0) {
				throw new ErrorHandling(processMonth +"  is locked, you can not rollback payroll");
			}
			
			payrollRollbackService.processRollbackPayroll(payRegisterDTO.getEmployeeIds() ,processMonth,payReghdId);
		}else {
			throw new ErrorHandling(" Please select employee for rollback payroll ");
		}
		 
	}
}
