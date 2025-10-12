package Splitwise.factory;

import Splitwise.model.User;

import java.util.concurrent.atomic.AtomicInteger;

class UserFactory {
    private final AtomicInteger userIdCounter = new AtomicInteger(0);

    public User createUser(String name, String email, String phone) {
        String id = generateUserId(name, phone);
        return new User(id, name, email, phone);
    }

    private String generateUserId(String name, String phone) {
        int count = userIdCounter.getAndIncrement();
        return name + "_" + phone.substring(phone.length() - 4) + "_" + count;
    }
}
