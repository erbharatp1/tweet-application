package com.csipl.hrms.service.employee;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.dto.employee.TicketRaisingHdDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.TicketRaisingHD;
import com.csipl.hrms.model.employee.TicketType;
import com.csipl.tms.dto.common.SearchDTO;

public interface TicketRaisingService {
	 public TicketRaisingHD save(TicketRaisingHD ticketRaisingHD, MultipartFile file , boolean fileFlag , Long companyId);
	  public List<TicketRaisingHD> findAllTicketRaising(Long companyId);
	  public List<TicketRaisingHD> findAllEmpTicketRaising(Long employeeId);
	  public TicketRaisingHD findTicketRaising(Long ticketRaisingHDId);
	  public List<TicketRaisingHD> findAllTicketRaisingOpen(Long companyId);
	  public boolean mailSend(ByteArrayInputStream bis, TicketRaisingHD ticketRaising, String emailTo, String emailFrom,
				TicketRaisingHdDTO ticketRaisingHdDto, String extension,Employee employee,TicketType ticketType);
	 public List<Object[]> getTicketRaisingbyPagination(Long employeeId, SearchDTO ticketSearchDTO);
	
	 public List<Object[]> getTicketDetailsbyPagination(Long cmpId, SearchDTO searchDTO);

}
