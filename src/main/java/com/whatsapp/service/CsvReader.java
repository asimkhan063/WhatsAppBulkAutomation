package com.whatsapp.service;

import com.opencsv.CSVReader;
import com.whatsapp.model.Contact;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Contact> readContacts(String filePath) {

        List<Contact> contacts = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

            // Header skip
            reader.skip(1);

            String[] line;

            while ((line = reader.readNext()) != null) {

                String name = line[0];
                String phone = line[1];
                String message = line[2];

                Contact contact =
                        new Contact(name, phone, message);

                contacts.add(contact);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error reading CSV file: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return contacts;
    }
}