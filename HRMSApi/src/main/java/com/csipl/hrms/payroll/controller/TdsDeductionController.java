package com.csipl.hrms.payroll.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeCountDTO;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.employee.PageIndex;
import com.csipl.hrms.dto.organisation.DesignationDTO;
import com.csipl.hrms.dto.payroll.TdsDeductionDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.dto.search.EmployeeSearchDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.adaptor.TdsDeductionAdaptor;
import com.csipl.hrms.service.employee.EmployeePagingAndFilterService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.payroll.FinancialYearService;
import com.csipl.hrms.service.payroll.InvestmentService;
import com.csipl.hrms.service.payroll.TdsDeductionService;
import com.csipl.hrms.service.payroll.TdsSummaryBeforeDeclarationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tdsDeduction")
public class TdsDeductionController {

	private static final Logger logger = LoggerFactory.getLogger(TdsDeductionController.class);
	@Autowired
	TdsDeductionService tdsDeductionService;

	@Autowired
	private EmployeePagingAndFilterService employeePagingAndFilterService;
	
	@Autowired
	FinancialYearService financialYearService;
	
	TdsDeductionAdaptor tdsDeductionAdaptor = new TdsDeductionAdaptor();
	
	@Autowired
	InvestmentService investmentService;
	
	@Autowired
	private TdsSummaryBeforeDeclarationService tdsSummaryBeforeDeclarationService;
	
	@Autowired
	private EmployeePersonalInformationService employeePersonalInformationService;
	
	int count;

	/**
	 * 
	 * saveTdsDeduction}
	 */
	@ApiOperation(value = "Save or Update tdsDeduction")
	@RequestMapping(method = RequestMethod.POST)
	public TdsDeductionDTO saveTdsDeduction(@RequestBody TdsDeductionDTO tdsDeductionDTO) {
		logger.info("savetdsDeduction is calling : " + " : TdsDeductionDTO " + tdsDeductionDTO);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, tdsDeductionDTO.getCompanyId());
		tdsDeductionDTO.setFinancialYearId(financialYear.getFinancialYearId());
		TdsDeduction tdsDeduction = tdsDeductionAdaptor.uiDtoToDatabaseModel(tdsDeductionDTO);
		logger.info("savetdsDeduction is end  :" + "tdsDeduction" + tdsDeduction);
		tdsDeductionService.save(tdsDeduction);
		
