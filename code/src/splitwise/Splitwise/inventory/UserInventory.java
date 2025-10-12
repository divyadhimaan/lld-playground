package Splitwise.inventory;

import Splitwise.model.Group;
import Splitwise.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserInventory {
    private final Map<String, User> userMap;
    private final Map<User, List<Group>> userGroupMap;
    private static UserInventory inventory;

    private UserInventory() {
        this.userMap = new HashMap<>();
        this.userGroupMap = new HashMap<>();
    }

    public static synchronized UserInventory getInstance() {
        if (inventory == null) {
            inventory = new UserInventory();
        }
        return inventory;
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
    }

    public User getUser(String id) {
        if (!userMap.containsKey(id)) {
            System.out.println("[ERROR] | User not found: " + id);
            return null;
        }
        return userMap.get(id);
    }

    public void addGroupForUser(User user, Group group) {
        userGroupMap.putIfAbsent(user, new ArrayList<>());
        userGroupMap.get(user).add(group);
    }

    public List<Group> getGroupsForUser(User user) {
        return userGroupMap.getOrDefault(user, new ArrayList<>());
    }

}
