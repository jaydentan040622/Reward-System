
package Assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDashBoard {

    private static final String FILENAME = "user.txt";
    private String memberNo;

    public void printAdminMainMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("|            Admin Main Menu            |");
        System.out.println("-----------------------------------------");
        System.out.println("| 0. Exit                               |");
        System.out.println("| 1. Check Customer Details             |");
        System.out.println("| 2. View all products                  |");
        System.out.println("| 3. Check Earning file                 |");
        System.out.println("| 4. Top redeemed Item from customer    |");
        System.out.println("| 5. Least redeemed Item from Customer  |");
        System.out.println("| 6. User Activity Checking             |");
        System.out.println("| 7. Update TierMultiplier              |");
        System.out.println("| 8. Update Expiration Duration         |");
        System.out.println("| 9. Live server                        |");
        System.out.println("-----------------------------------------");
        System.out.print("  Enter your choice: ");
    }

    public void printAdminReportMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println("|              Report Menu              |");
        System.out.println("-----------------------------------------");
        System.out.println("| 1. View the most popular gift redeem  |");
        System.out.println("| 2. View the least gift redeem         |");
        System.out.println("| 3. User profile status                |");
        System.out.println("-----------------------------------------");
        System.out.print("Enter your choice: ");
    }

    public void checkCustomerDetails() {
        boolean found = false;
        System.out.println("--------------------------------");
        System.out.println("View Customer Details");
                    System.out.println("--------------------------------");
        try (InputStream input = Files.newInputStream(Paths.get(FILENAME)); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            Scanner scanner = new Scanner(System.in);
            

            System.out.print("Enter membership number: ");
            memberNo = scanner.nextLine();
            System.out.println("");


            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(memberNo) && !found) {
                    
                    System.out.println("Username: " + user[0]);
                    System.out.println("Email: " + user[1]);
                    System.out.println("Phone: " + user[2]);
                    System.out.println("Membership Number: " + user[4]);
                    

                    // Extract current value from earning.csv
                    int currentValue = getCurrentValueFromCSV();
                        System.out.println("Current Value: " + currentValue);
                        System.out.println("");
                    found = true;

                    break;
                }
                
            }
            if (!found){
                System.err.println("Member Not Found!");
            }

        } catch (Exception e) {
            System.out.println("Error viewing customer details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getCurrentValueFromCSV() {
        int currentValue = 0;
        try (BufferedReader csvReader = new BufferedReader(new FileReader("earning.csv"))) {
            String row;
            // Skip header row
            csvReader.readLine();
            // Read the next row containing the current value
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if(data[0].equals(memberNo)){
                    currentValue += (Integer.parseInt(data[2]));
                }
                

            }
        } catch (IOException e) {
            System.out.println("Error reading earning.csv: " + e.getMessage());
            e.printStackTrace();
        }
        
        return currentValue;
    }

    public static void open(File document) throws IOException {

        Desktop dt = Desktop.getDesktop();
        dt.open(document);
    }

    public void checkEarningFile() {
        try {
            open(new File("earning.csv"));
            System.out.println(" ");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewAllProducts() {
        Map<String, Integer> productQuantities = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length > 2) {
                    String item = data[2].split(": ")[1];
                    int quantity = Integer.parseInt(data[3].split(": ")[1]);

                    productQuantities.put(item, productQuantities.getOrDefault(item, 0) + quantity);
                }
            }
            System.out.println("\n-----------------------------------------------------");
            System.out.printf("| %-28s | %-18s |%n", "Product", "Total Quantity");
            System.out.println("-----------------------------------------------------");
            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                System.out.printf("| %-28s | %-18d |%n", entry.getKey(), entry.getValue());
                System.out.println("-----------------------------------------------------");
            }
            System.out.println();

        } catch (IOException e) {
            System.out.println("Error reading redemptionHistory.txt: " + e.getMessage());
        }
    }

    public void displayTopRedeemedItem() {
        Map<String, Integer> redeemedItems = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length < 4) {
                    continue; // Skip this line because it doesn't contain the expected data
                }
                String itemName = parts[2].replace("Redeemed Item: ", "");
                int quantity = Integer.parseInt(parts[3].replace("Quantity: ", ""));
                redeemedItems.put(itemName, redeemedItems.getOrDefault(itemName, 0) + quantity);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(redeemedItems.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("-------------------------");
        System.out.println("Top 3 items redeemed:");
        System.out.println("-------------------------");
        for (int i = 0; i < Math.min(list.size(), 3); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getKey());
        }
    }

    public void displayLowRedeemedItem() {
        Map<String, Integer> redeemedItems = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length < 4) {
                    continue; // Skip this line because it doesn't contain the expected data
                }
                String itemName = parts[2].replace("Redeemed Item: ", "");
                int quantity = Integer.parseInt(parts[3].replace("Quantity: ", ""));
                redeemedItems.put(itemName, redeemedItems.getOrDefault(itemName, 0) + quantity);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(redeemedItems.entrySet());
        list.sort(Map.Entry.comparingByValue());
        
        System.out.println("-------------------------");
        System.out.println("Top 3 least redeemed items:");
        System.out.println("-------------------------");
        for (int i = 0; i < Math.min(list.size(), 3); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getKey());
        }
    }

    public void ActivityTracking() {
        System.out.println("Activity Tracking");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MemberNo: ");
        memberNo = scanner.nextLine();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
            String line;
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-20s %-20s %-30s %-10s%n", "Date", "Time", "Redeemed Item", "Quantity");
            System.out.println("---------------------------------------------------------------------------------");
            while ((line = reader.readLine()) != null) {
                if (line.contains("Member No: " + memberNo + ",")) {
                    String[] parts = line.split(", ");
                    String dateTime = parts[0].replace("Date: ", "");
                    String[] dateTimeParts = dateTime.split(" ");
                    String date = dateTimeParts[0];
                    String time = dateTimeParts[1];
                    String itemName = parts[2].replace("Redeemed Item: ", "");
                    String quantity = parts[3].replace("Quantity: ", "");
                    System.out.printf("%-20s %-20s %-30s %-10s %n", date, time, itemName, quantity);
                }
            }
            System.out.println("---------------------------------------------------------------------------------");
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

    }
}
