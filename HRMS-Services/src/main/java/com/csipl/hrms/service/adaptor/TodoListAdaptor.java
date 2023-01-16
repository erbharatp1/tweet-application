
package com.csipl.hrms.service.adaptor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.dto.report.ToDoListDTO;
import com.csipl.hrms.model.employee.ToDoList;

public class TodoListAdaptor implements Adaptor<ToDoListDTO, ToDoList> {

	@Override
	public List<ToDoList> uiDtoToDatabaseModelList(List<ToDoListDTO> toDoListDtoList) {
		return toDoListDtoList.stream().map(toDoList -> uiDtoToDatabaseModel(toDoList)).collect(Collectors.toList());
	}
	
	

	@Override
	public List<ToDoListDTO> databaseModelToUiDtoList(List<ToDoList> toDoListList) {
		return toDoListList.stream().map(toDoList -> databaseModelToUiDto(toDoList)).collect(Collectors.toList());
	}

	@Override
	public ToDoListDTO databaseModelToUiDto(ToDoList toDoList) {
		ToDoListDTO toDoListDTO = new ToDoListDTO();
		toDoListDTO.setCompanyId(toDoList.getCompanyId());
		toDoListDTO.setTodoListId(toDoList.getTodoListId());
		toDoListDTO.setDueDate(toDoList.getDueDate());
	 
		toDoListDTO.setTask(toDoList.getTask());
		if (toDoList.getDateUpdate() != null) {
			toDoListDTO.setDateUpdate(toDoList.getDateUpdate());
		}
		toDoListDTO.setActiveStatus(toDoList.getActiveStatus());
		toDoListDTO.setDateUpdate(toDoList.getDateUpdate());
		toDoListDTO.setUserId(toDoList.getUserId());
		toDoListDTO.setDateCreated(toDoList.getDateCreated());
		toDoListDTO.setEmployeeId(toDoList.getEmployeeId());
		toDoListDTO.setUserIdUpdate(toDoList.getUserIdUpdate());

		return toDoListDTO;
	}

	@Override
	public ToDoList uiDtoToDatabaseModel(ToDoListDTO toDoListDTO) {
		ToDoList toDoList = new ToDoList();
		toDoList.setCompanyId(toDoListDTO.getCompanyId());
		toDoList.setTodoListId(toDoListDTO.getTodoListId());
		toDoList.setDueDate(toDoListDTO.getDueDate());
	 
		toDoList.setEmployeeId(toDoListDTO.getEmployeeId());
		if (toDoListDTO.getTodoListId() == null) {
			toDoList.setDateCreated(new Date());
		} else {
			toDoList.setDateCreated(toDoListDTO.getDateCreated());
		}
		toDoList.setDateUpdate(new Date());
		toDoList.setTask(toDoListDTO.getTask());
		toDoList.setActiveStatus(toDoListDTO.getActiveStatus());
		toDoList.setUserId(toDoListDTO.getUserId());
		toDoList.setUserIdUpdate(toDoListDTO.getUserIdUpdate());
		return toDoList;
	}


	public List<ToDoList> uiDtoToDatabaseModelListUpdate(List<ToDoListDTO> toDoListDtoList) {
		return toDoListDtoList.stream().map(toDoList -> updateUIDtoToDatabaseModel(toDoList)).collect(Collectors.toList());
	}
	
	public ToDoList updateUIDtoToDatabaseModel(ToDoListDTO toDoListDTO) {
		ToDoList toDoList = new ToDoList();
		toDoList.setCompanyId(toDoListDTO.getCompanyId());
		toDoList.setTodoListId(toDoListDTO.getTodoListId());
		toDoList.setDueDate(toDoListDTO.getDueDate());
	 
		toDoList.setEmployeeId(toDoListDTO.getEmployeeId());
		if (toDoListDTO.getTodoListId() == null) {
			toDoList.setDateCreated(new Date());
		} else {
			toDoList.setDateCreated(toDoListDTO.getDateCreated());
		}
		toDoList.setDateUpdate(new Date());
		toDoList.setTask(toDoListDTO.getTask());
		toDoList.setActiveStatus(StatusMessage.COMPLETED_CODE);
		toDoList.setUserId(toDoListDTO.getUserId());
		toDoList.setUserIdUpdate(toDoListDTO.getUserIdUpdate());
		return toDoList;
	}
	
}
