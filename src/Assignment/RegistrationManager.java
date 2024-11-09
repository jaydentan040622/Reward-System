package Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RegistrationManager {

    private static final String FILENAME = "user.txt";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    Policy policy = new Policy();
    Scanner scanner = new Scanner(System.in);
    String memberNo;

    public void createAccountUser() {

        try {
            Path path = Paths.get(FILENAME);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            // Check if the file is empty
            boolean fileIsEmpty = Files.size(path) == 0;

            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            // Write the header line only if the file is empty
            if (fileIsEmpty) {
                writer.write("Username Email PhoneNo Password MemberNo Referral");
                writer.newLine();
            }

            System.out.println("\n===========================================");
            System.out.println("|          Create User Account            |");
            System.out.println("===========================================\n");

            // Collect user input
            String username = "";
            String email = "";
            String phone = "";
            String password = "";
            String referrer = "";

            System.out.print("Username: ");
            username = scanner.nextLine();
            if (!isValidUsername(username)) {
                System.out.println(
                        "\u001B[31mInvalid username format should be (6-20 characters, no special characters)! \u001B[0m");
                return; // Return to the menu
            }

            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            
            if (!isValidEmail(email)) {
                System.out.println("\u001B[31mInvalid email format should be (format: xxxxxx@yyy.zzz)! \u001B[0m");
                return; // Return to the menu
            }

            System.out.print("Enter your phone number: ");
            phone = scanner.nextLine();
            
            if (!isValidPhone(phone)) {
                System.out.println("\u001B[31mInvalid phone number format should be (digits only, between 10/11 words)! \u001B[0m");
                return; // Return to the menu
            }

            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            
            if (!isValidPassword(password)) {
                System.out.println("\u001B[31mInvalid password format should be (6-20 characters)! \u001B[0m");
                return; // Return to the menu
            }

            // Check if the username, email, or phone already exist in the file
            if (isAlreadyRegistered(username, email, phone)) {
                System.out.println(
                        "\u001B[31mUsername, email, or phone number already exists. Please try again! \u001B[0m");
                return; // Return to the menu
            }
            

            System.out.print("Referred by (Member No) (Empty to skip): ");
            referrer = scanner.nextLine();
            if (referrer.length() > 0) {
                boolean validMember = memberIsExists(referrer);
                if (!validMember) {
                    System.out.println("\u001B[31mMember does not exist! \u001B[0m");
                    return; // Return to the menu
                } else {
                    new Earning("Referral", 10, referrer);
                    policy.updateExpiryDate();
                }
            } else {
                referrer = "0";
            }

            

            

            

            

            // Generate a random membership number
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            memberNo = "ABC" + String.format("%04d", randomNumber);
            System.out.println("Your membership number is: " + memberNo);

            //Account creation message
            System.out.println(ANSI_GREEN + "\nAccount Creation\n" + ANSI_RESET);

            // Write the user account details to the file
            writer.write(username + " " + email + " " + phone + " " + password + " " + memberNo + " " + referrer);
            writer.newLine();

            // Close the writer
            writer.close();

            // Sort the user accounts based on username
            sortUserAccountsByUsername(FILENAME);

        } catch (IOException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        }
    }

    private void sortUserAccountsByUsername(String FILENAME) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILENAME));
            Collections.sort(lines); // Sort the lines
            Files.write(Paths.get(FILENAME), lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            System.out.println("Error sorting user accounts: " + ex.getMessage());
        }
    }

    public void createAccountStaff() {
        try {
            Path path = Paths.get("staff.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            // Check if the file is empty
            boolean fileIsEmpty = Files.size(path) == 0;

            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            // Write the header line only if the file is empty
            if (fileIsEmpty) {
                writer.write("Username Email PhoneNo Password StaffID");
                writer.newLine();
            }

            System.out.println("\n===========================================");
            System.out.println("|          Create Staff Account            |");
            System.out.println("===========================================\n");

            System.out.print("Enter your authentication key: ");
            String key = scanner.nextLine();
            if (!key.equals("A123")) {
                System.out.println("\u001B[31mInvalid authentication key! \u001B[0m");
                writer.close(); // Close the writer before returning
                return;
            }

            // Collect user input
            String username = "";
            String email = "";
            String phone = "";
            String password = "";

            System.out.print("Enter your username: ");
            username = scanner.nextLine();

            System.out.print("Enter your email: ");
            email = scanner.nextLine();

            System.out.print("Enter your phone number: ");
            phone = scanner.nextLine();

            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            // Validate input
            if (!isValidUsername(username)) {
                System.out.println(
                        "\u001B[31mInvalid username format should be (6-20 characters, no special characters)! \u001B[0m");
                return; // Return to the menu
            }

            if (!isValidEmail(email)) {
                System.out.println("\u001B[31mInvalid email format should be (format: xxxxxx@yyy.zzz)! \u001B[0m");
                return; // Return to the menu
            }

            if (!isValidPhone(phone)) {
                System.out.println("\u001B[31mInvalid phone number format should be (digits only, between 10/11 words)! \u001B[0m");
                return; // Return to the menu
            }

            if (!isValidPassword(password)) {
                System.out.println("\u001B[31mInvalid password format should be (6-20 characters)! \u001B[0m");
                return; // Return to the menu
            }

            // Check if the username, email, or phone already exist in the file
            if (isAlreadyRegisteredStaff(username, email, phone)) {
                System.out.println(
                        "\u001B[31mUsername, email, or phone number already exists. Please try again! \u001B[0m");
                writer.close(); // Close the writer before returning
                return;
            }

            // Generate staffID
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            String staffID = "STF" + String.format("%04d", randomNumber);
            System.out.println("Your staff ID is: " + staffID);

            // Write staff details to the staff file
            writer.write(username + " " + email + " " + phone + " " + password + " " + staffID);
            writer.newLine();
            writer.close();

            System.out.println("Staff account created successfully.");
            // If needed, you can add further logic here to redirect to the admin dashboard
            // AdminDashBoard adminDashBoard = new AdminDashBoard();
            // adminDashBoard.display();

        } catch (IOException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        }
    }

    private boolean isAlreadyRegistered(String username, String email, String phone) throws IOException {
        Path path = Paths.get(FILENAME);
        if (Files.exists(path)) {
            try (Scanner fileScanner = new Scanner(path)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(" ");
                    if (parts.length >= 3
                            && (parts[0].equals(username) || parts[1].equals(email) || parts[2].equals(phone))) {
                        return true; // Username, email, or phone already exists in the file
                    }
                }
            }
        }
        return false; // Not already registered
    }

    private boolean isAlreadyRegisteredStaff(String username, String email, String phone) throws IOException {
        Path path = Paths.get("staff.txt"); // Enclose the file name in quotes

        if (Files.exists(path)) {
            try (Scanner fileScanner = new Scanner(path)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(" ");

                    // Check if the line contains enough parts and matches the given username,
                    // email, or phone
                    if (parts.length >= 4
                            && (parts[0].equals(username) || parts[1].equals(email) || parts[2].equals(phone))) {
                        return true; // Username, email, or phone already exists in the file
                    }
                }
            }
        }
        return false; // Not already registered
    }

    private boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9]+") && username.length() >= 6 && username.length() <= 20;

    }

    private boolean isValidPhone(String phone) {
        return phone.matches("[0-9]+") && phone.length() == 10 || phone.length() == 11;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
    }

    public static boolean memberIsExists(String memberNo) {
        boolean found = false;

        // validate member ID
        try (InputStream input = Files.newInputStream(Paths.get("user.txt")); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String membershipNumber = memberNo;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user[4].equals(membershipNumber)) {
                    found = true;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: user.txt not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }

        return found;

    }

    public String getMemberNo() {
        return memberNo;
    }

}
