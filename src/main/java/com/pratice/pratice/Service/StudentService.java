package com.pratice.pratice.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pratice.pratice.entity.Student;



public interface StudentService {
	void save(MultipartFile file);
	List<Student> findAll();
}
