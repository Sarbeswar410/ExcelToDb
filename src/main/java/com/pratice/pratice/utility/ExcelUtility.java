package com.pratice.pratice.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.pratice.pratice.entity.Student;


public class ExcelUtility {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//	static String[] Headers = { "ID", "Student Name", "Email", "Mobile Number" };
	static String SHEET = "";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Student> excelToStuList(InputStream is) throws IOException {
		  if (is == null) {
		        System.out.println("InputStream is null");
		        return null;
		    }
		  if (is.available() <= 0) {
		        System.out.println("Input file is empty");
		        return null;
		    }
		 
		try {
		
			Workbook workbook = new XSSFWorkbook(is);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				  SHEET = workbook.getSheetName(i);
			    System.out.println("Sheet Name: " + workbook.getSheetName(i));
			}
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			 Row headerRow = rows.next();
		        Iterator<Cell> headerCells = headerRow.cellIterator();
		        
		        // Dynamic headers
		        List<String> headers = new ArrayList<>();
		        while (headerCells.hasNext()) {
		            Cell cell = headerCells.next();
		            headers.add(cell.getStringCellValue());
		        }
		        System.out.println("Headers are:"+ headers);
			List<Student> stulist = new ArrayList<Student>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Student stu = new Student();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						stu.setId((int) currentCell.getNumericCellValue());
						break;
					case 1:
						stu.setStudentName(currentCell.getStringCellValue());
						break;
					case 2:
						stu.setEmail(currentCell.getStringCellValue());
						break;
					case 3:
					    stu.setMobileNumber(currentCell.getStringCellValue());
						break;
					default:
						break;
					}
					cellIdx++;

				}
				stulist.add(stu);
			}
			workbook.close();
			return stulist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}
