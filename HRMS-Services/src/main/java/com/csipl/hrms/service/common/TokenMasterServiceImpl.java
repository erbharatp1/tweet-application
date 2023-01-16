package com.csipl.hrms.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.model.common.TokenMaster;
import com.csipl.hrms.service.common.repository.TokenMasterRepository;
@Service("tokenService")
public class TokenMasterServiceImpl implements TokenMasterService {

	@Autowired
	TokenMasterRepository tokenMasterRepository;
	
	
	@Override
	public TokenMaster save(TokenMaster tokenMaster) {
		
		int count = tokenMasterRepository.getUserCount(tokenMaster.getUser().getUserId(),tokenMaster.getTokenTypeMaster().getTokenTypeId());
		if(count == 0) {
			return tokenMasterRepository.save(tokenMaster);
		}else {
			 tokenMasterRepository.updateTokenMaster(tokenMaster.getUser().getUserId(),tokenMaster.getTokenValue(),tokenMaster.getTokenTypeMaster().getTokenTypeId());
			 return tokenMaster;
		}
	
	}

}
