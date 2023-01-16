package com.csipl.hrms.recruitement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.dto.recruitment.PositionApprovalPersonDTO;
import com.csipl.hrms.model.recruitment.PositionApprovalPerson;
import com.csipl.hrms.service.recruitement.PositionApprovalService;
import com.csipl.hrms.service.recruitement.adaptor.PositionApprovalAdaptor;

@RestController
@RequestMapping("/positionApproval")
public class PositionApprovalController {

	private static final Logger logger = LoggerFactory.getLogger(PositionApprovalController.class);

	@Autowired
	private PositionApprovalService positionApprovalService;

	@Autowired
	private PositionApprovalAdaptor positionApprovalAdaptor;

	/**
	 * Method performed save operation
	 * 
	 */
	@PostMapping(path = "/savePositionApproval")
	public void savePositionApproval(@RequestBody List<PositionApprovalPersonDTO> positionApprovalDTO,
			HttpServletRequest req) {

		logger.info("savePositionApproval is calling : " + positionApprovalDTO);

		List<PositionApprovalPerson> positionApproval = positionApprovalAdaptor
				.uiDtoToDatabaseModelList(positionApprovalDTO);

		positionApprovalService.savePositionApproval(positionApproval);

		logger.info("savePositionApproval is end  :" + positionApproval);
	}

	@GetMapping(path = "/getAllPositionApprovals")
	public List<PositionApprovalPersonDTO> getAllPositionApprovals() {
		logger.info("getAllPositionApprovals is calling ");
		List<PositionApprovalPerson> positionApprovalPerson = positionApprovalService.findAllPositionApprovals();

		return positionApprovalAdaptor.databaseModelToUiDtoList(positionApprovalPerson);

	}

}
