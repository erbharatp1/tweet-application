package com.csipl.hrms.service.recruitement.adaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.recruitment.PositionDTO;
import com.csipl.hrms.dto.recruitment.PositionInterviewlevelXRefDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.recruitment.Position;
import com.csipl.hrms.model.recruitment.PositionInterviewlevelXRef;
import com.csipl.hrms.service.adaptor.Adaptor;
import com.csipl.hrms.service.employee.EmployeePersonalInformationService;
import com.csipl.hrms.service.recruitement.PositionService;

@Component
public class PositionAdaptor implements Adaptor<PositionDTO, Position> {
    @Autowired
    EmployeePersonalInformationService employeePersonalInformationService;
    
	@Autowired
	private PositionService positionService;

    @Override
    public List<Position> uiDtoToDatabaseModelList(List<PositionDTO> positionDtoList) {

        return positionDtoList.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
    }

    @Override
    public List<PositionDTO> databaseModelToUiDtoList(List<Position> dbobj) {

        return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
    }

    @Override
    public Position uiDtoToDatabaseModel(PositionDTO uiobj) {

        Position position = new Position();

        position.setPositionId(uiobj.getPositionId());
        position.setPositionTitle(uiobj.getPositionTitle());
        position.setNoOfLevel(uiobj.getNoOfLevel());
        position.setGradeId(uiobj.getGradeId());

        String str1 = uiobj.getRequiredExperience();
        // In If-else statements you can use the contains() method

        if (str1.contains("Years") || str1.contains("Yrs")) {
            position.setRequiredExperience(uiobj.getRequiredExperience());

        } else {
            position.setRequiredExperience(uiobj.getRequiredExperience() + " Years");
        }

        //	position.setRequiredExperience(uiobj.getRequiredExperience());
        position.setEmployeementType(uiobj.getEmployeementType());
        position.setJobLocation(uiobj.getJobLocation());
        position.setNoOfPosition(uiobj.getNoOfPosition());
        position.setExpectedate(uiobj.getExpectedate());
        position.setMaxBudget(uiobj.getMaxBudget());
        position.setHiringManagerId(uiobj.getHiringManagerId());
        position.setJdId(uiobj.getJdId());
        position.setSourceOfPosion(uiobj.getSourceOfPosion());
        position.setApprovePersonId(uiobj.getApprovePersonId());
        position.setRemark(uiobj.getRemark());
        position.setExtraBudget(uiobj.getExtraBudget());
        position.setExtraBudgetRemark(uiobj.getExtraBudgetRemark());
        position.setUserId(uiobj.getUserId());
        position.setDateCreated(new Date());
        position.setInterviewType(uiobj.getInterviewType());
        position.setDateUpdated(uiobj.getDateUpdated());
        position.setUserIdUpdate(uiobj.getUserIdUpdate());
        position.setPositionStatus(uiobj.getPositionStatus());
        position.setPositionType(uiobj.getPositionType());
        position.setApprovalRemark(uiobj.getApprovalRemark());
        position.setNoOfInterviewLevel(uiobj.getNoOfInterviewLevel());

        if (uiobj.getPositionCode() != null) {
            position.setPositionCode(uiobj.getPositionCode());
        } else {
            int generateCode = ThreadLocalRandom.current().nextInt();
            String additionGererate = "" + generateCode;
            String generatePositionCode = additionGererate.substring(1, 5);
            String pCode = uiobj.getPositionTitle().substring(0, 3).toUpperCase() + generatePositionCode;
            String positionCode = pCode.substring(0, 7);
            position.setPositionCode(positionCode);
        }

        if (uiobj.getPositionId() != null && uiobj.getPositionId() != 0) {
            position.setPositionId(uiobj.getPositionId());
        }
        List<PositionInterviewlevelXRef> positionInterviewlevelXRefList = new ArrayList<PositionInterviewlevelXRef>();
        for (PositionInterviewlevelXRefDTO positionInterviewlevelXRefDTO : uiobj.getPositionInterviewlevelXrefs()) {
            PositionInterviewlevelXRef positionlevelXRef = null;
            positionlevelXRef = new PositionInterviewlevelXRef();
            positionlevelXRef.setContactNo(positionInterviewlevelXRefDTO.getContactNo());
            positionlevelXRef.setEmailId(positionInterviewlevelXRefDTO.getEmailId());
            positionlevelXRef.setExternalInterviewerName(positionInterviewlevelXRefDTO.getExternalInterviewerName());
            positionlevelXRef.setUserId(positionInterviewlevelXRefDTO.getUserId());
            positionlevelXRef.setDatecreated(new Date());
            positionlevelXRef.setInterviewLevelType(positionInterviewlevelXRefDTO.getInterviewLevelType());
            positionlevelXRef.setLevelId(positionInterviewlevelXRefDTO.getLevelId());
            positionlevelXRef.setLevelName(positionInterviewlevelXRefDTO.getLevelName());
            positionlevelXRef.setLevelIndex(positionInterviewlevelXRefDTO.getLevelIndex());
            positionlevelXRef.setInternalInterviewerId(positionInterviewlevelXRefDTO.getInternalInterviewerId());
            positionlevelXRef.setPosition(position);
            positionlevelXRef.setUserIdUpdated(positionInterviewlevelXRefDTO.getUserIdUpdated());
            positionInterviewlevelXRefList.add(positionlevelXRef);

        }
        position.setPositionInterviewlevelXrefs(positionInterviewlevelXRefList);
        return position;
    }

