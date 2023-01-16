package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.ApprovalHierarchyDTO;
import com.csipl.hrms.model.employee.ApprovalHierarchy;

@Component
public class ApprovalHierarchyAdaptor implements Adaptor<ApprovalHierarchyDTO, ApprovalHierarchy> {

	@Override
	public List<ApprovalHierarchy> uiDtoToDatabaseModelList(List<ApprovalHierarchyDTO> approvalHierarchyList) {

		return approvalHierarchyList.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalHierarchyDTO> databaseModelToUiDtoList(List<ApprovalHierarchy> approvalHierarchyList) {
		List<ApprovalHierarchyDTO> ApprovalHierarchyDTOs = new ArrayList<ApprovalHierarchyDTO>();
		for (ApprovalHierarchy approvalHierarchy : approvalHierarchyList) {
			ApprovalHierarchyDTOs.add(databaseModelToUiDto(approvalHierarchy));
		}
		return ApprovalHierarchyDTOs;

	}

	@Override
	public ApprovalHierarchy uiDtoToDatabaseModel(ApprovalHierarchyDTO approvalHierarchyDTO) {
		ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();

		approvalHierarchy.setApprovalHierarchyId(approvalHierarchyDTO.getApprovalHierarchyId());
		approvalHierarchy.setLevels(approvalHierarchyDTO.getLevels());
		approvalHierarchy.setDesignationId(approvalHierarchyDTO.getDesignationId());
		approvalHierarchy.setActiveStatus(approvalHierarchyDTO.getActiveStatus());
		approvalHierarchy.setUserId(approvalHierarchyDTO.getUserId());
		approvalHierarchy.setCompanyId(approvalHierarchyDTO.getCompanyId());
		approvalHierarchy.setUserIdUpdate(approvalHierarchyDTO.getUserIdUpdate());

		return approvalHierarchy;
	}

	@Override
	public ApprovalHierarchyDTO databaseModelToUiDto(ApprovalHierarchy ApprovalHierarchy) {
		ApprovalHierarchyDTO approvalHierarchyDTO = new ApprovalHierarchyDTO();

		approvalHierarchyDTO.setApprovalHierarchyId(ApprovalHierarchy.getApprovalHierarchyId());
		approvalHierarchyDTO.setApprovalHierarchyMasterId(
				ApprovalHierarchy.getApprovalHierarchyMaster().getApprovalHierarchyMasterId());
		approvalHierarchyDTO.setLevels(ApprovalHierarchy.getLevels());
		approvalHierarchyDTO.setDesignationId(ApprovalHierarchy.getDesignationId());
		approvalHierarchyDTO.setActiveStatus(ApprovalHierarchy.getActiveStatus());
		approvalHierarchyDTO.setCompanyId(ApprovalHierarchy.getCompanyId());
		approvalHierarchyDTO.setDateCreated(ApprovalHierarchy.getDateCreated());

		return approvalHierarchyDTO;
	}

	public List<ApprovalHierarchyDTO> databaseModelToLetterApprovalList(List<Object[]> levelList) {

		List<ApprovalHierarchyDTO> letterLevelList = new ArrayList<ApprovalHierarchyDTO>();
		for (Object[] obj : levelList) {

			ApprovalHierarchyDTO approvalHierarchyDTO = new ApprovalHierarchyDTO();

			Long approvalHierarchyId = obj[0] != null ? Long.parseLong(obj[0].toString()) : null;
			Long approvalHierarchyMasterId = obj[1] != null ? Long.parseLong(obj[1].toString()) : null;
			Long letterId = obj[2] != null ? Long.parseLong(obj[2].toString()) : null;
			String letterName = obj[3] != null ? (String) obj[3] : null;
			String activeStatus = obj[4] != null ? (String) obj[4] : null;
			String levels = obj[5] != null ? (String) obj[5] : null;
			Long designationId = obj[6] != null ? Long.parseLong(obj[6].toString()) : null;

			approvalHierarchyDTO.setApprovalHierarchyId(approvalHierarchyId);
			approvalHierarchyDTO.setApprovalHierarchyMasterId(approvalHierarchyMasterId);
			approvalHierarchyDTO.setLetterId(letterId);
			approvalHierarchyDTO.setLetterName(letterName);
			approvalHierarchyDTO.setActiveStatus(activeStatus);
			approvalHierarchyDTO.setLevels(levels);
			approvalHierarchyDTO.setDesignationId(designationId);

			letterLevelList.add(approvalHierarchyDTO);
		}

		return letterLevelList;
	}

}
