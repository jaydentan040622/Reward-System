
package Assignment;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LoginManager {

    private static final String FILENAME = "user.txt";
    private Scanner scanner = new Scanner(System.in);
    private String memberNo;
    private String staffNo;

    public LoginManager() {
    }


    public String getMemberNo() {
        return memberNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    AdminInfo loginStaff() {

        try {
            Path path = Paths.get("staff.txt");
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n===========================================");
            System.out.println("|               Login (Staff)              |");
            System.out.println("===========================================\n");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            try {
                String line = null;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(" ");
                    if (user[0].equals(username) && user[3].equals(password)) {
                        found = true;
                        AdminInfo adminInfo = new AdminInfo(user[0], user[1], user[2], user[3], user[4]);
                        System.out.println("Login successful.");
                        System.out.println("-------------------------------\n");
                        return adminInfo;
                    }
                }
                if (!found) {
                    System.out.println("\u001B[31m\nInvalid username or password. \u001B[0m");
                    System.out.println("-------------------------------");
                    return null;

                }

            } catch (IOException ex) {
                System.out.println("Error reading staff accounts: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
                }
            }
            System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSES A KEY
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    MemberInfo loginMember() {
        try {
            Path path = Paths.get(FILENAME);
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n===========================================");
            System.out.println("|             Login (Customer)             |");
            System.out.println("===========================================\n");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            boolean loginSuccessful = false; // Flag to track login status
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(" ");
                    if (user[0].equals(username) && user[3].equals(password)) {
                        memberNo = user[4];
                        // Additional logic if login is successful
                        System.out.println("\nLogin successful");
                        System.out.println("-------------------------------\n");
                        // Direct to the user menu
                        loginSuccessful = true; // Set the flag to true upon successful login
                        MemberInfo memberInfo = new MemberInfo(user[0], user[1], user[2], user[3], user[4], user[5]);

                        return memberInfo; //continue; 
                    }
                }
                if (!loginSuccessful) { // Check login status before displaying invalid message
                    boolean invalidInput = true;
                    while (invalidInput) {
                        System.out.println("\u001B[31m\nInvalid username or passwod. \u001B[0m");
                        System.out.println("-------------------------------");
                        return null;
                    }
                }
            } catch (IOException ex) {
                System.err.println("Error reading user accounts: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
            
        }
        return null;

    }

}

class AdminInfo {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String adminNo;

    public AdminInfo(String username, String email, String phone, String password, String adminNo) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.adminNo = adminNo;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getAdminNo() {
        return adminNo;
    }
}

class MemberInfo {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String memberNo;
    private String referralMember;

    public MemberInfo(String username, String email, String phone, String password, String memberNo, String referralMember) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.memberNo = memberNo;
        this.referralMember = referralMember;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public String getReferralMember() {
        return referralMember;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMemberNo(String custAccountNo) {
        this.memberNo = custAccountNo;
    }

    public void setReferralMember(String referralMember) {
        this.referralMember = referralMember;
    }

}
