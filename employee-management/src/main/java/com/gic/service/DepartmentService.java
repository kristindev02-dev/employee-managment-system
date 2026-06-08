package com.gic.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gic.entity.Department;
import com.gic.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public Optional<Department> getDepartment(int id) {
		return departmentRepository.findById(id);
	}

	public Department addDepartment(Department newDepartment) {
		if (newDepartment.getEmployees() == null) newDepartment.setEmployees(0);
		return departmentRepository.save(newDepartment);
	}

	// This safely checks if the ID exists, updates the name, and saves it
	public Department updateDepartment(Department departmentDetails, int id) {
		return departmentRepository.findById(id)
			.map(existingDept -> {
				existingDept.setDepartment_name(departmentDetails.getDepartment_name());
				return departmentRepository.save(existingDept);
			})
			.orElseGet(() -> {
				departmentDetails.setDepartment_id(id);
				return departmentRepository.save(departmentDetails);
			});
	}

	public void deleteAllDepartments() {
		departmentRepository.deleteAll();
	}

	public void deleteDepartmentByID(int id) {
		departmentRepository.deleteById(id);
	}
}
