package com.csipl.hrms.service.recruitement.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.recruitment.CandidateSelectionRuleDTO;
import com.csipl.hrms.model.recruitment.SelectionRuleDescription;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class CandidateSelectionRuleAdaptor implements Adaptor<CandidateSelectionRuleDTO, SelectionRuleDescription> {


	@Override
	public CandidateSelectionRuleDTO databaseModelToUiDto(SelectionRuleDescription dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public List<CandidateSelectionRuleDTO> databaseModelToUiDtoList(
			List<SelectionRuleDescription> candidateSelectionRuleList) {
		List<CandidateSelectionRuleDTO> candidateSelectionRuleDTOList = new ArrayList<CandidateSelectionRuleDTO>();
		for (SelectionRuleDescription candidateSelectionRule : candidateSelectionRuleList) {
			CandidateSelectionRuleDTO candidateSelectionRuleDTO = new CandidateSelectionRuleDTO();
			candidateSelectionRuleDTO.setSelectionRuleId(candidateSelectionRule.getSelectionRuleId());
			candidateSelectionRuleDTO.setSelectionRuleDescription(candidateSelectionRule.getSelectionRuleDescription());
			candidateSelectionRuleDTO.setStatus(candidateSelectionRule.getStatus());
			candidateSelectionRuleDTO.setUserId(candidateSelectionRule.getUserId());
			candidateSelectionRuleDTO.setUserIdUpdate(candidateSelectionRule.getUserIdUpdate());
			candidateSelectionRuleDTO.setDateCreated(candidateSelectionRule.getDateCreated());
			candidateSelectionRuleDTO.setUpdatedDate(candidateSelectionRule.getUpdatedDate());
			candidateSelectionRuleDTOList.add(candidateSelectionRuleDTO);
		}

		return candidateSelectionRuleDTOList;
	}


	@Override
	public SelectionRuleDescription uiDtoToDatabaseModel(CandidateSelectionRuleDTO candidateSelectionRuleDTO) {
		SelectionRuleDescription updateSelectionRuleDescription=new SelectionRuleDescription();
			SelectionRuleDescription  selectionRuleDescription=new SelectionRuleDescription();
			selectionRuleDescription.setSelectionRuleId(candidateSelectionRuleDTO.getSelectionRuleId());
			selectionRuleDescription.setSelectionRuleDescription(candidateSelectionRuleDTO.getSelectionRuleDescription());
			selectionRuleDescription.setStatus(candidateSelectionRuleDTO.getStatus());
			selectionRuleDescription.setUserId(candidateSelectionRuleDTO.getUserIdUpdate());
			selectionRuleDescription.setUserIdUpdate(candidateSelectionRuleDTO.getUserIdUpdate());
			selectionRuleDescription.setDateCreated(candidateSelectionRuleDTO.getDateCreated());
			selectionRuleDescription.setUpdatedDate(candidateSelectionRuleDTO.getUpdatedDate());		
		return updateSelectionRuleDescription;
	}

	@Override
	public List<SelectionRuleDescription> uiDtoToDatabaseModelList(List<CandidateSelectionRuleDTO> candidateSelectionRuleDTO) {
		List<SelectionRuleDescription> updateSelectionRuleDescription=new ArrayList<SelectionRuleDescription>();
		for (CandidateSelectionRuleDTO candidateSelectionRule : candidateSelectionRuleDTO) {
			SelectionRuleDescription  selectionRuleDescription=new SelectionRuleDescription();
			selectionRuleDescription.setSelectionRuleId(candidateSelectionRule.getSelectionRuleId());
			selectionRuleDescription.setSelectionRuleDescription(candidateSelectionRule.getSelectionRuleDescription());
			selectionRuleDescription.setStatus(candidateSelectionRule.getStatus());
			selectionRuleDescription.setUserIdUpdate(candidateSelectionRule.getUserIdUpdate());
			selectionRuleDescription.setUserId(candidateSelectionRule.getUserIdUpdate());
			selectionRuleDescription.setDateCreated(candidateSelectionRule.getDateCreated());
			selectionRuleDescription.setUpdatedDate(candidateSelectionRule.getUpdatedDate());
			updateSelectionRuleDescription.add(selectionRuleDescription);
		}
		return updateSelectionRuleDescription;
	}
	
	
}
