package com.csipl.hrms.service.adaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.employee.EmployeeLetterDTO;
import com.csipl.hrms.dto.employee.EmployeeLettersTransactionDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.model.employee.EmployeeLetter;
import com.csipl.hrms.model.employee.EmployeeLettersTransaction;
import com.csipl.hrms.model.employee.Letter;
import com.csipl.hrms.service.employee.EmployeeLettersTransactionService;
import com.csipl.hrms.service.employee.repository.ApprovalHierarchyMasterRepository;

@Component
public class EmployeeLetterAdaptor implements Adaptor<EmployeeLetterDTO, EmployeeLetter> {
	@Autowired
	// EmployeeLettersTransactionAdaptor empLettersTransactionAdaptor;
	EmployeeLettersTransactionService empLettersTransactionService;
	@Autowired
	ApprovalHierarchyMasterRepository approvalHierarchyMasterRepository;
	
	@Override
	public List<EmployeeLetter> uiDtoToDatabaseModelList(List<EmployeeLetterDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeLetterDTO> databaseModelToUiDtoList(List<EmployeeLetter> dbobj) {
		// TODO Auto-generated method stub
		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public EmployeeLetter uiDtoToDatabaseModel(EmployeeLetterDTO empDTO) {
		EmployeeLetter empLtr = new EmployeeLetter();
		Letter letter = new Letter();
		letter.setLetterId(empDTO.getLetterId());
		empLtr.setActiveStatus(empDTO.getActiveStatus());
		empLtr.setDateCreated(empDTO.getDateCreated());
		empLtr.setDateUpdate(empDTO.getDateUpdate());
		empLtr.setEmpId(empDTO.getEmpId());
		empLtr.setEmpLetterId(empDTO.getEmpLetterId());
		empLtr.setEmpStatus(empDTO.getEmpStatus());
		empLtr.setHRStatus(empDTO.getHRStatus());
		empLtr.setLetterId(empDTO.getLetterId());
		empLtr.setLetterDecription(empDTO.getLetterDecription());
		empLtr.setUserId(empDTO.getUserId());
		empLtr.setUserIdUpdate(empDTO.getUserIdUpdate());
		empLtr.setRealeseStatus(empDTO.getRealeseStatus());
		
		empLtr.setDeclarationStatus(empDTO.getDeclarationStatus());
		empLtr.setDeclarationDate(empDTO.getDeclarationDate());
		
		return empLtr;
	}

	@Override
	public EmployeeLetterDTO databaseModelToUiDto(EmployeeLetter empLetter) {
		EmployeeLetterDTO empLetterDTO = new EmployeeLetterDTO();
		ApprovalHierarchyMaster master= approvalHierarchyMasterRepository.getMasterApprovalStatus(1L, empLetter.getLetterId());
		empLetterDTO.setActiveStatus(empLetter.getActiveStatus());
		empLetterDTO.setDateCreated(empLetter.getDateCreated());
		empLetterDTO.setDateUpdate(empLetter.getDateUpdate());
		empLetterDTO.setEmpId(empLetter.getEmpId());
		empLetterDTO.setEmpLetterId(empLetter.getEmpLetterId());
		empLetterDTO.setEmpStatus(empLetter.getEmpStatus());
		empLetterDTO.setHRStatus(empLetter.getHRStatus());
		empLetterDTO.setLetterId(empLetter.getLetterId());
		empLetterDTO.setLetterDecription(empLetter.getLetterDecription());
		empLetterDTO.setUserId(empLetter.getUserId());
		empLetterDTO.setUserIdUpdate(empLetter.getUserIdUpdate());
		empLetterDTO.setRealeseStatus(empLetter.getRealeseStatus());
		empLetterDTO.setApprovalHierarchyStatus(master.getActiveStatus());
		empLetterDTO.setDeclarationStatus(empLetter.getDeclarationStatus());
		empLetterDTO.setDeclarationDate(empLetter.getDeclarationDate());
		
		List<EmployeeLettersTransactionDTO> empTransactionList = databaseModelToUiDtoListTx(
				empLettersTransactionService.findAllApprovalStatus(1L, empLetter.getLetterId(), empLetter.getEmpId()));
		empLetterDTO.setEmpTransactionList(empTransactionList);
		return empLetterDTO;
	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeDocumentViewList(
			List<Object[]> pendingEmployeeDocumentViewList) {
		// TODO Auto-generated method stub
		List<EmployeeLetterDTO> employeeDocumentDTOList = new ArrayList<EmployeeLetterDTO>();

		for (Object[] employeeDocument : pendingEmployeeDocumentViewList) {
			EmployeeLetterDTO employeeDocumentDto = new EmployeeLetterDTO();

			Long employeeId = employeeDocument[0] != null ? Long.parseLong(employeeDocument[0].toString()) : null;
			String firstName = employeeDocument[1] != null ? (String) employeeDocument[1] : null;
			String lastName = employeeDocument[2] != null ? (String) employeeDocument[2] : null;
			String designationName = employeeDocument[3] != null ? (String) employeeDocument[3] : null;
			String employeeCode = employeeDocument[4] != null ? (String) employeeDocument[4] : null;
			String departmentName = employeeDocument[5] != null ? (String) employeeDocument[5] : null;
			Date dateOfBirth = employeeDocument[6] != null ? (Date) employeeDocument[6] : null;
			String letterType = employeeDocument[7] != null ? (String) employeeDocument[7] : null;
			Long letterId = employeeDocument[8] != null ? Long.parseLong(employeeDocument[8].toString()) : null;
			Long empLetterId = employeeDocument[9] != null ? Long.parseLong(employeeDocument[9].toString()) : null;
			String letterDecription = employeeDocument[10] != null ? (String) employeeDocument[10] : null;
			Date dateUpdate = employeeDocument[11] != null ? (Date) employeeDocument[11] : null;
			Long userIdUpdate = employeeDocument[12] != null ? Long.parseLong(employeeDocument[12].toString()) : null;

			employeeDocumentDto.setEmpId(employeeId);
			employeeDocumentDto.setFirstName(firstName);
			employeeDocumentDto.setLastName(lastName);
			employeeDocumentDto.setDesignationName(designationName);
			employeeDocumentDto.setEmployeeCode(employeeCode);
			employeeDocumentDto.setDepartmentName(departmentName);
			employeeDocumentDto.setDateOfBirth(dateOfBirth);
			employeeDocumentDto.setLetterType(letterType);
			employeeDocumentDto.setLetterId(letterId);
			employeeDocumentDto.setLetterDecription(letterDecription);
			employeeDocumentDto.setDateUpdate(dateUpdate);
			employeeDocumentDto.setUserIdUpdate(userIdUpdate);
			employeeDocumentDto
					.setCharFirstName(Character.toString(employeeDocumentDto.getFirstName().charAt(0)).toUpperCase());
			employeeDocumentDto
					.setCharLastName(Character.toString(employeeDocumentDto.getLastName().charAt(0)).toUpperCase());
			employeeDocumentDto.setEmpLetterId(empLetterId);

			employeeDocumentDTOList.add(employeeDocumentDto);
		}

		return employeeDocumentDTOList;

	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeLetterMonthwiseList(
			List<Object[]> pendingEmployeeDocumentViewList) {

		List<EmployeeLetterDTO> employeeDocumentDTOList = new ArrayList<EmployeeLetterDTO>();

		for (Object[] employeeLetter : pendingEmployeeDocumentViewList) {
			EmployeeLetterDTO employeeLetterDto = new EmployeeLetterDTO();
			String employeeName = employeeLetter[0] != null ? (String) employeeLetter[0] : null;
			String employeeCode = employeeLetter[1] != null ? (String) employeeLetter[1] : null;
			String hrName = employeeLetter[2] != null ? (String) employeeLetter[2] : null;
			String hrCode = employeeLetter[3] != null ? (String) employeeLetter[3] : null;
			Date deteCreated = employeeLetter[4] != null ? (Date) employeeLetter[4] : null;
			String letterName = employeeLetter[5] != null ? (String) employeeLetter[5] : null;
			Long letterId = employeeLetter[6] != null ? Long.parseLong(employeeLetter[6].toString()) : null;
			Long empLetterId = employeeLetter[7] != null ? Long.parseLong(employeeLetter[7].toString()) : null;
			String declarationStatus = employeeLetter[8] != null ? (String) employeeLetter[8] : null;
			employeeLetterDto.setEmployeeName(employeeName);
			employeeLetterDto.setEmployeeCode(employeeCode);
			employeeLetterDto.setHrCode(hrCode);
			employeeLetterDto.setHrName(hrName);
			employeeLetterDto.setLetterId(letterId);
			employeeLetterDto.setLetterName(letterName);
			employeeLetterDto.setEmpLetterId(empLetterId);
			employeeLetterDto.setDateCreated(deteCreated);
			employeeLetterDto.setDeclarationStatus(declarationStatus);
			employeeDocumentDTOList.add(employeeLetterDto);
		}

		return employeeDocumentDTOList;

	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeLetterList(List<Object[]> employeeLetterList) {

		List<EmployeeLetterDTO> employeeLetterDTOList = new ArrayList<EmployeeLetterDTO>();
		List<EmployeeLetterDTO> nonPendingLetterDTOList = new ArrayList<EmployeeLetterDTO>();

		for (Object[] employeeLetter : employeeLetterList) {

			EmployeeLetterDTO employeeLetterDto = new EmployeeLetterDTO();

			String employeeName = employeeLetter[0] != null ? (String) employeeLetter[0] : null;
			String employeeCode = employeeLetter[1] != null ? (String) employeeLetter[1] : null;
			String deparmentName = employeeLetter[2] != null ? (String) employeeLetter[2] : null;
			Date dateOfJoining = employeeLetter[3] != null ? (Date) employeeLetter[3] : null;
			String letterType = employeeLetter[4] != null ? (String) employeeLetter[4] : null;
			String HRStatus = employeeLetter[5] != null ? (String) employeeLetter[5] : null;
			String designation = employeeLetter[6] != null ? (String) employeeLetter[6] : null;

			Long designationId = employeeLetter[7] != null ? Long.parseLong(employeeLetter[7].toString()) : null;
			String levels = employeeLetter[8] != null ? (String) employeeLetter[8] : null;
			Long levelDesignationId = employeeLetter[9] != null ? Long.parseLong(employeeLetter[9].toString()) : null;
			Long letterId = employeeLetter[10] != null ? Long.parseLong(employeeLetter[10].toString()) : null;
			Long empId = employeeLetter[11] != null ? Long.parseLong(employeeLetter[11].toString()) : null;
			String levelStatus = employeeLetter[12] != null ? (String) employeeLetter[12] : "Pending";
			Long employeeLetterTransactionId = employeeLetter[13] != null
					? Long.parseLong(employeeLetter[13].toString())
					: null;
			Long empLetterId = employeeLetter[14] != null ? Long.parseLong(employeeLetter[14].toString()) : null;
			Long approvalId = employeeLetter[15] != null ? Long.parseLong(employeeLetter[15].toString()) : null;
			String approvalStatus = employeeLetter[16] != null ? (String) employeeLetter[16] : null;

			employeeLetterDto.setEmployeeName(employeeName);
			employeeLetterDto.setEmployeeCode(employeeCode);
			employeeLetterDto.setDepartmentName(deparmentName);
			employeeLetterDto.setDateOfJoining(dateOfJoining);
			employeeLetterDto.setLetterType(letterType);
			if (StatusMessage.APPROVED_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.APPROVED_VALUE);
			else if (StatusMessage.REJECTED_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.REJECTED_VALUE);
			else if (StatusMessage.CANCEL_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.CANCEL_VALUE);
			else if (StatusMessage.PENDING_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.PENDING_VALUE);

			employeeLetterDto.setDesignationName(designation);

			employeeLetterDto.setDesignationId(designationId);
			employeeLetterDto.setLevels(levels);
			employeeLetterDto.setLevelDesignationId(levelDesignationId);
			employeeLetterDto.setLetterId(letterId);
			employeeLetterDto.setEmployeeId(empId);
			employeeLetterDto.setEmployeeLetterTransactionId(employeeLetterTransactionId);
			employeeLetterDto.setLevelStatus(levelStatus);
			employeeLetterDto.setEmpLetterId(empLetterId);
			employeeLetterDto.setApprovalId(approvalId);
			employeeLetterDto.setApprovalStatus(approvalStatus);

			if (employeeLetterDto.getEmployeeLetterTransactionId() == null) {

			} else {

				if (employeeLetterDto.getApprovalId() == null
						&& employeeLetterDto.getApprovalStatus().equalsIgnoreCase("PEN")) {

					employeeLetterDTOList.add(employeeLetterDto);
				} else {
					nonPendingLetterDTOList.add(employeeLetterDto);
				}

			}

		}

		return employeeLetterDTOList;
	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeLetterListForNonPending(List<Object[]> empLetterList) {

		List<EmployeeLetterDTO> employeeLetterDTOList = new ArrayList<EmployeeLetterDTO>();
		List<EmployeeLetterDTO> nonPendingLetterDTOList = new ArrayList<EmployeeLetterDTO>();

		for (Object[] employeeLetter : empLetterList) {

			EmployeeLetterDTO employeeLetterDto = new EmployeeLetterDTO();

			String employeeName = employeeLetter[0] != null ? (String) employeeLetter[0] : null;
			String employeeCode = employeeLetter[1] != null ? (String) employeeLetter[1] : null;
			String deparmentName = employeeLetter[2] != null ? (String) employeeLetter[2] : null;
			Date dateOfJoining = employeeLetter[3] != null ? (Date) employeeLetter[3] : null;
			String letterType = employeeLetter[4] != null ? (String) employeeLetter[4] : null;
			String HRStatus = employeeLetter[5] != null ? (String) employeeLetter[5] : null;
			String designation = employeeLetter[6] != null ? (String) employeeLetter[6] : null;

			Long designationId = employeeLetter[7] != null ? Long.parseLong(employeeLetter[7].toString()) : null;
			String levels = employeeLetter[8] != null ? (String) employeeLetter[8] : null;
			Long levelDesignationId = employeeLetter[9] != null ? Long.parseLong(employeeLetter[9].toString()) : null;
			Long letterId = employeeLetter[10] != null ? Long.parseLong(employeeLetter[10].toString()) : null;
			Long empId = employeeLetter[11] != null ? Long.parseLong(employeeLetter[11].toString()) : null;
			String levelStatus = employeeLetter[12] != null ? (String) employeeLetter[12] : "Pending";
			Long employeeLetterTransactionId = employeeLetter[13] != null
					? Long.parseLong(employeeLetter[13].toString())
					: null;
			Long empLetterId = employeeLetter[14] != null ? Long.parseLong(employeeLetter[14].toString()) : null;
			Long approvalId = employeeLetter[15] != null ? Long.parseLong(employeeLetter[15].toString()) : null;
			String approvalStatus = employeeLetter[16] != null ? (String) employeeLetter[16] : null;
			String approvedBy = employeeLetter[17] != null ? (String) employeeLetter[17] : null;
			Date dateUpdated = employeeLetter[18] != null ? (Date) employeeLetter[18] : null;
			String approvalRemarks = employeeLetter[19] != null ? (String) employeeLetter[19] : null;
			String designationNameApprovedBy = employeeLetter[20] != null ? (String) employeeLetter[20] : null;

			employeeLetterDto.setEmployeeName(employeeName);
			employeeLetterDto.setEmployeeCode(employeeCode);
			employeeLetterDto.setDepartmentName(deparmentName);
			employeeLetterDto.setDateOfJoining(dateOfJoining);
			employeeLetterDto.setLetterType(letterType);
			if (StatusMessage.APPROVED_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.APPROVED_VALUE);
			else if (StatusMessage.REJECTED_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.REJECTED_VALUE);
			else if (StatusMessage.CANCEL_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.CANCEL_VALUE);
			else if (StatusMessage.PENDING_CODE.equals(HRStatus))
				employeeLetterDto.setHRStatus(StatusMessage.PENDING_VALUE);

			employeeLetterDto.setDesignationName(designation);
			employeeLetterDto.setDesignationId(designationId);
			employeeLetterDto.setLevels(levels);
			employeeLetterDto.setLevelDesignationId(levelDesignationId);
			employeeLetterDto.setLetterId(letterId);
			employeeLetterDto.setEmployeeId(empId);
			employeeLetterDto.setEmployeeLetterTransactionId(employeeLetterTransactionId);
			employeeLetterDto.setLevelStatus(levelStatus);
			employeeLetterDto.setEmpLetterId(empLetterId);
			employeeLetterDto.setApprovalId(approvalId);
			employeeLetterDto.setApprovalStatus(approvalStatus);
			employeeLetterDto.setApprovedBy(approvedBy);
			employeeLetterDto.setDateUpdate(dateUpdated);
			employeeLetterDto.setApprovalRemarks(approvalRemarks);
			employeeLetterDto.setDesignationNameApprovedBy(designationNameApprovedBy);

			if (employeeLetterDto.getEmployeeLetterTransactionId() == null) {

			} else {

				if (employeeLetterDto.getApprovalId() == null
						&& employeeLetterDto.getApprovalStatus().equalsIgnoreCase("PEN")) {

					employeeLetterDTOList.add(employeeLetterDto);
				} else {
					nonPendingLetterDTOList.add(employeeLetterDto);
				}

			}

		}

		return nonPendingLetterDTOList;
	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeDocumentViewListTx(
			List<Object[]> pendingEmployeeDocumentViewList, List<ApprovalHierarchyMaster> approvalHierarchyMasters) {
		List<EmployeeLetterDTO> employeeDocumentDTOList = new ArrayList<EmployeeLetterDTO>();
	
		for (Object[] employeeDocument : pendingEmployeeDocumentViewList) {
			EmployeeLetterDTO employeeDocumentDto = new EmployeeLetterDTO();

			Long employeeId = employeeDocument[0] != null ? Long.parseLong(employeeDocument[0].toString()) : null;
			String firstName = employeeDocument[1] != null ? (String) employeeDocument[1] : null;
			String lastName = employeeDocument[2] != null ? (String) employeeDocument[2] : null;
			String designationName = employeeDocument[3] != null ? (String) employeeDocument[3] : null;
			String employeeCode = employeeDocument[4] != null ? (String) employeeDocument[4] : null;
			String departmentName = employeeDocument[5] != null ? (String) employeeDocument[5] : null;
			Date dateOfBirth = employeeDocument[6] != null ? (Date) employeeDocument[6] : null;
			String letterType = employeeDocument[7] != null ? (String) employeeDocument[7] : null;
			Long letterId = employeeDocument[8] != null ? Long.parseLong(employeeDocument[8].toString()) : null;
			Long empLetterId = employeeDocument[9] != null ? Long.parseLong(employeeDocument[9].toString()) : null;
			String letterDecription = employeeDocument[10] != null ? (String) employeeDocument[10] : null;
			Date dateUpdate = employeeDocument[11] != null ? (Date) employeeDocument[11] : null;
			Long userIdUpdate = employeeDocument[12] != null ? Long.parseLong(employeeDocument[12].toString()) : null;
			String releaseStatus = employeeDocument[13] != null ? (String) employeeDocument[13] : null;
			String employeeLogoPath = employeeDocument[14] != null ? (String) employeeDocument[14] : null;
			employeeDocumentDto.setEmpId(employeeId);
			employeeDocumentDto.setFirstName(firstName);
			employeeDocumentDto.setLastName(lastName);
			employeeDocumentDto.setDesignationName(designationName);
			employeeDocumentDto.setEmployeeCode(employeeCode);
			employeeDocumentDto.setDepartmentName(departmentName);
			employeeDocumentDto.setDateOfBirth(dateOfBirth);
			employeeDocumentDto.setLetterType(letterType);
			employeeDocumentDto.setLetterId(letterId);
			employeeDocumentDto.setLetterDecription(letterDecription);
			employeeDocumentDto.setDateUpdate(dateUpdate);
			employeeDocumentDto.setUserIdUpdate(userIdUpdate);
			employeeDocumentDto.setRealeseStatus(releaseStatus);
			employeeDocumentDto.setEmployeeLogoPath(employeeLogoPath);
			employeeDocumentDto
					.setCharFirstName(Character.toString(employeeDocumentDto.getFirstName().charAt(0)).toUpperCase());
			employeeDocumentDto
					.setCharLastName(Character.toString(employeeDocumentDto.getLastName().charAt(0)).toUpperCase());
			employeeDocumentDto.setEmpLetterId(empLetterId);
			ApprovalHierarchyMaster hierarchyMaster =	approvalHierarchyMasterRepository.getMasterApprovalStatus(1L, letterId);
			employeeDocumentDto.setApprovalStatus(hierarchyMaster.getActiveStatus());
			for (ApprovalHierarchyMaster approvalHierarchy : approvalHierarchyMasters) {
				if (letterId == approvalHierarchy.getLetter().getLetterId()) {
					if (approvalHierarchy.getActiveStatus().equalsIgnoreCase(StatusMessage.DEACTIVE_CODE)) {
						employeeDocumentDto.setApprovalHierarchyStatus(StatusMessage.DEACTIVE_CODE);
					} else
						employeeDocumentDto.setApprovalHierarchyStatus(StatusMessage.ACTIVE_CODE);
				}
			}

			List<EmployeeLettersTransactionDTO> empTransactionList = databaseModelToUiDtoListTx(
					empLettersTransactionService.findAllApprovalStatus(1L, letterId, employeeId));
			
			
			int count=0;
			for(EmployeeLettersTransactionDTO EmployeeLettersTransactionDto:empTransactionList)
			{
				String status="";
				status=EmployeeLettersTransactionDto.getStatus();
				if (status != null) {
					if (status.equals("Approved")) {
						++count;
					}

				}
			}
			employeeDocumentDto.setCount(count);
			
			int statusCount=employeeDocumentDto.getCount();
			int length=empTransactionList.size();
			int cal=(100/length)*statusCount;
			employeeDocumentDto.setCal(cal);
			
			employeeDocumentDto.setEmpTransactionList(empTransactionList);
			employeeDocumentDTOList.add(employeeDocumentDto);

		}
		return employeeDocumentDTOList;

	}

	public List<EmployeeLettersTransactionDTO> databaseModelToUiDtoListTx(List<EmployeeLettersTransaction> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDtoTx(item)).collect(Collectors.toList());
	}

	public EmployeeLettersTransactionDTO databaseModelToUiDtoTx(EmployeeLettersTransaction employeeLettersTransaction) {
		EmployeeLettersTransactionDTO empLettersTransactionDTO = new EmployeeLettersTransactionDTO();
		empLettersTransactionDTO.setApprovalLevel(employeeLettersTransaction.getApprovalLevel());
		empLettersTransactionDTO.setApprovalId(employeeLettersTransaction.getApprovalId());
		empLettersTransactionDTO.setLevels(employeeLettersTransaction.getLevels());
		empLettersTransactionDTO.setDesignationId(employeeLettersTransaction.getDesignationId());
		empLettersTransactionDTO.setDesignationName(employeeLettersTransaction.getDesignationName());
		if (employeeLettersTransaction.getStatus() != null) {
			if (employeeLettersTransaction.getStatus().equalsIgnoreCase(StatusMessage.PENDING_CODE)) {
				empLettersTransactionDTO.setStatus(StatusMessage.AWAITED_VALUE);
			}
			if (employeeLettersTransaction.getStatus().equalsIgnoreCase(StatusMessage.APPROVED_CODE)) {
				empLettersTransactionDTO.setStatus(StatusMessage.APPROVED_VALUE);
			}
			if (employeeLettersTransaction.getStatus().equalsIgnoreCase(StatusMessage.REJECTED_CODE)) {
				empLettersTransactionDTO.setStatus(StatusMessage.REJECTED_VALUE);
			}
		} else {
			empLettersTransactionDTO.setStatus(StatusMessage.AWAITED_VALUE);
		}
		empLettersTransactionDTO.setApprovalRemarks(employeeLettersTransaction.getApprovalRemarks());
		empLettersTransactionDTO.setCompanyId(employeeLettersTransaction.getCompanyId());
		empLettersTransactionDTO.setUserId(employeeLettersTransaction.getUserId());
		empLettersTransactionDTO.setDateCreated(employeeLettersTransaction.getDateCreated());
		empLettersTransactionDTO.setDateUpdate(employeeLettersTransaction.getDateUpdate());
		empLettersTransactionDTO
				.setEmployeeLetterTransactionId(employeeLettersTransaction.getEmployeeLetterTransactionId());
		empLettersTransactionDTO.setUserId(employeeLettersTransaction.getUserId());
		empLettersTransactionDTO.setUserIdUpdate(employeeLettersTransaction.getUserIdUpdate());

		return empLettersTransactionDTO;
	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmployeeLtrList(List<Object[]> pendingEmployeeDocumentViewList) {

		List<EmployeeLetterDTO> employeeDocumentDTOList = new ArrayList<EmployeeLetterDTO>();

		for (Object[] employeeLetter : pendingEmployeeDocumentViewList) {
			EmployeeLetterDTO employeeLetterDto = new EmployeeLetterDTO();
			String letterType = employeeLetter[0] != null ? (String) employeeLetter[0] : null;
			Long employeeId = employeeLetter[1] != null ? Long.parseLong(employeeLetter[1].toString()) : null;
			Long letterId = employeeLetter[2] != null ? Long.parseLong(employeeLetter[2].toString()) : null;
			Long empLetterId = employeeLetter[3] != null ? Long.parseLong(employeeLetter[3].toString()) : null;
			Date deteCreated = employeeLetter[4] != null ? (Date) employeeLetter[4] : null;
			SimpleDateFormat sm = new SimpleDateFormat("dd-MMM-yyyy");
			String date = sm.format(deteCreated);
			employeeLetterDto.setLetterType(letterType + " - " + date);
			employeeLetterDto.setEmpId(employeeId);
			employeeLetterDto.setLetterId(letterId);
			employeeLetterDto.setEmpLetterId(empLetterId);
			employeeDocumentDTOList.add(employeeLetterDto);
		}

		return employeeDocumentDTOList;

	}

	public List<EmployeeLetterDTO> databaseModelToUiDtoEmpLetterList(List<Object[]> empLetterList) {
		List<EmployeeLetterDTO> employeeDocumentDTOList = new ArrayList<EmployeeLetterDTO>();
		
		for (Object[] employeeDocument : empLetterList) {
			EmployeeLetterDTO employeeDocumentDto = new EmployeeLetterDTO();

			Long employeeId = employeeDocument[0] != null ? Long.parseLong(employeeDocument[0].toString()) : null;
			String firstName = employeeDocument[1] != null ? (String) employeeDocument[1] : null;
			String lastName = employeeDocument[2] != null ? (String) employeeDocument[2] : null;
			String designationName = employeeDocument[3] != null ? (String) employeeDocument[3] : null;
			String employeeCode = employeeDocument[4] != null ? (String) employeeDocument[4] : null;
			String departmentName = employeeDocument[5] != null ? (String) employeeDocument[5] : null;
			Date dateOfJoining = employeeDocument[6] != null ? (Date) employeeDocument[6] : null;
			String letterType = employeeDocument[7] != null ? (String) employeeDocument[7] : null;
			Long letterId = employeeDocument[8] != null ? Long.parseLong(employeeDocument[8].toString()) : null;
			//Long empLetterId = employeeDocument[9] != null ? Long.parseLong(employeeDocument[9].toString()) : null;
			String letterDecription = employeeDocument[10] != null ? (String) employeeDocument[10] : null;
			Date dateUpdate = employeeDocument[11] != null ? (Date) employeeDocument[11] : null;
			Long userIdUpdate = employeeDocument[12] != null ? Long.parseLong(employeeDocument[12].toString()) : null;
			String releaseStatus = employeeDocument[13] != null ? (String) employeeDocument[13] : null;
			String employeeLogoPath = employeeDocument[14] != null ? (String) employeeDocument[14] : null;
			employeeDocumentDto.setEmpId(employeeId);
			employeeDocumentDto.setFirstName(firstName);
			employeeDocumentDto.setLastName(lastName);
			employeeDocumentDto.setDesignationName(designationName);
			employeeDocumentDto.setEmployeeCode(employeeCode);
			employeeDocumentDto.setDepartmentName(departmentName);
			employeeDocumentDto.setDateOfJoining(dateOfJoining);
			employeeDocumentDto.setLetterType(letterType);
			employeeDocumentDto.setLetterId(letterId);
			employeeDocumentDto.setLetterDecription(letterDecription);
			employeeDocumentDto.setDateUpdate(dateUpdate);
			employeeDocumentDto.setUserIdUpdate(userIdUpdate);
			employeeDocumentDto.setRealeseStatus(releaseStatus);
			employeeDocumentDto.setEmployeeLogoPath(employeeLogoPath);
			employeeDocumentDTOList.add(employeeDocumentDto);
		}
		return employeeDocumentDTOList;
	}

}
