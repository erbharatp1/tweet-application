package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.organisation.ProjectDTO;
import com.csipl.hrms.dto.payroll.FinancialMonthDTO;
import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.PayrollControlDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.Groupg;
import com.csipl.hrms.model.organisation.Client;
import com.csipl.hrms.model.organisation.Department;
import com.csipl.hrms.model.organisation.Project;
import com.csipl.hrms.model.payrollprocess.FinancialMonth;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.model.payrollprocess.PayrollControl;
import com.csipl.hrms.service.util.PayrollDateCalculation;

public class PayrollPeriodAdaptor implements Adaptor<FinancialYearDTO, FinancialYear> {

	PayrollDateCalculation payrollDateCal = new PayrollDateCalculation();
	PayrollControlAdaptor payrollControlAdaptor = new PayrollControlAdaptor();

	@Override
	public FinancialYear uiDtoToDatabaseModel(FinancialYearDTO financialYearDto) {
		List<PayrollControl> payrollControlList = new ArrayList<PayrollControl>();
		FinancialYear financialYear = new FinancialYear();

		if (financialYearDto.getFinancialYearId() != null && financialYearDto.getFinancialYearId() != 0) {
			financialYear.setFinancialYearId(financialYearDto.getFinancialYearId());
			financialYear.setUserIdUpdate(financialYearDto.getUserId());
			financialYear.setDateUpdate(new Date());
			financialYear.setDateCreated(financialYearDto.getDateCreated());

		} else {
			financialYear.setDateCreated(new Date());
		}
	/*	Groupg groupg = new Groupg();
		groupg.setGroupId(financialYearDto.getGroupId());//coment future for enhancement 
		financialYear.setGroupg(groupg);
*/		financialYear.setUserId(financialYearDto.getUserId());
		financialYear.setDateFrom(financialYearDto.getDateFrom());
		financialYear.setIsPayrollDaysManuals(financialYearDto.getIsPayrollDaysManuals());
		financialYear.setIsWeekOffConsider(financialYearDto.getIsWeekoffConsider());
		financialYear.setIsHolidayConsider(financialYearDto.getIsHolidayConsider());
		

		financialYear.setDateTo(payrollDateCal.getLastDate(financialYear.getDateFrom()));

		financialYear.setFinancialYear(payrollDateCal.getFinancialYear(financialYear.getDateFrom()));

		financialYear.setPayrollControls(payrollDateCal.getPayrollControl(financialYear,financialYearDto.getPayrollDays()));
		// financialYear.setUserId(financialYearDto.getUserId());
		Company comp = new Company();
		comp.setCompanyId(financialYearDto.getCompanyId());
		financialYear.setCompany(comp);

		financialYear.setActiveStatus(financialYearDto.getActiveStatus());

		// financialYear.setDateCreated(financialYearDto.getDateCreated());

		return financialYear;
	}

	@Override
	public List<FinancialYear> uiDtoToDatabaseModelList(List<FinancialYearDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PayrollControlDTO> databaseModelToPayControlDto(FinancialYear financialYear) {

		return payrollControlAdaptor.databaseModelToUiDtoList(financialYear.getPayrollControls());
	}

	@Override
	public FinancialYearDTO databaseModelToUiDto(FinancialYear financialYear) {
		// TODO Auto-generated method stub

		FinancialYearDTO financialYearDTO = new FinancialYearDTO();
		financialYearDTO.setCompanyId(financialYear.getCompany().getCompanyId());
		financialYearDTO.setFinancialYear(financialYear.getFinancialYear());
		financialYearDTO.setDateTo(financialYear.getDateTo());
		financialYearDTO.setDateFrom(financialYear.getDateFrom());
		financialYearDTO.setFinancialYearId(financialYear.getFinancialYearId());
		financialYearDTO.setIsPayrollDaysManuals(financialYear.getIsPayrollDaysManuals());
		financialYearDTO.setIsHolidayConsider(financialYear.getIsHolidayConsider());
		financialYearDTO.setIsWeekoffConsider(financialYear.getIsWeekOffConsider());
		financialYearDTO.setPayrollDays(financialYear.getPayrollControls().get(0).getPayrollDays());
		financialYearDTO.setUserId(financialYear.getUserId());
		financialYearDTO.setActiveStatus(financialYear.getActiveStatus());
		financialYearDTO.setDateCreated(financialYear.getDateCreated());
		return financialYearDTO;
	}

	@Override
	public List<FinancialYearDTO> databaseModelToUiDtoList(List<FinancialYear> finacialYearList) {

		List<FinancialYearDTO> financialYearDTOList = new ArrayList<FinancialYearDTO>();

		for (FinancialYear financialYear : finacialYearList) {
			financialYearDTOList.add(databaseModelToUiDto(financialYear));
		}

		// TODO Auto-generated method stub
		return financialYearDTOList;
	}

	public FinancialMonthDTO databaseModelToUiMonthDto(FinancialMonth financialMonth) {
		FinancialMonthDTO financialMonthDTO = new FinancialMonthDTO();
		financialMonthDTO.setFinancialMonthId(financialMonth.getFinancialMonthId());
		financialMonthDTO.setMonth(financialMonth.getMonth());
		financialMonthDTO.setUserId(financialMonth.getUserId());
		financialMonthDTO.setDateCreated(financialMonth.getDateCreated());
		financialMonthDTO.setUserIdUpdate(financialMonth.getUserIdUpdate());
		financialMonthDTO.setUpdatedDate(financialMonth.getUpdatedDate());
		financialMonthDTO.setCompanyId(financialMonth.getCompanyId());
		return financialMonthDTO;

	}

}