		return tdsDeductionAdaptor.databaseModelToUiDto(tdsDeduction);
	}

	/**
	 * 
	 * findAlltdsDeduction}
	 */
	@GetMapping(value = "findAlltdsDeduction/{companyId}")
	public List<TdsDeductionDTO> findAlltdsDeduction(@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
		logger.info("findAlltdsDeduction is calling : " + " : companyId " + companyId);
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
	 
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		List<Object[]> tdsDeductionList = tdsDeductionService.findAlltdsDeduction(companyId, financialYear.getFinancialYearId());
		
		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		
		if (tdsDeductionList != null && tdsDeductionList.size() > 0) {
			List<TdsDeductionDTO>  list =  tdsDeductionAdaptor.tdsDeductionListdatabaseModelToUiDtoList(tdsDeductionList, tdsGroupList, financialYear);
			
			for(TdsDeductionDTO tdsDeductionDTO:list) {
				TdsSummaryChangeDTO tdsSummaryChangeDTO =new  TdsSummaryChangeDTO();
				if(tdsDeductionDTO.getTotalTax()==null) {
						if (tdsDeductionDTO.getEmployeeId()!=null) {
							 Employee employee =employeePersonalInformationService.findEmployeesById(tdsDeductionDTO.getEmployeeId());
							 tdsSummaryChangeDTO = tdsSummaryBeforeDeclarationService.getTdsSummaryBeforeDeclation(tdsGroupList, employee, financialYear, tdsDeductionDTO.getCompanyId(), tdsDeductionDTO.getUserId(), false);
							
							 tdsDeductionDTO.setTotalTax(tdsSummaryChangeDTO.getTotalTax());
							//need to be change logic
//							 tdsDeductionDTO.setTaxTobeDeductedMonthly((tdsSummaryChangeDTO.getTotalTax().divide(new BigDecimal(12), 2, RoundingMode.CEILING)));
							 tdsDeductionDTO.setTaxTobeDeductedMonthly(tdsSummaryChangeDTO.getNetTaxMonthly());
					}
				}
			}
			return list;
		}
		else
			throw new ErrorHandling("TdsDEduction data not present");
	}

	
	@GetMapping(value = "getTdsDeduction/{companyId}/{employeeId}")
	public TdsDeductionDTO getTdsDeduction(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException {
		logger.info("findAlltdsDeduction is calling : " + " : companyId " + companyId);
		
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		TdsDeduction tdsDeduction = tdsDeductionService.getTdsDeduction(companyId, financialYear.getFinancialYearId(),employeeId);
		
		if (tdsDeduction!=null )
			return tdsDeductionAdaptor.databaseModelToUiDto(tdsDeduction);
		else
			throw new ErrorHandling("TdsDEduction data not present");

	}
	
	
//	@RequestMapping(path = "/employeeList", method = RequestMethod.GET)
	@RequestMapping(path = "/employeeList",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeDTO> allEmployeesList(@RequestBody EmployeeSearchDTO employeeSearcDto,
			HttpServletRequest req) throws ParseException {
		//logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		List<Object[]> employeeObjList = tdsDeductionService.findAllEmployee( employeeSearcDto);
		
		List<EmployeeDTO> employeeDtoList=tdsDeductionAdaptor.databaseObjModelToUiDtoList(employeeObjList);
		return employeeDtoList;
	}
	
	
	@RequestMapping(path = "/employeesListWithTdsStatus",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EmployeeDTO> findAllEmployeesWithTdsStatus(@RequestBody EmployeeSearchDTO employeeSearcDto,
			HttpServletRequest req) throws ParseException {
		//logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, employeeSearcDto.getCompanyId());
	 
		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(employeeSearcDto.getCompanyId(), financialYear);
		
		List<Object[]> employeeObjList = tdsDeductionService.findAllEmployeesWithTdsStatus(employeeSearcDto,financialYear.getFinancialYearId());
		
		List<EmployeeDTO> employeeDtoList=tdsDeductionAdaptor.databaseObjModelToUiDtoEmployeeList(employeeObjList);
		 
		for(EmployeeDTO employeeDTO:employeeDtoList) {
			TdsSummaryChangeDTO tdsSummaryChangeDTO =new  TdsSummaryChangeDTO();
			if(employeeDTO.getTotalIncome()==null) {
					if (employeeDTO.getEmployeeId()!=null) {
						 Employee employee =employeePersonalInformationService.findEmployeesById(employeeDTO.getEmployeeId());
						 tdsSummaryChangeDTO = tdsSummaryBeforeDeclarationService.getTdsSummaryBeforeDeclation(tdsGroupList, employee, financialYear, employee.getCompany().getCompanyId(), employee.getUserId(), false);
						
						 employeeDTO.setTotalIncome(tdsSummaryChangeDTO.getYearlyGrossFy());
						  
						 employeeDTO.setTaxableIncome((tdsSummaryChangeDTO.getTaxableIncome()));
						 employeeDTO.setTotalRebate(tdsSummaryChangeDTO.getExempStandard().add(tdsSummaryChangeDTO.getTotalIncomeProfessionalTax()));
					 
				}
			}
		}
		
		return employeeDtoList;
	}
	
	
	@GetMapping(value = "/employeecountWithTdsStatus/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeCountWithTdsStatus(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		employeePagingAndFilterService.getEmployeeCountWithTdsStatus(longCompanyId, employeeCountDto);
		count = employeeCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);
		return employeeCountDto;
	}

	
	@GetMapping(value = "/employeecount/{companyId}/{pageSize}")
	public @ResponseBody EmployeeCountDTO getAllEmployeeCount(@PathVariable("companyId") String companyId,
			@PathVariable("pageSize") String pageSize, HttpServletRequest req) throws PayRollProcessException {

		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		Long longCompanyId = Long.parseLong(companyId);
		int parPageRecord = Integer.parseInt(pageSize);
		EmployeeCountDTO employeeCountDto = new EmployeeCountDTO();
		employeePagingAndFilterService.getEmployeeCount(longCompanyId, employeeCountDto);
		count = employeeCountDto.getCount();
		System.out.println("Count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;
		System.out.println("Pages : -" + pages);
		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		employeeCountDto.setPageIndexs(pageIndexList);
		employeeCountDto.setCount(count);
		return employeeCountDto;
	}

	
	
	@RequestMapping(path = "/updateTdsLockUnlockStatus",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public int updateTdsLockUnlockStatus(@RequestParam("tdsLockUnlockStatus") String tdsLockUnlockStatus, @RequestBody List<EmployeeDTO> EmployeeDTOList,
			HttpServletRequest req) throws ParseException {
		//logger.info("allEmployeesList is calling :" + " : companyId " + companyId);
		int i = tdsDeductionService.updateTdsLockUnlockStatus(EmployeeDTOList, tdsLockUnlockStatus);
		
		//List<EmployeeDTO> employeeDtoList=tdsDeductionAdaptor.databaseObjModelToUiDtoList(employeeObjList);
		return i;
	}
	
	@RequestMapping(path = "/resetTdsScheme",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void resetTdsScheme(@RequestBody List<EmployeeDTO> EmployeeDTOList,
			HttpServletRequest req) throws ParseException {
		  tdsDeductionService.resetTdsScheme(EmployeeDTOList);
	}
	
	@RequestMapping(path = "/tdsLockUnlockStatus/{companyId}/{employeeId}", method = RequestMethod.GET)
	public Map<Integer, String> getTdsLockUnlockStatus(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") String employeeId, HttpServletRequest req) {
		Long empId = Long.parseLong(employeeId);
		String status = tdsDeductionService.getTdsLockUnlockStatus(companyId, empId);
		Map<Integer, String> map = new HashMap<>();
		map.put(1, status);
		return map;
	}

	
	@GetMapping(value = "employeeTdsDeduction/{companyId}/{employeeId}")
	public List<TdsDeductionDTO> getEmployeeTdsDeduction(@PathVariable("companyId") Long companyId, @PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException, ParseException {
		logger.info("findAlltdsDeduction is calling : " + " : companyId " + companyId);
		  
		DateUtils dateUtils = new DateUtils();
		Date currentDate = dateUtils.getCurrentDate();
		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
		List<Object[]> employeeTdsDeductionList = tdsDeductionService.getTdsSummary(companyId, financialYear.getFinancialYearId(),employeeId);
		
		List<TdsDeductionDTO> tdsDeductionList =	tdsDeductionAdaptor.databaseModelToUiSummaryDto(employeeTdsDeductionList, financialYear.getFinancialYear());
		
		Employee employee =employeePersonalInformationService.findEmployeesById(employeeId);
		Date joiningDate = employee.getDateOfJoining();
		Date finYearLastDate = financialYear.getDateTo();
		 
		String dateDiffersnce = DateUtils.getDateTdsDif(joiningDate, finYearLastDate);
		logger.info("Date month :  "+dateDiffersnce);
		String[] differences = dateDiffersnce.split(",");
		
		boolean tdsFullYearFlag = Integer.parseInt(differences[0]) > 0 ? true : false;
		List<TdsGroupSetup> tdsGroupList = investmentService.getInvestmentList(companyId, financialYear);
		
		DateFormat mmm = new SimpleDateFormat("MMM");
		String dojMonth = mmm.format(joiningDate);
		System.out.println(dojMonth);
		int index=0;
		
		List<TdsDeductionDTO> newTdsDeductionList = new ArrayList<>();
		
		if(!tdsFullYearFlag) {
			for (TdsDeductionDTO tdsDeductionDTO : tdsDeductionList) {
				String[] processMonth =tdsDeductionDTO.getProcessMonth().split("-");
				if(processMonth[0].equalsIgnoreCase(dojMonth)) {
					while(index<tdsDeductionList.size()) {
						newTdsDeductionList.add(tdsDeductionList.get(index));
						index++;
					}
					break;
//					System.out.println("singya");
				}
				index++;
			}
		}else {
			newTdsDeductionList.addAll(tdsDeductionList);
		}
		 
		if (newTdsDeductionList != null && newTdsDeductionList.size() > 0) {
			for(TdsDeductionDTO tdsDeductionDTO:newTdsDeductionList) {
				TdsSummaryChangeDTO tdsSummaryChangeDTO =new  TdsSummaryChangeDTO();
				if (tdsDeductionDTO.getTotalTax().compareTo(BigDecimal.ZERO) == 0) {

					tdsSummaryChangeDTO = tdsSummaryBeforeDeclarationService.getTdsSummaryBeforeDeclation(tdsGroupList, employee, financialYear, companyId , tdsDeductionDTO.getUserId(), false);
					tdsDeductionDTO.setTotalTax(tdsSummaryChangeDTO.getTotalTax());
					tdsDeductionDTO.setTaxTobeDeductedMonthly(tdsSummaryChangeDTO.getNetTaxMonthly());
				}
			}
			return newTdsDeductionList;
		}
		else
			throw new ErrorHandling("TdsDEduction data not present");
		/*
		if (employeeTdsDeductionList!=null )
			return tdsDeductionAdaptor.databaseModelToUiSummaryDto(employeeTdsDeductionList, financialYear.getFinancialYear());
		else
			throw new ErrorHandling("TdsDEduction data not present");
*/
	}
	

	
	
//	@GetMapping(value = "findAlltdsDeduction/{companyId}")
//	public List<TdsDeductionDTO> searchTdsDeduction(@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
//		logger.info("findAlltdsDeduction is calling : " + " : companyId " + companyId);
	
//		DateUtils dateUtils = new DateUtils();
//		Date currentDate = dateUtils.getCurrentDate();
//		
//		FinancialYear financialYear = financialYearService.findCurrentFinancialYear(currentDate, companyId);
//		List<Object[]> tdsDeductionList = tdsDeductionService.searchTdsDeduction(companyId, financialYear.getFinancialYearId());
//		if (tdsDeductionList != null && tdsDeductionList.size() > 0)
//			return tdsDeductionAdaptor.tdsDeductionListdatabaseModelToUiDtoList(tdsDeductionList);
//		else
//			throw new ErrorHandling("TdsDEduction data not present");
//
//	}
	
	
}