    @Override
    public PositionDTO databaseModelToUiDto(Position dbobj) {

        PositionDTO positionDTO = new PositionDTO();
        List<PositionInterviewlevelXRef> PositionInterviewlevelXRefList = dbobj.getPositionInterviewlevelXrefs();
        List<PositionInterviewlevelXRefDTO> positionInterviewlevelXRefDTOList = new ArrayList<PositionInterviewlevelXRefDTO>();
        positionDTO.setPositionId(dbobj.getPositionId());
        positionDTO.setPositionCode(dbobj.getPositionCode());
        positionDTO.setPositionTitle(dbobj.getPositionTitle());
        positionDTO.setNoOfLevel(dbobj.getNoOfLevel());
        positionDTO.setGradeId(dbobj.getGradeId());
        positionDTO.setInterviewType(dbobj.getInterviewType());
        positionDTO.setRequiredExperience(dbobj.getRequiredExperience());
        positionDTO.setEmployeementType(dbobj.getEmployeementType());
        positionDTO.setJobLocation(dbobj.getJobLocation());
        positionDTO.setNoOfPosition(dbobj.getNoOfPosition());
        positionDTO.setExpectedate(dbobj.getExpectedate());
        positionDTO.setMaxBudget(dbobj.getMaxBudget());
        positionDTO.setHiringManagerId(dbobj.getHiringManagerId());
        positionDTO.setJdId(dbobj.getJdId());
        positionDTO.setSourceOfPosion(dbobj.getSourceOfPosion());
        positionDTO.setApprovePersonId(dbobj.getApprovePersonId());
        positionDTO.setRemark(dbobj.getRemark());
        positionDTO.setExtraBudget(dbobj.getExtraBudget());
        positionDTO.setExtraBudgetRemark(dbobj.getExtraBudgetRemark());
        positionDTO.setUserId(dbobj.getUserId());
        positionDTO.setDateCreated(dbobj.getDateCreated());
        positionDTO.setDateUpdated(dbobj.getDateUpdated());
        positionDTO.setUserIdUpdate(dbobj.getUserIdUpdate());
        positionDTO.setPositionStatus(dbobj.getPositionStatus());
        positionDTO.setExtraBudgetStatus(dbobj.getExtraBudgetStatus());
        positionDTO.setApprovalRemark(dbobj.getApprovalRemark());
        positionDTO.setNoOfInterviewLevel(dbobj.getNoOfInterviewLevel());
        for (PositionInterviewlevelXRef positionInterviewlevel : PositionInterviewlevelXRefList) {

            PositionInterviewlevelXRefDTO positionlevelXRefDTO = new PositionInterviewlevelXRefDTO();
            positionlevelXRefDTO.setContactNo(positionInterviewlevel.getContactNo());
            positionlevelXRefDTO.setEmailId(positionInterviewlevel.getEmailId());
            positionlevelXRefDTO.setExternalInterviewerName(positionInterviewlevel.getExternalInterviewerName());
            positionlevelXRefDTO.setUserId(positionInterviewlevel.getUserId());
            positionlevelXRefDTO.setDatecreated(new Date());
            positionlevelXRefDTO.setInterviewLevelType(positionInterviewlevel.getInterviewLevelType());
            positionlevelXRefDTO.setLevelId(positionInterviewlevel.getLevelId());
            positionlevelXRefDTO.setLevelName(positionInterviewlevel.getLevelName());
            positionlevelXRefDTO.setLevelIndex(positionInterviewlevel.getLevelIndex());
            positionlevelXRefDTO.setInternalInterviewerId(positionInterviewlevel.getInternalInterviewerId());
            positionlevelXRefDTO.setUserIdUpdated(positionInterviewlevel.getUserIdUpdated());
            if (positionInterviewlevel.getInternalInterviewerId() != null) {
                Employee employee = employeePersonalInformationService.getEmployeeInfo(positionInterviewlevel.getInternalInterviewerId());
                if (employee != null) {
                    if (positionInterviewlevel.getInternalInterviewerId().equals(employee.getEmployeeId()))
                        positionlevelXRefDTO.setFullNameCodeValues(employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmployeeCode() + ")");
                    positionInterviewlevelXRefDTOList.add(positionlevelXRefDTO);
                }
            }
        }
        positionDTO.setPositionInterviewlevelXrefs(positionInterviewlevelXRefDTOList);
        return positionDTO;
    }

