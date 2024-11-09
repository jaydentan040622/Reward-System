package Assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TARUC
 */
public class Main {

    private final static int POINTS_PER_RM = 10;
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    public static void writeToRedemptionHistory(RedemptionItem redemptionItem, String memberNo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("redemptionHistory.txt", true));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.write("Date: " + LocalDateTime.now().format(formatter) + ", Member No: " + memberNo + ", Redeemed Item: "
                + redemptionItem.getName() + ", Quantity: 1\n");
        writer.close();
    }

    // Record the redeemed item to the redemptionHistory.txt file
    public static void recordRedeemedItem(RedemptionItem redeemedItem, String memberNo, int quantity) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            String record = "Date: " + formattedNow + ", Member No: " + memberNo + ", Redeemed Item: "
                    + redeemedItem.getName() + ", Quantity: " + quantity;

            // Always add a new record
            try (FileWriter writer = new FileWriter("redemptionHistory.txt", true)) {
                writer.write(record + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while recording the redeemed item.");
            e.printStackTrace();
        }
    }

    static void deductPoints(int total, List<Earning> filteredList) {
        int counter = 0;
        while (total > 0) {
            if (total >= filteredList.get(counter).getValue()) {
                total -= filteredList.get(counter).getValue();
                filteredList.get(counter).setValue(0);
                counter++;
            } else {
                filteredList.get(counter).setValue(filteredList.get(counter).getValue() - total);
                total = 0;
                /////////
            }
        }
    }

    public static void printStartMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Start Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 0. Exit                    |");
        System.out.println("| 1. Create User Account     |");
        System.out.println("| 2. Login                   |");
        System.out.println("| 3. Forgot Password         |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void printRegisterMenu() {
        System.out.println("------------------------------");
        System.out.println("|       Register Menu        |");
        System.out.println("------------------------------");
        System.out.println("| 0. Exit                    |");
        System.out.println("| 1. Customer Register       |");
        System.out.println("| 2. Admin Register          |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void printLoginMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Login Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 0. Exit                    |");
        System.out.println("| 1. Customer Login          |");
        System.out.println("| 2. Admin Login             |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
    }

    /**
     * @param args the command line arguments
     */
    public static void open(File document) throws IOException {
        Desktop dt = Desktop.getDesktop();
        dt.open(document);
    }

    public static void main(String[] args) throws FileNotFoundException {

        // AccountOperations userAccount; // Use the interface type
        // userAccount = new MemberDashBoard();
        // userAccount.displayMenu();

        RedemptionItem[] redemptionItems = { new Product("Umbrella", 2000, "Calvin Klein"),
                new Product("Shampoo", 200, "Shokutbutsu"), new Product("Toothpaste", 250, "Colgate"),
                new Voucher("Year end sale voucher", 100, 50), new Voucher("Gold voucher", 500, 85) };
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean validate = false;
        Loyalty loyalty = new Loyalty();
        Policy policy = new Policy();
        Admin admin = new Admin();
        Member member = new Member();
        String expiryMonths;
        String startMenuChoice = "";
        String registerMenuChoice = "";
        String loginMenuChoice = "";
        String customerMainMenuChoice = "";
        String adminMainMenuCHoice = "";
        

        String memberNo = "";

        // System.out.println("MemberNo: " + memberNo); //Checking purpose, remove
        // before submit
        while (isRunning) {
            startMenuChoice = "";
            while (!startMenuChoice.equals("0")) {
                printStartMenu();
                startMenuChoice = scanner.nextLine();
                switch (startMenuChoice) {
                    case "1":
                        registerMenuChoice = "";
                        while (!registerMenuChoice.equals("0") && !registerMenuChoice.equals("exit")) {
                            printRegisterMenu();
                            registerMenuChoice = scanner.nextLine();
                            switch (registerMenuChoice) {
                                case "1":
                                    member.createAccount();
                                    // userAccount.createAccount();
                                    registerMenuChoice = "exit";
                                    break;
                                case "2":
                                    admin.createAccount();
                                    // registrationManager.createAccountStaff();
                                    registerMenuChoice = "exit";
                                    // continue handling
                                    break;
                                case "0":
                                    break;
                                default:
                                    System.out.println("\u001B[31mInvalid choice! \n\u001B[0m");
                            }

                        }

                        break;
                    case "2":
                        loginMenuChoice = "";
                        while (!loginMenuChoice.equals("0") && !loginMenuChoice.equals("exit")) {
                            printLoginMenu();
                            loginMenuChoice = scanner.nextLine();
                            switch (loginMenuChoice) {
                                case "1":
                                    if (member.login()) {
                                        memberNo = member.getMemberNo();
                                        // System.out.println("MemberNo in login : " + memberNo);
                                        customerMainMenuChoice = "";
                                        while (!customerMainMenuChoice.equals("0")
                                                && !customerMainMenuChoice.equals("exit")) {
                                            member.printMemberMainMenu();
                                            customerMainMenuChoice = scanner.nextLine();
                                            switch (customerMainMenuChoice) {
                                                case "0":
                                                    break;
                                                case "1":
                                                    System.out.print("Enter your invoice NO. (starting with ABC): ");
                                                    String invoiceNo = scanner.nextLine();

                                                    // Validate and format the invoice number
                                                    if (!invoiceNo.matches("ABC\\d{4,}")) {
                                                        System.out.println("\u001B[31mInvalid invoice number format. It should start with ABC followed by 4 or more digits (e.g., ABC1234)\u001B[0m");
                                                        continue; // Continue to prompt the user until a valid invoice number is provided
                                                    }
                                                    String paymentAmount;
                                                    do {
                                                        System.out.print("Enter the total payment amount: ");
                                                        paymentAmount = scanner.nextLine();
                                                        if (!isNumeric(paymentAmount)) {
                                                            System.err.println("Please enter number only.");
                                                        } else if (Double.parseDouble(paymentAmount) <= 0) {
                                                            System.err.println("Please enter a positive number.");
                                                        }
                                                    } while (!isNumeric(paymentAmount) || Double.parseDouble(paymentAmount) <= 0);
                                                    int value = (int) (Math.round(Double.parseDouble(paymentAmount) * POINTS_PER_RM) * loyalty.getMultiplier(memberNo));
                                                    new Earning(invoiceNo, value, memberNo);
                                                    policy.updateExpiryDate();
                                                    System.out.println(
                                                            "You have earned a total of " + value + " points!");
                                                    System.out.print("\n");
                                                    break;

                                                case "2":
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

                                                    int total = 0;
                                                    System.out.println(
                                                            "\n\n-----------------------------------------------------");
                                                    System.out.println(
                                                            "|                 Available Product                 |");
                                                    System.out.println(
                                                            "-----------------------------------------------------");
                                                    for (int i = 0; i < redemptionItems.length; i++) {
                                                        if (redemptionItems[i].getRedemptionValue() <= totalPoints) {
                                                            total++;
                                                            System.out.printf("%d. %s (%d points)\n", i + 1,
                                                                    redemptionItems[i].getName(),
                                                                    redemptionItems[i].getRedemptionValue());
                                                        }

                                                    }
                                                    System.out.println(
                                                            "-----------------------------------------------------");
                                                    System.out.print("Current points: " + totalPoints + '\n');

                                                    System.out.println("What would you like to redeem?");

                                                    if (total <= 0) {
                                                        System.out.println(
                                                                "Sorry, you have insufficient points to redeem any item\n");
                                                        break;
                                                    }

                                                    System.out.print("Enter your choice:");
                                                    String redemptionChoice = scanner.nextLine();

                                                    if (!isNumeric(redemptionChoice) || Integer.parseInt(redemptionChoice) < 1 || Integer.parseInt(redemptionChoice) > redemptionItems.length) {
                                                        System.err.println("Invalid input!");
                                                        break;
                                                    }

                                                {
                                                    try {
                                                        // write to txt file
                                                        writeToRedemptionHistory(
                                                                redemptionItems[Integer.parseInt(redemptionChoice) - 1],
                                                                memberNo);
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
                                                                ex);
                                                    }
                                                }

                                                    deductPoints(redemptionItems[Integer.parseInt(redemptionChoice) - 1]
                                                            .getRedemptionValue(), filteredList);

                                                    // for (Earning test : filteredList) {
                                                    // System.out.println(test.getValue());
                                                    // }
                                                    List<Earning> newEarnings = new ArrayList<>();
                                                    newEarnings.addAll(filteredList);
                                                    newEarnings.addAll(otherMembers);
                                                    Earning.rewriteToFile(newEarnings);
                                                    System.out.println("Redemption successful!\n");
                                                    break;

                                                case "3":
                                                    member.viewProfile();
                                                    break;
                                                case "4":
                                                    member.showReferees();
                                                    break;
                                                case "5":
                                                    Client client = new Client("127.0.0.2", 8000);
                                                    client.handleConnection();
                                                    break;

                                                default:
                                                    System.out.println("\u001B[31mInvalid Input.\u001B[0m");
                                            }
                                        }

                                    }
                                    break;
                                case "2":
                                    if (admin.login()) {
                                        adminMainMenuCHoice = "";
                                        while (!adminMainMenuCHoice.equals("0")
                                                && !adminMainMenuCHoice.equals("exit")) {
                                            admin.printAdminMainMenu();
                                            // printAdminMainMenu();
                                            adminMainMenuCHoice = scanner.nextLine();
                                            switch (adminMainMenuCHoice) {
                                                case "0":
                                                    break;
                                                case "1":
                                                    admin.checkCustomerDetails();
                                                    // adminDashBoard.checkCustomerDetails();
                                                    break;
                                                case "2":
                                                    admin.viewAllProducts();
                                                    // adminDashBoard.viewAllProducts();
                                                    break;
                                                case "3":
                                                    admin.checkEarningFile();
                                                    // adminDashBoard.checkEarningFile();
                                                    break;
                                                case "4":
                                                    admin.displayTopRedeemedItem();
                                                    // adminDashBoard.displayTopRedeemedItem();
                                                    break;
                                                case "5":
                                                    admin.displayLowRedeemedItem();
                                                    // adminDashBoard.displayLowRedeemedItem();
                                                    break;
                                                case "6":
                                                    admin.ActivityTracking();
                                                    // adminDashBoard.ActivityTracking();
                                                    break;
                                                case "7":
                                                    loyalty.printTierMultipliers();
                                                    System.out.print("Do you want to modify?(Y/N):");
                                                    String tierMultipliersChoice = scanner.nextLine();
                                                    while (!tierMultipliersChoice.equalsIgnoreCase("Y")
                                                            && !tierMultipliersChoice.equalsIgnoreCase("N")) {
                                                        System.out.println("\u001B[31mInvalid choice.\u001B[0m");
                                                        System.out.print("Do you want to modify?(Y/N):");
                                                        tierMultipliersChoice = scanner.nextLine();
                                                    }
                                                    if (tierMultipliersChoice.equalsIgnoreCase("Y")) {
                                                        System.out.print("Enter the tier you want to modify: ");
                                                        String tierChoice = scanner.nextLine();

                                                        while (!tierChoice.equals("1") && !tierChoice.equals("2")
                                                                && !tierChoice.equals("3") && !tierChoice.equals("4")) {
                                                            System.out.println("\u001B[31mInvalid choice.\u001B[0m");
                                                            System.out.print("Enter the tier you want to modify: ");
                                                            tierChoice = scanner.nextLine();
                                                        }
                                                        double multiplier = 0.0;
                                                        boolean validInput = false;

                                                        while (!validInput) {
                                                            System.out.print("Enter the new multiplier: ");
                                                            try {
                                                                multiplier = Double.parseDouble(scanner.nextLine());
                                                                validInput = true;
                                                            } catch (NumberFormatException e) {
                                                                System.out.println(
                                                                        "\u001B[31mInvalid input! Please enter a valid number.\u001B[0m");
                                                            }
                                                        }

                                                        switch (tierChoice) {
                                                            case "1":
                                                                loyalty.updateMultiplier("Bronze", multiplier);
                                                                break;
                                                            case "2":
                                                                loyalty.updateMultiplier("Silver", multiplier);
                                                                break;
                                                            case "3":
                                                                loyalty.updateMultiplier("Gold", multiplier);
                                                                break;
                                                            case "4":
                                                                loyalty.updateMultiplier("Platinum", multiplier);
                                                                break;
                                                            default:
                                                                System.out.println("\u001B[31mError Selection!\u001B[0m");
                                                                break;
                                                        }
                                                    }
                                                    break;
case "8":
                                                        System.out.println("-------------------------------------------------");
                                                        if (policy.getExpiryMonths() > 1) {
                                                            System.out.println("| Current Expiration Duration : " + policy.getExpiryMonths() + " Months\t|");
                                                        } else {
                                                            System.out.println("| Current Expiration Duration : " + policy.getExpiryMonths() + " Month \t|");
                                                        }
                                                        System.out.println("-------------------------------------------------");
                                                        System.out.print("Do you want to update expiration duration?(Y/N):");
                                                        String expirationDurationChoice = scanner.nextLine();
                                                        while (!expirationDurationChoice.equalsIgnoreCase("Y") && !expirationDurationChoice.equalsIgnoreCase("N")) {
                                                            System.out.println("\u001B[31mInvalid choice.\u001B[0m");
                                                            System.out.print("Do you want to update expiration duration?(Y/N):");
                                                            expirationDurationChoice = scanner.nextLine();
                                                        }
                                                        if (expirationDurationChoice.equalsIgnoreCase("Y")) {
                                                            validate = false;
                                                            while (!validate) {
                                                                System.out.print("Update the expiration durations in months (01-12) :");
                                                                expiryMonths = scanner.nextLine();
                                                                if (policy.validateMonth(expiryMonths)) {
                                                                    policy.setExpiryMonths(Integer.parseInt(expiryMonths));
                                                                    System.out.println("--------------------------------");
                                                                    System.out.println("New expiration durations : " + policy.getExpiryMonths());
                                                                    validate = true;
                                                                } else {
                                                                    System.err.println("\u001B[31mInvalid!\u001B[0m");
                                                                }
                                                            }
                                                        }
                                                    
                                                        break;
                                                case "9":
                                                    Server server = new Server(8000);
                                                    server.handleConnection();
                                                    break;

                                                default:
                                                    System.out.println("\u001B[31mInvalid choice.\u001B[0m");
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                case "0":
                                    break;

                                default:
                                    System.out.println("\u001B[31mInvalid choice.\u001B[0m");
                                //displayLoginMenu();
                            }

                        }

                        break;

                    case "3":
                        try {
                            member.forgot();
                            // userAccount.forgot();//member.forgot()
                        } catch (IOException ex) {
                            Logger.getLogger(MemberDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        break;

                    case "0":
                        EndingPage endingPage = new EndingPage();
                        endingPage.ending();
                        break;
                    default:
                        System.out.println("\u001B[31mInvalid choice! \n \u001B[0m");
                    //displayMenu();
                }

            }

        }

    }

}

class EarningComparator implements java.util.Comparator<Earning> {

    @Override
    public int compare(Earning a, Earning b) {
        return a.getEarningDate().isBefore(b.getEarningDate()) ? -1 : 1;
    }
}

