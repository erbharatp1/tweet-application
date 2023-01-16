package com.csipl.hrms.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.MassCommunication;
import com.csipl.hrms.service.employee.repository.MassCommunicationRepository;

@Service("massCommunicationService")
@Transactional
public class MassCommunicationServiceImpl implements MassCommunicationService {

	@Autowired
	private MassCommunicationRepository massCommunicationRepository;

	/**
	 * Method performed save OR update operation
	 */
	@Override
	public MassCommunication save(MassCommunication massCommunication) {
		if (massCommunication.getMassCommunicationId() != null && massCommunication.getMassCommunicationId() != 0) {
			massCommunicationRepository.deleteMassCommById(massCommunication.getMassCommunicationId());
		}
		return massCommunicationRepository.save(massCommunication);
	}

	@Override
	public MassCommunication findMassComm(Long massCommunicationId) {

		return massCommunicationRepository.getMassCommunicationById(massCommunicationId);
	}

	@Override
	public List<MassCommunication> getAllMassCommDate(Long companyId, Long departmentId) {

		return massCommunicationRepository.getAllMassCommDate(companyId, departmentId);
	}

}