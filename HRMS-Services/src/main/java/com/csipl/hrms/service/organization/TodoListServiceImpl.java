package com.csipl.hrms.service.organization;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.constant.StatusMessage;
import com.csipl.hrms.model.employee.ToDoList;
import com.csipl.hrms.service.organization.repository.TodoListRepository;

@Service
@Transactional
public class TodoListServiceImpl implements TodoListService {
	@Autowired
	TodoListRepository todoListRepository;

	@Override
	public ToDoList save(ToDoList toDoList) {
		ToDoList doLists = todoListRepository.save(toDoList);
		return doLists;
	}

	public List<ToDoList> save(List<ToDoList> toDoList) {
		List<ToDoList> doLists = (List<ToDoList>) todoListRepository.save(toDoList);
		return doLists;
	}

	@Override
	public List<ToDoList> getAllTodoPandingList(Long companyId, Long employeeId) {
		return todoListRepository.getAllTodoPandingList(companyId, employeeId);
	}

	@Override
	public void deleteByToDoListId(Long toDoListId) {
		todoListRepository.delete(toDoListId);
	}

	@Override
	public List<ToDoList> getAllTodoCompleteList(Long companyId, Long employeeId) {
		return todoListRepository.getAllTodoCompleteList(companyId, employeeId);
	}

	@Override
	public List<ToDoList> saveUpdate(List<ToDoList> toDoList) {
		List<ToDoList> doLists = (List<ToDoList>) todoListRepository.save(toDoList);
		return doLists;

	}

	@SuppressWarnings("static-access")
	@Override
	public List<ToDoList> findToDoByDate(Date fDate, Date tDate, Long companyId, Long employeeId, boolean status) {

		StatusMessage sm = new StatusMessage();
		String pen = sm.PENDING_CODE;
		String com = sm.COMPLETED_CODE;

		if (status == false) {
			return todoListRepository.findToDoByDateForPending(fDate, tDate, companyId, employeeId, pen);
		} else {
			return todoListRepository.findToDoByDateforCompleted(fDate, tDate, companyId, employeeId, com);
		}

	}

}
