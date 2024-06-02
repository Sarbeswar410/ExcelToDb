package com.pratice.pratice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratice.pratice.entity.Student;



public interface StudentRepository extends JpaRepository<Student,Integer>  {

}
