package com.csipl.hrms.service.payroll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.service.payroll.repository.PayHeadRepository;

@Service("payHeadService")
public class PayHeadServiceImpl implements PayHeadService {

	@Autowired
	private PayHeadRepository payheadRepository;

	/**
	 * to get List of PayHead objects from database based on companyId and earning
	 * OR deduction
	 */
	@Override
	public List<PayHead> getAllPayHeads(String opt, Long companyId) {
		return payheadRepository.findAllPayHead(opt, companyId);
	}

	/**
	 * Method Performed save OR update operation
	 * 
	 * @throws ErrorHandling
	 */
	@Override
	public PayHead save(PayHead payHead) throws ErrorHandling {
		return payheadRepository.save(payHead);
//
//		int count = payheadRepository.getPriority(payHead.getPriority());
//		if (count < 1) {
//			return payheadRepository.save(payHead);
//		} else {
//			throw new ErrorHandling(" priority already exists ");
//		}
	}

	/**
	 * to get List of PayHead objects from database based on companyId
	 */
	@Override
	public List<PayHead> findAllPayHeadOfCompany(long companyId) {

		return payheadRepository.findAllPayHeadOfCompany(companyId);
	}

	/**
	 * to get List of PayHead objects from database based on companyId
	 */
	@Override
	public List<PayHead> findActivePayHeadOfCompany(long companyId) {

		return payheadRepository.findActivePayHeadOfCompany(companyId);
	}

	
	/**
	 * to PayHead object from database based on payHeadId (Primary Key)
	 */
	@Override
	public PayHead findPayHeadById(long payHeadId) {
		return payheadRepository.findOne(payHeadId);
	}

	@Override
	public List<PayHead> findAllEarnigPaystructurePayHeads(Long companyId) {
		return payheadRepository.findAllEarnigPaystructurePayHeads(companyId);
	}


	@Override
	public List<PayHead> getAllOneTimePayHeads(String opt, Long companyId) {
		// TODO Auto-generated method stub
		 return payheadRepository.findAllOneTimePayHead(opt,companyId);
	}

	@Override
	public Map<String, Long> getSquenceId(Long companyId) {
		Map<String, Long> map = new HashMap<>();
		Long earing = payheadRepository.getEarningSquenceId(companyId);
		Long deduction = payheadRepository.getDeductionSquenceId(companyId);
		Long max = payheadRepository.getMaxSquenceId(companyId);
		map.put("earing", earing);
		map.put("deduction", deduction);
		map.put("max", max);
		return map;
	}
 }

