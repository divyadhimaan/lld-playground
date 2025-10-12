package Splitwise.factory;

import Splitwise.model.Group;

import java.util.concurrent.atomic.AtomicInteger;

class GroupFactory {
    private final AtomicInteger groupIdCounter = new AtomicInteger(0);

    public Group createGroup(String name) {
        String id = generateUserId(name);
        return new Group(id, name);
    }

    private String generateUserId(String name) {
        int count = groupIdCounter.getAndIncrement();
        return name + "_" + count;
    }
}