    public List<PositionDTO> databaseModelToUiDtoLists(List<Object[]> recruitersList) {
        List<PositionDTO> recruitersPositionList = new ArrayList<>();
        for (Object[] positionObj : recruitersList) {
            PositionDTO positionDto = new PositionDTO();

            Long employeeId = positionObj[0] != null ? Long.parseLong(positionObj[0].toString()) : null;
            String employeeName = positionObj[1] != null ? (String) positionObj[1] : null;

            positionDto.setEmployeeId(employeeId);
            positionDto.setEmployeeName(employeeName);

            recruitersPositionList.add(positionDto);
        }
        return recruitersPositionList;

    }

    public List<PositionDTO> databaseModelToUiPositionDto(List<Object[]> position) {

        List<PositionDTO> positionDTOList = new ArrayList<PositionDTO>();

        for (Object[] report : position) {

            PositionDTO positionDTO = new PositionDTO();

            String positionCode = report[0] != null ? (String) report[0] : null;
            String positionTitle = report[1] != null ? (String) report[1] : null;
            //	Long noOfLevel = report[2] != null ? Long.parseLong(report[2].toString()) : null;
            String noOfLevel = report[2] != null ? (String) report[2] : null;
            String requiredExperience = report[3] != null ? (String) report[3] : null;
            String jobLocation = report[4] != null ? (String) report[4] : null;
            Long noOfPosition = report[5] != null ? Long.parseLong(report[5].toString()) : null;
            Date expectedate = report[6] != null ? (Date) report[6] : null;
            BigDecimal maxBudget = report[7] != null ? (BigDecimal) report[7] : null;
            //		String sourceOfPosion = report[8] != null ? Long.parseLong(report[8].toString()) : null;
            String sourceOfPosion = report[8] != null ? (String) report[8] : null;
            BigDecimal extraBudget = report[9] != null ? (BigDecimal) report[9] : null;
            String employeeName = report[10] != null ? (String) report[10] : null;
            String gradeName = report[11] != null ? (String) report[11] : null;

            positionDTO.setPositionCode(positionCode);
            positionDTO.setPositionTitle(positionTitle);
            positionDTO.setNoOfLevel(noOfLevel);
            positionDTO.setRequiredExperience(requiredExperience);
            positionDTO.setJobLocation(jobLocation);
            positionDTO.setNoOfPosition(noOfPosition);
            positionDTO.setExpectedate(expectedate);
            positionDTO.setMaxBudget(maxBudget);
            positionDTO.setSourceOfPosion(sourceOfPosion);
            positionDTO.setExtraBudget(extraBudget);
            positionDTO.setEmployeeName(employeeName);
            positionDTO.setGradeName(gradeName);

            positionDTOList.add(positionDTO);
        }
        return positionDTOList;
    }

