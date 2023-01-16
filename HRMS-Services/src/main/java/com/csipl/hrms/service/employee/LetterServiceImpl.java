package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.service.employee.repository.LetterRepository;

@Service("letterService")
@Transactional
public class LetterServiceImpl implements LetterService {
	@Autowired
	private LetterRepository letterRepository;

	@Override
	public Letter saveLtr(Letter ltr) {

		return letterRepository.save(ltr);
	}

	@Override
	public List<Letter> findAllLetter(Long companyId) {
		// TODO Auto-generated method stub
		return letterRepository.findLetterAll(companyId);
	}

	@Override
	public Letter findLetter(Long letterId) {

		return letterRepository.findLetter(letterId);
	}

	@Override
	public Letter findLetterByType(String letterType) {
		// TODO Auto-generated method stub
		return letterRepository.findLetterByType(letterType);
	}

	@Override
	public void deleteLetter(Long letterId) {
		  letterRepository.delete(letterId);
		
	}

	@Override
	public List<Letter> findLetterByType(Long companyId) {
		// TODO Auto-generated method stub
		return letterRepository.findLetterByType(companyId);
	}

	@Override
	public List<Letter> findLetterByTypeList(String letterType) {
		return letterRepository.findLetterByTypeList(letterType);
	}

	@Override
	public Letter findLetterByEnableGrade(String letterType, String enableGrade) {
	
		return letterRepository.findLetterByEnableGrade(letterType,enableGrade);
	}

}
