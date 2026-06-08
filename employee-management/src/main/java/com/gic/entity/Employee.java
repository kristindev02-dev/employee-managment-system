package com.gic.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	private double salary;
	
	@Column(name = "joined_date")
	private LocalDate joinedDate; // Handles Hire/Joined Date
	
	// Joins Employee to Department via department_id foreign key
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
	private Department department;

	// Joins Employee to Position via position_id foreign key
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", referencedColumnName = "position_id")
	private Position position;

	// MANDATORY NO-ARGUMENT CONSTRUCTOR (For JPA)
	public Employee() {}

	public Employee(Integer id, String name, String email, String phoneNo, double salary,
			LocalDate joinedDate, Department department, Position position) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.salary = salary;
		this.joinedDate = joinedDate;
		this.department = department;
		this.position = position;
	}

	// Getters and Setters
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPhoneNo() { return phoneNo; }
	public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

	public double getSalary() { return salary; }
	public void setSalary(double salary) { this.salary = salary; }

	public LocalDate getJoinedDate() { return joinedDate; }
	public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }

	public Department getDepartment() { return department; }
	public void setDepartment(Department department) { this.department = department; }

	public Position getPosition() { return position; }
	public void setPosition(Position position) { this.position = position; }
}