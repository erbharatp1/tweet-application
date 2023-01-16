package com.csipl.tms.weekoffpattern.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.tms.model.weekofpattern.WeekOffPattern;

public interface WeekOffPatternRepository extends CrudRepository<WeekOffPattern, Long> {
	@Query("from WeekOffPattern where companyId=?1 AND activeStatus='AC'")
	public List<WeekOffPattern> getAllWeekOffPattern(Long companyId);
	
	@Query("from WeekOffPattern where companyId=?1")
	public List<WeekOffPattern> getWeekOffPattern(Long companyId);
	
	String weekOffPatternByEmp="SELECT wop.day FROM Employee e "
			+ "JOIN Department d ON d.departmentId = e.departmentId "
			+ "JOIN TMSWeekOffPattern wop ON wop.patternId = d.patternId "
			+ "where e.companyId=?1 AND e.employeeId=?2";
	
	
	String QUERY_WEEKOFF_PATTERN = "select tmsWeekOffPattern.patternId,tmsWeekOffPattern.day from TMSWeekOffPattern tmsWeekOffPattern \r\n" + 
			"	JOIN Employee employee ON employee.patternId=tmsWeekOffPattern.patternId  \r\n" + 
			"	WHERE employee.employeeId=?2 and  employee.companyId=?1 And employee.activeStatus='AC'";
	
	@Query(value=QUERY_WEEKOFF_PATTERN, nativeQuery = true)
	public String[] getWeekOffPatternByEmp(Long companyId, Long employeeId);
	
	
}
