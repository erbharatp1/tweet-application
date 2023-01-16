package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.recruitment.PositionDTO;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.service.recruitement.PositionService;
import com.csipl.hrms.service.recruitement.adaptor.PositionAdaptor;

@RestController
@RequestMapping("/position")
public class PositionController {

	private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

	@Autowired
	private PositionService positionService;

	@Autowired
	private PositionAdaptor positionAdaptor;

	@GetMapping(path = "/getAllPositionData")
	public List<PositionDTO> getAllPositionData() {
		logger.info("getAllPositionData is calling ");
		List<Position> positionApprovalPerson = positionService.findAllPositionData();
		return positionAdaptor.databaseModelToUiDtoList(positionApprovalPerson);

	}

	/**
	 * 
	 * @param positionDTO
	 * @param req
	 */
	@PostMapping(path = "/savePosition")
	public void save(@RequestBody PositionDTO positionDTO, HttpServletRequest req) {
		logger.info("save is calling : positionDTO " + positionDTO);
		Position position = positionAdaptor.uiDtoToDatabaseModel(positionDTO);
		positionService.save(position);
		logger.info("save is calling :  Done");
	}

	/**
	 * to get all Recruiters List from database based on positionId
	 * 
	 *
	 */
	@GetMapping(value = "/findAllRecruitersBaesdOnPosition/{positionId}")
	public @ResponseBody List<PositionDTO> getRecruitersListBasedOnPosition(@PathVariable("positionId") Long positionId,
			HttpServletRequest req) {
		logger.info("PositionController.getRecruitersListBasedOnPosition()");
		List<Object[]> recruitersList = positionService.getRecruitersListBasedOnPosition(positionId);
		if (recruitersList != null && recruitersList.size() > 0)
			return positionAdaptor.databaseModelToUiDtoLists(recruitersList);
		else
			return null;

	}

	@GetMapping(path = "/findOnePosition/{positionId}")
	public List<PositionDTO> findOnePosition(@PathVariable("positionId") Long positionId, HttpServletRequest req) {
		logger.info("findOnePosition is calling ");
		List<Object[]> position = positionService.findOnePosition(positionId);
		return positionAdaptor.databaseModelToUiFIndPositionDto(position);

	}

	@GetMapping(path = "/findPositionListForSixMonths")
	public List<PositionDTO> findPositionListForSixMonths() {
		logger.info("findPositionListForSixMonths is calling ");
		List<Position> positionApprovalPerson = positionService.findPositionListForSixMonths();
		return positionAdaptor.databaseModelToUiDtoList(positionApprovalPerson);

	}

	@PutMapping(value = "/updateByPosition")
	public void updateByPosition(@RequestBody PositionDTO positionDTO, HttpServletRequest req) {
		logger.info("save letterStatusUpdate is calling :  " + positionDTO);
		Position position = new Position();
		position.setPositionId(positionDTO.getPositionId());
		position.setPositionStatus(positionDTO.getPositionStatus());
		position.setApprovalRemark(positionDTO.getApprovalRemark());
		position.setUserIdUpdate(positionDTO.getUserIdUpdate());
		positionService.updateByPosition(position);

	}

	/**
	 * to get all InterviewLevel List from database based on positionId
	 * 
	 *
	 */
	@GetMapping(value = "/findAllLevelsBaesdOnPosition/{positionId}")
	public @ResponseBody List<PositionDTO> getLevelListBasedOnPosition(@PathVariable("positionId") Long positionId,
			HttpServletRequest req) {
		logger.info("PositionController.getLevelListBasedOnPosition()");
		List<Object[]> levelList = positionService.getLevelListBasedOnPosition(positionId);
		if (levelList != null && levelList.size() > 0)
			return positionAdaptor.databaseModelToUiDtoLevelLists(levelList);
		else
			return null;

	}

	@GetMapping(path = "/getPositionByPositionCode/{positionCode}")
	public PositionDTO getPositiongByPositionCode(@PathVariable("positionCode") String positionCode,HttpServletRequest req) {
		logger.info("getPositiongByPositionCode is calling:" + positionCode);
		List<Object[]> position = positionService.findPositiongByPositionCode(positionCode);
		PositionDTO positionData = positionAdaptor.databaseModelToUiPositionExtraBudgetDto(position);
		Position noOfLevel=positionService.findPositionById(positionData.getPositionId());
		PositionDTO onePositionData = positionAdaptor.databaseModelToUiPositionExtraBudgetWithLevelDto(positionData,noOfLevel);
		logger.info("getPositiongByPositionCode is ending");
		return onePositionData;
	}

