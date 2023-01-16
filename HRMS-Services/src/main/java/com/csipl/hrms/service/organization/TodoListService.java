package com.csipl.hrms.service.organization;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.csipl.hrms.model.employee.ToDoList;
@Component
public interface TodoListService {

	public ToDoList save(ToDoList toDoList);

	public List<ToDoList> getAllTodoPandingList(Long companyId, Long employeeId);

	public List<ToDoList> getAllTodoCompleteList(Long companyId, Long employeeId);

	public void deleteByToDoListId(Long toDoListId);

	public List<ToDoList> findToDoByDate(Date fDate, Date tDate,Long companyId, Long employeeId, boolean status);

	public List<ToDoList> saveUpdate(List<ToDoList> toDoList);

}
