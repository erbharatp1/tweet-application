package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.MenuMaster;



public interface MenuMasterRepository  extends CrudRepository<MenuMaster, Long>{
//	 FROM MenuMaster WHERE companyId = 1
	@Query(" from MenuMaster where  companyId=?1")
    public List<MenuMaster> findAllMenu(Long companyId);
}
