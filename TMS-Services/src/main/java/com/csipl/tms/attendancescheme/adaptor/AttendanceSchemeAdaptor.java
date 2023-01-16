package com.csipl.tms.attendancescheme.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.csipl.tms.dto.attendancescheme.AttendanceSchemeDTO;
import com.csipl.tms.dto.attendancetype.AttendanceTypeDTO;
import com.csipl.tms.dto.attendancetypetransaction.AttendanceTypeTransactionDTO;
import com.csipl.tms.dto.latlonglocation.AttendanceLocationMappingDTO;
import com.csipl.tms.dto.latlonglocation.LocationMasterDTO;
import com.csipl.tms.model.attendancescheme.AttendanceScheme;
import com.csipl.tms.model.attendancetype.AttendanceType;
import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTransaction;
import com.csipl.tms.model.latlonglocation.AttendanceLocationMapping;
import com.csipl.tms.model.latlonglocation.LocationMaster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class AttendanceSchemeAdaptor {

	 Gson gs =new GsonBuilder().create();
	
	public AttendanceScheme uiDtoToModel(AttendanceSchemeDTO attendanceSchemeDTO, List<LocationMaster> savedlocationMaster) {
		System.out.println(" <<<<<<<<< AttendanceSchemeDTO >>>>>>>> "+gs.toJson(attendanceSchemeDTO));
		AttendanceScheme attendanceSchemeModel = new AttendanceScheme();
		
		if(attendanceSchemeDTO.getAttendanceSchemeId() != null) {
			attendanceSchemeModel.setAttendanceSchemeId(attendanceSchemeDTO.getAttendanceSchemeId());
		}
		attendanceSchemeModel.setCreatedBy(attendanceSchemeDTO.getCreatedBy());
		attendanceSchemeModel.setCreatedDate(new Date());
		attendanceSchemeModel.setActiveStatus(attendanceSchemeDTO.getActiveStatus());
		attendanceSchemeModel.setSchemeName(attendanceSchemeDTO.getSchemeName());
		attendanceSchemeModel.setArDays(attendanceSchemeDTO.getArDays());
		
		List<AttendanceTypeTransactionDTO> typeTransactionDtoList = attendanceSchemeDTO.getAttendanceTypeTransactionsDtoList();
		////attendance Type transaction db model
		List<AttendanceTypeTransaction> transactionsModelList = new ArrayList<AttendanceTypeTransaction>();
		
		for(AttendanceTypeTransactionDTO txnDto : typeTransactionDtoList) {
			AttendanceTypeTransaction attendanceTypeTransaction = new AttendanceTypeTransaction();
			attendanceTypeTransaction.setActiveStatus(txnDto.getActiveStatus());
			attendanceTypeTransaction.setCreatedBy(attendanceSchemeDTO.getCreatedBy());
			attendanceTypeTransaction.setCreatedDate(new Date());
			if(txnDto.getAttendanceTypeTransactionId() != null) {
				attendanceTypeTransaction.setAttendanceTypeTransactionId(txnDto.getAttendanceTypeTransactionId());
			}
			attendanceTypeTransaction.setAttendanceScheme(attendanceSchemeModel);
			AttendanceTypeDTO attendanceTypeDTO = txnDto.getAttendanceTypeDTO();
			AttendanceType attendanceTypeModel = new AttendanceType();
			attendanceTypeModel.setTypeName(attendanceTypeDTO.getTypeName());
			attendanceTypeModel.setAttendanceTypeId(attendanceTypeDTO.getAttendanceTypeId());
			attendanceTypeTransaction.setAttendanceType(attendanceTypeModel);
			transactionsModelList.add(attendanceTypeTransaction);
		}

		List<AttendanceLocationMappingDTO> locationMappingDtoList = attendanceSchemeDTO.getAttendanceLocationMappingsDtoList();
		////AttendanceLocationMapping db model
		List<AttendanceLocationMapping> attendanceLocationMappings = new ArrayList<AttendanceLocationMapping>();
		for(AttendanceLocationMappingDTO locationMappingDTO :locationMappingDtoList) {
			if(locationMappingDTO.getAttendanceLocationId() != null) {
			AttendanceLocationMapping locMapping = new AttendanceLocationMapping();
			locMapping.setActiveStatus(locationMappingDTO.getActiveStatus());
			locMapping.setCreatedBy(attendanceSchemeDTO.getCreatedBy());
			locMapping.setCreatedDate(new Date());
			if(locationMappingDTO.getAttendanceLocationId() != null) {
				locMapping.setAttendanceLocationId(locationMappingDTO.getAttendanceLocationId());
			}
			
			LocationMaster locationMaster = new LocationMaster();
			locationMaster.setLocationId(locationMappingDTO.getLocationId());
			locationMaster.setCreatedBy(attendanceSchemeDTO.getCreatedBy());
			locationMaster.setCreatedDate(new Date());
			locationMaster.setLatitude(locationMappingDTO.getLatitude());
			locationMaster.setLongitude(locationMappingDTO.getLongitude());
			locationMaster.setLocationAddress(locationMappingDTO.getLocationAddress());
			
			Integer index=containLocationId(locationMappingDTO.getLocationId() ,savedlocationMaster);
			if(index!=null) {
				int i = index;
			savedlocationMaster.remove(i);
			}
			locMapping.setLocationMaster(locationMaster);
			locMapping.setAttendanceScheme(attendanceSchemeModel);
			attendanceLocationMappings.add(locMapping);
		}
		}
		
		
		for(LocationMaster locationMaster : savedlocationMaster) {
			AttendanceLocationMapping locMapping = new AttendanceLocationMapping();
			locMapping.setActiveStatus(locationMappingDtoList.get(0).getActiveStatus());
			locMapping.setCreatedBy(attendanceSchemeDTO.getCreatedBy());
			locMapping.setCreatedDate(new Date());
			locMapping.setLocationMaster(locationMaster);
			locMapping.setAttendanceScheme(attendanceSchemeModel);
			
			attendanceLocationMappings.add(locMapping);
		}
		attendanceSchemeModel.setAttendanceLocationMappings(attendanceLocationMappings);
		attendanceSchemeModel.setAttendanceTypeTransactions(transactionsModelList);
		return attendanceSchemeModel;
	}
	
	
	public List<AttendanceSchemeDTO> modelToUiDtoList(List<AttendanceScheme> schememodelList) {
		return schememodelList.stream().map(item -> modelToUiDto(item)).collect(Collectors.toList());
	}
	
	public AttendanceSchemeDTO modelToUiDto(AttendanceScheme schememodel) {
		AttendanceSchemeDTO attendanceSchemeDTO = new AttendanceSchemeDTO();
		attendanceSchemeDTO.setActiveStatus(schememodel.getActiveStatus());
		attendanceSchemeDTO.setArDays(schememodel.getArDays());
		attendanceSchemeDTO.setSchemeName(schememodel.getSchemeName());
		attendanceSchemeDTO.setCreatedBy(schememodel.getCreatedBy());
		attendanceSchemeDTO.setCreatedDate(schememodel.getCreatedDate());
		attendanceSchemeDTO.setAttendanceSchemeId(schememodel.getAttendanceSchemeId());
		
		List<AttendanceLocationMapping> attendanceLocationMappingList = schememodel.getAttendanceLocationMappings();
		List<AttendanceLocationMappingDTO> attendanceLocationMappingsDtoList = new ArrayList<AttendanceLocationMappingDTO>();
		for(AttendanceLocationMapping attendanceLocationMapping : attendanceLocationMappingList) {
			AttendanceLocationMappingDTO AttendanceLocationMappingDTO = new AttendanceLocationMappingDTO();
			if(attendanceLocationMapping.getAttendanceLocationId() !=null) {
				AttendanceLocationMappingDTO.setAttendanceLocationId(attendanceLocationMapping.getAttendanceLocationId());
			}
			AttendanceLocationMappingDTO.setActiveStatus(attendanceLocationMapping.getActiveStatus());
			AttendanceLocationMappingDTO.setCreatedBy(attendanceLocationMapping.getCreatedBy());
			AttendanceLocationMappingDTO.setCreatedDate(attendanceLocationMapping.getCreatedDate());
			
			AttendanceLocationMappingDTO.setLocationId(attendanceLocationMapping.getLocationMaster().getLocationId());
			AttendanceLocationMappingDTO.setLatitude(attendanceLocationMapping.getLocationMaster().getLatitude());
			AttendanceLocationMappingDTO.setLongitude(attendanceLocationMapping.getLocationMaster().getLongitude());
			AttendanceLocationMappingDTO.setRadius(attendanceLocationMapping.getLocationMaster().getRadius());
			AttendanceLocationMappingDTO.setLocationAddress(attendanceLocationMapping.getLocationMaster().getLocationAddress());
			
			LocationMaster locationMaster = attendanceLocationMapping.getLocationMaster();
			LocationMasterDTO locationMasterDTO = new LocationMasterDTO();
			locationMasterDTO.setLocationId(locationMaster.getLocationId());
			locationMasterDTO.setLatitude(locationMaster.getLatitude());
			locationMasterDTO.setLongitude(locationMaster.getLongitude());
			locationMasterDTO.setLocationAddress(locationMaster.getLocationAddress());
			locationMasterDTO.setRadius(locationMaster.getRadius());
			AttendanceLocationMappingDTO.setLocationMasterDto(locationMasterDTO);
			attendanceLocationMappingsDtoList.add(AttendanceLocationMappingDTO);
		}
		
		attendanceSchemeDTO.setAttendanceLocationMappingsDtoList(attendanceLocationMappingsDtoList);
		
		List<AttendanceTypeTransactionDTO> attendanceTypeTransactionsDtoList = new ArrayList<AttendanceTypeTransactionDTO>();
		List<AttendanceTypeTransaction> attendanceTypeTransactions = schememodel.getAttendanceTypeTransactions();
		for(AttendanceTypeTransaction attendanceTypeTransaction : attendanceTypeTransactions) {
			AttendanceTypeTransactionDTO attendanceTypeTransactionDTO = new AttendanceTypeTransactionDTO();
			attendanceTypeTransactionDTO.setAttendanceTypeTransactionId(attendanceTypeTransaction.getAttendanceTypeTransactionId());
			attendanceTypeTransactionDTO.setActiveStatus(attendanceTypeTransaction.getActiveStatus());
			attendanceTypeTransactionDTO.setUpdatedBy(attendanceTypeTransaction.getUpdatedBy());
			attendanceTypeTransactionDTO.setCreatedBy(attendanceTypeTransaction.getCreatedBy());
			attendanceTypeTransactionDTO.setCreatedDate(attendanceTypeTransaction.getCreatedDate());
			attendanceTypeTransactionDTO.setUpdatedDate(attendanceTypeTransaction.getUpdatedDate());
			attendanceTypeTransactionDTO.setAttendanceTypeId(attendanceTypeTransaction.getAttendanceType().getAttendanceTypeId());
			
			
			AttendanceType attendanceType = attendanceTypeTransaction.getAttendanceType();
			AttendanceTypeDTO attendanceTypeDTO = new AttendanceTypeDTO();
			attendanceTypeDTO.setAttendanceTypeId(attendanceType.getAttendanceTypeId());
			attendanceTypeDTO.setTypeCode(attendanceType.getTypeCode());
			attendanceTypeDTO.setTypeName(attendanceType.getTypeName());
			
			attendanceTypeTransactionDTO.setAttendanceTypeDTO(attendanceTypeDTO);
			attendanceTypeTransactionsDtoList.add(attendanceTypeTransactionDTO);
		}
		
		attendanceSchemeDTO.setAttendanceTypeTransactionsDtoList(attendanceTypeTransactionsDtoList);
		
		return attendanceSchemeDTO;
	}
	
	public Integer containLocationId(long id ,List<LocationMaster> savedlocationMaster) {
		for(int i =0 ;i< savedlocationMaster.size();i++) {
			if(id == savedlocationMaster.get(i).getLocationId()) {
				return i;
			}
		}
		
		return null;
	}
	
	
	
	
}
