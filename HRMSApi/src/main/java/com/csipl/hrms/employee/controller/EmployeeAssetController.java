package com.csipl.hrms.employee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.csipl.hrms.dto.employee.EmployeeAssetDTO;
import com.csipl.hrms.model.employee.EmployeeAsset;
import com.csipl.hrms.service.adaptor.EmployeeAssetAdaptor;
import com.csipl.hrms.service.employee.EmployeeAssetService;

@RestController
@RequestMapping("/EmpAssets")
public class EmployeeAssetController {

	/**
	 * Logger declaration for knowing the flow of execution for debugging
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeAssetController.class);
	EmployeeAssetAdaptor employeeAssetAdaptor = new EmployeeAssetAdaptor();

	@Autowired
	EmployeeAssetService employeeAssetService;

	/**
	 * @param empId
	 *            This is the first parameter for getting employeeId from UI
	 * @param employeeAssetDTOList
	 *            This is the second parameter for getting List of employeeAsset
	 *            Objects from UI
	 * @param req
	 *            This is the second parameter to maintain user session
	 */
	@RequestMapping(value="/{empId}",method = RequestMethod.POST)
	public List<EmployeeAssetDTO> saveEmployeeAssets(@PathVariable("empId") String empId,
			@RequestBody List<EmployeeAssetDTO> employeeAssetDTOList, HttpServletRequest req) {
		logger.info("saveEmployeeAssets is calling : " + "employeeId : " + empId + " : List<EmployeeAssetDTO> "
				+ employeeAssetDTOList);
		List<EmployeeAsset> employeeAssetList = employeeAssetAdaptor
				.empAssetsDtoToDatabaseModelList(employeeAssetDTOList, empId);
		// employeeAssetList.forEach(employeeAsset -> {
		// boolean newFlag = employeeAsset == null ||
		// employeeAsset.getEmployeeAssetsId() == null;
		// editLogInfoWithoutCG(employeeAsset, newFlag, req);
		// });
		List<EmployeeAsset> employeeAssetListResult = employeeAssetService.saveAll(employeeAssetList);
		logger.info("saveEmployeeAssets is end  :" + employeeAssetListResult);
		return employeeAssetAdaptor.databaseModelToUiDtoList(employeeAssetListResult);
	}

	/**
	 * to get list of Employee Assets List from database based on employeeId
	 */
	@RequestMapping(value="/{empId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeAssetDTO> findEmpAssets(@PathVariable("empId") String empId,
			HttpServletRequest req) {
		logger.info("findEmpAssets is calling :" + "employeeId" + empId);
		return employeeAssetAdaptor.databaseModelToUiDtoList(employeeAssetService.findAllemployeeAssets(empId));
	}

	/**
	 * delete Employee Assets from database based on assestsId (primary key)
	 */
	@RequestMapping( method = RequestMethod.DELETE)
	public void deleteEmp(@RequestParam("assetsID") String assetsID, HttpServletRequest req) {
		logger.info("deleteEmp is calling :" + "assetsID" + assetsID);
		Long assetsId = Long.parseLong(assetsID);
		employeeAssetService.delete(assetsId);
	}
	
	
	@RequestMapping(value="assets/{companyId}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeAssetDTO> findEmpAssetsByCompanyId(@PathVariable("companyId") String companyId,
			HttpServletRequest req) {
		logger.info("findEmpAssetsByCompanyId is calling :" + "companyId  ==" + companyId);
		return employeeAssetAdaptor.databaseModelObjectToUiDtoList(employeeAssetService.findEmpAssetsByCompanyId(companyId));
	}
	
	
	@RequestMapping(value="assetsStatusUpdate/{employeeAssetsId}", method = RequestMethod.GET)
	public @ResponseBody int empAssetsStatusUpdate(@PathVariable("employeeAssetsId") String employeeAssetsId,
			HttpServletRequest req) {
		logger.info("EmpAssetsStatusUpdate is calling :" + "employeeAssetsId  ==" + employeeAssetsId);
		return  employeeAssetService.empAssetsStatusUpdate(employeeAssetsId);
	}

	@RequestMapping(value="assetsCount/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody int empAssetsCount(@PathVariable("employeeId") Long employeeId,
			HttpServletRequest req) {
	int count = employeeAssetService.empAssetsCount(employeeId);
	System.out.println("assets count ================"+count);
		return  count;
	}
	
	@RequestMapping(value="sortedAssets/{companyId}", method = RequestMethod.GET)
	
	public @ResponseBody List<EmployeeAssetDTO> findSortedEmpAssetsByCompanyId(@PathVariable("companyId") String companyId,
			HttpServletRequest req) {
		logger.info("findSortedEmpAssetsByCompanyId is calling :" + "companyId  ==" + companyId);
		return employeeAssetAdaptor.databaseModelObjectToUiDtoList(employeeAssetService.findSortedEmpAssetsByCompanyId(companyId));
	}
}
