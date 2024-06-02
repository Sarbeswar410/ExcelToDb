package com.pratice.pratice.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratice.pratice.Service.JsonExcelService;
import com.pratice.pratice.Service.StudentService;
import com.pratice.pratice.entity.Student;
import com.pratice.pratice.utility.ExcelUtility;


@RestController
@RequestMapping("/api/admin")
public class StudentController {

	@Autowired
	StudentService studentservice;
	@Autowired 
	JsonExcelService excelService;
	@PostMapping("/excel/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
      String message = "";
      if (ExcelUtility.hasExcelFormat(file)) {
        try {
        	System.out.println("This is file "+ file);
        	 System.out.println("Received file: " + file.getOriginalFilename());
             System.out.println("File size: " + file.getSize() + " bytes");
        	studentservice.save(file);
          message = "The Excel file is uploaded: " + file.getOriginalFilename();
          return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception exp) {
          message = "The Excel file is not upload: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
      }
      message = "Please upload an excel file!";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
  
  @GetMapping("/student-list")
  public ResponseEntity<?> getStudents() {
      Map<String, Object> respStu = new LinkedHashMap<String, Object>();
      List<Student> studList = studentservice.findAll();
      if (!studList.isEmpty()) {
          respStu.put("status", 1);
          respStu.put("data", studList);
          return new ResponseEntity<>(respStu, HttpStatus.OK);
      } else {
          respStu.clear();
          respStu.put("status", 0);
          respStu.put("message", "Data is not found");
          return new ResponseEntity<>(respStu, HttpStatus.NOT_FOUND);
      }
  }
  @PostMapping("/saveToExcel")
  public ResponseEntity<String> saveToExcel(@RequestBody String jsonData) {
      if (excelService.saveJsonToExcel(jsonData)) {
          return ResponseEntity.ok("Data saved to Excel successfully.");
      } else {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data to Excel.");
      }
  }
	


}
