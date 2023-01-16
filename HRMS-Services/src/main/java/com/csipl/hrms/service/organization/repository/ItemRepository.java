package com.csipl.hrms.service.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.csipl.hrms.model.organisation.Item;


public interface ItemRepository extends CrudRepository<Item, Long>{
	@Query(" from Item where companyId=?1 AND activeStatus='AC' ORDER BY  itemId  DESC")
    public List<Item> findAllItems(Long companyId);

	
	@Modifying
	@Query("Update Item t SET t.activeStatus=:status WHERE t.itemId=:itemId")
	public int deleteItem(@Param("itemId") Long ticketTypeId, @Param("status") String status);
}
