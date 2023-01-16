package com.csipl.hrms.service.recruitement.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.hrms.dto.recruitment.InterviewLevelDTO;
import com.csipl.hrms.dto.recruitment.InterviewSchedulerDTO;
import com.csipl.hrms.dto.recruitment.PositionAllocationXrefDTO;
import com.csipl.hrms.dto.recruitment.PositionDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.model.recruitment.PositionAllocationXref;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class PositionAllocationAdaptor implements Adaptor<PositionAllocationXrefDTO, PositionAllocationXref> {

	@Override
	public List<PositionAllocationXref> uiDtoToDatabaseModelList(List<PositionAllocationXrefDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<PositionAllocationXrefDTO> databaseModelToUiDtoList(List<PositionAllocationXref> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public PositionAllocationXref uiDtoToDatabaseModel(PositionAllocationXrefDTO uiobj) {

		PositionAllocationXref positionAllocationXref = new PositionAllocationXref();

		positionAllocationXref.setPositionAllocationId(uiobj.getPositionAllocationId());

		Position position = new Position();
		position.setPositionId(uiobj.getPositionId());
		positionAllocationXref.setPosition(position);

		positionAllocationXref.setNoOfPosition(uiobj.getNoOfPosition());

		Employee employee = new Employee();
		employee.setEmployeeId(uiobj.getRecruiterEmployeeId());
		positionAllocationXref.setEmployee(employee);

		positionAllocationXref.setUserId(uiobj.getUserId());
		positionAllocationXref.setDatecreated(uiobj.getDatecreated());

		return positionAllocationXref;
	}

	@Override
	public PositionAllocationXrefDTO databaseModelToUiDto(PositionAllocationXref dbobj) {

		PositionAllocationXrefDTO positionAllocationXrefDTO = new PositionAllocationXrefDTO();

		positionAllocationXrefDTO.setPositionAllocationId(dbobj.getPositionAllocationId());

		positionAllocationXrefDTO.setNoOfPosition(dbobj.getNoOfPosition());

		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPositionId(positionDTO.getPositionId());
		positionAllocationXrefDTO.setPositionId(dbobj.getPosition().getPositionId());

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeId(employeeDTO.getEmployeeId());
		positionAllocationXrefDTO.setRecruiterEmployeeId(dbobj.getEmployee().getEmployeeId());

		positionAllocationXrefDTO.setUserId(dbobj.getUserId());
		positionAllocationXrefDTO.setDatecreated(dbobj.getDatecreated());

		return positionAllocationXrefDTO;
	}

	public List<PositionAllocationXrefDTO> databaseModelToUiRecentPositionDto(List<Object[]> positionAllocationList) {

		List<PositionAllocationXrefDTO> recentPositionList = new ArrayList<PositionAllocationXrefDTO>();

		for (Object[] positionObj : positionAllocationList) {

			PositionAllocationXrefDTO positionAllocationXrefDTO = new PositionAllocationXrefDTO();

			String employeeName = positionObj[0] != null ? (String) positionObj[0] : null;
			String employeeCode = positionObj[1] != null ? (String) positionObj[1] : null;
			Long noOfPosition = positionObj[2] != null ? Long.parseLong(positionObj[2].toString()) : null;
			String positionTitle = positionObj[3] != null ? (String) positionObj[3] : null;
			String positionCode = positionObj[4] != null ? (String) positionObj[4] : null;

			positionAllocationXrefDTO.setEmployeeName(employeeName);
			positionAllocationXrefDTO.setEmployeeCode(employeeCode);
			positionAllocationXrefDTO.setNoOfPosition(noOfPosition);
			positionAllocationXrefDTO.setPositionTitle(positionTitle);
			positionAllocationXrefDTO.setPositionCode(positionCode);

			recentPositionList.add(positionAllocationXrefDTO);
		}
		return recentPositionList;
	}

	public List<PositionAllocationXrefDTO> databaseModelToUiAssignedPositionList(List<Object[]> positionAllocationList,
			List<Object[]> closedAllocations) {

		List<PositionAllocationXrefDTO> recentPositionList = new ArrayList<PositionAllocationXrefDTO>();

		Long closedCount = 0l;
		Long pendingCount = 0l;
		for (Object[] positionObj : positionAllocationList) {

			PositionAllocationXrefDTO positionAllocationXrefDTO = new PositionAllocationXrefDTO();

			String positionCode = positionObj[0] != null ? (String) positionObj[0] : null;
			String positionTitle = positionObj[1] != null ? (String) positionObj[1] : null;
			String requiredExperience = positionObj[2] != null ? (String) positionObj[2] : null;
			String jobLocation = positionObj[3] != null ? (String) positionObj[3] : null;
			String employmentType = positionObj[4] != null ? (String) positionObj[4] : null;
			String sourceOfPosion = positionObj[5] != null ? (String) positionObj[5] : null;
			String employeeName = positionObj[6] != null ? (String) positionObj[6] : null;
			String employeeCode = positionObj[7] != null ? (String) positionObj[7] : null;
			Long noOfPosition = positionObj[8] != null ? Long.parseLong(positionObj[8].toString()) : null;
			Long positionId = positionObj[9] != null ? Long.parseLong(positionObj[9].toString()) : null;
			Long positionAllocationId = positionObj[10] != null ? Long.parseLong(positionObj[10].toString()) : null;

			for (Object[] positionAllocationObj : closedAllocations) {

				Long positionnId = positionAllocationObj[0] != null
						? Long.parseLong(positionAllocationObj[0].toString())
						: null;

				String recruiterName = positionAllocationObj[1] != null ? (String) positionAllocationObj[1] : null;

				if (positionId.equals(positionnId)) {
					if (employeeName.equals(recruiterName)) {
						closedCount++;
						pendingCount = noOfPosition - closedCount;
					}
				}
			}
			System.out.println("----closedCount----" + closedCount + "------pendingCount-------" + pendingCount);

			positionAllocationXrefDTO.setClosedAllocationCount(closedCount);
			positionAllocationXrefDTO.setPendingAllocationCount(pendingCount);
			closedCount = 0l;
			pendingCount = 0l;
			positionAllocationXrefDTO.setEmployeeName(employeeName);
			positionAllocationXrefDTO.setEmployeeCode(employeeCode);
			positionAllocationXrefDTO.setNoOfPosition(noOfPosition);
			positionAllocationXrefDTO.setPositionTitle(positionTitle);
			positionAllocationXrefDTO.setPositionCode(positionCode);
			positionAllocationXrefDTO.setJobLocation(jobLocation);
			positionAllocationXrefDTO.setRequiredExperience(requiredExperience);
			positionAllocationXrefDTO.setPositionId(positionId);
			positionAllocationXrefDTO.setPositionAllocationId(positionAllocationId);

			positionAllocationXrefDTO.setEmpType(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(), employmentType));

			if (sourceOfPosion.equals("SN")) {
				positionAllocationXrefDTO.setPosition("New");
			} else if (sourceOfPosion.equals("SE")) {
				positionAllocationXrefDTO.setPosition("Existing");
			} else if (sourceOfPosion.equals("SA")) {
				positionAllocationXrefDTO.setPosition("Additional");
			}

			recentPositionList.add(positionAllocationXrefDTO);
		}
		return recentPositionList;
	}

	public List<PositionAllocationXrefDTO> databaseModelToUiPositionDto(List<Object[]> position) {

		List<PositionAllocationXrefDTO> positionDTOList = new ArrayList<PositionAllocationXrefDTO>();

		for (Object[] report : position) {

			PositionAllocationXrefDTO positionDTO = new PositionAllocationXrefDTO();

			String positionCode = report[0] != null ? (String) report[0] : null;
			String positionTitle = report[1] != null ? (String) report[1] : null;
			// Long noOfLevel = report[2] != null ? Long.parseLong(report[2].toString()) :
			// null;
			String noOfLevel = report[2] != null ? (String) report[2] : null;
			String requiredExperience = report[3] != null ? (String) report[3] : null;
			String jobLocation = report[4] != null ? (String) report[4] : null;
			Long noOfPosition = report[5] != null ? Long.parseLong(report[5].toString()) : null;
			Date expectedate = report[6] != null ? (Date) report[6] : null;
			BigDecimal maxBudget = report[7] != null ? (BigDecimal) report[7] : null;
			// Long sourceOfPosion = report[8] != null ?
			// Long.parseLong(report[8].toString()) : null;
			String sourceOfPosion = report[8] != null ? (String) report[8] : null;
			BigDecimal extraBudget = report[9] != null ? (BigDecimal) report[9] : null;
			String employeeName = report[10] != null ? (String) report[10] : null;
			String gradeName = report[11] != null ? (String) report[11] : null;
			String interviewType = report[12] != null ? (String) report[12] : null;
			String levelName = report[13] != null ? (String) report[13] : null;
			// Long employeementType = report[14] != null ?
			// Long.parseLong(report[14].toString()) : null;
			String employmentType = report[14] != null ? (String) report[14] : null;

			positionDTO.setPositionCode(positionCode);
			positionDTO.setPositionTitle(positionTitle);
			// positionDTO.setNoOfLevel(noOfLevel);
//			positionDTO.setNoOfLevel(DropDownCache.getInstance()
//					.getDropDownValue(DropDownEnum.positionLavel.getDropDownName(), noOfLevel));
			if (noOfLevel.equals("MI")) {
				positionDTO.setNoOfLevel("Middle-Level");
			} else if (noOfLevel.equals("EN")) {
				positionDTO.setNoOfLevel("Entry Level");
			} else if (noOfLevel.equals("EX")) {
				positionDTO.setNoOfLevel("Experienced Level");
			} else if (noOfLevel.equals("TO")) {
				positionDTO.setNoOfLevel("Top-Level");
			}

			positionDTO.setRequiredExperience(requiredExperience);
			positionDTO.setJobLocation(jobLocation);
			positionDTO.setNoOfPosition(noOfPosition);
			positionDTO.setExpectedate(expectedate);
			positionDTO.setMaxBudget(maxBudget);
			positionDTO.setPosition(sourceOfPosion);
			positionDTO.setExtraBudget(extraBudget);
			positionDTO.setEmployeeName(employeeName);
			positionDTO.setGradeName(gradeName);
			positionDTO.setLevelName(levelName);

			if (interviewType.equalsIgnoreCase("IN")) {
				positionDTO.setInterviewType("Internal");
			} else if (interviewType.equalsIgnoreCase("EX")) {
				positionDTO.setInterviewType("External");
			}

			positionDTO.setEmpType(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(), employmentType));

//			positionDTO.setPosition(DropDownCache.getInstance()
//			.getDropDownValue(DropDownEnum.SourceOfPosition.getDropDownName(), sourceOfPosion));

//			if (employeementType.equals(1l)) {
//				positionDTO.setEmpType("Trainee");
//			} else if (employeementType.equals(2l)) {
//				positionDTO.setEmpType("On Roll");
//			} else if (employeementType.equals(3l)) {
//				positionDTO.setEmpType("Off Roll");
//			} else if (employeementType.equals(4l)) {
//				positionDTO.setEmpType("Apprentship");
//			}
//
			if (sourceOfPosion.equals("SN")) {
				positionDTO.setPosition("New");
			} else if (sourceOfPosion.equals("SE")) {
				positionDTO.setPosition("Existing");
			} else if (sourceOfPosion.equals("SA")) {
				positionDTO.setPosition("Additional");
			}

			positionDTOList.add(positionDTO);
		}
		return positionDTOList;
	}

	public List<PositionAllocationXrefDTO> databaseModelToUiPositionAllocationDto(List<Object[]> position) {

		List<PositionAllocationXrefDTO> positionDTOList = new ArrayList<PositionAllocationXrefDTO>();

		for (Object[] report : position) {

			PositionAllocationXrefDTO positionDTO = new PositionAllocationXrefDTO();

			String positionCode = report[0] != null ? (String) report[0] : null;
			String positionTitle = report[1] != null ? (String) report[1] : null;
			// Long noOfLevel = report[2] != null ? Long.parseLong(report[2].toString()) :
			// null;
			String noOfLevel = report[2] != null ? (String) report[2] : null;
			String requiredExperience = report[3] != null ? (String) report[3] : null;
			String jobLocation = report[4] != null ? (String) report[4] : null;
			Long noOfPosition = report[5] != null ? Long.parseLong(report[5].toString()) : null;
			Date expectedate = report[6] != null ? (Date) report[6] : null;
			BigDecimal maxBudget = report[7] != null ? (BigDecimal) report[7] : null;
			// Long sourceOfPosion = report[8] != null ?
			// Long.parseLong(report[8].toString()) : null;
			String sourceOfPosion = report[8] != null ? (String) report[8] : null;
			BigDecimal extraBudget = report[9] != null ? (BigDecimal) report[9] : null;
			String employeeName = report[10] != null ? (String) report[10] : null;
			String gradeName = report[11] != null ? (String) report[11] : null;
			String recruiterName = report[12] != null ? (String) report[12] : null;
			Long positionAssigned = report[13] != null ? Long.parseLong(report[13].toString()) : null;
			Long recruiterEmployeeId = report[14] != null ? Long.parseLong(report[14].toString()) : null;
			String interviewType = report[15] != null ? (String) report[15] : null;
			String levelName = report[16] != null ? (String) report[16] : null;
			// Long employeementType = report[17] != null ?
			// Long.parseLong(report[17].toString()) : null;
			String employeementType = report[17] != null ? (String) report[17] : null;

			positionDTO.setPositionCode(positionCode);
			positionDTO.setPositionTitle(positionTitle);
			// positionDTO.setNoOfLevel(noOfLevel);
//			positionDTO.setNoOfLevel(DropDownCache.getInstance()
//					.getDropDownValue(DropDownEnum.positionLavel.getDropDownName(), noOfLevel));
			if (noOfLevel.equals("MI")) {
				positionDTO.setNoOfLevel("Middle-Level");
			} else if (noOfLevel.equals("EN")) {
				positionDTO.setNoOfLevel("Entry Level");
			} else if (noOfLevel.equals("EX")) {
				positionDTO.setNoOfLevel("Experienced Level");
			} else if (noOfLevel.equals("TO")) {
				positionDTO.setNoOfLevel("Top-Level");
			}
			positionDTO.setRequiredExperience(requiredExperience);
			positionDTO.setJobLocation(jobLocation);
			positionDTO.setNoOfPosition(noOfPosition);
			positionDTO.setExpectedate(expectedate);
			positionDTO.setMaxBudget(maxBudget);
			// positionDTO.setPosition(sourceOfPosion);
			positionDTO.setExtraBudget(extraBudget);
			positionDTO.setEmployeeName(employeeName);
			positionDTO.setGradeName(gradeName);
			positionDTO.setRecruiterName(recruiterName);
			positionDTO.setPositionAssigned(positionAssigned);
			positionDTO.setRecruiterEmployeeId(recruiterEmployeeId);
			if (interviewType.equalsIgnoreCase("IN")) {
				positionDTO.setInterviewType("Internal");
			} else if (interviewType.equalsIgnoreCase("EX")) {
				positionDTO.setInterviewType("External");
			}

			positionDTO.setLevelName(levelName);

			positionDTO.setEmpType(DropDownCache.getInstance()
					.getDropDownValue(DropDownEnum.EmploymentType.getDropDownName(), employeementType));
//			if (employeementType.equals(1l)) {
//				positionDTO.setEmpType("Trainee");
//			} else if (employeementType.equals(2l)) {
//				positionDTO.setEmpType("On Roll");
//			} else if (employeementType.equals(3l)) {
//				positionDTO.setEmpType("Off Roll");
//			} else if (employeementType.equals(4l)) {
//				positionDTO.setEmpType("Apprentship");
//			}

			if (sourceOfPosion.equals("SN")) {
				positionDTO.setPosition("New");
			} else if (sourceOfPosion.equals("SE")) {
				positionDTO.setPosition("Existing");
			} else if (sourceOfPosion.equals("SA")) {
				positionDTO.setPosition("Additional");
			}

			positionDTOList.add(positionDTO);
		}
		return positionDTOList;
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
			Long gradeId = assesment[15] != null ? Long.parseLong(assesment[15].toString()) : null;
			Long finalEvolutionId = assesment[16] != null ? Long.parseLong(assesment[16].toString()) : null;

			if (finalStatus.equals("P")) {
				interviewSchedulerDTO.setFinalStatus("Pass");
			} else if (finalStatus.equals("R")) {
				interviewSchedulerDTO.setFinalStatus("Rejected");
			} else {
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
			interviewSchedulerDTO.setGradeId(gradeId);
			interviewSchedulerDTO.setFinalEvolutionId(finalEvolutionId);
			// interviewSchedulerDTO.setNoOfLevel(noOfLevel);

			if (noOfLevel.equals("EN")) {
				interviewSchedulerDTO.setNoOfLevel("Entry Level");
			} else if (noOfLevel.equals("EX")) {
				interviewSchedulerDTO.setNoOfLevel("Experienced Level");
			} else if (noOfLevel.equals("MI")) {
				interviewSchedulerDTO.setNoOfLevel("Middle-Level");
			} else if (noOfLevel.equals("TO")) {
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

			interviewLevelDTO.setInterviewerName(interviewerName);
			i++;
			String levels = "L" + i;
			interviewLevelDTO.setLevel(levels);
			InterviewLevelDTOList.add(interviewLevelDTO);
		}
		interviewSchedulerDTO.setInterviewLevelDTOList(InterviewLevelDTOList);
		return interviewSchedulerDTO;
	}

}
