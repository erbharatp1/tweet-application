package com.csipl.hrms.service.employee;

import org.springframework.stereotype.Component;

import com.csipl.hrms.model.employee.LetterDaclaration;

/**
 * 
 * @author Bharat
 *
 */
@Component
public interface LetterDaclarationService {

	LetterDaclaration findLetterDaclarationById(Long letterId);

	void save(LetterDaclaration questionTamplates);

}
