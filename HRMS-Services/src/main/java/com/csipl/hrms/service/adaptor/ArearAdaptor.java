package com.csipl.hrms.service.adaptor;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.csipl.hrms.dto.payroll.ArearCalculationDTO;
import com.csipl.hrms.dto.payroll.ArearMasterDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payroll.ArearCalculation;
import com.csipl.hrms.model.payroll.ArearMaster;


public class ArearAdaptor implements Adaptor<ArearCalculationDTO, ArearCalculation>{

	@Override
	public List<ArearCalculation> uiDtoToDatabaseModelList(List<ArearCalculationDTO> uiobj) {
		return null;
	}


	

	@Override
	public ArearCalculation uiDtoToDatabaseModel(ArearCalculationDTO uiobj) {
		return null;
	}

	@Override
	public ArearCalculationDTO databaseModelToUiDto(ArearCalculation arear) {
		ArearCalculationDTO arearDto=new ArearCalculationDTO();
		arearDto.setArearCalculationId(arear.getArearCalculationId());
		arearDto.setPayrollMonth(arear.getPayrollMonth());
		arearDto.setActualAmount(arear.getActualAmount());
		
		arearDto.setPfDeduction(arear.getPfDeduction());
		arearDto.setEsiDeduction(arear.getEsiDeduction());
		arearDto.setNetPayableAmount(arear.getNetPayableAmount());
		arearDto.setUserId(arear.getUserId());
		arearDto.setUserIdUpdate(arear.getUserIdUpdate());
		
		arearDto.setDateCreated(arear.getDateCreated());
		arearDto.setDateUpdated(arear.getDateUpdated());
		arearDto.setCompanyId(arear.getCompanyId());
		
		return arearDto;
	}
	
public List<ArearCalculationDTO> arearCaldatabaseModelToUiDtoList(List<Object[]> arearList) {
	List<ArearCalculationDTO> arearDtoList = new ArrayList<ArearCalculationDTO>();
	for (Object[] arear :arearList) {
		ArearCalculationDTO arearDTO=new ArearCalculationDTO();
		if (arear[0] != null) {
			arearDTO.setPayrollMonth(arear[0].toString());
		}
		if (arear[1] != null) {
			arearDTO.setActualAmount((BigDecimal)arear[1]);
		}
		if (arear[2] != null) {
			arearDTO.setPfDeduction((BigDecimal)arear[2]);
		}
		if (arear[3] != null) {
			arearDTO.setEsiDeduction((BigDecimal)arear[3]);
		}
		if (arear[4] != null) {
			arearDTO.setNetPayableAmount((BigDecimal)arear[4]);
		}
		if (arear[5] != null) {
			arearDTO.setBasicSalary((BigDecimal)arear[5]);
		}
		if (arear[6] != null) {
			arearDTO.setSpecialAllowance((BigDecimal)arear[6]);
		}
		
		if (arear[7] != null) {
			arearDTO.setPtDeduction((BigDecimal)arear[7]);
		}
		arearDtoList.add(arearDTO);
	}
	
	
		return arearDtoList;
	}
	
	public List<ArearCalculationDTO> areardatabaseModelToUiDtoList(List<Object[]> arearList) throws ParseException{
		
		
		List<ArearCalculationDTO> arearDtoList = new ArrayList<ArearCalculationDTO>();
		for (Object[] arear :arearList) {
			ArearCalculationDTO arearDTO=new ArearCalculationDTO();
			
			if (arear[0] != null) {
				arearDTO.setEmployeeCode(arear[0].toString());
			}
			
			if (arear[1] != null) {
				arearDTO.setEmpName(arear[1].toString());
			}
			if (arear[2] != null) {
				arearDTO.setDepartmentName(arear[2].toString());
			}
			if (arear[3] != null) {
			
				arearDTO.setActualAmount((BigDecimal)arear[3]);
			}
			if (arear[4] != null) {
				arearDTO.setNetPayableAmount((BigDecimal)arear[4]);
			}
			if (arear[5] != null) {
				arearDTO.setDeductionAmt(arear[5].toString());
			}
			if (arear[6] != null) { 
				
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = arear[6].toString();
				arearDTO.setArearFrom(sdf.parse(date) );
			}
			if (arear[7] != null) {
				arearDTO.setArearId(Long.valueOf(arear[7].toString()));
			}
			//am.arearTo,am.isBooked,am.companyId,am.userId,am.dateCreated,am.userIdUpdate,am.dateUpdate
			if (arear[8] != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = arear[8].toString();
				arearDTO.setArearTo(sdf.parse(date) );
			}
			if (arear[9] != null) {
				arearDTO.setIsBooked(Boolean.valueOf(arear[9].toString()));
			}
			if (arear[10] != null) {
				arearDTO.setCompanyId(Long.valueOf(arear[10].toString()));
			}
			if (arear[11] != null) {
				arearDTO.setUserId(Long.valueOf(arear[11].toString()));
			}
			if (arear[12] != null) {
				arearDTO.setDateCreated(new Date());
			}
			if (arear[13] != null) {
				arearDTO.setUserIdUpdate(Long.valueOf(arear[13].toString()));
			}
			if (arear[14] != null) {
				arearDTO.setDateUpdated(new Date());
			}
			if (arear[15] != null) {
				arearDTO.setEmployeeId(Long.valueOf(arear[15].toString()));
			}
			if (arear[16] != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = arear[16].toString();
				System.out.println("@@@@@@@@@@@@@@@@"+date);
				arearDTO.setDateOfJoining(sdf.parse(date) );
			}
		//	desig.designationName,gr.gradesName
			if (arear[17] != null) {
				arearDTO.setDesignationName(arear[17].toString());
			}
			if (arear[18] != null) {
				arearDTO.setGradesName(arear[18].toString());
			}
			if (arear[19] != null) {
				arearDTO.setEmployeeLogoPath(arear[19].toString());
			}
			arearDtoList.add(arearDTO);
		}
		
		return arearDtoList;
		
	}
	public ArearMaster arearMasteruiDtoToDatabaseModel(ArearMasterDTO arearMasterDto){
		ArearMaster arearMaster=new ArearMaster();
		Employee employee = new Employee();
		employee.setEmployeeId(arearMasterDto.getEmployeeId());
		arearMaster.setEmployee(employee);
	//	BeanUtils.copyProperties(arearMasterDto, arearMaster);
		arearMaster.setArearId(arearMasterDto.getArearId());
		arearMaster.setArearFrom(arearMasterDto.getArearFrom());
		arearMaster.setArearTo(arearMasterDto.getArearTo());
		arearMaster.setCompanyId(arearMasterDto.getCompanyId());
		arearMaster.setDateCreated(arearMasterDto.getDateCreated());
		arearMaster.setDateUpdate(arearMasterDto.getDateUpdate());
		arearMaster.setIsBooked(arearMasterDto.getIsBooked());
		arearMaster.setUserId(arearMasterDto.getUserId());
		arearMaster.setUserIdUpdate(arearMasterDto.getUserIdUpdate());
		arearMaster.setBookedPayrollMonth(arearMasterDto.getBookedPayrollMonth());
		return arearMaster;
	}

	@Override
	public List<ArearCalculationDTO> databaseModelToUiDtoList(List<ArearCalculation> dbobj) {
		// TODO Auto-generated method stub
		return null;
	}
}
