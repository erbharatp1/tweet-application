package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.Letter;

public interface LetterService {
  
	public Letter saveLtr(Letter ltr);

	public  List<Letter> findAllLetter(Long companyId);

	public Letter findLetter(Long letterId);
	
	public Letter findLetterByType(String letterType);

	public void deleteLetter(Long letterId);
	
	public List<Letter> findLetterByType(Long companyId);

	public List<Letter> findLetterByTypeList(String letterType);

	public Letter findLetterByEnableGrade(String appointmentLetterCode, String enableGrade);
	
}
