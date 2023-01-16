package com.csipl.hrms.service.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csipl.hrms.model.authoriztion.SubMenuMaster;



public interface SubMenuMasterRepository extends CrudRepository<SubMenuMaster, Long> {
	@Query("from SubMenuMaster where  menuId=?1")
    public List<SubMenuMaster> findSubMenu(Long menuId);
	

}
