package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.payroll.TdsGroupSetupDTO;
import com.csipl.hrms.dto.payroll.TdsSectionSetupDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.EsiCycle;
import com.csipl.hrms.model.payroll.ProfessionalTaxInfo;
import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.model.payrollprocess.FinancialYear;
import com.csipl.hrms.service.payroll.TdsGroupSetupService;
import com.csipl.hrms.service.payroll.TdsSectionSetupService;

public class TdsGroupSetupAdaptor implements Adaptor<TdsGroupSetupDTO, TdsGroupSetup> {

	@Autowired
	TdsGroupSetupService tdsGroupSetupService;

	
	public List<TdsGroupSetup> uisetupDtoToDatabaseModelList(List<TdsGroupSetupDTO> tdsGroupSetupDTO,Long financialYearId) {
		List<TdsGroupSetup> tdsGroupSetupList = new ArrayList<TdsGroupSetup>();
		tdsGroupSetupDTO.forEach(setup -> {
			tdsGroupSetupList.add(setupUiToDatabaseModel(setup,financialYearId));
		});

		return tdsGroupSetupList;
	}

	private TdsGroupSetup setupUiToDatabaseModel(TdsGroupSetupDTO setup,Long financialYearId) {
		TdsGroupSetup tdsGroupSetup=new TdsGroupSetup();
		TdsGroupMaster tdsGroupMaster = new TdsGroupMaster();

		tdsGroupMaster.setTdsGroupMasterId(setup.getTdsGroupMasterId());
		tdsGroupSetup.setTdsGroupMaster(tdsGroupMaster);
		tdsGroupSetup.setActiveStatus("AC");
		FinancialYear financialYear = new FinancialYear();
		financialYear.setFinancialYearId(financialYearId);
		tdsGroupSetup.setFinancialYear(financialYear);
		tdsGroupSetup.setCompanyId(setup.getCompanyId());
		tdsGroupSetup.setUserId(setup.getUserId());
		tdsGroupSetup.setDateCreated(new Date());
		tdsGroupSetup.setDateUpdate(new Date());
		tdsGroupSetup.setTdsGroupId(null);
		tdsGroupSetup.setTdsSectionSetups(uiDtoToSectionList(setup.getTdsSectionSetupDTO(),tdsGroupSetup));
		return tdsGroupSetup;
	}

	private List<TdsSectionSetup> uiDtoToSectionList(List<TdsSectionSetupDTO> tdsSectionSetupDTOList,TdsGroupSetup tdsGroupSetup) {
		
		 List<TdsSectionSetup> tdsSectionSetupList=new ArrayList<TdsSectionSetup>();
		 tdsSectionSetupDTOList.forEach(tdsSectionSetupDTO->{
			 tdsSectionSetupList.add(uiDtoToSectionModel( tdsSectionSetupDTO,tdsGroupSetup)); 
		 });
		 return tdsSectionSetupList;
	}

	private TdsSectionSetup uiDtoToSectionModel(TdsSectionSetupDTO tdsSectionSetupDTO, TdsGroupSetup tdsGroupSetup) {
		// TODO Auto-generated method stub
		TdsSectionSetup tdsSectionSetup=new TdsSectionSetup();
		 tdsSectionSetup.setActiveStatus("AC");
		 tdsSectionSetup.setDateCreated(tdsSectionSetupDTO.getDateCreated());
		 tdsSectionSetup.setDateUpdate(tdsSectionSetupDTO.getDateUpdate());
		 tdsSectionSetup.setMaxLimit(tdsSectionSetupDTO.getMaxLimit());
		 tdsSectionSetup.setTdsSectionName(tdsSectionSetupDTO.getTdsSectionName());
		 tdsSectionSetup.setUserId(tdsSectionSetupDTO.getUserId());
		 tdsSectionSetup.setUserIdUpdate(tdsSectionSetupDTO.getUserIdUpdate());
             //	 setup.setTdsGroupId(setup.getTdsGroupId());
		  tdsSectionSetup.setTdsGroupSetup(tdsGroupSetup);
//		  tdsSectionSetup.add(tdsSectionSetup);
		
		return tdsSectionSetup;
	}

