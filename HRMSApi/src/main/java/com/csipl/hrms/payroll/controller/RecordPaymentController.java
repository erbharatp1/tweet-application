package com.csipl.hrms.payroll.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.service.adaptor.ReportPayOutAdaptor;
import com.csipl.hrms.service.payroll.PayrollRegisterService;
import com.csipl.hrms.service.payroll.ReportPayOutService;


@RequestMapping("/recordPayment")
@RestController
public class RecordPaymentController {
	@Autowired
	ReportPayOutService reportPayOutService;
	ReportPayOutAdaptor reportPayOutAdaptor = new ReportPayOutAdaptor();
	
	@Autowired
	PayrollRegisterService payrollRegisterService;
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(RecordPaymentController.class);
	
	
	
	
	/**
	 * to get List of EmployeeCode objects from database    
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(path = "/{companyId}/{payrollMonth}", method = RequestMethod.GET)
	public @ResponseBody List<ReportPayOutDTO> getAllEmployeeDetails(@PathVariable("companyId") String companyId,@PathVariable("payrollMonth") String payrollMonth,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("payrollMonth--"+payrollMonth +"companyId--"+companyId);
 	
 		Long longCompanyId = Long.parseLong(companyId);
 		
 		List<Object[]> recordPaymentList =reportPayOutService.findReportPayoutForRecordPayment(payrollMonth, longCompanyId);
		return reportPayOutAdaptor.reportPayoutObjToRepoortPayDTO(recordPaymentList);
	}
	
	
	
	/**
	 * to get List of EmployeeCode objects from database    
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(path = "/updateRecordPayment/{companyId}/{payrollMonth}", method = RequestMethod.POST)
	public int updateRecordPayment(@PathVariable("companyId") String companyId,@PathVariable("payrollMonth") String payrollMonth,@RequestBody List<ReportPayOutDTO> reportPayOutDTOList,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("payrollMonth--"+payrollMonth +"companyId--"+companyId);
		List<ReportPayOut> reportPayoutList = new ArrayList<ReportPayOut>();
 		Long longCompanyId = Long.parseLong(companyId);
 		List<Long> employeeIdList = new ArrayList<Long>();
 		
 		for (ReportPayOutDTO reportPayOutDTO : reportPayOutDTOList) {
 			if(reportPayOutDTO.getTransactionNo()!=null && reportPayOutDTO.getTransactionMode()!=null && reportPayOutDTO.getTransactionMode()!="" && reportPayOutDTO.getTransactionDate()!=null) {
 	 			int count =reportPayOutService.updateReportPayout(reportPayOutDTO.getEmployeeId(), reportPayOutDTO.getTransactionNo(),reportPayOutDTO.getTransactionMode(),payrollMonth, reportPayOutDTO.getTransactionDate(), reportPayOutDTO.getCompanyId());
 	 			employeeIdList.add(reportPayOutDTO.getEmployeeId());
 	 			System.out.println("empploee report payout not updated" + reportPayOutDTO.getName() +"..."+count);
 			}
 		}
 		int updatedRow=0;
 		 if(employeeIdList.size()>0) {
 			updatedRow=payrollRegisterService.updatePayrollLockFlagInPayRegister(employeeIdList, payrollMonth);
 		 }
 		//List<Object[]> recordPaymentList =reportPayOutService.findReportPayoutForRecordPayment(payrollMonth, longCompanyId);
		return updatedRow;
	}
	
	@RequestMapping(path = "recordPaymentCount/{companyId}/{payrollMonth}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> recordPaymentCount(@PathVariable("companyId") String companyId,@PathVariable("payrollMonth") String payrollMonth,
			HttpServletRequest req) throws PayRollProcessException {
		 
		return reportPayOutService.recordPaymentCount(companyId,payrollMonth);
	}
	
	
}
