package com.csipl.hrms.service.adaptor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.csipl.hrms.dto.employee.AuthorizedSignatoryDTO;
import com.csipl.hrms.model.employee.AuthorizedSignatory;

/**
 * 
 * @author Bharat
 *
 */
@Component
public class AuthorizedSignatoryAdaptor implements Adaptor<AuthorizedSignatoryDTO, AuthorizedSignatory> {
	 
 
	@Override
	public List<AuthorizedSignatory> uiDtoToDatabaseModelList(List<AuthorizedSignatoryDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<AuthorizedSignatoryDTO> databaseModelToUiDtoList(List<AuthorizedSignatory> dbobj) {

		return dbobj.stream().map(item -> databaseModelToUiDto(item)).collect(Collectors.toList());
	}

	@Override
	public AuthorizedSignatory uiDtoToDatabaseModel(AuthorizedSignatoryDTO uiobj) {
		AuthorizedSignatory signatory = new AuthorizedSignatory();
		BeanUtils.copyProperties(uiobj, signatory);
		return signatory;
	}

	@Override
	public AuthorizedSignatoryDTO databaseModelToUiDto(AuthorizedSignatory dbobj) {
		AuthorizedSignatoryDTO signatoryDTO = new AuthorizedSignatoryDTO();
		BeanUtils.copyProperties(dbobj, signatoryDTO);
		return signatoryDTO;
	}

}
