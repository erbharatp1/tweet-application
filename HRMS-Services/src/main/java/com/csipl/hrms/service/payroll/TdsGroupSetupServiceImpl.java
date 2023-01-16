package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.TdsGroupSetup;
import com.csipl.hrms.service.payroll.repository.TdsGroupSetupRepository;


@Service
public class TdsGroupSetupServiceImpl implements TdsGroupSetupService {

	@Autowired
	private TdsGroupSetupRepository tdsGroupSetupRepository;
	
	@Override
	public List<TdsGroupSetup> findByCompanyId(Long companyId) {
		// TODO Auto-generated method stub
		return tdsGroupSetupRepository.findByCompanyId(companyId);
	}

	@Override
	@Transactional
	public List<TdsGroupSetup> saveTdsGroup(List<TdsGroupSetup> tdsGroupSetupList) {
		List<TdsGroupSetup> TdsGroupSetup= (List<com.csipl.hrms.model.payroll.TdsGroupSetup>) tdsGroupSetupRepository.save(tdsGroupSetupList);

		return TdsGroupSetup;
	}

//	@Override
//	@Transactional
//	public List<TdsGroupSetup> saveTdsGroup(List<TdsGroupSetup> tdsGroupSetupList) {
//		List<TdsGroupSetup> TdsGroupSetup= (List<com.csipl.hrms.model.payroll.TdsGroupSetup>) tdsGroupSetupRepository.save(tdsGroupSetupList);
//		// TODO Auto-generated method stub
//		return TdsGroupSetup;
//	}

	





	

	

	
}
