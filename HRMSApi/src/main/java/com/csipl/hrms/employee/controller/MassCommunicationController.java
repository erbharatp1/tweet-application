package com.csipl.hrms.employee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.MassCommunicationDTO;
import com.csipl.hrms.model.employee.MassCommunication;
import com.csipl.hrms.service.adaptor.MassCommunicationAdaptor;
import com.csipl.hrms.service.employee.CompanyAnnouncementService;
import com.csipl.hrms.service.employee.MassCommunicationService;
import com.csipl.tms.attendanceregularizationrequest.service.ARRequestPagingAndFilterService;
import com.csipl.tms.dto.common.EntityCountDTO;
import com.csipl.tms.dto.common.PageIndex;
import com.csipl.tms.dto.common.SearchDTO;

@RestController
@RequestMapping("/massCommunication")
public class MassCommunicationController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(MassCommunicationController.class);

	@Autowired
	MassCommunicationService massCommunicationService;

	@Autowired
	CompanyAnnouncementService companyAnnouncementService;

	@Autowired
	ARRequestPagingAndFilterService aRRequestPagingAndFilterService;

	MassCommunicationAdaptor massCommunicationAdaptor = new MassCommunicationAdaptor();

	/**
	 * 
	 * @param massCommunicationDTO
	 * @param req
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void saveMasscommunicaton(@RequestBody MassCommunicationDTO massCommunicationDTO, HttpServletRequest req) {
		logger.info("saveMasscommunicaton is calling : " + "MassCommunicationDTO : " + massCommunicationDTO);
		int index = 0;
		MassCommunication massCommunication = massCommunicationAdaptor.uiDtoToDatabaseModel(massCommunicationDTO);
		massCommunicationService.save(massCommunication);
//		for (Long ids : massCommunicationDTO.getDepartmentId()) {
//			Department department = new Department();
//			department.setDepartmentId(ids);
//			department.setDepartmentId(massCommunicationDTO.getDepartmentId().get(index));
//
//			CompanyAnnouncementDTO dto = new CompanyAnnouncementDTO();
//			dto.setMassCommunicationId(massCommunicationDTO.getMassCommunicationId());
//			dto.setCompanyAnnouncementId(massCommunicationDTO.getMassCommunicationId());
//			dto.setDepartmentId(massCommunicationDTO.getDepartmentId());
//		
//			
//			CompanyAnnouncement communication = massCommunicationAdaptor.uiDtoToDatabaseModelCompanyAnnouncement(dto);
//			logger.info("saveMasscommunicaton is end  :" + "massCommunication" + massCommunication);
//			communication.setDepartment(department);
//			
//			 communication.setMassCommunication(massCommunication);
//			  companyAnnouncementService.save(communication);
//			index++;
//
//		}

	}

	/**
	 * 
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<MassCommunicationDTO> getAllMassCommunications(@PathVariable("companyId") Long companyId,
			@RequestBody SearchDTO searcDto, HttpServletRequest req) throws ErrorHandling, PayRollProcessException {
		List<Object[]> massCommunicationList = aRRequestPagingAndFilterService.getAllMassCommunications(companyId,
				searcDto);
		logger.info("getAllEmployeeFamilyDetails is calling :" + "massCommunicationList");

		if (massCommunicationList != null && massCommunicationList.size() > 0)
			return massCommunicationAdaptor.databaseModelToDtoList(massCommunicationList);
		else
			throw new ErrorHandling(" MassCommunication Data not present");
	}

	/**
	 * 
	 * @param massCommunicationId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "massCom/{massCommunicationId}", method = RequestMethod.GET)
	public @ResponseBody MassCommunicationDTO getMassComm(@PathVariable("massCommunicationId") Long massCommunicationId,
			HttpServletRequest req) {
		logger.info("getMassComm is calling : massCommunicationId =" + massCommunicationId);
		return massCommunicationAdaptor
				.databaseModelToUiDto(massCommunicationService.findMassComm(massCommunicationId));
	}

	/**
	 * 
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 */
	@RequestMapping(value = "getAllMassCommDate/{companyId}/{departmentId}", method = RequestMethod.GET)
	public @ResponseBody List<MassCommunicationDTO> getAllMassCommDate(@PathVariable("companyId") Long companyId,
			@PathVariable("departmentId") Long departmentId, HttpServletRequest req)
			throws ErrorHandling, PayRollProcessException {
		List<MassCommunication> massCommunicationList = massCommunicationService.getAllMassCommDate(companyId,
				departmentId);
		logger.info("getAllMassCommDate is calling :" + "massCommunicationList");

		if (massCommunicationList != null && massCommunicationList.size() > 0)
			return massCommunicationAdaptor.databaseModelToUiDtoList(massCommunicationList);
		else
			throw new ErrorHandling(" MassCommunication Data not present");
	}

	@RequestMapping(value = "/{companyId}/{pageSize}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EntityCountDTO getPendingCount(@PathVariable("companyId") Long companyId,
			@PathVariable("pageSize") String pageSize, @RequestBody SearchDTO searcDto) throws PayRollProcessException {
		int count;
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();

		int parPageRecord = Integer.parseInt(pageSize);
		EntityCountDTO entityCountDto = new EntityCountDTO();
		List<Object[]> massCommunicationList = aRRequestPagingAndFilterService.getAllMassCommunications(companyId,
				searcDto);
		count = massCommunicationList.size();
		System.out.println("massCommunicationList count :" + count);
		int pages = (count + parPageRecord - 1) / parPageRecord;

		for (int i = 1; i <= pages; i++) {
			PageIndex pageIndex = new PageIndex();
			pageIndex.setPageIndex(i);
			pageIndexList.add(pageIndex);
		}
		entityCountDto.setPageIndexs(pageIndexList);
		entityCountDto.setCount(count);
		return entityCountDto;
	}
}
