package com.pratice.pratice.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.pratice.pratice.Service.StudentService;
import com.pratice.pratice.entity.Student;
import com.pratice.pratice.repository.StudentRepository;
import com.pratice.pratice.utility.ExcelUtility;

@Service
public class StudentServiceImpl implements StudentService {

@Autowired
StudentRepository studentRepo;
	@Override
	public void save(MultipartFile file) {
		try {
			   List<Student> stuList = ExcelUtility.excelToStuList(file.getInputStream());
			   System.out.println("****"+ stuList);
			   studentRepo.saveAll(stuList);
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<Student> findAll() {
		return studentRepo.findAll();
		
	}


}
