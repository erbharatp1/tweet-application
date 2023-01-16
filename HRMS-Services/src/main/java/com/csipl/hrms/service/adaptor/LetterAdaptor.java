package com.csipl.hrms.service.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.LetterDTO;
import com.csipl.hrms.model.employee.Letter;

/**
 * bharat
 */
@Component
public class LetterAdaptor implements Adaptor<LetterDTO, Letter> {

	@Override
	public List<Letter> uiDtoToDatabaseModelList(List<LetterDTO> ltrList) {
		return ltrList.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<LetterDTO> databaseModelToUiDtoList(List<Letter> ltrList) {
		return ltrList.stream().map(item->databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public Letter uiDtoToDatabaseModel(LetterDTO ltrDTO) {
		Letter ltr = new Letter();
		ltr.setActiveStatus(ltrDTO.getActiveStatus());
		ltr.setCompanyId(ltrDTO.getCompanyId());
		ltr.setDateCreated(ltrDTO.getDateCreated());
		ltr.setLetterDecription(ltrDTO.getLetterDecription().replace("<p>", "<p style=\"margin:0;font-size: 11px;line-height: 16px;\">").replace("<strong>", "<strong style=\"margin:0;font-size: 11px;line-height: 16px;\">"));
		ltr.setLetterId(ltrDTO.getLetterId());
		ltr.setLetterName(ltrDTO.getLetterName());
		ltr.setLetterType(ltrDTO.getLetterType());
		ltr.setUserId(ltrDTO.getUserId());
		ltr.setGradeId(ltrDTO.getGradeId());
		ltr.setEnableGrade(ltrDTO.getEnableGrade());
		ltr.setUserIdUpdate(ltrDTO.getUserIdUpdate());
		ltr.setDateUpdate(ltrDTO.getDateUpdate());
		return ltr;
	}

	@Override
	public LetterDTO databaseModelToUiDto(Letter letter) {
		LetterDTO ltrDTO = new LetterDTO();
		ltrDTO.setCompanyId(letter.getCompanyId());
		ltrDTO.setDateCreated(letter.getDateCreated());
		ltrDTO.setLetterDecription(letter.getLetterDecription());
		ltrDTO.setLetterId(letter.getLetterId());
		ltrDTO.setLetterName(letter.getLetterName());
		ltrDTO.setLetterType(letter.getLetterType());
		ltrDTO.setUserId(letter.getUserId());
		ltrDTO.setGradeId(letter.getGradeId());
		ltrDTO.setUserIdUpdate(letter.getUserIdUpdate());
		ltrDTO.setActiveStatus(letter.getActiveStatus());
		ltrDTO.setEnableGrade(letter.getEnableGrade());
		return ltrDTO;
	}


}
