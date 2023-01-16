package com.csipl.hrms.service.recruitement.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.recruitment.PositionApprovalPersonDTO;
import com.csipl.hrms.model.recruitment.PositionApprovalPerson;
import com.csipl.hrms.service.adaptor.Adaptor;

@Component
public class PositionApprovalAdaptor implements Adaptor<PositionApprovalPersonDTO, PositionApprovalPerson> {

	@Override
	public List<PositionApprovalPerson> uiDtoToDatabaseModelList(List<PositionApprovalPersonDTO> uiobj) {

		return uiobj.stream().map(item -> uiDtoToDatabaseModel(item)).collect(Collectors.toList());
	}

	@Override
	public List<PositionApprovalPersonDTO> databaseModelToUiDtoList(List<PositionApprovalPerson> dbobj) {

		List<PositionApprovalPersonDTO> positionApprovalPersonDTO = new ArrayList<PositionApprovalPersonDTO>();
		for (PositionApprovalPerson positionApprovalPerson : dbobj) {
			positionApprovalPersonDTO.add(databaseModelToUiDto(positionApprovalPerson));
		}
		return positionApprovalPersonDTO;
	}

	@SuppressWarnings("static-access")
	@Override
	public PositionApprovalPerson uiDtoToDatabaseModel(PositionApprovalPersonDTO uiobj) {

		PositionApprovalPerson positionApprovalPerson = new PositionApprovalPerson();

		positionApprovalPerson.setPositionApprovalPersonId(uiobj.getPositionApprovalPersonId());
		positionApprovalPerson.setEmployeeId(uiobj.getEmployeeId());
		if (uiobj.getEmployeeName() != null) {
			positionApprovalPerson.setEmployeeName(uiobj.getEmployeeName());
		} else {
			positionApprovalPerson.setEmployeeName(uiobj.getFirstName() + " " + uiobj.getLastName());
		}

		if (uiobj.getPositionApprovalPersonId() != null) {
			positionApprovalPerson.setUpatedDate(new Date());
		}

		positionApprovalPerson.setDateCreated(uiobj.getDateCreated());
		positionApprovalPerson.setUserId(uiobj.getUserId());
		positionApprovalPerson.setUserIdUpdate(uiobj.getUserIdUpdate());
		StatusMessage sm = new StatusMessage();
		boolean status = false;

		if (uiobj.getDeactiveEmployeeList().size() > 0l) {
			for (Long empId : uiobj.getDeactiveEmployeeList()) {
				if (empId.equals(uiobj.getEmployeeId())) {
					status = true;
				}
			}
		}

		if (status == true) {
			positionApprovalPerson.setStatus(sm.DEACTIVE_CODE);
		} else {
			positionApprovalPerson.setStatus(sm.ACTIVE_CODE);
		}

		return positionApprovalPerson;
	}

	@Override
	public PositionApprovalPersonDTO databaseModelToUiDto(PositionApprovalPerson dbobj) {

		PositionApprovalPersonDTO positionApprovalPersonDTO = new PositionApprovalPersonDTO();

		positionApprovalPersonDTO.setPositionApprovalPersonId(dbobj.getPositionApprovalPersonId());
		positionApprovalPersonDTO.setEmployeeId(dbobj.getEmployeeId());

		if (dbobj.getEmployeeName() != null) {
			positionApprovalPersonDTO.setEmployeeName(dbobj.getEmployeeName());
		} else {
			positionApprovalPersonDTO.setEmployeeName(dbobj.getFirstName() + " " + dbobj.getLastName());
		}

		positionApprovalPersonDTO.setUpatedDate(dbobj.getUpatedDate());
		positionApprovalPersonDTO.setDateCreated(dbobj.getDateCreated());
		positionApprovalPersonDTO.setUserId(dbobj.getUserId());
		positionApprovalPersonDTO.setUserIdUpdate(dbobj.getUserIdUpdate());
		positionApprovalPersonDTO.setStatus(dbobj.getStatus());

		return positionApprovalPersonDTO;
	}

}
