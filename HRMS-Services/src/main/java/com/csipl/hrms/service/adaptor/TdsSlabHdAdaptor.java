package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.payroll.TdsSlabHdDTO;
import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.model.payroll.TdsSlabMaster;
import com.csipl.hrms.model.payrollprocess.FinancialYear;

public class TdsSlabHdAdaptor implements Adaptor<TdsSlabHdDTO, TdsSlabHd> {

	@Override
	public List<TdsSlabHd> uiDtoToDatabaseModelList(List<TdsSlabHdDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsSlabHdDTO> databaseModelToUiDtoList(List<TdsSlabHd> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsSlabHd uiDtoToDatabaseModel(TdsSlabHdDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsSlabHdDTO databaseModelToUiDto(TdsSlabHd dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * tdsMasterToTdsSlabHdConversion}
	 */
	public List<TdsSlabHd> tdsMasterToTdsSlabHdConversion(List<TdsSlabMaster> tdsSlabMasterList, Long finencialYearId) {
		List<TdsSlabHd>  tdsSlabHdList = new ArrayList<TdsSlabHd> ();
		tdsSlabMasterList.forEach(tdsMaster->{
			tdsSlabHdList.add(tdsHdToDataBaseModel(tdsMaster, finencialYearId ));
		});
		return tdsSlabHdList;
	}

	/**
	 * tdsHdToDataBaseModel}
	 */
	private TdsSlabHd tdsHdToDataBaseModel(TdsSlabMaster tdsMaster, Long finencialYearId) {
		TdsSlabHd tdsSlabHd= new TdsSlabHd();

		tdsSlabHd.setActiveStatus("AC");
		Company company = new Company();
		company.setCompanyId(tdsMaster.getCompanyId());
		tdsSlabHd.setCompany(company);
		tdsSlabHd.setGroupId(1L);
		FinancialYear financialYear= new FinancialYear();
		financialYear.setFinancialYearId(finencialYearId);
		tdsSlabHd.setFinancialYear(financialYear);
		tdsSlabHd.setTdsSlabMaster(tdsMaster);
		tdsSlabHd.setDateCreated(new Date());
//		tdsSlabHd.setTdsSlabs(tdsHdToTdsSlabConversion(tdsMaster.getTdsSlabHds()));
		tdsSlabHd.setUserId(tdsMaster.getUserId());
		return tdsSlabHd;
	}

	/**
	 * tdsHdToTdsSlabConversion}
	 */
	public List<TdsSlab> tdsHdToTdsSlabConversion(List<TdsSlabHd> tdsSlabHdList) {
		List<TdsSlab> tdsSlabList = new ArrayList<TdsSlab>();
		tdsSlabHdList.forEach(tdsHd->{
			tdsSlabList.add(tdsToDataBaseModel(tdsHd));
		});
		return tdsSlabList;
	}
/**
 * 
 * tdsToDataBaseModel}
 */
	private TdsSlab tdsToDataBaseModel(TdsSlabHd tdsSlabHd) {
		TdsSlab tdsSlab= new TdsSlab();
		tdsSlab.setActiveStatus("AC");
		tdsSlab.setUserIdUpdate(tdsSlabHd.getUserIdUpdate());
		tdsSlab.setTdsSlabHd(tdsSlabHd);
		tdsSlab.setDateCreated(new Date());
		tdsSlab.setUserId(tdsSlabHd.getUserId());
		return tdsSlab;
	}
	
}
