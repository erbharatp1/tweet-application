
package com.csipl.hrms.dashboard.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.report.ToDoListDTO;
import com.csipl.hrms.model.employee.ToDoList;
import com.csipl.hrms.service.adaptor.TodoListAdaptor;
import com.csipl.hrms.service.organization.TodoListService;

@RestController
@RequestMapping("/todoList")
public class ToDoListController {
	private static final Logger log = LoggerFactory.getLogger(ToDoListController.class);

	@Autowired
	TodoListService todoListService;

	TodoListAdaptor todoListAdaptor = new TodoListAdaptor();;

	/**
	 * 
	 * @param toDoListDTO
	 * @return
	 */
	@PostMapping(value = "/save")
	public @ResponseBody ToDoListDTO save(@RequestBody ToDoListDTO toDoListDTO) {
		log.info("ToDoListController.save()");
		ToDoList toDoList = todoListAdaptor.uiDtoToDatabaseModel(toDoListDTO);
		ToDoList toDoListResult = todoListService.save(toDoList);
		return todoListAdaptor.databaseModelToUiDto(toDoListResult);
	}

	/**
	 * 
	 * @param toDoListDTO
	 * @return
	 */
	@PostMapping(value = "/saveUpdate")
	public @ResponseBody List<ToDoListDTO> saveUpdate(@RequestBody List<ToDoListDTO> toDoListDTO) {
		log.info("ToDoListController.saveUpdate()");
		List<ToDoList> toDoList = todoListAdaptor.uiDtoToDatabaseModelListUpdate(toDoListDTO);

		List<ToDoList> toDoListResult = todoListService.saveUpdate(toDoList);
		log.info("ToDoListController.saveUpdate()  end" + toDoListResult.toString());
		return todoListAdaptor.databaseModelToUiDtoList(toDoListResult);
	}

	/**
	 * getAllTodoPandingList
	 * 
	 */
	@GetMapping(value = "/getAllTodoPandingList/{companyId}/{employeeId}")
	public @ResponseBody List<ToDoListDTO> getAllTodoPandingList(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException {
		log.info("ToDoListController.getAllTodoPandingList()");

		List<ToDoList> todoListList = todoListService.getAllTodoPandingList(companyId, employeeId);
		if (todoListList != null && todoListList.size() > 0) {
			return todoListAdaptor.databaseModelToUiDtoList(todoListList);
		}
		throw new ErrorHandling("TodoList data not present");
	}

	/**
	 * getAllTodoApproveList
	 * 
	 */
	@GetMapping(value = "/getAllTodoCompleteList/{companyId}/{employeeId}")
	public @ResponseBody List<ToDoListDTO> getAllTodoCompleteList(@PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId) throws ErrorHandling, PayRollProcessException {
		log.info("ToDoListController.getAllTodoApproveList()");

		List<ToDoList> todoListList = todoListService.getAllTodoCompleteList(companyId, employeeId);
		if (todoListList != null && todoListList.size() > 0) {
			return todoListAdaptor.databaseModelToUiDtoList(todoListList);
		}
		throw new ErrorHandling("TodoList data not present");
	}

	/**
	 * 
	 * @param toDoListId
	 */
	@DeleteMapping(value = "/delete/{toDoListId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteByTodoListId(@PathVariable("toDoListId") Long[] toDoListId) {
		log.info("ToDoListController.deleteByTodoListId()" + toDoListId);
		for (Long long1 : toDoListId) {
			todoListService.deleteByToDoListId(long1);
		}

	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param companyId
	 * @return
	 * @throws ErrorHandling
	 * @throws PayRollProcessException
	 * @throws ParseException
	 */
	@GetMapping(value = "/findToDoByDate/{fromDate}/{toDate}/{companyId}/{employeeId}/{status}")
	public @ResponseBody List<ToDoListDTO> findToDoByDate(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("companyId") Long companyId,
			@PathVariable("employeeId") Long employeeId,@PathVariable("status") boolean status) throws ErrorHandling, PayRollProcessException, ParseException {
		
		log.info("ToDoListController.findToDoByDate()");
		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy");
		Date fDate = inputFormat.parse(fromDate);
		Date tDate = inputFormat.parse(toDate);

		List<ToDoList> todoListList = todoListService.findToDoByDate(fDate, tDate, companyId, employeeId, status);

		if (todoListList != null && todoListList.size() > 0) {
			return todoListAdaptor.databaseModelToUiDtoList(todoListList);
		} else {
			throw new ErrorHandling("TodoList data not present");
		}

	}

}
