package factory;

import model.Theatre;
import model.User;
import model.UserType;

public class TheatreFactory {

    public Theatre createTheatre(User user, String theatreName){
        if(!verifyUserAsAdmin(user)){
            System.out.println("[ERROR]: User doesn't have create theatre access");
        }

        return new Theatre(theatreName, user);
    }

    private boolean verifyUserAsAdmin(User user){
        return user.getUserType().equals(UserType.ADMIN);
    }
}
