package com.csipl.hrms.payroll.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.AppUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.PayrollExelWriter;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.OneTimeDeductionDTO;
import com.csipl.hrms.dto.payroll.OneTimeEarningDeductionDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.OneTimeEarningDeduction;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.model.payrollprocess.Attendance;
import com.csipl.hrms.model.payrollprocess.ReportPayOut;
import com.csipl.hrms.org.controller.ClientController;
import com.csipl.hrms.service.adaptor.EmployeePersonalInformationAdaptor;
import com.csipl.hrms.service.adaptor.OneTimeEarningDeductionAdaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.AttendanceDTO;
import com.csipl.hrms.service.payroll.OneTimeEarningDeductionService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.ReportPayOutService;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
@RequestMapping("/onetimeearningdeduction")
@RestController
public class OneTimeEarningDeductionController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
/*	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;*/
	@Autowired 
	OneTimeEarningDeductionService oneTimeEarningDeductionService;
	@Autowired
	EmployeePersonalInformationService employeePersonalInformationService;
	
	@Autowired
	ReportPayOutService reportPayOutService;
	@Autowired
	PayHeadService payHeadService;
	OneTimeEarningDeductionAdaptor oneTimeEarningDeductionAdaptor= new OneTimeEarningDeductionAdaptor();
	EmployeePersonalInformationAdaptor employeePersonalInformationAdaptor = new EmployeePersonalInformationAdaptor();
	/**
	 * @param oneTimeDeductionDTO
	 *            This is the first parameter for getting oneTimeDeduction Object
	 *            from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void saveOneTimeDeduction(@RequestBody List<OneTimeEarningDeductionDTO> oneTimeEarningDeductionDtoList, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		List<OneTimeEarningDeduction> oneTimeEarningDeductionList =oneTimeEarningDeductionAdaptor.uiDtoToDatabaseModelList(oneTimeEarningDeductionDtoList);
		HashMap<Long,OneTimeEarningDeductionDTO> mapEmpList= new HashMap<Long,OneTimeEarningDeductionDTO>();
       List<String> empList = new ArrayList<String>();
		//employee payroll validation
		for (OneTimeEarningDeductionDTO oneTimeEarningDeduction : oneTimeEarningDeductionDtoList) {
			Long empCount = reportPayOutService.checkPayrollOfEmployee(oneTimeEarningDeduction.getEmployeeId(),
					oneTimeEarningDeduction.getEarningDeductionMonth());
			if(empCount!=0) {
				empList.add(oneTimeEarningDeduction.getFullNameCodeValues())	;
			mapEmpList.put(oneTimeEarningDeduction.getEmployeeId(), oneTimeEarningDeduction);
			}
		}
		if(mapEmpList.size()>0) {
			StringBuilder builder = new StringBuilder();
			//builder.append(" System can't upload  file due to mismatch of employ code : \n ");
			builder.append(" Payroll already done of this employee for this process month " + empList);
			System.out.printf("Payroll already done of this employee for this process month   "+empList);

			throw new PayRollProcessException(builder.toString());	
		}
		oneTimeEarningDeductionService.saveAll(oneTimeEarningDeductionList);
	}
	

	/**
	 * Get complete deduction list
	 * 
	 * @param req
	 * @return
	 * @throws PayRollProcessException
	 * @throws ParseException
	 */
	@RequestMapping(path = "/oneTimeEarning/{companyId}/{payrollMonth}", method = RequestMethod.GET)
	public @ResponseBody List<OneTimeEarningDeductionDTO> findAllOneTimeEarningList(
			@PathVariable("companyId") String companyId,@PathVariable("payrollMonth") String payrollMonth, HttpServletRequest req) throws PayRollProcessException {
		Long companyID = Long.parseLong(companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService
				.findAllPersonalInformationDetails(companyID);
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoList(employeeObjList);
		
		List<OneTimeEarningDeduction> earningDeductionList = oneTimeEarningDeductionService.getOneTimeEarningList(companyID,payrollMonth);	
	System.out.println("earningDeductionList.."+earningDeductionList);
		return oneTimeEarningDeductionAdaptor.databaseModelToUiDtoList(earningDeductionList, employeeDtoList);
		
	}
	
	@RequestMapping(path = "/oneTimeDeduction/{companyId}/{payrollMonth}", method = RequestMethod.GET)
	public @ResponseBody List<OneTimeEarningDeductionDTO> findAllOneTimeDeductionList(
			@PathVariable("companyId") String companyId,@PathVariable("payrollMonth") String payrollMonth, HttpServletRequest req) throws PayRollProcessException {
		Long companyID = Long.parseLong(companyId);
		List<Object[]> employeeObjList = employeePersonalInformationService
				.findAllPersonalInformationDetails(Long.valueOf(companyId));
		List<EmployeeDTO> employeeDtoList = employeePersonalInformationAdaptor
				.databaseObjModelToUiDtoList(employeeObjList);
		
		List<OneTimeEarningDeduction> earningDeductionList = oneTimeEarningDeductionService.getOneTimeDeductionList(companyID,payrollMonth);	
		return oneTimeEarningDeductionAdaptor.databaseModelToUiDtoList(earningDeductionList, employeeDtoList);
		
	}
	/**
	 * @param Id
	 *            This is the first parameter for getting oneTimeDeduction Object
	 *            from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(path = "/deleteById/{Id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOneTimeDeduction(@PathVariable(value = "Id") String Id, HttpServletRequest req)
			throws ErrorHandling {
		Long ID = Long.parseLong(Id);
		oneTimeEarningDeductionService.delete(ID);
	}
	
	/**
	 * @param oneTimeDeductionDTO
	 *            This is the first parameter for getting oneTimeDeduction Object
	 *            from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 * @throws Exception 
	 */
	/**
	 * @param file
	 * @param oneTimeEarningDeductionDto
	 * @throws ErrorHandling
	 * @throws Exception
	 */
	@RequestMapping(value = "/earningfileUpload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public List<OneTimeEarningDeductionDTO> saveOneTimeDeductionFile(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") OneTimeEarningDeductionDTO oneTimeEarningDeductionDto)
			throws ErrorHandling, Exception {
		logger.info("saveOneTimeDeductionFile is calling : " + " : file " + file);
		logger.info("saveOneTimeDeductionFile is calling : " + " : file "
				+ oneTimeEarningDeductionDto.getEarningDeductionMonth() + "******  "
				+ oneTimeEarningDeductionDto.getUserId() + "   " + oneTimeEarningDeductionDto.getCompanyId());
		AppUtils util = new AppUtils();
		List<String> listEmpCode = new ArrayList<>();
		List<String> listEmpInpayrollCode = new ArrayList<>();
		List<Long> listPayheadIdList = new ArrayList<>();
		List<Long> listPayheadIdDB = new ArrayList<>();
		List<String> listDB = new ArrayList<>();
		List<String> listEmpCodeDB = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		// List<OneTimeEarningDeduction> oneEarningDeductionList = new
		// ArrayList<OneTimeEarningDeduction>();
		List<OneTimeEarningDeductionDTO> oneEarningDeductionList = new ArrayList<OneTimeEarningDeductionDTO>();
		List<OneTimeEarningDeductionDTO> oneEarningDeductionNewList = new ArrayList<OneTimeEarningDeductionDTO>();
		HashMap<String, Employee> mapEmpList = new HashMap<String, Employee>();

		final String SAMPLE_XLSX_FILE_PATH = file.getContentType();
		List<Employee> employeeList = employeePersonalInformationService.getEmployeeInPayroll(
				oneTimeEarningDeductionDto.getCompanyId(), oneTimeEarningDeductionDto.getEarningDeductionMonth());
		List<Employee> allEmployeeList = employeePersonalInformationService
				.fetchEmployee(oneTimeEarningDeductionDto.getCompanyId());  
		List<PayHead> payheadList = payHeadService.getAllOneTimePayHeads(oneTimeEarningDeductionDto.getType(),
				oneTimeEarningDeductionDto.getCompanyId());
  
		listDB = AppUtils.getEmployeeCode(allEmployeeList);
		listEmpInpayrollCode = AppUtils.getEmployeeCode(employeeList);
		for (Employee employee : allEmployeeList) {
			mapEmpList.put(employee.getEmployeeCode(), employee);
		}
		for (PayHead payHead : payheadList) {
			listPayheadIdDB.add(payHead.getPayHeadId());
		}
		// Map<String, OneTimeEarningDeduction> mapEarnDeduction = new HashMap<>();
		Map<String, OneTimeEarningDeductionDTO> mapEarnDeduction = new HashMap<>();

		// ecxel data reading
		Workbook workbook = WorkbookFactory.create(AppUtils.createXLSFile(file));
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		int i = 0;
		for (Row row : sheet) {
			int j = 0;
			// OneTimeEarningDeduction onetimeEarningDeduction= new
			// OneTimeEarningDeduction();
			OneTimeEarningDeductionDTO onetimeEarningDeduction = new OneTimeEarningDeductionDTO();
			String cellEmpCode = null;
			for (Cell cell : row) {
				if (i > 0) {

					if (j == 0) {
						cellEmpCode = dataFormatter.formatCellValue(cell);
						listEmpCode.add(cellEmpCode);
						onetimeEarningDeduction.setEmployeeCode(cellEmpCode);
					}
					if (j == 1) {
						String cellValue = dataFormatter.formatCellValue(cell);
						onetimeEarningDeduction.setEmployeeName(cellValue);
					}
					if (j == 2) { 
						String cellValue = dataFormatter.formatCellValue(cell);
						String arr[] = cellValue.split("-");
						onetimeEarningDeduction.setType(cellValue);
						Long payHeadId = Long.parseLong(arr[1]);
						listPayheadIdList.add(payHeadId);
						onetimeEarningDeduction.setPayHeadId(payHeadId);
						// String cellValue = dataFormatter.formatCellValue(cell);
						// onetimeEarningDeduction.setType(cellValue);
					}
					if (j == 3) {
						String cellValue = dataFormatter.formatCellValue(cell);
						onetimeEarningDeduction.setAmount(new BigDecimal(cellValue));
					}

					if (j == 4) {
						String cellValue = dataFormatter.formatCellValue(cell);
						onetimeEarningDeduction.setRemarks(cellValue);
					}
				}
				j++;
			}
			if (i > 0)
				oneEarningDeductionList.add(onetimeEarningDeduction);

			// mapEarnDeduction.put(cellEmpCode, onetimeEarningDeduction);
			System.out.println();
			i++;
		}

  
		// employee code validation code
		System.out.println(listEmpCode);
		Collection<String> similarEmployeeCode = new HashSet<String>(listEmpCode);
		Collection<String> differentEmployeeCode = new HashSet<String>();
		differentEmployeeCode.addAll(listDB);
		differentEmployeeCode.addAll(listEmpCode);
		similarEmployeeCode.retainAll(listDB);
		differentEmployeeCode.removeAll(listDB);

		System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listEmpCode, listDB, similarEmployeeCode,
				differentEmployeeCode);
		if (differentEmployeeCode.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" System can't upload  file due to mismatch of employ code : \n ");
			builder.append(" Mismatch employee Codes are " + differentEmployeeCode);

			throw new PayRollProcessException(builder.toString());
		}
		// payhead Id validation Code
		Collection<Long> similarPayhead = new HashSet<Long>(listPayheadIdList);
		Collection<Long> differentPayhead = new HashSet<Long>();
		differentPayhead.addAll(listPayheadIdDB);
		differentPayhead.addAll(listPayheadIdList);
		similarPayhead.retainAll(listPayheadIdDB);
		differentPayhead.removeAll(listPayheadIdDB);
		System.out.printf("listPayheadIdList:%s%nlistPayheadIdDB:%s%nsimilarPayhead:%s%ndifferentPayhead:%s%n",
				listPayheadIdList, listPayheadIdDB, similarPayhead, differentPayhead);
		if (differentPayhead.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" System can't upload  file due to mismatch of Payment Type : \n ");
			builder.append(" Mismatch Payment Type Id's are " + differentPayhead);
			System.out.printf("System can't upload  file due to mismatch of payhead  " + differentPayhead);

			throw new PayRollProcessException(builder.toString());
		}
		// employee payroll validation

		Collection<String> similarCode = new HashSet<String>(listEmpCode);
		Collection<String> differentCode = new HashSet<String>();
		differentCode.addAll(listEmpInpayrollCode);
		differentCode.addAll(listEmpCode);
		similarCode.retainAll(listEmpInpayrollCode);
		differentCode.removeAll(listEmpInpayrollCode);
		if (similarCode.size() > 0) {
			StringBuilder builder = new StringBuilder();
			// builder.append(" System can't upload file due to mismatch of employ code : \n
			// ");
			builder.append(" Payroll already done of this employee for this process month " + similarCode);
			System.out.printf("Payroll already done of this employee for this process month   " + similarCode);

			throw new PayRollProcessException(builder.toString());
		} else {

			for (OneTimeEarningDeductionDTO oneEranDeduction : oneEarningDeductionList) {
				Employee employee = mapEmpList.get(oneEranDeduction.getEmployeeCode());
				oneEranDeduction.setEmployeeId(employee.getEmployeeId());
				oneEranDeduction.setUserId(oneTimeEarningDeductionDto.getUserId());
				oneEranDeduction.setUpdateId(oneTimeEarningDeductionDto.getUserId());
				oneEranDeduction.setDateCreated(new Date());
				oneEranDeduction.setUpdateDate(new Date());
				oneEranDeduction.setEarningDeductionMonth(oneTimeEarningDeductionDto.getEarningDeductionMonth());
				oneEranDeduction.setType(oneTimeEarningDeductionDto.getType());
				oneEranDeduction.setCompanyId(oneTimeEarningDeductionDto.getCompanyId());
				oneEranDeduction.setFullNameCodeValues(employee.getFirstName() + " " + employee.getLastName() + " ("
						+ employee.getEmployeeCode() + ")");
				oneEarningDeductionNewList.add(oneEranDeduction);
			}
			return oneEarningDeductionNewList;
		}
	}
	
	
	
	/**
	 * Get complete deduction list
	 * 
	 * @param req
	 * @return
	 * @throws PayRollProcessException
	 * @throws ErrorHandling 
	 * @throws ParseException
	 */
	@RequestMapping(path = "/employeePayrollValidation/{employeeId}/{payrollMonth}", method = RequestMethod.GET)
	public @ResponseBody Boolean employeePayrollValidation(
			@PathVariable("employeeId") String employeeId,@PathVariable("payrollMonth") String payrollMonth, HttpServletRequest req) throws  ErrorHandling {
		Long employeeID = Long.parseLong(employeeId);
		logger.info("employeePayrollValidation is calling : " + " : payrollMonth " + payrollMonth);
		Long empCount = reportPayOutService.checkPayrollOfEmployee(employeeID,
				payrollMonth);
		System.out.println("count..."+empCount);
		if (empCount != null && empCount > 0) {
			System.out.println("Payroll already done of this employee for this process month");
			throw new ErrorHandling("Payroll already done of this employee for this process month");
		}
			return true;
		
	}
	@RequestMapping(path = "/oneTimeEarningDeductionSampleFile/{companyId}/{payrollMonth}/{status}", method = RequestMethod.GET)
	public void oneTimeEarningDeductionSampleFile(@PathVariable("companyId") Long companyId,
			@PathVariable("payrollMonth") String payrollMonth, @PathVariable("status") String status, HttpServletRequest req, HttpServletResponse response)
			throws ErrorHandling, InvalidFormatException {

		String[] columns = { "Employee Code", "Employee Name", "Payment Type", "Amount", "Comment", "Process Month" };
		List<String> payHeadList = reportPayOutService.getPayHeadList(companyId, status);
		
//		List<Employee> allEmployeeList = employeePersonalInformationService.fetchEmployee(companyId);
//		List<String> employeeCodeList = AppUtils.getEmployeeCode(allEmployeeList);
	 
		try {
			
			if(status.equals("EA")) {
				response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=One Time Earning.xlsx");
				response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
				if (companyId != null) {

					if (companyId != null) {
						Workbook workbook = PayrollExelWriter.SampleFileWritter(columns, payrollMonth, payHeadList, status);
						ServletOutputStream fileOut = response.getOutputStream();
						workbook.write(fileOut);
					}
					/*
					 * else { throw new ErrorHandling("Comapny and Esi data not available"); }
					 */
				} else
					throw new ErrorHandling("Invalid session .Please login again");
			}else if(status.equals("DE")) {

				response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment;filename=One Time Deduction.xlsx");
				if (companyId != null) { 

					if (companyId != null) {
						Workbook workbook = PayrollExelWriter.SampleFileWritter(columns, payrollMonth, payHeadList, status);
						ServletOutputStream fileOut = response.getOutputStream();
						workbook.write(fileOut);
					}
					/*
					 * else { throw new ErrorHandling("Comapny and Esi data not available"); }
					 */
				} else
					throw new ErrorHandling("Invalid session .Please login again");
			
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
