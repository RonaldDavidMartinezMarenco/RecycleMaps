package model;

public class ModelUserr
{
  private int UserID;
  private String userName;
  private String email;
  private String password;
  private String verifyCode;

    public ModelUserr() {
    }
    
    public ModelUserr(int UserID, String userName, String email, String password, String verifyCode) {
        this.UserID = UserID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }
    public ModelUserr(int UserID, String userName, String email, String password) {
        this.UserID = UserID;
        this.userName = userName;
        this.email = email;
        this.password = password;    
    }
    
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return userName;
    } 

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
    
}
