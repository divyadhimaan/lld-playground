package model;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private final AtomicInteger id= new AtomicInteger();
    @Getter
    private final String userId;
    @Getter
    private final UserType userType;
    @Getter
    private final String userName;
    private final String password;


    public User(String userName, UserType userType, String idGen, String password){
        this.userId = idGen + id.incrementAndGet();
        this.userName = userName;
        this.userType = userType;
        this.password = password;
    }

    public void displayInfo(){
        System.out.println("===== User Info =====");
        System.out.println("User ID   : " + getUserId());
        System.out.println("User Name : " + getUserName());
        System.out.println("User Type : " + getUserType());
        System.out.println("=====================");
    }

}
