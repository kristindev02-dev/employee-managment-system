package com.gic.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gic.entity.Employee;
import com.gic.repository.EmployeeRepository;
import com.gic.repository.DepartmentRepository;
import com.gic.entity.Department;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> getEmployee(int id) {
		return employeeRepository.findById(id);
	}

	public Employee addEmployee(Employee newEmployee) {
		Employee saved = employeeRepository.save(newEmployee);

		incrementDepartmentCount(saved.getDepartment());

		return saved;
	}

	public Employee updateEmployee(Employee employeeDetails, int id) {
		return employeeRepository.findById(id)
			.map(existingEmp -> {
				Department oldDept = existingEmp.getDepartment();
				Department newDept = employeeDetails.getDepartment();

				existingEmp.setName(employeeDetails.getName());
				existingEmp.setEmail(employeeDetails.getEmail());
				existingEmp.setPhoneNo(employeeDetails.getPhoneNo());
				existingEmp.setSalary(employeeDetails.getSalary());
				existingEmp.setJoinedDate(employeeDetails.getJoinedDate());
				existingEmp.setDepartment(newDept);
				existingEmp.setPosition(employeeDetails.getPosition());

				if (!isSameDepartment(oldDept, newDept)) {
					decrementDepartmentCount(oldDept);
					incrementDepartmentCount(newDept);
				}

				return employeeRepository.save(existingEmp);
			})
			.orElseGet(() -> {
				employeeDetails.setId(id);
				Employee saved = employeeRepository.save(employeeDetails);
				incrementDepartmentCount(saved.getDepartment());
				return saved;
			});
	}

	public void deleteEmployeeByID(int id) {
		employeeRepository.findById(id).ifPresent(emp -> {
			decrementDepartmentCount(emp.getDepartment());
			employeeRepository.delete(emp);
		});
	}

	private void incrementDepartmentCount(Department department) {
		if (department == null || department.getDepartment_id() == null) {
			return;
		}

		departmentRepository.findById(department.getDepartment_id()).ifPresent(dept -> {
			if (dept.getEmployees() == null) {
				dept.setEmployees(0);
			}
			dept.setEmployees(dept.getEmployees() + 1);
			departmentRepository.save(dept);
		});
	}

	private void decrementDepartmentCount(Department department) {
		if (department == null || department.getDepartment_id() == null) {
			return;
		}

		departmentRepository.findById(department.getDepartment_id()).ifPresent(dept -> {
			if (dept.getEmployees() == null || dept.getEmployees() <= 0) {
				dept.setEmployees(0);
			} else {
				dept.setEmployees(dept.getEmployees() - 1);
			}
			departmentRepository.save(dept);
		});
	}

	private boolean isSameDepartment(Department oldDept, Department newDept) {
		if (oldDept == null || oldDept.getDepartment_id() == null) {
			return newDept == null || newDept.getDepartment_id() == null;
		}
		return newDept != null && oldDept.getDepartment_id().equals(newDept.getDepartment_id());
	}
}
