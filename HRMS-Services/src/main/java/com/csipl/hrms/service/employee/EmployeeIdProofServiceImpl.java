package com.csipl.hrms.service.employee;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.csipl.common.services.dropdown.DropDownCache;
import com.csipl.common.services.dropdown.repository.DropDownHdRepository;
import com.csipl.hrms.common.enums.DropDownEnum;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.common.MandatoryInfoCheck;
import com.csipl.hrms.model.employee.EmployeeIdProof;
import com.csipl.hrms.model.employee.MasterBook;
import com.csipl.hrms.service.employee.repository.EmployeeIdProofRepository;
import com.csipl.hrms.service.employee.repository.MasterBookRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.csipl.hrms.service.organization.repository.MandatoryInfoCheckRepository;

@Service("employeeIdProofService")
public class EmployeeIdProofServiceImpl implements EmployeeIdProofService {
	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeIdProofServiceImpl.class);

	@Autowired
	private EmployeeIdProofRepository employeeIdProofRepository;
	@Autowired
	MandatoryInfoCheckRepository mandatoryInfoCheckRepository;
	@Autowired
	private MasterBookRepository masterBookRepository;
	@Autowired
	StorageService storageService;
	
	
	/**
	 * Method performed save OR update operation
	 * @throws ErrorHandling 
	 */
	@Override
	public List<EmployeeIdProof> save(List<EmployeeIdProof> employeeIdProofList,HttpServletRequest request) throws ErrorHandling {
		
		/*
		 * first check EmployeeIdProof related data already exist or not
		 */
		if(employeeIdProofList!=null) {
			for(EmployeeIdProof es : employeeIdProofList) {
				if(es.getIdNumber()!=null && es.getIdNumber()!="") {
				int count=employeeIdProofRepository.checkIdProofExist(es.getEmployee().getEmployeeId(), es.getIdTypeId(), es.getIdNumber());
				if(count > 0 ) {
					throw new ErrorHandling(DropDownCache.getInstance().getDropDownValue(
							DropDownEnum.SelectIdType.getDropDownName(), es.getIdTypeId()) +" already exist " );
				}
				}
			}
		}
		
		logger.info("EmployeeIdProofList is ===== " + employeeIdProofList);
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		Long empId = 0l;
		Long userIdUpdate = 0l;
		String idType;
		boolean flag = false;
		boolean uiFlag = false;
		
		//checking pan card is in list or not
		for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
			empId = employeeIdProof.getEmployee().getEmployeeId();
			logger.info("empId for mandatoryInfoCheck is  : " + empId);
			userIdUpdate = employeeIdProof.getUserIdUpdate();
			logger.info("userIdUpdate for mandatoryInfoCheck is  : " + userIdUpdate);
			idType = employeeIdProof.getIdTypeId();
			logger.info("idType for mandatoryInfoCheck is  : " + idType);
			if (employeeIdProof.getIdTypeId().equals("PA")) {
				flag = true;
				logger.info("flag for mandatoryInfoCheck is  : " + flag);
				break;
			}
			
			if (employeeIdProof.getIdTypeId().equals("AC")) {
				uiFlag = true;
				logger.info("flag for mandatoryInfoCheck is  : " + uiFlag);
				break;
			}
			
		}

		if (flag) {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setPA("YES");
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);

		} 
		else {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setPA(null);
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
		}
		
		if (uiFlag) {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setUi("YES");
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);

		} 
		else {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setUi(null);
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
		}

		
		MultipartHttpServletRequest multiPartRequest = new DefaultMultipartHttpServletRequest(request);

		multiPartRequest = (MultipartHttpServletRequest) request;
		multiPartRequest.getParameterMap();

		String bookCode = "EMPNO";
		//
		int index = 0;
		int count = 0;
		for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
			Iterator<String> itr = multiPartRequest.getFileNames();
			System.out.println("----designDocument.getDesignName() :::;::" + employeeIdProof.getDocumentName());
			if (employeeIdProof.getDocumentName() != null && !("").equals(employeeIdProof.getDocumentName())) {
				System.out.println("----inner for loop ");
				int fileIndex = 0;
				while (itr.hasNext()) {
					MultipartFile mFile = multiPartRequest.getFile(itr.next());
					System.out.println("fileIndex  "+fileIndex);
					System.out.println("Index  "+index);
					if (fileIndex == index) {
						MasterBook masterBook = masterBookRepository.findMasterBook(1l, bookCode);
						BigDecimal lastNumberValue;
						lastNumberValue = masterBook.getLastNo();
						long longValue;
						longValue = lastNumberValue.longValue() + 1;
						BigDecimal newDecimalValue = new BigDecimal(longValue);
						String fileName=masterBook.getPrefixBook() + newDecimalValue;
						String extension = FilenameUtils.getExtension(mFile.getOriginalFilename());
						fileName = fileName + "." + extension;
						//logger.info(" File with extension " + fileName);
						
//						String path = File.separator + "Document" + File.separator + "Employee"+ File.separator + "EmployeeIdproofDoc";
						String path = storageService.createFilePath(HrmsGlobalConstantUtil.EMPLOYEE_ID_PROOF_DOC);
						String dbPath = path + File.separator + fileName;
					   storageService.store(mFile, path, fileName);
					   masterBook.setLastNo(newDecimalValue);
					  masterBookRepository.save(masterBook);
						
						System.out.println("FileName is " + mFile.getOriginalFilename());
						employeeIdProof.setIdProofDoc(dbPath);
						employeeIdProof.setDocumentName(fileName);
						System.out.println("store  end ()");
						
					}
					fileIndex++;
					System.out.println(".while end ()");
				}
				
				index++;
			} 
			
			
		
		}
		
		List<EmployeeIdProof> employeeIdProofInfos = (List<EmployeeIdProof>) employeeIdProofRepository
				.save(employeeIdProofList);
		return employeeIdProofInfos;
	}

	/**
	 * to get List of EmployeeIdProof from database based on employeeId
	 */
	@Override
	public List<EmployeeIdProof> findAllemployeeIdProofs(Long employeeId) {
		logger.info("findAllemployeeIdProofs is ===== " + employeeId);
		return employeeIdProofRepository.findAllEmpIDProof(employeeId);
	}

	@Override
	public EmployeeIdProof update(EmployeeIdProof employeeIdProof) {

		return null;
	}

	/**
	 * delete EmployeeIdProof object from database based on employeeIdProofId
	 * (Primary Key)
	 */
	@Override
	public void delete(Long empIdProofId) {
		employeeIdProofRepository.delete(empIdProofId);

	}

	@Override
	public List<EmployeeIdProof> saveEmployeeIdProof(List<EmployeeIdProof> employeeIdProofList) throws ErrorHandling {
		
		/*
		 * first check EmployeeIdProof related data already exist or not
		 */
		if(employeeIdProofList!=null) {
			for(EmployeeIdProof es : employeeIdProofList) {
				if(es.getIdNumber()!=null && es.getIdNumber()!="") {
				int count=employeeIdProofRepository.checkIdProofExist(es.getEmployee().getEmployeeId(), es.getIdTypeId(), es.getIdNumber());
				if(count > 0 ) {
					throw new ErrorHandling(DropDownCache.getInstance().getDropDownValue(
							DropDownEnum.SelectIdType.getDropDownName(), es.getIdTypeId()) +" already exist " );
				}
				}
			}
		}
		
		logger.info("EmployeeIdProofList is ===== " + employeeIdProofList);
		MandatoryInfoCheck mandatoryInfoCheck = new MandatoryInfoCheck();
		logger.info(" mandatoryInfoCheck is : " + mandatoryInfoCheck);
		Long empId = 0l;
		Long userIdUpdate = 0l;
		String idType;
		boolean flag = false;
		boolean uiFlag = false;
		
		for (EmployeeIdProof employeeIdProof : employeeIdProofList) {
			empId = employeeIdProof.getEmployee().getEmployeeId();
			logger.info("empId for mandatoryInfoCheck is  : " + empId);
			userIdUpdate = employeeIdProof.getUserIdUpdate();
			logger.info("userIdUpdate for mandatoryInfoCheck is  : " + userIdUpdate);
			idType = employeeIdProof.getIdTypeId();
			logger.info("idType for mandatoryInfoCheck is  : " + idType);

			if (employeeIdProof.getIdTypeId().equals("PA")) {
				flag = true;
				logger.info("flag for mandatoryInfoCheck is  : " + flag);
				break;
			}

			if (employeeIdProof.getIdTypeId().equals("AC")) {
				uiFlag = true;
				logger.info("flag for mandatoryInfoCheck is  : " + uiFlag);
				break;
			}

		}

		if (flag) {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setPA("YES");
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);

		} 
		else {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setPA(null);
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
		}
		
		
		if (uiFlag) {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setUi("YES");
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);

		}
		
		else {
			mandatoryInfoCheck = mandatoryInfoCheckRepository.getMandatoryInfoCheck(empId);
			if (mandatoryInfoCheck != null && mandatoryInfoCheck.getUserId() != null
					&& mandatoryInfoCheck.getDateCreated() != null) {
				mandatoryInfoCheck.setUserId(mandatoryInfoCheck.getUserId());
				mandatoryInfoCheck.setDateCreated(mandatoryInfoCheck.getDateCreated());
			}
			mandatoryInfoCheck.setUi(null);
			mandatoryInfoCheck.setDateUpdate(new Date());
			mandatoryInfoCheck.setUserIdUpdate(userIdUpdate);
			mandatoryInfoCheckRepository.save(mandatoryInfoCheck);
		}
		List<EmployeeIdProof> employeeIdProofInfos = (List<EmployeeIdProof>) employeeIdProofRepository
				.save(employeeIdProofList);
		return employeeIdProofInfos;
	}

}
