package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private AtomicInteger idCounter = new AtomicInteger(0);
    @Getter
    private String userId;
    @Getter
    private String name;
    @Getter @Setter
    private Card card;
    @Getter @Setter
    private List<Account> accounts;

    User(String name){
        this.userId = "User-" + idCounter.getAndIncrement();
        this.name = name;
        this.card = null;
        this.accounts = new ArrayList<>();
    }


}