	@PutMapping(path = "/requestForExtraBudget")
	public void addExtraBudget(@RequestBody PositionDTO positionDTO, HttpServletRequest req) {
		logger.info("requestForExtraBudget is calling ");
		Position position = positionAdaptor.uiDtoToDatabaseModelForBudget(positionDTO);
		positionService.saveExtraBudgetRequest(position);
		logger.info("requestForExtraBudget is ending ");


	}

	@GetMapping(value = "/findPositionById/{positionId}")
	public @ResponseBody PositionDTO findPositionById(
			@PathVariable("positionId") Long positionId, HttpServletRequest req) {
		logger.info("PositionController.findPositionById()"+positionId);
		return positionAdaptor.databaseModelToUiDto(positionService.findPositionById(positionId));
	}
	
	

	@GetMapping(value = "/findAllSelectedLevelsList/{positionId}/{interviewScheduleId}")
	public @ResponseBody List<PositionDTO> getAllSelectedLevelList(@PathVariable("positionId") Long positionId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId, HttpServletRequest req) {
		logger.info("PositionController.getAllSelectedLevelList()");
		//List<Object[]> levelIdList = positionService.getAllSelectedLevelList(positionId, interviewScheduleId);
		List<Object[]> levelList = positionService.getAllRemainingLevelList(positionId, interviewScheduleId);
		return positionAdaptor.databaseModelToUiDtoLevelLists(levelList);

	}

	@GetMapping(value = "/findAllRemainingLevels/{positionId}/{interviewScheduleId}")
	public @ResponseBody List<PositionDTO> getAllRemainingLevelList(@PathVariable("positionId") Long positionId,
			@PathVariable("interviewScheduleId") Long interviewScheduleId, HttpServletRequest req) {
		logger.info("PositionController.getAllRemainingLevelList()");
		//List<Object[]> levelIdList = positionService.getAllLevelIdFromCandidateEvolution(positionId,interviewScheduleId);
		List<Object[]> levelList = positionService.getAllRemainingLevelList(positionId, interviewScheduleId);
		return positionAdaptor.databaseModelToUiDtoLevelLists(levelList);

	}
	
	@GetMapping(value = "/findAllLevels/{interviewScheduleId}/{levelId}")
	public @ResponseBody List<PositionDTO> getAllLevels(@PathVariable("interviewScheduleId") Long interviewScheduleId,@PathVariable("levelId") Long levelId, HttpServletRequest req) {
		logger.info("PositionController.getAllRemainingLevelList()");
		List<Object[]> levelList = positionService.getAllLevels(interviewScheduleId,levelId);
		return positionAdaptor.databaseModelToUiDtoLevelLists(levelList);

	}
	
	@GetMapping(value = "/findAllPositionList")
	public @ResponseBody List<PositionDTO> getAllPositionList(HttpServletRequest req) {
		logger.info("PositionController.getAllPositionList()");
		List<Object[]> allPositionList = positionService.getAllPositionList();
//		List<Object[]> allPositionCountList=positionService.getAllPositionCountList();
		return positionAdaptor.databaseModelToUiDtoPositionCountLists(allPositionList);
			
	}
	
	
	/**
	 * shubham yaduwanshi
	 * 
	 *
	 */
    
	@PutMapping(value = "/extraBudgetApprovalStatus")
	public void extraBudgetApprovalStatus(@RequestBody PositionDTO positionDTO, HttpServletRequest req) {
		logger.info("extraBudgetApprovalStatus is calling :  " + positionDTO);
		Position position=positionAdaptor.uiDtoToDatabaseModelExtraBudgetStatus(positionDTO);
		positionService.updateExtraBudgetApprovalStatus(position);
		logger.info("extraBudgetApprovalStatus is ending :");
	}
	
	
	@GetMapping(path = "/getAllPositionDataNext")
	public List<PositionDTO> getAllPositionDataNext() {
		logger.info("getAllPositionDataNext is calling ");
		List<Position> positionApprovalPerson = positionService.findAllPositionDataNext();
		List<PositionDTO> getAllPositionDataNext=positionAdaptor.databaseModelToUiDtoListNext(positionApprovalPerson);
		logger.info("getAllPositionDataNext is calling done");
		return getAllPositionDataNext;

	}
	
	@GetMapping(path = "/getAllPositionCode")
	public List<PositionDTO> getAllPositionCode() {
		logger.info("getAllPositionCode is calling ");
		List<Position> positionCodeList = positionService.findAllPositionCode();
		logger.info("getAllPositionCode is ending");
		return positionAdaptor.databaseModelToUiDtoList(positionCodeList);
	}
}
