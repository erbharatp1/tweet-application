package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.employee.Skill;
import com.csipl.hrms.model.employee.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {
	@Query(" from TicketType where companyId=?1 And activeStatus='AC' ORDER BY ticketTypeId DESC ") 
 	public List<TicketType> findAllticketType(Long companyId);
	
	@Modifying
	@Query("Update TicketType t SET t.activeStatus=:status WHERE t.ticketTypeId=:ticketTypeId")
	public int delete(@Param("ticketTypeId") Long ticketTypeId, @Param("status") String status);
	    
 }
