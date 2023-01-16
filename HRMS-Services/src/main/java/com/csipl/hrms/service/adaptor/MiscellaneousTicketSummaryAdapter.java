package com.csipl.hrms.service.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.employee.EmployeeTicketSummaryDTO;

public class MiscellaneousTicketSummaryAdapter {

	public List<EmployeeTicketSummaryDTO> objectListToTicketSummaryReport(List<Object[]> ticketsSummaryObjList)
			throws ParseException {

		List<EmployeeTicketSummaryDTO> employeeTicketSummaryList = new ArrayList<EmployeeTicketSummaryDTO>();

		for (Object[] ticketSummeryObj : ticketsSummaryObjList) {

			EmployeeTicketSummaryDTO employeeTicketSummeryDTO = new EmployeeTicketSummaryDTO();

			Long ticketId = ticketSummeryObj[0] != null ?(Long) Long.parseLong(String.valueOf(ticketSummeryObj[0]))  : null;
			String employeeCode = ticketSummeryObj[1] != null ? (String) ticketSummeryObj[1] : null;
			String employeeName = ticketSummeryObj[2] != null ? (String) ticketSummeryObj[2] : null;
			String employeeDesignation = ticketSummeryObj[3] != null ? (String) ticketSummeryObj[3] : null;
			String employeeDepartment = ticketSummeryObj[4] != null ? (String) ticketSummeryObj[4] : null;
			String ticketNo = ticketSummeryObj[5] != null ? (String) ticketSummeryObj[5] : null;
			String category = ticketSummeryObj[6] != null ? (String) ticketSummeryObj[6] : null;
			Date datedOn = ticketSummeryObj[7] != null ? (Date) ticketSummeryObj[7] : null;
			String status = ticketSummeryObj[8] != null ? (String) ticketSummeryObj[8] : null;
			Date closedOn = ticketSummeryObj[9] != null ? (Date) ticketSummeryObj[9] : null;
			String comments = ticketSummeryObj[10] != null ? (String) ticketSummeryObj[10] : null;

			employeeTicketSummeryDTO.setTicketId(ticketId);
			employeeTicketSummeryDTO.setEmployeeCode(employeeCode);
			employeeTicketSummeryDTO.setEmployeeName(employeeName);
			employeeTicketSummeryDTO.setEmployeeDesignation(employeeDesignation);
			employeeTicketSummeryDTO.setEmployeeDepartment(employeeDepartment);
			employeeTicketSummeryDTO.setTicketNo(ticketNo);
			employeeTicketSummeryDTO.setCategory(category);
			employeeTicketSummeryDTO.setDatedOn(datedOn);
			employeeTicketSummeryDTO.setStatus(status);
			employeeTicketSummeryDTO.setClosedOn(closedOn);
			employeeTicketSummeryDTO.setComments(comments);

			employeeTicketSummaryList.add(employeeTicketSummeryDTO);

		}

		return employeeTicketSummaryList;
	}

}
