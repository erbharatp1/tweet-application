package com.csipl.hrms.service.employee;

import java.util.List;

import com.csipl.hrms.model.employee.MassCommunication;

public interface MassCommunicationService {

	public MassCommunication save(MassCommunication massCommunication);
	
	/**
	 * findMassComm
	 */
	public MassCommunication findMassComm(Long massCommunicationId);
	public List<MassCommunication> getAllMassCommDate(Long companyId,Long departmentId);
}
