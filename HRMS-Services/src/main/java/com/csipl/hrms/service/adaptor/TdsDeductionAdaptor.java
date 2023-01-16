package com.csipl.hrms.service.adaptor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.payroll.TdsDeductionDTO;
import com.csipl.hrms.dto.payroll.TdsSummaryChangeDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.TdsDeduction;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.employee.EmployeePersonalInformationServiceImpl;
import com.csipl.hrms.service.payroll.TdsSummaryBeforeDeclarationService;
import com.csipl.hrms.service.payroll.TdsSummaryBeforeDeclarationServiceImpl;

public class TdsDeductionAdaptor implements Adaptor<TdsDeductionDTO, TdsDeduction> {
 

	@Override
	public List<TdsDeduction> uiDtoToDatabaseModelList(List<TdsDeductionDTO> uiobj) {

		return null;
	}

	@Override
	public List<TdsDeductionDTO> databaseModelToUiDtoList(List<TdsDeduction> dbobj) {

		return null;
	}

	@Override
	public TdsDeduction uiDtoToDatabaseModel(TdsDeductionDTO tdsDeductionDTO) {
		TdsDeduction tdsDeduction = new TdsDeduction();
		Company company = new Company();
		company.setCompanyId(tdsDeductionDTO.getCompanyId());
		tdsDeduction.setCompany(company);

		FinancialYear financialYear = new FinancialYear();
		financialYear.setFinancialYearId(tdsDeductionDTO.getFinancialYearId());
		tdsDeduction.setFinancialYear(financialYear);

		Employee employee = new Employee();
		employee.setEmployeeId(tdsDeductionDTO.getEmployeeId());
		tdsDeduction.setEmployee(employee);

		tdsDeduction.setDateCreated(tdsDeductionDTO.getDateCreated());
		tdsDeduction.setDateUpdate(tdsDeductionDTO.getDateUpdate());
		tdsDeduction.setRemark(tdsDeductionDTO.getRemark());
		tdsDeduction.setTaxDeductedMonthly(tdsDeductionDTO.getTaxDeductedMonthly());
		tdsDeduction.setTaxTobeDeductedMonthly(tdsDeductionDTO.getTaxTobeDeductedMonthly());
		tdsDeduction.setActiveStatus(StatusMessage.ACTIVE_CODE);
		tdsDeduction.setTdsDeductionId(tdsDeductionDTO.getTdsDeductionId());
		// tdsDeduction.setTotalTax(new BigDecimal((tdsDeductionDTO.getTotalTax()));
		tdsDeduction.setUserId(tdsDeductionDTO.getUserId());
		tdsDeduction.setUserIdUpdate(tdsDeductionDTO.getUserIdUpdate());
		tdsDeduction.setDateUpdate(tdsDeductionDTO.getDateUpdate());
		tdsDeduction.setDateCreated(new Date());

		// if (tdsDeductionDTO.getTdsDeductionId() != null) {
		// tdsDeduction.setDateCreated(tdsDeductionDTO.getDateCreated());
		// } else {
		// tdsDeduction.setDateCreated(new Date());
		// }
		return tdsDeduction;
	}

	@Override
	public TdsDeductionDTO databaseModelToUiDto(TdsDeduction tdsDeduction) {

		TdsDeductionDTO tdsDeductionDTO = new TdsDeductionDTO();

		tdsDeductionDTO.setTdsDeductionId(tdsDeduction.getTdsDeductionId());
		tdsDeductionDTO.setCompanyId(tdsDeduction.getCompany().getCompanyId());
		tdsDeductionDTO.setFinancialYearId(tdsDeduction.getFinancialYear().getFinancialYearId());
		tdsDeductionDTO.setRemark(tdsDeduction.getRemark());
		tdsDeductionDTO.setTaxDeductedMonthly(tdsDeduction.getTaxDeductedMonthly());
		tdsDeductionDTO.setTaxTobeDeductedMonthly(tdsDeduction.getTaxTobeDeductedMonthly());
//		tdsDeductionDTO.setTotalTax(new BigDecimal(tdsDeduction.getTotalTax()));
		tdsDeductionDTO.setUserId(tdsDeductionDTO.getUserId());
		tdsDeductionDTO.setEmployeeId(tdsDeductionDTO.getEmployeeId());
		tdsDeductionDTO.setActiveStatus(tdsDeduction.getActiveStatus());
		return tdsDeductionDTO;
	}

