
package Assignment;

public class Admin extends Account {

    private String adminNo;
    private AdminDashBoard adminDashBoard = new AdminDashBoard();
    private LoginManager loginManager = new LoginManager();
    private RegistrationManager registrationManager = new RegistrationManager();

    public Admin(String username, String email, String phone, String password, String adminNo) {
        super(username, email, phone, password);
        this.adminNo = adminNo;
    }

    public Admin() {
    }
     
    @Override
    public boolean login() {
        AdminInfo adminInfo = loginManager.loginStaff();
        if (adminInfo != null) {
            // Set the attributes of Admin class using data from AdminInfo
            setUsername(adminInfo.getUsername());
            setEmail(adminInfo.getEmail());
            setPhone(adminInfo.getPhone());
            setPassword(adminInfo.getPassword());
            // Set adminNo
            this.adminNo = adminInfo.getAdminNo();
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public void createAccount(){
        registrationManager.createAccountStaff();
    }
    
    public void createAccountStaff(){
        registrationManager.createAccountStaff();
    }
    
    public void printAdminMainMenu(){
        adminDashBoard.printAdminMainMenu();
    }
    
    public void checkCustomerDetails(){
        adminDashBoard.checkCustomerDetails();
    }
    
    public void viewAllProducts(){
        adminDashBoard.viewAllProducts();
    }
    
    public void checkEarningFile(){
        adminDashBoard.checkEarningFile();
    }
    
    public void displayTopRedeemedItem(){
        adminDashBoard.displayTopRedeemedItem();
    }
    
    public void displayLowRedeemedItem(){
        adminDashBoard.displayLowRedeemedItem();
    }
    
    public void ActivityTracking(){
        adminDashBoard.ActivityTracking();
    }
    
    public String getAdminNo(){
        return adminNo;
    }  
}
