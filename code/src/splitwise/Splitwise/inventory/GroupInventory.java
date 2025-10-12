package Splitwise.inventory;

import Splitwise.model.Group;

import java.util.HashMap;
import java.util.Map;

class GroupInventory {
    private final Map<String, Group> groupMap = new HashMap<>();
    private static GroupInventory inventory;

    private GroupInventory() {
    }

    public static synchronized GroupInventory getInstance() {
        if (inventory == null) {
            inventory = new GroupInventory();
        }
        return inventory;
    }

    public void addGroup(Group group) {
        groupMap.put(group.getId(), group);
    }

    public Group getGroup(String id) {
        if (!groupMap.containsKey(id)) {
            System.out.println("[ERROR] | Group not found: " + id);
            return null;
        }
        return groupMap.get(id);
    }
}
