package com.csipl.hrms.payroll.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.payroll.PayHeadDTO;
import com.csipl.hrms.dto.payroll.PayHeadListDTO;
import com.csipl.hrms.model.payroll.PayHead;
import com.csipl.hrms.service.adaptor.PayHeadAdaptor;
import com.csipl.hrms.service.employee.PayStructureService;
import com.csipl.hrms.service.payroll.PayHeadService;
import com.csipl.hrms.service.payroll.repository.PayHeadRepository;

@RequestMapping("/payhead")
@RestController
public class PayHeadController {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(PayHeadController.class);
	boolean status = false;
	PayHeadAdaptor payheadAdaptor = new PayHeadAdaptor();

	@Autowired
	PayHeadService payHeadService;
	@Autowired
	PayStructureService payStructureService;
	@Autowired
	private PayHeadRepository payheadRepository;

	/**
	 * @param payHeadDto This is the first parameter for getting payHead object from
	 *                   UI
	 * @param req        This is the second parameter to maintain user session
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void payhead(@RequestBody PayHeadDTO payHeadDto, HttpServletRequest req) throws Exception {
		logger.info("PayHeadController.payhead(000000000000000000000)" + payHeadDto.getPriority());
		int count = payheadRepository.getPriority(payHeadDto.getPriority());
	//	int countResult = payheadRepository.getPriorityByStatus(payHeadDto.getPriority(), payHeadDto.getActiveStatus(),	payHeadDto.getPayHeadId());
		PayHead payHead = payheadAdaptor.uiDtoToDatabaseModel(payHeadDto);
		System.out.println("PayHeadControlle " + payHead.toString());
		payHeadService.save(payHead);
	//	if (countResult < 1) {
//			if (count  < 1) {
//				payHeadService.save(payHead);
//			}
//		 else
//			throw new IllegalArgumentException(" priority already exists ");
	}

	/**
	 * to get PayHeadList object from database based on companyId and earning OR
	 * deduction
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody PayHeadListDTO findAllPayHeads(@PathVariable("companyId") Long companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("PayHeadController.findAllPayHeads()");
		PayHeadListDTO listDTO = new PayHeadListDTO();
		List<PayHeadDTO> earningDtoList = payheadAdaptor
				.databaseModelToUiDtoList(payHeadService.getAllPayHeads("EA", companyId));
		List<PayHeadDTO> deductionDtoList = payheadAdaptor
				.databaseModelToUiDtoList(payHeadService.getAllPayHeads("DE", companyId));
		listDTO.setEarningPayHead(earningDtoList);
		listDTO.setDeductionPayHead(deductionDtoList);
		return listDTO;
	}

	/**
	 * to get List PayHead objects from database based on companyId and earning
	 * 
	 * @throws PayRollProcessException
	 */

	@RequestMapping(path = "/earningDeduction/{companyId}/{type}", method = RequestMethod.GET)

	public @ResponseBody List<PayHeadDTO> findAllEarningPayHeads(@PathVariable("companyId") String companyId,@PathVariable("type") String type,HttpServletRequest req) throws PayRollProcessException {
 		Long longcompanyId = Long.parseLong(companyId);
		List<PayHeadDTO> earningDtoList = payheadAdaptor.databaseModelToUiDtoList(payHeadService.getAllOneTimePayHeads(type, longcompanyId));

		return earningDtoList;
	}

	@RequestMapping(path = "/payEarning/{companyId}", method = RequestMethod.GET)

	public @ResponseBody List<PayHeadDTO> findAllPayStrutureHeads(@PathVariable("companyId") Long companyId,HttpServletRequest req) throws PayRollProcessException {
 	
		System.out.println("findAllPayStrutureHeads");
		List<PayHeadDTO> earningDtoList = payheadAdaptor.databaseModelToUiDtoList1(payHeadService.findAllEarnigPaystructurePayHeads( companyId));

		return earningDtoList;
	}

	
	
	/**
	 * to get List PayHead objects from database based on companyId and earning OR
	 * deduction
	 * 
	 * @throws PayRollProcessException
	 */
	@RequestMapping(path = "/payHeadList", method = RequestMethod.GET)
	public @ResponseBody List<PayHeadDTO> findAllPayHeadList(@RequestParam("companyId") String companyId,
			HttpServletRequest req) throws PayRollProcessException {
		logger.info("PayHeadController.findAllPayHeadList()");
		Long longcompanyId = Long.parseLong(companyId);
		List<PayHeadDTO> payHeadDtoList = payheadAdaptor
				.databaseModelToUiDtoList(payHeadService.findAllPayHeadOfCompany(longcompanyId));
		return payHeadDtoList;
	}

	/**
	 * to get PayHead objects from database based on payHeadId and earning OR
	 * deduction
	 * 
	 * @throws PayRollProcessException
	 */

	@GetMapping(path = "/payHeadByID/{payHeadId}")
	public @ResponseBody PayHeadDTO findPayHeadById(@PathVariable("payHeadId") long payHeadId) {
		logger.info("PayHeadController.findPayHeadById()" + payHeadId);
		return payheadAdaptor.databaseModelToUiDto(payHeadService.findPayHeadById(payHeadId));
	}

	@RequestMapping(path = "sequence/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> getSquenceId(@PathVariable("companyId") String companyId) {
//		logger.info("PayHeadController.findPayHeadById()" + payHeadId);
		return  payHeadService.getSquenceId(Long.valueOf(companyId));
	}
	
}
