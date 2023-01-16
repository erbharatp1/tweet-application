package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.ApprovalHierarchyDTO;
import com.csipl.hrms.dto.employee.ApprovalHierarchyMasterDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchy;
import com.csipl.hrms.model.employee.ApprovalHierarchyMaster;
import com.csipl.hrms.model.employee.Letter;

@Component
public class ApprovalHierarchyMasterAdaptor implements Adaptor<ApprovalHierarchyMasterDTO, ApprovalHierarchyMaster> {

	@Override
	public List<ApprovalHierarchyMaster> uiDtoToDatabaseModelList(
			List<ApprovalHierarchyMasterDTO> approvalHierarchyMasterList) {

		return approvalHierarchyMasterList.stream().map(item -> uiDtoToDatabaseModel(item))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalHierarchyMasterDTO> databaseModelToUiDtoList(
			List<ApprovalHierarchyMaster> approvalHierarchyMasterList) {

		List<ApprovalHierarchyMasterDTO> ApprovalHierarchyDTOs = new ArrayList<ApprovalHierarchyMasterDTO>();
		for (ApprovalHierarchyMaster approvalHierarchyMaster : approvalHierarchyMasterList) {
			ApprovalHierarchyDTOs.add(databaseModelToUiDto(approvalHierarchyMaster));
		}
		return ApprovalHierarchyDTOs;
	}

	@Override
	public ApprovalHierarchyMaster uiDtoToDatabaseModel(ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO) {

		ApprovalHierarchyMaster approvalHierarchyMaster = new ApprovalHierarchyMaster();

		approvalHierarchyMaster.setApprovalHierarchyMasterId(approvalHierarchyMasterDTO.getApprovalHierarchyMasterId());
		approvalHierarchyMaster.setActiveStatus(approvalHierarchyMasterDTO.getActiveStatus());

		Letter letter = new Letter();
		letter.setLetterId(approvalHierarchyMasterDTO.getLetterId());
		approvalHierarchyMaster.setLetter(letter);

		approvalHierarchyMaster.setUserId(approvalHierarchyMasterDTO.getUserId());
		approvalHierarchyMaster.setCompanyId(approvalHierarchyMasterDTO.getCompanyId());
		approvalHierarchyMaster.setUserIdUpdate(approvalHierarchyMasterDTO.getUserIdUpdate());

		return approvalHierarchyMaster;
	}

	@SuppressWarnings("static-access")
	@Override
	public ApprovalHierarchyMasterDTO databaseModelToUiDto(ApprovalHierarchyMaster approvalHierarchyMaster) {

		ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO = new ApprovalHierarchyMasterDTO();
		List<ApprovalHierarchyDTO> approvalHierarchyList = new ArrayList<ApprovalHierarchyDTO>();

		List<ApprovalHierarchy> approvalHierarchies = approvalHierarchyMaster.getApprovalHierarchies();

		approvalHierarchyMasterDTO.setApprovalHierarchyMasterId(approvalHierarchyMaster.getApprovalHierarchyMasterId());
		approvalHierarchyMasterDTO.setLetterId(approvalHierarchyMaster.getLetter().getLetterId());
		approvalHierarchyMasterDTO.setLetterName(approvalHierarchyMaster.getLetter().getLetterName());
		approvalHierarchyMasterDTO.setActiveStatus(approvalHierarchyMaster.getActiveStatus());

		String status = null;
		StatusMessage sm = new StatusMessage();

		if (approvalHierarchyMasterDTO.getActiveStatus().equals(sm.ACTIVE_CODE)) {
			status = sm.ACTIVE_VALUE;
		} else if (approvalHierarchyMasterDTO.getActiveStatus().equals(sm.DEACTIVE_CODE)) {
			status = sm.DEACTIVE_VALUE;
		}

		approvalHierarchyMasterDTO.setActiveStatus(status);

		String level = "";
		for (ApprovalHierarchy obj : approvalHierarchies) {
			ApprovalHierarchyDTO approvalHierarchyObj = new ApprovalHierarchyDTO();
			approvalHierarchyObj.setLevels(obj.getLevels());
			approvalHierarchyObj.setLetterId(obj.getApprovalHierarchyMaster().getLetter().getLetterId());
			approvalHierarchyObj.setLetterName(obj.getApprovalHierarchyMaster().getLetter().getLetterName());
			if (approvalHierarchyObj.getLetterId() != null) {
				if (level != "") {
					level += '-' + obj.getLevels();
				} else {
					level = obj.getLevels();
				}
			}

			approvalHierarchyObj.setLevels(level);
			approvalHierarchyList.add(approvalHierarchyObj);
		}
		approvalHierarchyMasterDTO.setLevels(level);
		approvalHierarchyMasterDTO.setLetterApprovalList(approvalHierarchyList);
		approvalHierarchyMasterDTO.setUserId(approvalHierarchyMaster.getUserId());
		approvalHierarchyMasterDTO.setCompanyId(approvalHierarchyMaster.getCompanyId());
		approvalHierarchyMasterDTO.setUserIdUpdate(approvalHierarchyMaster.getUserIdUpdate());

		return approvalHierarchyMasterDTO;
	}

	public ApprovalHierarchyMaster approvalMastertoDatabaseModel(ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO)
			throws ErrorHandling {

		List<ApprovalHierarchy> approvalHierarchyList = new ArrayList<ApprovalHierarchy>();

		ApprovalHierarchyMaster approvalHierarchyMaster = new ApprovalHierarchyMaster();
		Letter letter = new Letter();

		approvalHierarchyMaster.setApprovalHierarchyMasterId(approvalHierarchyMasterDTO.getApprovalHierarchyMasterId());
		letter.setLetterId(approvalHierarchyMasterDTO.getLetterId());
		approvalHierarchyMaster.setLetter(letter);
		approvalHierarchyMaster.setActiveStatus(approvalHierarchyMasterDTO.getActiveStatus());
		approvalHierarchyMaster.setDateCreated(approvalHierarchyMasterDTO.getDateCreated());
		approvalHierarchyMaster.setUserId(approvalHierarchyMasterDTO.getUserId());
		approvalHierarchyMaster.setCompanyId(approvalHierarchyMasterDTO.getCompanyId());
		approvalHierarchyMaster.setDateUpdate(approvalHierarchyMasterDTO.getDateUpdate());
		approvalHierarchyMaster.setUserIdUpdate(approvalHierarchyMasterDTO.getUserIdUpdate());

		ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
		approvalHierarchy.setActiveStatus(approvalHierarchyMasterDTO.getActiveStatus());

		List<ApprovalHierarchyDTO> approvalHierarchyDTOList = approvalHierarchyMasterDTO.getLetterApprovalList();

		if (approvalHierarchyDTOList.size() > 0) {
			for (ApprovalHierarchyDTO obj : approvalHierarchyDTOList) {
				ApprovalHierarchy approvalHierarchyObj = new ApprovalHierarchy();
				approvalHierarchyObj.setApprovalHierarchyId(obj.getApprovalHierarchyId());
				approvalHierarchyObj.setApprovalHierarchyMaster(approvalHierarchyMaster);
				approvalHierarchyObj.setLevels(obj.getLevels());
				if (obj.getDesignationId() != null) {
					approvalHierarchyObj.setDesignationId(obj.getDesignationId());
				}
				approvalHierarchyObj.setActiveStatus(obj.getActiveStatus());
				approvalHierarchyObj.setDateCreated(obj.getDateCreated());
				approvalHierarchyObj.setUserId(obj.getUserId());
				approvalHierarchyObj.setCompanyId(obj.getCompanyId());
				approvalHierarchyObj.setUserIdUpdate(obj.getUserIdUpdate());
				approvalHierarchyObj.setDateUpdate(obj.getDateUpdate());

				approvalHierarchyList.add(approvalHierarchyObj);
			}
		}

		approvalHierarchyMaster.setApprovalHierarchies(approvalHierarchyList);

		return approvalHierarchyMaster;
	}

	public List<ApprovalHierarchyMasterDTO> databaseModelToLevelByLetter(List<Object[]> levelList) {

		List<ApprovalHierarchyMasterDTO> letterLevelList = new ArrayList<ApprovalHierarchyMasterDTO>();
		for (Object[] obj : levelList) {

			ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO = new ApprovalHierarchyMasterDTO();

			String levels = obj[0] != null ? (String) obj[0] : null;

			approvalHierarchyMasterDTO.setLevels(levels);

			letterLevelList.add(approvalHierarchyMasterDTO);
		}

		return letterLevelList;
	}

	public ApprovalHierarchyMasterDTO databaseModelToApprovalListByIdDto(List<Object[]> approvalList) {

		ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO = new ApprovalHierarchyMasterDTO();

		List<ApprovalHierarchyDTO> letterApprovalList = new ArrayList<ApprovalHierarchyDTO>();

		Long letterId = 0l;
		String activeStatus = null;
		Long approvalHierarchyMasterId = 0l;

		for (Object[] obj : approvalList) {

			ApprovalHierarchyDTO approvalHierarchyDTO = new ApprovalHierarchyDTO();

			Long approvalHierarchyId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			Long designationId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
			String levels = obj[2] != null ? (String) obj[2] : null;
			activeStatus = obj[3] != null ? (String) obj[3] : null;
			letterId = obj[4] != null ? Long.parseLong(obj[4].toString()) : null;
			String status = obj[5] != null ? (String) obj[5] : null;
			approvalHierarchyMasterId = obj[6] != null ? Long.parseLong(obj[6].toString()) : null;

			approvalHierarchyDTO.setApprovalHierarchyId(approvalHierarchyId);
			approvalHierarchyDTO.setDesignationId(designationId);
			approvalHierarchyDTO.setLevels(levels);
			approvalHierarchyDTO.setActiveStatus(status);

			letterApprovalList.add(approvalHierarchyDTO);
		}
		approvalHierarchyMasterDTO.setApprovalHierarchyMasterId(approvalHierarchyMasterId);
		approvalHierarchyMasterDTO.setLetterId(letterId);
		approvalHierarchyMasterDTO.setActiveStatus(activeStatus);

		approvalHierarchyMasterDTO.setLetterApprovalList(letterApprovalList);

		return approvalHierarchyMasterDTO;
	}

	public List<ApprovalHierarchyMasterDTO> databaseModelToMasterApprovalDtoList(
			List<Object[]> approvalHierarchyMasterList) {

		List<ApprovalHierarchyMasterDTO> letterApprovalList = new ArrayList<ApprovalHierarchyMasterDTO>();

		for (Object[] obj : approvalHierarchyMasterList) {

			ApprovalHierarchyMasterDTO approvalHierarchyMasterDTO = new ApprovalHierarchyMasterDTO();

			Long letterId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			String letterName = obj[1] != null ? (String) obj[1] : null;
			String activeStatus = obj[2] != null ? (String) obj[2] : null;

			approvalHierarchyMasterDTO.setLetterId(letterId);
			approvalHierarchyMasterDTO.setLetterName(letterName);
			approvalHierarchyMasterDTO.setActiveStatus(activeStatus);

			letterApprovalList.add(approvalHierarchyMasterDTO);
		}

		return letterApprovalList;
	}

}
