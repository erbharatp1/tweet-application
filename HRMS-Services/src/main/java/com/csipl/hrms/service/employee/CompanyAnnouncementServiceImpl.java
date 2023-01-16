package com.csipl.hrms.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.employee.CompanyAnnouncement;
import com.csipl.hrms.service.employee.repository.CompanyAnnouncementRepository;

@Service("companyAnnouncementService")
@Transactional
public class CompanyAnnouncementServiceImpl implements CompanyAnnouncementService {
 
	@Autowired
	private CompanyAnnouncementRepository companyAnnouncementRepository;

	@Override
	public  CompanyAnnouncement  save( CompanyAnnouncement  companyAnnouncement) {
		// TODO Auto-generated method stub
		return   companyAnnouncementRepository.save(companyAnnouncement);
	}
	
	 
}