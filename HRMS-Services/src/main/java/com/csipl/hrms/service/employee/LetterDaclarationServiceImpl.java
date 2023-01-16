package com.csipl.hrms.service.employee;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.employee.LetterDaclaration;
import com.csipl.hrms.service.employee.repository.LetterDaclarationRepository;

/**
 * 
 * @author Bharat
 *
 */
@Service("letterDaclarationService")
@Transactional
public class LetterDaclarationServiceImpl implements LetterDaclarationService {
	@Autowired
	private LetterDaclarationRepository letterDaclarationRepository;

	@Override
	public LetterDaclaration findLetterDaclarationById(Long letterId) {
		 
		return letterDaclarationRepository.findLetterDaclarationById(letterId);
	}

	@Override
	public void save(LetterDaclaration letterDaclaration) {
		// TODO Auto-generated method stub
		letterDaclarationRepository.save(letterDaclaration);
	}
 
	 

}
