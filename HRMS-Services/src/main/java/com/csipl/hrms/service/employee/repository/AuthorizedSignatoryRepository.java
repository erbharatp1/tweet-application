package com.csipl.hrms.service.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.AuthorizedSignatory;

@Repository("authorizedSignatoryRepository")
public interface AuthorizedSignatoryRepository extends JpaRepository<AuthorizedSignatory, Long> {
	@Query(value = "FROM AuthorizedSignatory ar WHERE  ar.activeStatus='AC' AND ar.letterId=?1")
	AuthorizedSignatory findAuthorizedSignatoryById(Long letterId);

}
