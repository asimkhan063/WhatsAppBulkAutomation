package com.whatsapp.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    public static void main(String[] args) {

        String filePath = "src/main/resources/contacts.xlsx";

        // Create resources folder if it does not exist
        File file = new File(filePath);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            // Create Sheet
            Sheet sheet = workbook.createSheet("Contacts");

            // Create Header Row
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("name");
            headerRow.createCell(1).setCellValue("phone");
            headerRow.createCell(2).setCellValue("message");

            // Contact 1
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Asim");
            row1.createCell(1).setCellValue("916397942823");
            row1.createCell(2).setCellValue("Hello Himanshu, this is a test message.");

            // Contact 2
            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("Himanshu");
            row2.createCell(1).setCellValue("919627599597");
            row2.createCell(2).setCellValue("Hello Himanshu, this is a test message.");

            // Contact 3
            Row row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue("Rahul Yadav");
            row3.createCell(1).setCellValue("918218651146");
            row3.createCell(2).setCellValue("Hello Rahul Sir, this is a test message.");
            // Contact 4
            Row row4 = sheet.createRow(4);
            row3.createCell(0).setCellValue("Arjun");
            row3.createCell(1).setCellValue("919876594321");
            row3.createCell(2).setCellValue("Hello Arjun, this is a test message.");

            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            // Write Excel file
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }

            System.out.println("=================================");
            System.out.println(" Excel File Generated Successfully");
            System.out.println("=================================");
            System.out.println("File: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error creating Excel file:");
            e.printStackTrace();
        }
    }
}