    public List<PositionDTO> databaseModelToUiFIndPositionDto(List<Object[]> position) {

        List<PositionDTO> positionDTOList = new ArrayList<PositionDTO>();

        for (Object[] report : position) {

            PositionDTO positionDTO = new PositionDTO();

            Long positionId = report[0] != null ? Long.parseLong(report[0].toString()) : null;
            String positionCode = report[1] != null ? (String) report[1] : null;
            String positionTitle = report[2] != null ? (String) report[2] : null;
            //	Long noOfLevel = report[3] != null ? Long.parseLong(report[3].toString()) : null;

            String noOfLevel = report[3] != null ? (String) report[3] : null;

            String requiredExperience = report[4] != null ? (String) report[4] : null;
            //	Long employeementType = report[5] != null ? Long.parseLong(report[5].toString()) : null;

            String employeementType = report[5] != null ? (String) report[5] : null;

            String jobLocation = report[6] != null ? (String) report[6] : null;
            Long noOfPosition = report[7] != null ? Long.parseLong(report[7].toString()) : null;
            Date expectedate = report[8] != null ? (Date) report[8] : null;
            BigDecimal maxBudget = report[9] != null ? (BigDecimal) report[9] : null;
            //	Long sourceOfPosion = report[10] != null ? Long.parseLong(report[10].toString()) : null;

            String sourceOfPosion = report[10] != null ? (String) report[10] : null;

            String interviewType = report[11] != null ? (String) report[11] : null;
            String remark = report[12] != null ? (String) report[12] : null;
            String positionStatus = report[13] != null ? (String) report[13] : null;

            BigDecimal extraBudget = report[14] != null ? (BigDecimal) report[14] : null;
            //	BigDecimal extraBudgetStatus = report[15] != null ? (BigDecimal) report[15] : null;
            String gradeName = report[16] != null ? (String) report[16] : null;
            String userName = report[17] != null ? (String) report[17] : null;

            String userNameCode = report[18] != null ? (String) report[18] : null;
            Date datecreated = report[19] != null ? (Date) report[19] : null;
            String hiringMangerName = report[20] != null ? (String) report[20] : null;
            String hiringMangerCode = report[21] != null ? (String) report[21] : null;

            String approvalPersonName = report[22] != null ? (String) report[22] : null;
            String approvalPersonCode = report[23] != null ? (String) report[23] : null;

            positionDTO.setPositionId(positionId);
            positionDTO.setPositionCode(positionCode);
            positionDTO.setPositionTitle(positionTitle);
            positionDTO.setNoOfLevel(noOfLevel);
            positionDTO.setRequiredExperience(requiredExperience);
            positionDTO.setJobLocation(jobLocation);
            positionDTO.setNoOfPosition(noOfPosition);
            positionDTO.setExpectedate(expectedate);
            positionDTO.setMaxBudget(maxBudget);
            positionDTO.setSourceOfPosion(sourceOfPosion);
            positionDTO.setExtraBudget(extraBudget);
            positionDTO.setRemark(remark);
            positionDTO.setExtraBudget(extraBudget);
            positionDTO.setEmployeementType(employeementType);
            positionDTO.setUserName(userName);
            positionDTO.setUserNameCode(userNameCode);
            positionDTO.setHiringMangerName(hiringMangerName);
            positionDTO.setHiringMangerCode(hiringMangerCode);
            positionDTO.setGradeName(gradeName);

            positionDTO.setUserNameCode(userNameCode);
            positionDTO.setDateCreated(datecreated);
            positionDTO.setInterviewType(interviewType);
            positionDTO.setPositionStatus(positionStatus);

            positionDTO.setApprovalPersonName(approvalPersonName);
            positionDTO.setApprovalPersonCode(approvalPersonCode);

            positionDTOList.add(positionDTO);
        }
        return positionDTOList;
    }

