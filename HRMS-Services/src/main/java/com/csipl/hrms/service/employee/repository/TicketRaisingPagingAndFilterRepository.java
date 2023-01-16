package com.csipl.hrms.service.employee.repository;

import java.util.List;

import com.csipl.tms.dto.common.SearchDTO;


public interface TicketRaisingPagingAndFilterRepository {

	public List<Object[]> getTicketRaisingbyPagination(Long employeeId, SearchDTO ticketSearchDTO);

	public List<Object[]> getTicketDetailsbyPagination(Long cmpId, SearchDTO searchDTO);

}
