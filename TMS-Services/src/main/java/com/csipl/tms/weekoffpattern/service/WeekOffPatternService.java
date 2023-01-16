package com.csipl.tms.weekoffpattern.service;

import java.util.List;

import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;
import com.csipl.tms.model.weekofpattern.WeekOffPattern;

public interface WeekOffPatternService {

	public List<WeekOffPattern> getAllWeekOffPattern(Long companyId);
	
	public List<WeekOffPattern> getWeekOffPattern(Long companyId);

	public void save(WeekOffPattern week_Off_Pattern);

	public WeekOffPattern findOne(Long patternId);

	public String [] getWeekOffPatternByEmp(Long companyId,Long employeeId);
	
	
	//New Weekly Off Pattern Methods
	public List<TMSWeekOffMasterPattern> getAllTMSWeekOffPattern(Long companyId);
	
	public TMSWeekOffMasterPattern saveTMSWeekOffMasterPattern(TMSWeekOffMasterPattern week_Off_Pattern);

	public TMSWeekOffMasterPattern findTMSWeekOffMasterPattern(Long patternId);

	public String [] getTMSWeekOffPatternByEmp(Long companyId,Long employeeId);

	public List<TMSWeekOffChildPattern> getTMSWeekOffChildPattern(Long patternId, String dayName);

	public void deleteWeekOffPatternChild(Long weekOffPatternChildId);
	
	
	
}
