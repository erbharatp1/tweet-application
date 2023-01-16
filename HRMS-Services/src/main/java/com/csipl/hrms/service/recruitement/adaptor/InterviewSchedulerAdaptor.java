package com.csipl.hrms.service.recruitement.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.recruitment.CandidateEvolutionDTO;
import com.csipl.hrms.dto.recruitment.FinalCandidateEvolutionDTO;
import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.model.recruitment.CandidateEvolution;
import com.csipl.hrms.model.recruitment.CandidateFinalEvalution;
import com.csipl.hrms.model.recruitment.InterviewScheduler;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;
import com.csipl.hrms.service.adaptor.Adaptor;
import com.csipl.hrms.service.employee.repository.ApprovalHierarchyMasterRepository;
import com.csipl.hrms.service.recruitement.InterviewSchedulerService;

@Component
public class InterviewSchedulerAdaptor implements Adaptor<InterviewSchedulerDTO, InterviewScheduler> {

	@Autowired
	InterviewSchedulerService interviewSchedulerService;

	@Override
	public List<InterviewScheduler> uiDtoToDatabaseModelList(List<InterviewSchedulerDTO> interviewSchedulerDtoList) {
		// TODO Auto-generated method stub
		return interviewSchedulerDtoList.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<InterviewSchedulerDTO> databaseModelToUiDtoList(List<InterviewScheduler> dbobj) {
		// TODO Auto-generated method stub
		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	public InterviewScheduler uiDtoToDatabaseModels(InterviewSchedulerDTO interviewSchedulerDTO, InterviewScheduler interviewScheduleObj) {
		// TODO Auto-generated method stub
		

		Position position = new Position();
		if (interviewSchedulerDTO.getInterviewScheduleId() != null
				&& interviewSchedulerDTO.getInterviewScheduleId() != 0) {
			interviewScheduleObj.setInterviewScheduleId(interviewSchedulerDTO.getInterviewScheduleId());
		}
		interviewScheduleObj.setCandidateName(interviewSchedulerDTO.getCandidateName());
		interviewScheduleObj.setCandidateContactNo(interviewSchedulerDTO.getCandidateContactNo());
		interviewScheduleObj.setCandidateEmailId(interviewSchedulerDTO.getCandidateEmailId());
		position.setPositionId(interviewSchedulerDTO.getPositionId());
		interviewScheduleObj.setPosition(position);
		interviewScheduleObj.setRecuiterId(interviewSchedulerDTO.getRecuiterId());
		interviewScheduleObj.setSourceOfProfile(interviewSchedulerDTO.getSourceOfProfile());
		interviewScheduleObj.setUserId(interviewSchedulerDTO.getUserId());
		interviewScheduleObj.setUserIdUpdate(interviewSchedulerDTO.getUserIdUpdate());
		interviewScheduleObj.setDateUpdated(new Date());
		interviewScheduleObj.setActiveStatus(interviewSchedulerDTO.getActiveStatus());

	

		for(CandidateEvolution candidateEvolutionOld:interviewScheduleObj.getCandidateEvolution()) {
		
		for (CandidateEvolutionDTO candidateEvolutionDto : interviewSchedulerDTO.getCandidateEvolutionList()) {

			PositionInterviewlevelXRef positionlevelXRef = new PositionInterviewlevelXRef();
			if (candidateEvolutionDto.getLevelId().equals(candidateEvolutionOld.getPositionInterviewlevelXRef().getLevelId())) {
				
			positionlevelXRef.setLevelId(candidateEvolutionDto.getLevelId());
			candidateEvolutionOld.setPositionInterviewlevelXRef(positionlevelXRef);
			candidateEvolutionOld.setInterviewMode(candidateEvolutionDto.getInterviewMode());
			String fromTime = candidateEvolutionDto.getInterviewTime();
			String[] timeArray = fromTime.split(":");
			if (Integer.parseInt(timeArray[0]) > 12) {
				candidateEvolutionOld.setInterviewTime((Integer.parseInt(timeArray[0]) - 12 + ":" + timeArray[1] + "PM"));

			} else {

				candidateEvolutionOld.setInterviewTime(timeArray[0] + ":" + timeArray[1] + "AM");
			}

			candidateEvolutionOld.setInterviewDate(candidateEvolutionDto.getInterviewDate());
			candidateEvolutionOld.setUserId(candidateEvolutionDto.getUserId());
			candidateEvolutionOld.setInterviewScheduler(interviewScheduleObj);
			candidateEvolutionOld.setIsInterviewScheduled("Y");
			}
		  }
		}
	

		return interviewScheduleObj;

	}
	
	
	
	
	public InterviewScheduler uiDtoToDatabaseModels1(InterviewSchedulerDTO interviewSchedulerDTO, Long levelId,List<PositionInterviewlevelXRef> positionInterviewlevelXRefList ) {
		// TODO Auto-generated method stub
		InterviewScheduler interviewScheduler = new InterviewScheduler();

		Position position = new Position();

		interviewScheduler.setInterviewScheduleId(interviewSchedulerDTO.getInterviewScheduleId());
		if (interviewSchedulerDTO.getInterviewScheduleId() != null
				&& interviewSchedulerDTO.getInterviewScheduleId() != 0) {
			interviewScheduler.setInterviewScheduleId(interviewSchedulerDTO.getInterviewScheduleId());
		}
		interviewScheduler.setCandidateName(interviewSchedulerDTO.getCandidateName());
		interviewScheduler.setCandidateContactNo(interviewSchedulerDTO.getCandidateContactNo());
		interviewScheduler.setCandidateEmailId(interviewSchedulerDTO.getCandidateEmailId());
		position.setPositionId(interviewSchedulerDTO.getPositionId());
		interviewScheduler.setPosition(position);
		interviewScheduler.setRecuiterId(interviewSchedulerDTO.getRecuiterId());
		interviewScheduler.setSourceOfProfile(interviewSchedulerDTO.getSourceOfProfile());
		interviewScheduler.setUserId(interviewSchedulerDTO.getUserId());
		if (interviewSchedulerDTO.getInterviewScheduleId() != null && interviewSchedulerDTO.getInterviewScheduleId() != 0)
			interviewScheduler.setDateCreated(interviewSchedulerDTO.getDateCreated());
		else
			interviewScheduler.setDateCreated(new Date());
		interviewScheduler.setUserIdUpdate(interviewSchedulerDTO.getUserIdUpdate());
		interviewScheduler.setDateUpdated(new Date());
		interviewScheduler.setActiveStatus(interviewSchedulerDTO.getActiveStatus());

		List<CandidateEvolution> candidateEvolutionList = new ArrayList<CandidateEvolution>();
		for(PositionInterviewlevelXRef positionInterviewlevelXRef:positionInterviewlevelXRefList) {
			CandidateEvolution candidateEvolution =new CandidateEvolution();
			candidateEvolution.setInterviewScheduler(interviewScheduler);
			PositionInterviewlevelXRef positionlevelXRef = new PositionInterviewlevelXRef();
			positionlevelXRef.setLevelId(positionInterviewlevelXRef.getLevelId());
			candidateEvolution.setPositionInterviewlevelXRef(positionInterviewlevelXRef);
			candidateEvolution.setDateCreated(new Date());
			candidateEvolution.setIsInterviewScheduled("N");
			
			for (CandidateEvolutionDTO candidateEvolutionDto : interviewSchedulerDTO.getCandidateEvolutionList()) {
				if (candidateEvolutionDto.getLevelId().equals(positionInterviewlevelXRef.getLevelId())) {

					candidateEvolution.setEvalutionId(candidateEvolutionDto.getEvalutionId());
					candidateEvolution.setInterviewMode(candidateEvolutionDto.getInterviewMode());
					String fromTime = candidateEvolutionDto.getInterviewTime();
					String[] timeArray = fromTime.split(":");
					if (Integer.parseInt(timeArray[0]) > 12) {
						candidateEvolution.setInterviewTime((Integer.parseInt(timeArray[0]) - 12 + ":" + timeArray[1] + "PM"));
					} else {
						candidateEvolution.setInterviewTime(timeArray[0] + ":" + timeArray[1] + "AM");
					}

					candidateEvolution.setInterviewDate(candidateEvolutionDto.getInterviewDate());
					candidateEvolution.setUserId(candidateEvolutionDto.getUserId());
					candidateEvolution.setIsInterviewScheduled("Y");
				}
			}
			candidateEvolutionList.add(candidateEvolution);
		}
		interviewScheduler.setCandidateEvolution(candidateEvolutionList);
		return interviewScheduler;

	}


	@Override
	public InterviewSchedulerDTO databaseModelToUiDto(InterviewScheduler dbobj) {
		// TODO Auto-generated method stub

		return null;
	}

	public List<InterviewSchedulerDTO> databaseModelToUiDtoLists(List<Object[]> interviewScheduleDetailsList, List<Object[]> candidateEvolutionList) {
		// TODO Auto-generated method stub
		List<InterviewSchedulerDTO> interviewScheduleDtoList = new ArrayList<>();
		for (Object[] interviewScheduleObj : interviewScheduleDetailsList) {
			InterviewSchedulerDTO interviewSchedulerDTO = new InterviewSchedulerDTO();
			List<InterviewLevelDTO> InterviewLevelDTOList = new ArrayList<>();
			//List<CandidateEvolutionDTO> candidateEvolutionDtoList = new ArrayList<>();
			String candidateName = interviewScheduleObj[0] != null ? (String) interviewScheduleObj[0] : null;
			String candidateContactNo = interviewScheduleObj[1] != null ? (String) interviewScheduleObj[1] : null;
			String candidateEmailId = interviewScheduleObj[2] != null ? (String) interviewScheduleObj[2] : null;
			String positionTitle = interviewScheduleObj[3] != null ? (String) interviewScheduleObj[3] : null;
			String positionCode = interviewScheduleObj[4] != null ? (String) interviewScheduleObj[4] : null;
			String requiredExperience = interviewScheduleObj[5] != null ? (String) interviewScheduleObj[5] : null;
			String noOfLevel = interviewScheduleObj[6] != null ? (String) interviewScheduleObj[6] : null;
			String employeeName = interviewScheduleObj[7] != null ? (String) interviewScheduleObj[7] : null;
			Long interviewScheduleId = interviewScheduleObj[8] != null ? Long.parseLong(interviewScheduleObj[8].toString()) : null;
			Long evalutionId = interviewScheduleObj[9] != null ? Long.parseLong(interviewScheduleObj[9].toString()): null;
			Long positionId = interviewScheduleObj[11] != null ? Long.parseLong(interviewScheduleObj[9].toString()): null;

			Long candidateEvalutionId = null;
			Long levelId = null;
			String interviewTime = null;
			Date interviewDate = null;
			String interviewMode = null;
			
			for (Object[] candidateEvolutionObj : candidateEvolutionList) {
				candidateEvalutionId = candidateEvolutionObj[0] != null? Long.parseLong(candidateEvolutionObj[0].toString()): null;

				if (candidateEvalutionId.equals(evalutionId)) {
					levelId = candidateEvolutionObj[1] != null ? Long.parseLong(candidateEvolutionObj[1].toString()): null;
					interviewTime = candidateEvolutionObj[2] != null ? (String) candidateEvolutionObj[2] : null;
					interviewDate = candidateEvolutionObj[3] != null ? (Date) candidateEvolutionObj[3] : null;
					interviewMode = interviewScheduleObj[4] != null ? (String) interviewScheduleObj[4] : null;
					
				}
			}

		List<Object[]> allLevelsCandidateList = interviewSchedulerService.getAllLevelsFromCandidate(interviewScheduleId);
			
			String selectedLevel = null;
			String selectedLevelArr[] = null;
			Long selectedLevelsSize1 = null;

			for (Object[] candidateObj : allLevelsCandidateList) {

			selectedLevel = candidateObj[1] != null ? (String) candidateObj[1] : null;
			selectedLevelArr = selectedLevel.split(",");
			selectedLevelsSize1 =(long) selectedLevelArr.length;
				//System.out.println(selectedLevelsSize1);
				for (int i = 0; i < selectedLevelArr.length; i++) {
					InterviewLevelDTO interviewLevelDTO = new InterviewLevelDTO();
					String arr[] = selectedLevelArr[i].split("-");
					interviewLevelDTO.setLevelName(arr[1]);
					if (arr[2].equals("Y")) {
						interviewLevelDTO.setStatus("Scheduled");
					} else if (arr[2].equals("N")) {
						interviewLevelDTO.setStatus("To be Scheduled");
					}else {
						interviewLevelDTO.setStatus("To be scheduled");
					}
					interviewLevelDTO.setLevel(arr[0]);
					InterviewLevelDTOList.add(interviewLevelDTO);
				}
		}
			
			
			List<Object[]> selectedLevelsList = interviewSchedulerService.getSelectedLevelList(interviewScheduleId);
			
			//long levelSize = allLevelsList.size();
			long selectedLevelsSize = selectedLevelsList.size();
			BigDecimal levelPercentage = BigDecimal.valueOf((100 / selectedLevelsSize1) * selectedLevelsSize);
			
			
			interviewSchedulerDTO.setCandidateName(candidateName);
			interviewSchedulerDTO.setCandidateContactNo(candidateContactNo);
			interviewSchedulerDTO.setCandidateEmailId(candidateEmailId);
			interviewSchedulerDTO.setInterviewDate(interviewDate);
			interviewSchedulerDTO.setInterviewTime(interviewTime);
			interviewSchedulerDTO.setInterviewMode(interviewMode);
			interviewSchedulerDTO.setPositionTitle(positionTitle);
			interviewSchedulerDTO.setPositionCode(positionCode);
			interviewSchedulerDTO.setRequiredExperience(requiredExperience);
			interviewSchedulerDTO.setNoOfLevel(noOfLevel);
			interviewSchedulerDTO.setEmployeeName(employeeName);
			interviewSchedulerDTO.setInterviewScheduleId(interviewScheduleId);
			interviewSchedulerDTO.setLevelId(levelId);
			interviewSchedulerDTO.setEvalutionId(evalutionId);
			interviewSchedulerDTO.setLevelPercentage(levelPercentage);
			interviewSchedulerDTO.setNoOfInterviewLevel(selectedLevelsSize1);
			interviewSchedulerDTO.setPositionId(positionId);
			interviewSchedulerDTO.setInterviewLevelDTOList(InterviewLevelDTOList);
			interviewScheduleDtoList.add(interviewSchedulerDTO);
		}
		return interviewScheduleDtoList;
	}

	@Override
	public InterviewScheduler uiDtoToDatabaseModel(InterviewSchedulerDTO uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<InterviewSchedulerDTO> databaseModelToAssesmentList(List<Object[]> assesmentList) {
		// TODO Auto-generated method stub
		List<InterviewSchedulerDTO> interviewScheduleDtoList = new ArrayList<>();
		for (Object[] assesment : assesmentList) {
			List<InterviewLevelDTO> InterviewLevelDTOList = new ArrayList<>();
			InterviewSchedulerDTO interviewSchedulerDTO = new InterviewSchedulerDTO();
			String candidateName = assesment[0] != null ? (String) assesment[0] : null;
			String candidateContactNo = assesment[1] != null ? (String) assesment[1] : null;
			String candidateEmailId = assesment[2] != null ? (String) assesment[2] : null;
			String positionTitle = assesment[3] != null ? (String) assesment[3] : null;
			String recuiterName = assesment[4] != null ? (String) assesment[4] : null;
			String level = assesment[5] != null ? (String) assesment[5] : null;
			Long interviewScheduleId = assesment[6] != null ? Long.parseLong(assesment[6].toString()) : null;
			interviewSchedulerDTO.setInterviewScheduleId(interviewScheduleId);
			interviewSchedulerDTO.setLevel(level);
			interviewSchedulerDTO.setCandidateName(candidateName);
			interviewSchedulerDTO.setCandidateContactNo(candidateContactNo);
			interviewSchedulerDTO.setCandidateEmailId(candidateEmailId);
			interviewSchedulerDTO.setPositionTitle(positionTitle);
			interviewSchedulerDTO.setRecuiterName(recuiterName);
			String levelArr[] = level.split(",");
			for (int i = 0; i < levelArr.length; i++) {
				InterviewLevelDTO interviewLevelDTO = new InterviewLevelDTO();
				String arr[] = levelArr[i].split("-");
				interviewLevelDTO.setLevelName(arr[1]);
				if (arr[2].equals("P")) {
					interviewLevelDTO.setStatus("Pass");
				} else if (arr[2].equals("R")) {
					interviewLevelDTO.setStatus("Reject");
				}else {
					interviewLevelDTO.setStatus("Pending");
				}
				interviewLevelDTO.setLevel(arr[0]);
				InterviewLevelDTOList.add(interviewLevelDTO);
			}
			interviewSchedulerDTO.setInterviewLevelDTOList(InterviewLevelDTOList);
			interviewScheduleDtoList.add(interviewSchedulerDTO);
		}
		return interviewScheduleDtoList;
	}

	public InterviewSchedulerDTO databaseModelToInterviewSchedulerUiDto(List<Object[]> interviewScheduler,
			List<Object[]> levelList) {
		InterviewSchedulerDTO interviewSchedulerDTO = new InterviewSchedulerDTO();
		List<InterviewLevelDTO> InterviewLevelDTOList = new ArrayList<>();
		for (Object[] assesment : interviewScheduler) {
			String candidateName = assesment[0] != null ? (String) assesment[0] : null;
			String candidateContactNo = assesment[1] != null ? (String) assesment[1] : null;
			String candidateEmailId = assesment[2] != null ? (String) assesment[2] : null;
			String positionTitle = assesment[3] != null ? (String) assesment[3] : null;
			String level = assesment[4] != null ? (String) assesment[4] : null;
			Long interviewScheduleId = assesment[5] != null ? Long.parseLong(assesment[5].toString()) : null;
			String positionCode = assesment[6] != null ? (String) assesment[6] : null;
			String jobLocation = assesment[7] != null ? (String) assesment[7] : null;
			String gradeName = assesment[8] != null ? (String) assesment[8] : null;
			String positionType = assesment[9] != null ? (String) assesment[9] : null;
			Long positionId = assesment[10] != null ? Long.parseLong(assesment[10].toString()) : null;
			String recuiterName = assesment[11] != null ? (String) assesment[11] : null;
			String finalStatus = assesment[12] != null ? (String) assesment[12] : null;
			String noOfLevel = assesment[13] != null ? (String) assesment[13] : null;
			Long noOfInterviewLevel = assesment[14] != null ? Long.parseLong(assesment[14].toString()) : null;
			if (finalStatus.equals("P")) {
				interviewSchedulerDTO.setFinalStatus("Pass");
			} else if (finalStatus.equals("R")) {
				interviewSchedulerDTO.setFinalStatus("Rejected");
			}else {
				interviewSchedulerDTO.setFinalStatus("Pending");
			}
			interviewSchedulerDTO.setNoOfInterviewLevel(noOfInterviewLevel);
			interviewSchedulerDTO.setRecuiterName(recuiterName);
			interviewSchedulerDTO.setPositionId(positionId);
			interviewSchedulerDTO.setInterviewScheduleId(interviewScheduleId);
			interviewSchedulerDTO.setLevel(level);
			interviewSchedulerDTO.setCandidateName(candidateName);
			interviewSchedulerDTO.setCandidateContactNo(candidateContactNo);
			interviewSchedulerDTO.setCandidateEmailId(candidateEmailId);
			interviewSchedulerDTO.setPositionTitle(positionTitle);
			interviewSchedulerDTO.setPositionCode(positionCode);
			interviewSchedulerDTO.setJobLocation(jobLocation);
			interviewSchedulerDTO.setGradeName(gradeName);
			interviewSchedulerDTO.setPositionType(positionType);
			//interviewSchedulerDTO.setNoOfLevel(noOfLevel);
			
			if (noOfLevel.equals("EN")) {
				interviewSchedulerDTO.setNoOfLevel("Entry Level");
			} else if (noOfLevel.equals("EX")) {
				interviewSchedulerDTO.setNoOfLevel("Experienced Level");
			}
			else if (noOfLevel.equals("MI")) {
				interviewSchedulerDTO.setNoOfLevel("Middle-Level");
			}
			else if (noOfLevel.equals("TO")) {
				interviewSchedulerDTO.setNoOfLevel("Top-Level");
			}
			
		}
		int i = 0;
		for (Object[] level : levelList) {
			InterviewLevelDTO interviewLevelDTO = new InterviewLevelDTO();
			String levelName = level[0] != null ? (String) level[0] : null;
			String status = level[1] != null ? (String) level[1] : null;
			String remark = level[2] != null ? (String) level[2] : null;
			String interviewMode = level[3] != null ? (String) level[3] : null;
			String interviewerName = level[4] != null ? (String) level[4] : null;
			Long evalutionId = level[5] != null ? Long.parseLong(level[5].toString()) : null;
			Long interviewScheduleId = level[6] != null ? Long.parseLong(level[6].toString()) : null;
			Long levelId = level[7] != null ? Long.parseLong(level[7].toString()) : null;
			String filePath = level[8] != null ? (String) level[8] : null;
			Date dateUpdated = level[9] != null ? (Date) level[9] : null;
			String fileName = level[10] != null ? (String) level[10] : null;
			interviewLevelDTO.setDateUpdated(dateUpdated);
			interviewLevelDTO.setFilePath(filePath);
			interviewLevelDTO.setFileName(fileName);
			interviewLevelDTO.setEvalutionId(evalutionId);
			interviewLevelDTO.setInterviewScheduleId(interviewScheduleId);
			interviewLevelDTO.setLevelId(levelId);
			interviewLevelDTO.setInterviewMode(interviewMode);
			interviewLevelDTO.setLevelName(levelName);
			interviewLevelDTO.setRemarks(remark);
			interviewLevelDTO.setStatus(status);
//			if (status.equals("P")) {
//				interviewLevelDTO.setStatus("Pass");
//			} else if (status.equals("R")) {
//				interviewLevelDTO.setStatus("Reject");
//			}
			interviewLevelDTO.setInterviewerName(interviewerName);
			i++;
			String levels = "L" + i;
			interviewLevelDTO.setLevel(levels);
			InterviewLevelDTOList.add(interviewLevelDTO);
		}
		interviewSchedulerDTO.setInterviewLevelDTOList(InterviewLevelDTOList);
		return interviewSchedulerDTO;
	}

	public CandidateFinalEvalution databaseModelToCandidateFinalEvalution(InterviewSchedulerDTO dbobj) {
		CandidateFinalEvalution candidateFinalEvalution = new CandidateFinalEvalution();
		candidateFinalEvalution.setInterviewScheduledId(dbobj.getInterviewScheduleId());
		candidateFinalEvalution.setCandidateName(dbobj.getCandidateName());
		candidateFinalEvalution.setContactNo(dbobj.getCandidateContactNo());
		candidateFinalEvalution.setEmailId(dbobj.getCandidateEmailId());
		candidateFinalEvalution.setPositionId(dbobj.getPositionId());
		candidateFinalEvalution.setRecruiterName(dbobj.getRecuiterName());
		for (InterviewLevelDTO level : dbobj.getInterviewLevelDTOList()) {
			if (level.getStatus().equals("R")) {
				candidateFinalEvalution.setFinalStatus("R");
				break;
			} else {
				candidateFinalEvalution.setFinalStatus("P");
			}
		}
		return candidateFinalEvalution;
	}

	public InterviewSchedulerDTO databaseModelToInterviewScheduleUiDto(InterviewScheduler dbobj, Long levelId) {
		// TODO Auto-generated method stub
		InterviewSchedulerDTO interviewSchedulerDTO = new InterviewSchedulerDTO();

		List<CandidateEvolution> candidateEvolutionXRefList = new ArrayList<CandidateEvolution>();
		candidateEvolutionXRefList = dbobj.getCandidateEvolution();
		List<CandidateEvolutionDTO> candidateEvolutionDTOList = new ArrayList<CandidateEvolutionDTO>();

		interviewSchedulerDTO.setInterviewScheduleId(dbobj.getInterviewScheduleId());
		interviewSchedulerDTO.setCandidateName(dbobj.getCandidateName());
		interviewSchedulerDTO.setCandidateContactNo(dbobj.getCandidateContactNo());
		interviewSchedulerDTO.setCandidateEmailId(dbobj.getCandidateEmailId());
		interviewSchedulerDTO.setPositionId(dbobj.getPosition().getPositionId());
		interviewSchedulerDTO.setRecuiterId(dbobj.getRecuiterId());
		interviewSchedulerDTO.setSourceOfProfile(dbobj.getSourceOfProfile());
		interviewSchedulerDTO.setUserId(dbobj.getUserId());
		interviewSchedulerDTO.setDateCreated(dbobj.getDateCreated());
		interviewSchedulerDTO.setUserIdUpdate(dbobj.getUserIdUpdate());
		interviewSchedulerDTO.setDateUpdated(dbobj.getDateUpdated());

		for (CandidateEvolution candidateEvolution : candidateEvolutionXRefList) {

			if (candidateEvolution.getPositionInterviewlevelXRef().getLevelId().equals(levelId) ) {
				CandidateEvolutionDTO candidateEvolutionDTO = new CandidateEvolutionDTO();
				candidateEvolutionDTO.setEvalutionId(candidateEvolution.getEvalutionId());
				candidateEvolutionDTO.setStatus(candidateEvolution.getStatus());
				if (candidateEvolution.getStatus() == null) {
					candidateEvolutionDTO.setInterviewMode(candidateEvolution.getInterviewMode());
					String fromTime = candidateEvolution.getInterviewTime();
					String[] timeArray = fromTime.split(":");

					if (timeArray[1].contains("PM")) {
						candidateEvolutionDTO.setInterviewTime((Integer.parseInt(timeArray[0]) + 12 + ":" + "00"));

					} else {

						candidateEvolutionDTO.setInterviewTime(timeArray[0] + ":" + "00");
					}

					candidateEvolutionDTO.setInterviewDate(candidateEvolution.getInterviewDate());
				}
				candidateEvolutionDTO.setLevelId(candidateEvolution.getPositionInterviewlevelXRef().getLevelId());
				candidateEvolutionDTO.setUserId(candidateEvolution.getUserId());
				candidateEvolutionDTO.setDateCreated(candidateEvolution.getDateCreated());

				candidateEvolutionDTOList.add(candidateEvolutionDTO);
			}
		}
		interviewSchedulerDTO.setCandidateEvolutionList(candidateEvolutionDTOList);
		return interviewSchedulerDTO;
	}

	public List<FinalCandidateEvolutionDTO> databaseModelToFinalCandidateEvolutionDTO(
			List<CandidateFinalEvalution> finalEvlList) {
		List<FinalCandidateEvolutionDTO> FinalCandidateEvolutionDTOList = new ArrayList<FinalCandidateEvolutionDTO>();
		for (CandidateFinalEvalution candidateFinalEvalution : finalEvlList) {
			FinalCandidateEvolutionDTO finalCandidateEvolutionDTO=new FinalCandidateEvolutionDTO();
			finalCandidateEvolutionDTO.setId(candidateFinalEvalution.getId());
			finalCandidateEvolutionDTO.setCandidateName(candidateFinalEvalution.getCandidateName());
			finalCandidateEvolutionDTO.setContactNo(candidateFinalEvalution.getContactNo());
			finalCandidateEvolutionDTO.setEmailId(candidateFinalEvalution.getEmailId());
			finalCandidateEvolutionDTO.setPositionId(candidateFinalEvalution.getPositionId());
			finalCandidateEvolutionDTO.setInterviewScheduledId(candidateFinalEvalution.getInterviewScheduledId());
			finalCandidateEvolutionDTO.setFinalStatus(candidateFinalEvalution.getFinalStatus());
			finalCandidateEvolutionDTO.setRecruiterName(candidateFinalEvalution.getRecruiterName());
			FinalCandidateEvolutionDTOList.add(finalCandidateEvolutionDTO);
		}
		return FinalCandidateEvolutionDTOList;
		
	}

}
