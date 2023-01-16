package com.csipl.tms.weekoffpattern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;

@Repository
public interface TMSWeekOffPatternRepository extends CrudRepository<TMSWeekOffMasterPattern, Long>{

	@Query("from TMSWeekOffMasterPattern where companyId=?1 ")
	List<TMSWeekOffMasterPattern> getAllTMSWeekOffPattern(Long companyId);

	@Query("from TMSWeekOffMasterPattern where patternId=?1 and activeStatus='AC' ")
	TMSWeekOffMasterPattern findTMSWeekOffMasterPattern(Long patternId);

	@Query("from TMSWeekOffChildPattern where patternId=?1 and dayName=?2 and activeStatus='AC' ")
	List<TMSWeekOffChildPattern> getTMSWeekOffChildPattern(Long patternId, String dayName);

}