    public List<PositionDTO> databaseModelToUiDtoLevelLists(List<Object[]> levelList) {
        List<PositionDTO> levelPositionList = new ArrayList<>();
        for (Object[] positionObj : levelList) {
            PositionDTO positionDto = new PositionDTO();

            Long levelId = positionObj[0] != null ? Long.parseLong(positionObj[0].toString()) : null;
            String levelName = positionObj[1] != null ? (String) positionObj[1] : null;

            positionDto.setLevelId(levelId);
            positionDto.setLevelName(levelName);

            levelPositionList.add(positionDto);
        }
        return levelPositionList;
    }


    public List<PositionDTO> databaseModelToUiDtoPositionLists(List<Object[]> allPositionList) {
        // TODO Auto-generated method stub
        List<PositionDTO> positionList = new ArrayList<>();
        for (Object[] positionObj : allPositionList) {
            PositionDTO positionDto = new PositionDTO();

            Long positionId = positionObj[0] != null ? Long.parseLong(positionObj[0].toString()) : null;
            String positionTitle = positionObj[1] != null ? (String) positionObj[1] : null;

            positionDto.setPositionId(positionId);
            positionDto.setPositionTitle(positionTitle);

            positionList.add(positionDto);
        }
        return positionList;
    }

    public PositionDTO databaseModelToUiPositionExtraBudgetDto(List<Object[]> position) {

        PositionDTO positionDTO = new PositionDTO();

        for (Object[] report : position) {

            Long positionId = report[0] != null ? Long.parseLong(report[0].toString()) : null;
            String positionCode = report[1] != null ? (String) report[1] : null;
            String positionTitle = report[2] != null ? (String) report[2] : null;
            String requiredExperience = report[3] != null ? (String) report[3] : null;
            String jobLocation = report[4] != null ? (String) report[4] : null;
            //	Long noOfLevel = report[5] != null ? Long.parseLong(report[5].toString()) : null;

            String noOfLevel = report[5] != null ? (String) report[5] : null;

            String gradeName = report[6] != null ? (String) report[6] : null;
            BigDecimal maxBudget = report[7] != null ? (BigDecimal) report[7] : null;
            Long noOfPosition = report[8] != null ? Long.parseLong(report[8].toString()) : null;
            Date expectedate = report[9] != null ? (Date) report[9] : null;
            BigDecimal extraBudget = report[10] != null ? (BigDecimal) report[10] : null;
            String employeeName = report[11] != null ? (String) report[11] : null;

            String sourceOfPosion = report[12] != null ? (String) report[12] : null;

            //	Long sourceOfPosion = report[12] != null ? Long.parseLong(report[12].toString()) : null;
            String remark = report[13] != null ? (String) report[13] : null;
            Date datecreated = report[14] != null ? (Date) report[14] : null;
            Long gradeId = report[15] != null ? Long.parseLong(report[15].toString()) : null;
            Long hiringManagerId = report[16] != null ? Long.parseLong(report[16].toString()) : null;
            //Long employeementType = report[17] != null ? Long.parseLong(report[17].toString()) : null;

            String employeementType = report[17] != null ? (String) report[17] : null;

            String extraBudgetRemark = report[18] != null ? (String) report[18] : null;
            Long userId = report[19] != null ? Long.parseLong(report[19].toString()) : null;
            Long JdId = report[20] != null ? Long.parseLong(report[20].toString()) : null;
            Long approvePersonId = report[21] != null ? Long.parseLong(report[21].toString()) : null;
            String interviewType = report[22] != null ? (String) report[22] : null;
            String extraBudgetStatus = report[23] != null ? (String) report[23] : null;
            String positionStatus = report[24] != null ? (String) report[24] : null;
            String positionType = report[25] != null ? (String) report[25] : null;
            String levelName = report[26] != null ? (String) report[26] : null;

            positionDTO.setPositionId(positionId);
            positionDTO.setPositionCode(positionCode);
            positionDTO.setPositionTitle(positionTitle);
            positionDTO.setNoOfLevel(noOfLevel);
            positionDTO.setRequiredExperience(requiredExperience);
            positionDTO.setJobLocation(jobLocation);
            positionDTO.setNoOfPosition(noOfPosition);
            positionDTO.setExpectedate(expectedate);
            positionDTO.setMaxBudget(maxBudget);
            positionDTO.setSourceOfPosion(sourceOfPosion);
            positionDTO.setExtraBudget(extraBudget);
            positionDTO.setEmployeeName(employeeName);
            positionDTO.setGradeName(gradeName);
            positionDTO.setRemark(remark);
            positionDTO.setDateCreated(datecreated);
            positionDTO.setGradeId(gradeId);
            positionDTO.setHiringManagerId(hiringManagerId);
            positionDTO.setLevelName(levelName);
            positionDTO.setEmployeementType(employeementType);

            if (employeementType.equals(1l)) positionDTO.setEmpType("Trainee");
            else if (employeementType.equals(2l)) positionDTO.setEmpType("On Roll");
            else if (employeementType.equals(3l)) positionDTO.setEmpType("Off Roll");
            else if (employeementType.equals(4l)) positionDTO.setEmpType("Apprentship");

            positionDTO.setExtraBudget(extraBudget);
            positionDTO.setExtraBudgetRemark(extraBudgetRemark);
            positionDTO.setUserId(userId);
            positionDTO.setJdId(JdId);
            positionDTO.setApprovePersonId(approvePersonId);
            positionDTO.setInterviewType(interviewType);
            positionDTO.setExtraBudgetStatus(extraBudgetStatus);
            positionDTO.setPositionStatus(positionStatus);
            positionDTO.setPositionType(positionType);
            break;
        }
        return positionDTO;
    }

