package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.csipl.hrms.dto.candidate.CandidateNomineeDTO;
import com.csipl.hrms.model.candidate.CandidateFamily;
import com.csipl.hrms.model.candidate.CandidateNominee;

public class CandidateNomineeAdaptor implements Adaptor<CandidateNomineeDTO,CandidateNominee>{

	@Override
	public List<CandidateNominee> uiDtoToDatabaseModelList(List<CandidateNomineeDTO> candidateNomineeDTOList) {
		List<CandidateNominee> candidateNomineeList = new ArrayList<CandidateNominee>();
		for (CandidateNomineeDTO nominee : candidateNomineeDTOList) {
			candidateNomineeList.add(uiDtoToDatabaseModel(nominee));
		}
		return candidateNomineeList;
	}

	@Override
	public List<CandidateNomineeDTO> databaseModelToUiDtoList(List<CandidateNominee> candidateNomineeList) {
	
		List<CandidateNomineeDTO> candidateNomineeDTOList = new ArrayList<CandidateNomineeDTO>();
		for (CandidateNominee candidateNominee : candidateNomineeList) {
			candidateNomineeDTOList.add(databaseModelToUiDto(candidateNominee));
		}
		return candidateNomineeDTOList;
	}

	@Override
	public CandidateNominee uiDtoToDatabaseModel(CandidateNomineeDTO candidateNomineeDTO) {
		CandidateNominee candidateNominee=new CandidateNominee();
		CandidateFamily family =  new CandidateFamily();
		family.setFamilyId(candidateNomineeDTO.getFamilyId());
		candidateNominee.setCandidateFamily(family);
		candidateNominee.setActiveStatus(candidateNomineeDTO.getActiveStatus());
		candidateNominee.setStaturyHeadId(candidateNomineeDTO.getStaturyHeadId());
		candidateNominee.setStaturyHeadName(candidateNomineeDTO.getStaturyHeadName());
		candidateNominee.setUserId(candidateNomineeDTO.getUserId());
		candidateNominee.setUserIdUpdate(candidateNomineeDTO.getUserIdUpdate());
		candidateNominee.setDateUpdate(candidateNomineeDTO.getDateUpdate());
		candidateNominee.setCandidateId(candidateNomineeDTO.getCandidateId());
		candidateNominee.setCandidateNomineeid(candidateNomineeDTO.getCandidateNomineeid());
		if(candidateNomineeDTO.getCandidateNomineeid() != null) {
			candidateNominee.setDateCreated(candidateNomineeDTO.getDateCreated());
		}
		else
		{
			candidateNominee.setDateCreated(new Date());
		}
		
		return candidateNominee;
	}

	@Override
	public CandidateNomineeDTO databaseModelToUiDto(CandidateNominee candidateNominee) {
		CandidateNomineeDTO candidateNomineeDTO=new CandidateNomineeDTO();
		candidateNomineeDTO.setActiveStatus(candidateNominee.getActiveStatus());
		candidateNomineeDTO.setActiveStatus(candidateNominee.getActiveStatus());
		candidateNomineeDTO.setStaturyHeadId(candidateNominee.getStaturyHeadId());
		candidateNomineeDTO.setStaturyHeadName(candidateNominee.getStaturyHeadName());
		candidateNomineeDTO.setUserId(candidateNominee.getUserId());
		candidateNomineeDTO.setUserIdUpdate(candidateNominee.getUserIdUpdate());
		candidateNomineeDTO.setDateUpdate(candidateNominee.getDateUpdate());
		candidateNomineeDTO.setFamilyId(candidateNominee.getCandidateFamily().getFamilyId());
		candidateNomineeDTO.setDateCreated(candidateNominee.getDateCreated());
		candidateNomineeDTO.setCandidateNomineeid(candidateNominee.getCandidateNomineeid());
		candidateNomineeDTO.setCandidateId(candidateNominee.getCandidateId());
		return candidateNomineeDTO;
	}

}
