package com.csipl.hrms.service.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.payroll.EpfDTO;
import com.csipl.hrms.dto.payroll.GratuityDTO;
import com.csipl.hrms.model.payroll.Epf;
import com.csipl.hrms.model.payroll.Gratuaty;
import com.csipl.hrms.service.payroll.repository.GratuityRepository;

@Service("gratuityService")
public class GratuityServiceImpl implements GratuityService {

	@Autowired
	private GratuityRepository gratuityRepository;
	/**
	 * to get List of gratuity Objects from database based on companyId
	 */
	@Override
	public  Gratuaty  getAllGratuity(Long companyId) {
		return gratuityRepository.findAllGratuity(companyId);

	}
	/**
	 * Save OR update gratuaty object  into Database 
	 */
	@Transactional
	@Override
	public Gratuaty save(Gratuaty gratuaty) {
//		Gratuaty gratuatyOldObj = gratuityRepository.findOne(gratuaty.getGraduityId());
//		gratuatyOldObj.setActiveStatus(StatusMessage.DEACTIVE_CODE);
//		 gratuityRepository.save(gratuatyOldObj);
//		 gratuaty.setGraduityId(null);
		return gratuityRepository.save(gratuaty);
	}
 	 
	public  List<GratuityDTO> findAllGratuityByDescEffectiveDate(Long companyId){
		List<Gratuaty>gratuityList = gratuityRepository.findAllGratuityByDescEffectiveDate(companyId);
		if(gratuityList!=null) {
			return	gratuityList.stream().map((Gratuaty gr) ->
			new GratuityDTO(gr.getGraduityId(), gr.getEffectiveDate(), gr.getNoOfDays(), gr. getNoOfDaysDevide(), gr.getNoOfMonths(), gr.getActiveStatus(), gr.getUserId(), gr.getUserIdUpdate(), gr.getCompany().getCompanyId(), gr.getDateCreated())
			).collect(Collectors.toList());                                                                                                                                                                                                                                                                
		}
		return null;
	}
	
	@Transactional
	public Gratuaty saveGratuatys(Gratuaty existingGratuity , Gratuaty newGratuity) {
		gratuityRepository.save(existingGratuity);
		return gratuityRepository.save(newGratuity);
	}
}