    public Position uiDtoToDatabaseModelForBudget(PositionDTO uiobj) {

        Position position = new Position();

        position.setPositionId(uiobj.getPositionId());
        position.setExtraBudget(uiobj.getExtraBudget());
        position.setExtraBudgetRemark(uiobj.getExtraBudgetRemark());
        position.setDateUpdated(uiobj.getDateUpdated());
        position.setUserIdUpdate(uiobj.getUserIdUpdate());
        position.setExtraBudgetStatus(uiobj.getExtraBudgetStatus());
        return position;
    }

    public Position uiDtoToDatabaseModelExtraBudgetStatus(PositionDTO positionDTO) {
        Position position = new Position();
        position.setPositionId(positionDTO.getPositionId());
        position.setExtraBudgetStatus(positionDTO.getExtraBudgetStatus());
        position.setExtraBudgetApprovalRemark(positionDTO.getExtraBudgetApprovalRemark());
        position.setUserIdUpdate(positionDTO.getUserIdUpdate());
        position.setDateUpdated(positionDTO.getDateUpdated());
        return position;
    }

    public PositionDTO databaseModelToUiPositionExtraBudgetWithLevelDto(PositionDTO positionData, Position noOfLevel) {

        List<PositionInterviewlevelXRef> PositionInterviewlevelXRefList = noOfLevel.getPositionInterviewlevelXrefs();
        List<PositionInterviewlevelXRefDTO> positionInterviewlevelXRefDTOList = new ArrayList<PositionInterviewlevelXRefDTO>();
        for (PositionInterviewlevelXRef positionInterviewlevel : PositionInterviewlevelXRefList) {
            PositionInterviewlevelXRefDTO positionlevelXRefDTO = new PositionInterviewlevelXRefDTO();
            positionlevelXRefDTO.setContactNo(positionInterviewlevel.getContactNo());
            positionlevelXRefDTO.setEmailId(positionInterviewlevel.getEmailId());
            positionlevelXRefDTO.setExternalInterviewerName(positionInterviewlevel.getExternalInterviewerName());
            positionlevelXRefDTO.setUserId(positionInterviewlevel.getUserId());
            positionlevelXRefDTO.setDatecreated(new Date());
            positionlevelXRefDTO.setInterviewLevelType(positionInterviewlevel.getInterviewLevelType());
            positionlevelXRefDTO.setLevelId(positionInterviewlevel.getLevelId());
            positionlevelXRefDTO.setLevelName(positionInterviewlevel.getLevelName());
            // positionlevelXRefDTO.setPositionDTO(positionDTO);
            positionlevelXRefDTO.setUserIdUpdated(positionInterviewlevel.getUserIdUpdated());

            positionInterviewlevelXRefDTOList.add(positionlevelXRefDTO);
        }
        positionData.setPositionInterviewlevelXrefs(positionInterviewlevelXRefDTOList);
        return positionData;
    }

