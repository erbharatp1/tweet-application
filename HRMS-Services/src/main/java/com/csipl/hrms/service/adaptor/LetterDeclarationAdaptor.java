package com.csipl.hrms.service.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.LetterDaclarationDTO;
import com.csipl.hrms.model.employee.LetterDaclaration;

/**
 * 
 * @author Bharat
 *
 */
@Component
public class LetterDeclarationAdaptor implements Adaptor<LetterDaclarationDTO, LetterDaclaration> {
	 
 
	@Override
	public List<LetterDaclaration> uiDtoToDatabaseModelList(List<LetterDaclarationDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<LetterDaclarationDTO> databaseModelToUiDtoList(List<LetterDaclaration> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public LetterDaclaration uiDtoToDatabaseModel(LetterDaclarationDTO uiobj) {
		LetterDaclaration letterDaclaration = new LetterDaclaration();
		
		//LetterDaclarationDTO letterDaclarationDTO = new LetterDaclarationDTO();
		letterDaclaration.setCompanyId(uiobj.getCompanyId());
		letterDaclaration.setDateCreated(uiobj.getDateCreated());
		letterDaclaration.setDateUpdate(uiobj.getDateUpdate());
		
		letterDaclaration.setDeclarationContant(uiobj.getDeclarationContant());
		
		letterDaclaration.setDeclarationId(uiobj.getDeclarationId());
		letterDaclaration.setHeading(uiobj.getHeading());
		letterDaclaration.setLetterId(uiobj.getLetterId());
		letterDaclaration.setDeclarationStatus(uiobj.getDeclarationStatus());
		letterDaclaration.setUserId(uiobj.getUserId());
		letterDaclaration.setUserIdUpdate(uiobj.getUserIdUpdate());
		//BeanUtils.copyProperties(uiobj, signatory);
		return letterDaclaration;
	}

	@Override
	public LetterDaclarationDTO databaseModelToUiDto(LetterDaclaration letterDaclaration) {
		LetterDaclarationDTO letterDaclarationDTO = new LetterDaclarationDTO();
		letterDaclarationDTO.setCompanyId(letterDaclaration.getCompanyId());
		letterDaclarationDTO.setDateCreated(letterDaclaration.getDateCreated());
		letterDaclarationDTO.setDateUpdate(letterDaclaration.getDateUpdate());
		
		letterDaclarationDTO.setDeclarationContant(letterDaclaration.getDeclarationContant());
		
		letterDaclarationDTO.setDeclarationId(letterDaclaration.getDeclarationId());
		letterDaclarationDTO.setHeading(letterDaclaration.getHeading());
		letterDaclarationDTO.setLetterId(letterDaclaration.getLetterId());
		letterDaclarationDTO.setDeclarationStatus(letterDaclaration.getDeclarationStatus());
		letterDaclarationDTO.setUserId(letterDaclaration.getUserId());
		letterDaclarationDTO.setUserIdUpdate(letterDaclaration.getUserIdUpdate());
//		BeanUtils.copyProperties(letterDaclaration, letterDaclarationDTO);
		return letterDaclarationDTO;
	}

}
