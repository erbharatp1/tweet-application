package com.csipl.hrms.service.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.LetterDaclaration;

@Repository("letterDaclarationRepository")
public interface LetterDaclarationRepository extends JpaRepository<LetterDaclaration, Long> {
	 
    @Query(value="FROM LetterDaclaration ld WHERE ld.letterId=?1")
	LetterDaclaration findLetterDaclarationById(Long letterId);
 

}