	public List<PositionDTO> databaseModelToUiDtoPositionCountLists(List<Object[]> allPositionList) {
		// TODO Auto-generated method stub
		List<PositionDTO> positionList = new ArrayList<>();
		List<Object[]> IdCountList = new ArrayList<>();
		for (Object[] positionObj : allPositionList) {
			
			Long positionId = positionObj[0] != null ? Long.parseLong(positionObj[0].toString()) : null;
			List<Object[]> allPositionCountList=positionService.getAllPositionCountList(positionId);
			
			
			for(Object[] positionCountObj : allPositionCountList)
			{
				Long finalCount = positionCountObj[4] != null ? Long.parseLong(positionCountObj[4].toString()): null;
				
				if (finalCount!=0L) {
					//Long positionCountId = positionCountObj[0] != null ? Long.parseLong(positionCountObj[0].toString()): null;
				    IdCountList.add(positionCountObj);	
				}
				}
			}
			
		for (Object[] positionObj : IdCountList) {
			PositionDTO positionDto = new PositionDTO();
			Long positionId = positionObj[0] != null ? Long.parseLong(positionObj[0].toString()) : null;
			String positionTitle = positionObj[1] != null ? (String) positionObj[1] : null;

			positionDto.setPositionId(positionId);
			positionDto.setPositionTitle(positionTitle);
			positionList.add(positionDto);
		}
			

		return positionList;
	}

	public List<PositionDTO> databaseModelToUiDtoListNext(List<Position> positionApprovalPerson) {
		List<PositionDTO> positionDtoLIst = new ArrayList<PositionDTO>();
		for (Position position : positionApprovalPerson) {
			List<Object[]> allPositionCountList = positionService.getAllPositionCountList(position.getPositionId());
			for (Object[] positionCountObj : allPositionCountList) {
				Long finalCount = positionCountObj[4] != null ? Long.parseLong(positionCountObj[4].toString()) : null;
				if (finalCount != 0L) {
					positionDtoLIst.add(databaseModelToUiDto(position));
				}
			}
		}
		return positionDtoLIst;
	}

}



