package com.csipl.hrms.service.payroll;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csipl.hrms.model.payroll.TdsSlab;
import com.csipl.hrms.model.payroll.TdsSlabHd;
import com.csipl.hrms.service.payroll.repository.TdsSlabRepository;
import com.csipl.hrms.service.payroll.repository.TdsSlabesRepository;

@Service("tdsSlabService")
public class TdsSlabServiceImpl implements TdsSlabService {

	@Autowired
	private TdsSlabRepository tdsSlabRepository;
	
	@Autowired
	private TdsSlabesRepository tdsSlabesRepository;
	/**
	 * Save OR update tdsSlabHd data into database
	 */
	@Override
	public TdsSlabHd save(TdsSlabHd tdsSlabHd) {
  		return tdsSlabRepository.save(tdsSlabHd);
	}
	/**
	 * to get List of TdsSlabHd objects from database based on companyId
	 */
	@Override
	public List<TdsSlabHd> getAllTdsSlabHd(Long companyId) {
 		return tdsSlabRepository.findAllTdsSlabList(companyId);
	}
	/**
	 * to get TdsSlabHd object from database based on tdsSlabHdId(Primary Key)
	 */

	@Override
	public TdsSlabHd getTdsSlabHd(Long tdsSlabHdId) {
		return tdsSlabRepository.findOne(tdsSlabHdId);
 	}
	@Override
	@Transactional
	public List<TdsSlab> saveTdsSlab(List<TdsSlab> tdsSlab) {
		List<TdsSlab> tdsSlabList = (List<TdsSlab>) tdsSlabesRepository.save(tdsSlab);
		return tdsSlabList;
	}
//	@Override
//	public List<TdsSlab> findAllTdsSlabHd(Long tdsSLabHdId) {
//		
//		return tdsSlabesRepository.findAllTdsSlabById(tdsSLabHdId);
//	}
	@Override
	public void deleteById(Long tdsSLabId) {
		tdsSlabesRepository.delete(tdsSLabId);
		
	}
	@Override
	@Transactional
	public void updateByStatus(TdsSlab tdsSlab) {
		// TODO Auto-generated method stub
		tdsSlabesRepository.updateByStatus(tdsSlab.getTdsSLabId(), tdsSlab.getActiveStatus());
	}
	@Override
	public List<TdsSlab> findAllTdsSlabHd(Long tdsSLabHdId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TdsSlab> findAllTdsSlabHdById(Long tdsSLabHdId, String planType) {
		// TODO Auto-generated method stub
		return tdsSlabesRepository.findAllTdsSlabById(tdsSLabHdId, planType);
	}




  }
