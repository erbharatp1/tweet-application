package com.csipl.hrms.service.recruitement.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.recruitment.QuestionTamplatesDTO;
import com.csipl.hrms.dto.recruitment.QuestionTamplatesOptionsDTO;
import com.csipl.hrms.model.recruitment.QuestionTamplates;
import com.csipl.hrms.model.recruitment.QuestionTamplatesOptions;
import com.csipl.hrms.service.adaptor.Adaptor;

/**
 * @author Bharat
 *
 */
@Component
public class QuestionTamplatesAdaptor implements Adaptor<QuestionTamplatesDTO, QuestionTamplates> {

	@Override
	public List<QuestionTamplates> uiDtoToDatabaseModelList(List<QuestionTamplatesDTO> uiobj) {
		// TODO Auto-generated method stub
		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<QuestionTamplatesDTO> databaseModelToUiDtoList(List<QuestionTamplates> dbobj) {
		// TODO Auto-generated method stub
		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public QuestionTamplates uiDtoToDatabaseModel(QuestionTamplatesDTO uiobj) {
		QuestionTamplates questionTamplates = null;
		questionTamplates = new QuestionTamplates();
		questionTamplates.setCompanyId(uiobj.getCompanyId());
		questionTamplates.setDateCreated(uiobj.getDateCreated());
		questionTamplates.setDateUpdate(uiobj.getDateUpdate());
		questionTamplates.setDesirableAnswer(uiobj.getDesirableAnswer());
		questionTamplates.setQuestionDescription(uiobj.getQuestionDescription());
		questionTamplates.setQuestionTamplateId(uiobj.getQuestionTamplateId());
		questionTamplates.setStatus(uiobj.getStatus());
		questionTamplates.setUserId(uiobj.getUserId());
		questionTamplates.setUserIdUpdate(uiobj.getUserIdUpdate());
		if (uiobj.getQuestionTamplateId() != null && uiobj.getQuestionTamplateId() != 0) {
			questionTamplates.setQuestionTamplateId(uiobj.getQuestionTamplateId());
		}
		List<QuestionTamplatesOptions> questionTampOptList = new ArrayList<QuestionTamplatesOptions>();
//		if (!uiobj.getQuestionTamplatesOptions().isEmpty()) {
			for (QuestionTamplatesOptionsDTO questionTamplatesOptions : uiobj.getQuestionTamplatesOptions()) {
				QuestionTamplatesOptions questionTamplatesOpt = null;
				questionTamplatesOpt = new QuestionTamplatesOptions();
				questionTamplatesOpt.setOptionId(questionTamplatesOptions.getOptionId());
				questionTamplatesOpt.setCompanyId(questionTamplatesOptions.getCompanyId());
				questionTamplatesOpt.setStatus(questionTamplatesOptions.getStatus());
				questionTamplatesOpt.setUserId(questionTamplatesOptions.getUserId());
				questionTamplatesOpt.setDateCreated(questionTamplatesOptions.getDateCreated());
				questionTamplatesOpt.setDateUpdate(questionTamplatesOptions.getDateUpdate());
				questionTamplatesOpt.setQuestionTamplates(questionTamplates);
				questionTampOptList.add(questionTamplatesOpt);

			}
		//}
		questionTamplates.setQuestionTamplatesOptions(questionTampOptList);
		return questionTamplates;
	}

	@Override
	public QuestionTamplatesDTO databaseModelToUiDto(QuestionTamplates questionTamplates) {
		QuestionTamplatesDTO questionTamplatesDTO = null;
		questionTamplatesDTO = new QuestionTamplatesDTO();
		List<QuestionTamplatesOptions> questionTamplatesOptions = questionTamplates.getQuestionTamplatesOptions();
		List<QuestionTamplatesOptionsDTO> questionTampOptList = new ArrayList<QuestionTamplatesOptionsDTO>();
		questionTamplatesDTO.setCompanyId(questionTamplates.getCompanyId());
		questionTamplatesDTO.setDateCreated(questionTamplates.getDateCreated());
		questionTamplatesDTO.setDateUpdate(questionTamplates.getDateUpdate());
		questionTamplatesDTO.setDesirableAnswer(questionTamplates.getDesirableAnswer());
		questionTamplatesDTO.setQuestionDescription(questionTamplates.getQuestionDescription());
		questionTamplatesDTO.setQuestionTamplateId(questionTamplates.getQuestionTamplateId());
		questionTamplatesDTO.setStatus(questionTamplates.getStatus());
		questionTamplatesDTO.setUserId(questionTamplates.getUserId());
		questionTamplatesDTO.setUserIdUpdate(questionTamplates.getUserIdUpdate());
		for (QuestionTamplatesOptions questionTempOpt : questionTamplatesOptions) {
			QuestionTamplatesOptionsDTO questionTempOptionDTO = new QuestionTamplatesOptionsDTO();
			questionTempOptionDTO.setCompanyId(questionTempOpt.getCompanyId());
			questionTempOptionDTO.setDateCreated(questionTempOpt.getDateCreated());
			questionTempOptionDTO.setOptionId(questionTempOpt.getOptionId());
			questionTempOptionDTO.setUserId(questionTempOpt.getUserId());
			questionTempOptionDTO.setUserIdUpdate(questionTempOpt.getUserIdUpdate());
			questionTempOptionDTO.setQuestionTamplateId(questionTempOpt.getQuestionTamplates().getQuestionTamplateId());
			questionTempOptionDTO.setStatus(questionTempOpt.getStatus());
			questionTempOptionDTO.setCompanyId(questionTempOpt.getCompanyId());

			questionTampOptList.add(questionTempOptionDTO);
		}
		questionTamplatesDTO.setQuestionTamplatesOptions(questionTampOptList);
		return questionTamplatesDTO;
	}

}
