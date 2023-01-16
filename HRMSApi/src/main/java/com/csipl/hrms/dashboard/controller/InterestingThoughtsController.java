
package com.csipl.hrms.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.MassCommunicationDTO;
import com.csipl.hrms.dto.report.InterestingThoughtDTO;
import com.csipl.hrms.model.employee.InterestingThought;
import com.csipl.hrms.model.employee.MassCommunication;
import com.csipl.hrms.service.adaptor.InterestingThoughtsAdaptor;
import com.csipl.hrms.service.organization.InterestingThoughtsService;

@RestController
@RequestMapping("/interestingThoughts")
public class InterestingThoughtsController {
	private static final Logger log = LoggerFactory.getLogger(InterestingThoughtsController.class);

	@Autowired
	InterestingThoughtsService interestingThoughtsService;

	InterestingThoughtsAdaptor interestingThoughtsAdaptor = new InterestingThoughtsAdaptor();;

	/**
	 * 
	 * save
	 */
	@PostMapping(value = "/save")
	public @ResponseBody InterestingThoughtDTO save(@RequestBody InterestingThoughtDTO interestingThoughtDTO) {
		log.info("InterestingThoughtsController.save()");
		InterestingThought interestingThought = interestingThoughtsAdaptor.uiDtoToDatabaseModel(interestingThoughtDTO);
		InterestingThought interestingThoughtResult = interestingThoughtsService.save(interestingThought);
		return interestingThoughtsAdaptor.databaseModelToUiDto(interestingThoughtResult);
	}
	

	/**
	 * 
	 * getAllInterestingThoughts ErrorHandling PayRollProcessException
	 */

	@GetMapping(value = "getAllInterestingThought/{companyId}")
	public @ResponseBody List<InterestingThoughtDTO> getAllInterestingThoughts(
			@PathVariable("companyId") Long companyId) throws ErrorHandling, PayRollProcessException {
		log.info("InterestingThoughtsController.getAllInterestingThoughts()");

		List<InterestingThought> interestingThoughtList = interestingThoughtsService
				.findAllInterestingThought(companyId);
		if (interestingThoughtList != null && interestingThoughtList.size() > 0) {
			return interestingThoughtsAdaptor.databaseModelToUiDtoList(interestingThoughtList);
		}
		throw new ErrorHandling("InterestingThoughts data not present");
	}

	/**
	 * 
	 * @param interestingThoughtsId
	 */

	@DeleteMapping(value = "/delete/{interestingThoughtsId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteByinterestingThoughtsId(@PathVariable("interestingThoughtsId") Long interestingThoughtsId) {
		log.info("InterestingThoughtsController.deleteByinterestingThoughtsId()" + interestingThoughtsId);
		interestingThoughtsService.deleteByinterestingThoughtsId(interestingThoughtsId);
	}

	/**
	 * 
	 * @param thoughtsId
	 * @param companyId
	 * @param req
	 * @return
	 * @throws ErrorHandling
	 */
	@RequestMapping(value = "/getThought/{thoughtsId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody InterestingThoughtDTO getInterestingThought(@PathVariable("thoughtsId") String thoughtsId,
			@PathVariable("companyId") Long companyId, HttpServletRequest req) throws ErrorHandling {
		Long interestingthoughtsId = Long.parseLong(thoughtsId);
		InterestingThought interestingThought = interestingThoughtsService.findInterestingThought(interestingthoughtsId,
				companyId);
		if (interestingThought != null)
			return interestingThoughtsAdaptor.databaseModelToUiDto(interestingThought);
		else
			throw new ErrorHandling(" thought Data not present");

	}

}
