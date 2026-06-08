package com.gic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gic.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
