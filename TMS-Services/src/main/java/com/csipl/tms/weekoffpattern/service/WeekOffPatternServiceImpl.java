package com.csipl.tms.weekoffpattern.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;
import com.csipl.tms.model.weekofpattern.WeekOffPattern;
import com.csipl.tms.weekoffpattern.repository.TMSWeekOffPatternChildRepository;
import com.csipl.tms.weekoffpattern.repository.TMSWeekOffPatternRepository;
import com.csipl.tms.weekoffpattern.repository.WeekOffPatternRepository;


@Service("week_Off_PatternService")
public class WeekOffPatternServiceImpl implements WeekOffPatternService {

	@Autowired
	WeekOffPatternRepository week_Off_PatternRepository;
	
	@Autowired
	TMSWeekOffPatternRepository tmsWeekOffPatternRepository;

	@Autowired
	TMSWeekOffPatternChildRepository tmsWeekOffPatternChildRepository;
	
	@Override
	public List<WeekOffPattern> getAllWeekOffPattern(Long companyId) {
		return week_Off_PatternRepository.getAllWeekOffPattern(companyId);
	}

	@Override
	public void save(WeekOffPattern week_Off_Pattern) {
		week_Off_PatternRepository.save(week_Off_Pattern);
	}

	@Override
	public WeekOffPattern findOne(Long patternId) {
		return week_Off_PatternRepository.findOne(patternId);
	}

	@Override
	public List<WeekOffPattern> getWeekOffPattern(Long companyId) {
		return week_Off_PatternRepository.getWeekOffPattern(companyId);
	}

	@Override
	public String[] getWeekOffPatternByEmp(Long companyId, Long employeeId) {
		return week_Off_PatternRepository.getWeekOffPatternByEmp(companyId,employeeId);
	}

	
	
	
	//  new implementation 
	@Override
	public List<TMSWeekOffMasterPattern> getAllTMSWeekOffPattern(Long companyId) {
		// TODO Auto-generated method stub
		return tmsWeekOffPatternRepository.getAllTMSWeekOffPattern(companyId);
	}

	 

	@Override
	@Transactional
	public TMSWeekOffMasterPattern saveTMSWeekOffMasterPattern(TMSWeekOffMasterPattern tmsWeekOffMasterPattern) {
		return tmsWeekOffPatternRepository.save(tmsWeekOffMasterPattern);
	}

	@Override
	public TMSWeekOffMasterPattern findTMSWeekOffMasterPattern(Long patternId) {
		// TODO Auto-generated method stub
		return tmsWeekOffPatternRepository.findTMSWeekOffMasterPattern(patternId);
	}

	@Override
	public String[] getTMSWeekOffPatternByEmp(Long companyId, Long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TMSWeekOffChildPattern> getTMSWeekOffChildPattern(Long patternId, String dayName) {
		// TODO Auto-generated method stub
		return tmsWeekOffPatternRepository.getTMSWeekOffChildPattern(patternId, dayName);
	}

	@Override
	public void deleteWeekOffPatternChild(Long id) {
		tmsWeekOffPatternChildRepository.delete(id);
	}

}
