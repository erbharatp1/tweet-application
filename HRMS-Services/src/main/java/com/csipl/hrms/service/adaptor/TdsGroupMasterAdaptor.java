package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.FinancialYearDTO;
import com.csipl.hrms.dto.payroll.TdsGroupMasterDTO;
import com.csipl.hrms.dto.payroll.TdsGroupSetupDTO;
import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public class TdsGroupMasterAdaptor implements Adaptor<TdsGroupMasterDTO, TdsGroupMaster> {

	@Override
	public List<TdsGroupMaster> uiDtoToDatabaseModelList(List<TdsGroupMasterDTO> TdsGroupMasterDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsGroupMasterDTO> databaseModelToUiDtoList(List<TdsGroupMaster> tdsGroupMasterList) {
		List<TdsGroupMasterDTO> tdsGroupSetupDtoList = new ArrayList<TdsGroupMasterDTO>();
		tdsGroupMasterList.forEach(items -> {
			tdsGroupSetupDtoList.add(databaseModelToUiDto(items));
		});

		return tdsGroupSetupDtoList;
	}

	@Override
	public TdsGroupMaster uiDtoToDatabaseModel(TdsGroupMasterDTO tdsGroupMasterDTO) {
		TdsGroupMaster tdsGroupMaster = new TdsGroupMaster();
		tdsGroupMaster.setCompanyId(tdsGroupMasterDTO.getCompanyId());
		// tdsGroupSetup.setTdsGroupId(tdsGroupMasterDTO.getTdsGroupId());
		// tdsGroupSetup.setTdsGroupName(tdsGroupSetupDTO.getTdsGroupName());
		// tdsGroupSetup.setActiveStatus(tdsGroupMasterDTO.getActiveStatus());
		// tdsGroupSetup.setIsSubGroupLimit(tdsGroupSetupDTO.getIsSubGroupLimit());
		// tdsGroupSetup.setMaxLimit(tdsGroupMasterDTO.getMaxLimit());
		tdsGroupMaster.setUserId(tdsGroupMasterDTO.getUserId());
		// tdsGroupSetup.setDateCreated(tdsGroupMasterDTO.getDateCreated());
		// if (tdsGroupMasterDTO.getDateCreated() == null)
		// tdsGroupMaster.setDateCreated(new Date());
//		else
		// tdsGroupSetup.setDateCreated(tdsGroupMasterDTO.getDateCreated());
		// tdsGroupMaster.setDateUpdate(new Date());
		// tdsGroupSetup.setUserIdUpdate(tdsGroupMasterDTO.getUserIdUpdate());
		return tdsGroupMaster;
	}

	public TdsGroupSetup uiDtoToDatabaseModel(TdsGroupSetupDTO tdsGroupSetupDTO, TdsGroupSetup tdsGroupSetup) {

		tdsGroupSetup.setCompanyId(tdsGroupSetupDTO.getCompanyId());
		tdsGroupSetup.setTdsGroupId(tdsGroupSetupDTO.getTdsGroupId());
		// tdsGroupSetup.setTdsGroupName(tdsGroupSetupDTO.getTdsGroupName());
		tdsGroupSetup.setActiveStatus(tdsGroupSetupDTO.getActiveStatus());
		// tdsGroupSetup.setIsSubGroupLimit(tdsGroupSetupDTO.getIsSubGroupLimit());
		tdsGroupSetup.setMaxLimit(tdsGroupSetupDTO.getMaxLimit());
		tdsGroupSetup.setUserId(tdsGroupSetupDTO.getUserId());
		// tdsGroupSetup.setFinancialYear(tdsGroupSetupDTO.getFinancialYearId());
		if (tdsGroupSetupDTO.getDateCreated() == null)
			tdsGroupSetup.setDateCreated(new Date());
		else
			tdsGroupSetup.setDateCreated(tdsGroupSetupDTO.getDateCreated());
		tdsGroupSetup.setDateUpdate(new Date());
		tdsGroupSetup.setUserIdUpdate(tdsGroupSetupDTO.getUserId());
		return tdsGroupSetup;
	}

	@Override
	public TdsGroupMasterDTO databaseModelToUiDto(TdsGroupMaster tdsGroupMaster) {
		TdsGroupMasterDTO tdsGroupMasterDTO = new TdsGroupMasterDTO();
		/*
		 * List<TdsGroupSetupDTO> tdsGroupSetupsList = new
		 * ArrayList<TdsGroupSetupDTO>(); tdsGroupSetupsList.forEach(items -> {
		 * TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		 * tdsGroupSetup.setActiveStatus(items.getActiveStatus());
		 * tdsGroupSetup.setTdsGroupId(items.getTdsGroupId());
		 * tdsGroupSetup.setMaxLimit(items.getMaxLimit());
		 * tdsGroupSetup.setUserId(items.getUserId()); });
		 */
		
		//tdsGroupMasterDTO.setTdsGroupSetups(tdsGroupSetupsList);
		tdsGroupMasterDTO.setIsSubGroupLimit(tdsGroupMaster.getIsSubGroupLimit());
		tdsGroupMasterDTO.setTdsGroupName(tdsGroupMaster.getTdsGroupName());
		tdsGroupMasterDTO.setTdsGroupMasterId(tdsGroupMaster.getTdsGroupMasterId());
		tdsGroupMasterDTO.setCompanyId(tdsGroupMaster.getCompanyId());
		tdsGroupMasterDTO.setUserId(tdsGroupMaster.getUserId());
		return tdsGroupMasterDTO;
	}

	public List<TdsGroupSetup> uiDtoToDatabaseModelListTds(List<TdsGroupSetupDTO> tdsGroupSetupDtoList,
			TdsGroupSetup tdsGroupSetup) {
		List<TdsGroupSetup> dsGroupSetupList = new ArrayList<TdsGroupSetup>();
		tdsGroupSetupDtoList.forEach(tdsGroupSetupDto -> {
			dsGroupSetupList.add(uiDtoToDatabaseModel(tdsGroupSetupDto, tdsGroupSetup));
		});
		return dsGroupSetupList;
	}

	public TdsGroupSetup tdsSectionUitoDatabaseModel(TdsGroupSetupDTO tdsGroupSetupDTO) {
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setCompanyId(tdsGroupSetupDTO.getCompanyId());
		tdsGroupSetup.setTdsGroupId(tdsGroupSetupDTO.getTdsGroupId());
		tdsGroupSetup.setActiveStatus(tdsGroupSetupDTO.getActiveStatus());
		tdsGroupSetup.setMaxLimit(tdsGroupSetupDTO.getMaxLimit());
		tdsGroupSetup.setUserId(tdsGroupSetupDTO.getUserId());
		tdsGroupSetup.setDateCreated(tdsGroupSetupDTO.getDateCreated());
		if (tdsGroupSetupDTO.getDateCreated() == null)
			tdsGroupSetup.setDateCreated(new Date());
		else
			tdsGroupSetup.setDateCreated(tdsGroupSetupDTO.getDateCreated());
		tdsGroupSetup.setDateUpdate(new Date());
		tdsGroupSetup.setUserIdUpdate(tdsGroupSetupDTO.getUserId());

		return tdsGroupSetup;
	}

	public List<TdsGroupSetup> tdsSectionuiDtoToDatabaseModelListTds(List<TdsGroupSetupDTO> tdsGroupSetupDtoList) {
		List<TdsGroupSetup> tdsGroupSetup = new ArrayList<TdsGroupSetup>();
		tdsGroupSetupDtoList.forEach(tdsGroupSetupDTO -> {
			tdsGroupSetup.add(tdsSectionUitoDatabaseModel(tdsGroupSetupDTO));
		});

		return tdsGroupSetup;
	}

	public List<TdsGroupSetupDTO> tdsSectiondatabaseModelToUiDtoList(List<TdsGroupSetup> tdsGroupSetupList) {
		List<TdsGroupSetupDTO> tdsGroupSetupDTO = new ArrayList<TdsGroupSetupDTO>();

		tdsGroupSetupList.forEach(tdsGroupSetup -> {
			tdsGroupSetupDTO.add(tdsSectionbDatabaseModelToUi(tdsGroupSetup));
		});

		return tdsGroupSetupDTO;
	}

	private TdsGroupSetupDTO tdsSectionbDatabaseModelToUi(TdsGroupSetup tdsGroupSetup) {
		TdsGroupSetupDTO tdsGroupSetupDTO = new TdsGroupSetupDTO();
		tdsGroupSetupDTO.setFinancialYearId(tdsGroupSetup.getFinancialYear().getFinancialYearId());
		tdsGroupSetupDTO.setUserId(tdsGroupSetup.getUserId());
		tdsGroupSetupDTO.setTdsGroupMasterId(tdsGroupSetup.getTdsGroupMaster().getTdsGroupMasterId());
		tdsGroupSetupDTO.setDateCreated(tdsGroupSetup.getDateCreated());
		tdsGroupSetupDTO.setUserIdUpdate(tdsGroupSetup.getUserIdUpdate());
		tdsGroupSetupDTO.setCompanyId(tdsGroupSetup.getCompanyId());
		tdsGroupSetupDTO.setTdsGroupId(tdsGroupSetup.getTdsGroupId());
		tdsGroupSetupDTO.setTdsGroupName(tdsGroupSetup.getTdsGroupMaster().getTdsGroupName());
		tdsGroupSetupDTO.setFinancialYearName(tdsGroupSetup.getFinancialYear().getFinancialYear());
		tdsGroupSetupDTO.setMaxLimit(tdsGroupSetup.getMaxLimit());
		tdsGroupSetupDTO.setActiveStatus(tdsGroupSetup.getActiveStatus());

		return tdsGroupSetupDTO;

	}

	/**
	 * financialdatabaseModelToUiDto}
	 */
	public FinancialYearDTO financialdatabaseModelToUiDto(FinancialYear findCurrentFinancialYear) {
		FinancialYearDTO financialYearDTO = new FinancialYearDTO();
		financialYearDTO.setCompanyId(findCurrentFinancialYear.getCompany().getCompanyId());
		financialYearDTO.setActiveStatus(findCurrentFinancialYear.getActiveStatus());
		financialYearDTO.setFinancialYearId(findCurrentFinancialYear.getFinancialYearId());
		financialYearDTO.setFinancialYear(findCurrentFinancialYear.getFinancialYear());
		financialYearDTO.setDateFrom(findCurrentFinancialYear.getDateFrom());
		financialYearDTO.setDateTo(findCurrentFinancialYear.getDateTo());
		financialYearDTO.setUserId(findCurrentFinancialYear.getUserId());
		financialYearDTO.setDateCreated(findCurrentFinancialYear.getDateCreated());
//		financialYearDTO.setGroupId(findCurrentFinancialYear.getGroupg().getGroupId());
		return financialYearDTO;
	}

	public List<TdsGroupSetup> tdsGroupMasterTotdsGroupSetupConversion(List<TdsGroupMaster> tdsGroupMasterList,Long financialYearId) {
		List<TdsGroupSetup> tdsGroupSetupList= new ArrayList<TdsGroupSetup>();
		tdsGroupMasterList.forEach(group -> {
			tdsGroupSetupList.add(setupUiToDatabaseModel(group,financialYearId));
		});
		return tdsGroupSetupList;
	}

	private TdsGroupSetup setupUiToDatabaseModel(TdsGroupMaster tdsGroupMaster,Long financialYearId) {
		
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setActiveStatus("AC");
		tdsGroupSetup.setCompanyId(tdsGroupMaster.getCompanyId());
		tdsGroupSetup.setDateCreated(new Date());
		
		FinancialYear financialYear = new FinancialYear();
		financialYear.setFinancialYearId(financialYearId);
		tdsGroupSetup.setFinancialYear(financialYear);
		
		tdsGroupSetup.setTdsGroupMaster(tdsGroupMaster);
		
		tdsGroupSetup.setUserId(tdsGroupMaster.getUserId());
		return tdsGroupSetup;
	}

}
