package com.whatsapp.service;

import com.whatsapp.model.Contact;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class WhatsAppService {

    private WebDriver driver;
    private WebDriverWait wait;

    public void openWhatsApp() {

        System.out.println("=================================");
        System.out.println(" Starting WhatsApp Automation");
        System.out.println("=================================");

        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--user-data-dir=C:\\WhatsAppAutomation\\ChromeProfileNew1"
        );

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(60)
        );

        driver.manage().window().maximize();

        driver.get("https://web.whatsapp.com");

        System.out.println("WhatsApp Web opened.");
        System.out.println("Please scan QR code if required.");
    }

    public boolean sendMessage(
            String phoneNumber,
            String message
    ) {

        try {

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Sending message");
            System.out.println("Phone   : " + phoneNumber);
            System.out.println("Message : " + message);

            String cleanPhone =
                    phoneNumber.replaceAll("\\D", "");

            if (cleanPhone.isEmpty()) {

                System.out.println(
                        "Invalid phone number."
                );

                return false;
            }

            if (message == null || message.isBlank()) {

                System.out.println(
                        "Message is empty."
                );

                return false;
            }

            String encodedMessage =
                    URLEncoder.encode(
                            message,
                            StandardCharsets.UTF_8
                    );

            String url =
                    "https://web.whatsapp.com/send?phone="
                            + cleanPhone
                            + "&text="
                            + encodedMessage;

            System.out.println(
                    "Opening WhatsApp chat..."
            );

            driver.get(url);

            /*
             * Give WhatsApp time to load.
             */
            Thread.sleep(3000);


            /*
             * IMPORTANT:
             * Check whether WhatsApp says that the
             * phone number is not registered on WhatsApp.
             */
            if (isNumberNotOnWhatsApp()) {

                System.out.println();
                System.out.println(
                        "Number is NOT available on WhatsApp."
                );
                System.out.println(
                        "Skipping contact: " + cleanPhone
                );

                closeInvalidNumberDialog();

                return false;
            }


            /*
             * Find message box.
             */
            WebElement messageBox =
                    findMessageBox();

            if (messageBox == null) {

                System.out.println(
                        "Message box not found."
                );

                System.out.println(
                        "Current URL: "
                                + driver.getCurrentUrl()
                );

                return false;
            }

            System.out.println(
                    "Message box found."
            );

            messageBox.click();

            Thread.sleep(500);


            /*
             * Message is already present in the composer
             * because it was passed through the URL.
             *
             * Press ENTER to send.
             */
            messageBox.sendKeys(Keys.ENTER);

            System.out.println(
                    "Message sent successfully."
            );

            Thread.sleep(2000);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Failed to send message to: "
                            + phoneNumber
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            return false;
        }
    }
    private boolean isNumberNotOnWhatsApp() {

        long endTime =
                System.currentTimeMillis() + 10000;

        while (System.currentTimeMillis() < endTime) {

            try {

                /*
                 * WhatsApp displays a dialog when the
                 * phone number is not registered.
                 */
                List<WebElement> dialogs =
                        driver.findElements(
                                By.xpath(
                                        "//div[@role='dialog' and @aria-modal='true']"
                                )
                        );

                for (WebElement dialog : dialogs) {

                    try {

                        if (!dialog.isDisplayed()) {
                            continue;
                        }

                        String text =
                                dialog.getText()
                                        .toLowerCase();

                        if (text.contains("isn't on whatsapp")
                                || text.contains("is not on whatsapp")
                                || text.contains("not on whatsapp")) {

                            System.out.println(
                                    "WhatsApp invalid-number dialog detected."
                            );

                            return true;
                        }

                    } catch (Exception ignored) {
                    }
                }

                Thread.sleep(500);

            } catch (Exception ignored) {
            }
        }

        return false;
    }


    private void closeInvalidNumberDialog() {

        try {

            /*
             * Try to find the button inside the dialog.
             */
            List<WebElement> buttons =
                    driver.findElements(
                            By.xpath(
                                    "//div[@role='dialog']//button"
                            )
                    );

            for (WebElement button : buttons) {

                try {

                    if (button.isDisplayed()
                            && button.isEnabled()) {

                        String text =
                                button.getText()
                                        .toLowerCase();

                        /*
                         * Common close/dismiss buttons.
                         */
                        if (text.contains("ok")
                                || text.contains("close")
                                || text.contains("cancel")) {

                            button.click();

                            System.out.println(
                                    "Invalid-number dialog closed."
                            );

                            return;
                        }

                    }

                } catch (Exception ignored) {
                }
            }


            /*
             * Fallback: press ESC.
             */
            driver.switchTo()
                    .activeElement()
                    .sendKeys(Keys.ESCAPE);

            System.out.println(
                    "Invalid-number dialog dismissed."
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not close invalid-number dialog."
            );
        }
    }



    private WebElement findMessageBox() {

        long endTime =
                System.currentTimeMillis() + 60000;

        while (System.currentTimeMillis() < endTime) {

            try {

                // Selector 1: Message box with role textbox
                List<WebElement> elements =
                        driver.findElements(
                                By.xpath(
                                        "//div[@contenteditable='true' and @role='textbox']"
                                )
                        );

                for (WebElement element : elements) {

                    try {

                        if (element.isDisplayed()
                                && element.isEnabled()) {

                            System.out.println(
                                    "Message box found using role='textbox'."
                            );

                            return element;
                        }

                    } catch (Exception ignored) {
                    }
                }


                // Selector 2: Any visible contenteditable element
                elements =
                        driver.findElements(
                                By.cssSelector(
                                        "div[contenteditable='true']"
                                )
                        );

                for (WebElement element : elements) {

                    try {

                        if (element.isDisplayed()
                                && element.isEnabled()) {

                            System.out.println(
                                    "Message box found using contenteditable."
                            );

                            return element;
                        }

                    } catch (Exception ignored) {
                    }
                }


                // Selector 3: textarea/input style textbox
                elements =
                        driver.findElements(
                                By.xpath(
                                        "//*[@role='textbox']"
                                )
                        );

                for (WebElement element : elements) {

                    try {

                        if (element.isDisplayed()
                                && element.isEnabled()) {

                            System.out.println(
                                    "Message box found using role textbox."
                            );

                            return element;
                        }

                    } catch (Exception ignored) {
                    }
                }


                Thread.sleep(1000);

            } catch (Exception e) {

                System.out.println(
                        "Waiting for message box..."
                );
            }
        }

        System.out.println(
                "Message box could not be located after 60 seconds."
        );

        return null;
    }

    public void sendBulkMessages(
            List<Contact> contacts
    ) {

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                " Starting Bulk Message Sending"
        );
        System.out.println(
                " Total Contacts: "
                        + contacts.size()
        );
        System.out.println(
                "================================="
        );

        int success = 0;
        int failed = 0;

        for (Contact contact : contacts) {

            System.out.println();
            System.out.println(
                    "Processing: "
                            + contact.getName()
            );

            boolean sent =
                    sendMessage(
                            contact.getPhoneNumber(),
                            contact.getMessage()
                    );

            if (sent) {

                success++;

            } else {

                failed++;
            }

            /*
             * Small pause between opted-in test contacts.
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println();
        System.out.println(
                "================================="
        );
        System.out.println(
                " Bulk Message Report"
        );
        System.out.println(
                "================================="
        );
        System.out.println(
                "Total Contacts : "
                        + contacts.size()
        );
        System.out.println(
                "Successful     : "
                        + success
        );
        System.out.println(
                "Failed         : "
                        + failed
        );
        System.out.println(
                "================================="
        );
    }

    public void closeBrowser() {

        if (driver != null) {

            driver.quit();

            System.out.println(
                    "Browser closed."
            );
        }
    }
}