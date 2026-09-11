package com.whatsapp.service;

import com.whatsapp.model.Contact;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public List<Contact> readContacts(String filePath) {

        List<Contact> contacts = new ArrayList<>();

        try (
                FileInputStream fileInputStream =
                        new FileInputStream(filePath);

                Workbook workbook =
                        WorkbookFactory.create(fileInputStream)
        ) {

            Sheet sheet = workbook.getSheetAt(0);

            DataFormatter formatter = new DataFormatter();

            // Row 0 = Header
            for (int i = 1;
                 i <= sheet.getLastRowNum();
                 i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String name =
                        getCellValue(
                                row.getCell(0),
                                formatter
                        );

                String phone =
                        getCellValue(
                                row.getCell(1),
                                formatter
                        );

                String message =
                        getCellValue(
                                row.getCell(2),
                                formatter
                        );

                if (name.isEmpty()
                        || phone.isEmpty()
                        || message.isEmpty()) {

                    System.out.println(
                            "Skipping empty row: " + i
                    );

                    continue;
                }

                Contact contact =
                        new Contact(
                                name,
                                phone,
                                message
                        );

                contacts.add(contact);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error reading Excel file: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return contacts;
    }


    private String getCellValue(
            Cell cell,
            DataFormatter formatter) {

        if (cell == null) {
            return "";
        }

        return formatter
                .formatCellValue(cell)
                .trim();
    }
}