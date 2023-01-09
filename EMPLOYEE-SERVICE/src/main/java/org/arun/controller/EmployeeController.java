package org.arun.controller;

import java.util.List;

import org.arun.entity.Employee;
import org.arun.response.EmployeeResponse;
import org.arun.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") int id) {

		EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);

		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
	}

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponse>> getEmployee(){
		
		List<EmployeeResponse> employeeList = employeeService.getAllEmployees();
		
		return ResponseEntity.status(HttpStatus.OK).body(employeeList);
	}
}
