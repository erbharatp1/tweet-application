package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.TdsSlabDTO;
import com.csipl.hrms.dto.payroll.TdsSlabHdDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.common.Groupg;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.model.payroll.TdsSlabMaster;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public class TdsSlabAdaptor implements Adaptor<TdsSlabHdDTO, TdsSlabHd> {

	@Override
	public List<TdsSlabHd> uiDtoToDatabaseModelList(List<TdsSlabHdDTO> tdsSlabHdDtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * databaseModelToUiDtoList
	 */
	@Override
	public List<TdsSlabHdDTO> databaseModelToUiDtoList(List<TdsSlabHd> tdsSlabHdList) {
		List<TdsSlabHdDTO> tdsSlabHdDtoList = new ArrayList<TdsSlabHdDTO>();
		for (TdsSlabHd tdsSlabHd : tdsSlabHdList) {
			tdsSlabHdDtoList.add(databaseModelToUiDto(tdsSlabHd));
		}
		return tdsSlabHdDtoList;
	}

	@Override
	public TdsSlabHd uiDtoToDatabaseModel(TdsSlabHdDTO tdsSlabHdDto) {
		TdsSlabHd tdsSlabHd = new TdsSlabHd();
		tdsSlabHd.setDateEffective(tdsSlabHdDto.getDateEffective());
		// tdsSlabHd.setTdsCategory(tdsSlabHdDto.getTdsCategory());
		// tdsSlabHd.setTdsSLabHdId(tdsSlabHdDto.getTdsSLabHdId());
		// tdsSlabHd.setUserId(tdsSlabHdDto.getUserId());
		tdsSlabHd.setDateCreated(tdsSlabHdDto.getDateCreated());
		tdsSlabHd.setTdsSlabs(uiDtoToDatabaseModelListTds(tdsSlabHdDto.getTdsSlabs(), tdsSlabHd));
		Company company = new Company();
		company.setCompanyId(tdsSlabHdDto.getCompanyId());
		tdsSlabHd.setCompany(company);
//		Groupg groupg = new Groupg();
//		groupg.setGroupId(1l);
		// tdsSlabHd.setGroupg(groupg);
		if (tdsSlabHdDto.getDateCreated() == null)
			tdsSlabHd.setDateCreated(new Date());
		else
			tdsSlabHd.setDateCreated(tdsSlabHdDto.getDateCreated());
		tdsSlabHd.setDateUpdate(new Date());
		// tdsSlabHd.setUserIdUpdate(tdsSlabHdDto.getUserIdUpdate());
		return tdsSlabHd;
	}

	/**
	 * 
	 * databaseModelToUiDto}
	 */
	public List<TdsSlabDTO> databaseModelToUiDto(List<TdsSlab> tdsSlabListHd) {
		List<TdsSlabDTO> TdsSlabDtoList = new ArrayList<TdsSlabDTO>();
		for (TdsSlab tdsSlab : tdsSlabListHd) {
			TdsSlabDtoList.add(databaseModelToUiDto(tdsSlab));
		}
		return TdsSlabDtoList;
	}

	/**
	 * 
	 * uiDtoToDatabaseModel}
	 */
	public TdsSlab uiDtoToDatabaseModel(TdsSlabDTO tdsSlabDto, TdsSlabHd tdsSlabHd) {
		TdsSlab tdsSlab = new TdsSlab();
		tdsSlab.setLimitFrom(tdsSlabDto.getLimitFrom());
		tdsSlab.setLimitTo(tdsSlabDto.getLimitTo());
		tdsSlab.setTdsSLabId(tdsSlabDto.getTdsSLabId());
		tdsSlab.setTdsPercentage(tdsSlabDto.getTdsPercentage());
		tdsSlab.setUserId(tdsSlabDto.getUserId());
		tdsSlab.setDateCreated(tdsSlabDto.getDateCreated());
		tdsSlab.setTdsSlabHd(tdsSlabHd);
		tdsSlab.setTdsSlabPlanType(tdsSlabDto.getTdsSlabPlanType());
		tdsSlab.setUserId(tdsSlabDto.getUserId());
		if (tdsSlabDto.getDateCreated() == null)
			tdsSlab.setDateCreated(new Date());
		else
			tdsSlab.setDateCreated(tdsSlabDto.getDateCreated());
		tdsSlab.setDateUpdate(new Date());
		tdsSlab.setUserIdUpdate(tdsSlabDto.getUserId());
		return tdsSlab;
	}

	/**
	 * 
	 * databaseModelToUiDto}
	 */
	public TdsSlabDTO databaseModelToUiDto(TdsSlab tdsSlab) {
		TdsSlabDTO tdsSlabDto = new TdsSlabDTO();
		tdsSlabDto.setLimitFrom(tdsSlab.getLimitFrom());
		tdsSlabDto.setActiveStatus(tdsSlab.getActiveStatus());
		tdsSlabDto.setLimitTo(tdsSlab.getLimitTo());
		tdsSlabDto.setTdsPercentage(tdsSlab.getTdsPercentage());
		tdsSlabDto.setTdsSLabId(tdsSlab.getTdsSLabId());
		tdsSlabDto.setTdsSlabPlanType(tdsSlab.getTdsSlabPlanType());
		tdsSlabDto.setUserId(tdsSlab.getUserId());
		tdsSlabDto.setDateCreated(tdsSlab.getDateCreated());
		return tdsSlabDto;
	}

	/**
	 * databaseModelToUiDto
	 */
	@Override
	public TdsSlabHdDTO databaseModelToUiDto(TdsSlabHd tdsSlabHd) {
		TdsSlabHdDTO tdsSlabHdDto = new TdsSlabHdDTO();
		tdsSlabHdDto.setDateEffective(tdsSlabHd.getDateEffective());
		// tdsSlabHdDto.setTdsCategory(tdsSlabHd.getTdsCategory());
		// tdsSlabHdDto.setTdsCategoryValue(DropDownCache.getInstance().getDropDownValue(
		// DropDownEnum.Category.getDropDownName() , tdsSlabHd.getTdsCategory() ));
		// tdsSlabHdDto.setTdsSLabHdId(tdsSlabHd.getTdsSLabHdId());
		// tdsSlabHdDto.setUserId(tdsSlabHd.getUserId());
		tdsSlabHdDto.setDateCreated(tdsSlabHd.getDateCreated());
		if (tdsSlabHd.getTdsSlabs() != null)
			tdsSlabHdDto.setTdsSlabs(databaseModelToUiDto(tdsSlabHd.getTdsSlabs()));
		return tdsSlabHdDto;
	}

	/**
	 * 
	 * uiDtoToDatabaseModelListTds}
	 */
	public List<TdsSlab> uiDtoToDatabaseModelListTds(List<TdsSlabDTO> tdsSlabDtoList, TdsSlabHd tdsSlabHd) {
		List<TdsSlab> tdsSlabList = new ArrayList<TdsSlab>();
		tdsSlabDtoList.forEach(tdsSlabDto -> {
			tdsSlabList.add(uiDtoToDatabaseModel(tdsSlabDto, tdsSlabHd));
		});

		return tdsSlabList;
	}

	/**
	 * 
	 * tdsSlabUitoDatabaseModel}
	 */
	public TdsSlab tdsSlabUitoDatabaseModel(TdsSlabDTO tdsSlabDto) {
		TdsSlab tdsSlab = new TdsSlab();
		TdsSlabHd tdsSlabHd = new TdsSlabHd();
		tdsSlabHd.setTdsSLabHdId(tdsSlabDto.getTdsSLabHdId());
		tdsSlab.setLimitFrom(tdsSlabDto.getLimitFrom());
		tdsSlab.setLimitTo(tdsSlabDto.getLimitTo());
		tdsSlab.setActiveStatus(tdsSlabDto.getActiveStatus());
		tdsSlab.setTdsSLabId(tdsSlabDto.getTdsSLabId());
		tdsSlab.setTdsSlabPlanType(tdsSlabDto.getTdsSlabPlanType());
		tdsSlab.setTdsPercentage(tdsSlabDto.getTdsPercentage());
		tdsSlab.setUserId(tdsSlabDto.getUserId());
		tdsSlab.setDateCreated(tdsSlabDto.getDateCreated());
		tdsSlab.setTdsSlabHd(tdsSlabHd);
		if (tdsSlabDto.getDateCreated() == null)
			tdsSlab.setDateCreated(new Date());
		else
			tdsSlab.setDateCreated(tdsSlabDto.getDateCreated());
		tdsSlab.setDateUpdate(new Date());
		tdsSlab.setUserIdUpdate(tdsSlabDto.getUserId());

		return tdsSlab;
	}

	/**
	 * 
	 * tdsSlabuiDtoToDatabaseModelListTds}
	 */
	public List<TdsSlab> tdsSlabuiDtoToDatabaseModelListTds(List<TdsSlabDTO> tdsSlabDtoList) {
		List<TdsSlab> tdsSlabList = new ArrayList<TdsSlab>();
		tdsSlabDtoList.forEach(tdsSlabDTO -> {
			tdsSlabList.add(tdsSlabUitoDatabaseModel(tdsSlabDTO));
		});

		return tdsSlabList;
	}

	/**
	 * 
	 * tdsSlabdatabaseModelToUiDtoList}
	 */
	public List<TdsSlabDTO> tdsSlabdatabaseModelToUiDtoList(List<TdsSlab> findAllTdsSlabByCategory) {

		List<TdsSlabDTO> tdsSlabDtoList = new ArrayList<TdsSlabDTO>();
		findAllTdsSlabByCategory.forEach(tdsSlab -> {
			tdsSlabDtoList.add(tdsSlabDatabaseModelToUi(tdsSlab));
		});
		return tdsSlabDtoList;
	}

	/**
	 * 
	 * tdsSlabDatabaseModelToUi}
	 */
	private TdsSlabDTO tdsSlabDatabaseModelToUi(TdsSlab tdsSlab) {
		TdsSlabDTO tdsSlabDTO = new TdsSlabDTO();
		tdsSlabDTO.setLimitFrom(tdsSlab.getLimitFrom());
		tdsSlabDTO.setLimitTo(tdsSlab.getLimitTo());
		tdsSlabDTO.setActiveStatus(tdsSlab.getActiveStatus());
		tdsSlabDTO.setTdsPercentage(tdsSlab.getTdsPercentage());
		tdsSlabDTO.setUserId(tdsSlab.getUserId());
		tdsSlabDTO.setDateCreated(tdsSlab.getDateCreated());
		tdsSlabDTO.setUserIdUpdate(tdsSlab.getUserIdUpdate());
		tdsSlabDTO.setTdsSlabPlanType(tdsSlab.getTdsSlabPlanType());
		// dsSlabDTO.setTdsSLabHdId(tdsSlab.getTdsSlabHd().getTdsSLabHdId());
		tdsSlabDTO.setTdsSLabId(tdsSlab.getTdsSLabId());
		tdsSlabDTO.setUserId(tdsSlab.getUserId());
		return tdsSlabDTO;
	}

	/**
	 * tdsSlabHddatabaseModelToUiDtoList}
	 */
	public List<TdsSlabHdDTO> tdsSlabHddatabaseModelToUiDtoList(List<TdsSlabHd> tdsHdList) {
		List<TdsSlabHdDTO> tdsSlabHdDtoList = new ArrayList<TdsSlabHdDTO>();
		tdsHdList.forEach(tdsSlabHd -> {
			tdsSlabHdDtoList.add(tdsSlabHdDatabaseModelToUi(tdsSlabHd));
		});
		return tdsSlabHdDtoList;
	}

	/**
	 * tdsSlabHdDatabaseModelToUi}
	 */
	private TdsSlabHdDTO tdsSlabHdDatabaseModelToUi(TdsSlabHd tdsSlabHd) {
		TdsSlabHdDTO tdsSlabHdDTO = new TdsSlabHdDTO();
		tdsSlabHdDTO.setTdsSLabHdId(tdsSlabHd.getTdsSLabHdId());
		tdsSlabHdDTO.setAllowModi(tdsSlabHd.getAllowModi());
		tdsSlabHdDTO.setActiveStatus(tdsSlabHd.getActiveStatus());
		tdsSlabHdDTO.setCompanyId(tdsSlabHd.getCompany().getCompanyId());
		tdsSlabHdDTO.setFinencialYearId(tdsSlabHd.getFinancialYear().getFinancialYearId());
		tdsSlabHdDTO.setUserId(tdsSlabHd.getUserId());
		tdsSlabHdDTO.setTdsSlabMasterId(tdsSlabHd.getTdsSlabMaster().getTdsSlabMasterId());
		tdsSlabHdDTO.setDateCreated(tdsSlabHd.getDateCreated());
		tdsSlabHdDTO.setUserIdUpdate(tdsSlabHd.getUserIdUpdate());
		tdsSlabHdDTO.setGroupId(tdsSlabHd.getGroupId());
		tdsSlabHdDTO.setTdsSlabs(databaseModelToUiDto(tdsSlabHd.getTdsSlabs()));
		return tdsSlabHdDTO;
	}

	/**
	 * tdsSlabHdDtoList}
	 */
	public List<TdsSlabHd> tdsSlabHdDtoList(List<TdsSlabHd> tdsSlabHd, Long finencialYearId) {
		List<TdsSlabHd> tdsSlabHdList = new ArrayList<TdsSlabHd>();
		List<TdsSlab> tdsSlabList = new ArrayList<TdsSlab>();
		for (TdsSlabHd tdsSlabHD : tdsSlabHd) {
			TdsSlabHd tdSlabHd = new TdsSlabHd();
			TdsSlabMaster tdsSlabMaster = new TdsSlabMaster();
			tdsSlabMaster.setTdsSlabMasterId(tdsSlabHD.getTdsSlabMaster().getTdsSlabMasterId());

			Company company = new Company();
			company.setCompanyId(tdsSlabHD.getCompany().getCompanyId());

			FinancialYear financialYear = new FinancialYear();
			financialYear.setFinancialYearId(tdsSlabHD.getFinancialYear().getFinancialYearId());

			tdSlabHd.setActiveStatus("AC");
			tdSlabHd.setDateCreated(tdsSlabHD.getDateCreated());
			tdSlabHd.setUserId(tdsSlabHD.getUserId());

			tdSlabHd.setCompany(company);
			tdSlabHd.setEffectiveEndDate(tdsSlabHD.getEffectiveEndDate());
			tdSlabHd.setEffectiveStartDate(tdsSlabHD.getEffectiveStartDate());
			tdSlabHd.setFinancialYear(financialYear);
			tdSlabHd.setTdsSlabMaster(tdsSlabMaster);
			tdSlabHd.setTdsSLabHdId(null);
			tdSlabHd.setGroupId(tdsSlabHD.getGroupId());

			for (TdsSlab tdsSlab2 : tdsSlabHD.getTdsSlabs()) {
				TdsSlab tdsSlabnew = new TdsSlab();

				tdsSlabnew.setActiveStatus("AC");
				tdsSlabnew.setLimitFrom(tdsSlab2.getLimitFrom());
				tdsSlabnew.setUserId(tdsSlab2.getUserId());
				tdsSlabnew.setDateCreated(tdsSlab2.getDateCreated());
				tdsSlabnew.setTdsPercentage(tdsSlab2.getTdsPercentage());
				tdsSlabnew.setLimitTo(tdsSlab2.getLimitTo());
				tdsSlabnew.setTdsSlabHd(tdsSlabHD);
				tdsSlabnew.setTdsSlabPlanType(tdsSlab2.getTdsSlabPlanType());
				tdsSlabnew.setUserIdUpdate(tdsSlab2.getUserIdUpdate());
				tdsSlabnew.setAllowModi(tdsSlab2.getAllowModi());
				tdsSlabList.add(tdsSlabnew);
			}
			tdSlabHd.setTdsSlabs(tdsSlabList);
			tdsSlabHdList.add(tdSlabHd);

		}

		return tdsSlabHdList;

	}

	/**
	 * 
	 * tdsSlabHDdatabaseModelToUiDtoList}
	 */
	public List<TdsSlabHd> tdsSlabHDdatabaseModelToUiDtoList(List<TdsSlabHd> tdsHdList, Long finencialYearId) {
		List<TdsSlabHd> tdsSlabHdList = new ArrayList<TdsSlabHd>();
		tdsHdList.forEach(tdsSlabHd -> {
			tdsSlabHdList.add(tdsSlabHDDatabaseModelToUi(tdsSlabHd));
		});
		return tdsSlabHdList;
	}

	/**
	 * tdsSlabHdDatabaseModelToUi}
	 */
	private TdsSlabHd tdsSlabHDDatabaseModelToUi(TdsSlabHd tdsSlab) {
		TdsSlabHd tdsSlabHd = new TdsSlabHd();
		Company company = new Company();
		company.setCompanyId(tdsSlab.getCompany().getCompanyId());
		TdsSlabMaster tdsSlabMaster = new TdsSlabMaster();
		tdsSlabMaster.setTdsSlabMasterId(tdsSlab.getTdsSlabMaster().getTdsSlabMasterId());
		FinancialYear financialYear = new FinancialYear();
		financialYear.setFinancialYearId(tdsSlab.getFinancialYear().getFinancialYearId());

		tdsSlabHd.setActiveStatus("AC");
		tdsSlabHd.setDateCreated(tdsSlab.getDateCreated());
		tdsSlabHd.setUserId(tdsSlab.getUserId());

		tdsSlabHd.setCompany(company);
		tdsSlabHd.setEffectiveEndDate(tdsSlab.getEffectiveEndDate());
		tdsSlabHd.setEffectiveStartDate(tdsSlab.getEffectiveStartDate());
		tdsSlabHd.setFinancialYear(financialYear);
		tdsSlabHd.setTdsSlabMaster(tdsSlabMaster);
		tdsSlabHd.setTdsSLabHdId(null);
		tdsSlabHd.setGroupId(tdsSlab.getGroupId());
		tdsSlabHd.setTdsSlabs(tdsDatabaseModelToUiDto(tdsSlab.getTdsSlabs()));
		return tdsSlabHd;
	}

	public List<TdsSlab> tdsDatabaseModelToUiDto(List<TdsSlab> tdsSlabListHd) {
		List<TdsSlab> TdsSlabDtoList = new ArrayList<TdsSlab>();
		for (TdsSlab tdsSlab : tdsSlabListHd) {
			TdsSlabDtoList.add(tdsDatabaseModelToUiDto(tdsSlab));
		}
		return TdsSlabDtoList;
	}

	/**
	 * 
	 * tdsDatabaseModelToUiDto}
	 */
	public TdsSlab tdsDatabaseModelToUiDto(TdsSlab tdsSlab) {
		TdsSlab tdsSlabnew = new TdsSlab();
		TdsSlabHd tdsSlabHd = new TdsSlabHd();
		tdsSlabHd.setTdsSLabHdId(tdsSlab.getTdsSlabHd().getTdsSLabHdId());
		tdsSlabnew.setTdsSlabHd(tdsSlabHd);
		tdsSlabnew.setActiveStatus("AC");
		tdsSlabnew.setLimitFrom(tdsSlab.getLimitFrom());
		tdsSlabnew.setUserId(tdsSlab.getUserId());
		tdsSlabnew.setDateCreated(tdsSlab.getDateCreated());
		tdsSlabnew.setTdsPercentage(tdsSlab.getTdsPercentage());
		tdsSlabnew.setLimitTo(tdsSlab.getLimitTo());
		tdsSlabnew.setUserIdUpdate(tdsSlab.getUserIdUpdate());
		tdsSlabnew.setAllowModi(tdsSlab.getAllowModi());
		return tdsSlabnew;
	}

	/**
	 * 
	 * uislabHdDtoToDatabaseModelList
	 */

	public List<TdsSlabHd> uislabHdDtoToDatabaseModelList(List<TdsSlabHdDTO> tdsSlabHdDTO, Long financialYearId) {
		List<TdsSlabHd> tdsSlabHdList = new ArrayList<TdsSlabHd>();
		tdsSlabHdDTO.forEach(slab -> {
			tdsSlabHdList.add(slabUiToDatabaseModel(slab, financialYearId));
		});

		return tdsSlabHdList;
	}

	/**
	 * 
	 * slabUiToDatabaseModel}
	 */
	private TdsSlabHd slabUiToDatabaseModel(TdsSlabHdDTO tdsSlabHdDTO, Long financialYearId) {
		TdsSlabHd tdsSlabHd = new TdsSlabHd();
		TdsSlabMaster tdsSlabMaster = new TdsSlabMaster();

		tdsSlabMaster.setTdsSlabMasterId(tdsSlabHdDTO.getTdsSlabMasterId());
		tdsSlabHd.setTdsSlabMaster(tdsSlabMaster);
		tdsSlabHd.setActiveStatus("AC");
		FinancialYear financialYear = new FinancialYear();
		Company company = new Company();
		company.setCompanyId(tdsSlabHdDTO.getCompanyId());
		financialYear.setFinancialYearId(financialYearId);
		tdsSlabHd.setFinancialYear(financialYear);
		tdsSlabHd.setCompany(company);
		tdsSlabHd.setUserId(tdsSlabHdDTO.getUserId());
		tdsSlabHd.setDateCreated(new Date());
		tdsSlabHd.setDateUpdate(new Date());
		tdsSlabHd.setEffectiveEndDate(tdsSlabHdDTO.getEffectiveEndDate());
		tdsSlabHd.setEffectiveStartDate(tdsSlabHdDTO.getEffectiveStartDate());
		tdsSlabHd.setGroupId(tdsSlabHdDTO.getGroupId());
		tdsSlabHd.setUserId(tdsSlabHdDTO.getUserId());
		tdsSlabHd.setDateEffective(tdsSlabHdDTO.getDateEffective());
		tdsSlabHd.setUserIdUpdate(tdsSlabHdDTO.getUserIdUpdate());
		tdsSlabHd.setTdsSlabs(uiDtoToSlabList(tdsSlabHdDTO.getTdsSlabs(), tdsSlabHd));

		return tdsSlabHd;
	}

	/**
	 * 
	 * uiDtoToSlabList}
	 */
	private List<TdsSlab> uiDtoToSlabList(List<TdsSlabDTO> tdsSlabDTOList, TdsSlabHd tdsGroupSetup) {

		List<TdsSlab> tdsSectionSetupList = new ArrayList<TdsSlab>();
		tdsSlabDTOList.forEach(tdsSectionSetupDTO -> {
			tdsSectionSetupList.add(uiDtoToSectionModel(tdsSectionSetupDTO, tdsGroupSetup));
		});
		return tdsSectionSetupList;
	}

	/**
	 * 
	 * uiDtoToSectionModel}
	 */

	private TdsSlab uiDtoToSectionModel(TdsSlabDTO tdsSlabDTO, TdsSlabHd tdsSlabHd) {
		// TODO Auto-generated method stub
		TdsSlab tdsSlab = new TdsSlab();
		tdsSlab.setActiveStatus("AC");
		tdsSlab.setDateCreated(tdsSlabDTO.getDateCreated());
		tdsSlab.setLimitFrom(tdsSlabDTO.getLimitFrom());
		tdsSlab.setLimitTo(tdsSlabDTO.getLimitTo());
		tdsSlab.setTdsPercentage(tdsSlabDTO.getTdsPercentage());
		tdsSlab.setUserId(tdsSlabDTO.getUserId());
		tdsSlab.setUserIdUpdate(tdsSlabDTO.getUserIdUpdate());
		tdsSlab.setTdsSlabPlanType(tdsSlabDTO.getTdsSlabPlanType());
		// tdsSlab.setTdsSlabHd(tdsSlabHd.getTdsSLabHdId());
		tdsSlabHd.setTdsSLabHdId(tdsSlabDTO.getTdsSLabHdId());
		tdsSlab.setTdsSlabHd(tdsSlabHd);

		return tdsSlab;
	}

}
