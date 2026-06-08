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
import com.gic.entity.Department;
import com.gic.service.DepartmentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/departments")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	// Displaying list of all departments -> GET http://localhost:8080/departments
	@GetMapping
	public List<Department> getAllDepartment() {
		logger.info("Fetching all departments");
		return departmentService.getAllDepartments();
	}

	// Displaying department by id -> GET http://localhost:8080/departments/3
	@GetMapping("/{id}")
	public Optional<Department> getDepartment(@PathVariable int id) {
		logger.info("Fetching department with id: {}", id);
		return departmentService.getDepartment(id);
	}

	// Inserting department -> POST http://localhost:8080/departments
	@PostMapping
    public Department addDepartment(@RequestBody Department department) {
        logger.info("Adding new department: {}", department.getDepartment_name());
        return departmentService.addDepartment(department);
    }

    // Updating department by id -> PUT http://localhost:8080/departments/3
    @PutMapping("/{id}")
    public Department updateDepartment(@RequestBody Department department, @PathVariable int id) {
        logger.info("Updating department with id: {}", id);
        return departmentService.updateDepartment(department, id);
    }

    // Deleting all departments -> DELETE http://localhost:8080/departments
    @DeleteMapping
    public void deleteAllDepartments() {
        logger.warn("Deleting all departments");
        departmentService.deleteAllDepartments();
    }

	// Deleting department by id -> DELETE http://localhost:8080/departments/3
	@DeleteMapping("/{id}")
	public void deleteDepartmentByID(@PathVariable int id) {
		logger.info("Deleting department with id: {}", id);
		departmentService.deleteDepartmentByID(id);
	}
}