package com.csipl.hrms.service.organization.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.hrms.model.employee.ToDoList;

@Repository
@Transactional
public interface TodoListRepository extends CrudRepository<ToDoList, Long> {

	@Query(" from ToDoList where companyId=?1 and employeeId=?2 and activeStatus='PEN' ORDER BY  todoListId  DESC ")
	public List<ToDoList> getAllTodoPandingList(Long companyId, Long employeeId);

	@Query(" from ToDoList where companyId=?1 and employeeId=?2 and activeStatus='COM' ORDER BY  todoListId  DESC ")
	public List<ToDoList> getAllTodoCompleteList(Long companyId, Long employeeId);

//	@Modifying
//	@Query("Update ToDoList d SET d.activeStatus=:status WHERE d.todoListId=:todoListId")
//	public void updateById(@Param("todoListId") Long todoListId, @Param("status") String status);

	@Query(value = "SELECT * FROM ToDoList JOIN Employee e ON e.employeeId=ToDoList.employeeId where ToDoList.dueDate >=?1 and ToDoList.dueDate <=?2 and ToDoList.activeStatus=?5 and ToDoList.companyId=?3 AND e.employeeId=?4 ORDER BY ToDoList.todoListId  DESC", nativeQuery = true)
	public List<ToDoList> findToDoByDateForPending(Date fDate, Date tDate, Long companyId, Long employeeId, String pen);

	@Query(value = "SELECT * FROM ToDoList JOIN Employee e ON e.employeeId=ToDoList.employeeId where ToDoList.dueDate >=?1 and ToDoList.dueDate <=?2 and ToDoList.activeStatus=?5 and ToDoList.companyId=?3 AND e.employeeId=?4 ORDER BY ToDoList.todoListId  DESC", nativeQuery = true)
	public List<ToDoList> findToDoByDateforCompleted(Date fDate, Date tDate, Long companyId, Long employeeId,
			String com);

}
