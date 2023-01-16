package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.Letter;

@Repository("letterRepository")
public interface LetterRepository extends CrudRepository<Letter, Long> {
public static final String APPOINTMENT_LETTER ="Appointment Letter";
	@Query(" from Letter where companyId=?1 ORDER BY  letterId  DESC")
	public List<Letter> findLetterAll(Long companyId);

	@Query(" from Letter where letterId=?1 ORDER BY  letterId  DESC")
	public Letter findLetter(Long letterId);

	@Query(" from Letter where letterType=?1 ORDER BY  letterId  DESC")
	public Letter findLetterByType(String letterType);

	@Query(nativeQuery = true,value = " select * from Letter ltr where ltr.companyId=?1 AND ltr.activeStatus='AC' AND ltr.letterType  IN(ltr.letterType='Appointment Letter') AND ltr.letterType  IN(ltr.letterType='Appraisal Letter') AND ltr.letterType  IN(ltr.letterType='Experience Letter')")
	public List<Letter> findLetterByType(Long companyId);

	@Query(" from Letter where letterType=?1 ORDER BY  letterId  DESC")
    List<Letter> findLetterByTypeList(String letterType);
	
	@Query(" from Letter where letterType=?1 AND enableGrade=?2 ORDER BY  letterId  DESC")
	public Letter findLetterByEnableGrade(String letterType, String enableGrade);
}
