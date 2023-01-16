package com.csipl.hrms.service.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.common.model.EmailNotificationMaster;

@Repository
public interface EmailNotificationRepository extends CrudRepository<EmailNotificationMaster, Long> {
	@Query(" from EmailNotificationMaster where  companyId=?1 ORDER BY mailId  ASC")
	List<EmailNotificationMaster> findAll(Long companyId);

	@Query(" from EmailNotificationMaster where  mailType=?1 ")
	 EmailNotificationMaster findAllByStatus(String mailType);

	@Query(" from EmailNotificationMaster where  companyId=?1  AND mailId=?2 ")
	EmailNotificationMaster findById(Long companyId, Long mailId);

}
