package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.service.payroll.repository.TdsSlabHdRepository;

@Service("tdsSlabHdService")
public class TdsSlabHdServiceImpl implements TdsSlabHdService {
  @Autowired
	private TdsSlabHdRepository tdsSlabHdRepository;
	
	@Override
	public List<TdsSlabHd> getAllTdsSlabHd(Long companyId) {
		// TODO Auto-generated method stub
		return tdsSlabHdRepository.findAllTdsSlabHdList(companyId);
	}

	@Override
	public List<TdsSlabHd> findTdsSlabHdByFinencailYear(Long companyId, Long finencialYearId) {
		// TODO Auto-generated method stub
		return tdsSlabHdRepository.finddsSlab(companyId, finencialYearId);
	}

	@Override
	public List<TdsSlabHd> saveTdsSlabHd(List<TdsSlabHd> tdsSlabList) {
		List<TdsSlabHd> tdsSlabHdList =(List<TdsSlabHd>) tdsSlabHdRepository.save(tdsSlabList);
		return tdsSlabHdList;
	
	}

	

  }
