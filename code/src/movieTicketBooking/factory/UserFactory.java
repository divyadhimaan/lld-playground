package factory;

import model.User;
import model.UserType;

public class UserFactory {
    private final String verificationCode;

    public UserFactory(String code){
        this.verificationCode = code;
    }
    public User createUser(String userType, String userName, String password){
        User user = null;
        if (userType.equalsIgnoreCase("ADMIN")) {
            if(verifyAdmin(password)){
                user = new User(userName, UserType.ADMIN, "ADMIN", password);
            }else {
                System.out.println("[ERROR]: Verification for Admin failed");
            }
        }else if(userType.equalsIgnoreCase("CUSTOMER")){
            user = new User(userName, UserType.CUSTOMER, "CUST", password);
        }
        return user;
    }

    // change verification logic later
    private boolean verifyAdmin(String password){
        return password.contains(verificationCode);
    }
}
