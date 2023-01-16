package com.csipl.hrms.service.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.csipl.hrms.model.employee.MassCommunication;

public interface MassCommunicationRepository extends CrudRepository<MassCommunication, Long> {
	public static final String DELETE_COMPANY_ANNOUCEMENT_BY_ID = "Delete  From CompanyAnnouncement Where massCommunicationId=?1";

	@Query(" from MassCommunication where companyId=?1 ORDER BY  massCommunicationId  DESC ")
	List<MassCommunication> findAllMassCommunications(Long companyId);

	/**
	 * getMassCommunicationById}
	 */
	@Query(" from MassCommunication where massCommunicationId=?1 ORDER BY  massCommunicationId  DESC ")
	MassCommunication getMassCommunicationById(Long massCommunicationId);

	//@Query(value = "SELECT * FROM MassCommunication mc where date(mc.dateTo)>= date(now()) AND mc.companyId=?1 AND   mc.departmentId=?2 ORDER by mc.massCommunicationId DESC ", nativeQuery = true)
	@Query(value = "SELECT * FROM MassCommunication mc JOIN CompanyAnnouncement ca on mc.massCommunicationId=ca.massCommunicationId  where date(mc.dateTo)>= date(now()) AND mc.companyId=?1 AND   ca.departmentId=?2 ORDER by mc.massCommunicationId DESC ", nativeQuery = true)
	List<MassCommunication> getAllMassCommDate(Long companyId,Long departmentId);
	
	@Modifying
	@Query(value = DELETE_COMPANY_ANNOUCEMENT_BY_ID, nativeQuery = true)
	public void deleteMassCommById(Long massCommunicationId);
}
