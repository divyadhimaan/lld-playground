package Splitwise.orchestration;

import Splitwise.Logger;
import Splitwise.factory.ExpenseFactory;
import Splitwise.factory.GroupFactory;
import Splitwise.factory.UserFactory;
import Splitwise.inventory.GroupInventory;
import Splitwise.inventory.UserInventory;
import Splitwise.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SplitwiseOrchestration {
    private static SplitwiseOrchestration orchestration;
    private final UserInventory userInventory;
    private final GroupInventory groupInventory;

    private final UserFactory userFactory;
    private final GroupFactory groupFactory;
    private final ExpenseFactory expenseFactory;

    private final Logger logger;


    private SplitwiseOrchestration() {
        this.userInventory = UserInventory.getInstance();
        this.groupInventory = GroupInventory.getInstance();
        this.logger = Logger.getInstance();

        this.userFactory = new UserFactory();
        this.groupFactory = new GroupFactory();
        this.expenseFactory = new ExpenseFactory();
    }

    public static synchronized SplitwiseOrchestration getInstance() {
        if (orchestration == null) {
            orchestration = new SplitwiseOrchestration();
        }
        return orchestration;
    }

    public void addUser(String name, String email, String phone) {
        User user = userFactory.createUser(name, email, phone);
        userInventory.addUser(user);
        System.out.println("[INFO] | User added successfully: " + user.getId());

    }

    public void viewUser(String userId) {
        User user = userInventory.getUser(userId);
        if (user != null) {
            logger.displayUserDetails(user);
        }
    }

    public void createGroup(String name) {
        Group group = groupFactory.createGroup(name);
        groupInventory.addGroup(group);
        System.out.println("[INFO] | Group created successfully: " + group.getId());
    }

    public void addUserToGroup(String userId, String groupId) {
        User user = userInventory.getUser(userId);
        Group group = groupInventory.getGroup(groupId);
        if (user != null && group != null) {
            group.addMember(user);
            userInventory.addGroupForUser(user, group);
            System.out.println("[INFO] | User " + userId + " added to group " + groupId + " successfully.");
        }
    }

    public void removeUserFromGroup(String userId, String groupId) {
        User user = userInventory.getUser(userId);
        Group group = groupInventory.getGroup(groupId);
        if (user != null && group != null) {
            group.removeMember(user);
            System.out.println("[INFO] | User " + userId + " removed from group " + groupId + " successfully.");
        }
    }

    public void showGroupDetails(String groupId) {
        Group group = groupInventory.getGroup(groupId);
        if (group != null) {
            logger.displayGroupDetails(group);
        }
    }

    public void showGroupsForUser(String userId) {
        User user = userInventory.getUser(userId);
        if (user != null) {
            List<Group> groups = userInventory.getGroupsForUser(user);
            if (!groups.isEmpty()) {
                logger.displayGroupsForUser(user, groups);
            }
        }
    }

    // No split map (for Equal split)

    public void addExpense(String groupId, double expenseAmount, String description, String adderId,
                           String participants, String strategy) {
        addExpenseInternal(groupId, expenseAmount, description, adderId, participants, strategy, null);
    }

    // With split map (for Percentage/Exact splits)
    public void addExpense(String groupId, double expenseAmount, String description, String adderId,
                           String participants, String strategy, Map<String, Double> splitMap) {
        addExpenseInternal(groupId, expenseAmount, description, adderId, participants, strategy, splitMap);
    }

    public void addExpenseInternal(String groupId, double expenseAmount, String description, String adderId, String participants,
                                   String strategy, Map<String, Double> splitMap) {
        Group group = groupInventory.getGroup(groupId);
        User expenseAddingUser = userInventory.getUser(adderId);

        if (group == null || expenseAddingUser == null)
            return;

        List<User> users = getParticipantsInExpense(group, participants);
        if (users.isEmpty()) {
            System.out.println("[ERROR]: Failed adding the expense - Invalid User provided");
            return;
        }

        Map<User, Double> userSplitMap = null;
        if (splitMap != null && !splitMap.isEmpty()) {
            userSplitMap = new HashMap<>();
            for (Map.Entry<String, Double> entry : splitMap.entrySet()) {
                User user = userInventory.getUser(entry.getKey());
                if (user == null || !verifyUserInGroup(group, user)) {
                    System.out.println("[ERROR]: Invalid user in split map: " + entry.getKey());
                    return; // or skip invalid user
                }
                userSplitMap.put(user, entry.getValue());
            }
        }


        Expense expense = expenseFactory.createExpense(group, expenseAmount, description, expenseAddingUser, users, strategy, userSplitMap);
        if (expense == null) {
            System.out.println("[ERROR]: Failed adding the expense");
            return;
        }
        group.addExpense(expense);
        Map<User, Double> splitAmounts = expense.calculateSplit();


        logger.displayExpense(ExpenseType.CREATED, expense, splitAmounts);
    }

    private List<User> getParticipantsInExpense(Group group, String participants) {
        List<User> users = new ArrayList<>();
        List<String> participantsList = List.of(participants.split(","));

        for (String participantId : participantsList) {
            User currentUser = userInventory.getUser(participantId);

            if (currentUser == null) {
                System.out.println("[ERROR]: User id(" + participantId + ") not valid.");
                return new ArrayList<>();
            }
            if (!verifyUserInGroup(group, currentUser)) {
                System.out.println("[ERROR]: User " + participantId + " not part of the specified group");
            }
            users.add(currentUser);
        }
        return users;
    }

    private boolean verifyUserInGroup(Group group, User user) {
        if (group.getMembers().containsKey(user.getId())) {
            return true;
        }
        return false;
    }

    public void updateExpense(ExpenseUpdateType expenseUpdateType, String groupId, String expenseId, Object value) {
        Group group = groupInventory.getGroup(groupId);
        if (group == null) return;

        Expense expense = group.getExpenseById(expenseId);
        if (expense == null) {
            System.out.println("[ERROR]: Trying to update an invalid expense");
            return;
        }

        switch (expenseUpdateType) {
            case ADD_USER -> {
                if (value instanceof String usersStr) {
                    List<User> newUsers = getParticipantsInExpense(group, usersStr);
                    for (User user : newUsers) expense.addUserToExpense(user);
                }
            }
            case REMOVE_USER -> {
                if (value instanceof String usersStr) {
                    List<User> removeUsers = getParticipantsInExpense(group, usersStr);
                    for (User user : removeUsers) expense.removeUserFromExpense(user);
                }
            }
            case UPDATE_AMOUNT -> {
                if (value instanceof Double newAmount) {
                    expense.updateAmount(newAmount); // Add setter in Expense
                }
            }
            default -> System.out.println("[ERROR]: Invalid expense update type");
        }

        group.updateExpense(expenseId, expense);
        Map<User, Double> splitAmounts = expense.calculateSplit();
        logger.displayExpense(ExpenseType.UPDATED, expense, splitAmounts);
    }

    public void viewExpenseSummaryForUser(String userId) {
        User user = userInventory.getUser(userId);
        if (user == null)
            return;
        Map<User, Double> expensesPerUser = user.getOwedAmountPerUser();
        logger.displayExpenseForUser(expensesPerUser, user);
    }

    public void showTransactionHistoryForUser(String userId) {
        User user = userInventory.getUser(userId);
        if (user == null)
            return;

        List<Expense> expenses = user.getExpenses();
        logger.displayAllExpensesForUser(expenses, user);
    }

    public void showGroupBalanceSummary(String groupId) {
        Group group = groupInventory.getGroup(groupId);
        if (group == null) {
            System.out.println("[ERROR] | Group not found: " + groupId);
            return;
        }

        Map<String, Double> balanceMap = new HashMap<>(); // key = "UserA->UserB"

        for (Expense expense : group.getExpenses().values()) {
            Map<User, Double> splitAmounts = expense.calculateSplit();
            User payer = expense.getExpenseAddingUser();

            for (Map.Entry<User, Double> entry : splitAmounts.entrySet()) {
                User participant = entry.getKey();
                double amount = entry.getValue();
                if (participant.equals(payer)) continue; // skip payer themselves

                String key = participant.getId() + "->" + payer.getId();
                balanceMap.put(key, balanceMap.getOrDefault(key, 0.0) + amount);
            }
        }

        logger.displayGroupBalances(balanceMap, group);
    }


}
