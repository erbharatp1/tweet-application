package com.csipl.hrms.service.payroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.dto.payrollprocess.PayOutDTO;
 import com.csipl.hrms.service.adaptor.PayOutAdaptor;
 import com.csipl.hrms.service.payroll.repository.PayOutRepository;

@Transactional
@Service("payOutService")
public class PayOutServiceImpl implements PayOutService {
	
	PayOutAdaptor ppayOutAdaptor=new PayOutAdaptor();
	@Autowired
	PayOutRepository payOutRepository;

	@Override
	public List<PayOutDTO> getPayOutsBasedOnProcessMonthAndEmployeeId(Long employeeId, String processMonth) {
		List<Object[]> payOutDtoObjList = payOutRepository.getPayOutsBasedOnProcessMonthAndEmployeeId(employeeId, processMonth)	;
		List<PayOutDTO> payOutList= ppayOutAdaptor.payOutObjectListTopayOutDtoListConversion(payOutDtoObjList);

		return payOutList;
	}
}
