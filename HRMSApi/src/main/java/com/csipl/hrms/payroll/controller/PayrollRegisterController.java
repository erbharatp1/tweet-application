package com.csipl.hrms.payroll.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.PayRegisterExcelWriter;
import com.csipl.hrms.common.util.PayrollExelWriter;
import com.csipl.hrms.dto.payroll.PayRegisterHdDTO;
import com.csipl.hrms.dto.payrollprocess.HeaderReportPayOutDTO;
import com.csipl.hrms.dto.payrollprocess.ReportPayOutDTO;
import com.csipl.hrms.model.payrollprocess.PayRegisterHd;
import com.csipl.hrms.service.adaptor.PayrollRegisterAdaptor;
import com.csipl.hrms.service.payroll.PayrollRegisterService;
import com.csipl.hrms.service.payroll.ReportPayOutService;
import com.csipl.hrms.service.report.PayrollReportService;

@RequestMapping("/payrollRegister")
@RestController
public class PayrollRegisterController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(PayrollRegisterController.class);
	
	@Autowired
	private ReportPayOutService reportPayOutService;
	
	@Autowired
	private PayrollRegisterService payrollRegisterService;
	
	@Autowired
	PayrollReportService payrollReportService;
	
	PayrollRegisterAdaptor payrollRegisterAdaptor =new PayrollRegisterAdaptor();
	
	
	/**
	 * to get List of HeaderReportPayOut objects from database based on departmentId
	 * OR else companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/{companyId}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<PayRegisterHdDTO> getPayOutReportOfDepartment(
			@PathVariable("companyId") String companyId,
			@PathVariable("processMonth") String processMonth, HttpServletRequest req) throws PayRollProcessException {
		logger.info(" getPayOutReportOfDepartment for  :"  + "  processMonth  " + processMonth);
		 
		List<Object[]> objectListOfReport = null;
		Long longCompanyId = Long.parseLong(companyId);
	
	 List<Object[]> payRegisterList=	payrollRegisterService.getPayrollRegisterListBypayMonth(longCompanyId,processMonth);
		System.out.println(payRegisterList);
		 List<PayRegisterHdDTO> payRegisterDtoList = payrollRegisterAdaptor.reportPayOutObjectListToPayRegisterHdDTOConversion( payRegisterList);
		return payRegisterDtoList;
	}

	
	
	/**
	 * to get List of HeaderReportPayOut objects from database based on departmentId
	 * OR else companyId
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payrollRegister/{Id}/{processMonth}", method = RequestMethod.GET)
	public @ResponseBody List<PayRegisterHdDTO> getPayRegister(
			@PathVariable("Id") String payRegisterHdId,
			@PathVariable("processMonth") String processMonth,
			 HttpServletRequest req) throws PayRollProcessException {
		logger.info(" getPayOutReportOfDepartment for  :"  + "  payRegisterHdId  " + payRegisterHdId);
		 
		
		Long Id = Long.parseLong(payRegisterHdId);
	
	 List<Object[]> payRegisterList=	payrollRegisterService.getPayrollRegisterListById(Id,processMonth);
		//System.out.println(payRegisterList);
		 List<PayRegisterHdDTO> payRegisterDtoList = payrollRegisterAdaptor.reportPayOutObjectListToPayRegisterHdDTO( payRegisterList,processMonth);
		return payRegisterDtoList;
	}
	
	
	/**
	 * to get List of HeaderReportPayOut objects from database based on departmentId
	 * OR else companyId
	 * bank payment transfer sheet
	 * 
	 * @throws PayRollProcessException
	 * @throws ErrorHandling 
	 */
	// generate Payment transfer sheet 
	@RequestMapping(path = "/payrollRegisterReport/{Id}/{processMonth}", method = RequestMethod.GET)
	public void getPayRegisterForSalaryReport(
			@PathVariable("Id") String payRegisterHdId,
			@PathVariable("processMonth") String processMonth,
			 HttpServletRequest req, HttpServletResponse response) throws PayRollProcessException, ErrorHandling {
		logger.info(" getPayOutReportOfDepartment for  :"  + "  payRegisterHdId  " + payRegisterHdId);
		 
		List<Object[]> objectListOfReport = null;
		Long Id = Long.parseLong(payRegisterHdId);
	
		List<Object[]> payRegisterList=	payrollRegisterService.getPayrollRegisterListForPaymentTransfer(Id, processMonth);
		System.out.println(payRegisterList);
		List<ReportPayOutDTO> payRegisterDtoList = payrollRegisterAdaptor.paymentTransferSheetReport( payRegisterList,processMonth);

		if(payRegisterList.size() > 0) {
		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=BankPaymentTransferSheet.xlsx");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		String[] columns = { "BANK NAME", "HEAD COUNT", "TOTAL NET PAY"};
		String[] sheet2Columns= {"S No.", "EmpNo", "Name", "Department","Bank", "IFSC Code", "Bank Account No", "Amount","Status" };
			Workbook workbook;
			try {
				workbook = PayRegisterExcelWriter.paymentTransferSheet(payRegisterDtoList, columns,sheet2Columns,processMonth);
				ServletOutputStream fileOut = response.getOutputStream();
			    workbook.write(fileOut);
			} catch (InvalidFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			throw new ErrorHandling("Invalid session .Please login again");
		}
		
	}
	
	
	@RequestMapping(path = "/salarySheetHd/{companyId}/{hdId}/{processMonth}", method = RequestMethod.GET)
	public void generateSalarySheetById(@PathVariable("companyId") String companyId,@PathVariable("hdId") String payRegisterHdId,
			@PathVariable("processMonth") String processMonth,
			HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, PayRollProcessException, ParseException, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee Name", "Designation", "Department", "Date of Joining",
				"Gender", "Job Location","Days In Month","Absent","Days Payable","Monthly Gross",};
		String[] totalGross = {"Earned Gross"};
		
		String[] column = {"Total Deductions","Net Pay","Bank","Account Number","IFSC","Branch"};
		Long longcompanyId = Long.parseLong(companyId);
		Long hdId= Long.parseLong(payRegisterHdId);
		
		String earnngPayHeads[] =payrollReportService.getEarnngPayHeads(longcompanyId);
		String deductionPayHeads[] =payrollReportService.getDeductionPayHeads(longcompanyId);
		String earnngPayHeadsNew[]= new String[earnngPayHeads.length];
		String earnngPayHeadsId[]= new String[earnngPayHeads.length];
		String deductionPayHeadsNew[]= new String[deductionPayHeads.length];
		String deductionPayHeadsId[]= new String[deductionPayHeads.length];
		
		for(int i=0; i<earnngPayHeads.length; i++) {
			String columnName[]=earnngPayHeads[i].split("/");
			earnngPayHeadsNew[i]=columnName[0];
			earnngPayHeadsId[i]=columnName[1];
		}

		for(int i=0; i<deductionPayHeads.length; i++) {
			String columnNames[]=deductionPayHeads[i].split("/");
			deductionPayHeadsNew[i]=columnNames[0];
			deductionPayHeadsId[i]=columnNames[1];
		}
		
		List<String> list = new ArrayList<>(Arrays.asList(columns));
		list.addAll(Arrays.asList(earnngPayHeadsNew));
		list.addAll(Arrays.asList(totalGross));
		list.addAll(Arrays.asList(deductionPayHeadsNew));
		list.addAll(Arrays.asList(column));
		
		Object[] newColumns = list.toArray();
		List<Object[]> reportPayoutObj = payrollRegisterService.getSalarySheetByHd(longcompanyId, hdId, processMonth );
		 
		List<ReportPayOutDTO> reportPayOutDTOList = payrollRegisterAdaptor.objectListToSalarySheethdList(reportPayoutObj);
		try {
			response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=SalarySheet.xlsx");
			if (longcompanyId != null && reportPayOutDTOList.size() >0) {
					Workbook workbook = PayRegisterExcelWriter.salarySheetHd(reportPayOutDTOList, newColumns,
							processMonth,earnngPayHeadsId ,deductionPayHeadsId);
					ServletOutputStream fileOut = response.getOutputStream();
					workbook.write(fileOut);
			} else
				throw new ErrorHandling("Invalid session .Please login again");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
