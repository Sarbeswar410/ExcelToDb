package com.pratice.pratice.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonExcelService {
 public boolean saveJsonToExcel(String jsonData) {
	 System.out.println(jsonData);
	 try {
		    // Parse JSON data array
		    ObjectMapper mapper = new ObjectMapper();
		    JsonNode rootNode = mapper.readTree(jsonData);

		    // Create a new workbook
		    try (Workbook workbook = new XSSFWorkbook()) {
		        // Create a sheet
		        Sheet sheet = workbook.createSheet("Data");

		        // Create header row dynamically from JSON keys
		        Row headerRow = sheet.createRow(0);
		        Set<String> headersSet = new HashSet<>();
		        for (JsonNode node : rootNode) {
		            Iterator<String> fieldNames = node.fieldNames();
		            while (fieldNames.hasNext()) {
		                headersSet.add(fieldNames.next());
		            }
		        }
		        String[] headers = headersSet.toArray(new String[0]);
		        for (int i = 0; i < headers.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(headers[i]);
		        }

		        // Iterate over JSON objects and populate data rows
		        int rowNum = 1;
		        for (JsonNode node : rootNode) {
		            Row dataRow = sheet.createRow(rowNum++);
		            for (int i = 0; i < headers.length; i++) {
		                String header = headers[i];
		                dataRow.createCell(i).setCellValue(node.has(header) ? node.get(header).asText() : "");
		            }
		        }

		        // Write to file
		        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
		            workbook.write(fileOut);
		            System.out.println("Excel file generated successfully.");
		            return true;
		        }
		    }
     } catch (IOException e) {
         e.printStackTrace();
         return false;
     }
}}