	public List<TdsDeductionDTO> tdsDeductionListdatabaseModelToUiDtoList(List<Object[]> tdsDeductionList,
			List<TdsGroupSetup> tdsGroupList, FinancialYear financialYear) {
		List<TdsDeductionDTO> tdsDeductionDto = new ArrayList<>();
		// EmployeeDTO employeeDTO = new EmployeeDTO();
		// Employee employee = new Employee();

		/**
		 * td.tdsDeductionId, td.financialYearId, td.taxTobeDeductedMonthly,
		 * td.taxDeductedMonthly, td.userId, td.totalTax td.remark, td.userIdUpdate
		 * td.dateUpdate, td.dateCreated, td.companyId, emp.firstName, emp.lastName
		 * 
		 * 
		 */
		for (Object[] tdsDeduction : tdsDeductionList) {
			TdsDeductionDTO tdsDeductionDTO = new TdsDeductionDTO();

			long emolyeeId = 0l;

//			TdsSummaryChangeDTO tdsSummaryChangeDTO = new TdsSummaryChangeDTO();

			if (tdsDeduction[0] != null) {
				tdsDeductionDTO.setTdsDeductionId(Long.valueOf(tdsDeduction[0].toString()));

			}
			
			if (tdsDeduction[1] != null) {
				tdsDeductionDTO.setCompanyId(Long.valueOf(tdsDeduction[1].toString()));
			}
			if (tdsDeduction[2] != null) {
				tdsDeductionDTO.setFinancialYearId(Long.valueOf(tdsDeduction[2].toString()));
			}
			if (tdsDeduction[3] != null) {
				tdsDeductionDTO.setTaxTobeDeductedMonthly(new BigDecimal(tdsDeduction[3].toString()));
			}  

			if (tdsDeduction[4] != null) {
				tdsDeductionDTO.setTaxDeductedMonthly(new BigDecimal(tdsDeduction[4].toString()));
			}
			if (tdsDeduction[5] != null) {
				tdsDeductionDTO.setUserId(Long.valueOf(tdsDeduction[5].toString()));
			}

			if (tdsDeduction[6] != null) {
				tdsDeductionDTO.setTotalTax(new BigDecimal(tdsDeduction[6].toString()));
			} 

			if (tdsDeduction[7] != null) {
				tdsDeductionDTO.setRemark((tdsDeduction[7].toString()));
			}

			if (tdsDeduction[8] != null) {
				tdsDeductionDTO.setFirstName(tdsDeduction[8].toString());
			}
			if (tdsDeduction[9] != null) {
				tdsDeductionDTO.setLastName(tdsDeduction[9].toString());

			}
			if (tdsDeduction[10] != null) {
				emolyeeId = Long.valueOf(tdsDeduction[10].toString());
				tdsDeductionDTO.setEmployeeId(Long.valueOf(tdsDeduction[10].toString()));
			}
			if (tdsDeduction[8] != null && tdsDeduction[9] != null) {
				tdsDeductionDTO.setEmployeeName(tdsDeduction[8].toString() + " " + tdsDeduction[9].toString());
			}

			if (tdsDeduction[11] != null) {
				tdsDeductionDTO.setDesignationName((tdsDeduction[11].toString()));
			}
			if (tdsDeduction[12] != null) {
				tdsDeductionDTO.setActiveStatus((tdsDeduction[12].toString()));
			}

			
			tdsDeductionDto.add(tdsDeductionDTO);
		}

		// System.out.println("TdsDeductionAdaptor.()"+tdsDeductionDto.toString());
		return tdsDeductionDto;
	}

	public List<EmployeeDTO> databaseObjModelToUiDtoList(List<Object[]> employeeObjList) throws ParseException {

		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] employeeObj : employeeObjList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			if (employeeObj[0] != null) {
				employeeDto.setEmployeeId(employeeObj[0] != null ? Long.parseLong(employeeObj[0].toString()) : null);
			}

			if (employeeObj[1] != null) {
				employeeDto.setFirstName(employeeObj[1].toString());
			}
			if (employeeObj[2] != null) {
				employeeDto.setLastName(employeeObj[2].toString());
			}
			if (employeeObj[3] != null) {
				employeeDto.setEmployeeCode(employeeObj[3].toString());
			}

			if (employeeObj[4] != null) {
				employeeDto.setEmployeeLogoPath(employeeObj[4].toString());
			}
			if (employeeObj[5] != null) {
				employeeDto.setDepartmentId(employeeObj[5] != null ? Long.parseLong(employeeObj[5].toString()) : null);
			}
			if (employeeObj[6] != null) {
				employeeDto.setDepartmentName(employeeObj[6].toString());
			}
			if (employeeObj[7] != null) {
				employeeDto.setDesignationId(employeeObj[7] != null ? Long.parseLong(employeeObj[5].toString()) : null);
			}
			if (employeeObj[8] != null) {
				employeeDto.setDesignationName(employeeObj[8].toString());
			}
			if (employeeObj[9] != null) {
				Date date = employeeObj[9] != null ? (Date) employeeObj[9] : null;
				employeeDto.setDateOfJoining(date);
			}
			if (employeeObj[10] != null) {
				employeeDto.setTdsLockUnlockStatus(employeeObj[10].toString());
			}

			employeeDto.setFullNameCodeVaues(employeeDto.getFirstName() + " " + employeeDto.getLastName() + " ("
					+ employeeDto.getEmployeeCode() + ")");

