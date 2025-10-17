package repository;

import model.Seat;
import model.User;
import model.UserType;

import java.util.HashMap;
import java.util.Map;


public class UserInventory {
    private static UserInventory instance;
    private final Map<String, User> userMap;

    private UserInventory(){
        this.userMap = new HashMap<>();
    }
    public static synchronized UserInventory getInstance(){
        if(instance==null)
            instance=new UserInventory();
        return instance;
    }

    public void addUser(User user){
        if(user==null){
            System.out.println("[ERROR]: Failed to add user to inventory");
            return;
        }
        userMap.put(user.getUserId(), user);
    }

    public User getUserById(String userId){
        User user = userMap.get(userId);
        if(user==null){
            System.out.println("[ERROR]: Accessing Invalid User");
        }
        return user;
    }

    public boolean verifyUserAsAdmin(User user){
        return user.getUserType().equals(UserType.ADMIN);
    }
}
