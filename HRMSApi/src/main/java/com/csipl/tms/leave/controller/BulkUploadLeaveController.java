package com.csipl.tms.leave.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.employee.EmployeeDTO;
import com.csipl.tms.bulkupload.service.BulkUploadLeaveService;
import com.csipl.tms.common.bulkupload.BulkUploadLeaveUtil;
import com.csipl.tms.leave.service.LeaveTypeMasterService;
import com.csipl.tms.model.leave.EmployeeOpeningLeaveMaster;
import com.csipl.tms.model.leave.TMSLeaveTypeMaster;

@RestController
public class BulkUploadLeaveController {
	private static final Logger logger = LoggerFactory.getLogger(BulkUploadLeaveController.class);

	@Autowired
	BulkUploadLeaveService bulkUploadLeaveService;

	@Autowired
	LeaveTypeMasterService leaveTypeMasterService;

	/**
	 * @param file        This is the first parameter for taking file Input
	 * @param employeeDto This is the second parameter for getting Employee Object
	 *                    from UI
	 * @param req         This is the third parameter to maintain user session
	 * @throws PayRollProcessException
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 * @throws CustomException
	 * @throws ErrorHandling
	 */

	@RequestMapping(value = "/bulkUploadLeave", method = RequestMethod.POST, consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ErrorHandling employeeUpload(@RequestPart("uploadFile") MultipartFile file,
			@RequestPart("info") EmployeeDTO employeeDto)
			throws PayRollProcessException, IOException, EncryptedDocumentException, InvalidFormatException {
		logger.info("bulkUploadOfEmployeeLeave is calling : " + "" + ": uploadFile" + file);

		BulkUploadLeaveUtil bulkUploadUtil = new BulkUploadLeaveUtil();
		ErrorHandling error = new ErrorHandling();
		Map<Integer, StringBuilder> errorMap = new HashMap<Integer, StringBuilder>();
		Map<Long, TMSLeaveTypeMaster> leaveTypeMap = loadLeaveTypeInMap(employeeDto.getCompanyId());

		if (employeeDto.getDropdownId().equals("Leave Opening") && file != null) {
			logger.info("============9");
			// Map<Long,String> headerMap = loadExcelHeaderInMap("EIA");
			List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList = bulkUploadUtil
					.saveEmployeeLeaveOpeningBalance(file, employeeDto, leaveTypeMap, errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============10");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} else {
				logger.info("============11");
				if (employeeLeaveOpeningList.size() > 0) {
					bulkUploadLeaveService.saveEmployeeLeaveOpeningBalance(employeeLeaveOpeningList, employeeDto,
							errorMap);
					logger.info("Employee Leave Opening File Uploaded Successfully.............");
				} else {
					logger.info("Employee Leave Opening File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
				}

			}

		} else if (employeeDto.getDropdownId().equals("Leave Carryforward") && file != null) {
			logger.info("============12");
			List<EmployeeOpeningLeaveMaster> employeeLeaveOpeningList = bulkUploadUtil
					.saveEmployeeLeaveCarryForward(file, employeeDto, leaveTypeMap, errorMap);
			if (errorMap.size() > 0) {
				StringBuilder builder = new StringBuilder();

				String message, value = null;
				for (Map.Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
					message = entry.getValue().substring(0, entry.getValue().lastIndexOf(","));
					builder.append("For row - " + entry.getKey() + " " + message + " is missing,  ");
				}
				logger.info("============18");
				error.setMessage("There is problem" + builder);
				throw new PayRollProcessException(builder.toString());
			} else {
				logger.info("============19");
				if (employeeLeaveOpeningList.size() > 0) {
					bulkUploadLeaveService.saveEmployeeLeaveCarryForward(employeeLeaveOpeningList, employeeDto,
							errorMap);
					logger.info("Employee Leave Carryforward File Uploaded Successfully.............");
				} else {
					logger.info("Employee Leave Carryforward File can not upload");
					throw new PayRollProcessException("There is problem in excel,Please upload correct excel format ");
				}

			}
		}

		else {
			StringBuilder builder = new StringBuilder();
			builder.append("Please upload file ");
			error.setMessage("" + builder);
		}
		return error;
	}

	private Map<Long, TMSLeaveTypeMaster> loadLeaveTypeInMap(Long companyId) {
		List<TMSLeaveTypeMaster> leaveTypes = leaveTypeMasterService.findAllLeaveTypeMaster(companyId);
		Map<Long, TMSLeaveTypeMaster> leaveTypeMap = new HashMap<Long, TMSLeaveTypeMaster>();

		for (TMSLeaveTypeMaster l : leaveTypes) {
			// stateId = city.getState().getStateId();
			// key = city.getCityName()+"#"+stateId;
			leaveTypeMap.put(l.getLeaveId(), l);
		}
		return leaveTypeMap;
	}
}