	@Override
	public List<TdsGroupSetupDTO> databaseModelToUiDtoList(List<TdsGroupSetup> tdsGroupSetupList) {
		List<TdsGroupSetupDTO> tdsGroupSetupDtoList = new ArrayList<TdsGroupSetupDTO>();
		tdsGroupSetupList.forEach(items -> {
			tdsGroupSetupDtoList.add(databaseModelToUiDto(items));
		});

		return tdsGroupSetupDtoList;
	}

	@Override
	public TdsGroupSetup uiDtoToDatabaseModel(TdsGroupSetupDTO tdsGroupSetupDTO) {
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setCompanyId(tdsGroupSetupDTO.getCompanyId());
		tdsGroupSetup.setTdsGroupId(tdsGroupSetupDTO.getTdsGroupId());
		// tdsGroupSetup.setTdsGroupName(tdsGroupSetupDTO.getTdsGroupName());
		tdsGroupSetup.setActiveStatus(tdsGroupSetupDTO.getActiveStatus());
		// tdsGroupSetup.setIsSubGroupLimit(tdsGroupSetupDTO.getIsSubGroupLimit());
		tdsGroupSetup.setMaxLimit(tdsGroupSetupDTO.getMaxLimit());
		tdsGroupSetup.setUserId(tdsGroupSetupDTO.getUserId());
		tdsGroupSetup.setDateCreated(tdsGroupSetupDTO.getDateCreated());
		if (tdsGroupSetupDTO.getDateCreated() == null)
			tdsGroupSetup.setDateCreated(new Date());
		else
			tdsGroupSetup.setDateCreated(tdsGroupSetupDTO.getDateCreated());
		tdsGroupSetup.setDateUpdate(new Date());
		tdsGroupSetup.setUserIdUpdate(tdsGroupSetupDTO.getUserIdUpdate());
		return tdsGroupSetup;
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
	public TdsGroupSetupDTO databaseModelToUiDto(TdsGroupSetup tdsGroupSetup) {
		TdsGroupSetupDTO tdsGroupSetupDTO = new TdsGroupSetupDTO();
		FinancialYear financialYear = new FinancialYear();
		financialYear.setFinancialYearId(tdsGroupSetup.getFinancialYear().getFinancialYearId());
		financialYear.setFinancialYear(tdsGroupSetup.getFinancialYear().getFinancialYear());
		tdsGroupSetupDTO.setFinancialYearId(financialYear.getFinancialYearId());
		tdsGroupSetupDTO.setFinancialYearName(financialYear.getFinancialYear());
		tdsGroupSetupDTO.setCompanyId(tdsGroupSetup.getCompanyId());
		tdsGroupSetupDTO.setTdsGroupId(tdsGroupSetup.getTdsGroupId());
		tdsGroupSetupDTO.setTdsGroupMasterId(tdsGroupSetup.getTdsGroupMaster().getTdsGroupMasterId());
		// tdsGroupSetupDTO.setTdsGroupName(tdsGroupSetup.getTdsGroupName());
		tdsGroupSetupDTO.setMaxLimit(tdsGroupSetup.getMaxLimit());
		tdsGroupSetupDTO.setActiveStatus(tdsGroupSetup.getActiveStatus());
		// tdsGroupSetupDTO.setIsSubGroupLimit(tdsGroupSetup.getIsSubGroupLimit());
		tdsGroupSetupDTO.setUserId(tdsGroupSetup.getUserId());
		tdsGroupSetupDTO.setDateCreated(tdsGroupSetup.getDateCreated());
		tdsGroupSetupDTO.setTdsSectionSetupDTO(tdsSectionModelToUiDtoList(tdsGroupSetup.getTdsSectionSetups()));
		;
		return tdsGroupSetupDTO;
	}

	private List<TdsSectionSetupDTO> tdsSectionModelToUiDtoList(List<TdsSectionSetup> tdsSectionSetupsList) {
		List<TdsSectionSetupDTO> tdsSectionSetupDTOList = new ArrayList<TdsSectionSetupDTO>();

		for (TdsSectionSetup tdsSectionSetup : tdsSectionSetupsList) {
			tdsSectionSetupDTOList.add(tdsSectionModelToUi(tdsSectionSetup));
		}
		return tdsSectionSetupDTOList;
	}

	private TdsSectionSetupDTO tdsSectionModelToUi(TdsSectionSetup tdsSectionSetup) {
		TdsSectionSetupDTO tdsSectionSetupDTO = new TdsSectionSetupDTO();
		Company company = new Company();
		tdsSectionSetupDTO.setActiveStatus(tdsSectionSetup.getActiveStatus());
		tdsSectionSetupDTO.setDateCreated(tdsSectionSetup.getDateCreated());
		tdsSectionSetupDTO.setUserIdUpdate(tdsSectionSetup.getUserIdUpdate());
		tdsSectionSetupDTO.setMaxLimit(tdsSectionSetup.getMaxLimit());
		tdsSectionSetupDTO.setTdsSectionId(tdsSectionSetup.getTdsSectionId());
		tdsSectionSetupDTO.setTdsSectionName(tdsSectionSetup.getTdsSectionName());
		tdsSectionSetupDTO.setUserId(tdsSectionSetup.getUserId());
		tdsSectionSetupDTO.setDateUpdate(tdsSectionSetup.getDateUpdate());
		tdsSectionSetupDTO.setTdsGroupId(tdsSectionSetup.getTdsGroupSetup().getTdsGroupId());
		return tdsSectionSetupDTO;
	}

	public List<TdsGroupSetup> uiDtoToDatabaseModelListTds(List<TdsGroupSetupDTO> tdsGroupSetupDtoList,
			TdsGroupSetup tdsGroupSetup) {
		List<TdsGroupSetup> dsGroupSetupList = new ArrayList<TdsGroupSetup>();
		tdsGroupSetupDtoList.forEach(tdsGroupSetupDto -> {
			dsGroupSetupList.add(uiDtoToDatabaseModel(tdsGroupSetupDto, tdsGroupSetup));
		});
		/*
		 * for (TdsGroupSetupDTO tdsGroupSetupDto : tdsGroupSetupDtoList) {
		 * dsGroupSetupList.add(uiDtoToDatabaseModel(tdsGroupSetupDto, tdsGroupSetup));
		 * }
		 */

		return dsGroupSetupList;
	}
	/*
	 * tdsGroupSetupList.forEach(items -> {
	 * tdsGroupSetupDtoList.add(databaseModelToUiDto(items)); });
	 */

	public TdsGroupSetup tdsSetupUitoDatabaseModel(TdsGroupSetupDTO tdsGroupSetupDTO) {
		TdsGroupSetup tdsGroupSetup = new TdsGroupSetup();
		tdsGroupSetup.setCompanyId(tdsGroupSetupDTO.getCompanyId());
		tdsGroupSetup.setTdsGroupId(tdsGroupSetupDTO.getTdsGroupId());

		tdsGroupSetup.setActiveStatus(tdsGroupSetupDTO.getActiveStatus());
		// tdsGroupSetup.setIsSubGroupLimit(tdsGroupSetupDTO.getIsSubGroupLimit());
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
			tdsGroupSetup.add(tdsSetupUitoDatabaseModel(tdsGroupSetupDTO));
		});

