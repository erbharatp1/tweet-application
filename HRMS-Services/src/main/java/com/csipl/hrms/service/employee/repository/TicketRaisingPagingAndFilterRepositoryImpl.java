package com.csipl.hrms.service.employee.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csipl.tms.dto.common.SearchDTO;


@Repository
public class TicketRaisingPagingAndFilterRepositoryImpl implements TicketRaisingPagingAndFilterRepository{
	
	@PersistenceContext(unitName = "mySQL")
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getTicketRaisingbyPagination(Long employeeId,SearchDTO ticketSearchDTO) {
		StringBuilder sb = new StringBuilder();

		sb.append("Select hd.ticketRaisingHDId,hd.ticketTypeId,hd.employeeId,hd.title,hd.createdBy,hd.status,hd.ticketNo,hd.userId,hd.dateCreated"
				+ ",tt.category from TicketRaisingHD hd LEFT JOIN TicketType tt  ON  hd.ticketTypeId=tt.ticketTypeId Where hd.employeeId=:employeeId "
				+ "and  hd.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");
	
//		Group By emp.employeeId
		String active = ticketSearchDTO.getActive();
		String sortDirection = ticketSearchDTO.getSortDirection();
		int offset = ticketSearchDTO.getOffset();
		buildCondtion(sb, ticketSearchDTO);
		sortSearchQuery(sb, active, sortDirection);
		int limit = ticketSearchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------" + search);
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("employeeId", employeeId);
		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}
	private void buildCondtion(StringBuilder sb, SearchDTO searcDto) {
		if (searcDto.getTicketNo() != null && !searcDto.getTicketNo().equals("")
				&& !searcDto.getTicketNo().equalsIgnoreCase("null")
				&& !searcDto.getTicketNo().equalsIgnoreCase("undefined")) {

			sb.append(" and hd.ticketNo LIKE '" + searcDto.getTicketNo() + "%' ");
		}

	}
	
	
	private void sortSearchQuery(StringBuilder sb, String active, String sortDirection) {
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" order by hd.ticketRaisingHDId desc ");

			 
		} else if (active != null && (active.trim().equals("ticketNo"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by hd.ticketNo asc ");
			} else {
				sb.append(" order by hd.ticketNo desc ");
			}
		} else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by hd.dateCreated asc ");
			} else {
				sb.append(" order by hd.dateCreated desc ");
			}

		}
		 else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by hd.status asc ");
			} else {
				sb.append(" order by hd.status desc ");
			}

		} else if (active != null && (active.trim().equals("category"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tt.category asc ");
			} else {
				sb.append(" order by tt.category desc ");
			}

		} 
		else {

			sb.append(" order by hd.ticketRaisingHDId desc");
		}
	}
	@Override
	public List<Object[]> getTicketDetailsbyPagination(Long companyId, SearchDTO ticketSearchDTO) {
		StringBuilder sb = new StringBuilder();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		Date date = cal.getTime();
		
		sb.append("SELECT tr.ticketRaisingHDId,emp.employeeId,emp.employeeCode,CONCAT(UPPER(SUBSTRING(emp.firstName ,1,1)) ,\r\n" + 
				"LOWER(SUBSTRING(emp.firstName ,2))) firstName, CONCAT(UPPER(SUBSTRING(emp.lastName ,1,1)),\r\n" + 
				"LOWER(SUBSTRING(emp.lastName ,2))) lastName, tr.ticketNo,tr.dateCreated,tr.status,\r\n" + 
				"tt.category FROM `TicketRaisingHD` tr LEFT JOIN Employee emp ON tr.employeeId=emp.employeeId \r\n" + 
				"LEFT JOIN TicketType tt ON tr.ticketTypeId=tt.ticketTypeId where tr.companyId=:companyId AND tr.dateCreated  >=:date  \r\n" + 
				"and tr.dateCreated > DATE_SUB(now(), INTERVAL 6 MONTH) ");
	
//		Group By emp.employeeId
		String active = ticketSearchDTO.getActive();
		String sortDirection = ticketSearchDTO.getSortDirection();
		int offset = ticketSearchDTO.getOffset();
		buildTicketDetailsCondtion(sb, ticketSearchDTO);
		sortSearchQueryForTicketDetails(sb, active, sortDirection);
		int limit = ticketSearchDTO.getLimit();

		String search = sb.toString();

		System.out.println("Query Start-----------------------" + search);
		System.out.println(search);
		System.out.println("Query End-----------------------");
		Query nativeQuery = em.createNativeQuery(search);
		nativeQuery.setParameter("companyId", companyId);
		nativeQuery.setParameter("date", date);

		nativeQuery.setFirstResult(offset);
		nativeQuery.setMaxResults(limit);
		final List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println("result Size ---" + resultList.size());
		return resultList;

	}
	private void buildTicketDetailsCondtion(StringBuilder sb, SearchDTO searcDto) {
		if (searcDto.getEmployeeName() != null && !searcDto.getEmployeeName().equals("")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("null")
				&& !searcDto.getEmployeeName().equalsIgnoreCase("undefined")) {

			sb.append(" and ( emp.firstName LIKE '%" + searcDto.getEmployeeName() + "%' or  tr.ticketNo  LIKE '%"
					+ searcDto.getEmployeeName() + "%'" + "OR tr.status  LIKE '%" + searcDto.getEmployeeName() + "%' or  tt.category LIKE '%" + searcDto.getEmployeeName() + "%')");

		}

	}
	
	
	private void sortSearchQueryForTicketDetails(StringBuilder sb, String active, String sortDirection) {
		if (active != null && (active.trim().equals("") || active.trim().equals("undefined"))) {

			sb.append(" ORDER BY  tr.ticketRaisingHDId  DESC ");

			 
		} else if (active != null && (active.trim().equals("ticketNo"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tr.ticketNo asc ");
			} else {
				sb.append(" order by tr.ticketNo desc ");
			}
		}else if (active != null && (active.trim().equals("employeeName"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by emp.firstName,emp.lastName asc ");
			} else {
				sb.append(" order by emp.firstName,emp.lastName desc ");
			}
		}  else if (active != null && (active.trim().equals("dateCreated"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tr.dateCreated asc ");
			} else {
				sb.append(" order by tr.dateCreated desc ");
			}

		}
		 else if (active != null && (active.trim().equals("status"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tr.status asc ");
			} else {
				sb.append(" order by tr.status desc ");
			}

		} else if (active != null && (active.trim().equals("category"))) {

			if (sortDirection.equals("asc")) {
				sb.append(" order by tt.category asc ");
			} else {
				sb.append(" order by tt.category desc ");
			}
		} 
		else {

			sb.append(" ORDER BY  tr.ticketRaisingHDId  DESC");
		}
	}
}
