package com.csipl.hrms.service.common.adaptor;

import java.util.Date;
import java.util.List;

import com.csipl.hrms.dto.common.TokenMasterDTO;
import com.csipl.hrms.model.common.TokenMaster;
import com.csipl.hrms.model.common.TokenTypeMaster;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.service.adaptor.Adaptor;

public class TokenMasterAdaptor  implements Adaptor<TokenMasterDTO,TokenMaster>{

	@Override
	public List<TokenMaster> uiDtoToDatabaseModelList(List<TokenMasterDTO> uiobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TokenMasterDTO> databaseModelToUiDtoList(List<TokenMaster> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TokenMaster uiDtoToDatabaseModel(TokenMasterDTO tokenMasterDTO) {
		TokenMaster tokenMaster=new TokenMaster();
		tokenMaster.setCreatedBy(tokenMasterDTO.getCreatedBy());
		tokenMaster.setDateCreated(new Date());
		tokenMaster.setTokenId(tokenMasterDTO.getTokenId());
		tokenMaster.setTokenValue(tokenMasterDTO.getTokenValue());
		
		TokenTypeMaster tokenTypeMaster=new TokenTypeMaster();
		tokenTypeMaster.setTokenTypeId(tokenMasterDTO.getTokentypeId());
		tokenMaster.setTokenTypeMaster(tokenTypeMaster);
		
		User user =new User();
		user.setUserId(tokenMasterDTO.getUserId());
		tokenMaster.setUser(user);
		return tokenMaster;
	}

	@Override
	public TokenMasterDTO databaseModelToUiDto(TokenMaster tokenMaster) {
		TokenMasterDTO tokenMasterDTO=new TokenMasterDTO();
		tokenMasterDTO.setCreatedBy(tokenMaster.getCreatedBy());
		tokenMasterDTO.setDateCreated(tokenMaster.getDateCreated());
		tokenMasterDTO.setTokenId(tokenMaster.getTokenId());
		tokenMasterDTO.setTokentypeId(tokenMaster.getTokenTypeMaster().getTokenTypeId());
		tokenMasterDTO.setTokenValue(tokenMaster.getTokenValue());
		tokenMasterDTO.setUserId(tokenMaster.getUser().getUserId());
		
		return tokenMasterDTO;
	}

}
