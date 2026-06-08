package com.gic.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gic.entity.Employee;
import com.gic.service.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		logger.info("Fetching all employees");
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{id}")
	public Optional<Employee> getEmployee(@PathVariable int id) {
		logger.info("Fetching employee with id: {}", id);
		return employeeService.getEmployee(id);
	}

	@PostMapping
	public void addEmployee(@RequestBody Employee employee) {
		logger.info("Adding new employee: {}", employee.getName());
		employeeService.addEmployee(employee);
	}

	@PutMapping("/{id}")
	public void updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
		logger.info("Updating employee with id: {}", id);
		employeeService.updateEmployee(employee, id);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployeeByID(@PathVariable int id) {
		logger.info("Deleting employee with id: {}", id);
		employeeService.deleteEmployeeByID(id);
	}
}