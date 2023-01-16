package com.csipl.hrms.employee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.TicketTypeDTO;
import com.csipl.hrms.model.employee.TicketType;
import com.csipl.hrms.service.adaptor.TicketManagementAdaptor;
import com.csipl.hrms.service.employee.TicketTypeService;

@RestController
@RequestMapping("/ticketType")
public class TicketTypeMasterController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(TicketTypeMasterController.class);
	@Autowired
	TicketTypeService ticketTypeService;
	
	TicketManagementAdaptor ticketManagementAdaptor = new TicketManagementAdaptor();
	
	/**
	 * @param ticketTypeDTO
	 *            This is the first parameter for getting Skill Object from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping( method = RequestMethod.POST)
	public void save(@RequestBody TicketTypeDTO ticketTypeDTO, HttpServletRequest req) {
		TicketType ticketType = ticketManagementAdaptor.uiDtoToDatabaseModel(ticketTypeDTO);
		ticketTypeService.save(ticketType);
	}
	
	/**
	 * to get List of TicketType from database based on companyId
	 * @throws PayRollProcessException 
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<TicketTypeDTO> fetchAllTicket(@PathVariable("companyId") String companyId) throws ErrorHandling, PayRollProcessException {
		Long companyIdValue=Long.parseLong(companyId);
		List<TicketType> ticketTypeList = ticketTypeService.findAllTicketType(companyIdValue);
		if (ticketTypeList != null && ticketTypeList.size() > 0)
			return ticketManagementAdaptor.databaseModelToUiDtoList(ticketTypeList);
		else
			throw new ErrorHandling(" ticketType Data not present");
	}
	/**
	 * to get TicketType Object from database based on ticketTypeId (Primary Key)
	 */
	@RequestMapping(value = "/ticketTypeById/{ticketTypeId}", method = RequestMethod.GET)
	public @ResponseBody TicketTypeDTO getTicketType(@PathVariable("ticketTypeId") String ticketTypeID,
			HttpServletRequest req) throws ErrorHandling {
		Long ticketTypeId = Long.parseLong(ticketTypeID);
		TicketType ticketType = ticketTypeService.findTicketType(ticketTypeId);
		if (ticketType != null)
			return ticketManagementAdaptor.databaseModelToUiDto(ticketType);
		else
			throw new ErrorHandling(" ticketType Data not present");
	}
}
