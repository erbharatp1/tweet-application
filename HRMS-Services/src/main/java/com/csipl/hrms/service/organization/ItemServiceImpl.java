package com.csipl.hrms.service.organization;



import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.model.organisation.Item;
import com.csipl.hrms.service.employee.repository.ProfessionalInformationRepository;
import com.csipl.hrms.service.organization.repository.ItemRepository;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * Method performed delete operation,delete item object from  Database
 	 */
	

	/**
	 * Method performed save operation, to save List of items into Database
 	 */
	@Override
	public Item save(Item itemList) {
		return itemRepository.save(itemList);
	}
	/**
	 * Method performed fetch  operation, get  List of items from  Database based on companyId
 	 */
	@Override
	public List<Item> findAllItems(Long companyId) {
		return itemRepository.findAllItems(companyId);
	}

	@Override
	@Transactional
	public int delete(Long itemId) {
		// TODO Auto-generated method stub
	return	itemRepository.deleteItem(itemId,StatusMessage.DEACTIVE_CODE);
	}
}
