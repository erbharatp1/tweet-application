package com.csipl.hrms.service.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.common.model.EmailConfiguration;

@Repository
@Transactional
public interface EmailConfigurationRepository extends CrudRepository<EmailConfiguration, Long> {
	@Transactional(readOnly = true)
	@Query(" from EmailConfiguration where activeStatus='AC' ")
	EmailConfiguration findEMail();

	@Query(" from EmailConfiguration ec where ec.activeStatus='AC' and ec.companyId=?1")
	EmailConfiguration getById(Long companyId);
}
