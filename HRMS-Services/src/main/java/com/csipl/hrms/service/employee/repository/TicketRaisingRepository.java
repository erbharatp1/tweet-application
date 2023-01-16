package com.csipl.hrms.service.employee.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.TicketRaisingHD;
@Repository
public interface TicketRaisingRepository  extends CrudRepository<TicketRaisingHD,Long>{
 
	@Query(nativeQuery=true,value="Select * from TicketRaisingHD hd where hd.employeeId=?1 and hd.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  ticketRaisingHDId  DESC ") 
 	public List<TicketRaisingHD> findAllEmpTicketRaising(Long employeeId);
	
	@Query(" from TicketRaisingHD where  ticketRaisingHDId=?1 ORDER BY  ticketRaisingHDId  DESC ") 
 	public TicketRaisingHD findTicketRaising( Long ticketRaisingHDId);
	
	@Query(" from TicketRaisingHD where companyId=?1 AND status=?2 And dateCreated>=?3 ORDER BY  ticketRaisingHDId  DESC ") 
	public List<TicketRaisingHD> findAllTicketRaisingOpen(Long companyId, String ticketStatusOpen, Timestamp currentDate);

	@Query(nativeQuery=true,value="Select * from TicketRaisingHD where companyId=?1 AND dateCreated  >=?2  and dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ORDER BY  ticketRaisingHDId  DESC ") 
 	public List<TicketRaisingHD> findAllTicketRaising(Long companyId, Date date);

}
