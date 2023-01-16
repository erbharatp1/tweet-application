package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.TdsGroupMaster;
import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.model.payroll.TdsSectionSetup;
import com.csipl.hrms.service.payroll.repository.FinancialYearRepository;
import com.csipl.hrms.service.payroll.repository.TdsGroupMasterRepository;
import com.csipl.hrms.service.payroll.repository.TdsGroupSetupRepository;
import com.csipl.hrms.service.payroll.repository.TdsSectionSetupRepository;

@Service
public class TdsSectionSetupServiceImpl implements TdsSectionSetupService {

	@Autowired
	private TdsSectionSetupRepository tdsSectionSetupRepository;

	@Autowired
	private TdsGroupSetupRepository tdsGroupSetupRepository;

	@Autowired
	private TdsGroupMasterRepository tdsGroupMasterRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;

	/*
	 * @Override public List<TdsSectionSetup> getAllTdsSection(Long tdsGroupId) { //
	 * TODO Auto-generated method stub return
	 * tdsSectionSetupRepository.findAllTdsSections(tdsGroupId); }
	 */
	@Override
	public List<TdsSectionSetup> saveTdsSection(List<TdsSectionSetup> tdsSectionSetupList) {
		List<TdsSectionSetup> tdsSectionList = (List<TdsSectionSetup>) tdsSectionSetupRepository
				.save(tdsSectionSetupList);
		return tdsSectionList;
	}

	@Override
	public void deleteById(Long tdsSectionId) {
		// TODO Auto-generated method stub
		tdsSectionSetupRepository.delete(tdsSectionId);
	}

	@Override
	public TdsGroupSetup saveTdsGroup(TdsGroupSetup tdsGroupSetup) {
		// TODO Auto-generated method stub
		return tdsGroupSetupRepository.save(tdsGroupSetup);
	}



	@Override
	@Transactional
	public void updateById(TdsGroupSetup tdsGroupSetup) {
		// TODO Auto-generated method stub
		tdsGroupSetupRepository.updateById(tdsGroupSetup.getTdsGroupId(), tdsGroupSetup.getMaxLimit());
	}

	@Override
	public List<TdsSectionSetup> getAllTdsSection(Long tdsGroupId) {
		return tdsSectionSetupRepository.findAllTdsSections(tdsGroupId);
	}

	@Override
	@Transactional
	public void updateByStatus(TdsSectionSetup tdsSectionSetup) {
		// TODO Auto-generated method stub
		tdsSectionSetupRepository.updateByStatus(tdsSectionSetup.getTdsSectionId(), tdsSectionSetup.getActiveStatus());
	}

	

	@Override
	public TdsGroupMaster getAllTdsGroupSetup(String tdsGroupName, Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TdsGroupSetup> saveTdsGroupSetup(List<TdsGroupSetup> tdsSectionSetupList) {
		List<TdsGroupSetup> tdsSectionList = (List<TdsGroupSetup>) tdsGroupSetupRepository.save(tdsSectionSetupList);
		return tdsSectionList;
	}

	/*
	 * @Override public TdsGroupSetup findByFinanicalYear(Long companyId) {
	 * 
	 * return tdsGroupSetupRepository.findByFinanicalYear(companyId); }
	 */

	@Override
	public List<TdsGroupSetup> findByFinanicalYear(Long companyId, Long financialYearId) {
		// TODO Auto-generated method stub
		return tdsGroupSetupRepository.findByFinanicalYearId(companyId, financialYearId);
	}

	@Override
	public TdsGroupSetup findByFinanicalYear(Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TdsGroupSetup getByGroupId(Long companyId, Long tdsGroupMasterId) {
		// TODO Auto-generated method stub
		return tdsGroupSetupRepository.findGroupSetup(companyId, tdsGroupMasterId);
	}
//	@Override
//	public TdsGroupSetup getByGroupId(Long companyId, String tdsGroupName) {
//		// TODO Auto-generated method stub
//		return tdsGroupSetupRepository.findGroupSetup(companyId, tdsGroupName);
//	}
	
}
