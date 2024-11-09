
package Assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MemberDashBoard {

    private static final String FILENAME = "user.txt";
    private String memberNo;
    private final static String ANSI_COLORNAME = "\u001B[37m";
    private final static String ANSI_RED_BACKGROUND = "\u001B[41m";
    Loyalty loyalty = new Loyalty();
    Scanner scanner = new Scanner(System.in);

    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    public void printStartMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Start Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 1. Create User Account     |");
        System.out.println("| 2. Login                   |");
        System.out.println("| 3. Forgot Password         |");
        System.out.println("| 4. Exit                    |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public void printMemberMainMenu() {
        System.out.println("\n--------------------------------------------------------");
        System.out.print("|           ");
        System.out.print(ANSI_COLORNAME + ANSI_RED_BACKGROUND + " Welcome to our rewards system " + ANSI_RESET);
        System.out.println("            |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 0. Exit                                              |");
        System.out.println("| 1. Earn rewards                                      |");
        System.out.println("| 2. Redeem rewards                                    |");
        System.out.println("| 3. View Profile                                      |");
        System.out.println("| 4. Show my referees                                  |");
        System.out.println("| 5. Customer Service                                  |");
        System.out.println("--------------------------------------------------------");

        System.out.print("Enter your choice: ");

    }

    public void showReferees(String memberNo) {

        try (InputStream input = Files.newInputStream(Paths.get(FILENAME));
                BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            System.out.println("Show memberNo at referal: " + memberNo);

            String line;
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|             My Referees               |");
            System.out.println("-----------------------------------------");
            int total = 0;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 6 && user[5].equals(memberNo)) {

                    total += 1;
                    System.out.println("| Username\t\t: " + user[0] + "\t|");
                    System.out.println("| Membership Number\t: " + user[4] + "\t|");

                    System.out.println("-----------------------------------------");

                }
            }

            System.out.println("Total: " + total);

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mError: user.txt not found!\u001B[0m");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void forgot() throws IOException {

        System.out.println("\n===========================================");
        System.out.println("|             Forgot Password               |");
        System.out.println("===========================================\n");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        boolean updated = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] user = line.split(" ");
            if (user.length >= 5 && user[0].equals(username) && user[1].equals(email)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                System.out.print("Confirm new password: ");
                String confirmPassword = scanner.nextLine();

                if (newPassword.equals(confirmPassword)) {
                    if (isValidPassword(newPassword)) {
                        user[3] = newPassword;
                        lines.set(i, String.join(" ", user));
                        updated = true;
                        break;
                    } else {
                        System.err.println(
                                "\u001B[31mInvalid password! Password must be between 6 and 20 characters.\u001B[0m");
                    }
                } else {
                    System.err.println("\u001B[31mPasswords do not match!\u001B[0m");
                }
            }
        }

        if (updated) {
            Files.write(Paths.get(FILENAME), lines, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("\nPassword reset successful\n");
        } else {
            System.err.println("\nUsername or email not found!\n");

        }

    }

    public void viewProfile(String memberNo) throws FileNotFoundException {

        // Assuming getMemberId() returns the correct membership number
        String membershipNumber = memberNo;
        List<Earning> earnings = CSVWrite.getAllEarnings();

        List<Earning> filteredList = new ArrayList<>();
        List<Earning> otherMembers = new ArrayList<>();

        for (Earning earn : earnings) {

            if (earn.getMemberNo().equals(memberNo)) {

                filteredList.add(earn);
            } else {
                otherMembers.add(earn);
            }
        }
        Collections.sort(filteredList, new EarningComparator());
        int totalPoints = 0;
        for (Earning e : filteredList) { // for each Player p in list
            totalPoints += e.getValue();
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("|                         View Profile                          |");
        System.out.println("-----------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Username", ":", user[0], "|");
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Email", ":", user[1], "|");
                    // System.out.printf("| Email\t\t\t: %-37s |\n", user[1]);
                    // System.out.printf("| Phone\t\t\t: %-37s |\n", user[2]);
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Phone", ":", user[2], "|");
                    // System.out.printf("| Membership Number\t: %-37s |\n", user[4]);
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Membership Number", ":", user[4], "|");
                    // System.out.printf("| Current Point\t\t: %-37d |\n", totalPoints);
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Current Point", ":", totalPoints, "|");
                    System.out.printf("%-23s %-1s %-9s %-16s%-4s%-6s %-1s\n", "| Current Grade", ":",
                            loyalty.determineGrade(user[4]), "(Earning Points x", loyalty.getMultiplier(user[4]), ")",
                            "|");
                    System.out.printf("%-23s %-1s %-37s %-1s\n", "| Next Tier", ":", loyalty.getNextTiers(user[4]),
                            "|");
                    // System.out.println("| " + loyalty.toString(memberNo));
                    System.out.println("-----------------------------------------------------------------");

                    // Prompt the user outside of the loop
                    String modifyDetails = promptModifyDetails();
                    if (modifyDetails.equalsIgnoreCase("Y")) {
                        updateAccount(membershipNumber);
                    } else if (modifyDetails.equalsIgnoreCase("N")) {
                        return;
                    } else {
                        System.err.println("Invalid Input!\nReturning to the Menu");
                        return;
                    }

                    return; // Return if the profile is found
                }
            }

            System.err.println("User not found");

        } catch (FileNotFoundException e) {
            System.out.println("Error: user.txt not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String promptModifyDetails() {
        System.out.print("Do you want to modify your account details? (Y/N): ");
        String choice = scanner.nextLine();
        return choice;
    }

    public void updateAccount(String membershipNumber) {
        System.out.println("\nWhich details do you want to modify?");
        System.out.println("------------------------------------------------");
        System.out.println("1. Username");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Password");
        System.out.println("5. Go back to main menu");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                updateUsername(membershipNumber); // Pass membershipNumber to updateUsername
                break;
            case "2":
                updateEmail(membershipNumber);
                break;
            case "3":
                updatePhone(membershipNumber);
                break;
            case "4":
                updatePassword(membershipNumber);
                break;
            case "5":

                break;
            default:
                System.err.println("Invalid choice!\n");
                updateAccount(membershipNumber); // Pass membershipNumber to recursive call
        }
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 6 && username.length() <= 20 && !username.matches("[^a-zA-Z0-9]");
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("[0-9]+") && phone.length() == 10 || phone.length() == 11;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    public void updateUsername(String membershipNumber) {
        try {
            Path path = Paths.get(FILENAME);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();

                    // Check if the new username is valid
                    if (!isValidUsername(newUsername)) {
                        System.out.println(
                                "\u001B[31mInvalid username format should be (6-20 characters && no special characters)! \u001B[0m");
                        updateAccount(membershipNumber);
                    } else {

                        // Update the username in the line
                        user[0] = newUsername;
                        lines.set(i, String.join(" ", user));
                        updated = true;
                        break; // Exit the loop after updating the username
                    }
                }
            }

            if (updated) {

                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Username updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating username: " + ex.getMessage());
        }
    }

    public void updateEmail(String membershipNumber) {
        try {
            Path path = Paths.get(FILENAME);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    String newEmail = promptForValidEmail(); // Validate and get new email from user input
                    if (newEmail == null) {
                        System.out.println("\u001B[31mInvalid email format! Email not updated! \u001B[0m");
                        updateAccount(membershipNumber);
                        ; // Exit if the user input is invalid
                    }else{
                    
                    user[1] = newEmail;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break;
                }
            }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating email: " + ex.getMessage());
        }
    }

    private String promptForValidEmail() {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        if (!isValidEmail(newEmail)) {
            return null;
        }
        return newEmail;
    }



    public void updatePhone(String membershipNumber) {
        try {
            Path path = Paths.get(FILENAME);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();

                    // Check if the new phone number is valid
                    if (!isValidPhone(newPhone)) {
                        System.out.println(
                                "\u001B[31mInvalid phone number format should be (digits only, between 10/11 words)! \u001B[0m");
                        updateAccount(membershipNumber);
                        ; // Exit the method if the phone number is invalid
                    }else{

                    // Update the phone number in the line
                    user[2] = newPhone;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break; // Exit the loop after updating the phone number
                }
            }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Phone number updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating phone number: " + ex.getMessage());
        }

    }

    public void updatePassword(String membershipNumber) {
        try {
            Path path = Paths.get(FILENAME);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();

                    // Check if the new password is valid
                    if (!isValidPassword(newPassword)) {
                        System.out.println("\u001B[31mInvalid password format should be (6-20 characters)! \u001B[0m");
                        updateAccount(membershipNumber);
                        ; // Exit the method if the password is invalid
                    }else{

                    // Update the password in the line
                    user[3] = newPassword;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break; // Exit the loop after updating the password
                }
            }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("\u001B[31mMembership Number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating password: " + ex.getMessage());
        }

    }

}