			employeeDtoList.add(employeeDto);

		}
		return employeeDtoList;

	}

	public List<EmployeeDTO> databaseObjModelToUiDtoEmployeeList(List<Object[]> employeeObjList) throws ParseException {

		List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();

		for (Object[] employeeObj : employeeObjList) {
			EmployeeDTO employeeDto = new EmployeeDTO();
			if (employeeObj[0] != null) {
				employeeDto.setEmployeeId(employeeObj[0] != null ? Long.parseLong(employeeObj[0].toString()) : null);
			}

			if (employeeObj[1] != null) {
				employeeDto.setFirstName(employeeObj[1].toString());
			}
			if (employeeObj[2] != null) {
				employeeDto.setLastName(employeeObj[2].toString());
			}
			if (employeeObj[3] != null) {
				employeeDto.setEmployeeCode(employeeObj[3].toString());
			}

			if (employeeObj[4] != null) {
				employeeDto.setEmployeeLogoPath(employeeObj[4].toString());
			}
			if (employeeObj[5] != null) {
				employeeDto.setDepartmentId(employeeObj[5] != null ? Long.parseLong(employeeObj[5].toString()) : null);
			}
			if (employeeObj[6] != null) {
				employeeDto.setDepartmentName(employeeObj[6].toString());
			}
			if (employeeObj[7] != null) {
				employeeDto.setDesignationId(employeeObj[7] != null ? Long.parseLong(employeeObj[5].toString()) : null);
			}
			if (employeeObj[8] != null) {
				employeeDto.setDesignationName(employeeObj[8].toString());
			}
			if (employeeObj[9] != null) {
				Date date = employeeObj[9] != null ? (Date) employeeObj[9] : null;
				employeeDto.setDateOfJoining(date);
			}
			if (employeeObj[10] != null) {
				employeeDto.setTdsLockUnlockStatus(employeeObj[10].toString());
			}
			if (employeeObj[11] != null) {
				employeeDto.setTdsStatus(employeeObj[11].toString());
			}
			if (employeeObj[12] != null) {
				employeeDto.setTotalIncome((new BigDecimal(employeeObj[12].toString())));
			}
			if (employeeObj[13] != null) {
				employeeDto.setTaxableIncome((new BigDecimal(employeeObj[13].toString())));
			}
			if (employeeObj[14] != null) {
				employeeDto.setTotalRebate((new BigDecimal(employeeObj[14].toString())));
			}
			employeeDto.setFullNameCodeVaues(employeeDto.getFirstName() + " " + employeeDto.getLastName() + " ("
					+ employeeDto.getEmployeeCode() + ")");

			employeeDtoList.add(employeeDto);

		}
		return employeeDtoList;

	}

	public List<TdsDeductionDTO> databaseModelToUiSummaryDto(List<Object[]> tdsDeductionList, String financialYear) throws ParseException{
		List<TdsDeductionDTO> tdsDeductionDto = new ArrayList<>();
		
		for (Object[] tdsDeduction : tdsDeductionList) {
			TdsDeductionDTO tdsDeductionDTO = new TdsDeductionDTO();

			long emolyeeId = 0l;
			
			if (tdsDeduction[0] != null) {
				emolyeeId = Long.valueOf(tdsDeduction[0].toString());
				tdsDeductionDTO.setEmployeeId(Long.valueOf(tdsDeduction[0].toString()));
			}

			if (tdsDeduction[1] != null) {
				tdsDeductionDTO.setFirstName(tdsDeduction[1].toString());
			}
			if (tdsDeduction[2] != null) {
				tdsDeductionDTO.setLastName(tdsDeduction[2].toString());

			}

			if (tdsDeduction[3] != null) {
				tdsDeductionDTO.setDesignationName((tdsDeduction[3].toString()));
			}

			// if (tdsDeduction[8] != null && tdsDeduction[9] != null) {
			// tdsDeductionDTO.setEmployeeName(tdsDeduction[8].toString() + " " +
			// tdsDeduction[9].toString());
			// }
			if (tdsDeduction[4] != null) {
				tdsDeductionDTO.setProcessMonth(tdsDeduction[4].toString());
			}

			if (tdsDeduction[5] != null) {
				tdsDeductionDTO.setTaxDeductedMonthly(new BigDecimal(tdsDeduction[5].toString()));
			}else {
				tdsDeductionDTO.setTaxDeductedMonthly(new BigDecimal(0.00));
			}

			if (tdsDeduction[6] != null) {
				tdsDeductionDTO.setTotalTax(new BigDecimal(tdsDeduction[6].toString()));
			}else {
				tdsDeductionDTO.setTotalTax(new BigDecimal(0.00));
			}

			if (tdsDeduction[7] != null) {
				tdsDeductionDTO.setTaxTobeDeductedMonthly(new BigDecimal(tdsDeduction[7].toString()));
			}else {
				tdsDeductionDTO.setTaxTobeDeductedMonthly(new BigDecimal(0.00));
			}

			if (tdsDeduction[8] != null) {
				tdsDeductionDTO.setTdsAmount(new BigDecimal(tdsDeduction[8].toString()));
			}else {
				tdsDeductionDTO.setTdsAmount(new BigDecimal(0.00));
			}

			if (tdsDeduction[9] != null) {
				tdsDeductionDTO.setRemark((tdsDeduction[9].toString()));
			}
			tdsDeductionDTO.setFinancialYear(financialYear);
			
			tdsDeductionDto.add(tdsDeductionDTO);
		}
		return tdsDeductionDto;
	}
	
	
	
}
