package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.authoriztion.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
	@Query("from UserRole ")
	public List<UserRole> findAllUserRoles();

	@Query("from UserRole where userId=?1")
	public List<UserRole> findUserRoles(Long userId);

	@Query("from UserRole where userId=?1")
	public UserRole getUserRoles(Long userId);

	
	
	@Modifying
	 @Query("Update UserRole d SET d.roleMaster.roleId=:roleId WHERE d.userRolesSrNo=:userRolesSrNo")
	public void updateByIds(@Param("roleId") Long roleId, @Param("userRolesSrNo") Long userRolesSrNo);

}
