package Assignment;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Member extends Account {

    private String memberNo;
    private String referralMember;
    private LoginManager loginManager = new LoginManager();
    private RegistrationManager registrationManager = new RegistrationManager();
    private MemberDashBoard memberDashBoard = new MemberDashBoard();

    public Member(String username, String email, String phone, String password, String memberNo) {
        super(username, email, phone, password);
        this.memberNo = memberNo;
    }

    public Member() {
    }

    @Override
    public boolean login() {
        MemberInfo memberInfo = loginManager.loginMember();
        if (memberInfo != null) {
            setUsername(memberInfo.getUsername());
            setEmail(memberInfo.getEmail());
            setPhone(memberInfo.getPhone());
            setPassword(memberInfo.getPassword());
            this.memberNo = memberInfo.getMemberNo();
            this.referralMember = memberInfo.getReferralMember();
            return true;
        } else {
            return false;
        }

    }
    
    public void printMemberMainMenu(){
        memberDashBoard.printMemberMainMenu();
    }
    
    public void showReferees(){
        memberDashBoard.showReferees(memberNo);
    }
    
    public void viewProfile() throws FileNotFoundException{
        memberDashBoard.viewProfile(memberNo);
    }
    
    public void forgot() throws IOException{
        memberDashBoard.forgot();
    }
    
    @Override
    public void createAccount(){
        registrationManager.createAccountUser();
    }

    public String getMemberNo() {
        return memberNo;
    }

    public String getReferralMember() {
        return referralMember;
    }

    
    

}


