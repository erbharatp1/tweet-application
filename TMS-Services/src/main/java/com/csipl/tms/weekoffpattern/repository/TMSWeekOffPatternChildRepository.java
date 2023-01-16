package com.csipl.tms.weekoffpattern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.weekofpattern.TMSWeekOffChildPattern;
import com.csipl.tms.model.weekofpattern.TMSWeekOffMasterPattern;

@Repository
public interface TMSWeekOffPatternChildRepository extends CrudRepository<TMSWeekOffChildPattern, Long>{

	 

}
