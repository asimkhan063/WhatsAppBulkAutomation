/**package com.whatsapp;

import com.whatsapp.model.Contact;
import com.whatsapp.service.ExcelReader;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" WhatsApp Bulk Automation");
        System.out.println(" Excel Contact Reader");
        System.out.println("=================================");

        ExcelReader excelReader =
                new ExcelReader();

        List<Contact> contacts =
                excelReader.readContacts(
                        "src/main/resources/contacts.xlsx"
                );

        System.out.println(
                "\nTotal Contacts: "
                        + contacts.size()
        );

        System.out.println(
                "\nContact Details:\n"
        );

        for (Contact contact : contacts) {

            System.out.println(
                    "Name: "
                            + contact.getName()
            );

            System.out.println(
                    "Phone: "
                            + contact.getPhoneNumber()
            );

            System.out.println(
                    "Message: "
                            + contact.getMessage()
            );

            System.out.println(
                    "--------------------------"
            );
        }
    }
}*/
/**
package com.whatsapp;

import com.whatsapp.service.WhatsAppService;

public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" WhatsApp Bulk Automation");
        System.out.println("=================================");

        WhatsAppService whatsappService = new WhatsAppService();

        whatsappService.openWhatsApp();

        System.out.println();
        System.out.println("WhatsApp Web is ready.");
        System.out.println("Scan QR code if required.");
        System.out.println("Press ENTER to close browser...");

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        whatsappService.closeBrowser();
    }
}

*/


package com.whatsapp;

import com.whatsapp.model.Contact;
import com.whatsapp.service.ExcelReader;
import com.whatsapp.service.WhatsAppService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" WhatsApp Bulk Automation");
        System.out.println("=================================");


        // ==============================
        // STEP 1: Read Excel
        // ==============================

        ExcelReader excelReader =
                new ExcelReader();

        List<Contact> contacts =
                excelReader.readContacts(
                        "src/main/resources/contacts.xlsx"
                );

        System.out.println();
        System.out.println(
                "Total Contacts: "
                        + contacts.size()
        );


        if (contacts.isEmpty()) {

            System.out.println(
                    "No contacts found in Excel."
            );

            return;
        }


        // ==============================
        // STEP 2: Start WhatsApp
        // ==============================

        WhatsAppService whatsappService =
                new WhatsAppService();

        whatsappService.openWhatsApp();


        // ==============================
        // STEP 3: Login / QR
        // ==============================

        System.out.println();
        System.out.println(
                "================================="
        );

        System.out.println(
                "WhatsApp Web is opening..."
        );

        System.out.println(
                "Scan QR code if required."
        );

        System.out.println(
                "After WhatsApp Web is completely"
        );

        System.out.println(
                "loaded, press ENTER to continue..."
        );

        Scanner scanner =
                new Scanner(System.in);

        scanner.nextLine();


        // ==============================
        // STEP 4: Send Messages
        // ==============================

        whatsappService.sendBulkMessages(
                contacts
        );
/**
        // STEP 4: Test ONE message

        System.out.println();
        System.out.println("=================================");
        System.out.println(" Testing One WhatsApp Message");
        System.out.println("=================================");

        Contact testContact = contacts.get(1);

        whatsappService.sendMessage(
                testContact.getPhoneNumber(),
                testContact.getMessage()
        );*/

        // ==============================
        // STEP 5: Close Browser
        // ==============================

        System.out.println();
        System.out.println(
                "Press ENTER to close browser..."
        );

        scanner.nextLine();

        whatsappService.closeBrowser();

        scanner.close();
    }
}