		/*
		 * for (TdsGroupSetupDTO tdsGroupSetupDTO : tdsGroupSetupDtoList) {
		 * tdsGroupSetup.add(tdsSectionUitoDatabaseModel(tdsGroupSetupDTO)); }
		 */
		return tdsGroupSetup;
	}

	public List<TdsGroupSetupDTO> tdsSectiondatabaseModelToUiDtoList(List<TdsGroupSetup> tdsGroupSetupList) {
		List<TdsGroupSetupDTO> tdsGroupSetupDTO = new ArrayList<TdsGroupSetupDTO>();

		tdsGroupSetupList.forEach(tdsGroupSetup -> {
			tdsGroupSetupDTO.add(tdsSetupDatabaseModelToUi(tdsGroupSetup));
		});
		/*
		 * for (TdsGroupSetup tdsGroupSetup : dsGroupSetupList) {
		 * tdsGroupSetupDTO.add(tdsSectionbDatabaseModelToUi(tdsGroupSetup)); }
		 */
		return tdsGroupSetupDTO;
	}

	private TdsGroupSetupDTO tdsSetupDatabaseModelToUi(TdsGroupSetup tdsGroupSetup) {
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
		// tdsGroupSetupDTO.setIsSubGroupLimit(tdsGroupSetup.getIsSubGroupLimit());
		tdsGroupSetupDTO.setTdsSectionSetupDTO(tdsSectionModelToUiDtoList(tdsGroupSetup.getTdsSectionSetups()));
		return tdsGroupSetupDTO;

	}

	public List<TdsGroupSetup> tdsSetupConversion(List<TdsGroupSetup> tdsGroupSetupList, Long financialYearId) {
		List<TdsGroupSetup> TdsGroupSetup = new ArrayList<TdsGroupSetup>();
		List<TdsSectionSetup> TdsSectionSetup = new ArrayList<TdsSectionSetup>();

		for (TdsGroupSetup tdsGroupSetup : tdsGroupSetupList) {
			TdsGroupSetup setup = new TdsGroupSetup();

			FinancialYear financialYear = new FinancialYear();
			TdsGroupMaster tdsGroupMaster = new TdsGroupMaster();
			financialYear.setFinancialYearId(financialYearId);

			tdsGroupMaster.setTdsGroupMasterId(tdsGroupSetup.getTdsGroupMaster().getTdsGroupMasterId());
			setup.setTdsGroupMaster(tdsGroupMaster);
			setup.setActiveStatus("AC");
			setup.setFinancialYear(financialYear);
			setup.setCompanyId(tdsGroupSetup.getCompanyId());
			setup.setUserId(tdsGroupSetup.getUserId());
			setup.setDateCreated(new Date());
			setup.setDateUpdate(new Date());
			setup.setTdsGroupId(null);
			 for (TdsSectionSetup section : tdsGroupSetup.getTdsSectionSetups()) {
				 TdsSectionSetup tdsSectionSetup=new TdsSectionSetup();
				 tdsSectionSetup.setActiveStatus("AC");
				 tdsSectionSetup.setDateCreated(section.getDateCreated());
				 tdsSectionSetup.setDateUpdate(section.getDateUpdate());
				 tdsSectionSetup.setMaxLimit(section.getMaxLimit());
				 tdsSectionSetup.setTdsSectionName(section.getTdsSectionName());
				 tdsSectionSetup.setUserId(section.getUserId());
				 tdsSectionSetup.setUserIdUpdate(section.getUserIdUpdate());
//				 setup.setTdsGroupId(setup.getTdsGroupId());
				  tdsSectionSetup.setTdsGroupSetup(setup);
				 TdsSectionSetup.add(tdsSectionSetup);
				}
			 setup.setTdsSectionSetups(TdsSectionSetup);
			TdsGroupSetup.add(setup);
		

		}
		return TdsGroupSetup;
	}

	@Override
	public List<TdsGroupSetup> uiDtoToDatabaseModelList(List<TdsGroupSetupDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

//	public List<TdsSectionSetup> tdsSetupConversion
//	(List<TdsGroupSetup> tdsGroupSetupList, Long financialYearId) {
//		
//	}
	
	
}
