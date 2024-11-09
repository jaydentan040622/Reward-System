package Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Policy {

    private int expiryMonths;

    public Policy() {
        expiryMonths = 3; //Deault Expiration Duration
    }

    // Method to update expiry date for the last record only
    public void updateExpiryDate() {
        // Input and output file paths
        String inputFile = "earning.csv";
        String outputFile = "updated_earning.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            String lastLine = null;
            String prevLines = ""; // Store previous lines excluding the last line
            while ((line = reader.readLine()) != null) {
                if (lastLine != null) {
                    // Append previous lines excluding the last line
                    prevLines += lastLine + System.lineSeparator();
                }
                lastLine = line;
            }

            // Write previous lines back to the output file
            writer.write(prevLines);

            if (lastLine != null) {
                // Split the last line by comma to extract date field
                String[] fields = lastLine.split(",");
                if (fields.length >= 6) {
                    // Extract the earning date field
                    String earningDateString = fields[4];

                    // Parse earning date string to LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate earningDate = LocalDate.parse(earningDateString, formatter);

                    // Add Expiry months to the earning date
                    LocalDate updatedDate = earningDate.plusMonths(getExpiryMonths());

                    // Format updated date back to string
                    String updatedDateString = formatter.format(updatedDate);

                    // Update the expiration date field (index 5) with the updated date
                    fields[5] = updatedDateString;

                    // Join the fields back to a single line
                    String updatedLine = String.join(",", fields);

                    // Write the updated line to the output file
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    // Handle malformed last line
                    System.out.println("Malformed last line: " + lastLine);
                }
            } else {
                System.out.println("No records found in the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the updated file to overwrite the original file
        try {
            java.io.File originalFile = new java.io.File(inputFile);
            java.io.File updatedFile = new java.io.File(outputFile);
            if (originalFile.exists()) {
                if (originalFile.delete()) {
                    if (!updatedFile.renameTo(originalFile)) {
                        System.out.println("Failed to rename the updated file.");
                    }
                } else {
                    System.out.println("Failed to delete the original file.");
                }
            } else {
                System.out.println("Original file does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getExpiryMonths() {
        return expiryMonths;
    }

    public void setExpiryMonths(int expiryMonths) {
        this.expiryMonths = expiryMonths;
    }

    public boolean validateMonth(String input) {
        String monthsRegex = "(0?[1-9]|1[0-2])";
        return Pattern.matches(monthsRegex, input);
    }



}
